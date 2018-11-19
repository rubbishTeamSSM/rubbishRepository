package com.neusoft.sdd.logManage.service;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.base.model.easyui.Grid;

public interface ILogMgrService {
	//获取登录日志
	Grid<Map<String, String>> getLoginLog(Map<String, String> param);
	//获取启动日志
	Grid<Map<String, String>> getStartLog(Map<String, String> param);
	//获取商铺访问日志
	Grid<Map<String, String>> getShopLog(Map<String, String> param);
	//获取商品访问日志
	Grid<Map<String, String>> getCommodityLog(Map<String, String> param);
	//获取登陆日志详情
	List<Map<String, String>> getLoginLogInfo(Map<String, String> param);
	//获取启动日志详情
	List<Map<String, String>> getStartLogInfo(Map<String, String> param);
	//获取商铺访问日志详情
	List<Map<String, String>> getShopLogInfo(Map<String, String> param);
	//获取商品访问日志详情
	List<Map<String, String>> getCommodityLogInfo(Map<String, String> param);
	
	void addLog(Map<String, String> paramMap);
}
