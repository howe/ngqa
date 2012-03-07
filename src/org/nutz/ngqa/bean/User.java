package org.nutz.ngqa.bean;

import java.util.Date;

import org.nutz.json.JsonField;
import org.nutz.lang.Lang;
import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIndexes;

@Co("user")
@CoIndexes("!ids:+provider,+validatedId")
public class User {

	@CoId
	private String id;
	@CoField
	private String nickName;
	@CoField
	private String validatedId;
	@CoField
	@JsonField(getBy="emailMD5")
	private String email;
	@CoField
	private String provider;
	@CoField
	private String createAt;
	@CoField
	private Date lastLoginDate;
	
	@CoField(ref=true, lazy=true)
	@JsonField(ignore=true)
	private Role[] roles;
	
	@CoField(ref=true, lazy=true)
	@JsonField(ignore=true)
	private App[] authedApps;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValidatedId() {
		return validatedId;
	}

	public void setValidatedId(String validatedId) {
		this.validatedId = validatedId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Role[] getRoles() {
		return roles;
	}

	public void setRoles(Role[] roles) {
		this.roles = roles;
	}

	public App[] getAuthedApps() {
		return authedApps;
	}

	public void setAuthedApps(App[] authedApps) {
		this.authedApps = authedApps;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	
	public String emailMD5() {
		return Lang.md5(email);
	}
}
