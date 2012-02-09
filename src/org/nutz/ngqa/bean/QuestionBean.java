package org.nutz.ngqa.bean;

import java.util.Date;

import com.mongodb.BasicDBObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionBean extends MongodbBean {

	private int id;

	private String title;

	private String content;

	private String tags;

	private String format;

	private Date createdAt;

	private Date updatedAt;

	public BasicDBObject asRef() {
		BasicDBObject me = new BasicDBObject();
		me.append("$ref", "vuser");
		me.append("$id", get_id());
		return me;
	}
}
