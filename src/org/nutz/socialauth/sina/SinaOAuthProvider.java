package org.nutz.socialauth.sina;

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
 * 实现Sina微博帐号登录,OAuth2
 * 
 * @author wendal
 */
@SuppressWarnings("serial")
public class SinaOAuthProvider extends AbstractOAuthProvider {

	public SinaOAuthProvider(final OAuthConfig providerConfig) {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,"https://api.weibo.com/oauth2/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL,"https://api.weibo.com/oauth2/access_token");
		AllPerms = new String[] {};
		AuthPerms = new String[] {};
		authenticationStrategy = new OAuth2(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());

//		PROFILE_URL = "https://api.weibo.com/2/account/get_uid.json"; //只是取uid,其他啥都拿不到
		PROFILE_URL = "https://api.weibo.com/2/users/show.json?uid=${uid}"; //只是取uid,其他啥都拿不到
	}

	@SuppressWarnings("unchecked")
	protected Profile authLogin() throws Exception {
		String presp;

		try {
		    String uid = this.accessGrant.getAttribute("uid").toString(); 
			Response response = authenticationStrategy.executeFeed(PROFILE_URL.replace("${uid}", uid));
			presp = response.getResponseBodyAsString(Constants.ENCODING);
		} catch (Exception e) {
			throw new SocialAuthException("Error while getting profile from "
					+ PROFILE_URL, e);
		}
		try {
			Map<String, Object> data = Json.fromJson(Map.class, presp);
			if (!data.containsKey("id"))
				throw new SocialAuthException("Error: " + presp);
			if (userProfile == null)
				userProfile = new Profile();
			userProfile.setValidatedId(data.get("id").toString());
			userProfile.setProviderId(getProviderId());
			if(data.containsKey("screen_name")){
			    userProfile.setDisplayName(data.get("screen_name").toString());
			}
			if(data.containsKey("profile_image_url")){
			    userProfile.setProfileImageURL(data.get("profile_image_url").toString());
			}
			if(data.containsKey("gender")){
			    userProfile.setGender(data.get("gender").toString());
			}
			return userProfile;

		} catch (Exception ex) {
			throw new ServerDataException(
					"Failed to parse the user profile json : " + presp, ex);
		}
	}
	
	@Override
	protected String verifyResponseMethod() {
		return "POST";
	}
}