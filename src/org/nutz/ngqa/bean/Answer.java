package org.nutz.ngqa.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.mongo.annotation.Co;
import org.nutz.mongo.annotation.CoField;
import org.nutz.mongo.annotation.CoId;

@Data
@EqualsAndHashCode(callSuper=false)
@Co("answer")
public class Answer implements Freshable{

	@CoId
	private String id;
	@CoField(ref=true)
	private User user;
	@CoField
	private String content;
	@CoField
	private String format;
	@CoField
	private Date createdAt;
	@CoField
	private Date updatedAt;

}
