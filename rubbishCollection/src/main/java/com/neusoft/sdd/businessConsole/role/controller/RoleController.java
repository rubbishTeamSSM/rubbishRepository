package com.neusoft.sdd.businessConsole.role.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.businessConsole.menu.model.MenuButton;
import com.neusoft.sdd.businessConsole.role.model.Role;
import com.neusoft.sdd.businessConsole.role.service.IRoleService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

@Controller
@RequestMapping("/businessConsole/role")
public class RoleController extends BaseSimpleFormController {
	
	 @Autowired
	 private IRoleService roleService;
	 
	 /**
	  * 跳转到角色管理页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-27
	 */
	@RequestMapping("/systemRoleManager")
	public String systemRoleManager(HttpServletResponse response,Role rolePage){
		return "businessConsole/role/roleManager";
	}
	
	/**
     * 跳转到修改角色页面
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@RequestMapping("/roleAdd")
    public String roleAdd(){
    	return "businessConsole/role/roleAdd";
    }
	
    /**
     * 跳转到修改角色页面
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@RequestMapping("/modifyRole")
    public String modifyRole(HttpServletRequest request){
    	String ROLE_CODE = request.getParameter("ROLE_CODE");
    	Role role =roleService.getRole(ROLE_CODE);
    	request.setAttribute("role", role);
    	return "businessConsole/role/roleModify";
    }
	
	 /**
     * 跳转到菜单授权页面
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@RequestMapping("/menuGrantToRole")
    public String menuGrantToRole(){
    	return "businessConsole/role/menuGrantToRole";
    }
	
	 /**
     * 跳转到用户授权页面
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@RequestMapping("/userGrantToRole")
    public String userGrantToRole(){
    	return "businessConsole/role/userGrantToRole";
    }


	/**
	 * 角色分页查询
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-27
	 */
	@RequestMapping("/manageRole")
	public void manageRole(HttpServletResponse response,Role rolePage){
		try {
			Grid<Role> grid = roleService.getRoles(rolePage);
			printHttpServletResponse(GsonUtil.toJson(grid),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> manageRole() Exception : " + e);
		}
		
	}
	
	/**
	 * 查询系统所有角色
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-27
	 */
	@RequestMapping("/sysAllRole")
	public void sysAllRole(HttpServletResponse response){
		try {
			List<Role> allRoles = roleService.getAllRoles();
			printHttpServletResponse(GsonUtil.toJson(allRoles),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> sysAllRole() Exception : " + e);
		}
		
	}

	/**
	 * 新增角色
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addNewRole")
    public void addNewRole(HttpSession session,HttpServletResponse response,Role rolePage){
        JsonResult result = new JsonResult();
        result.setMsg("");
        result.setSuccess(true);
        try{
        	Map<String,Object> loginUser = (Map<String, Object>) session.getAttribute(GlobalConstant.LOGIN_USER);    	
        	rolePage.setCreated_by(String.valueOf(loginUser.get("USER_ID")));
            roleService.addNewRole(rolePage);

            printHttpServletResponse(new Gson().toJson(result),response);
        }catch(Exception e){
			throw new IServiceException(this.getClass() + " --> addNewRole() Exception : " + e);
        }
    }

	
    /**
     * 获得角色已经分配的菜单
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@RequestMapping("/getRoleAssginMenu")
    public void getRoleAssginMenu(HttpServletRequest request,HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        try{
            String roleId = request.getParameter("roleId");
            jsonResult.setMsg(roleService.getRoleAssginMenu(roleId));

            printHttpServletResponse(new Gson().toJson(jsonResult),response);
        }catch (Exception e){
			throw new IServiceException(this.getClass() + " --> getRoleAssginMenu() Exception : " + e);
        }

    }

    /**
     * datagrid当前页角色分配的用户
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@RequestMapping("/getRoleAssginUserCurrentPage")
    public void getRoleAssginUserCurrentPage(HttpServletRequest request,HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
       try{
    	   String roleId = request.getParameter("roleId");//角色编号
           String ids = request.getParameter("ids");//用户编号

           jsonResult.setMsg(roleService.getRoleAssginUser(roleId,ids));
           printHttpServletResponse(new Gson().toJson(jsonResult),response);
        }catch (Exception e){
			throw new IServiceException(this.getClass() + " --> getRoleAssginUserCurrentPage() Exception : " + e);
        }
       
    }

    /**
     * 部门：软件开发事业部
     * 功能：配置角色菜单
     * 描述：略
     * 作成者：朱庆锋
     * 作成时间：2017-10-20
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/changeRoleMenu")
    public void changeRoleMenu(HttpServletRequest request,HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        try{
            String roleId = request.getParameter("roleId");//角色编号
            String menus = request.getParameter("menus");//菜单
            String buttons = request.getParameter("buttons");//菜单按钮
            String menu_code = request.getParameter("menu_code");//未选中菜单的菜单code
            String select_menu_code = request.getParameter("select_menu_code");//重新设置的菜单按钮树的菜单code

            Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
        	
        	List<Map<String, Object>> menuList = new Gson().fromJson(menus, new TypeToken<List<Map<String, Object>>>(){}.getType());
        	List<Map<String, Object>> buttonList = new Gson().fromJson(buttons, new TypeToken<List<Map<String, Object>>>(){}.getType());
            roleService.changeRoleMenu(roleId, String.valueOf(loginUser.get("USER_ID")), menu_code, select_menu_code, menuList, buttonList);

            printHttpServletResponse(new Gson().toJson(jsonResult),response);
        }catch (Exception e){
			throw new IServiceException(this.getClass() + " --> changeRoleMenu() Exception : " + e);
        }
    }

    /**
     * 更新角色
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/changeRoleUser")
    public void changeRoleUser(HttpServletRequest request,HttpServletResponse response) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(true);
        try {
            String roleId = request.getParameter("roleId");
            String currUserIds = request.getParameter("currUserIds");
            String newUserIds = request.getParameter("newUserIds");
            
            Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
            roleService.changeRoleUser(roleId, currUserIds, newUserIds, newUserIds.split(","),String.valueOf(loginUser.get("USER_ID")));
            
            printHttpServletResponse(new Gson().toJson(jsonResult),response);
        } catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> changeRoleUser() Exception : " + e);
        }

    }
    
    
    /**
     * 修改role
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateRole")
    public void updateRole(HttpServletRequest request,HttpServletResponse response,Role rolePage){
         JsonResult result = new JsonResult();
         result.setMsg("");
         result.setSuccess(true);
         try{
        	 Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
        	 rolePage.setCreated_by(String.valueOf(loginUser.get("USER_ID")));
             roleService.updateRole(rolePage);
             printHttpServletResponse(new Gson().toJson(result),response);
         }catch(Exception e){
 			throw new IServiceException(this.getClass() + " --> updateRole() Exception : " + e);
         }
    }
    
    /**
     * 删除角色
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-27
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteRoles")
    public void deleteRoles(HttpServletRequest request,HttpServletResponse response){
    	 JsonResult result = new JsonResult();
         result.setMsg("");
         result.setSuccess(true);
         try{
        	 Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
         	 String roleDms = request.getParameter("roleDms");
             roleService.deleteRoles(roleDms, String.valueOf(loginUser.get("USER_ID")));
             printHttpServletResponse(new Gson().toJson(result),response);
         }catch(Exception e){
  			throw new IServiceException(this.getClass() + " --> deleteRoles() Exception : " + e);
         }
    }

	/**
	 * 部门：软件开发事业部
	 * 功能：获取角色菜单按钮
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-11-9
	 */
	@ResponseBody
	@RequestMapping("/getRoleMenuButton")
	public List<MenuButton> getRoleMenuButton(MenuButton btn, HttpSession session) {
		try{
			String cur_role = (String)session.getAttribute(GlobalConstant.LOGIN_USER_CURRENT_ROLE);
			btn.setRole_code(cur_role);
			return roleService.getRoleMenuBtn(btn);
		}catch(Exception e){
  			throw new IServiceException(this.getClass() + " --> getRoleMenuButton() Exception : " + e);
        }
	}
}
