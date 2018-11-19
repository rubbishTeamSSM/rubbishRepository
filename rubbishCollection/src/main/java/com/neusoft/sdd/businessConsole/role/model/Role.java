package com.neusoft.sdd.businessConsole.role.model;

import java.io.Serializable;

import com.neusoft.sdd.base.model.BaseModelSupport;

public class Role extends BaseModelSupport implements Serializable{
	private static final long serialVersionUID = -9114852567146541787L;
	
	private String role_code;//角色代码
	private String role_name;//角色名称
	private String uuid;//uuid
	private String remark;//角色备注
	private String sort_no;//序号
	private String del_flag;//作废标记（1作废，0未作废）
	private String created_time;//创建时间
	private String created_by;//创建者(登录账号)
	private String updated_time;//更新时间
	private String updated_by;//更新者(登录账号)
	
	private String default_flag;//用户默认角色
	private String user_code;//用户代码
	
	
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getDefault_flag() {
		return default_flag;
	}
	public void setDefault_flag(String default_flag) {
		this.default_flag = default_flag;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
	@Override
	public String toString() {
		return "Role [role_code=" + role_code + ", role_name=" + role_name
				+ ", uuid=" + uuid + ", remark=" + remark + ", sort_no="
				+ sort_no + ", del_flag=" + del_flag + ", created_time="
				+ created_time + ", created_by=" + created_by
				+ ", updated_time=" + updated_time + ", updated_by="
				+ updated_by + "]";
	}
    
	
}
