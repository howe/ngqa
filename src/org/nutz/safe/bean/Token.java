package org.nutz.safe.bean;

import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("safe_token")
public class Token {

	@Name
	private String token;
	private int ttype;
	private long expireTime;
	private String extCheck;
	private String extData;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getTtype() {
		return ttype;
	}
	public void setTtype(int ttype) {
		this.ttype = ttype;
	}
	public long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	public String getExtCheck() {
		return extCheck;
	}
	public void setExtCheck(String extCheck) {
		this.extCheck = extCheck;
	}
	public String getExtData() {
		return extData;
	}
	public void setExtData(String extData) {
		this.extData = extData;
	}
	
	
}
