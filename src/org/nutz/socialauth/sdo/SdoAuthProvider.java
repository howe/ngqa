package org.nutz.socialauth.sdo;

import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.OAuthConfig;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.Encoding;
import org.nutz.mvc.Mvcs;
import org.nutz.socialauth.AbstractOAuthProvider;

@SuppressWarnings("serial")
public class SdoAuthProvider extends AbstractOAuthProvider {

	public SdoAuthProvider(OAuthConfig providerConfig) {
		super(providerConfig);
	}

	@Override
	public String getLoginRedirectURL(String successUrl) throws Exception {
		return String
				.format("https://cas.sdo.com/cas/login?gateway=true&service=%s&appId=%s&appArea=0",
						URLEncoder.encode(successUrl, Encoding.UTF8),
						config.get_consumerKey());
	}

	@Override
	protected Profile doVerifyResponse(Map<String, String> requestParams)
			throws Exception {
		String url = String.format("https://cas.sdo.com/cas/Validate.Ex?service=%s&ticket=%s", URLEncoder.encode(Mvcs.getReq().getRequestURL().toString(), Encoding.UTF8), requestParams.get("ticket"));
		System.out.println(url);
		Response resp = Http.get(url);
		byte[] d = new byte[1024];
		GZIPInputStream in = new GZIPInputStream(resp.getStream());
		in.read(d);
		String[] data = new String(d).trim().split("\n");
		System.out.println(Json.toJson(data));
		if ("yes".endsWith(data[0])) {
			String uid = data[1];
			Profile profile = new Profile();
			profile.setValidatedId(uid);
			profile.setProviderId(getProviderId());
			userProfile = profile;
			return profile;
		}
		throw new SocialAuthException("Sdo Error : " + Json.toJson(data));
	}
	
	@Override
	protected Profile authLogin() throws Exception {
		return null;
	}

}
