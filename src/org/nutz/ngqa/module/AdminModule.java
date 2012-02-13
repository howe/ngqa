package org.nutz.ngqa.module;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mongo.MongoDao;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.mvc.Auth;

import com.mongodb.BasicDBObject;

@IocBean
@InjectName
@Ok("ajax")
@Fail("ajax")
public class AdminModule {
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;

	@Filters({@By(type=ActionFilter.class, args={"ioc:authFilter"})})
	@Auth("role.refresh")
	public void refreshRoleCache(){
		Map<String, Role> roles = new HashMap<String, Role>();
		for(Role role : dao.find(Role.class, new BasicDBObject(), null)) {
			roles.put(role.getName(), role);
		}
		Mvcs.getServletContext().setAttribute("sys.roles", roles);
	}
}
