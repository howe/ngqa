package org.nutz.socialauth.qq;

import java.util.Map;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.oauthstrategy.OAuth2;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.nutz.json.Json;
import org.nutz.socialauth.AbstractOAuthProvider;

/**
 * 实现QQ帐号登录,OAuth2
 * 
 * @author wendal
 */
@SuppressWarnings("serial")
public class QQAuthProvider extends AbstractOAuthProvider {

	public QQAuthProvider(final OAuthConfig providerConfig) {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,"https://graph.qq.com/oauth2.0/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL,"https://graph.qq.com/oauth2.0/token");
		AllPerms = new String[] { "get_user_info", "get_info" };
		AuthPerms = new String[] { "get_user_info", "get_info" };
		authenticationStrategy = new OAuth2(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());

		PROFILE_URL = "https://graph.qq.com/oauth2.0/me";
	}

	@SuppressWarnings("unchecked")
	protected Profile authLogin() throws Exception {
		String presp;
		try {
			Response response = authenticationStrategy.executeFeed(PROFILE_URL);
			presp = response.getResponseBodyAsString(Constants.ENCODING); // callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
			if (presp != null) {
				presp = presp.trim().intern();
				if (presp.startsWith("callback(") && presp.endsWith(");")) {
					presp = presp.substring(presp.indexOf("{"), presp.indexOf("}") + 1);
					Map<String, String> map = (Map<String, String>) Json.fromJson(presp);
					if (map.get("openid") != null) {
						Profile p = new Profile();
						p.setValidatedId(map.get("openid")); // QQ定义的
						p.setProviderId(getProviderId());
						userProfile = p;
						return p;
					}
				}
			}
			
			throw new SocialAuthException("QQ Error : " + presp);
		} catch (Exception e) {
			throw new SocialAuthException("Error while getting profile from "
					+ PROFILE_URL, e);
		}

	}
}