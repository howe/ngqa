package org.nutz.ngqa.module;

import java.util.Date;

import org.bson.types.ObjectId;
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
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.ngqa.Helpers;
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
import com.mongodb.DBRef;

@IocBean(create="init") //创建本对象时,执行init方法
@InjectName
@Filters({@By(type=AjaxCheckSession.class,args={"me"})})
public class CoreModule {
	
	/*question的title是必须的,其他都是可选*/
	@At("/ask")
	@AdaptBy(type=JsonAdaptor.class) //将输入流以Json格式读取,生成参数表
	public AjaxReturn createQuestion(final @Param("..")Question question, @Attr("me") User user) {
		if (question == null || Lang.length(question.getTitle()) < 5 || Lang.length(question.getTitle()) > 100)
			return Ajax.fail().setMsg("ERROR(title.length): 100 >= title >= 5 , not match!");
		if (question.getTitle().contains("<") || question.getTitle().contains("/")) //还需要过滤敏感词
			return Ajax.fail().setMsg("ERROR(title.context): Bad Title!!");
		if (dao.count(Question.class, Moo.NEW("title", question.getTitle())) != 0)
			return Ajax.fail().setMsg("ERROR(title.duplicate): Duplicate Title!!");
		question.setUser(user);
		question.setCreatedAt(new Date());
		question.setUpdatedAt(new Date());
		if (question.getTags() == null)
			question.setTags(new String[0]);
		else
			question.setTags(Helpers.cleanTags(question.getTags()));
		question.setAnswers(new Answer[0]);
		dao.runNoError(new Callback<DB>() { //以安全方式执行,其实就是执行完毕后,执行getError来确保顺利完成
			public void invoke(DB arg0) {
				dao.save(question);
			}
		});
		return Ajax.ok().setData(question);
	}
	
	/*获取具体的question*/
	@At("/question/?") //最后面一个问号,代表一个路径参数,将设置为questionId的值
	@Filters() //查询无需任何权限
	@Ok("smart:/question/one")
	public Object fetch(String questionId) {
		Question question = dao.findById(Question.class, questionId);
		if (question == null)
			return new HttpStatusView(404);
		return question;
	}
	
	/**通用查询入口,复杂查询都走这个入口*/
	@At("/question/query")
	public Object query(@Param("..")QuestionQuery query) { //@Param("..")的意思是,参数abc对应这个类的abc属性,从而构建一个完整的对象
		return questionMS.query(query);
	}
	
	@At("/question/?/answer/add") //问号放在中间也是可以的,数量不限
	@AdaptBy(type=JsonAdaptor.class)
	public AjaxReturn addAnswer(final String questionId, final @Param("..")Answer answer, @Attr("me")User user) { //@Attr的意思是取req.getAttr或者session.getAttr
		if (answer == null || Lang.length(answer.getContent()) < 5)
			return Ajax.fail().setMsg("Not OK");
		Question question = dao.findById(Question.class, questionId);
		if (question == null)
			Ajax.fail().setMsg("Not Found");
		
		answer.setUser(user);
		answer.setCreatedAt(new Date());
		answer.setUpdatedAt(new Date());
		dao.runNoError(new Callback<DB>() {
			public void invoke(DB db) {
				dao.save(answer);
				Moo moo = Moo.NEW();
				moo.push("answers", new DBRef(db, "answer", new ObjectId(answer.getId())));
				dao.update(Question.class, Moo.NEW("id", questionId), moo);
			}
		});
		commons.fresh(Question.class, questionId);
		return Ajax.ok();
	}
	
	/**为一个问题设置一个标签*/
	@At("/question/?/tag/add/?") //多个问好的情况,很方便,对吧?
	public AjaxReturn addTag(String questionId,String tag, @Attr("me")User user) { 
		if (tag == null || Strings.isBlank(tag) || tag.trim().length() < 3 || tag.trim().length() > 12)
			return Ajax.fail().setMsg("Not OK");
		tag = tag.trim().intern();
		if (!Helpers.checkTag(tag))
			return Ajax.fail().setMsg("Not allow!");
		BasicDBObject update = new BasicDBObject();
		update.append("$addToSet", new BasicDBObject("tags",tag));
		questionColl.update(new BasicDBObject("_id", questionId), update);
		commons.fresh(Question.class, questionId);
		return Ajax.ok();
	}
	
	/**为一个问题移除一个标签*/
	@At("/question/?/tag/remove/?")
	public AjaxReturn removeTag(String questionId,String tag, @Attr("me")User user) {
		if (!Helpers.checkTag(tag))
			return Ajax.fail().setMsg("Not allow!");
		BasicDBObject update = new BasicDBObject();
		update.append("$pull", new BasicDBObject("tags",tag));
		questionColl.update(new BasicDBObject("_id", new ObjectId(questionId)), update);
		commons.fresh(Question.class, questionId);
		return Ajax.ok();
	}
	
	/**监视一个问题,问题被修改的时候(添加删除答案等),发出提醒*/
	@At("/question/?/watch")
	public void watch(String questionId, @Attr("me") User me) {
		dao.update(Question.class, new BasicDBObject("_id", new ObjectId(questionId)), new BasicDBObject("$addToSet", new BasicDBObject("watchers", new DBRef(null, "user", new ObjectId(me.getId())))));
	}
	
	/**不再监视一个问题*/
	@At("/question/?/unwatch")
	public void unwatch(String questionId, @Attr("me") User me) {
		dao.update(Question.class, new BasicDBObject("_id", new ObjectId(questionId)), new BasicDBObject("$pop", new BasicDBObject("watchers", new DBRef(null, "user", new ObjectId(me.getId())))));
	}
	
	/**更新问题的某些属性*/
	@At("/question/?/update")
	public Object update(String questionId, @Param("..")Question question, @Attr("me")User me) {
		Question q = dao.findById(Question.class, questionId);
		if (q == null)
			return Ajax.fail().setMsg("Question not found");
		if (!me.getId().equals(q.getUser().getId()))
			return Ajax.fail().setMsg("You don't own this question");
		if (question == null)
			return Ajax.fail().setMsg("No data");
		if (Lang.length(question.getTitle()) >= 5 || Lang.length(question.getTitle()) <= 100)
			dao.update(Question.class, new BasicDBObject("_id", new ObjectId(questionId)), Moo.SET("title", question.getTitle()));
		if (Lang.length(question.getContent()) >= 5 || Lang.length(question.getContent()) <= 100)
			dao.update(Question.class, new BasicDBObject("_id", new ObjectId(questionId)), Moo.SET("content", question.getContent()));
		return Ajax.ok();
	}
	
	/**统计全体Tag的数量*/
	@At("/tags")
	@Ok("smart:/tag")
	@Filters() //查询无需任何权限
	public Object tags() {
		final Object[] objs = new Object[1];
		dao.run(new Callback<DB>() {
			public void invoke(DB db) {
				objs[0] = db.eval("tags()");
			}
		});
		return objs[0];
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
