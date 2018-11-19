package com.neusoft.sdd.businessConsole.menu.service;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.menu.model.MenuButton;

public interface MenuButtonService {
	
	Tree getMenuBtnList(String menu_code,String rootid);
	
	void addMenuBtn(MenuButton obj);
	
	MenuButton getMenuBtnDetail(MenuButton obj);
	
	void updateMenuBtn(MenuButton obj);
	
	void deleteMenuBtn(MenuButton obj);
	
	Grid<MenuButton> getAllMenuBtn();
	
	Map<String, Object> getRoleAssginMenuBtn(MenuButton obj);
	
	List<MenuButton> getGrantMenuBtn(MenuButton obj);
	
}
