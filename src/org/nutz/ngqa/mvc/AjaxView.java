package org.nutz.ngqa.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

/**
 * 将返回渲染成标准 AjaxReturn
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class AjaxView implements View {

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws IOException {
		AjaxReturn re;
		// 空
		if (null == obj) {
			re = Ajax.ok();
		}
		// 异常
		else if (obj instanceof Throwable) {
			re = Ajax.fail()
					.setMsg(Lang.unwrapThrow((Throwable) obj).getMessage())
					.setData(obj);
		}
		// AjaxReturn
		else if (obj instanceof AjaxReturn) {
			re = (AjaxReturn) obj;
		}
		// 数据对象
		else {
			re = Ajax.ok().setData(obj);
		}

		// 写入返回
		Mvcs.write(resp, re, JsonFormat.nice());
	}

}
