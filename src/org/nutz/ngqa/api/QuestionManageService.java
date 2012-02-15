package org.nutz.ngqa.api;

import org.nutz.ngqa.api.meta.Pager;
import org.nutz.ngqa.api.meta.QuestionQuery;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.User;

public interface QuestionManageService {

	Question create(Question question);
	
	void markDeleted(Question question);
	
	Pager<Question> query(QuestionQuery query);
	
	Question addAnswer(Question question, Answer answer);
	
	void watch(Question question, User user);
	
	void unwatch(Question question, User user);
}
