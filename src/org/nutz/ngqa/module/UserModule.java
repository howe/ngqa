package org.nutz.ngqa.module;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Files;
import org.nutz.lang.stream.NullInputStream;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoDao;
import org.nutz.mongo.util.Moo;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.ngqa.bean.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

@IocBean(create="init")
@InjectName
@At("/user")
public class UserModule {
	
	/*提供匿名登录*/
	@At("/login/anonymous")
	@Ok("void")
	public View anonymousLogin(HttpSession session) throws Exception {
		User user = dao.findOne(User.class, new BasicDBObject("provider", "anonymous"));
		session.setAttribute("me", user);
		return new ServerRedirectView("/index.jsp");
	}
	
	/*提供社会化登录*/
	@At("/login/?")
	@Ok("void")
	public void login(String provider, HttpSession session, HttpServletRequest req) throws Exception {
		String returnTo = req.getRequestURL().toString() + "/callback";
		if (req.getParameterMap().size() > 0) {
			StringBuilder sb = new StringBuilder().append(returnTo).append("?");
			for (Object name : req.getParameterMap().keySet()) {
				sb.append(name).append('=').append(URLEncoder.encode(req.getParameter(name.toString()), Encoding.UTF8)).append("&");
			}
			returnTo = sb.toString();
		}
		SocialAuthManager manager = new SocialAuthManager(); //每次都要新建哦
		manager.setSocialAuthConfig(config);
		String url = manager.getAuthenticationUrl(provider, returnTo);
		Mvcs.getResp().setHeader("Location", url);
		Mvcs.getResp().setStatus(302);
		session.setAttribute("openid_manager", manager);
	}
	
	/*登出*/
	@At("/logout")
	@Ok("void")
	public View logout(HttpSession session) {
		session.invalidate(); //销毁会话,啥都米有了
		return new ServerRedirectView("/index.jsp");
	}
	
	/*无需做链接,这是OpenID的回调地址*/
	@At("/login/?/callback")
	@Ok(">>:${obj.nickName == null ? '/me' : '/'}")
	public User returnPoint(String providerId, HttpServletRequest request, HttpSession session) throws Exception {
		SocialAuthManager manager = (SocialAuthManager) session.getAttribute("openid_manager");
		if (manager == null)
			throw new SocialAuthException("Not manager found!");
		session.removeAttribute("openid_manager"); //防止重复登录的可能性
		Map<String, String> paramsMap = SocialAuthUtil.getRequestParametersMap(request); 
		AuthProvider provider = manager.connect(paramsMap);
		Profile p = provider.getUserProfile();
        BasicDBObject query = new BasicDBObject().append("validatedId", p.getValidatedId()).append("provider", providerId);
        User user = dao.findOne(User.class, query);
        if (user == null) {
        	user = new User();
        	user.setEmail(p.getEmail());
        	user.setProvider(providerId);
        	user.setValidatedId(p.getValidatedId());
        	final User _u = user;
        	dao.runNoError(new Callback<DB>() {
    			public void invoke(DB arg0) {
    				dao.save(_u);
    			}
    		});
        }
        Moo moo = Moo.SET("lastLoginDate", new Date()).set("email", p.getEmail());
        dao.update(User.class, new BasicDBObject("_id", user.getId()), moo);
        
        session.setAttribute("me", user);
        session.setMaxInactiveInterval(30 * 24 * 60 * 60);
        return user;
	}
	

	private SocialAuthConfig config;
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
	
	public void init() throws Exception {
		SocialAuthConfig config = new SocialAuthConfig();
		File devConfig = Files.findFile("oauth_consumer.properties_dev"); //开发期所使用的配置文件
		if (devConfig == null)
			devConfig = Files.findFile("oauth_consumer.properties"); //真实环境所使用的配置文件
		if (devConfig == null)
			config.load(new NullInputStream());
		else
			config.load(new FileInputStream(devConfig));
		this.config = config;
	}
}