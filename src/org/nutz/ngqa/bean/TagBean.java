package org.nutz.ngqa.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.mongodb.BasicDBObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagBean extends MongodbBean {

	private int id;

	private String name;

	private int questionId;

	private Date createdAt;

	private Date updatedAt;

	public BasicDBObject asRef() {
		BasicDBObject me = new BasicDBObject();
		me.append("$ref", "vuser");
		me.append("$id", get_id());
		return me;
	}
}
