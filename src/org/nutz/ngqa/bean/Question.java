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
@EqualsAndHashCode(callSuper = false)
@Co("question")
@CoIndexes("!:+title")
public class Question implements Freshable {

	@CoId(CoIdType.AUTO_INC)
	private Integer id;
	@CoField(ref=true)
	private User user;
	@CoField
	private String title;
	@CoField
	private String content;
	@CoField
	private String[] tags;
	@CoField
	private String format;
	@CoField
	private Date createdAt;
	@CoField
	private Date updatedAt;
	@CoField(ref=true)
	private Answer[] answers;
}
