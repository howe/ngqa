package org.nutz.mongo.session;

import org.nutz.lang.util.SimpleContext;
import org.nutz.mongo.MongoDao;

import com.mongodb.DBCollection;

public class ManagerContext extends SimpleContext {

	private MongoDao dao;
	private DBCollection sessions;
	private SessionValueAdpter provider;
	
	public MongoDao getMongoDao() {
		return dao;
	}
	public void setMongoDao(MongoDao dao) {
		this.dao = dao;
	}
	public DBCollection getSessions() {
		return sessions;
	}
	public void setSessions(DBCollection sessions) {
		this.sessions = sessions;
	}
	public SessionValueAdpter getProvider() {
		return provider;
	}
	public void setProvider(SessionValueAdpter provider) {
		this.provider = provider;
	}
	
}
