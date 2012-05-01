package org.nutz.ngqa.module;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.Moo;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.ngqa.bean.User;
import org.nutz.web.ajax.Ajax;

import com.mongodb.BasicDBObject;

@IocBean
@InjectName
@Ok("smart")
@Fail("ajax")
public class UserCenterModule {

	@At("/me")
	@Ok("smart:/me")
	public Object me(@Attr("me") User me) {
		if	(me != null) {
			Map<String, Object> map = Lang.obj2map(me);
			map.put("email", me.getEmail());
			return map;
		}
		return Ajax.expired();
	}
	
	@At("/me/update")
	@AdaptBy(type=JsonAdaptor.class)
	public Object update(@Param("..")User user, @Attr("me") User me) {
		if (me == null)
			return Ajax.expired();
		if ("anonymous".equals(me.getProvider()))
			return Ajax.fail().setMsg("anonymous can't change !!");
		if (user == null)
			return Ajax.fail().setMsg("Not data");
		if (!Strings.isBlank(user.getNickName())) {
			if (Strings.isBlank(user.getNickName()) || user.getNickName().equals(me.getNickName()))
				;
			else if (0 != dao.count(User.class, new BasicDBObject("nickName", user.getNickName())))
				return Ajax.fail().setMsg("Dup nickName");
			else
				dao.updateById(User.class, me.getId(), Moo.SET("nickName", user.getNickName()));
		}
		if (!Strings.isBlank(user.getEmail()) && Strings.isEmail(user.getEmail()))
			dao.updateById(User.class, me.getId(), Moo.SET("email", user.getEmail()));
		return Ajax.ok();
	}
	
	@At
	public void XXX(String abc, String ccc) {
		System.out.println("abc = " + abc);
		System.out.println("ccc = " + ccc);
	}
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
}
