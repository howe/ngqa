package org.nutz.ngqa.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIdType;
import org.nutz.mongo.annotation.CoIndexes;

@Data
@EqualsAndHashCode(callSuper=false)
@Co("user")
@CoIndexes("!ids:+email,+openid")
public class User {

	@CoId(CoIdType.AUTO_INC)
	private int id;
	@CoField
	private String email;
	@CoField
	private String openid;
	@CoField
	private Date lastLoginDate;
	
}
