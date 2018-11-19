package com.neusoft.sdd.base.model.wx.dto;

/**
 * 
 * [简要描述]:公众号用户信息<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015-7-6
 * @since Neusoft 001
 */
public class APPUser {
	private String subscribe;// 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private String openid;// openID
	private String nickname;// 昵称
	private String sex;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String city;// 所在城市
	private String country;// 用户所在国家
	private String province;// 用户所在省份
	private String language;// 用户的语言，简体中文为zh_CN
	private String headimgurl;// 用户头像
	private String unionid;// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	private String access_token;// 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	private String expires_in;// access_token接口调用凭证超时时间，单位（秒）
	private String refresh_token;// 用户刷新access_token
	private String scope;// 用户授权的作用域，使用逗号（,）分隔
	private String errorcode;// 错误码

	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
}
