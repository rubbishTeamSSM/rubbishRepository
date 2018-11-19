package com.neusoft.sdd.businessConsole.menu.service;

import java.util.List;

import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.menu.model.Menu;

public interface MenuService {
	List<Tree> getUserMenu(String userId,String roleId, String menuPId,String LEVEL);

	List<Menu> getSystemMenu(String pid);
	
	void addNewMenu(Menu menuPage);

    Tree getAllMenu(String rootId);

	Menu getMenuDetail(String MENU_CODE);

	void modifyMenu(Menu menu);

	void deleteMenu(Menu menu);
}
