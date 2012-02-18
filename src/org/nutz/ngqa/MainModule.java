package org.nutz.ngqa;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.UrlMappingBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.ngqa.mvc.EnhanceUrlMapping;
import org.nutz.ngqa.mvc.SmartViewMaker;
import org.nutz.web.ajax.AjaxViewMaker;

/**主模块,主要功能就是项目的整体配置*/
@Modules(scanPackage=true) //扫描当前类所在的package及子package中所有包含@At方法的类,作为模块类
@IocBy(args = {	//配置Ioc容器
		"*org.nutz.ioc.loader.json.JsonLoader","ioc/", //扫描ioc文件夹中的js文件,作为JsonLoader的配置文件
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","org.nutz.ngqa"}, 
		type = ComboIocProvider.class) //这里用到了复合Ioc加载提供器,因为本项目同时使用了js和注解作为Ioc的配置方式
@Ok("ajax") //配置默认视图,这里的ajax视图,是nutz-web项目额外提供的,为Ajax请求提供统一的响应格式
@Fail("ajax") //配置失败视图,只有抛异常,才会走这个视图的,无其他方式,这种视图,一般就是接受一个异常对象
@Views({AjaxViewMaker.class,SmartViewMaker.class}) //注册私有的ViewMaker,这里注册的是支持ajax和smart的两个ViewMaker
@SetupBy(NgqaSetup.class) //提供一个Setup接口的实现,在项目启动/关闭时执行一些逻辑
@UrlMappingBy(value=EnhanceUrlMapping.class) //UrlMapping能拿到很多Action映射方面的信息,所以,继承默认实现,获取自己需要的逻辑(主要Auth管理)
public class MainModule {}
