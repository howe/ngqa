package org.nutz.ngqa.api.meta;

import com.mongodb.BasicDBObject;

public class QuestionQuery {

	private BasicDBObject p = new BasicDBObject();
	
	public int skip;
	public int limit;
	
	public static QuestionQuery id(int questionId){
		QuestionQuery query = new QuestionQuery();
		query.p.put("_id", questionId);
		query.page(1, 1);
		return query;
	}
	
	public QuestionQuery page(int page, int pageSize) {
		skip = (page - 1) * pageSize;
		limit = pageSize;
		return this;
	}
}
