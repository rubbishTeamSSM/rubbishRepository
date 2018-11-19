package com.neusoft.sdd.businessConsole.task.model;

import com.neusoft.sdd.base.model.BaseModelSupport;

/**
 * 
 * [简要描述]:定时任务运行记录<br/>
 * [详细描述]:<br/>
 *
 * @version [revision],2015-7-7
 * @since Neusoft 001
 */
public class JobExec extends BaseModelSupport{
	private String UUID;//UUID
	
	private String JOB_CODE;//任务代码
	
	private String START_TIME;//执行开始时间
	
	private String END_TIME;//执行结束时间
	
	private String SUCCESS_FLAG;//成功标记(默认0未成功，1成功)
	
	private String LOG_INFO;//日志信息（填写执行成功或者失败的信息）
	
	private String CREATED_BY;//录入时间
	
	private String JOB_NAME;//任务名称
	
	private String SORT_NO;//序号
	
	private String UPDATED_BY;
	

	public String getSORT_NO() {
		return SORT_NO;
	}

	public void setSORT_NO(String sORT_NO) {
		SORT_NO = sORT_NO;
	}

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}

	public String getJOB_NAME() {
		return JOB_NAME;
	}

	public void setJOB_NAME(String JOB_NAME) {
		this.JOB_NAME = JOB_NAME;
	}


	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getJOB_CODE() {
		return JOB_CODE;
	}

	public void setJOB_CODE(String JOB_CODE) {
		this.JOB_CODE = JOB_CODE;
	}

	public String getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(String START_TIME) {
		this.START_TIME = START_TIME;
	}

	public String getEND_TIME() {
		return END_TIME;
	}

	public void setEND_TIME(String END_TIME) {
		this.END_TIME = END_TIME;
	}

	public String getSUCCESS_FLAG() {
		return SUCCESS_FLAG;
	}

	public void setSUCCESS_FLAG(String SUCCESS_FLAG) {
		this.SUCCESS_FLAG = SUCCESS_FLAG;
	}

	public String getLOG_INFO() {
		return LOG_INFO;
	}

	public void setLOG_INFO(String LOG_INFO) {
		this.LOG_INFO = LOG_INFO;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String CREATED_BY) {
		this.CREATED_BY = CREATED_BY;
	}


}
