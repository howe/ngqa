package org.nutz.socialauth.alipay;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.OAuthConfig;
import org.nutz.socialauth.AbstractOAuthProvider;
import org.nutz.socialauth.alipay.config.AlipayConfig;
import org.nutz.socialauth.alipay.util.AlipayNotify;
import org.nutz.socialauth.alipay.util.AlipaySubmit;

@SuppressWarnings("serial")
public class AlipayAuthProvider extends AbstractOAuthProvider {
	
	static String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
	public AlipayAuthProvider(OAuthConfig providerConfig) {
		super(providerConfig);
		AlipayConfig.partner = providerConfig.get_consumerKey();
		AlipayConfig.key = providerConfig.get_consumerSecret();
		//System.out.println(AlipayConfig.partner);
		//System.out.println(AlipayConfig.key);
	}
	
	@Override
	public String getLoginRedirectURL(String successUrl) throws Exception {
		//防钓鱼时间戳
		String anti_phishing_key  = "";
		//获取客户端的IP地址，建议：编写获取客户端IP地址的程序
		String exter_invoke_ip= "";
		
		//anti_phishing_key = AlipayService.query_timestamp();
		
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        
        sParaTemp.put("service", "alipay.auth.authorize");
        sParaTemp.put("target_service", "user.auth.quick.login");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("return_url", successUrl);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        
        sParaTemp = AlipaySubmit.buildRequestPara(sParaTemp);
        
        StringBuilder sb = new StringBuilder();
        sb.append(ALIPAY_GATEWAY_NEW);
        for (Entry<String, String> entry : sParaTemp.entrySet()) {
			sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF8")).append("&");
		}       
		return sb.toString();
	}
	
	@Override
	protected Profile doVerifyResponse(Map<String, String> requestParams)
			throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		//Map requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String valueStr = requestParams.get(name);
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}
		//支付宝用户id
		String user_id = requestParams.get("user_id");
		//授权令牌
		//String token = requestParams.get("token");
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		if(verify_result){//验证成功
			Profile profile = new Profile();
			profile.setValidatedId(user_id);
			profile.setProviderId(getProviderId());
			profile.setEmail(requestParams.get("email"));
			userProfile = profile;
			return profile;
		}
		throw new SocialAuthException("Auth fail!!");
	}

	protected Profile authLogin() throws Exception {
		return null;
	}

}
