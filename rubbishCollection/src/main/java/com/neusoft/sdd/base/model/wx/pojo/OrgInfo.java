/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :OrgInfo.java
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
public class OrgInfo {
	private String name;
	private String parentid;
	private String order;
	private String id;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
