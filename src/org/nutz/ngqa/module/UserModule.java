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
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.ngqa.bean.UserBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@IocBean(create="init")
@InjectName
@At("/user")
public class UserModule {

    static final long _5min = 300000L;
    static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";
	
	private String enpoint = "Google";
	
	private OpenIdManager manager = new OpenIdManager();
	
	@Inject("java:$mongos.coll('user')")
	private DBCollection userColl;
	
	public void init() {
//		userColl.setObjectClass(UserBean.class);
	}
	
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
	
	@At("/logout")
	@Ok("void")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@At("/login/callback")
	public String returnPoint(HttpServletRequest request) {
		checkNonce(request.getParameter("openid.response_nonce"));
        // get authentication:
        byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
        String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
        Authentication authentication = manager.getAuthentication(request, mac_key, alias);
        authentication.getEmail();
        BasicDBObject query = new BasicDBObject();
        query.append("email", authentication.getEmail());
        query.append("openid", "Google");
        BasicDBObject update = new BasicDBObject();
        update.append("$set", new BasicDBObject("lastLoginDate", new Date()));
        DBObject dbObject = userColl.findAndModify(query, null, null, false, update, true, true);
        UserBean user = new UserBean();
        user.putAll(dbObject);
        request.getSession().setAttribute("me", user);
        return "Login success!";
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

    @At("/code/?")
    public View just(int code) {
    	return new HttpStatusView(code);
    }
}