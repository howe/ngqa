package org.nutz.ngqa.bean;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIndexes;

import lombok.Data;

@Data
@Co
@CoIndexes("!:+name")
public class Role {

	@CoId
	private String id;
	
	@CoField
	private String name;
}
