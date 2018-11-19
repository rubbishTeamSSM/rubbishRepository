/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :AcceptMsgInfo.java
 * 创建人  :Administrator
 * 创建时间:2015年5月22日
 */

package com.neusoft.sdd.base.model.wx.pojo;

import java.util.Date;

import com.neusoft.sdd.util.wx.DateUtils;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月22日
 * @since Neusoft 001
 */
public class AcceptMsgInfo {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String msgType;
	private String event;
	private String eventKey;
	private String content;
	private String msgId;
	private String agentId;

	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(" 应用ID ========= ")
				.append(this.agentId)
				.append("\n")
				.append(" 发送人帐号 ========= ")
				.append(this.fromUserName)
				.append("\n")
				.append(" 接收人 ========= ")
				.append(this.toUserName)
				.append("\n")
				.append(" 消息ID ========= ")
				.append(this.msgId)
				.append("\n")
				.append(" 应消息内容 ========= ")
				.append(this.content)
				.append("\n")
				.append(" 消息类型 ========= ")
				.append(this.msgType)
				.append("\n")
				.append(" 事件类型========= ")
				.append(this.event)
				.append("\n")
				.append(" 事件KEY值========= ")
				.append(this.eventKey)
				.append("\n")
				.append(" 创建时间 ========= ")
				.append(DateUtils.dateToString(new Date(Long
						.parseLong(createTime)))).append("\n");
		return sb.toString();
	}

}
