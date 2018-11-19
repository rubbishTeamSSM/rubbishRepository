package com.neusoft.sdd.businessConsole.department.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Tree;
import com.neusoft.sdd.businessConsole.department.model.Department;
import com.neusoft.sdd.businessConsole.department.service.DepartmentService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;


@Controller
@RequestMapping("/businessConsole/department")
public class DepartmentController extends BaseSimpleFormController {
	
	@Autowired
	private DepartmentService departmentService;

	
	/**
	 * 跳转到部门首页
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-29
	 */
	@RequestMapping("/systemDepartmentManager")
	public String systemDepartmentManager()
	{
		return "businessConsole/department/departmentManager";
	}
	
	/**
	 * 跳转到新增页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-29
	 */
	@RequestMapping("/departmentAdd")
	public String departmentAdd()
	{
		return "businessConsole/department/departmentAdd";
	}
	
	
	/**
	 * 跳转到修改页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-29
	 */
	@RequestMapping("/departmentMod")
	public String departmentMod()
	{
		return "businessConsole/department/departmentModify";
	}
	
	/**
	 * 部门管理
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/managerDepartment")
	public void managerDepartment(HttpServletRequest request,HttpServletResponse response){
		try {
			String pid = request.getParameter("pid");
			if(null == pid){
				pid = GlobalConstant.DEPARTMENT_ROOT;
			}
			
			List<Department> departs = departmentService.getSystemDepartment(pid);
			printHttpServletResponse(new Gson().toJson(departs),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> managerDepartment() Exception : " + e);
		}
		
	}
	
	/**
	 * 新增部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/addDepartment")
	public void addDepartment(HttpServletRequest request,HttpServletResponse response,Department page){
		JsonResult result = new JsonResult();
		try{
			//新增时间  录入人员
			/*User loginUser  = (User) ServletUtil.getSessionAttr(request, GlobalConstant.LOGIN_USER);
			page.setCREATED_BY(loginUser.get("USER_ID"));*/
			
			result.setSuccess(true);
			departmentService.addDepartment(page);

			printHttpServletResponse(GsonUtil.toJson(result),response);
			
		}catch(Exception e){
			throw new IServiceException(this.getClass() + " --> addDepartment() Exception : " + e);
		}
		
		
	}
	
	/**
	 * 修改部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("modifyDepartment")
	public void modifyDepartment(HttpServletRequest request,HttpServletResponse response,Department page){
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		
		try{
			//新增时间  录入人员
			/*User loginUser  = (User) ServletUtil.getSessionAttr(request, GlobalConstant.LOGIN_USER);
			page.setUPDATED_BY(loginUser.get("USER_ID"));
			*/
			departmentService.modifyDepartment(page);
			printHttpServletResponse(GsonUtil.toJson(result),response);
			
		}catch(Exception e){
			result.setSuccess(false);
			throw new IServiceException(this.getClass() + " --> modifyDepartment() Exception : " + e);
		}
	}
	
	/**
	 * 部门tree
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("getDepartmentTree")
	public void getDepartmentTree(HttpServletResponse response) {
		try {
			Tree treeDeparts = departmentService.getDepartmentTree(GlobalConstant.DEPARTMENT_ROOT);
			StringBuilder builder = new StringBuilder();

			builder.append("[");
			builder.append(new Gson().toJson(treeDeparts));
			builder.append("]");
			printHttpServletResponse(builder.toString(),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getDepartmentTree() Exception : " + e);
		}
		
	}
	
	
	/**
	 * 用户所有部门编号
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/getUserDepartment")
	public void getUserDepartment(HttpServletRequest request,HttpServletResponse response){
	        try{
	        	String userCode = request.getParameter("user_code");
	        	List<Map<String,String>> resultList = departmentService.getUserDepartment(userCode);
	            printHttpServletResponse(GsonUtil.toJson(resultList),response);
	        }catch (Exception e){
	        	throw new IServiceException(this.getClass() + " --> getUserDepartment() Exception : " + e);
	        }
	}
	
	/**
	 * 部门详情
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/departmentDetails")
	public void departmentDetails(HttpServletRequest request,HttpServletResponse response){
		try {
			String DEPT_CODE = request.getParameter("DEPT_CODE");//部门代码
			Department dep = departmentService.getDepartmentDet(DEPT_CODE);
			printHttpServletResponse(GsonUtil.toJson(dep),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> departmentDetails() Exception : " + e);
		}
		
	}
	
	/**
	 * 验证部门代码
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/validDepartment")
	public void validDepartment(HttpServletRequest request,HttpServletResponse response){
		try {
			String DEPT_CODE = request.getParameter("DEPT_CODE");	
			printHttpServletResponse(GsonUtil.toJson(departmentService.validDepartment(DEPT_CODE)),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> validDepartment() Exception : " + e);
		}
	}
	
	/**
	 * 删除部门
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：严惠惠
	  * 作成时间：2015-10-28
	 */
	@RequestMapping("/delDepartment")
	public void delDepartment(HttpServletRequest request,HttpServletResponse response){
	        JsonResult jsonResult = new JsonResult();
	        jsonResult.setSuccess(true);
	        try{
	        	Department  dept = new Department();
	        	//新增时间  录入人员
			/*	User loginUser  = (User) ServletUtil.getSessionAttr(request, GlobalConstant.LOGIN_USER);
				dept.setUPDATED_BY(loginUser.get("USER_ID"));*/
	        	String depIds = request.getParameter("depId");
	        	dept.setDEPT_CODE(depIds);
	            departmentService.delDepartment(dept);
	            printHttpServletResponse(new Gson().toJson(jsonResult),response);
	        }catch (Exception e){
	            throw new IServiceException(this.getClass() + " --> delDepartment() Exception : " + e);
	        }
	}

}
