package org.nutz.socialauth.douban;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.oauthstrategy.OAuth1;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.OAuthConfig;
import org.nutz.socialauth.AbstractOAuthProvider;

@SuppressWarnings("serial")
public class DoubanOAuthProvider extends AbstractOAuthProvider {

	public DoubanOAuthProvider(OAuthConfig providerConfig) {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_REQUEST_TOKEN_URL,"http://www.douban.com/service/auth/request_token");
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,"http://www.douban.com/service/auth/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL, "http://www.douban.com/service/auth/access_token");
		AllPerms = new String[] {};
		AuthPerms = new String[] {};
		authenticationStrategy = new OAuth1(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());

		PROFILE_URL = "http://api.douban.com/people/@me";
	}

	@Override
	protected Profile authLogin() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
