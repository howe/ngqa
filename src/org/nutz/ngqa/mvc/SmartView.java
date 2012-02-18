package org.nutz.ngqa.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.mvc.View;
import org.nutz.mvc.view.JspView;
import org.nutz.web.ajax.AjaxView;

/**智能视图,通过用户访问的URL的后缀来判断需要执行的视图*/
public class SmartView implements View {

	public SmartView(String viewValue) {
		this.viewValue = viewValue;
	}

	private String viewValue;

	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Throwable {
		String uri = req.getRequestURI();
		if (uri.endsWith(".json"))
			new AjaxView().render(req, resp, obj);
		else if (uri.endsWith(".rss"))
			new RssView().render(req, resp, obj);
		else
			new JspView(viewValue).render(req, resp, obj); //这里默认跑jsp
	}

}
