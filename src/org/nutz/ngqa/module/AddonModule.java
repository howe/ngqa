package org.nutz.ngqa.module;

import java.awt.List;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.MCur;
import org.nutz.mongo.util.Moo;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.service.CommonMongoService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBRef;

@IocBean(create="init")
@InjectName
@Filters({@By(type=CheckSession.class,args={"me", "/index.jsp"})})
@At("/addon")
public class AddonModule {
	
	@At("/question/?/watch")
	public void watch(int questionId, @Attr("me") User me) {
		dao.update(Question.class, new BasicDBObject("_id", questionId), new BasicDBObject("$addToSet", new BasicDBObject("watchers", new DBRef(null, "user", me.getId()))));
	}
	
	@At("/question/?/unwatch")
	public void unwatch(int questionId, @Attr("me") User me) {
		dao.update(Question.class, new BasicDBObject("_id", questionId), new BasicDBObject("$pop", new BasicDBObject("watchers", new DBRef(null, "user", me.getId()))));
	}
	
	
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
	
	@Inject
	private CommonMongoService commons;
	
	public void init() {
	}
}
