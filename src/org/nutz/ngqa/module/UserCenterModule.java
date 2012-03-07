package org.nutz.ngqa.module;

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
	public User me(@Attr("me") User me) {
		if	(me != null)
			return me;
		throw Lang.makeThrow("Not login yet!");
	}
	
	@At("/me/update")
	@AdaptBy(type=JsonAdaptor.class)
	public Object update(@Param("..")User user, @Attr("me") User me) {
		if ("anonymous".equals(me.getProvider()))
			return Ajax.fail().setMsg("anonymous can't change !!");
		if (user == null)
			return Ajax.fail().setMsg("Not data");
		if (!Strings.isBlank(user.getNickName())) {
			if (0 != dao.count(User.class, new BasicDBObject("nickName", user.getNickName())))
				return Ajax.fail().setMsg("Dup nickName");
			dao.updateById(User.class, me.getId(), Moo.SET("nickName", user.getNickName()));
		}
		if (!Strings.isBlank(user.getEmail()) && Strings.isEmail(user.getEmail()))
			dao.updateById(User.class, me.getId(), Moo.SET("email", user.getEmail()));
		return Ajax.ok();
	}
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
}
