package com.neusoft.sdd.businessConsole.menu.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.businessConsole.menu.model.MenuButton;

public interface MenuButtonDao {
	List<MenuButton> getMenuBtnList(String menu_code);

	void addMenuBtn(MenuButton obj);
	
	MenuButton getMenuBtnDetail(MenuButton obj);
	
	void updateMenuBtn(MenuButton obj);
	
	void deleteMenuBtn(MenuButton obj);
	
	void delRoleMenuBtnBy(MenuButton obj);
	
	List<MenuButton> getAllMenuBtn();
	
	List<MenuButton> getRoleMenuBy(MenuButton obj);
	
	List<MenuButton> getRoleMenuBtn(MenuButton obj);
	
	void addRoleMenu(MenuButton obj);
	
	void addRoleMenuBtn(Map<String, Object> param);
	
	String getParentAuthCode(MenuButton obj);
}
