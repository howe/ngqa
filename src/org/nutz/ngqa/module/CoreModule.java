package org.nutz.ngqa.module;

import java.util.Date;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.MCur;
import org.nutz.mongo.util.Moo;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.service.CommonMongoService;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxReturn;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

@IocBean(create="init")
@InjectName
@Filters({@By(type=CheckSession.class,args={"me", "/index.jsp"})})
public class CoreModule {
	
	@Inject("java:$commons.coll('question')")
	private DBCollection questionColl;
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
	
	@Inject
	private CommonMongoService commons;
	
	public void init() {
		dao.create(Question.class, false);
		dao.create(Answer.class, false);
	}

	/*question的title是必须的,其他都是可选*/
	@At("/ask")
	@AdaptBy(type=JsonAdaptor.class)
	public AjaxReturn createQuestion(final @Param("..")Question question, @Attr("me") User user) {
		if (question == null || Lang.length(question.getTitle()) < 5)
			return Ajax.fail().setMsg("Not OK");
		question.setId(commons.seq("question"));
		question.setUser(user);
		question.setCreatedAt(new Date());
		question.setUpdatedAt(new Date());
		question.setTags(new String[0]);
		dao.runNoError(new Callback<DB>() {
			public void invoke(DB arg0) {
				dao.save(question);
			}
		});
		return Ajax.ok().setData(question);
	}
	
	/*暂时只支持简单分页,每页10条记录*/
	@At({"/question/list/?","/question/list"})
	public AjaxReturn query(int page) {
		if (page < 1)
			page = 1;
		int skip = (page - 1) * 10;
		MCur cur = MCur.DESC("updatedAt").limit(10).skip(skip);
		return Ajax.ok().setData(dao.find(Question.class, null, cur));
	}
	
	/*获取具体的question*/
	@At("/question/?")
	public AjaxReturn fetch(int questionId) {
		Question question = dao.findOne(Question.class, new BasicDBObject("_id", questionId));
		if (question != null)
			return Ajax.ok().setData(question);
		return Ajax.fail().setMsg("Not Found");
	}
	
	@At("/question/?/answer/add")
	@AdaptBy(type=JsonAdaptor.class)
	public AjaxReturn addAnswer(final int questionId, final @Param("..")Answer answer, @Attr("me")User user) {
		if (answer == null || Lang.length(answer.getContent()) < 5)
			return Ajax.fail().setMsg("Not OK");
		DBObject dbo = questionColl.findOne(new BasicDBObject("_id", questionId));
		if (dbo != null) {
			answer.setId(commons.seq("answer"));
			answer.setUser(user);
			answer.setCreatedAt(new Date());
			answer.setUpdatedAt(new Date());
			dao.runNoError(new Callback<DB>() {
				public void invoke(DB db) {
					dao.save(answer);
					Moo moo = Moo.NEW();
					moo.push("answers", new DBRef(db, "answer", answer.getId()));
					moo.set("updatedAt", new Date());
					dao.update(Question.class, new BasicDBObject("_id",questionId), moo);
				}
			});
			return Ajax.ok();
		}
		return Ajax.fail().setMsg("Not Found");
	}
	
	@At("/question/?/tag/add/?")
	public AjaxReturn addTag(int questionId,String tag, @Attr("me")User user) {
		//DBObject dbo = questionColl.findOne(new BasicDBObject("_id", questionId));
		//if (dbo != null) {
			BasicDBObject update = new BasicDBObject();
			update.append("$addToSet", new BasicDBObject("tags",tag));
			update.append("$set", new BasicDBObject("updatedAt",new Date()));
			questionColl.update(new BasicDBObject("_id", questionId), update);
			return Ajax.ok();
		//}
		//return Ajax.fail().setMsg("Not Found");
	}
	
	@At("/question/?/tag/remove/?")
	public AjaxReturn removeTag(int questionId,String tag, @Attr("me")User user) {
		//DBObject dbo = questionColl.findOne(new BasicDBObject("_id", questionId));
		//if (dbo != null) {
			BasicDBObject update = new BasicDBObject();
			update.append("$pull", new BasicDBObject("tags",tag));
			update.append("$set", new BasicDBObject("updatedAt",new Date()));
			questionColl.update(new BasicDBObject("_id", questionId), update);
			return Ajax.ok();
		//}
		//return Ajax.fail().setMsg("Not Found");
	}
}
