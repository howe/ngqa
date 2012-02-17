package org.nutz.ngqa.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.mvc.View;
import org.nutz.ngqa.api.meta.Pager;
import org.nutz.ngqa.bean.Question;

import com.rsslibj.elements.Channel;

public class RssView implements View {

	@SuppressWarnings("unchecked")
	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Throwable {
		String reqURL = req.getRequestURL().toString();
		String hostRoot = reqURL.substring(0, reqURL.indexOf("/", 8));
		Channel channel = new Channel();
		channel.setCopyright("Ngqa copy right");
        channel.setLink(reqURL);
        channel.setTitle("Ngqa questions");
        
        if (obj instanceof Pager) {
        	Pager<Question> pager = (Pager<Question>) obj;
        	for (Question question : pager.getData()) {
				channel.addItem(hostRoot + "/question/"+question.getId()+".shtml", question.getTitle(), question.getContent());
			}
        } else if (obj instanceof Question) {
        	Question question = (Question) obj;
        	channel.addItem(hostRoot + "/question/"+question.getId()+".shtml", question.getTitle(), question.getContent());
        }
        resp.getWriter().write(channel.getFeed("rss"));
	}

}
