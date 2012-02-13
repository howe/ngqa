package org.nutz.ngqa.module;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
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
@Filters()
public class AppModule {

	@Inject
	private CommonMongoService commons;

	@Inject("java:$commons.coll('systemconfig')")
	private DBCollection systemconfigColl;

	@Inject("java:$commons.dao()")
	private MongoDao dao;

	@At("/user/login/root")
	@Ok("jsp:/index.jsp")
	public Object loginAsRoot(@Param("key") String key, HttpSession session) {
		if (!Strings.isBlank(key)
				&& 1 == systemconfigColl.count(new BasicDBObject(
						"root.password", key))) {
			User root = dao.findOne(User.class, new BasicDBObject("provider",
					"root"));
			session.setAttribute("me", root);
			return null;
		} else
			return new HttpStatusView(403);
	}

	@At("/user/login/app")
	@Ok("ajax")
	public Object appLogin(@Param("appId") String appId, HttpSession session)
			throws Exception {
		if (appId != null) {
			App app = dao.findOne(App.class, new BasicDBObject("_id", appId));
			if (app != null) {
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
		dao.runNoError(new Callback<DB>() {
			
			@Override
			public void invoke(DB arg0) {
				dao.save(name);
			}
		});
		return Ajax.ok().setData(dao.findOne(App.class, new BasicDBObject("name", name)));
	}
	
	@At("/app/active/?")
	@Filters({@By(type=ActionFilter.class, args={"ioc:authFilter"})})
	@Auth("app.active")
	public Object activeApp(String name) {
		if (Strings.isBlank(name))
			return Ajax.fail().setMsg("name is emtry!");
		commons.coll("app").findAndModify(new BasicDBObject("name", name), new BasicDBObject("$set", new BasicDBObject("active", true)));
		return Ajax.ok();
	}
	
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
