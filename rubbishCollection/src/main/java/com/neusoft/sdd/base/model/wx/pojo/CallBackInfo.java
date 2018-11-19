/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :CallBackInfo.java
 * 创建人  :Administrator
 * 创建时间:2015年5月19日
 */

package com.neusoft.sdd.base.model.wx.pojo;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月19日
 * @since Neusoft 001
 */
public class CallBackInfo {
	private String url;
	private String token;
	private String encodingaeskey;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEncodingaeskey() {
		return encodingaeskey;
	}
	public void setEncodingaeskey(String encodingaeskey) {
		this.encodingaeskey = encodingaeskey;
	}

}
