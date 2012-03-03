package org.nutz.mongo.session;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import org.bson.types.ObjectId;
import org.nutz.lang.Lang;
import org.nutz.mongo.MongoDao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@SuppressWarnings("unchecked")
public class MongoSession {

	protected DBCollection sessions;
	private ObjectId id;
	private BasicDBObject queryKey;
	private boolean newCreate;
	protected SessionValueAdpter provider;
	protected MongoDao dao;

	public MongoSession(DBCollection sessions, ObjectId id,
			SessionValueAdpter provider, MongoDao dao) {
		this.sessions = sessions;
		this.id = id;
		queryKey = new BasicDBObject("_id", id);
		this.provider = provider;
		this.dao = dao;
	}

	public Object getAttribute(String key) {
		Object attr = ((Map<String, Object>) fetch("attr")).get(key);
		if (attr == null)
			return null;
		try {
			return provider.fromValue((DBObject) attr, dao);
		} catch (Throwable e) {
			throw Lang.wrapThrow(e);
		}
	}

	public void setAttribute(String key, Object obj) {
		try {
			sessions.update(queryKey, new BasicDBObject("$set",
					new BasicDBObject("attr." + key, provider.toValue(obj))));
		} catch (Throwable e) {
			throw Lang.wrapThrow(e);
		}
	}

	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(((Map<String, Object>) fetch("attr"))
				.keySet());
	}

	public long getCreationTime() {
		return (Long) fetch("creationTime");
	}

	public String getId() {
		return id.toString();
	}

	public long getLastAccessedTime() {
		return (Long) fetch("lastAccessedTime");
	}

	public void touch() {
		sessions.update(queryKey, new BasicDBObject("$set", new BasicDBObject(
				"lastAccessedTime", System.currentTimeMillis())));
	}

	public int getMaxInactiveInterval() {
		return (Integer) fetch("maxInactiveInterval");
	}

	public void invalidate() {
		sessions.remove(queryKey);
	}

	public void removeAttribute(String key) {
		sessions.update(queryKey, new BasicDBObject("$unset",
				new BasicDBObject("attr." + key, 1)));
	}

	public void setMaxInactiveInterval(int interval) {
		sessions.update(queryKey, new BasicDBObject("$set", new BasicDBObject(
				"maxInactiveInterval", interval)));
	}

	protected Object fetch(String key) {
		DBObject dbo = sessions.findOne(queryKey, new BasicDBObject(key, 1));
		if (dbo == null)
			throw Lang.makeThrow("Session is invalidated!");
		return dbo.get(key);
	}

	public Object getValue(String key) {
		return ((Map<String, Object>) fetch("info")).get(key);
	}

	public void putValue(String key, Object obj) {
		try {
			sessions.update(queryKey, new BasicDBObject("$set",
					new BasicDBObject("info." + key, provider.toValue(obj))));
		} catch (Throwable e) {
			throw Lang.wrapThrow(e);
		}
	}

	public String[] getValueNames() {
		return ((Map<String, Object>) fetch("info")).keySet().toArray(
				new String[0]);
	}

	public void removeValue(String key) {
		sessions.update(queryKey, new BasicDBObject("$unset",
				new BasicDBObject("info." + key, 1)));
	}

	public boolean isNew() {
		return newCreate;
	}

	protected void setNewCreate(boolean newCreate) {
		this.newCreate = newCreate;
	}

	public static final MongoSession create(DBCollection sessions,
			Map<String, String> info, SessionValueAdpter provider,
			MongoDao dao) {
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("_id", new ObjectId());
		dbo.put("info", info != null ? info : Collections.EMPTY_MAP);
		dbo.put("creationTime", System.currentTimeMillis());
		dbo.put("lastAccessedTime", System.currentTimeMillis());
		dbo.put("maxInactiveInterval", 30 * 60 * 1000); // 30min
		dbo.put("attr", Collections.EMPTY_MAP);
		sessions.insert(dbo);
		return new MongoSession(sessions, dbo.getObjectId("_id"), provider, dao);
	}
}
