package org.nutz.mongo.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.Set;

import org.bson.types.BSONTimestamp;
import org.bson.types.Binary;
import org.bson.types.Code;
import org.bson.types.CodeWScope;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.ObjectId;
import org.bson.types.Symbol;
import org.nutz.castor.Castors;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Each;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.Mongos;
import org.nutz.mongo.annotation.Co;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBRefBase;

/**
 * 
 * @author wendal(wendal1985@gmail.com)
 *
 */
public class SessionValueAdpter {

	public DBObject toValue(Object obj) throws Throwable {
		return new SessionValue(obj).dbo();
	}

	public Object fromValue(DBObject dbo, MongoDao dao) throws Throwable {
		return new SessionValue(dbo).obj(dao);
	}
}

class SessionValue {
	String type;
	String info;
	Object data;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SessionValue(Object obj) throws Throwable {
		if (obj == null) {
			type = "null";
			return;
		}
		info = obj.getClass().getName();
		Mirror mirror = Mirror.me(obj);
		//判断是否为原生支持类型
		if (mirror.isNumber() || mirror.isBoolean() || mirror.isChar()
				|| mirror.isStringLike()
				|| obj instanceof Pattern
				|| obj instanceof Date
				|| obj instanceof byte[]
				|| obj instanceof BSONTimestamp
				|| obj instanceof Code
				|| obj instanceof CodeWScope
				|| obj instanceof ObjectId
				|| obj instanceof Binary
				|| obj instanceof UUID
				|| obj instanceof Symbol
				|| obj instanceof DBRefBase
				|| obj instanceof MinKey
				|| obj instanceof MaxKey) {
			type = "simple";
			data = obj;
			return;
		}
		if (obj instanceof DBObject ) {
			type = "dbo";
			data = obj;
			return;
		}
		if (obj.getClass().getAnnotation(Co.class) != null) {
			type = "nutz_mongo";
			data = Mongos.entity(obj).toDBObject(obj).get("_id").toString();
			return;
		}
		if (mirror.isContainer()) {
			if (mirror.isMap()) {
				type = "map";
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<String, Object> entry : ((Map<String, Object>) obj)
						.entrySet()) {
					SessionValue attr = new SessionValue(
							entry.getValue());
					map.put(entry.getKey(), attr.dbo());
				}
				data = map;
				return;
			} else if (mirror.isArray() || obj instanceof List
					|| obj instanceof Set) {
				if (mirror.isArray())
					type = "array";
				else if (obj instanceof List)
					type = "list";
				else
					type = "set";
				final List<DBObject> array = new ArrayList<DBObject>();
				Lang.each(obj, new Each<Object>() {
					public void invoke(int index, Object ele, int length)
							throws org.nutz.lang.ExitLoop,
							org.nutz.lang.ContinueLoop,
							org.nutz.lang.LoopException {
						try {
							array.add(new SessionValue(ele).dbo());
						} catch (Throwable e) {
							throw Lang.wrapThrow(e);
						}
					};
				});
				data = array.toArray();
				return;
			} else {
				throw Lang.noImplement();
			}
		}
		if (obj instanceof Serializable) {
			type = "serializable";
			info = obj.getClass().getName();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			data = new Binary(out.toByteArray());
			return;
		}

		// 好吧,啥都不是? 转Json!!
		type = "json";
		data = Json.toJson(obj, JsonFormat.compact());
	}

	DBObject dbo() {
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("type", type);
		dbo.put("info", info);
		dbo.put("data", data);
		return dbo;
	}

	SessionValue(DBObject dbo) {
		type = (String) dbo.get("type");
		info = (String) dbo.get("info");
		data = dbo.get("data");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	Object obj(MongoDao dao) throws Throwable {
		if ("null".equals(type))
			return null;
		if ("simple".equals(type))
			return Castors.me().castTo(data, Class.forName(info));
		if ("dbo".equals(type)) {
			if (data instanceof DBObject)
				return data;
			DBObject dbo = (DBObject) Class.forName(info).newInstance();
			dbo.putAll((Map)data);
			return dbo;
		}
		if ("nutz_mongo".equals(type))
			return dao.findById(Class.forName(info), (String) data);
		if ("serializable".equals(type)) {
			ByteArrayInputStream in = new ByteArrayInputStream((byte[]) data);
			ObjectInputStream oin = new ObjectInputStream(in);
			Object obj = oin.readObject();
			oin.close();
			return obj;
		}
		if ("json".equals(type))
			return Json.fromJson(Class.forName(info), (String) data);
		if ("array".equals(type) || "list".equals(type) || "set".equals(type)) {
			List<Object> list = new ArrayList<Object>();
			for (Object obj : (List<Object>) data) {
				list.add(new SessionValue((DBObject) obj).obj(dao));
			}
			if ("list".equals(type))
				return list;
			if ("set".equals(type))
				return new HashSet<Object>(list);
			if ("array".equals(type)) {
				Object array = Array.newInstance(Class.forName(info),
						((List<Object>) data).size());
				for (int i = 0; i < list.size(); i++) {
					Array.set(array, i, list.get(i));
				}
				return array;
			}

		}
		if ("map".equals(type)) {
			DBObject data = (DBObject) this.data;
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (String key : data.keySet())
				map.put(key, new SessionValue((DBObject) data.get(key)));
			return map;
		}
		throw Lang.impossible();
	}
}