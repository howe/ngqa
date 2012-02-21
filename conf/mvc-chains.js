{
	"default" : {
		"ps" : [
		      "org.nutz.mvc.impl.processor.UpdateRequestAttributesProcessor",
		      "org.nutz.mvc.impl.processor.EncodingProcessor",
		      "org.nutz.mvc.impl.processor.ModuleProcessor",
		      "org.nutz.mvc.impl.processor.ActionFiltersProcessor",
		      "org.nutz.ngqa.mvc.EnhanceAdaptorProcessor",
		      'org.nutz.ngqa.mvc.ValidationProcessor',
		      "org.nutz.mvc.impl.processor.MethodInvokeProcessor",
		      "org.nutz.mvc.impl.processor.ViewProcessor"
		      ],
		"error" : 'org.nutz.mvc.impl.processor.FailProcessor'
	}
}