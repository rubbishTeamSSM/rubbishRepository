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
public class JobInfo extends BaseModelSupport{
	private String UUID;//UUID
	
	private String JOB_CODE;//任务代码
	
	private String JOB_NAME;//任务名称
	
	private String EXEC_CLASS_CODE;//执行类代码
	
	private String CRON_EXPRESS;//时间表达式（克隆表达式）
	
	private String DEL_FLAG;//状态标记(默认0未启用，1启用，2作废)
	
	private String EXEC_FLAG;//执行标记(默认0未执行，1执行中)
		
	private String CREATED_BY;//录入人员代码
	
	private String EXEC_CLASS_NAME;//执行类名称
	
	private String SORT_NO;//序号
	
	private String UPDATED_BY;//修改人
	
    private String EXEC_CLASS;
	
	private String cSEXj;
	
	private String NEXT_EXEC_DATE;//下次执行时间
	
	
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

	
	public String getJOB_CODE() {
		return JOB_CODE;
	}

	public void setJOB_CODE(String jOB_CODE) {
		JOB_CODE = jOB_CODE;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getCSEXj() {
		return cSEXj;
	}

	public void setCSEXj(String cSEXj) {
		this.cSEXj = cSEXj;
	}

	public String getNEXT_EXEC_DATE() {
		return NEXT_EXEC_DATE;
	}

	public void setNEXT_EXEC_DATE(String NEXT_EXEC_DATE) {
		this.NEXT_EXEC_DATE = NEXT_EXEC_DATE;
	}


	public String getEXEC_CLASS_NAME() {
		return EXEC_CLASS_NAME;
	}

	public void setEXEC_CLASS_NAME(String EXEC_CLASS_NAME) {
		this.EXEC_CLASS_NAME = EXEC_CLASS_NAME;
	}

	public String getEXEC_CLASS() {
		return EXEC_CLASS;
	}

	public void setEXEC_CLASS(String EXEC_CLASS) {
		this.EXEC_CLASS = EXEC_CLASS;
	}


	public String getJOB_NAME() {
		return JOB_NAME;
	}

	public void setJOB_NAME(String JOB_NAME) {
		this.JOB_NAME = JOB_NAME;
	}

	public String getEXEC_CLASS_CODE() {
		return EXEC_CLASS_CODE;
	}

	public void setEXEC_CLASS_CODE(String EXEC_CLASS_CODE) {
		this.EXEC_CLASS_CODE = EXEC_CLASS_CODE;
	}

	public String getCRON_EXPRESS() {
		return CRON_EXPRESS;
	}

	public void setCRON_EXPRESS(String CRON_EXPRESS) {
		this.CRON_EXPRESS = CRON_EXPRESS;
	}
	public String getDEL_FLAG() {
		return DEL_FLAG;
	}

	public void setDEL_FLAG(String DEL_FLAG) {
		this.DEL_FLAG = DEL_FLAG;
	}

	public String getEXEC_FLAG() {
		return EXEC_FLAG;
	}

	public void setEXEC_FLAG(String EXEC_FLAG) {
		this.EXEC_FLAG = EXEC_FLAG;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String CREATED_BY) {
		this.CREATED_BY = CREATED_BY;
	}

	

}
