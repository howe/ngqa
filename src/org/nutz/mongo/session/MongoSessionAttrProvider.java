package org.nutz.mongo.session;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Each;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.mongo.Mongos;
import org.nutz.mongo.annotation.Co;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoSessionAttrProvider {

	public DBObject toAttr(Object obj) throws Throwable {
		return new MongoSessionAttr(obj).dbo();
	}
	
	public Object fromAttr(DBObject dbo) {
		throw Lang.noImplement();
	}
}

class MongoSessionAttr {
	String type;
	String info;
	Object data;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MongoSessionAttr(Object obj) throws Throwable {
		if (obj == null) {
			type = "null";
			return;
		}
		info = obj.getClass().getName();
		Mirror mirror = Mirror.me(obj);
		if (mirror.isNumber() || mirror.isBoolean() || mirror.isChar() || mirror.isStringLike()) {
			type = "simple";
			data = obj;
		} else if (obj.getClass().getAnnotation(Co.class) != null ) {
			type = "nutz_mongo";
			data = Mongos.dbo("id", Mongos.entity(obj).toDBObject(obj).get("_id").toString());
		} else if (mirror.isContainer()) {
			if (mirror.isMap()) {
				type = "map";
				Map<String, Object> map = new HashMap<String, Object>();
				for (Entry<String, Object> entry : ((Map<String, Object>)obj).entrySet()) {
					MongoSessionAttr attr = new MongoSessionAttr(entry.getValue());
					map.put(entry.getKey(), attr.dbo());
				}
				data = map;
			} else if (mirror.isArray() || obj instanceof List || obj instanceof Set) {
				if (mirror.isArray())
					type = "array";
				else if (obj instanceof List)
					type = "list";
				else 
					type = "set";
				final List<DBObject> array = new ArrayList<DBObject>();
				Lang.each(obj, new Each<Object>() {
					public void invoke(int index, Object ele, int length) throws org.nutz.lang.ExitLoop ,org.nutz.lang.ContinueLoop ,org.nutz.lang.LoopException {
						try {
							array.add(new MongoSessionAttr(ele).dbo());
						} catch (Throwable e) {
							throw Lang.wrapThrow(e);
						}
					};
				});
				data = array.toArray();
			} else {
				throw Lang.noImplement();
			}
		} else if (obj instanceof Serializable) {
			type = "serializable";
			info = obj.getClass().getName();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			data = out.toByteArray();
		} else { //好吧,啥都不是? 转Json!!
			type = "json";
			data = Json.toJson(obj, JsonFormat.compact());
		}
		
	}
	
	DBObject dbo() {
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("type", type);
		dbo.put("info", info);
		dbo.put("data", data);
		return dbo;
	}
}