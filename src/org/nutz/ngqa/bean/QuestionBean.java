package org.nutz.ngqa.bean;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionBean extends MongodbBean {

	private UserBean user;
	private String title;
	private String content;
	private String[] tags;
	private String format;
	private Date createdAt;
	private Date updatedAt;
	private List<AnswerBean> answers;
}
