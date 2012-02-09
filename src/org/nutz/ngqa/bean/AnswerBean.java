package org.nutz.ngqa.bean;

import java.util.Date;

import com.mongodb.BasicDBObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnswerBean extends MongodbBean {

	private int id;

	private String userId;

	private String content;

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
