package org.nutz.ngqa.api.meta;

import com.mongodb.BasicDBObject;

public class QuestionQuery {

	private BasicDBObject p = new BasicDBObject();
	
	
	
	public static QuestionQuery id(int questionId){
		QuestionQuery query = new QuestionQuery();
		query.p.put("_id", questionId);
		return query;
	}
}
