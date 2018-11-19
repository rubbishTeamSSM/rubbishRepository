package com.neusoft.sdd.logManage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.sdd.base.controller.BaseService;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.logManage.dao.ILogMgrDao;
import com.neusoft.sdd.logManage.service.ILogMgrService;

@Service
public class LogMgrServiceImpl extends BaseService implements ILogMgrService{
	@Autowired
	private ILogMgrDao logMgrDao;
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取登陆日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public Grid<Map<String, String>> getLoginLog(Map<String, String> param) {
		Grid<Map<String, String>> grid = new Grid<Map<String, String>>();
		//获取第1页，10条内容，默认查询总数count
		String page = String.valueOf(param.get("page"));
		String rows = String.valueOf(param.get("rows"));
	    PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
	    List<Map<String, String>>list = logMgrDao.getLoginLog(param);
	    grid.setRows(list);
	    grid.setTotal((int)new PageInfo<Map<String,String>>(list).getTotal());
		return grid;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取启动日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public Grid<Map<String, String>> getStartLog(Map<String, String> param) {
		Grid<Map<String, String>> grid = new Grid<Map<String, String>>();
		//获取第1页，10条内容，默认查询总数count
		String page = String.valueOf(param.get("page"));
		String rows = String.valueOf(param.get("rows"));
	    PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
	    List<Map<String, String>>list = logMgrDao.getStartLog(param);
	    grid.setRows(list);
	    grid.setTotal((int)new PageInfo<Map<String,String>>(list).getTotal());
		return grid;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取商铺访问日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public Grid<Map<String, String>> getShopLog(Map<String, String> param) {
		Grid<Map<String, String>> grid = new Grid<Map<String, String>>();
		//获取第1页，10条内容，默认查询总数count
		String page = String.valueOf(param.get("page"));
		String rows = String.valueOf(param.get("rows"));
	    PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
	    List<Map<String, String>>list = logMgrDao.getShopLog(param);
	    grid.setRows(list);
	    grid.setTotal((int)new PageInfo<Map<String,String>>(list).getTotal());
		return grid;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取商品访问日志列表
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public Grid<Map<String, String>> getCommodityLog(Map<String, String> param) {
		Grid<Map<String, String>> grid = new Grid<Map<String, String>>();
		//获取第1页，10条内容，默认查询总数count
		String page = String.valueOf(param.get("page"));
		String rows = String.valueOf(param.get("rows"));
	    PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(rows));
	    List<Map<String, String>>list = logMgrDao.getCommodityLog(param);
	    grid.setRows(list);
	    grid.setTotal((int)new PageInfo<Map<String,String>>(list).getTotal());
		return grid;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：查看登录日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public List<Map<String, String>> getLoginLogInfo(Map<String, String> param) {
		return logMgrDao.getLoginLog(param);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：查看启动日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public List<Map<String, String>> getStartLogInfo(Map<String, String> param) {
		return logMgrDao.getStartLog(param);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：查看商铺访问日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public List<Map<String, String>> getShopLogInfo(Map<String, String> param) {
		return logMgrDao.getShopLog(param);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：查看商品访问日志详情
	 * 描述：略
	 * 作成者：李好旗
	 * 作成时间：2017-10-26
	 */
	@Override
	public List<Map<String, String>> getCommodityLogInfo(Map<String, String> param) {
		return logMgrDao.getCommodityLog(param);
	}

	/**
	 * 日志新增
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：严惠惠
	 * 作成时间：2017-5-20
	 */
	@Override
	public void addLog(Map<String, String> paramMap) {
		logMgrDao.addLog(paramMap);
	}
}
