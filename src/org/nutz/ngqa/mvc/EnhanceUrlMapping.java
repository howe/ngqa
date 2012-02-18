package org.nutz.ngqa.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mongo.MongoDao;
import org.nutz.mvc.ActionChainMaker;
import org.nutz.mvc.ActionInfo;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.impl.UrlMappingImpl;
import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.service.CommonMongoService;

import com.mongodb.BasicDBObject;

/**增强型UrlMappingImpl,获取更多入口方法的信息*/
public class EnhanceUrlMapping extends UrlMappingImpl {

	private static final Log log = Logs.get();
	
	private MongoDao dao;
	
	@SuppressWarnings("unchecked")
	public void add(ActionChainMaker maker, ActionInfo ai, NutConfig config) {
		super.add(maker, ai, config); //先把该做的做好,然后再来做特殊的逻辑
		
		if (dao == null)
			dao = config.getIoc().get(CommonMongoService.class, "commons").dao();
		
		Method method = ai.getMethod();
		Auth auth = method.getAnnotation(Auth.class);
		if (auth == null)
			return; //没标注@Auth,恩,路过....
		
		String[] roleNames = auth.value();
		if (roleNames == null || roleNames.length == 0)
			return;
		Map<String, Role> roles = (Map<String, Role>) config.getServletContext().getAttribute("sys.roles");
		if (roles == null) {
			roles = new HashMap<String, Role>();
			config.getServletContext().setAttribute("sys.roles", roles);
		}
		for (String roleName : roleNames) {
			if (roles.containsKey(roleName))
				continue;
			Role role = dao.findOne(Role.class, new BasicDBObject("name", roleName));
			if (role == null) {
				if (log.isInfoEnabled())
					log.info("Create new role = " + roleName);
				role = new Role();
				role.setName(roleName);
				dao.save(role);
			}
			roles.put(roleName, role);
			if (log.isDebugEnabled())
				log.debug("Cache role = " + roleName);
		}
	}
	
}
