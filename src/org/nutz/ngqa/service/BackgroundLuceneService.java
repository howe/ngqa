package org.nutz.ngqa.service;

import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

@IocBean(create="init", depose="depose")
public class BackgroundLuceneService {

	@Inject
	private CommonMongoService commons;
	protected MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(10);
	
	private static final Log log = Logs.get();
	
	protected Mailbox questionLunceneIndexMailbox;
	
	public void init() {
		questionLunceneIndexMailbox = mailboxFactory.createMailbox();
		
	}
	
	public void depose() {
		mailboxFactory.close();
	}
}
