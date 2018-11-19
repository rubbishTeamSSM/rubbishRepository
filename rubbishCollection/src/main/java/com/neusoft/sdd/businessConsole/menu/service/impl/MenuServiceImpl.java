package com.neusoft.sdd.businessConsole.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseService;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.menu.dao.MenuButtonDao;
import com.neusoft.sdd.businessConsole.menu.dao.MenuDAO;
import com.neusoft.sdd.businessConsole.menu.model.Menu;
import com.neusoft.sdd.businessConsole.menu.model.MenuAttribute;
import com.neusoft.sdd.businessConsole.menu.model.MenuButton;
import com.neusoft.sdd.businessConsole.menu.service.MenuService;
import com.neusoft.sdd.util.commonUtil.StringUtil;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;

//@Transactional
@Service
public class MenuServiceImpl extends BaseService implements MenuService {

	@Autowired
	private MenuDAO menuDao;
	
	@Autowired
	private MenuButtonDao menuBtnDao;

	public List<Tree> getUserMenu(String userId,String roleId, String menuPId,String LEVEL) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("roleId", roleId);
		params.put("menuPId", menuPId);
		
		int LEVELInt ;
		
		try{
			LEVELInt= Integer.parseInt(LEVEL);
		}catch(Exception e){
			LEVELInt = 1;
		}
		
		params.put("LEVEL",LEVELInt);
		List<Menu> menus = menuDao.getUserMenu(params);

		List<Tree> treeMenu = new ArrayList<Tree>();
		for (Menu menu : menus) {
			Tree temp = new Tree();
			temp.setId(menu.getMENU_CODE());
			temp.setChecked(false);
			temp.setIconCls("");
			temp.setPid(menu.getPARENT_MENU_CODE());
			temp.setState(menuHas(menu.getTYPE()));// 是否含有子节点
			temp.setText(menu.getMENU_NAME());

			MenuAttribute attribute = new MenuAttribute();
			attribute.setUrl(menu.getMENU_URL());
			
			attribute.setLEVEL(menu.getLEVEL());
			attribute.setTYPE(menu.getTYPE());
			temp.setAttributes(attribute);
			treeMenu.add(temp);
		}
		return treeMenu;
		
	}
	
	private String menuHas(String TYPE){
		if(TYPE.equals("1")){
			return "open";
		}else{
			return "closed";
		}
	}
	

	public List<Menu> getSystemMenu(String pid) {
		List<Menu> sysmenus = menuDao.getSystemMenu(pid);
		for(Menu m : sysmenus){
			m.setState(menuHas(m.getTYPE()));
		}
		return sysmenus;
	}
	


	/**
	 * 菜单新增
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：
	 * 作成时间：2017-8-29
	 */
	public void addNewMenu(Menu menu) {
		//获取新增菜单显示排序
		String SORT_NO="";
		if(0 == menu.getSORT_NO()){
			SORT_NO= menuDao.getMaxChildrenSORT_NO(menu.getPARENT_MENU_CODE());
			 if(null == SORT_NO){
				 SORT_NO = "1";
			 }else{
				 SORT_NO = (Integer.parseInt(SORT_NO) + 1) +"";
			 }
			menu.setSORT_NO(Integer.parseInt(SORT_NO));
		}
		
		String menuCode = StringUtil.getCommonCode(2);//菜单代码
		//根菜单就是菜单代码，其他新建子菜单时，获取父菜单的范围代码+‘3位自增长数’。
		if(null == menu.getPARENT_MENU_CODE() || "".equals(menu.getPARENT_MENU_CODE()))//根菜单
		{
			menu.setAUTH_CODE(menuCode);
			menu.setPARENT_MENU_CODE("0");
		}else{
			menu.setAUTH_CODE(menuDao.getAuthCode(menu));
		}
		  
		 menu.setUUID(UUIDUtil.uuidStr());
		 menu.setMENU_CODE(menuCode);
		 
		 menu.setDEL_FLAG(GlobalConstant.ZF_BJ_N);
		 menuDao.addNewMenu(menu);
		 menuDao.updateParentType(menu);
	}

	/**
	 * 加载所有菜单
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：
	 * 作成时间：2017-8-29
	 */
    @Override
    public Tree getAllMenu(String rootid) {
        //获取所有菜单
        List<Menu> allMenus = menuDao.getAllMenu();
        Tree rootTree = new Tree();
        initTree(rootTree,allMenus,rootid);
        return rootTree;
    }

    /**
	 * 初始化菜单树
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：
	 * 作成时间：2017-8-29
	 */
    private void initTree(Tree rootTree , List<Menu> allMenus,String selfCode){
        Iterator<Menu> menuIterator =  allMenus.iterator();
        while (menuIterator.hasNext()){
            Menu tmp = menuIterator.next();
            //初始化自身节点
            if (tmp.getMENU_CODE().equals(selfCode)){
                menuToTree(tmp,rootTree);

            }else if (tmp.getPARENT_MENU_CODE().equals(selfCode)){
                //初始化子节点
                Tree children = new Tree();
                menuToTree(tmp,children);
                rootTree.getChildren().add(children);

                //递归
                initTree(children,allMenus,tmp.getMENU_CODE());
            }
        }
    }

    private void menuToTree(Menu menu,Tree tree){
        tree.setId(menu.getMENU_CODE());
        tree.setPid(menu.getPARENT_MENU_CODE());
        tree.setIconCls("");
        tree.setText(menu.getMENU_NAME());
        tree.setState("open");
        
        MenuAttribute attribute = new MenuAttribute();
        attribute.setTYPE(menu.getTYPE());
        attribute.setLEVEL(menu.getLEVEL());
        attribute.setAUTH_CODE(menu.getAUTH_CODE());
        tree.setAttributes(attribute);
    }

    /**
	 * 菜单详情
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：
	 * 作成时间：2017-8-29
	 */
	@Override
	public Menu getMenuDetail(String MENU_CODE) {
		return menuDao.getMenuDetail(MENU_CODE);
	}

	/**
	 * 修改菜单
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：
	 * 作成时间：2017-8-29
	 */
	@Override
	public void modifyMenu(Menu menu) {
		menuDao.modifyMenu(menu);
	}

	/**
	 * 删除菜单以及关联关系
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：
	 * 作成时间：2017-8-29
	 */
	@Override
	public void deleteMenu(Menu menu) {
		String menuId = menu.getMENU_CODE();
		if(null != menuId)
		{
			menuDao.deleteRoleMenu(menuId.split(","));//删除角色关联菜单
			menuDao.deleteAdditionMenu(menuId.split(","));//删除用户关联额外菜单
			
			//删除菜单按钮
			MenuButton obj = new MenuButton();
			obj.setMenu_codes(menuId.split(","));
			menuBtnDao.deleteMenuBtn(obj);//删除菜单按钮
			
			menu.setMENU_CODES(menuId.split(","));
			menuDao.deleteMenu(menu);//删除菜单
			
			//如果父级菜单没有其他子菜单，则更新父级菜单类型为菜单
			if(0 == menuDao.getIfHaveChild(menu)){
				menuDao.updateParentTypes(menu);
			}
		}
	}

}
