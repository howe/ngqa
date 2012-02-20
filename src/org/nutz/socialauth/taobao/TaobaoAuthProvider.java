package org.nutz.socialauth.taobao;

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
 * 淘宝登陆, OAuth2
 * @author MingMing
 *
 */
@SuppressWarnings("serial")
public class TaobaoAuthProvider extends AbstractOAuthProvider {

	public TaobaoAuthProvider(OAuthConfig providerConfig) {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,"https://oauth.taobao.com/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL,"https://oauth.taobao.com/token");
		AllPerms = new String[] {};
		AuthPerms = new String[] {};
		authenticationStrategy = new OAuth2(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());

		PROFILE_URL = "http://gw.api.taobao.com/router/rest?method=taobao.user.get&fields=user_id&format=json";
	}

	@SuppressWarnings("unchecked")
	protected Profile authLogin() throws Exception {
		String presp;
		System.out.println(accessGrant.getAttributes());
		try {
			Response response = authenticationStrategy.executeFeed(PROFILE_URL);
			presp = response.getResponseBodyAsString(Constants.ENCODING);
		} catch (Exception e) {
			throw new SocialAuthException("Error while getting profile from "
					+ PROFILE_URL, e);
		}
		try {
			Map<String, Object> data = Json.fromJson(Map.class, presp);
			if (!data.containsKey("uid"))
				throw new SocialAuthException("Error: " + presp);
			if (userProfile == null)
				userProfile = new Profile();
			userProfile.setValidatedId(data.get("uid").toString());
			userProfile.setProviderId(getProviderId());
			return userProfile;

		} catch (Exception ex) {
			throw new ServerDataException(
					"Failed to parse the user profile json : " + presp, ex);
		}
	}

}
