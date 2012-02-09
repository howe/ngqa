package org.nutz.ngqa.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.ngqa.bean.AnswerBean;
import org.nutz.ngqa.bean.QuestionBean;
import org.nutz.ngqa.bean.UserBean;
import org.nutz.ngqa.mvc.Ajax;
import org.nutz.ngqa.mvc.AjaxReturn;
import org.nutz.ngqa.mvc.Mongos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@IocBean(create="init")
@InjectName
@Filters({@By(type=CheckSession.class,args={"me", "/index.jsp"})})
public class CoreModule {
	
	@Inject("java:$mongos.coll('question')")
	private DBCollection questionColl;
	
	@Inject("java:$mongos.coll('answer')")
	private DBCollection answerColl;
	
	@Inject
	private Mongos mongos;

	@At("/question/create")
	@AdaptBy(type=JsonAdaptor.class)
	public AjaxReturn createQuestion(@Param("..")QuestionBean question, @Attr("me") UserBean user) {
		if (question == null)
			return Ajax.fail().setMsg("NULL");
		question.set_id(mongos.autoInc("question"));
		question.setUser(user);
		question.setCreatedAt(new Date());
		question.setUpdatedAt(new Date());
		questionColl.insert(question);
		return Ajax.ok().setData(question);
	}
	
	@At({"/question/list/?","/question/list"})
	public AjaxReturn query(int page) {
		BasicDBObject ref = new BasicDBObject();
		if (page < 1)
			page = 1;
		int skip = (page - 1) * 10;
		DBCursor cursor = questionColl.find(ref);
		cursor.limit(10);
		cursor.sort(new BasicDBObject("updatedAt", -1));
		if (skip > 0)
			cursor.skip(skip);
		List<QuestionBean> questions = new ArrayList<QuestionBean>();
		for (DBObject dbObject : cursor)
			questions.add(mongos.map2(dbObject, QuestionBean.class));
		return Ajax.ok().setData(questions);
	}
	
	@At("/question/fetch/?")
	public AjaxReturn fetch(int questionId) {
		DBObject dbo = questionColl.findOne(new BasicDBObject("_id", questionId));
		if (dbo != null)
			return Ajax.ok().setData(mongos.map2(dbo, QuestionBean.class));
		return Ajax.fail().setMsg("Not Found");
	}
	
	@At("/answer/add/?")
	public AjaxReturn addAnswer(int questionId, @Param("..")AnswerBean answer, @Attr("me")UserBean user) {
		DBObject dbo = questionColl.findOne(new BasicDBObject("_id", questionId));
		if (dbo != null) {
			answer.set_id(mongos.autoInc("answer"));
			answer.setUser(user);
			answer.setCreatedAt(new Date());
			answer.setUpdatedAt(new Date());
			answerColl.insert(answer);
			BasicDBObject update = new BasicDBObject();
			update.append("$push", new BasicDBObject("answers",answer));
			update.append("$set", new BasicDBObject("updatedAt",new Date()));
			questionColl.update(new BasicDBObject("_id", dbo.get("_id")), update);
			return Ajax.ok();
		}
		return Ajax.fail().setMsg("Not Found");
	}
}
