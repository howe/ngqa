package org.nutz.ngqa.api.meta;

import com.mongodb.BasicDBObject;

public class QuestionQuery {

	public BasicDBObject q = new BasicDBObject();
	
	public QuestionQuery() {
	}
	
	public int page = 0;
	public int pageSize = 10;
	
	public int skip() {
		return (page - 1) * pageSize;
	}
	
	public int limit() {
		return pageSize;
	}
	
	public QuestionQuery page(int page, int pageSize) {
		if (page < 1)
			this.page = 1;
		if (pageSize < 10)
			this.pageSize = 10;
		else if (pageSize > 100)
			this.pageSize = 100;
		return this;
	}
	
	public static QuestionQuery Page(int page, int pageSize) {
		QuestionQuery query = new QuestionQuery();
		query.page(page, pageSize);
		return query;
	}
	
	public static QuestionQuery Id(String id) {
		QuestionQuery query = new QuestionQuery();
		query.page = 1;
		query.pageSize = 1;
		return query;
	}
}
