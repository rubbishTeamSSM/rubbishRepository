package com.neusoft.sdd.businessConsole.user.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.base.systemLog.SystemControllerLog;
import com.neusoft.sdd.businessConsole.role.model.Role;
import com.neusoft.sdd.businessConsole.role.service.IRoleService;
import com.neusoft.sdd.businessConsole.user.service.IUserManagerService;
import com.neusoft.sdd.util.commonUtil.DateUtil;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

@Controller
@RequestMapping("/businessConsole/user")
public class UserManagerController extends BaseSimpleFormController {
	
	@Autowired
	private IUserManagerService userManagerService;
	 
	@Autowired
	private IRoleService roleService;
	
	 /**
		 * 部门：软件开发事业部
		 * 功能：跳转到账号管理页面
		 * 描述：略
		 * 作成者：菅文达
		 * 作成时间：2017-10-14
		 */
		@RequestMapping("/toAccountManage")
		public String toAccountManage(HttpServletResponse response,HttpServletRequest request) {
			return "businessConsole/user/accountManage";
		}
		
		
		/**
		 * 部门：软件开发事业部
		 * 功能：获取账号列表
		 * 描述：略
		 * 作成者：菅文达
		 * 作成时间：2017-10-14
		 */
		@RequestMapping("/getAccount")
		@SystemControllerLog(description = "获取账号列表")
		public void getAccount(HttpServletRequest request,HttpServletResponse response){
			try {
				String data = request.getParameter("data");	
				Map<String, String> param = new Gson().fromJson(data,new TypeToken<Map<String, String>>() {}.getType());
				param.put("page", request.getParameter("page"));
				param.put("rows", request.getParameter("rows"));
				// 获取信息
				Grid<Map<String, String>> grid = userManagerService.getAccount(param);
				printHttpServletResponse(GsonUtil.toJson(grid), response);
			} catch (Exception e) {
				throw new IServiceException(this.getClass()+ "-->getAccount() exception:" + e);
			}
		}
		 /**
		   * 跳轉到用戶新增頁面
		   * 部门：软件开发事业部	 
		   * 描述：略
		   * 作成者：尹帅
		   * 作成时间：2017-11-1
		  */
		 @RequestMapping("/userAdd")
		 public String userAdd()
		 {
			 return "businessConsole/user/userAdd";
		 }
		 
        /**
		  * 用户代码和用户名验证
		  * 部门：软件开发事业部	 
		  * 描述：略
		  * 作成者：尹帅
		  * 作成时间：2017-11-1
		 */
		@RequestMapping("/validUser")
		public void validUser(HttpServletRequest request,HttpServletResponse response){
		   try {
				  String USER_ACCNT = request.getParameter("USER_ACCNT");	
				  printHttpServletResponse(GsonUtil.toJson(userManagerService.validUser(USER_ACCNT)),response);
			} catch (Exception e) {
				throw new IServiceException(this.getClass() + " --> validUser() Exception : " + e);
			}
		}		 
	 /**
	  * 跳转到用户额外菜单页面
	   * 部门：软件开发事业部	 
	   * 描述：略
	   * 作成者：yins
	   * 作成时间：2017-10-18
	  */
	 @RequestMapping("/UserAdditionMenu")
	 public String userAdditionMenu()
	 {
		 return "businessConsole/user/userAdditionMenu";
	 }
	 
		/**
	  * 用户指派角色 页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-17
	 */
	   @RequestMapping("/showUserAssignRole")
	   public String showUserAssignRole(HttpServletResponse response,HttpServletRequest request){
	     //获取用户代码
		String USER_CODE = request.getParameter("USER_CODE");//用户代码
		//得到所有的角色
		List<Role> getAllRoles=roleService.getAllRoles();
		//获取当前用户的角色
		List<Role> userOwnRoles = roleService.getUserAllRoles(USER_CODE);
		request.setAttribute("roles",getAllRoles);
		request.setAttribute("rolesId",GsonUtil.toJson(userOwnRoles));
	
		return "businessConsole/user/userAssignRole";
		
	}
	   
