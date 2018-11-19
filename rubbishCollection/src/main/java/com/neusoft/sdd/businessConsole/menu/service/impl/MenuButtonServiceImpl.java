package com.neusoft.sdd.businessConsole.menu.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.sdd.base.controller.BaseService;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.menu.dao.MenuButtonDao;
import com.neusoft.sdd.businessConsole.menu.model.MenuButton;
import com.neusoft.sdd.businessConsole.menu.service.MenuButtonService;
import com.neusoft.sdd.util.commonUtil.RandomUtil;
import com.neusoft.sdd.util.commonUtil.StringUtil;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;

//@Transactional
@Service
public class MenuButtonServiceImpl extends BaseService implements MenuButtonService {
	
	@Autowired
	private MenuButtonDao menuBtnDao;
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取菜单按钮列表
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-4
	 */
	@Override
	public Tree getMenuBtnList(String menu_code,String rootid) {
		List<MenuButton> list = menuBtnDao.getMenuBtnList(menu_code);
		//获取所有菜单
        Tree rootTree = new Tree();
        initTree(rootTree,list,rootid);
        return rootTree;
	}

	/**
	 * 初始化菜单树
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：
	 * 作成时间：2017-8-29
	 */
    private void initTree(Tree rootTree , List<MenuButton> allMenus,String selfCode){
        Iterator<MenuButton> menuIterator =  allMenus.iterator();
        while (menuIterator.hasNext()){
        	MenuButton tmp = menuIterator.next();
            //初始化自身节点
            if (tmp.getButton_code().equals(selfCode)){
                menuToTree(tmp,rootTree);

            }else if (null == tmp.getParent_button_code()|| tmp.getParent_button_code().equals(selfCode)){
                //初始化子节点
                Tree children = new Tree();
                menuToTree(tmp,children);
                rootTree.getChildren().add(children);

                //递归
                initTree(children,allMenus,tmp.getButton_code());
            }
        }
    }
    private void menuToTree(MenuButton menu,Tree tree){
        tree.setId(menu.getButton_code());
        tree.setPid(menu.getParent_button_code());
        tree.setIconCls("");
        tree.setText(menu.getButton_name());
        tree.setState("open");
        
        tree.setAttributes(menu);
    }
	/**
	 * 部门：软件开发事业部
	 * 功能：新增菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-4
	 */
	@Override
	public void addMenuBtn(MenuButton obj) {
		obj.setUuid(UUIDUtil.uuidStr());
		obj.setButton_code(StringUtil.getCommonCode(2));//按钮代码
		//如果有上级菜单按钮，按钮范围代码为上级按钮范围代码+3位随机数
		if("0".equals(obj.getParent_button_code())){
			obj.setAuth_code_btn(StringUtil.getCommonCode(2));
		}else{
			obj.setAuth_code_btn(menuBtnDao.getParentAuthCode(obj) + RandomUtil.radmonkey(3));
		}
		menuBtnDao.addMenuBtn(obj);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取按钮详情
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-6
	 */
	@Override
	public MenuButton getMenuBtnDetail(MenuButton obj) {
		
		return menuBtnDao.getMenuBtnDetail(obj);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：更新菜单按钮信息
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-6
	 */
	@Override
	public void updateMenuBtn(MenuButton obj) {
		
		menuBtnDao.updateMenuBtn(obj);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：删除菜单按钮，并同时删除角色菜单按钮关联关系
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-6
	 */
	@Override
	public void deleteMenuBtn(MenuButton obj) {
		if(null != obj.getButton_code() && !"".equals(obj.getButton_code())){
			obj.setButton_codes(obj.getButton_code().split(","));
		}
		menuBtnDao.delRoleMenuBtnBy(obj);//删除角色菜单按钮关联关系
		menuBtnDao.deleteMenuBtn(obj);//删除角色菜单按钮信息
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取全部菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-7
	 */
	@Override
	public Grid<MenuButton> getAllMenuBtn() {
		Grid<MenuButton> grid = new Grid<MenuButton>();
		
		grid.setRows(menuBtnDao.getAllMenuBtn());// 获取全部菜单按钮信息
		grid.setTotal(0);
		
		return grid;
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取菜单授权数据
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-8
	 */
	@Override
	public Map<String, Object> getRoleAssginMenuBtn(MenuButton obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("menu", menuBtnDao.getRoleMenuBy(obj));
		map.put("btn", menuBtnDao.getRoleMenuBtn(obj));
		
		return map;
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取角色已授权菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@Override
	public List<MenuButton> getGrantMenuBtn(MenuButton obj) {
		return menuBtnDao.getRoleMenuBtn(obj);
	}
   
}
