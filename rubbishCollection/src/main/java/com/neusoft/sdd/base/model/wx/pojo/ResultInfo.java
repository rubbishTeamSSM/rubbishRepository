/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :ResultInfo.java
 * 创建人  :Administrator
 * 创建时间:2015年5月20日
 */

package com.neusoft.sdd.base.model.wx.pojo;

import java.util.List;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月20日
 * @since Neusoft 001
 */
public class ResultInfo {
	private String errcode;
	private String errmsg;
	private List<UserInfo> userlist;

	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public List<UserInfo> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<UserInfo> userlist) {
		this.userlist = userlist;
	}

}
