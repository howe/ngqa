package org.nutz.ngqa.module.openid;

import lombok.Data;

@Data
public class QQUser {

	private int ret;
	private int errcode	            ;//       二级错误码，详见：【QQ登录】微博私有返回码说明。
	private String msg	                ;//   如果ret不为0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
	private String data	            ;//       登录用户的详细信息列表。
	private String name	            ;//       登录用户的帐号名。
	private String openid	            ;//       登录用户的唯一ID，与QQ号码一一对应。
	private String nick	            ;//       登录用户昵称。
	private String head	            ;//       登录用户头像url。
	private String location	        ;//           登录用户所在地。
	private String isvip	            ;//       登录用户是否为微博认证用户（0：不是；                   1：是）。
	private String isent	            ;//       登录用户是否为企业机构（0：不是；                   1：是）。
	private String introduction	    ;//               登录用户的个人介绍。
	private String verifyinfo	        ;//           认证信息。
	private int birth_year	        ;//           登录用户出生年。
	private int birth_month	        ;//           登录用户出生月份。
	private int birth_day	        ;//           登录用户出生日。
	private String country_code	    ;//               登录用户所在的国家代码。
	private String province_code	    ;//               登录用户所在的省代码。
	private String city_code	        ;//           登录用户所在的城市代码。
	private int sex	                ;//   登录用户性别（1：男；                   2：女；                   3：未知）。
	private String fansnum	            ;//       登录用户听众数。
	private String idolnum	            ;//       登录用户收听的人数。
	private String tweetnum	        ;//           登录用户发表的微博数。
	private String tag	                ;//   标签信息列表。
	private String id	                ;//   个人标签id。
	private String tagname	            ;//       标签名。
	private String edu	                ;//   登录用户教育信息列表。
	private String eduid	                ;//   学历记录ID。
	private String year	            ;//       入学年。
	private String schoolid	        ;//           学校ID。
	private String departmentid	    ;//               院系id。
	private String level	            ;//       学历级别。
	private String email	            ;//       用户注册的邮箱。
}
