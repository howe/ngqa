package org.nutz.mongo.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.nutz.lang.Strings;
import org.nutz.lang.util.Callback;
import org.nutz.mongo.MongoDao;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.SessionProvider;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoSessionManager implements SessionProvider {

	private MongoDao dao;
	private DBCollection sessions;

	public MongoSessionManager(final MongoDao dao) {
		this(dao, "http.session");
	}

	public MongoSessionManager(final MongoDao dao, final String colName) {
		this.dao = dao;
		this.dao.run(new Callback<DB>() {
			public void invoke(DB obj) {
				sessions = obj.getCollection(colName);
			}
		});
	}

	public MongoSession getSession(String key) {
		DBObject dbo = sessions.findOne(new BasicDBObject("_id", new ObjectId(
				key)), new BasicDBObject("_id", 1));
		if (dbo == null || dbo.get("_id") == null)
			return null;
		return new MongoSession(sessions, (ObjectId) dbo.get("_id"));
	}

	public MongoSession getSession(HttpServletRequest req) {
		return getSession(req, true);
	}

	public MongoSession getSession(HttpServletRequest req, boolean createNew) {
		String key = null;
		for (Cookie cookie : req.getCookies()) {
			if ("MongoSessionKey".equalsIgnoreCase(cookie.getName()))
				key = cookie.getValue();
		}
		MongoSession session = null;
		if (!Strings.isBlank(key)) {
			session = getSession(key);
			if (session != null
					&& req.getRemoteAddr().equals(
							session.getValue("remoteAddr"))
					&& req.getHeader("User-Agent").equals(
							session.getValue("userAgent")))
				return session;
		}
		if (!createNew)
			return null;
		Map<String, String> info = new HashMap<String, String>();
		info.put("remoteAddr", req.getRemoteAddr());
		info.put("userAgent", req.getHeader("User-Agent"));
		return MongoSession.create(sessions, info);
	}

	public MongoHttpSession getHttpSession(HttpServletRequest req,
			ServletContext servletContext, boolean createNew) {
		MongoSession session = getSession(req, createNew);
		if (session != null) {
			MongoHttpSession httpSession = new MongoHttpSession(sessions,
					new ObjectId(session.getId()));
			if (servletContext == null)
				servletContext = req.getSession().getServletContext();
			httpSession.setServletContext(servletContext);
		}
		return null;
	}
	
	public HttpSession getHttpSession(HttpServletRequest req) {
		return getHttpSession(req, Mvcs.getServletContext(), true);
	}
	
	public HttpSession getHttpSession(HttpServletRequest req, boolean createNew) {
		return getHttpSession(req, Mvcs.getServletContext(), createNew);
	}
}
