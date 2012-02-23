package org.nutz.socialauth.taobao;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.oauthstrategy.OAuth2;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.OAuthConfig;
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

	protected Profile authLogin() throws Exception {
		Profile profile = new Profile();
		profile.setValidatedId(accessGrant.getAttribute("taobao_user_id").toString());
		profile.setDisplayName(accessGrant.getAttribute("taobao_user_nick").toString());
		profile.setProviderId(getProviderId());
		userProfile = profile;
		return profile;
	}

	@Override
	protected String verifyResponseMethod() {
		return "POST";
	}
}
