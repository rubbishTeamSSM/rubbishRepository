package com.neusoft.sdd.businessConsole.menu.model;

import com.neusoft.sdd.base.model.BaseModelSupport;

/**
 * 菜单按钮
 * @author zhu
 *
 */
public class MenuButton extends BaseModelSupport {
	private String uuid;//
	private String menu_code;//菜单代码
	private String button_code;//按钮代码
	private String button_name;//按钮名称
	private String button_url;//执行方法
	private String button_style;//按钮样式
	private String remark;//备注
	private String sort_no;//序号
	private String del_flag;//作废标记
	private String created_by;//创建者
	private String updated_by;//修改者
	private String role_code;//角色代码
	private String[] button_codes;
	private String[] role_codes;
	private String[] menu_codes;
	
	private String menu_name;//菜单名称
	private String parent_menu_code;//父菜单代码
	private String _parentId;//父ID-treegrid用
	private String auth_code;//菜单范围代码
	private String btn_info;//按钮信息
	private String parent_button_code;//上级按钮代码
	private String level;//按钮层次（项目根节点是0，下面一次+1）
	private String auth_code_btn;//按钮范围代码（用于like'1%'上下级查询，方便mysql）
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMenu_code() {
		return menu_code;
	}
	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}
	public String getButton_code() {
		return button_code;
	}
	public void setButton_code(String button_code) {
		this.button_code = button_code;
	}
	public String getButton_name() {
		return button_name;
	}
	public void setButton_name(String button_name) {
		this.button_name = button_name;
	}
	public String getButton_url() {
		return button_url;
	}
	public void setButton_url(String button_url) {
		this.button_url = button_url;
	}
	public String getButton_style() {
		return button_style;
	}
	public void setButton_style(String button_style) {
		this.button_style = button_style;
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
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public String[] getButton_codes() {
		return button_codes;
	}
	public void setButton_codes(String[] button_codes) {
		this.button_codes = button_codes;
	}
	public String[] getRole_codes() {
		return role_codes;
	}
	public void setRole_codes(String[] role_codes) {
		this.role_codes = role_codes;
	}
	public String[] getMenu_codes() {
		return menu_codes;
	}
	public void setMenu_codes(String[] menu_codes) {
		this.menu_codes = menu_codes;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getParent_menu_code() {
		return parent_menu_code;
	}
	public void setParent_menu_code(String parent_menu_code) {
		this.parent_menu_code = parent_menu_code;
	}
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getBtn_info() {
		return btn_info;
	}
	public void setBtn_info(String btn_info) {
		this.btn_info = btn_info;
	}
	public String getParent_button_code() {
		return parent_button_code;
	}
	public void setParent_button_code(String parent_button_code) {
		this.parent_button_code = parent_button_code;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAuth_code_btn() {
		return auth_code_btn;
	}
	public void setAuth_code_btn(String auth_code_btn) {
		this.auth_code_btn = auth_code_btn;
	}
}
