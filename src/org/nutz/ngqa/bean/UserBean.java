package org.nutz.ngqa.bean;

import java.util.Date;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserBean extends MongodbBean {

	private String email;
	private String openid;
	private Map<String, String> notifyConfig;
	private Date lastLoginDate;
}
