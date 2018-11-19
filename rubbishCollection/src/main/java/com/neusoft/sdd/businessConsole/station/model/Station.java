package com.neusoft.sdd.businessConsole.station.model;

import com.neusoft.sdd.base.model.BaseModelSupport;

/**
 * 岗位
 * 
 * @author ccdt
 *
 */
public class Station extends BaseModelSupport {
	private String UUID;
	private String POST_CODE;// 岗位代码
	private String POST_NAME;// 岗位名称
	private String POST_NAME_J;// 岗位名称简
	//private String DEPT_CODE;// 部门代码
	//private String DEPT_NAME;// 部门名称
	private String CREATED_BY;// 录入人员代码
	private String IS_QUALITY;//是否品质部
	private String IS_400SERVICE;
	private String DEL_FLAG;//作废标记
	private String SORT_NO;
	private String UPDATED_BY;
	private String PARENT_POST_CODE;
	
	
	public String getPARENT_POST_CODE() {
		return PARENT_POST_CODE;
	}

	public void setPARENT_POST_CODE(String pARENT_POST_CODE) {
		PARENT_POST_CODE = pARENT_POST_CODE;
	}

	public String getIS_QUALITY() {
		return IS_QUALITY;
	}

	public void setIS_QUALITY(String iS_QUALITY) {
		IS_QUALITY = iS_QUALITY;
	}

	public String getIS_400SERVICE() {
		return IS_400SERVICE;
	}

	public void setIS_400SERVICE(String iS_400SERVICE) {
		IS_400SERVICE = iS_400SERVICE;
	}

	public String getDEL_FLAG() {
		return DEL_FLAG;
	}

	public void setDEL_FLAG(String dEL_FLAG) {
		DEL_FLAG = dEL_FLAG;
	}

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

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getPOST_CODE() {
		return POST_CODE;
	}

	public void setPOST_CODE(String POST_CODE) {
		this.POST_CODE = POST_CODE;
	}

	public String getPOST_NAME() {
		return POST_NAME;
	}

	public void setPOST_NAME(String POST_NAME) {
		this.POST_NAME = POST_NAME;
	}

	public String getPOST_NAME_J() {
		return POST_NAME_J;
	}

	public void setPOST_NAME_J(String POST_NAME_J) {
		this.POST_NAME_J = POST_NAME_J;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}

	public void setCREATED_BY(String CREATED_BY) {
		this.CREATED_BY = CREATED_BY;
	}

}
