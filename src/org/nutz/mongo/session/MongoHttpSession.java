package org.nutz.mongo.session;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@SuppressWarnings("deprecation")
public class MongoHttpSession extends MongoSession implements HttpSession {

	private ServletContext servletContext;
	
	private HttpSessionContext httpSessionContext;
	
	public MongoHttpSession(final DBCollection sessions, final ObjectId id) {
		super(sessions, id);
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
	
	protected void setServletContext(final ServletContext servletContext) {
		this.servletContext = servletContext;
		httpSessionContext = new HttpSessionContext() {
			
			public HttpSession getSession(String id) {
				MongoHttpSession session = new  MongoHttpSession(sessions, new ObjectId(id));
				session.setServletContext(servletContext);
				return session;
			}
			
			public Enumeration<String> getIds() {
				final DBCursor cur = sessions.find(new BasicDBObject(), new BasicDBObject("_id", 1));
				return new Enumeration<String>() {
					
					public boolean hasMoreElements() {
						return cur.hasNext();
					}
					
					public String nextElement() {
						return cur.next().get("_id").toString();
					}
					
					protected void finalize() throws Throwable {
						cur.close();
						super.finalize();
					}
				};
			}
		};
	}

	public HttpSessionContext getSessionContext() {
		return httpSessionContext;
	}

}
