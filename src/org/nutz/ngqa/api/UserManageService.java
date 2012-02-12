package org.nutz.ngqa.api;

import org.nutz.ngqa.bean.User;

public interface UserManageService {
	
	User find(User user);

	User findOrAdd(User user);
	
	User anonymous();
}
