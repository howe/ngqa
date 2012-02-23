package org.nutz.ngqa.module;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.ngqa.api.QuestionManageService;
import org.nutz.ngqa.api.meta.QuestionQuery;
import org.nutz.ngqa.bean.User;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxCheckSession;

import com.mongodb.BasicDBObject;

/**
 * 封装Core模块的调用,主要就是封装查询请求
 */
@IocBean(create="init")
@InjectName
@Filters()
public class QuestionQueryModule {
	
	/*只支持简单分页*/
	@At({"/question/query/list/?","/question/query/list"})
	@Ok("smart")
	public Object query(@Param("page")int page, @Param("pageSize") int pageSize) {
		return questionMS.query(QuestionQuery.Page(page, pageSize));
	}

	/*支持短URL访问特定的tag*/
	@At({"/tag/?","/question/query/hasTag/?","/question/query/hasTag"})
	@Ok("smart:/tag/one.jsp")
	public Object hasTag(@Param("tag")String tag, @Param("page")int page, @Param("pageSize") int pageSize) {
		if (Strings.isBlank(tag)) {
			return noTag(page, pageSize); //无tag的Question
		} 
		QuestionQuery query = QuestionQuery.Page(page, pageSize);
		query.q.append("tags", tag); //tags是数组,支持直接查值
		return questionMS.query(query);
	}
	
	@At({"/question/query/noTag/?","/question/query/noTag"})
	public Object noTag(@Param("page")int page, @Param("pageSize") int pageSize) {
		QuestionQuery query = QuestionQuery.Page(page, pageSize);
		query.q.append("tags", new BasicDBObject("$size", 0)); //无tag的Question
		return questionMS.query(query);
	}
	
	@At({"/question/query/noauswer/?", "/question/query/noauswer"})
	public Object noAuswer(@Param("page")int page, @Param("pageSize") int pageSize) {
		QuestionQuery query = QuestionQuery.Page(page, pageSize);
		query.q.append("answers", new BasicDBObject("$size", 0));
		return questionMS.query(query);
	}
	
	@Filters(@By(type=AjaxCheckSession.class, args={"me"}))
	@At({"/question/query/me/ask"}) //未实现
	public Object askByMe(@Attr("me") User me) {
		return Ajax.fail();
	}
	
	@Filters(@By(type=AjaxCheckSession.class, args={"me"}))
	@At({"/question/query/me/answer"}) //未实现
	public Object answerByMe() {
		return Ajax.fail();
	}
	
	@Filters(@By(type=AjaxCheckSession.class, args={"me"}))
	@At({"/question/query/random"}) //未实现
	public Object random(@Attr("me") User me) {
		return Ajax.fail();
	}
	
	
	
	@Inject("refer:questionMS")
	private QuestionManageService questionMS;
	
	public void init() {
	}
}
