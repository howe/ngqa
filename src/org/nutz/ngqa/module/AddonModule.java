package org.nutz.ngqa.module;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.Tag;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.ngqa.Helpers;
import org.nutz.ngqa.bean.Question;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@IocBean //声明为一个Ioc的bean
@InjectName //这是module类被识别为IocBean的一个桥. 反正你就加上吧,呵呵
@Ok("void") //不对响应做任何修改的VoidView
@Fail("http:500") //出错就直接跑errorPage 500了,呵呵
@Filters() //覆盖主模块的Filters设置,因为本模块的所有方法,都允许直接访问
public class AddonModule {

	/**生成google所读取的sitemap,方便google索引本站*/
	@At("/sitemap") //google实际访问的URI是 /sitemap.xml , 不过@At里面的值是绝对不能后缀的,因为它只会去匹配去除后缀的URI
	@Ok("void")
	public void sitemap(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DBCursor cur = questionColl.find(new BasicDBObject(), new BasicDBObject("updateAt", 1)).sort(new BasicDBObject("createAt", -1));
		Tag urlset = Tag.tag("urlset");
		urlset.attr("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9")
		   .attr("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
		   .attr("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");
		
		urlset.add("url").add("loc").setText("http://" + req.getHeader("Host"));
		
		while(cur.hasNext()) {
			Question question = new Question();
			question.setId(cur.next().get("_id").toString());
			urlset.add("url").add("loc").setText(Helpers.makeQuestionURL(question));
		}
		
		Writer writer = resp.getWriter();
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.write(urlset.toString());
	}
	
	/**全文输出,方便定义*/
	@At("/rss")
	@Ok("->:/question/query/list.rss?pageSize=100") //内部重定向视图,会重新跑一次流程,记得翻阅web.xml的相应配置哦
	public void rss() {
	}
	
	@Inject("java:$commons.coll('question')")
	private DBCollection questionColl;
}
