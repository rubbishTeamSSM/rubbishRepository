package com.neusoft.sdd.businessConsole.department.model;

import com.neusoft.sdd.base.model.BaseModelSupport;
/**
 * 部门
 * @author ccdt
 *
 */
public class Department extends BaseModelSupport {
	private String UUID;//主键
	private String DEPT_CODE;//部门代码
	private String PARENT_DEPT_CODE;//上级部门代码
	private String AUTH_CODE;//部门职能范围代码
	private String DEL_FLAG;//作废标记
	private String DEPT_NAME;//部门名称
	private String DEPT_NAME_J;//部门名称简
	private String MGR_USER_CODE;//部门负责人用户代码
	private String REMARK;//备注
	private String CREATED_BY;//录入人员代码
	private String UPDATED_BY;
	private String UPDATED_TIME;
	private String CREATED_TIME;
	private String[] DEPT_CODES;
	private String SORT_NO;
	private String PARENT_AUTH_CODE;
	public String getUPDATED_TIME() {
		return UPDATED_TIME;
	}

	public void setUPDATED_TIME(String uPDATED_TIME) {
		UPDATED_TIME = uPDATED_TIME;
	}

	public String getCREATED_TIME() {
		return CREATED_TIME;
	}

	public void setCREATED_TIME(String cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}

	

	public String getPARENT_AUTH_CODE() {
		return PARENT_AUTH_CODE;
	}

	public void setPARENT_AUTH_CODE(String pARENT_AUTH_CODE) {
		PARENT_AUTH_CODE = pARENT_AUTH_CODE;
	}

	public String getSORT_NO() {
		return SORT_NO;
	}

	public void setSORT_NO(String sORT_NO) {
		SORT_NO = sORT_NO;
	}

	public String[] getDEPT_CODES() {
		return DEPT_CODES;
	}

	public void setDEPT_CODES(String[] dEPT_CODES) {
		DEPT_CODES = dEPT_CODES;
	}

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getDEPT_CODE() {
		return DEPT_CODE;
	}

	public void setDEPT_CODE(String DEPT_CODE) {
		this.DEPT_CODE = DEPT_CODE;
	}

	public String getPARENT_DEPT_CODE() {
		return PARENT_DEPT_CODE;
	}

	public void setPARENT_DEPT_CODE(String PARENT_DEPT_CODE) {
		this.PARENT_DEPT_CODE = PARENT_DEPT_CODE;
	}

	public String getAUTH_CODE() {
		return AUTH_CODE;
	}

	public void setAUTH_CODE(String AUTH_CODE) {
		this.AUTH_CODE = AUTH_CODE;
	}

	public String getDEL_FLAG() {
		return DEL_FLAG;
	}

	public void setDEL_FLAG(String DEL_FLAG) {
		this.DEL_FLAG = DEL_FLAG;
	}

	public String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public void setDEPT_NAME(String DEPT_NAME) {
		this.DEPT_NAME = DEPT_NAME;
	}

	public String getDEPT_NAME_J() {
		return DEPT_NAME_J;
	}

	public void setDEPT_NAME_J(String DEPT_NAME_J) {
		this.DEPT_NAME_J = DEPT_NAME_J;
	}

	public String getMGR_USER_CODE() {
		return MGR_USER_CODE;
	}

	public void setMGR_USER_CODE(String MGR_USER_CODE) {
		this.MGR_USER_CODE = MGR_USER_CODE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}

	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String CREATED_BY) {
		this.CREATED_BY = CREATED_BY;
	}

}
