package org.nutz.ngqa.mvc;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.FailProcessor;
import org.nutz.ngqa.NgqaRuntimeException;
import org.nutz.ngqa.NgqaRuntimeException.JustFailViewException;

public class SmartFailProcessor extends FailProcessor {
	
	private static final Log log = Logs.get();
	
	public void process(ActionContext ac) throws Throwable {
		if (ac.getError() instanceof NgqaRuntimeException.JustFailViewException) {
			if (log.isInfoEnabled())
				log.info("Catch JustFailViewException, render Fail View");
			view.render(ac.getRequest(), ac.getResponse(), ((JustFailViewException)ac.getError()).getData());
			return;
		}
		super.process(ac);
	}
}
