var ioc = {
        "aop_log" : {
                type :'org.nutz.aop.interceptor.LoggingMethodInterceptor',
                fields : {
                		logBeforeInvoke : false,
                		logAfterInvoke  : false,
                		logWhenException: false,
                		logWhenError    : false
                }
        },
        "$aop" : {
                type : 'org.nutz.ioc.aop.config.impl.JsonAopConfigration',
                fields : {
                        itemList : [
                                ['.+','.+','ioc:aop_log']
                        ]
                }
        }
}