package org.nutz.ngqa.mvc;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

/**专门生成RSS视图的ViewMaker,暂时用不上,因为有SmartView,呵呵*/
public class RssViewMaker implements ViewMaker {

	public View make(Ioc ioc, String type, String value) {
		if ("rss".equals(type))
			return new RssView();
		return null;
	}

}
