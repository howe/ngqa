package org.nutz.ngqa.service;

import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.ngqa.api.AuthService;
import org.nutz.ngqa.api.meta.AuthContext;
import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.bean.User;

@IocBean(name="authService")
public class AuthServiceImpl implements AuthService {

	public boolean isAuth(AuthContext authContext) {
		String[] needRoles = authContext.getAuth().value();
		Role[] hasRoles = authContext.getUser().getRoles();
		Map<String, Role> roles = authContext.getRoles();
		for (String roleName : needRoles) {
			Role role = roles.get(roleName);
			for (Role role2 : hasRoles) {
				if (!role.getId().equals(role2.getId()))
					return false;
			}
		}
		return true;
	}

	public boolean addAuth(User user, AuthContext authContext) {
		return false;
	}

	public boolean removeAuth(User user, AuthContext authContext) {
		return false;
	}

}
