package org.nutz.ngqa.service;

import org.nutz.mongo.MongoConnector;
import org.nutz.mongo.MongoDao;

public abstract class AbstractMongoService {

	protected MongoDao dao;

	public AbstractMongoService(MongoConnector conn, String dbname) {
		this.dao = conn.getDao(dbname);
	}

	public MongoDao dao() {
		return dao;
	}

}
