package org.nutz.ngqa.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.mvc.View;
import org.nutz.ngqa.Helpers;
import org.nutz.ngqa.api.meta.Pager;
import org.nutz.ngqa.bean.Question;

import com.rsslibj.elements.Channel;

/**生成RSS全文输出*/
public class RssView implements View {

	@SuppressWarnings("unchecked")
	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Throwable {
		Channel channel = new Channel();
		channel.setCopyright("Ngqa copy right");
        channel.setLink(req.getRequestURL().toString());
        channel.setTitle("Ngqa questions");
        
        if (obj instanceof Pager) {
        	Pager<Question> pager = (Pager<Question>) obj;
        	for (Question question : pager.getData()) {
				channel.addItem(Helpers.makeQuestionURL(question), question.getTitle(), question.getContent());
			}
        } else if (obj instanceof Question) {
        	Question question = (Question) obj;
        	channel.addItem(Helpers.makeQuestionURL(question), question.getTitle(), question.getContent());
        }
        resp.getWriter().write(channel.getFeed("rss"));
	}

}
