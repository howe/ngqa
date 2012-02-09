package org.nutz.ngqa.bean;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AnswerBean extends MongodbBean {

	private UserBean user;
	private String content;
	private String format;
	private Date createdAt;
	private Date updatedAt;

}
