package org.nutz.ngqa.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionInfo;
import org.nutz.mvc.HttpAdaptor;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.adaptor.PairAdaptor;
import org.nutz.mvc.impl.processor.AbstractProcessor;
import org.nutz.mvc.upload.UploadAdaptor;

/**增强版的AdaptorProcessor,灵活处理转换异常*/
public class EnhanceAdaptorProcessor extends AbstractProcessor {

	private static final Log log = Logs.get();
	
	private HttpAdaptor adaptor;
	
	@Override
	public void init(NutConfig config, ActionInfo ai) throws Throwable {
		adaptor = evalHttpAdaptor(config, ai);
	}

	public void process(ActionContext ac) throws Throwable {
		List<String> phArgs = ac.getPathArgs();
		Object[] args = null;
		try {
			args = adaptor.adapt(	ac.getServletContext(),
										ac.getRequest(),
										ac.getResponse(),
										phArgs.toArray(new String[phArgs.size()]));
			ac.setMethodArgs(args);
		} catch (Throwable e) {
			if (log.isInfoEnabled())
				log.info("adaptor error", e);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", e);
			map.put("msg", e.getMessage());
			if (adaptor instanceof UploadAdaptor) {//首先,看看是不是上传导致的错误
				map.put("errType", "upload");
			} else if (adaptor instanceof JsonAdaptor) { //还是Json读取错误呢?
				map.put("errType", "json");
			} else { //还是其他类型错误呢?
				Throwable e2 = Lang.unwrapThrow(e);
				if (e2 instanceof NumberFormatException) { //数字格式错误
					map.put("errType", "number");
				} else if (e2 instanceof DataFormatException) { //日期格式错误
					map.put("errType", "date");
				} else { //其他,不知道了... 内部错误?
					map.put("errType", "unknow");
				}
			}
			//渲染去
			new SmartView("/error/adaptor").render(ac.getRequest(), ac.getResponse(), map); 
			return;
		}
		doNext(ac);
	}

	protected static HttpAdaptor evalHttpAdaptor(NutConfig config, ActionInfo ai) {
		HttpAdaptor re = evalObj(config, ai.getAdaptorInfo());
		if (null == re)
			re = new PairAdaptor();
		re.init(ai.getMethod());
		return re;
	}
}
