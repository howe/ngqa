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

/**权限过滤器,用于检测有@Auth注解的入口方法*/
@IocBean
public class AuthFilter implements ActionFilter {
	
	@Inject
	private AuthService authService;
	
	private static final Log log = Logs.get();
	
	@SuppressWarnings("unchecked")
	public View match(ActionContext actionContext) {
		Method method = actionContext.getMethod();
		Auth auth = method.getAnnotation(Auth.class);
		if (auth == null) //没有@Auth,恩,无需过滤
			return null; //返回null,就是继续下一个ActionFilter
		User me = (User) actionContext.getRequest().getSession().getAttribute("me");
		if (me == null)
			return new ViewWrapper(new AjaxView(), Ajax.fail().setData("Not login yet!"));
		if ("anonymous".equals(me.getProvider())) //默认用户? 啥授权都不允许
			return new ViewWrapper(new AjaxView(), Ajax.fail().setData("anonymous can't do this!"));
		if ("root".equals(me.getProvider())) {
			if (log.isInfoEnabled())
				log.info("Access as root , pass");
			return null;
		}
		//具体的权限数据,是EnhanceUrlMapping类在启动过程中,通过入口方法来获取配置信息的
		Map<String, Role> roles = (Map<String, Role>) actionContext.getServletContext().getAttribute("sys.roles");
		if (roles == null) { //为了安全期间,不允许这种无权限表的情况,所有一概返回500
			if (log.isWarnEnabled())
				log.warn("Role config not found?!");
			return new HttpStatusView(500); //Deny all req
		}
		if (authService.isAuth(new AuthContext(me, auth, roles)))
			return null;
		return new HttpStatusView(403); //Not Ok,客户端就收到403响应了,标准的未授权
	}

}
