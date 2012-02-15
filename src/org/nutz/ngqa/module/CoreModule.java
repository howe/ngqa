package org.nutz.ngqa.module;

import java.util.Date;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.Moo;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Param;
import org.nutz.ngqa.api.QuestionManageService;
import org.nutz.ngqa.api.meta.QuestionQuery;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.service.CommonMongoService;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxCheckSession;
import org.nutz.web.ajax.AjaxReturn;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

@IocBean(create="init")
@InjectName
@Filters({@By(type=AjaxCheckSession.class,args={"me"})})
public class CoreModule {
	
	/*question的title是必须的,其他都是可选*/
	@At("/ask")
	@AdaptBy(type=JsonAdaptor.class)
	public AjaxReturn createQuestion(final @Param("..")Question question, @Attr("me") User user) {
		if (question == null || Lang.length(question.getTitle()) < 5 || Lang.length(question.getTitle()) > 100)
			return Ajax.fail().setMsg("Not OK");
		question.setId(commons.seq("question"));
		question.setUser(user);
		question.setCreatedAt(new Date());
		question.setUpdatedAt(new Date());
		question.setTags(new String[0]);
		question.setAnswers(new Answer[0]);
		dao.runNoError(new Callback<DB>() {
			public void invoke(DB arg0) {
				dao.save(question);
			}
		});
		return Ajax.ok().setData(question);
	}
	
	/*获取具体的question*/
	@At("/question/?")
	public AjaxReturn fetch(int questionId) {
		Question question = dao.findOne(Question.class, new BasicDBObject("_id", questionId));
		if (question != null)
			return Ajax.ok().setData(question);
		return Ajax.fail().setMsg("Not Found");
	}
	
	@At("/question/query")
	public Object query(QuestionQuery query) {
		return questionMS.query(query);
	}
	
	@At("/question/?/answer/add")
	@AdaptBy(type=JsonAdaptor.class)
	public AjaxReturn addAnswer(final int questionId, final @Param("..")Answer answer, @Attr("me")User user) {
		if (answer == null || Lang.length(answer.getContent()) < 5)
			return Ajax.fail().setMsg("Not OK");
		DBObject dbo = questionColl.findOne(new BasicDBObject("_id", questionId));
		if (dbo != null) {
			answer.setUser(user);
			answer.setCreatedAt(new Date());
			answer.setUpdatedAt(new Date());
			dao.runNoError(new Callback<DB>() {
				public void invoke(DB db) {
					dao.save(answer);
					Moo moo = Moo.NEW();
					moo.push("answers", new DBRef(db, "answer", answer.getId()));
					dao.update(Question.class, new BasicDBObject("_id",questionId), moo);
				}
			});
			commons.fresh(Question.class, questionId);
			return Ajax.ok();
		}
		return Ajax.fail().setMsg("Not Found");
	}
	
	@At("/question/?/tag/add/?")
	public AjaxReturn addTag(int questionId,String tag, @Attr("me")User user) {
		if (tag == null || Strings.isBlank(tag) || tag.trim().length() < 3 || tag.trim().length() > 12)
			return Ajax.fail().setMsg("Not OK");
		tag = tag.trim().intern();
		BasicDBObject update = new BasicDBObject();
		update.append("$addToSet", new BasicDBObject("tags",tag));
		questionColl.update(new BasicDBObject("_id", questionId), update);
		commons.fresh(Question.class, questionId);
		return Ajax.ok();
	}
	
	@At("/question/?/tag/remove/?")
	public AjaxReturn removeTag(int questionId,String tag, @Attr("me")User user) {
		BasicDBObject update = new BasicDBObject();
		update.append("$pull", new BasicDBObject("tags",tag));
		questionColl.update(new BasicDBObject("_id", questionId), update);
		commons.fresh(Question.class, questionId);
		return Ajax.ok();
	}
	
	@At("/question/?/watch")
	public void watch(int questionId, @Attr("me") User me) {
		dao.update(Question.class, new BasicDBObject("_id", questionId), new BasicDBObject("$addToSet", new BasicDBObject("watchers", new DBRef(null, "user", me.getId()))));
	}
	
	@At("/question/?/unwatch")
	public void unwatch(int questionId, @Attr("me") User me) {
		dao.update(Question.class, new BasicDBObject("_id", questionId), new BasicDBObject("$pop", new BasicDBObject("watchers", new DBRef(null, "user", me.getId()))));
	}
	
	@Inject("java:$commons.coll('question')")
	private DBCollection questionColl;
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
	
	@Inject
	private QuestionManageService questionMS;
	
	@Inject
	private CommonMongoService commons;
	
	public void init() {}
}
