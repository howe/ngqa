package org.nutz.socialauth.qq;

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

		PROFILE_URL = "https://graph.qq.com/user/get_user_info";
	}

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
			System.out.println("User Profile : " + presp);
			QQUser qqUser = Json.fromJson(QQUser.class, presp);
			if (qqUser.getRet() != 0)
				throw new SocialAuthException("QQ Error: " + qqUser.getMsg());
			Profile p = new Profile();
			p.setValidatedId(qqUser.getOpenid()); // QQ定义的
			p.setEmail(qqUser.getEmail());
			p.setProviderId(getProviderId());
			userProfile = p;
			return p;

		} catch (Exception ex) {
			throw new ServerDataException(
					"Failed to parse the user profile json : " + presp, ex);
		}
	}
}