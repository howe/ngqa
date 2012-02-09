package org.nutz.ngqa;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.web.ajax.AjaxViewMaker;

@Modules(scanPackage=true)
@IocBy(args = {	"*org.nutz.ioc.loader.json.JsonLoader",
		"ioc/",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
		"org.nutz.ngqa"}, type = ComboIocProvider.class)
@Ok("ajax")
@Fail("ajax")
@Views(AjaxViewMaker.class)
public class MainModule {}
