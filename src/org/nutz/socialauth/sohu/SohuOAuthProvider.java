package org.nutz.socialauth.sohu;

import java.util.Map;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.ServerDataException;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.oauthstrategy.OAuth1;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.nutz.json.Json;
import org.nutz.socialauth.AbstractOAuthProvider;

/**
 * 实现搜狐微博帐号登录,OAuth1
 * 
 * @author wendal
 */
@SuppressWarnings("serial")
public class SohuOAuthProvider extends AbstractOAuthProvider {

	public SohuOAuthProvider(final OAuthConfig providerConfig) {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_REQUEST_TOKEN_URL,"http://api.t.sohu.com/oauth/request_token");
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,"http://api.t.sohu.com/oauth/authorize?hd=default");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL, "http://api.t.sohu.com/oauth/access_token");
		AllPerms = new String[] {};
		AuthPerms = new String[] {};
		authenticationStrategy = new OAuth1(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());

		PROFILE_URL = "http://api.t.sohu.com/account/verify_credentials.json";
	}

	@SuppressWarnings("unchecked")
	protected Profile authLogin() throws Exception {
		String presp;

		try {
			Response response = authenticationStrategy.executeFeed(PROFILE_URL);
			presp = response.getResponseBodyAsString(Constants.ENCODING);
			//System.out.println(response.getStatus());
		} catch (Exception e) {
			throw new SocialAuthException("Error while getting profile from "
					+ PROFILE_URL, e);
		}
		try {
			//System.out.println("User Profile : " + presp);
			Map<String, Object> data = Json.fromJson(Map.class, presp);
			if (!data.containsKey("id"))
				throw new SocialAuthException("Error: " + presp);
			if (userProfile == null)
				userProfile = new Profile();
			userProfile.setValidatedId(data.get("id").toString());
			userProfile.setProviderId(getProviderId());
			return userProfile;

		} catch (Exception ex) {
			throw new ServerDataException(
					"Failed to parse the user profile json : " + presp, ex);
		}
	}
}