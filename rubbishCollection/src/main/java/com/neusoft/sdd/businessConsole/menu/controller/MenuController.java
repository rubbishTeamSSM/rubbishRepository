package com.neusoft.sdd.businessConsole.menu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.menu.model.Menu;
import com.neusoft.sdd.businessConsole.menu.service.MenuService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

@Controller
@RequestMapping("/businessConsole/menu")
public class MenuController extends BaseSimpleFormController {
	
	
	@Autowired
	private MenuService menuService;

	
	/**
	 * 跳转到菜单页
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/systemMenuManager")
	public String systemMenuManager()
	{
		return "businessConsole/menu/menuManager";
	}
	
	/**
	 * 跳转到新增菜单页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/menuAdd")
	public String menuAdd()
	{
		return "businessConsole/menu/menuAdd";
	}
	
	
	/**
	 * 跳转到修改菜单页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/menuModify")
	public String menuModify()
	{
		return "businessConsole/menu/menuModify";
	}
	
	/**
	 * 获取用户菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getUserMenu")
	public void getUserMenu(HttpServletRequest request,HttpServletResponse response, HttpSession session){
		try {
			//登录用户
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//登录用户角色
			String roleId = request.getParameter("roleId");
			//父菜单编号
			String menuPId = request.getParameter("menuPId");
	        if(null == menuPId){
	            menuPId = GlobalConstant.USER_MENU_ROOT;
	        }
	        
	        //菜单层次      
	        String LEVEL = request.getParameter("LEVEL");
	        
	        if(null == LEVEL || "".equals(LEVEL)){
	        	LEVEL = "0";
	        }
	        
	        // 移除session
	        session.removeAttribute(GlobalConstant.LOGIN_USER_CURRENT_ROLE);
	        // 添加session
	        session.setAttribute(GlobalConstant.LOGIN_USER_CURRENT_ROLE, roleId);
	        
			List<Tree> menuTree = menuService.getUserMenu(String.valueOf(loginUser.get("USER_ID")), roleId, menuPId, LEVEL);
			
			printHttpServletResponse(new Gson().toJson(menuTree),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getUserMenu() Exception : " + e);
		}
		
	}
	
	/**
	 * 获取系统菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/getSystemMenu")
	public void getSystemMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String pid = request.getParameter("pid");
			if(null == pid){
				pid = GlobalConstant.MENU_ROOT;
			}
			
			List<Menu> menus = menuService.getSystemMenu(pid);
			printHttpServletResponse(new Gson().toJson(menus),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getSystemMenu() Exception : " + e);
		}
		
	}
	
	/**
	 * 增加新菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addNewMenu")
	public void addNewMenu(HttpServletRequest request,HttpServletResponse response, Menu menuPage){
		JsonResult result = new JsonResult();
		result.setMsg("");
		result.setSuccess(true);
		try{
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			menuPage.setCREATED_BY(String.valueOf(String.valueOf(loginUser.get("USER_ID"))));
			menuService.addNewMenu(menuPage);
			printHttpServletResponse(new Gson().toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> addNewMenu() Exception : " + e);
		}
	}

	/**
	 * 获取所有菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/getAllMenu")
    public void getAllMenu(HttpServletResponse response){
		try {
			Tree treeMenus = menuService.getAllMenu(GlobalConstant.MENU_ROOT);
	        printHttpServletResponse(GsonUtil.toJson(treeMenus.getChildren()),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getAllMenu() Exception : " + e);
		}
        
    }
    
    /**
     * 菜单详细信息
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-28
     */
	@RequestMapping("/menuDetails")
    public void menuDetails(HttpServletRequest request,HttpServletResponse response){
		try {
			String MENU_CODE = request.getParameter("MENU_CODE");//菜单代码
	    	printHttpServletResponse(GsonUtil.toJson(menuService.getMenuDetail(MENU_CODE)),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> menuDetails() Exception : " + e);
		}
    }
    
	/**
     * 修改菜单
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-28
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyMenu")
    public void modifyMenu(HttpServletResponse response,Menu menuPage,HttpServletRequest request){
		JsonResult result = new JsonResult();
		result.setMsg("");
		result.setSuccess(true);
		try{
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			menuPage.setUPDATED_BY(String.valueOf(String.valueOf(loginUser.get("USER_ID"))));
			menuService.modifyMenu(menuPage);
			printHttpServletResponse(new Gson().toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> modifyMenu() Exception : " + e);
		}
		
    }
    
	/**
     * 删除菜单
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-28
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteMenu")
    public void deleteMenu(HttpServletRequest request,HttpServletResponse response){
    	JsonResult result = new JsonResult();
		result.setMsg("");
		result.setSuccess(true);
		try{
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			Menu menu = new Menu();
			menu.setUPDATED_BY(String.valueOf(loginUser.get("USER_ID")));
	    	String menuCodes = request.getParameter("menuId");
	    	String parentCode = request.getParameter("parents");
	    	menu.setMENU_CODE(menuCodes);
	    	menu.setPARENT_MENU_CODE(parentCode);
			menuService.deleteMenu(menu);

			printHttpServletResponse(new Gson().toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> deleteMenu() Exception : " + e);
		}
    }
}
