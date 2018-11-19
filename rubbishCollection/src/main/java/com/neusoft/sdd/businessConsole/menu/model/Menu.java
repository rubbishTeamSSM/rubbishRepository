package com.neusoft.sdd.businessConsole.menu.model;

import com.neusoft.sdd.base.model.BaseModelSupport;

public class Menu extends BaseModelSupport {
	private String UUID;
	private String MENU_CODE;// 菜单代码
	private String MENU_NAME;// 菜单名称
	private String MENU_URL;// 菜单URL
	private String PARENT_MENU_CODE;// 父菜代码
	private String TYPE;// 类型 1代表菜单 2 代表目录
	private int SORT_NO;// 显示顺序
	private String DEL_FLAG;// 作废标记
	private String REMARK;// 备注
	private String CREATED_BY;// 录入人员代码
	private String UPDATED_BY;//修改人员代码
	private  int LEVEL;//菜单层次
	private String AUTH_CODE;//菜单范围代码
	private String PARENT_AUTH_CODE;//上级菜单范围代码
	private String[] MENU_CODES;//菜单代码数组
	

	public String[] getMENU_CODES() {
		return MENU_CODES;
	}

	public void setMENU_CODES(String[] mENU_CODES) {
		MENU_CODES = mENU_CODES;
	}

	public String getUPDATED_BY() {
		return UPDATED_BY;
	}

	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}

	public String getPARENT_AUTH_CODE() {
		return PARENT_AUTH_CODE;
	}

	public void setPARENT_AUTH_CODE(String pARENT_AUTH_CODE) {
		PARENT_AUTH_CODE = pARENT_AUTH_CODE;
	}

	public String getAUTH_CODE() {
		return AUTH_CODE;
	}

	public void setAUTH_CODE(String aUTH_CODE) {
		AUTH_CODE = aUTH_CODE;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getMENU_CODE() {
		return MENU_CODE;
	}

	public void setMENU_CODE(String MENU_CODE) {
		this.MENU_CODE = MENU_CODE;
	}

	public String getMENU_NAME() {
		return MENU_NAME;
	}

	public void setMENU_NAME(String MENU_NAME) {
		this.MENU_NAME = MENU_NAME;
	}

	public String getMENU_URL() {
		return MENU_URL;
	}

	public void setMENU_URL(String MENU_URL) {
		this.MENU_URL = MENU_URL;
	}

	public String getPARENT_MENU_CODE() {
		return PARENT_MENU_CODE;
	}

	public void setPARENT_MENU_CODE(String PARENT_MENU_CODE) {
		this.PARENT_MENU_CODE = PARENT_MENU_CODE;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String TYPE) {
		this.TYPE = TYPE;
	}

	public int getSORT_NO() {
		return SORT_NO;
	}

	public void setSORT_NO(int SORT_NO) {
		this.SORT_NO = SORT_NO;
	}

	public String getDEL_FLAG() {
		return DEL_FLAG;
	}

	public void setDEL_FLAG(String DEL_FLAG) {
		this.DEL_FLAG = DEL_FLAG;
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
	
	public int getLEVEL() {
		return LEVEL;
	}

	public void setLEVEL(int LEVEL) {
		this.LEVEL = LEVEL;
	}
	
	
	
	

}
