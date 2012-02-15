package org.nutz.ngqa.api.meta;

import java.util.Map;

import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.mvc.Auth;

public class AuthContext {

	public AuthContext(User user, Auth auth, Map<String, Role> roles) {
		super();
		this.user = user;
		this.auth = auth;
		this.roles = roles;
	}
	private User user;
	private Auth auth;
	private Map<String, Role> roles;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Auth getAuth() {
		return auth;
	}
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	public Map<String, Role> getRoles() {
		return roles;
	}
	public void setRoles(Map<String, Role> roles) {
		this.roles = roles;
	}
	
	
}
