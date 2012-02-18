package org.nutz.ngqa.module;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoDao;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.ngqa.bean.App;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.mvc.Auth;
import org.nutz.ngqa.service.CommonMongoService;
import org.nutz.web.ajax.Ajax;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@IocBean
@InjectName
@Filters() //覆盖主模块的Filters设置,因为本模块的操作,均自行校验权限
public class AppModule {

	@Inject//注入一个bean,是@Inject("refer:commons")的简写
	private CommonMongoService commons;

	@Inject("java:$commons.coll('systemconfig')") //可以调用方法来获取需要注入的内容的
	private DBCollection systemconfigColl;

	@Inject("java:$commons.dao()")
	private MongoDao dao;

	//这个是整个系统的最高权限,root用户,的登录入口
	@At("/user/login/root")
	@Ok(">>:/index.jsp")
	public Object loginAsRoot(@Param("key") String key, HttpSession session) {
		if (!Strings.isBlank(key)
				&& 1 == systemconfigColl.count(new BasicDBObject(
						"root_password", key))) {
			User root = dao.findOne(User.class, new BasicDBObject("provider",
					"root"));
			session.setAttribute("me", root);
			return null;
		} else
			return new HttpStatusView(403);
	}

	// app的登录入口.设计这个,是因为服务器端不单单面向Web,同时是个OpenAPI,允许通过其他方式访问该系统
	// app无法通过OAuth授权来登录,因为不是浏览器,所以做这个入口,供app登录,然后再切换到其他用户.app如果要切换到某个用户,那么,这个用户必须授权给app(通过检查authedApp属性).
	@At("/user/login/app")
	@Ok("ajax")
	public Object appLogin(@Param("appId") String appId, HttpSession session)
			throws Exception {
		if (appId != null) {
			App app = dao.findById(App.class, appId);
			if (app != null) {
				if (!app.isActive())
					return Ajax.fail().setData("app isn't active!");
				String value = UUID.randomUUID().toString() + "_"
						+ Math.random();
				session.setAttribute("app.token", value);
				MessageDigest sha1 = MessageDigest.getInstance("sha1");
				sha1.update(value.getBytes());
				sha1.update(app.getKey().getBytes());
				session.setAttribute("app.token", getHexString(sha1.digest()));
				session.setAttribute("app.id", appId);
				return Ajax.ok().setData(value);
			}
		}

		return Ajax.fail();
	}

	//为了避免app直接发送自己的密钥,所以需要这个回调,要求app回传login刚刚所提供的字符的加密后的内容进行比对
	@At("/user/login/app/callback")
	public Object appLoginCallback(@Param("token") String token,
			HttpSession session) {
		String _token = (String) session.getAttribute("app.token");
		session.removeAttribute("app.token");
		if (_token != null && _token.equals(token)) {
			App app = dao.findOne(App.class,
					new BasicDBObject("_id", session.getAttribute("app.id")));
			if (app != null) {
				session.setAttribute("app", app);
				return Ajax.ok();
			}
		}
		return Ajax.fail();
	}

	//创建app,并生成密钥
	@At("/app/create/?")
	@Filters({@By(type=ActionFilter.class, args={"ioc:authFilter"})})
	@Auth("app.create")
	public Object createApp(final String name) {
		if (Strings.isBlank(name))
			return Ajax.fail().setMsg("name is emtry!");
		App app = dao.findOne(App.class, new BasicDBObject("name", name));
		if (app != null)
			return Ajax.fail().setMsg("Name exist!!");
		app = new App();
		app.setName(name);
		app.setKey(R.sg(48).next());
		final App _app = app;
		dao.runNoError(new Callback<DB>() {
			
			@Override
			public void invoke(DB arg0) {
				dao.save(_app);
			}
		});
		return Ajax.ok().setData(dao.findOne(App.class, new BasicDBObject("name", name)));
	}
	
	//新创建的app是未激活状态,这个方法会激活app
	@At("/app/active/?")
	@Filters({@By(type=ActionFilter.class, args={"ioc:authFilter"})})
	@Auth("app.active")
	public Object activeApp(String name) {
		if (Strings.isBlank(name))
			return Ajax.fail().setMsg("name is emtry!");
		commons.coll("app").findAndModify(new BasicDBObject("name", name), new BasicDBObject("$set", new BasicDBObject("active", true)));
		return Ajax.ok();
	}
	
	//停用app,使其回到未激活状态
	@At("/app/deactive/?")
	@Filters({@By(type=ActionFilter.class, args={"ioc:authFilter"})})
	@Auth("app.deactive")
	public Object deactiveApp(String name) {
		if (Strings.isBlank(name))
			return Ajax.fail().setMsg("name is emtry!");
		commons.coll("app").findAndModify(new BasicDBObject("name", name), new BasicDBObject("$set", new BasicDBObject("active", false)));
		return Ajax.ok();
	}
	
	// ==============================================================================================
	static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2',
			(byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
			(byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c',
			(byte) 'd', (byte) 'e', (byte) 'f' };

	public static String getHexString(byte[] raw)
			throws UnsupportedEncodingException {
		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw) {
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}
		return new String(hex, "ASCII");
	}
}
