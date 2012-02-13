package org.nutz.socialauth.baidu;

import java.util.Map;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.ServerDataException;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.oauthstrategy.OAuth2;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.nutz.json.Json;
import org.nutz.socialauth.AbstractOAuthProvider;

/**
 * 实现百度帐号登录,OAuth2
 * 
 * @author wendal
 */
@SuppressWarnings("serial")
public class BaiduOAuthProvider extends AbstractOAuthProvider {

	public BaiduOAuthProvider(final OAuthConfig providerConfig) {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,"https://openapi.baidu.com/oauth/2.0/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL,"https://openapi.baidu.com/oauth/2.0/token");
		AllPerms = new String[] {};
		AuthPerms = new String[] {};
		authenticationStrategy = new OAuth2(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());

		PROFILE_URL = "https://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser?format=json";
	}

	@SuppressWarnings("unchecked")
	protected Profile authLogin() throws Exception {
		String presp;

		try {
			Response response = authenticationStrategy.executeFeed(PROFILE_URL);
			presp = response.getResponseBodyAsString(Constants.ENCODING);
		} catch (Exception e) {
			throw new SocialAuthException("Error while getting profile from "
					+ PROFILE_URL, e);
		}
		try {
			//System.out.println("User Profile : " + presp);
			Map<String, Object> data = Json.fromJson(Map.class, presp);
			if (!data.containsKey("uid"))
				throw new SocialAuthException("Error: " + presp);
			Profile p = new Profile();
			p.setValidatedId(data.get("uid").toString());
			userProfile = p;
			return p;

		} catch (Exception ex) {
			throw new ServerDataException(
					"Failed to parse the user profile json : " + presp, ex);
		}
	}
}