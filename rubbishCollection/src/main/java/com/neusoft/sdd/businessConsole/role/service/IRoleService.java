package com.neusoft.sdd.businessConsole.role.service;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.businessConsole.menu.model.MenuButton;
import com.neusoft.sdd.businessConsole.role.model.Role;

/**
 * Created by yins on 17-10-18.
 */
public interface IRoleService {
	
	//获取系统所有的角色
    List<Role> getAllRoles();
   //获取当前用户的角色信息
  	List<Role> getUserAllRoles(String USER_CODE);
   //更新用户角色
  	public void updateUserRoles(String USER_CODE, List<Role> roles, String UPDATED_BY);
  	//设置用户默认角色
	public void setUserDefaultRole(String USER_CODE, String ROLE_CODE, String UPDATED_BY);
	
	Grid<Role> getRoles(Role rolePage);

    void addNewRole(Role rolePage);

    String[] getRoleAssginMenu(String roleId);

    void changeRoleMenu(String roleId, String created_by, String menu_code, String select_menu_code,
    		List<Map<String, Object>> menuList, List<Map<String, Object>> buttonList);

    String[] getRoleAssginUser(String roleId, String ids);

    void changeRoleUser(String roleId, String currUserIds, String newUserIds ,String[] newUserId,String CREATED_BY);
    
	List<Role> getUserRoles(String USER_CODE);

	Role getRole(String ROLE_CODE);

	void updateRole(Role role);

	void deleteRoles(String roleDms, String UPDATED_BY);
	
	List<MenuButton> getRoleMenuBtn(MenuButton obj);
}
