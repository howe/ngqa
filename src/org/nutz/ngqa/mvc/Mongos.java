package org.nutz.ngqa.mvc;

import java.net.UnknownHostException;

import org.nutz.lang.Lang;
import org.nutz.ngqa.bean.MongodbBean;

import lombok.Data;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
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
	
	public <T> T map2(DBObject dbo, Class<T> klass) {
		T t = null;
		try {
			t = klass.newInstance();
			if (t instanceof MongodbBean)
				((MongodbBean)t).putAll(dbo);
		} catch (Throwable e) {
			throw Lang.wrapThrow(e);
		}
		return t;
	}
	
	public int autoInc(String idName){
		DBObject q = new BasicDBObject("name", idName);
		DBObject o = new BasicDBObject("$inc", 1);
		return (Integer) db.getCollection("inc_ids")
							.findAndModify(q, null, null, false, o, true, true)
							.get("id");
	}
	
}
