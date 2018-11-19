package com.neusoft.sdd.businessConsole.login.dao;

import java.util.Map;

import com.neusoft.sdd.businessConsole.user.model.User;

public interface ILoginDao {
	Map<String, Object> getLoginUser(User obj);
	//更新用户信息
	void updateUser(User obj);
	//新增用户信息
	void addNewUser(Map<String,Object> param);
/*	//新增用户角色
	void addNewUserRole(Map<String,Object> param);*/
	//短信验证码验证该用户是否存在
	Map<String, Object> getLoginUserByCode(User obj);
}
