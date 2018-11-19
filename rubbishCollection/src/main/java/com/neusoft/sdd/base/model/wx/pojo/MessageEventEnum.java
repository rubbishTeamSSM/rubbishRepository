/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :MessageEventEnum.java
 * 创建人  :Administrator
 * 创建时间:2015年5月28日
 */

package com.neusoft.sdd.base.model.wx.pojo;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月28日
 * @since Neusoft 001
 */
public enum MessageEventEnum {
	subscribe,
    LOCATION,
    CLICK,
    VIEW,
    scancode_push,
    scancode_waitmsg,
    pic_sysphoto,
    pic_photo_or_album,
    pic_weixin,
    location_select,
    enter_agent,
    batch_job_result,
    unsubscribe;
}
