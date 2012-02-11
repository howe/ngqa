package org.nutz.ngqa.module.openid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.brickred.socialauth.AbstractProvider;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Permission;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.AccessGrant;
import org.brickred.socialauth.util.OAuthConfig;
import org.brickred.socialauth.util.Response;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.xml.sax.SAXException;

import com.qq.connect.AccessToken;
import com.qq.connect.InfoToken;
import com.qq.connect.RequestToken;
import com.qq.oauth.Config;
import com.qq.util.ParseString;

/**
 * 实现QQ帐号登录
 * @代码作者：liux http://my.oschina.net/liux
 * @代码出处：http://www.oschina.net/
 * @date 2011-12-28 下午5:42:15
 */
public class QQAuthProvider extends AbstractProvider implements AuthProvider {

    private final Log LOG = LogFactory.getLog(QQAuthProvider.class);
    
	private final static String ID = "qq";
	private String OAUTH_TOKEN_SECRET; 

    private Profile userProfile;
    private QQUser userInfo;

	public QQAuthProvider(final OAuthConfig providerConfig) {}

	@Override
	public Response api(final String url, final String methodType,
            final Map<String, String> params,
            final Map<String, String> headerParams, final String body) throws Exception {
		return null;
	}

	@Override
	public AccessGrant getAccessGrant() {
		return null;
	}

	@Override
	public List<Contact> getContactList() throws Exception {
		return null;
	}

	@Override
	public String getLoginRedirectURL(final String successUrl) throws Exception {
		RequestToken requesttoken = new RequestToken();
        String request_token = null;
        try {
            request_token = requesttoken.getRequestToken();
        } catch (Exception e) {
        	LOG.error("Failed to get RequestToken.", e);
        	throw new SocialAuthException("Failed to get RequestToken.",e);
        }
        HashMap<String, String> tokens = ParseString.parseTokenString(request_token);
        OAUTH_TOKEN_SECRET = tokens.get("oauth_token_secret");
		return getRedirectURL(successUrl, tokens, null);
	}

	/**
	 * 重写 RedirectToken 中的方法
	 * @param successUrl
	 * @param tokens
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public String getRedirectURL(String successUrl, Map<String, String> tokens, List<NameValuePair> parameters) 
    		throws UnsupportedEncodingException {
        String url = "http://openapi.qzone.qq.com/oauth/qzoneoauth_authorize";
        StringBuffer redirect_url = new StringBuffer(url);
        redirect_url.append("?oauth_consumer_key=").append(Config.APP_ID);
        String oauth_token = tokens.get("oauth_token");
        redirect_url.append("&oauth_token=").append(oauth_token);
        redirect_url.append("&oauth_callback=").append(encode(successUrl));
        if (parameters != null && parameters.size() != 0) {
            for (int i = 0; i < parameters.size(); i++) {
                NameValuePair p = parameters.get(i);
                redirect_url.append("&").append(encode(p.getName())).append("=").append(encode(p.getValue()));
            }
        }
        return redirect_url.toString();
    }

    private String encode(String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, "UTF-8");
    }
    
	@Override
	public String getProviderId() {
		return ID;
	}

	@Override
	public Profile getUserProfile() throws Exception {
		if (userProfile == null && userInfo != null) {	
			userProfile = getUserInfo(userInfo);
		}
		return userProfile;
	}
	
	private Profile getUserInfo(QQUser user) {
		Profile p = new Profile();
		p.setEmail(user.getOpenid());
		p.setFirstName(userInfo.getNickname());
		p.setGender(userInfo.getGender());
		p.setProfileImageURL(userInfo.getFigureurl());
		p.setValidatedId(user.getOpenid());
		p.setProviderId(getProviderId());
		return p;
	}

	@Override
	public void logout() {
		userInfo = null;
	}

	@Override
	public void setAccessGrant(AccessGrant accessGrant) throws Exception {
		
	}

	@Override
	public void setPermission(Permission p) {

	}

	@Override
	public void updateStatus(String arg0) throws Exception {

	}

	@Override
	public Profile verifyResponse(HttpServletRequest request) throws Exception {
		Map<String, String> params = SocialAuthUtil
				.getRequestParametersMap(request);
		return doVerifyResponse(params);
	}

	@Override
	public Profile verifyResponse(Map<String, String> requestParams) throws Exception {
		return doVerifyResponse(requestParams);
	}

	private Profile doVerifyResponse(final Map<String, String> requestParams) throws Exception{
        String oauth_token = requestParams.get("oauth_token");
        String openid = requestParams.get("openid");
        String oauth_vericode = requestParams.get("oauth_vericode");

        // 用授权的request token换取access token
        AccessToken token = new AccessToken();
        String oauth_token_secret = OAUTH_TOKEN_SECRET;

        String access_token = null;
        try {
            access_token = token.getAccessToken(oauth_token, oauth_token_secret, oauth_vericode);
        } catch (Exception e) {
        	LOG.error("Failed to get AccessToken.", e);
        	throw new SocialAuthException("Failed to get AccessToken.",e);
        }

        HashMap<String, String> tokens = ParseString.parseTokenString(access_token);

        // 将access token，openid保存!!
        oauth_token = tokens.get("oauth_token");
        oauth_token_secret = tokens.get("oauth_token_secret");
        openid = tokens.get("openid");

        InfoToken infotoken = new InfoToken();
        // 用户信息xml
        String userinfo_xml = null;
        try {
        	userinfo_xml = infotoken.getInfo(oauth_token, oauth_token_secret, openid, "xml");
        } catch (Exception e) {
        	throw new SocialAuthException("Failed to get userinfo xml.",e);
        }

        try {
        	userInfo = parse_qquser(userinfo_xml);
        }  catch  (Exception e) {  
        	LOG.error("Failed to parse userinfo xml.", e);
        	throw new SocialAuthException("Failed to parse userinfo xml.",e);
        }
        if(userInfo == null) userInfo = new QQUser();
        userInfo.setOpenid(openid);
		userProfile = getUserInfo(userInfo);		
		return userProfile;
	}
	
	private QQUser parse_qquser(String userinfo_xml) throws IOException,SAXException{
		Digester digester = new Digester();
        digester.push(new QQUser());
        digester.setValidating(false);
        digester.addBeanPropertySetter("data/ret");
        digester.addBeanPropertySetter("data/msg");
        digester.addBeanPropertySetter("data/nickname");
        digester.addBeanPropertySetter("data/figureurl");
        digester.addBeanPropertySetter("data/figureurl_1");
        digester.addBeanPropertySetter("data/figureurl_2");
        digester.addBeanPropertySetter("data/gender");
        return (QQUser)digester.parse(new ByteArrayInputStream(userinfo_xml.getBytes("utf-8")));
	}
	
	static class QQUser {

		private String openid;
		
		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

		private  String ret;  
		private  String msg;  
		private  String nickname;  
		private  String figureurl;  
		private  String figureurl_1;  
		private  String figureurl_2;  
		private  String gender;

		public String getRet() {
			return ret;
		}

		public void setRet(String ret) {
			this.ret = ret;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getFigureurl() {
			return figureurl;
		}

		public void setFigureurl(String figureurl) {
			this.figureurl = figureurl;
		}

		public String getFigureurl_1() {
			return figureurl_1;
		}

		public void setFigureurl_1(String figureurl_1) {
			this.figureurl_1 = figureurl_1;
		}

		public String getFigureurl_2() {
			return figureurl_2;
		}

		public void setFigureurl_2(String figureurl_2) {
			this.figureurl_2 = figureurl_2;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}  


	}

}