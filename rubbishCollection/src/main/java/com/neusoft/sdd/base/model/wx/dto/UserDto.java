/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :UserDto.java
 * 创建人  :Administrator
 * 创建时间:2015年5月20日
 */

package com.neusoft.sdd.base.model.wx.dto;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月20日
 * @since Neusoft 001
 */
public class UserDto {
	private String id;
	private String name;
	private String system;
	private String department;
	private String station;
	private String phone;
	private String email;
	private String avatar;

	// 获取用户部门，岗位信息使用字段
	private String bm_dm;// 部门代码
	private String bm_mc;// 部门名称
	private String gw_dm;// 岗位代码

	// 访客使用字段
	private String openId;// openid

	public String getBm_dm() {
		return bm_dm;
	}
	public void setBm_dm(String bm_dm) {
		this.bm_dm = bm_dm;
	}
	public String getBm_mc() {
		return bm_mc;
	}
	public void setBm_mc(String bm_mc) {
		this.bm_mc = bm_mc;
	}
	public String getGw_dm() {
		return gw_dm;
	}
	public void setGw_dm(String gw_dm) {
		this.gw_dm = gw_dm;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id :[").append(this.id).append("]").append("name :[")
				.append(this.name).append("]").append("email :[")
				.append(this.email).append("]").append("phone :[")
				.append(this.phone).append("]");
		return sb.toString();
	}
}
