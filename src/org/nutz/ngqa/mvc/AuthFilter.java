package org.nutz.ngqa.mvc;

import java.lang.reflect.Method;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.mvc.view.ViewWrapper;
import org.nutz.ngqa.api.AuthService;
import org.nutz.ngqa.api.meta.AuthContext;
import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.bean.User;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxView;

@IocBean
public class AuthFilter implements ActionFilter {
	
	@Inject
	private AuthService authService;
	
	private static final Log log = Logs.get();
	
	@SuppressWarnings("unchecked")
	public View match(ActionContext actionContext) {
		Method method = actionContext.getMethod();
		Auth auth = method.getAnnotation(Auth.class);
		if (auth == null)
			return null;
		User me = (User) actionContext.getRequest().getSession().getAttribute("me");
		if (me == null)
			return new ViewWrapper(new AjaxView(), Ajax.fail().setData("Not login yet!"));
		if ("anonymous".equals(me.getProvider()))
			return new ViewWrapper(new AjaxView(), Ajax.fail().setData("anonymous can't do this!"));
		if ("root".equals(me.getProvider())) {
			if (log.isInfoEnabled())
				log.info("Access as root , pass");
			return null;
		}
		Map<String, Role> roles = (Map<String, Role>) actionContext.getServletContext().getAttribute("sys.roles");
		if (roles == null) {
			if (log.isWarnEnabled())
				log.warn("Role config not found?!");
			return new HttpStatusView(500); //Deny all req
		}
		if (authService.isAuth(new AuthContext(me, auth, roles)))
			return null;
		return new HttpStatusView(403); //Not Ok
	}

}
