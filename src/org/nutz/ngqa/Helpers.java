package org.nutz.ngqa;

import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.Mvcs;
import org.nutz.ngqa.bean.Question;

import com.petebevin.markdown.MarkdownProcessor;

public class Helpers {

	/**生成Question详情页的完整URL*/
	public static String makeQuestionURL(Question question) {
		HttpServletRequest req = Mvcs.getReq();
		String reqURL = req.getRequestURL().toString();
		String contextPath = req.getSession().getServletContext().getContextPath() + "/";
		String hostRoot = reqURL.substring(0, reqURL.indexOf("/", 8)) + contextPath;
		return hostRoot + "question/"+question.getId();
	}
	
	public static String formartContent(String format, String content) {
		if (format == null)
			return content;
		if ("markdown".equals(format)) {
			return new MarkdownProcessor().markdown(content);
		} 
		return content;
	}
}
