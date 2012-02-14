package org.nutz.ngqa.service;

import java.util.Date;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoConnector;
import org.nutz.mongo.Mongos;
import org.nutz.ngqa.bean.Freshable;

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
	
	public void fresh(Class<? extends Freshable> klass, Object id) {
		String collName = Mongos.entity(klass).getCollectionName(null);
		coll(collName).update(new BasicDBObject("_id", id), new BasicDBObject("$set", new BasicDBObject("updatedAt", new Date())));
	}
	
	public void fresh(Freshable obj) {
		String collName = Mongos.entity(obj).getCollectionName(null);
		obj.setUpdatedAt(new Date());
		coll(collName).update(new BasicDBObject("_id", obj.getId()), new BasicDBObject("$set", new BasicDBObject("updatedAt", obj.getUpdatedAt())));
	}
}