	   /**
		 * 新增用户
		 * 部门：软件开发事业部	 
		 * 描述：略
		 * 作成者：尹帅
		 * 作成时间：2017-11-1
	*/
	   @SuppressWarnings("unchecked")
	   @RequestMapping("/addNewUser")
		public void addNewUser(HttpServletRequest request,HttpServletResponse response){
			JsonResult result = new JsonResult();
			result.setMsg("");
			result.setSuccess(true);
			try{
				//接受前台json数据
				String data = request.getParameter("data");
				//转换成map
				Map<String,Object> param = new Gson().fromJson(data,new TypeToken<Map<String,Object>>() {}.getType());
				//获取session数据
				Map<String,Object> loginUser = (Map<String, Object>)request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
				param.put("YHMC", loginUser.get("USER_ID"));//获取当前登录用户的ID	
				userManagerService.addNewUser(param);
				printHttpServletResponse(new Gson().toJson(result),response);
			}catch(Exception e){
				throw new IServiceException(this.getClass() + " --> addNewUser() Exception : " + e);
			}
			
		}
	 /**
	   * 跳转到部门设置页面
	   * 部门：软件开发事业部	 
	   * 描述：略
	   * 作成者：yins
	   * 作成时间：2017-10-18
	  */
	 @RequestMapping("/UserAssignDepartment")
	 public String userAssignDepartment()
	 {
		 return "businessConsole/user/userAssignDepartment";
	 }
	 
	/**
	 *  配置用户默认角色 页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-18
	 */
	@RequestMapping("/UserDefaultRole")
	public String getUserAllRole(HttpServletRequest request){
		String USER_CODE = request.getParameter("user_code");
		List<Role> roles =  roleService.getUserAllRoles(USER_CODE);//用户所有角色
		request.setAttribute("roles", roles);	
		return "businessConsole/user/userDefaultRole";
	}
	
