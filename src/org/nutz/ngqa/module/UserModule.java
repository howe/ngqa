package org.nutz.ngqa.module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.expressme.openid.Association;
import org.expressme.openid.Authentication;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdException;
import org.expressme.openid.OpenIdManager;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoDao;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.ngqa.bean.User;
import org.nutz.ngqa.service.CommonMongoService;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@IocBean(create="init")
@InjectName
@At("/user")
public class UserModule {

    static final long _5min = 300000L;
    static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";
	
	private String enpoint = "Google";
	
	private OpenIdManager manager = new OpenIdManager();
	
	@Inject("java:$commons.coll('user')")
	private DBCollection userColl;
	
	@Inject("java:$commons.dao()")
	private MongoDao dao;
	
	@Inject
	private CommonMongoService commons;
	
	public void init() {
		dao.create(User.class, false);
	}
	
	/*暂时只提供google登录*/
	@At("/login")
	@Ok(">>:${obj}")
	public String login(HttpSession session) {
		manager.setReturnTo(Mvcs.getReq().getRequestURL().toString() + "/callback");
		manager.setRealm("http://"+Mvcs.getReq().getHeader("Host") + "/");
		manager.setTimeOut(300 * 1000);
		Endpoint endpoint = manager.lookupEndpoint(enpoint);
        Association association = manager.lookupAssociation(endpoint);
        session.setAttribute(ATTR_MAC, association.getRawMacKey());
        session.setAttribute(ATTR_ALIAS, endpoint.getAlias());
        return manager.getAuthenticationUrl(endpoint, association);
	}
	
	/*登出*/
	@At("/logout")
	@Ok("void")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	/*无需做链接,这是OpenID的回调地址*/
	@At("/login/callback")
	public View returnPoint(HttpServletRequest request) {
		checkNonce(request.getParameter("openid.response_nonce"));
        // get authentication:
        byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
        String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
        Authentication authentication = manager.getAuthentication(request, mac_key, alias);
        BasicDBObject query = new BasicDBObject().append("email", authentication.getEmail()).append("openid", "Google");
        User user = dao.findOne(User.class, query);
        if (user == null) {
        	user = new User();
        	user.setEmail(authentication.getEmail());
        	user.setOpenid("Google");
        	user.setLastLoginDate(new Date());
        	user.setId(commons.seq("user"));
        	final User _u = user;
        	dao.runNoError(new Callback<DB>() {
    			public void invoke(DB arg0) {
    				dao.save(_u);
    			}
    		});
        } else
        	userColl.update(new BasicDBObject("_id", user.getId()), new BasicDBObject("$set", new BasicDBObject("lastLoginDate", new Date())));
        request.getSession().setAttribute("me", user);
        return new ServerRedirectView("/index.jsp");
	}
	
	protected void checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce==null || nonce.length()<20)
            throw new OpenIdException("Verify failed.");
        long nonceTime = getNonceTime(nonce);
        long diff = System.currentTimeMillis() - nonceTime;
        if (diff < 0)
            diff = (-diff);
        if (diff > _5min)
            throw new OpenIdException("Bad nonce time.");
		
	}
	
    private static long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch(ParseException e) {
            throw new OpenIdException("Bad nonce time.");
        }
    }
}