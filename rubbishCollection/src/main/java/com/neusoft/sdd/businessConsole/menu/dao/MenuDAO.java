package com.neusoft.sdd.businessConsole.menu.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.businessConsole.menu.model.Menu;

public interface MenuDAO {

	List<Menu> getUserMenu(Map<String, Object> params);

	int menuHasChildren(Map<String, String> params);

	List<Menu> getSystemMenu(String pid);

	int sysMenuHasChildren(String menuPId);

	String getMaxChildrenSORT_NO(String PARENT_MENU_CODE);

	void addNewMenu(Menu menuPage);

    List<Menu> getAllMenu();

	void deleteUsersAdditionMenu(String[] USER_CODEs);

	Menu getMenuDetail(String MENU_CODE);

	void modifyMenu(Menu menu);

	void deleteMenu(Menu menu);

	void deleteRoleMenu(String[] menuId);

	void deleteAdditionMenu(String[] menuId);

	String getAuthCode(Menu menu);
	
	void updateParentType(Menu menu);
	
	int getIfHaveChild(Menu menu);
	
	void updateParentTypes(Menu menu);
}
