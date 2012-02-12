package org.nutz.ngqa.api;

import org.nutz.ngqa.api.meta.AuthContext;
import org.nutz.ngqa.bean.User;

public interface AuthService {

	boolean isAuth(AuthContext authContex);
	
	boolean addAuth(User user, AuthContext authContext);
	
	boolean removeAuth(User user, AuthContext authContext);
}
