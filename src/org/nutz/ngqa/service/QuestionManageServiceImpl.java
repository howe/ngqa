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

@IocBean(name="questionMS") //名字有点长,所以没用默认的名字,自己起一个
public class QuestionManageServiceImpl implements QuestionManageService {

	/**核心查询方法,通用查询*/
	public Pager<Question> query(QuestionQuery query) {
		if (query == null)
			query = new QuestionQuery();
		MCur cur = MCur.DESC("updatedAt");
		Pager<Question> pager = new Pager<Question>();
		pager.setCount(dao.count(Question.class, query.q));
		pager.setPage(query.page);
		pager.setPageSize(query.pageSize);
		if (query.skip() > 0)
			cur.skip(query.skip());
		if (query.limit() > 0)	
			cur.limit(query.limit());
		pager.setData(dao.find(Question.class, query.q, cur));
		return pager;
	}
	
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;

	@Override
	public Question create(Question question) {
		throw Lang.noImplement(); //未实现的方法,这语句最方便了
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
