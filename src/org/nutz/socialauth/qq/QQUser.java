package org.nutz.socialauth.qq;

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
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getIsvip() {
		return isvip;
	}
	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}
	public String getIsent() {
		return isent;
	}
	public void setIsent(String isent) {
		this.isent = isent;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getVerifyinfo() {
		return verifyinfo;
	}
	public void setVerifyinfo(String verifyinfo) {
		this.verifyinfo = verifyinfo;
	}
	public int getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}
	public int getBirth_month() {
		return birth_month;
	}
	public void setBirth_month(int birth_month) {
		this.birth_month = birth_month;
	}
	public int getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(int birth_day) {
		this.birth_day = birth_day;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getFansnum() {
		return fansnum;
	}
	public void setFansnum(String fansnum) {
		this.fansnum = fansnum;
	}
	public String getIdolnum() {
		return idolnum;
	}
	public void setIdolnum(String idolnum) {
		this.idolnum = idolnum;
	}
	public String getTweetnum() {
		return tweetnum;
	}
	public void setTweetnum(String tweetnum) {
		this.tweetnum = tweetnum;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public String getEduid() {
		return eduid;
	}
	public void setEduid(String eduid) {
		this.eduid = eduid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
