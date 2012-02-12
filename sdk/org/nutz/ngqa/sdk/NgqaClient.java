package org.nutz.ngqa.sdk;

import java.util.List;

import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.Question;

public interface NgqaClient {

	String login(String openId);
	void   loginCallback(String code);
	void   anonymousLogin();
	void   logout();
	
	Question createQuestion(Question question);
	void     addAnswer(int questionId, Answer answer);
	void     addTag(int questionId, String tag);
	void     removeTag(int questionId, String tag);
	
	List<Question>     listQuestions(int page, int pageSize);
	List<Question>     queryQuestions();//TODO
}
