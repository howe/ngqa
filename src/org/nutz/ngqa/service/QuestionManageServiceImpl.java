package org.nutz.ngqa.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mongo.MongoDao;
import org.nutz.ngqa.api.QuestionManageService;
import org.nutz.ngqa.api.meta.QuestionQuery;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.User;

import com.mongodb.DBCollection;

@IocBean(name="questionMS")
public class QuestionManageServiceImpl implements QuestionManageService {

	@Override
	public List<Question> query(QuestionQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Inject("java:$commons.coll('question')")
	private DBCollection questionColl;
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
	
	@Inject
	private CommonMongoService commons;

	@Override
	public Question create(Question question) {
		throw Lang.noImplement();
	}

	@Override
	public void markDeleted(Question question) {
		throw Lang.noImplement();
	}


	@Override
	public Question addAnswer(Question question, Answer answer) {
		throw Lang.noImplement();
	}

	@Override
	public void watch(Question question, User user) {
		throw Lang.noImplement();
	}

	@Override
	public void unwatch(Question question, User user) {
		throw Lang.noImplement();
	}
}
