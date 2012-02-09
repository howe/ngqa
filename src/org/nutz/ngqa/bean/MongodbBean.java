package org.nutz.ngqa.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.nutz.lang.Mirror;

import com.mongodb.DBObject;

@SuppressWarnings("rawtypes")
public class MongodbBean implements DBObject {
	
	protected transient Mirror mirror = Mirror.me(this);
	
	protected transient Map<String, Field> fields = new HashMap<String, Field>();
	
	private Object _id;

	@Override
	public boolean containsField(String filedName) {
		return fields.keySet().contains(filedName);
	}

	@Override
	public boolean containsKey(String key) {
		return fields.keySet().contains(key);
	}

	@Override
	public Object get(String filedName) {
		return mirror.getValue(this, fields.get(filedName));
	}

	@Override
	public Set<String> keySet() {
		return fields.keySet();
	}

	@Override
	public Object put(String key, Object value) {
		mirror.setValue(this, key, value);
		return null;
	}

	@Override
	public void putAll(BSONObject bsonObject) {
		for (String key : bsonObject.keySet()) {
			put(key, bsonObject.get(key));
		}
	}

	@Override
	public void putAll(Map map) {
		for (Object key : map.keySet()) {
			put((String) key, map.get(key));
		}
	}

	@Override
	public Object removeField(String arg0) {
		return null;
	}

	@Override
	public Map toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : fields.keySet()) {
			map.put(key, get(key));
		}
		return map;
	}

	@Override
	public boolean isPartialObject() {
		return false;
	}

	@Override
	public void markAsPartialObject() {
	}

	public Object get_id() {
		return _id;
	}
	
	public void set_id(Object _id) {
		this._id = _id;
	}
}
