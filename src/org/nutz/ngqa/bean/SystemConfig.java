package org.nutz.ngqa.bean;


import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIdType;

@Co
public class SystemConfig {

	@CoId(CoIdType.AUTO_INC)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
