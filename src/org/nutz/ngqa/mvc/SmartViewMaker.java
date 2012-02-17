package org.nutz.ngqa.mvc;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class SmartViewMaker implements ViewMaker {

	@Override
	public View make(Ioc ioc, String name, String value) {
		if ("smart".equals(name))
			return new SmartView(value);
		return null;
	}

}
