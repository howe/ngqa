package org.nutz.ngqa.api.meta;

import java.util.Map;

import lombok.Data;

import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.mvc.Auth;

@Data
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
	
	
}
