package com.neusoft.sdd.businessConsole.user.model;

import java.io.Serializable;

import com.neusoft.sdd.base.model.BaseModelSupport;

public class User extends BaseModelSupport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String USER_CODE;// 用户代码
	private String USER_ACCNT;//用户登录账号(前台页面录入，全系统唯一)
	private String USER_NICK_NAME;//用户昵称
	private String USER_NAME;// 用户名称
	private String USER_PWD;// 用户密码
	private String ADMIN_FLAG;// 超级管理员标记 1代表是 0 代表否
	private String ID_CARD;// 身份证号码
	private String SEX;// 性别
	private String TEL;// 固定电话
	private String PHONE;//手机号码
	private String EMAIL;// 电子邮件
	private String IP;// IP地址
	private String PIC_URL;//用户头像
	private String POST_CODE;//岗位代码
	private String PARENT_USER_CODE;//上级领导用户代码(自己在后台维护不用人力资源的)
	private String DEPT_CODE;// 主属部门代码
	private String SPECIAL_FLAG;//特殊添加标记（1是，0默认，否），1是后台自己添加不参与人资数据校验登录，0是需要和人资校验登录。
	private String USER_TYPE_CODE;//物业用户类型代码
	private String REMARK;// 备注
	private String DEL_FLAG;// 作废标记 1代表作废 0代表否
	private String UUID;
	private String SORT_NO;// 序号
	private String CREATED_BY;// 录入人员代码
	private String UPDATED_BY;// 修改人员代码
	private String code;//短信验证码
	
	public String getUSER_CODE() {
		return USER_CODE;
	}
	public void setUSER_CODE(String uSER_CODE) {
		USER_CODE = uSER_CODE;
	}
	public String getUSER_ACCNT() {
		return USER_ACCNT;
	}
	public void setUSER_ACCNT(String uSER_ACCNT) {
		USER_ACCNT = uSER_ACCNT;
	}
	public String getUSER_NICK_NAME() {
		return USER_NICK_NAME;
	}
	public void setUSER_NICK_NAME(String uSER_NICK_NAME) {
		USER_NICK_NAME = uSER_NICK_NAME;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getUSER_PWD() {
		return USER_PWD;
	}
	public void setUSER_PWD(String uSER_PWD) {
		USER_PWD = uSER_PWD;
	}
	public String getADMIN_FLAG() {
		return ADMIN_FLAG;
	}
	public void setADMIN_FLAG(String aDMIN_FLAG) {
		ADMIN_FLAG = aDMIN_FLAG;
	}
	public String getID_CARD() {
		return ID_CARD;
	}
	public void setID_CARD(String iD_CARD) {
		ID_CARD = iD_CARD;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPARENT_USER_CODE() {
		return PARENT_USER_CODE;
	}
	public void setPARENT_USER_CODE(String pARENT_USER_CODE) {
		PARENT_USER_CODE = pARENT_USER_CODE;
	}
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}
	public String getSPECIAL_FLAG() {
		return SPECIAL_FLAG;
	}
	public void setSPECIAL_FLAG(String sPECIAL_FLAG) {
		SPECIAL_FLAG = sPECIAL_FLAG;
	}
	public String getUSER_TYPE_CODE() {
		return USER_TYPE_CODE;
	}
	public void setUSER_TYPE_CODE(String uSER_TYPE_CODE) {
		USER_TYPE_CODE = uSER_TYPE_CODE;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getDEL_FLAG() {
		return DEL_FLAG;
	}
	public void setDEL_FLAG(String dEL_FLAG) {
		DEL_FLAG = dEL_FLAG;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getSORT_NO() {
		return SORT_NO;
	}
	public void setSORT_NO(String sORT_NO) {
		SORT_NO = sORT_NO;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	public String getUPDATED_BY() {
		return UPDATED_BY;
	}
	public void setUPDATED_BY(String uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}
	public String getPIC_URL() {
		return PIC_URL;
	}
	public void setPIC_URL(String pIC_URL) {
		PIC_URL = pIC_URL;
	}
	public String getPOST_CODE() {
		return POST_CODE;
	}
	public void setPOST_CODE(String pOST_CODE) {
		POST_CODE = pOST_CODE;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
