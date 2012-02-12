package org.nutz.ngqa;

import org.nutz.mongo.MongoDao;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.Question;
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
		
		//创建匿名用户
		User user = dao.findOne(User.class, new BasicDBObject("openid", "anonymous"));
		if (user == null) {
			user = new User();
			user.setId(commons.seq("user"));
			user.setOpenid("anonymous");
			dao.save(user);
		}
		
		//...
	}
	
	public void destroy(NutConfig config) {
		
	}
}
