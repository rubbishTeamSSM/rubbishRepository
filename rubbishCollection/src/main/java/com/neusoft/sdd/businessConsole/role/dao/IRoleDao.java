package com.neusoft.sdd.businessConsole.role.dao;

import com.neusoft.sdd.businessConsole.menu.model.MenuButton;
import com.neusoft.sdd.businessConsole.role.model.Role;

import java.util.List;
import java.util.Map;


public interface IRoleDao {
    List<Role> getRoles(Role rolePage);

    int countRoles(Role rolePage);

    void addNewRole(Role rolePage);

    String[] getRoleAssginMenu(String roleId);

    void deleteRoleMenu(String roleId);

    void addRoleMenu(Map<String,Object> params);

    List<String> getRoleAssginUser(Map<String,Object> params);

    void deleteRoleUser(Map<String,Object> params);

    void addRoleUser(Map<String,Object> params);

	Role getUserDefaultRole(String USER_CODE);

	List<Role> getUserRoles(String USER_CODE);

	List<Role> getUserAllRoles(String USER_CODE);

	void clearUserDefaultRole(Map<String,String> params);

	void setUserDefaultRole(Map<String,String> params);

	List<Role> getAllRoles();

	void insertUserDefaultRole(Map<String,String> params);

	void deleteUserRoles(String USER_CODE);

	void addUserRoles(Map<String,Object> params);

	void deleteUserSRoles(String[] USER_CODEs);

	Role getRole(String ROLE_CODE);

	void updateRole(Role role);

	void deleteRoleMenus(String[] roleDms);

	void deleteUserRoleInfo(String[] roleDms);

	void deleteRoles(Map<String,Object> map);
	
	int isExistMrjs(String USER_CODE);
	
	void insertUserRoleInfo(List<Role> list);
	
	List<MenuButton> getRoleMenuBtn(MenuButton obj);
}
