package com.neusoft.sdd.businessConsole.menu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.menu.model.MenuButton;
import com.neusoft.sdd.businessConsole.menu.service.MenuButtonService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

@Controller
@RequestMapping("/businessConsole/menuBtn")
public class MenuButtonController extends BaseSimpleFormController {
	
	@Autowired
	private MenuButtonService menuBtnService;

	/**
	 * 部门：软件开发事业部
	 * 功能：跳转新增页面
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-4
	 */
	@RequestMapping("/showAddMenuBtn")
	public String showAddMenuBtn(){
		return "businessConsole/menu/menuButtonAdd";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转修改页面
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-4
	 */
	@RequestMapping("/showModMenuBtn")
	public String showModMenuBtn(HttpServletRequest request){
		request.setAttribute("BTN_CODE", request.getAttribute("BTN_CODE"));
		return "businessConsole/menu/menuButtonModify";
	}
	/**
	 * 
	* 部门：软件开发事业部
	* 功能：跳转设置按钮子菜单页面
	* 描述：
	* 作成者：许洋阳
	* 作成时间：2017-10-16
	 */
	@RequestMapping("/showSetMenuBtn")
	public String showSetMenuBtn(HttpServletRequest request){
		return "businessConsole/menu/menuButtonSet";
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取菜单按钮列表
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-4
	 */
	@ResponseBody
	@RequestMapping("/getMenuBtnList")
	public void getMenuBtnList(HttpServletResponse response,HttpServletRequest request){
		try {
			//接受前台json数据
			String menu_code = request.getParameter("data");
			Tree treeMenus = menuBtnService.getMenuBtnList(menu_code,GlobalConstant.MENU_ROOT);
	        printHttpServletResponse(GsonUtil.toJson(treeMenus.getChildren()),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getMenuBtnList() Exception : " + e);
		}
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：新增菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-4
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/addMenuBtn")
	public void addMenuBtn(MenuButton obj, HttpSession session){
		try {
			Map<String,Object> loginUser = (Map<String, Object>) session.getAttribute(GlobalConstant.LOGIN_USER);
			obj.setCreated_by(String.valueOf(loginUser.get("USER_ID")));//创建人
			
			menuBtnService.addMenuBtn(obj);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> addMenuBtn() Exception : "+ e);
		}
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取按钮详情
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-6
	 */
	@ResponseBody
	@RequestMapping("/getMenuBtnDetail")
	public MenuButton getMenuBtnDetail(MenuButton obj) {
		return menuBtnService.getMenuBtnDetail(obj);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：修改菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-6
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/updateMenuBtn")
	public void updateMenuBtn(MenuButton obj, HttpSession session) {
		try {
			Map<String,Object> loginUser = (Map<String, Object>) session.getAttribute(GlobalConstant.LOGIN_USER);
			obj.setUpdated_by(String.valueOf(loginUser.get("USER_ID")));//修改者
			
			menuBtnService.updateMenuBtn(obj);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> updateMenuBtn() Exception : "+ e);
		}
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：删除菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-6
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/deleteMenuBtn")
	public void deleteMenuBtn(HttpSession session, MenuButton obj) {
		try {
			
			Map<String,Object> loginUser = (Map<String, Object>) session.getAttribute(GlobalConstant.LOGIN_USER);
			obj.setUpdated_by(String.valueOf(loginUser.get("USER_ID")));//修改者
			
			menuBtnService.deleteMenuBtn(obj);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> deleteMenuBtn() Exception : "+ e);
		}
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：角色管理-获取全部菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-7
	 */
	@ResponseBody
	@RequestMapping("/getAllMenuBtn")
	public Grid<MenuButton> getAllMenuBtn() {
		return menuBtnService.getAllMenuBtn();
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：角色管理-获取角色已授权的菜单和按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-8
	 */
	@ResponseBody
	@RequestMapping("/getRoleAssginMenuBtn")
	public Map<String, Object> getRoleAssginMenuBtn(MenuButton obj){
		return menuBtnService.getRoleAssginMenuBtn(obj);
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：角色管理-菜单授权
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-9-8
	 */
	@ResponseBody
	@RequestMapping("/changeRoleMenuBtn")
	public void changeRoleMenuBtn(HttpSession session, List<MenuButton> obj){
		try {
			
//			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
//			obj.setUpdated_by(loginUser.get("USER_ID"));//修改者
//			
//			menuBtnService.changeRoleMenuBtn(obj);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> changeRoleMenuBtn() Exception : "+ e);
		}
	}

	/**
	 * 部门：软件开发事业部
	 * 功能：获取角色已授权菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-19
	 */
	@ResponseBody
	@RequestMapping("/getGrantMenuBtn")
	public List<MenuButton> getGrantMenuBtn(MenuButton obj){
		return menuBtnService.getGrantMenuBtn(obj);
	}

}
