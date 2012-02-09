package org.nutz.ngqa.mvc;

import java.net.UnknownHostException;

import lombok.Data;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Data
public class Mongos {

	private Mongo mongo;
	private DB db;
	
	public Mongos() throws UnknownHostException, MongoException {
		mongo = new Mongo();
	}
	
	public Mongos(String host, int port) throws UnknownHostException, MongoException {
		mongo = new Mongo(host, port);
	}
	
	public void setDbName(String dbName) {
		this.db = this.mongo.getDB(dbName);
	}
	
	public DBCollection coll(String collectionName) {
		return db.getCollection(collectionName);
	}
}
