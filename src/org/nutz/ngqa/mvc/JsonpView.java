package org.nutz.ngqa.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;

public class JsonpView implements View {

	private String callback;
	
	public JsonpView(String callback) {
		this.callback = callback;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws IOException {
		resp.setHeader("Cache-Control", "no-cache");
		resp.setContentType("text/plain");

		PrintWriter writer = resp.getWriter();
		String callback = req.getParameter("jsoncallback");
		if ("?".equals(callback))
			callback = "";
		else if (Strings.isBlank(callback))
			callback = this.callback == null ? "" : this.callback;
		if (callback.length() > 0)
			writer.write(callback);
		writer.write("(");
		// by mawm 改为直接采用resp.getWriter()的方式直接输出!
		Json.toJson(writer, obj, JsonFormat.compact());
		writer.write(")");

		resp.flushBuffer();
	}
}
