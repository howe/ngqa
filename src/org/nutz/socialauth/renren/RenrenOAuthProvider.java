package org.nutz.socialauth.renren;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
 * 实现人人网帐号登录, OAuth2,但获取用户信息需要额外算法!!未完成!!
 * 
 * @author wendal
 */
@SuppressWarnings("serial")
public class RenrenOAuthProvider extends AbstractOAuthProvider {

	public RenrenOAuthProvider(final OAuthConfig providerConfig) {
		super(providerConfig);
		ENDPOINTS.put(Constants.OAUTH_AUTHORIZATION_URL,
				"https://graph.renren.com/oauth/authorize");
		ENDPOINTS.put(Constants.OAUTH_ACCESS_TOKEN_URL,
				"https://graph.renren.com/oauth/token");
		AllPerms = new String[] {};
		AuthPerms = new String[] {};
		authenticationStrategy = new OAuth2(config, ENDPOINTS);
		authenticationStrategy.setPermission(scope);
		authenticationStrategy.setScope(getScope());

		PROFILE_URL = "http://api.renren.com/restserver.do?method=";
	}

	@SuppressWarnings("unchecked")
	protected Profile authLogin() throws Exception {

		// 请求人人网开放平台API服务器的地址
		String url = "http://api.renren.com/restserver.do";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("method", "users.getLoggedInUser");
		map.put("v", "1.0");
		map.put("format", "json");
		map.put("access_token", accessGrant.getKey());
		
		// 上述的签名
		String signature = getSignature(map, config.get_consumerSecret());
		map.put("sig", signature);
		map.remove("access_token");

		String presp;
		try {
			Response response = authenticationStrategy.executeFeed(url, "POST", map, null, null);
			presp = response.getResponseBodyAsString(Constants.ENCODING);
		} catch (Exception e) {
			throw new SocialAuthException("Error while getting profile from "
					+ PROFILE_URL, e);
		}
		Map<String, Object> data = (Map<String, Object>) Json.fromJson(presp);
		if (data.get("uid") != null) {
			Profile profile = new Profile();
			profile.setValidatedId(data.get("uid").toString());
			profile.setProviderId(getProviderId());
			userProfile = profile;
			return profile;
		}
		throw new SocialAuthException("Auth fail : " + Json.toJson(data));
	}

	public String getSignature(Map<String, String> paramMap, String secret) {
		List<String> paramList = new ArrayList<String>(paramMap.size());
		// 1、参数格式化
		for (Map.Entry<String, String> param : paramMap.entrySet()) {
			paramList.add(param.getKey() + "=" + param.getValue());
		}
		// 2、排序并拼接成一个字符串
		Collections.sort(paramList);
		StringBuffer buffer = new StringBuffer();
		for (String param : paramList) {
			buffer.append(param);
		}
		// 3、追加script key
		buffer.append(secret);
		// 4、将拼好的字符串转成MD5值
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			StringBuffer result = new StringBuffer();
			try {
				for (byte b : md.digest(buffer.toString().getBytes("UTF-8"))) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			} catch (UnsupportedEncodingException e) {
				for (byte b : md.digest(buffer.toString().getBytes())) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			}
			return result.toString();
		} catch (java.security.NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}