package org.nutz.ngqa.bean;

import java.util.Date;

import org.nutz.json.JsonField;
import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIndexes;

@Co
@CoIndexes("!:+name")
public class App  implements Freshable{

	@CoId
	private String id;
	@CoField
	private String name;
	@CoField
	@JsonField(ignore=true) //不允许直接传出去
	private transient String key;
	@CoField
	private boolean active;
	@CoField
	private Date updatedAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
