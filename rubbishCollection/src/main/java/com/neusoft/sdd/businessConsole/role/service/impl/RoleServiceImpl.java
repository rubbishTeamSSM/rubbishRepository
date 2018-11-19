package com.neusoft.sdd.businessConsole.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseService;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.businessConsole.menu.dao.MenuButtonDao;
import com.neusoft.sdd.businessConsole.menu.model.MenuButton;
import com.neusoft.sdd.businessConsole.role.dao.IRoleDao;
import com.neusoft.sdd.businessConsole.role.model.Role;
import com.neusoft.sdd.businessConsole.role.service.IRoleService;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;

//@Transactional
@Service
public class RoleServiceImpl extends BaseService implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private MenuButtonDao menuButtonDao;

	/**
	 * 部门：软件开发事业部
	 * 功能：获取所有系统的角色
	 * 描述：略
	 * 作成者：尹帅
	 * 作成时间：2017-10-17
	 */
	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取当前用户的所有的角色
	 * 描述：略
	 * 作成者：尹帅
	 * 作成时间：2017-10-17
	 */
	@Override
	public List<Role> getUserAllRoles(String USER_CODE) {
		return roleDao.getUserAllRoles(USER_CODE);
	}
	/**
	 * 设置用户默认角色
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-18
	 */
	@Override
	public void setUserDefaultRole(String USER_CODE, String ROLE_CODE, String UPDATED_BY) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("USER_CODE", USER_CODE);
		params.put("ROLE_CODE", ROLE_CODE);
		params.put("UPDATED_BY", UPDATED_BY);
		roleDao.clearUserDefaultRole(params);// 清除原有的默认角色
		roleDao.setUserDefaultRole(params);// 设置新的默认角色
	}
	/**
	 * 更新用户角色
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2015-10-17
	 */
	@Override
	public void updateUserRoles(String USER_CODE, List<Role> roles, String CREATED_BY) {
		roleDao.deleteUserRoles(USER_CODE);
		this.addUserRoles(USER_CODE,roles,CREATED_BY);
	}
	
	 private void addUserRoles(String USER_CODE, List<Role> roles, String CREATED_BY) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("USER_CODE",USER_CODE);
		params.put("roles", roles);
		params.put("CREATED_BY", CREATED_BY);
		roleDao.addUserRoles(params);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：分页查询所有角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public Grid<Role> getRoles(Role rolePage) {
		// 获取第1页，10条内容，默认查询总数count
		PageHelper.startPage(rolePage.getPage(), rolePage.getRows());
		List<Role> list = roleDao.getRoles(rolePage);

		Grid<Role> grid = new Grid<Role>();
		grid.setRows(list);
		grid.setTotal((int)new PageInfo<Role>(list).getTotal());
		return grid;
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：新增角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public void addNewRole(Role role) {
		role.setUuid(UUIDUtil.uuidStr());
		role.setRole_code(UUIDUtil.uuidStr());
		role.setDel_flag(GlobalConstant.ZF_BJ_N);
		roleDao.addNewRole(role);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：角色授权菜单
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public String[] getRoleAssginMenu(String roleId) {
		return roleDao.getRoleAssginMenu(roleId);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：修改角色菜单
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public void changeRoleMenu(String roleId, String created_by, String menu_code, String select_menu_code, 
			List<Map<String, Object>> menuList, List<Map<String, Object>> buttonList) {
		// 删除角色菜单
		roleDao.deleteRoleMenu(roleId);

		// 添加角色菜单
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("menuIds", menuList);
		params.put("created_by", created_by);
		roleDao.addRoleMenu(params);

		// 删除重新设置的角色菜单按钮权限
		if(null != select_menu_code && !"".equals(select_menu_code)){
			MenuButton obj = new MenuButton();
			obj.setRole_code(roleId);
			obj.setMenu_codes(select_menu_code.split(","));
			menuButtonDao.delRoleMenuBtnBy(obj);
		}
		
		if(null != buttonList && !buttonList.isEmpty()){
			// 添加角色菜单按钮
			Map<String, Object> btnMap = new HashMap<String, Object>();
			btnMap.put("roleId", roleId);
			btnMap.put("created_by", created_by);
			btnMap.put("btns", buttonList);
			menuButtonDao.addRoleMenuBtn(btnMap);
		}
		
		// 删除未选中菜单的角色菜单按钮权限
		if(null != menu_code && !"".equals(menu_code)){
			MenuButton obj = new MenuButton();
			obj.setRole_code(roleId);
			obj.setMenu_codes(menu_code.split(","));
			menuButtonDao.delRoleMenuBtnBy(obj);
		}
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取用户分配角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public String[] getRoleAssginUser(String roleId, String userIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("userIds", userIds.split(","));
		List<String> result = roleDao.getRoleAssginUser(params);
		return result.toArray(new String[] {});
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：修改用户角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public void changeRoleUser(String roleId, String currUserIds,String newUserIds ,
			String[] newUserId, String CREATED_BY) {
		this.deleteRoleUser(roleId, currUserIds);// 删除用户角色
        this.deleteRoleUser("", newUserIds);//删除当前
		/* modify by zhu.qf 2015-02-15 start */
		int count = 0;
		List<Role> list = new ArrayList<Role>();
		for (int i = 0; i < newUserId.length; i++) {
			Role role = new Role();

			role.setUuid(UUIDUtil.uuidStr());
			role.setUser_code(newUserId[i]);
			role.setRole_code(roleId);
			role.setCreated_by(CREATED_BY);

			count = roleDao.isExistMrjs(newUserId[i]);

			// 如果已存在默认角色，则新插入的角色为非默认标记
			role.setDefault_flag(count > 0  ? "0" : "1");

			list.add(role);
		}

		// roleDao.addRoleUser(roleId, newUserId,CREATED_BY);
		roleDao.insertUserRoleInfo(list);

		/* modify by zhu.qf 2015-02-15 end */
	}

	private void deleteRoleUser(String roleId, String currUserIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("currUserIds", currUserIds.split(","));
		roleDao.deleteRoleUser(params);
	}

	@SuppressWarnings("unused")
	private void addRoleUser(String roleId, String[] newUserId,
			String CREATED_BY) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("newUserId", newUserId);
		params.put("created_by", CREATED_BY);
		roleDao.addRoleUser(params);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取用户角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public List<Role> getUserRoles(String USER_CODE) {
		return roleDao.getUserRoles(USER_CODE);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：查看角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public Role getRole(String ROLE_CODE) {
		return roleDao.getRole(ROLE_CODE);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：修改角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public void updateRole(Role role) {
		roleDao.updateRole(role);

	}

	/**
	 * 部门：软件开发事业部
	 * 功能：删除角色
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public void deleteRoles(String roleDms, String UPDATED_BY) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleCodes", roleDms.split(","));
		map.put("updated_by", UPDATED_BY);
		// 删除角色菜单
		roleDao.deleteRoleMenus(roleDms.split(","));

		// 删除角色用户关系表
		roleDao.deleteUserRoleInfo(roleDms.split(","));

		// 删除角色表
		roleDao.deleteRoles(map);

	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取角色菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-11-9
	 */
	@Override
	public List<MenuButton> getRoleMenuBtn(MenuButton obj) {
		return roleDao.getRoleMenuBtn(obj);
	}
}
