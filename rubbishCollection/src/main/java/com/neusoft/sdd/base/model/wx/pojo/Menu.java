/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :Menu.java
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
public class Menu {
	private String name;
	private String key;
	private String type;
	private String url;
	private List<ChildMenu> sub_button;

	public List<ChildMenu> getSub_button() {
		return sub_button;
	}
	public void setSub_button(List<ChildMenu> sub_button) {
		this.sub_button = sub_button;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
