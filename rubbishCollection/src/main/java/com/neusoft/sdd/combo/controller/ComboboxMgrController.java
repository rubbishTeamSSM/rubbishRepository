package com.neusoft.sdd.combo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.systemLog.SystemControllerLog;
import com.neusoft.sdd.combo.service.IComboboxMgrService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

@Controller
@RequestMapping("/comboboxMgr")
public class ComboboxMgrController extends BaseSimpleFormController  {

	@Autowired
	private IComboboxMgrService comboboxMgrService;
	
	/**
	 * 支持字典表、国家、省、市、区等都是返回code,name这种规则的查询
	 * 支持一个where条件的查询
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：严惠惠
	 * 作成时间：2017-5-4
	 */
	@RequestMapping("/getDictionaryList")
	public void getDictionaryList(HttpServletRequest request,HttpServletResponse response) {
		try {
			String data = request.getParameter("data");
			Map<String, String> queryMap = new Gson().fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
			List<Map<String,String>> resultList = comboboxMgrService.getDictionaryList(queryMap);
			printHttpServletResponse(GsonUtil.toJson(resultList),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + "--> getDictionaryList() Exception ：", e);
		}
	}
	
	/**
	 * 支持字典表、国家、省、市、区等都是返回code,name这种规则的查询
	 * 支持一个where条件的查询
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：严惠惠
	 * 作成时间：2017-5-4
	 */
	@RequestMapping("/getCombogridList")
	public void getCombogridList(HttpServletRequest request,HttpServletResponse response) {
		try {
			String data = request.getParameter("data");
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			Map<String, String> queryMap = new Gson().fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
			Grid<Map<String, String>> grid = comboboxMgrService.getCombogridList(queryMap,page,rows);
			printHttpServletResponse(GsonUtil.toJson(grid),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + "--> getCombogridList() Exception ：", e);
		}
	}
	
	
	/**
	 * 
	 * 部门：软件开发事业部
	 * 功能：验证下拉框列表数据是否存在
	 * 描述：略
	 * 作成者：封新芳
	 * 作成时间：2017-6-23
	 */
	@RequestMapping("/comboboxCheck")
	@ResponseBody
	public int comboboxCheck(HttpServletRequest request,HttpServletResponse response){
		int result = 1;
		try {
			String data = request.getParameter("data");
			Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
			result = comboboxMgrService.comboboxCheck(param);
		} catch (Exception e) {
			throw new IServiceException(this.getClass() + "--> getSerCodeList() Exception ：", e);
		}
		return result;
	}

	/**
	 * 
	 * 部门：软件开发事业部
	 * 功能：查重
	 * 描述：略
	 * 作成者：李万迪
	 * 作成时间：2017-6-9
	 */
	@RequestMapping("/duplicateCheck")
	@ResponseBody
	@SystemControllerLog(description="字段查重")
	public int duplicateCheck(HttpServletRequest request,HttpServletResponse response){
		try {
			String data = request.getParameter("data");
			Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
			return comboboxMgrService.duplicateCheck(param);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> duplicateCheck() Exception : "+ e);
		}
	}
}
