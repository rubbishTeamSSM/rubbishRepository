/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :TextMsg.java
 * 创建人  :Administrator
 * 创建时间:2015年5月21日
 */

package com.neusoft.sdd.base.model.wx.pojo.message;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月21日
 * @since Neusoft 001
 */
public class AbstractMsg {

	protected String touser;
	protected String toparty;
	protected String totag;
	protected String msgtype;
	protected String agentid;
	protected String safe;

	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTopaty() {
		return toparty;
	}
	public void setTopaty(String topaty) {
		this.toparty = topaty;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getSafe() {
		return safe;
	}
	public void setSafe(String safe) {
		this.safe = safe;
	}
}
