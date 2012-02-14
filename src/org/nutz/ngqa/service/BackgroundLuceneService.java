package org.nutz.ngqa.service;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.nutz.ioc.loader.annotation.IocBean;

/**跑一些定时/不定期的后台任务*/
@IocBean(create="init", depose="depose")
public class BackgroundLuceneService {
	
	private ScheduledThreadPoolExecutor es = new ScheduledThreadPoolExecutor(16);
	
	public void init() {
		//定期统计tag的信息
	}
	
	public void depose() {
	}
}
