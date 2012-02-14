package org.nutz.ngqa.bean;

import java.util.Date;

import lombok.Data;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;
import org.nutz.mongo.annotation.CoIndexes;

@Data
@Co
@CoIndexes("!:+name")
public class App  implements Freshable{

	@CoId
	private String id;
	@CoField
	private String name;
	@CoField
	private String key;
	@CoField
	private boolean active;
	@CoField
	private Date updatedAt;
}
