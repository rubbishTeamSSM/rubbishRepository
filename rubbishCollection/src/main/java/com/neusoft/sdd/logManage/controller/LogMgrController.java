package com.neusoft.sdd.logManage.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.logManage.service.ILogMgrService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

@Controller
@RequestMapping("/logManage/LogMgrController/")
public class LogMgrController extends BaseSimpleFormController{
	@Autowired
	private ILogMgrService logMgrService;
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转日志管理页面
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("logMgr")
	public String logMgr(HttpServletRequest request,HttpServletResponse response){
		return "/logManage/logMgr";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转登录日志详情页面
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("/loginLogInfo")
	public String loginLogInfo(){
		return "/logManage/loginLogInfo";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转启动日志详情页面
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("/startLogInfo")
	public String startLogInfo(){
		return "/logManage/startLogInfo";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转商铺访问日志详情页面
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("/shopLogInfo")
	public String shopLogInfo(){
		return "/logManage/shopLogInfo";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转商品访问日志详情页面
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("/commodityLogInfo")
	public String commodityLogInfo(){
		return "/logManage/commodityLogInfo";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取登陆日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getLoginLog")
	public void getLoginLog(HttpServletResponse response,HttpServletRequest request){
		try {
			//接受前台json数据
			String data = request.getParameter("data");
			Map<String,String> param = new Gson().fromJson(data,new TypeToken<Map<String,String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", loginUser.get("ISOLATION_CODE"));
			param.put("page", request.getParameter("page"));
			param.put("rows", request.getParameter("rows"));
			Grid<Map<String,String>> result = logMgrService.getLoginLog(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> getLoginLog() Exception : "+ e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取启动日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getStartLog")
	public void getStartLog(HttpServletResponse response,HttpServletRequest request){
		try {
			//接受前台json数据
			String data = request.getParameter("data");
			Map<String,String> param = new Gson().fromJson(data,new TypeToken<Map<String,String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", loginUser.get("ISOLATION_CODE"));
			param.put("page", request.getParameter("page"));
			param.put("rows", request.getParameter("rows"));
			Grid<Map<String,String>> result = logMgrService.getStartLog(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> getStartLog() Exception : "+ e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取商铺访问日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getShopLog")
	public void getShopLog(HttpServletResponse response,HttpServletRequest request){
		try {
			//接受前台json数据
			String data = request.getParameter("data");
			Map<String,String> param = new Gson().fromJson(data,new TypeToken<Map<String,String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", loginUser.get("ISOLATION_CODE"));
			param.put("page", request.getParameter("page"));
			param.put("rows", request.getParameter("rows"));
			Grid<Map<String,String>> result = logMgrService.getShopLog(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> getShopLog() Exception : "+ e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取商品访问日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getCommodityLog")
	public void getCommodityLog(HttpServletResponse response,HttpServletRequest request){
		try {
			//接受前台json数据
			String data = request.getParameter("data");
			Map<String,String> param = new Gson().fromJson(data,new TypeToken<Map<String,String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", loginUser.get("ISOLATION_CODE"));
			param.put("page", request.getParameter("page"));
			param.put("rows", request.getParameter("rows"));
			Grid<Map<String,String>> result = logMgrService.getCommodityLog(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> getCommodityLog() Exception : "+ e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取登录日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getLoginLogInfo")
	public void getLoginLogInfo(HttpServletRequest request,HttpServletResponse response) {
		try {
			String data = request.getParameter("data");
			Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", String.valueOf(loginUser.get("ISOLATION_CODE")));
			List<Map<String, String>> result = logMgrService.getLoginLogInfo(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getLoginLogInfo() Exception : " + e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取启动日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getStartLogInfo")
	public void getStartLogInfo(HttpServletRequest request,HttpServletResponse response) {
		try {
			String data = request.getParameter("data");
			Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", String.valueOf(loginUser.get("ISOLATION_CODE")));
			List<Map<String, String>> result = logMgrService.getStartLogInfo(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getStartLogInfo() Exception : " + e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取商铺访问日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getShopLogInfo")
	public void getShopLogInfo(HttpServletRequest request,HttpServletResponse response) {
		try {
			String data = request.getParameter("data");
			Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", String.valueOf(loginUser.get("ISOLATION_CODE")));
			List<Map<String, String>> result = logMgrService.getShopLogInfo(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getShopLogInfo() Exception : " + e);
		}
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取商品访问日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@RequestMapping("getCommodityLogInfo")
	public void getCommodityLogInfo(HttpServletRequest request,HttpServletResponse response) {
		try {
			String data = request.getParameter("data");
			Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
			//Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//param.put("ISOLATION_CODE", String.valueOf(loginUser.get("ISOLATION_CODE")));
			List<Map<String, String>> result = logMgrService.getCommodityLogInfo(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> getCommodityLogInfo() Exception : " + e);
		}
	}
	
}
