package org.nutz.ngqa.module;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.MCur;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.ngqa.bean.Question;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxReturn;

/**
 * 封装Core模块的调用,主要就是封装查询请求
 */
@IocBean(create="init")
@InjectName
@Filters({@By(type=CheckSession.class,args={"me", "/index.jsp"})})
@At("/addon")
public class AddonModule {
	

	
	/*暂时只支持简单分页,每页10条记录*/
	@At({"/question/list/?","/question/list"})
	public AjaxReturn query(int page, @Param("pageSize") int pageSize) {
		if (page < 1)
			page = 1;
		if (pageSize < 1)
			pageSize = 10;
		else if (pageSize > 100)
			pageSize = 100;
		int skip = (page - 1) * pageSize;
		MCur cur = MCur.DESC("updatedAt").limit(10).skip(skip);
		return Ajax.ok().setData(dao.find(Question.class, null, cur));
	}

	
	
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
	
	public void init() {
	}
}
