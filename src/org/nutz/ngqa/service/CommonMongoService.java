package org.nutz.ngqa.service;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoConnector;
import org.nutz.mongo.Mongos;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@IocBean(name="commons", args={"refer:connector","ngqa"})
public class CommonMongoService extends AbstractMongoService {

	public CommonMongoService(MongoConnector conn, String dbname) {
		super(conn, dbname);
	}

	public int seq(final String seqName) {
		final Object[] objs = new Object[1];
		dao.run(new Callback<DB>() {

			@Override
			public void invoke(DB db) {
				DBObject q = Mongos.dbo("name", seqName);
				DBObject o = Mongos.dbo("$inc", new BasicDBObject("id", 1));
				objs[0] = db.getCollection("inc_ids")
						.findAndModify(q, null, null, false, o, true, true)
						.get("id");
			}
		});
		return (Integer) objs[0];
	}
	
	public DBCollection coll(final String collName) {
		final Object[] objs = new Object[1];
		dao.run(new Callback<DB>() {
			public void invoke(DB db) {
				objs[0] = db.getCollection(collName);
			}
		});
		return (DBCollection) objs[0];
	}
	
	
}
