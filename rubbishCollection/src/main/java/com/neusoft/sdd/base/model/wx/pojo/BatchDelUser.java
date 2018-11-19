/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :BatchDelUser.java
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
public class BatchDelUser {
	private List<String> useridlist;

	public List<String> getUseridlist() {
		return useridlist;
	}

	public void setUseridlist(List<String> useridlist) {
		this.useridlist = useridlist;
	}

}