	/**
	  * 设置默认角色
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-18
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/setUserDefaultRole")
	public void setUserDefaultRole(HttpServletRequest request,HttpServletResponse response){
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		try{
			//User loginUser = (User) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			String user_code = request.getParameter("user_code");//用户代码
			String role_code = request.getParameter("role_code");//角色代码
			//roleService.setUserDefaultRole(user_code,role_code,loginUser.get("USER_ID"));
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			roleService.setUserDefaultRole(user_code,role_code,String.valueOf(loginUser.get("USER_ID")));
			printHttpServletResponse(new Gson().toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> setUserDefaultRole() Exception : " + e);
		}
	}
	/**
	 *  获取用户额外菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-18
	 */
	@RequestMapping("/getAdditionMenu")
	public void getAdditionMenu(HttpServletRequest request,HttpServletResponse response){
		JsonResult result = new JsonResult();
		result.setSuccess(true);
			
		try{
			String user_code = (String) request.getParameter("user_code");//用户编号
			result.setMsg(userManagerService.getUserAdditionMenu(user_code));
			printHttpServletResponse(new Gson().toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> getAdditionMenu() Exception : " + e);
		  }
		
	}
	
	/**
	 * 设置用户额外菜单
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-18
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/changeUserAdditionMenu")
	public void changeUserAdditionMenu(HttpServletRequest request,HttpServletResponse response){
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		
		try{
			String user_code = (String) request.getParameter("user_code");//用户编号
			String ids = (String) request.getParameter("ids");//菜单编号
			
			String startTime = (String) request.getParameter("startTime");//有效时间
			String endTime = (String) request.getParameter("endTime");//有效时间
			
			Timestamp start = DateUtil.getTimestamp(startTime);
			Timestamp end = DateUtil.getTimestamp(endTime);
			//User loginUser = (User) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//userManagerService.changeUserAdditionMenu(user_code,ids.split(","),loginUser.get("USER_ID"),start,end);
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			userManagerService.changeUserAdditionMenu(user_code,ids.split(","),String.valueOf(loginUser.get("USER_ID")),start,end);
			printHttpServletResponse(new Gson().toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> changeUserAdditionMenu() Exception : " + e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转到修改密码页面
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@RequestMapping("/showChangePassword")
	public String showChangePassword(HttpServletResponse response,HttpServletRequest request){
		request.setAttribute("USER_CODE", request.getParameter("USER_CODE"));
		return "/businessConsole/user/changePassword";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：修改/重置密码
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/setPassword")
	@SystemControllerLog(description="修改重置密码")
	public void setPassword(HttpServletResponse response,HttpServletRequest request){
		//定义返回信息
		JsonResult result = new JsonResult();
		result.setMsg("");
		result.setSuccess(true);
		try {
			//接受前台json数据
			String data = request.getParameter("data");
			//转换成map
			Map<String,Object> param = new Gson().fromJson(data,new TypeToken<Map<String,Object>>() {}.getType());
			//获取session数据
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			param.put("UPDATED_BY", loginUser.get("USER_ID"));//当前登录人ID
			userManagerService.setPassword(param);
			//向前台返回信息
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> setPassword() Exception : "+ e);
		}
		
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转到用户修改页面
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@RequestMapping("/toAccountMgrModify")
	public String toAccountMgrModify(HttpServletResponse response,HttpServletRequest request){
		request.setAttribute("USER_CODE", request.getParameter("USER_CODE"));
		return "/businessConsole/user/accountMgrModify";
	}
	
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取修改账号信息
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@RequestMapping("/getAccountMessage")
	public void getAccountMessage(HttpServletResponse response,HttpServletRequest request){
		try {
			//接受前台json数据
			String data = request.getParameter("data");
			//转换成map
			Map<String,Object> param = new Gson().fromJson(data,new TypeToken<Map<String,Object>>() {}.getType());
			//获取session数据
			Map<String,Object> result = userManagerService.getAccountMessage(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> getAccountMessage() Exception : "+ e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：修改账号
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateAccountManage")
	@SystemControllerLog(description="更新用户信息")
	public void updateAccountManage(HttpServletResponse response,HttpServletRequest request){
		//定义返回信息
		JsonResult result = new JsonResult();
		result.setMsg("");
		result.setSuccess(true);
		try {
			//接受前台json数据
			String data = request.getParameter("data");
			//转换成map
			Map<String,Object> param = new Gson().fromJson(data,new TypeToken<Map<String,Object>>() {}.getType());
			//获取当前登录用户的ID	
			Map<String,Object> loginUser = (Map<String, Object>)request.getSession().getAttribute(GlobalConstant.LOGIN_USER);	
			param.put("YHMC", loginUser.get("USER_ID"));//获取当前登录用户的ID
			userManagerService.updateAccount(param);
			//向前台返回信息
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> updateAccountManage() Exception : "+ e);
		}
		
	}

	/**
	 * 更新用户角色
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-18
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateUserRoles")
	public void updateUserRoles(HttpServletRequest request,HttpServletResponse response) {
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		try{
			String user_code = request.getParameter("user_code");//用户代码
			String ROLE_CODEs = request.getParameter("ROLE_CODEs");//角色代码
			Gson gson = new Gson();
			List<Role> roles =  gson.fromJson(ROLE_CODEs, new TypeToken<List<Role>>(){}.getType());
			//获取session数据
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//User loginUser = (User) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			roleService.updateUserRoles(user_code, roles, String.valueOf(loginUser.get("USER_ID")));//修改
			
			printHttpServletResponse(GsonUtil.toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> updateUserRoles() Exception : " + e);
		}
		
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：删除账号
	 * 描述：略
	 * 作成者：菅文达
	 * 作成时间：2017-10-14
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteAccount")
	public void deleteAccount(HttpServletResponse response,HttpServletRequest request){
		JsonResult result = new JsonResult();
		result.setMsg("");
		result.setSuccess(true);
		try {
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			String USER_CODES = request.getParameter("USER_CODES");
			String UUIDS=request.getParameter("UUIDS");
			userManagerService.deleteAccount(USER_CODES,UUIDS,String.valueOf(loginUser.get("USER_ID")));
			//向前台返回信息
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> deleteAccount() Exception : "+ e);
		}
		
	}
	
	
	/**
	  * 变更用户部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-10-18
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/changeUserDepartment")
	public void changeUserDepartment(HttpServletRequest request,HttpServletResponse response){
		try{
//			String userCode = request.getParameter("userId");//用户代码
//			String areaIds = request.getParameter("areaIds");//小区代码
//			String deptIds = request.getParameter("deptIds");//部门代码
			//获取session数据
//			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
//			userManagerService.changeUserDepartment(USER_CODE,DEPT_CODES.split(","),String.valueOf(loginUser.get("USER_ID")));
//			printHttpServletResponse(GsonUtil.toJson(result),response);
		
			String data = request.getParameter("data");
			Map<String, Object> param = new Gson().fromJson(data,new TypeToken<Map<String,Object>>() {}.getType());
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			param.put("CREATED_BY", String.valueOf(loginUser.get("USER_ID")));
			JsonResult result = userManagerService.changeUserDepartment(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> changeUserDepartment() Exception : " + e);
		}
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转到岗位分配界面
	 * 描述：略
	 * 作成者：尹帅
	 * 作成时间：2017-11-2
	 */
	@RequestMapping("/showAssginStation")
	public String showAssginStation(HttpServletResponse response,HttpServletRequest request){
		request.setAttribute("USER_CODE", request.getParameter("user_code"));
		return "/businessConsole/user/userAssignStation";
	}
	/**
	  * 获取所有小区
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-2
	 */
	@RequestMapping("/getArea")
	public void getArea(HttpServletRequest request,HttpServletResponse response){
		try {
			String data = request.getParameter("data");
		 	Map<String, Object> param = new Gson().fromJson(data,new TypeToken<Map<String, Object>>() {}.getType());
		 	param.put("page", request.getParameter("page"));
			param.put("rows", request.getParameter("rows"));
			param.put("YHMC", request.getParameter("USER_CODE"));
			Grid<Map<String, String>> allArea = userManagerService.getArea(param);
			printHttpServletResponse(new Gson().toJson(allArea),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getArea() Exception : " + e);
		}
	}
	/**
	 * 岗位tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("getStationTree")
	public void getStationTree(HttpServletResponse response,HttpServletRequest request) {
		try {
			String AREA_CODE=request.getParameter("AREA_CODE");
			//查询小区岗位代码=上级岗位的代码
			//String STATION_ROOT=userManagerService.getStationRoot(AREA_CODE);
			Tree treeDeparts = userManagerService.getStationTree("0",AREA_CODE);
			printHttpServletResponse(GsonUtil.toJson(treeDeparts.getChildren()),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getStationTree() Exception : " + e);
		}
	}
	/**
	 * 用户所有部门编号
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/getUserstation")
	public void getUserstation(HttpServletRequest request,HttpServletResponse response){
	        JsonResult jsonResult = new JsonResult();
	        jsonResult.setSuccess(true);
	        try{
	        	String USER_CODE = request.getParameter("USER_CODE");
	        	String AREA_CODE = request.getParameter("AREA_CODE");
	            jsonResult.setMsg(userManagerService.getUserstation(USER_CODE,AREA_CODE));
	            printHttpServletResponse(new Gson().toJson(jsonResult),response);
	        }catch (Exception e){
	        	throw new IServiceException(this.getClass() + " --> getUserstation() Exception : " + e);
	        }
	}
	
	/**
	  * 变更用户岗位
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-2
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateUserStation")
	public void updateUserStation(HttpServletRequest request,HttpServletResponse response){
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		try{
			//获取当前登录用户的ID	
			Map<String,Object> loginUser = (Map<String, Object>)request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			String YHMC=String.valueOf(loginUser.get("USER_ID"));
			String USER_CODE = request.getParameter("USER_CODE");//用户代码
			String POST_CODE = request.getParameter("POST_CODE");//岗位代码
			String AREA_CODE = request.getParameter("AREA_CODE");//小区代码
			userManagerService.updateUserStation(USER_CODE,POST_CODE,YHMC,AREA_CODE);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> updateUserStation() Exception : " + e);
		}
	}
	
	/**
	  * 查找用户所在的部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：尹帅
	  * 作成时间：2017-11-2
	 */
	@RequestMapping("/getAreaDepart")
	public void getAreaDepart(HttpServletRequest request,HttpServletResponse response){
		try{
			//获取当前登录用户的ID	
			String USER_CODE = request.getParameter("USER_CODE");//用户代码
			int num=userManagerService.getAreaDepart(USER_CODE);
			printHttpServletResponse(GsonUtil.toJson(num),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> getAreaDepart() Exception : " + e);
		}
	}
	/**
	  * 用户设置项目 页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：盛婷婷
	  * 作成时间：2018-08-10
	 */
	@RequestMapping("/showUserAssignProject")
	public String showUserAssignProject(HttpServletResponse response,HttpServletRequest request){
		//获取用户代码
		String USER_CODE = request.getParameter("USER_CODE");//用户代码
		request.setAttribute("USER_CODE",USER_CODE);
		return "businessConsole/user/userAssignProject";
		
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：获取所有项目信息
	 * 描述：略
	 * 作成者：盛婷婷
	 * 作成时间：2018-08-10
	 */
	@RequestMapping("/getAllProject")
	public void getAllProject(HttpServletRequest request,HttpServletResponse response){
		try {
			String data = request.getParameter("data");	
			Map<String, String> param = new Gson().fromJson(data,new TypeToken<Map<String, String>>() {}.getType());
			// 获取信息
			Grid<Map<String, String>> grid = userManagerService.getAllProject(param);
			printHttpServletResponse(GsonUtil.toJson(grid), response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "-->getAllProject() exception:" + e);
		}
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：修改项目信息
	 * 描述：略
	 * 作成者：盛婷婷
	 * 作成时间：2018-08-10
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateUserProject")
	public void updateUserProject(HttpServletRequest request,HttpServletResponse response){
		try{
			String data = request.getParameter("data");
			Map<String, Object> param = new Gson().fromJson(data,new TypeToken<Map<String,Object>>() {}.getType());
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			param.put("CREATED_BY", String.valueOf(loginUser.get("USER_ID")));
			JsonResult result = userManagerService.updateUserProject(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> updateUserProject() Exception : " + e);
		}
	}
}
