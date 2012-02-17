package org.nutz.ngqa;

import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.Mvcs;
import org.nutz.ngqa.bean.Question;

public class Helpers {

	public static String makeQuestionURL(Question question) {
		HttpServletRequest req = Mvcs.getReq();
		String reqURL = req.getRequestURL().toString();
		String contextPath = req.getSession().getServletContext().getContextPath() + "/";
		String hostRoot = reqURL.substring(0, reqURL.indexOf("/", 8)) + contextPath;
		return hostRoot + "question/"+question.getId()+".shtml";
	}
}
