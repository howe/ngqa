package org.nutz.ngqa.bean;

import lombok.Data;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIndexes;

@Data
@Co
@CoIndexes("!:+name")
public class Role {

	@CoId
	private String id;
	
	@CoField
	private String name;
}
