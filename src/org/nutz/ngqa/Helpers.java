package org.nutz.ngqa;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.ngqa.bean.Question;

import com.petebevin.markdown.MarkdownProcessor;

public class Helpers {

	/** 生成Question详情页的完整URL */
	public static String makeQuestionURL(Question question) {
		HttpServletRequest req = Mvcs.getReq();
		String reqURL = req.getRequestURL().toString();
		String contextPath = Mvcs.getServletContext().getContextPath() + "/";
		String hostRoot = reqURL.substring(0, reqURL.indexOf("/", 8)) + contextPath;
		return hostRoot + "question/" + question.getId();
	}

	/** 根据format返回格式化后的内容 */
	public static String formatContent(String content, String format) {
		if ("markdown".equals(format.toLowerCase()) || "md".equals(format.toLowerCase())) {
			return new MarkdownProcessor().markdown(content);
		}

		return splitContent(content);
	}

	/** 返回格式化后的日期 */
	public static String getFormatData(Date data) {
		final String formatStr = "yyyy-MM-dd HH:mm";
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(data);
	}

	/** 返回页面表示用的Tags的Html */
	public static String getTagsHtml(String[] tags) {
		if (tags.length == 0) {
			return "&nbsp;||&nbsp;Not tags now";
		}
		final String tagTamplate = "<a href=\"./tags/%s\">%s</a>";
		for (int i = 0; i < tags.length; i++) {
			String html = Strings.escapeHtml(tags[i]);
			tags[i] = String.format(tagTamplate, html, html);
		}
		return "&nbsp;||&nbsp;Question it in " + join(Lang.list(tags), ",&nbsp;");
	}

	public static String getInfosHtml() {
		StringBuilder infosHtml = new StringBuilder();
		String boxTamplate = "<div class=\"box sep21\">%s</div>";

		for (File file : Files.findFile("infos/").listFiles()) {
			String html = formatContent(Files.read(file), Files.getSuffixName(file));
			infosHtml.append(String.format(boxTamplate, html));
//			boxTamplate = "<div class=\"box sep21\">%s</div>";
		}

		return infosHtml.toString();
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
