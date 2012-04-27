package org.nutz.safe.module;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.safe.Assert;
import org.nutz.safe.Enc;
import org.nutz.safe.bean.Token;
import org.nutz.safe.bean.User;

/**
 * 
 * @author wendal
 *
 */
@IocBean
@At("/usr")
public class CoreModule {
	
	@Inject
	Dao dao;

	/**普通注册*/
	@At("/reg")
	public Object normalReg(@Param("nm")String _name, @Param("pwd")String _passwd) {
		Assert.len(_name, 3, -1);
		Assert.len(_passwd, 6, -1);
		//解码客户端提交的密码,得到用户真正输入的密码
		String passwd = Enc.sys().enc(_passwd, "rsa-de");
		Assert.len(passwd, 6, 20);
		
		User user = dao.fetch(User.class, Cnd.where("name", "=", _name));
		Assert.mustNull(user, "Username is dup!");
		user = new User();
		user.setName(_name);
		user.setPasswd("NOT OK");
		user = dao.insert(user);
		//加密密码
		user.setPasswd(Enc.sys().enc(passwd + user.getCreateTime().getTime(), "md5"));
		dao.update(user, "passwd");
		return user;
	}
	
	@At("/login")
	public Object login(@Param("nm")String _name, @Param("pwd")String _passwd, @Param("rme")boolean _remeberMe, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
		if (session.getAttribute("me") != null) { //已经登录过? 你还想登录?!
			return session.getAttribute("me");
		}
		
		Assert.len(_name, 3, -1);
		Assert.len(_passwd, 6, -1);
		
		User me = dao.fetch(User.class, Cnd.where("name", "=", _name));
		Assert.notNull(me, "Login Fail!! Username or passwd is wrong!");
		_passwd = Enc.sys().enc(_passwd, "rsa-de") + me.getCreateTime().getTime();
		Assert.equal(me.getPasswd(), Enc.sys().enc(_passwd, "md5"), "Login Fail!! Username or passwd is wrong!");
		
		dao.clear(Token.class, Cnd.where("extData", "=", me.getId()).and("ttype", "=", 1));
		if (_remeberMe) {
			Token token = new Token();
			token.setTtype(1);
			token.setToken(UUID.randomUUID().toString().replace("-", ""));
			token.setExtCheck("" + req.getHeader("User-Agent") + "," + req.getRemoteHost());
			token.setExpireTime(System.currentTimeMillis() + 30 * 24 * 3600 * 1000);
			token.setExtData(me.getId());
			dao.insert(token);
			Cookie cookie = new Cookie("jauto", token.getToken());
			cookie.setMaxAge(30 * 24 * 3600); //一个月
			resp.addCookie(cookie);
		}
		
		session.setAttribute("me", me);
		return me;
	}
	
	@At("/logout") 
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@At("/login/auto")
	public void autoLogin(HttpSession session, HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jauto".equals(cookie.getName())) {
					try {
						String _token = cookie.getValue();
						Cnd cnd = Cnd.where("token", "=", _token);
						cnd.and("extCheck", "=", "" + req.getHeader("User-Agent") + "," + req.getRemoteHost());
						cnd.and("expireTime", ">", System.currentTimeMillis());
						cnd.and("ttype", "=", 1);
						Token token = dao.fetch(Token.class, cnd);
						if (token != null) {
							User me = dao.fetch(User.class, token.getExtData());
							if (me != null) {
								session.setAttribute("me", me);
							}
						}
					} catch (Throwable e) {}
				}
			}
		}
	}
	
	@Ok("raw")
	@At("/server/key")
	public Object encPubKey() {
		return Enc.sys().getPublicKey().getEncoded();
	}
}