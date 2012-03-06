package org.nutz.ngqa;

import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.session.MongoSessionManager;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.ngqa.api.Ngqa;
import org.nutz.ngqa.bean.Answer;
import org.nutz.ngqa.bean.App;
import org.nutz.ngqa.bean.Question;
import org.nutz.ngqa.bean.Role;
import org.nutz.ngqa.bean.SystemConfig;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.service.CommonMongoService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class NgqaSetup implements Setup {

	/**NutzMvc正常启动后就会执行这个方法,你可以认为所有东西都已经准备好了,你可以做一些额外的逻辑,例如启动某些后台线程*/
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
			anonymous.setProvider("anonymous");
			dao.save(anonymous);
		}
		
		//创建超级用户
		User root = dao.findOne(User.class, new BasicDBObject("provider", "root"));
		if (root == null) {
			root = new User();
			root.setProvider("root");
			dao.save(root);
		}
		
		//检查超级用户的密码
		DBObject dbo = commons.coll("systemconfig").findOne();
		if (dbo == null) {
			commons.coll("systemconfig").insert(new BasicDBObject("api_version", Ngqa.apiVersion()));
			dbo = commons.coll("systemconfig").findOne();
		}
		if (Strings.isBlank((String)dbo.get("root_password"))) {
			commons.coll("systemconfig").findAndModify(new BasicDBObject(), null, null, false, new BasicDBObject("$set", new BasicDBObject("root_password", R.sg(64).next())), true, true);
		}
		
		//载入js脚本
		MongoJsManager.load(dao.getDB(), "mongo_js");
		
		//载入MongoSession,实现分布式Session机制
		new MongoSessionManager(dao).register(config.getServletContext(), null);
	}
	
	/**项目关闭时执行的逻辑*/
	public void destroy(NutConfig config) {
		//暂时无任何操作
	}
}
