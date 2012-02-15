package org.nutz.ngqa.module;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
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

@IocBean
@InjectName
@Ok("ajax")
@Fail("ajax")
public class UserCenterModule {

	@At("/me")
	public User me(@Attr("me") User me) {
		return me;
	}
	
	@At("/me/update")
	@AdaptBy(type=JsonAdaptor.class)
	public void update(@Param("..")User user, @Attr("me") User me) {
		if (user == null)
			return;
		if (!Strings.isBlank(user.getEmail()) && Strings.isEmail(user.getEmail()))
			dao.updateById(User.class, me.getId(), Moo.SET("email", user.getEmail()));
	}
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
}
