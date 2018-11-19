/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :UserInfo.java
 * 创建人  :Administrator
 * 创建时间:2015年5月20日
 */

package com.neusoft.sdd.base.model.wx.pojo;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月20日
 * @since Neusoft 001
 */
public class UserInfo {
	private String userid;
	private String name;
	private String[] department;

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getDepartment() {
		return department;
	}
	public void setDepartment(String[] department) {
		this.department = department;
	}

}
