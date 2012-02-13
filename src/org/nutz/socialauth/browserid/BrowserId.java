package org.nutz.socialauth.browserid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.lang.Streams;

public class BrowserId {
	
	public static String VerifyURL = "https://browserid.org/verify";

	@SuppressWarnings("unchecked")
	public static Map<String, Object> verify(String audience, String assertion) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audience", audience);
		params.put("assertion", assertion);
		Request req = Request.create(VerifyURL, METHOD.POST, params);
		Response resp = Sender.create(req).send();
		if (resp.getStatus() != 200) {
			throw new RuntimeException("server resp code != 200");
		}
		return Json.fromJson(Map.class, Streams.utf8r(resp.getStream()));
	}
}
