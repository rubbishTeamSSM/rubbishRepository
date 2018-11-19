/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :Article.java
 * 创建人  :Administrator
 * 创建时间:2015年5月22日
 */

package com.neusoft.sdd.base.model.wx.pojo.message;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月22日
 * @since Neusoft 001
 */
public class Article {
	private String title;// 图文消息的标题

	private String description;// 图文消息的描述

	private String url;// 点击后的url

	private String picurl;// 图片的url

	private String thumb_media_id;// 图文消息缩略图的media_id

	private String author;

	private String content;

	private String content_source_url;

	private String digest;

	private String show_cover_pic;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

}
