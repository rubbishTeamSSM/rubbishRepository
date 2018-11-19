package com.neusoft.sdd.logManage.dao;

import java.util.List;
import java.util.Map;

public interface ILogMgrDao {
	List<Map<String, String>> getLoginLog(Map<String, String> param);//获得登陆日志列表
	//Map<String, String> getLoginLogInfo(Map<String, String> param);//获得登录日志详情
	List<Map<String, String>> getStartLog(Map<String, String> param);//获得登陆日志列表
	//Map<String, String> getStartLogInfo(Map<String, String> param);//获得登录日志详情
	List<Map<String, String>> getShopLog(Map<String, String> param);//获得登陆日志列表
	//Map<String, String> getShopLogInfo(Map<String, String> param);//获得登录日志详情
	List<Map<String, String>> getCommodityLog(Map<String, String> param);//获得登陆日志列表
	//Map<String, String> getCommodityLogInfo(Map<String, String> param);//获得登录日志详情
	
	void addLog(Map<String, String> paramMap);
}
