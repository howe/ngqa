package org.nutz.mongo.session;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;

public class MongoSessionFilter implements Filter {

	private MongoSessionManager manager;
	private ServletContext servletContext;
	private String sessionAttrName;
	private String managerAttrName;
	private boolean createNew;
	private Pattern ingore;

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if (ingore == null
				|| !ingore.matcher(((HttpServletRequest) req).getRequestURI())
						.find()) {
			if (manager == null)
				manager = (MongoSessionManager) servletContext
						.getAttribute(managerAttrName);
			MongoHttpSession session = manager.getHttpSession(
					(HttpServletRequest) req, servletContext, createNew);
			if (session != null) {
				req.setAttribute(sessionAttrName, session);
				boolean flag = true;
				for (Cookie cookie : ((HttpServletRequest) req).getCookies()) {
					if ("MongoSessionKey".equalsIgnoreCase(cookie.getName())) {
						if (session.getId().equals(cookie.getValue())) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					Cookie cookie = new Cookie("MongoSessionKey", session.getId());
					cookie.setMaxAge(30 * 24 * 60 * 60);
					Mvcs.getResp().addCookie(cookie);
				}
			}
		}
		doFilter(req, resp, chain);
	}

	public void init(FilterConfig config) throws ServletException {
		servletContext = config.getServletContext();

		if (Strings.isBlank(config.getInitParameter("managerAttrName")))
			managerAttrName = "MongoSessionManager";
		else
			managerAttrName = config.getInitParameter("managerAttrName");

		if (Strings.isBlank(config.getInitParameter("sessionAttrName")))
			sessionAttrName = "session";
		else
			sessionAttrName = config.getInitParameter("sessionAttrName");
		createNew = !"false".equals(config.getInitParameter("createNew"));
		String ingorePatten = config.getInitParameter("ingorePatten");
		if (Strings.isBlank(ingorePatten))
			this.ingore = Pattern
					.compile(".+[.](jpg|png|ico|git|html|swf|flv|mp4|zip|gz|rar|7z)$");
		else if (!"false".equalsIgnoreCase(ingorePatten))
			this.ingore = Pattern.compile(ingorePatten);
	}

	public void destroy() {
		manager = null;
	}
}
