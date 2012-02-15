package org.nutz.ngqa.service;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.MCur;
import org.nutz.ngqa.api.QuestionManageService;
import org.nutz.ngqa.api.meta.Pager;
import org.nutz.ngqa.api.meta.QuestionQuery;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.User;

@IocBean(name="questionMS")
public class QuestionManageServiceImpl implements QuestionManageService {

	@Override
	public Pager<Question> query(QuestionQuery query) {
		if (query == null)
			query = new QuestionQuery();
		MCur cur = MCur.DESC("updatedAt");
		cur.skip(query.skip()).limit(query.limit());
		Pager<Question> pager = new Pager<Question>();
		pager.setCount(dao.count(Question.class, query.q));
		pager.setPage(query.page);
		pager.setPageSize(query.pageSize);
		pager.setData(dao.find(Question.class, query.q, cur));
		return pager;
	}
	
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;

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
