/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :NewsMsg.java
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
public class NewsMsg extends AbstractMsg {
	private Map<String, Article[]> news;

	public Map<String, Article[]> getNews() {
		return news;
	}

	public void setNews(Map<String, Article[]> news) {
		this.news = news;
	}

}
