package org.nutz.ngqa.module.openid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.brickred.socialauth.AbstractProvider;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Permission;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.ServerDataException;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.exception.UserDeniedPermissionException;
import org.brickred.socialauth.oauthstrategy.OAuth2;
import org.brickred.socialauth.oauthstrategy.OAuthStrategyBase;
import org.brickred.socialauth.util.AccessGrant;
import org.brickred.socialauth.util.Constants;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 实现QQ帐号登录
 * 
 * @author wendal
 */
public class QQAuthProvider extends AbstractProvider implements AuthProvider {

	private static final long serialVersionUID = 3132881816210239230L;

	private static final Log log = Logs.get();

	private Permission scope;
	private OAuthConfig config;
	private Profile userProfile;
	private AccessGrant accessGrant;
	private OAuthStrategyBase authenticationStrategy;

	private static final String[] AllPerms = new String[] { "get_user_info","get_info" };
	private static final String[] AuthPerms = new String[] { "get_user_info", "get_info" };
	private static final String PROFILE_URL = "https://graph.qq.com/user/get_info";

	private static final Map<String, String> ENDPOINTS;

	static {
		ENDPOINTS = new HashMap<String, String>();
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,
				"https://graph.qq.com/oauth2.0/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL,
				"https://graph.qq.com/oauth2.0/token");
	}

	public QQAuthProvider(final OAuthConfig providerConfig) {
		this.config = providerConfig;
		authenticationStrategy = new OAuth2(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());
	}

	public String getLoginRedirectURL(final String successUrl) throws Exception {
		return authenticationStrategy.getLoginRedirectURL(successUrl);
	}

	public Profile verifyResponse(HttpServletRequest httpReq) throws Exception {
		Map<String, String> params = SocialAuthUtil
				.getRequestParametersMap(httpReq);
		return doVerifyResponse(params);
	}

	public Profile verifyResponse(Map<String, String> params) throws Exception {
		return doVerifyResponse(params);
	}

	private Profile doVerifyResponse(final Map<String, String> requestParams)
			throws Exception {
        log.info("Retrieving Access Token in verify response function");
        if (requestParams.get("error_reason") != null
                        && "user_denied".equals(requestParams.get("error_reason"))) {
                throw new UserDeniedPermissionException();
        }
        accessGrant = authenticationStrategy.verifyResponse(requestParams);

        if (accessGrant != null) {
                log.debug("Obtaining user profile");
                return authQQLogin();
        } else {
                throw new SocialAuthException("Access token not found");
        }
	}

    private Profile authQQLogin() throws Exception {
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
                p.setValidatedId(qqUser.getOpenid()); //QQ定义的
                p.setEmail(qqUser.getEmail());
                p.setProviderId(getProviderId());
                userProfile = p;
                return p;

        } catch (Exception ex) {
                throw new ServerDataException(
                                "Failed to parse the user profile json : " + presp, ex);
        }
}
	
	public Response api(String arg0, String arg1, Map<String, String> arg2,
			Map<String, String> arg3, String arg4) throws Exception {
		return null;
	}

	@Override
	public List<Contact> getContactList() throws Exception {
		return null;
	}

	@Override
	public void logout() {
	}

	@Override
	public void setAccessGrant(AccessGrant accessGrant) throws Exception {
		this.accessGrant = accessGrant;
	}

	@Override
	public void setPermission(Permission permission) {
		this.scope = permission;
		authenticationStrategy.setPermission(this.scope);
		authenticationStrategy.setScope(getScope());
	}

	@Override
	public void updateStatus(String arg0) throws Exception {
	}

	@Override
	public Profile getUserProfile() throws Exception {
		return userProfile;
	}

	@Override
	public AccessGrant getAccessGrant() {
		return accessGrant;
	}

	@Override
	public String getProviderId() {
		return config.getId();
	}

	private String getScope() {
		StringBuffer result = new StringBuffer();
		String arr[] = null;
		if (Permission.AUTHENTICATE_ONLY.equals(scope)) {
			arr = AuthPerms;
		} else if (Permission.CUSTOM.equals(scope)
				&& config.getCustomPermissions() != null) {
			arr = config.getCustomPermissions().split(",");
		} else {
			arr = AllPerms;
		}
		result.append(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			result.append(",").append(arr[i]);
		}
		return result.toString();
	}
}