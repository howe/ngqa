package org.nutz.ngqa.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.mvc.View;

import com.colorfulsoftware.atom.FeedDoc;

public class AtomView implements View {

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Throwable {
		FeedDoc doc = new FeedDoc();
		
	}

}
