package org.nutz.ngqa.mvc;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class AjaxViewMaker implements ViewMaker {

	@Override
	public View make(Ioc ioc, String type, String value) {
		if ("ajax".equalsIgnoreCase(type)) {
			return new AjaxView();
		}
		return null;
	}

}
