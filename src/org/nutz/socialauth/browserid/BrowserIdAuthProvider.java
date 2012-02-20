package org.nutz.socialauth.browserid;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Permission;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.AccessGrant;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.nutz.json.Json;

/**
 * 实现百度帐号登录,OAuth2
 * 
 * @author wendal
 */
public class BrowserIdAuthProvider implements AuthProvider {
	
	private Profile profile;
	
	private OAuthConfig config;
	
	public BrowserIdAuthProvider(final OAuthConfig providerConfig) {
		this.config = providerConfig;
	}

	public String getLoginRedirectURL(String successUrl) throws Exception {
		return successUrl;
	}

	@Override
	public Response api(String arg0, String arg1, Map<String, String> arg2,
			Map<String, String> arg3, String arg4) throws Exception {
		return null;
	}

	@Override
	public AccessGrant getAccessGrant() {
		return null;
	}

	@Override
	public List<Contact> getContactList() throws Exception {
		return null;
	}

	@Override
	public String getProviderId() {
		return config.getId();
	}

	@Override
	public Profile getUserProfile() throws Exception {
		return profile;
	}

	@Override
	public void logout() {}

	@Override
	public void setAccessGrant(AccessGrant arg0) throws Exception {}

	@Override
	public void setPermission(Permission arg0) {}

	@Override
	public void updateStatus(String arg0) throws Exception {}

	@SuppressWarnings("unchecked")
	@Override
	public Profile verifyResponse(HttpServletRequest req) throws Exception {
		return verifyResponse(req.getParameterMap());
	}

	@Override
	public Profile verifyResponse(Map<String, String> params) throws Exception {
		Map<String, Object> result = BrowserId.verify(params.get("audience"), params.get("assertion"));
		if (result != null && "okay".equals(result.get("status"))) {
			Profile profile = new Profile();
			profile.setValidatedId(result.get("email").toString());
			profile.setEmail(result.get("email").toString());
			profile.setProviderId(getProviderId());
			this.profile = profile;
			return profile;
		}
		throw new SocialAuthException("Error: " + Json.toJson(result));
	}
}