package org.nutz.ngqa.bean;

import java.util.Date;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.mongodb.BasicDBObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserBean extends MongodbBean {

	private String email;
	private String openid;
	private Map<String, String> notifyConfig;
	private Date lastLoginDate;

	
	public BasicDBObject asRef() {
		BasicDBObject me = new BasicDBObject();
		me.append("$ref", "vuser");
		me.append("$id", get_id());
		return me;
	}
}
