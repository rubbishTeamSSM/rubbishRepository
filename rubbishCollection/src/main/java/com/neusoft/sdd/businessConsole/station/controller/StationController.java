package com.neusoft.sdd.businessConsole.station.controller;
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
import com.neusoft.sdd.base.systemLog.SystemControllerLog;
import com.neusoft.sdd.businessConsole.station.model.Station;
import com.neusoft.sdd.businessConsole.station.service.StationService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

@Controller
@RequestMapping("/businessConsole/station")
public class StationController extends BaseSimpleFormController {
	
	
	@Autowired
	private StationService stationService;
	
	/**
	 * 跳转到岗位管理页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/systemStationManager")
	public String systemStationManager()
	{
		return "businessConsole/station/stationManager";
	}
	
	/**
	 * 跳转到新增岗位页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/toStationAdd")
	public String toStationAdd(HttpServletRequest request)
	{
		return "businessConsole/station/stationAdd";
	}
	
	/**
	 * 跳转到修改岗位页面
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/toStationMod")
	public String toStationMod()
	{
		return "businessConsole/station/stationModify";
	}
	
	/**
	 * 	显示岗位列表
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/manangerStation")
	public void manangerStation(HttpServletRequest request,HttpServletResponse response){
		try {
			String data = request.getParameter("data");
			Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
			param.put("page", request.getParameter("page"));
			param.put("rows", request.getParameter("rows"));
			Grid<Map<String,String>> grid = stationService.getStations(param);	
			printHttpServletResponse(GsonUtil.toJson(grid),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> manangerStation() Exception : " + e);
		}
		
	}
	
	/**
	 *  新增岗位
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertStation")
	@SystemControllerLog(description = "新增岗位")
	public void insertStation(HttpServletRequest request,HttpServletResponse response,Station page){		
		JsonResult result = new JsonResult();
		try {
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			String data = request.getParameter("data");
			Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
			param.put("CREATED_BY", String.valueOf(loginUser.get("USER_ID")));
			result = stationService.insertStation(param);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + "--> insertStation() Exception ：", e);
		}
	}

	/**
	 *  修改岗位
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/modifyStation")
	@SystemControllerLog(description = "修改岗位")
    public void modifyStation(HttpServletRequest request,HttpServletResponse response,Station page){
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        try{
        	@SuppressWarnings("unchecked")
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			String data = request.getParameter("data");
			Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
    		param.put("UPDATED_BY",  String.valueOf(loginUser.get("USER_ID")));
    		result = stationService.modifyStation(param);
            printHttpServletResponse(GsonUtil.toJson(result),response);
        }catch(Exception e){
			throw new IServiceException(this.getClass() + " --> modifyStation() Exception : " + e);
        }
    }

	/**
	 *  验证岗位名称
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/validStation")
    public void validStation(HttpServletRequest request,HttpServletResponse response)
	{	
		JsonResult result = new JsonResult();
		try {
			 String data = request.getParameter("data");
			 Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
			 result = stationService.validStation(param);
		     printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> validStation() Exception : " + e);
		}
       
    }
	
	/**
	 *  验证400客服
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/validService")
    public void validService(HttpServletRequest request,HttpServletResponse response)
	{	
		JsonResult result = new JsonResult();
		try {
			 String data = request.getParameter("data");
			 Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
			 result = stationService.validService(param);
		     printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> validStation() Exception : " + e);
		}
       
    }

	/**
	 *  岗位详情
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@RequestMapping("/stationDetails")
    public void stationDetails(HttpServletRequest request,HttpServletResponse response){
		try {
			   String data = request.getParameter("data");
			   Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
			   Map<String, Object> result = stationService.stationDetails(param);
		       printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + " --> stationDetails() Exception : " + e);
		}
    }

	/**
	 *  删除岗位
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：菅文达
	  * 作成时间：2017-11-1
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteStation")
	@SystemControllerLog(description = "删除岗位")
	public void deleteStation(HttpServletResponse response,HttpServletRequest request){
		JsonResult result = new JsonResult();
		try {
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			String data = request.getParameter("data");
			Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
			param.put("UPDATED_BY",  String.valueOf(loginUser.get("USER_ID")));
			result = stationService.deleteStation(param);
			//向前台返回信息
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> deleteStation() Exception : "+ e);
		}
		
	}
	

}
