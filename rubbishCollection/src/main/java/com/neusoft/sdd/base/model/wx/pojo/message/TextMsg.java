/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :TextMsg.java
 * 创建人  :Administrator
 * 创建时间:2015年5月22日
 */

package com.neusoft.sdd.base.model.wx.pojo.message;

import java.util.Map;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月22日
 * @since Neusoft 001
 */
public class TextMsg extends AbstractMsg {
	private Map<String, String> text;

	public Map<String, String> getText() {
		return text;
	}

	public void setText(Map<String, String> text) {
		this.text = text;
	}

}
