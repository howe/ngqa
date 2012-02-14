package org.nutz.ngqa.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.json.JsonField;
import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIndexes;

@Data
@EqualsAndHashCode(callSuper=false)
@Co("user")
@CoIndexes("!ids:+provider,+validatedId")
public class User {

	@CoId
	private String id;
	@CoField
	private String validatedId;
	@CoField
	private String email;
	@CoField
	private String provider;
	@CoField
	private Date lastLoginDate;
	
	@CoField(ref=true, lazy=true)
	@JsonField(ignore=true)
	private Role[] roles;
	
	@CoField(ref=true, lazy=true)
	@JsonField(ignore=true)
	private App[] authedApps;
}
