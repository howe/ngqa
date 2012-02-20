package org.nutz.ngqa;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.ngqa.bean.Question;

import com.petebevin.markdown.MarkdownProcessor;

public class Helpers {

	/** 生成Question详情页的完整URL */
	public static String makeQuestionURL(Question question) {
		HttpServletRequest req = Mvcs.getReq();
		String reqURL = req.getRequestURL().toString();
		String contextPath = req.getSession().getServletContext().getContextPath() + "/";
		String hostRoot = reqURL.substring(0, reqURL.indexOf("/", 8)) + contextPath;
		return hostRoot + "question/" + question.getId();
	}

	/** 根据format返回格式化后的内容 */
	public static String formartContent(String content, String format) {
		if (format == null)
			return content;
		if ("markdown".equals(format)) {
			return new MarkdownProcessor().markdown(content);
		}
		if ("txt".equals(format)) {
			return splitContent(content);
		}
		return content;
	}

	private static String splitContent(String content) {
		if (Strings.isEmpty(Strings.trim(content))) {
			return "";
		}
		StringBuilder showHtml = new StringBuilder();
		String[] lines = content.split("\n");
		List<String> pTags = new LinkedList<String>();
		final String pTagsTamplate = "<p>%s</p>";

		for (int i = 0; i < lines.length; i++) {
			String line = Strings.trim(Strings.escapeHtml(lines[i]));
			if (i != lines.length - 1) {
				if (Strings.isEmpty(line)) {
					showHtml.append(String.format(pTagsTamplate, join(pTags, "<br />")));
					pTags.clear();
				} else {
					pTags.add(line);
				}
			} else {
				if (pTags.size() != 0) {
					pTags.add(line);
					showHtml.append(String.format(pTagsTamplate, join(pTags, "<br />")));
				} else {
					showHtml.append(String.format(pTagsTamplate, line));
				}
			}
		}
		return showHtml.toString();
	}

	private static String join(List<String> list, String conjunction) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String item : list) {
			if (first)
				first = false;
			else
				sb.append(conjunction);
			sb.append(item);
		}
		return sb.toString();
	}
}
