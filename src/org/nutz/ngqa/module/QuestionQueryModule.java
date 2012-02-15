package org.nutz.ngqa.module;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Param;
import org.nutz.ngqa.api.QuestionManageService;
import org.nutz.ngqa.api.meta.QuestionQuery;

import com.mongodb.BasicDBObject;

/**
 * 封装Core模块的调用,主要就是封装查询请求
 */
@IocBean(create="init")
@InjectName
@Filters()
@At("/question/query")
public class QuestionQueryModule {
	
	/*只支持简单分页*/
	@At({"/list/?","/list"})
	public Object query(@Param("page")int page, @Param("pageSize") int pageSize) {
		return questionMS.query(QuestionQuery.Page(page, pageSize));
	}

	@At({"/hasTag/?","/hasTag"})
	public Object hasTag(@Param("tag")String tag, @Param("page")int page, @Param("pageSize") int pageSize) {
		QuestionQuery query = QuestionQuery.Page(page, pageSize);
		if (tag == null) {
			query.q.append("tags", new BasicDBObject("$size", 0)); //无tag的Question
		} else {
			query.q.append("tags", tag); //tags是数组,支持直接查值
		}
		return questionMS.query(query);
	}
	
	@At({"/noauswer/?", "/noauswer"})
	public Object noAuswer(@Param("page")int page, @Param("pageSize") int pageSize) {
		QuestionQuery query = QuestionQuery.Page(page, pageSize);
		query.q.append("answers", new BasicDBObject("$size", 0));
		return questionMS.query(query);
	}
	
	
	
	@Inject("refer:questionMS")
	private QuestionManageService questionMS;
	
	public void init() {
	}
}
