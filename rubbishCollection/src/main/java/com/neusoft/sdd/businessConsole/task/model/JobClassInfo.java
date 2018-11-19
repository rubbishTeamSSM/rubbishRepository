package com.neusoft.sdd.businessConsole.task.model;

/**
 * 
 * [简要描述]:定时任务执行类信息<br/>
 * [详细描述]:<br/>
 *
 * @version [revision],2015-7-7
 * @since Neusoft 001
 */
public class JobClassInfo {
	private String UUID;//UUID
	
	private String EXEC_CLASS_CODE;//执行类代码
	
	private String EXEC_CLASS_NAME;//执行类名称
	
	private String EXEC_CLASS;//执行类
	
	private String DEL_FLAG;//作废标记(默认是0未作废，1作废)
	
	private String CREATED_BY;//录入人员代码
	
	private String cSEXj;
	
	

	public String getEXEC_CLASS_CODE() {
		return EXEC_CLASS_CODE;
	}

	public void setEXEC_CLASS_CODE(String eXEC_CLASS_CODE) {
		EXEC_CLASS_CODE = eXEC_CLASS_CODE;
	}

	public String getCSEXj() {
		return cSEXj;
	}

	public void setCSEXj(String cSEXj) {
		this.cSEXj = cSEXj;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String CREATED_BY) {
		this.CREATED_BY = CREATED_BY;
	}


	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
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

	public String getDEL_FLAG() {
		return DEL_FLAG;
	}

	public void setDEL_FLAG(String DEL_FLAG) {
		this.DEL_FLAG = DEL_FLAG;
	}

}
