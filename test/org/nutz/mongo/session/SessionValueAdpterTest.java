package org.nutz.mongo.session;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class SessionValueAdpterTest {

	@Test
	public void test_simle_value() throws Throwable {
		SessionValueAdpter adpter = new SessionValueAdpter();

		assertEquals("abc", adpter.fromValue(adpter.toValue("abc"), null));
		assertEquals(1, adpter.fromValue(adpter.toValue(1), null));
		assertEquals('c', adpter.fromValue(adpter.toValue('c'), null));
		assertEquals(false, adpter.fromValue(adpter.toValue(false), null));
		assertEquals(1L, adpter.fromValue(adpter.toValue(1L), null));
		assertEquals(Pattern.compile("(jsp|html)$").pattern(), 
				((Pattern)adpter.fromValue(adpter.toValue(Pattern.compile("(jsp|html)$")), null)).pattern());
	}

}
