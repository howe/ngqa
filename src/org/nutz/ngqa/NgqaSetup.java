package org.nutz.ngqa;

import org.nutz.mongo.MongoDao;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.App;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.bean.SystemConfig;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.service.CommonMongoService;

import com.mongodb.BasicDBObject;

public class NgqaSetup implements Setup {

	public void init(NutConfig config) {
		CommonMongoService commons = config.getIoc().get(CommonMongoService.class, "commons");
		MongoDao dao = commons.dao();
		
		//初始化集合
		dao.create(User.class, false);
		dao.create(Question.class, false);
		dao.create(Answer.class, false);
		dao.create(SystemConfig.class, false);
		dao.create(App.class, false);
		dao.create(Role.class, false);
		
		//创建匿名用户
		User anonymous = dao.findOne(User.class, new BasicDBObject("provider", "anonymous"));
		if (anonymous == null) {
			anonymous = new User();
			anonymous.setId(commons.seq("user"));
			anonymous.setProvider("anonymous");
			dao.save(anonymous);
		}
		
		//创建超级用户
		User root = dao.findOne(User.class, new BasicDBObject("provider", "root"));
		if (root == null) {
			root = new User();
			root.setId(commons.seq("user"));
			root.setProvider("root");
			dao.save(root);
		}
	}
	
	public void destroy(NutConfig config) {
		
	}
}
