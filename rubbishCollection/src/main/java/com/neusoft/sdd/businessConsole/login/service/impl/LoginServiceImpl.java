package com.neusoft.sdd.businessConsole.login.service.impl;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseService;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.businessConsole.login.dao.ILoginDao;
import com.neusoft.sdd.businessConsole.login.service.ILoginService;
import com.neusoft.sdd.businessConsole.role.dao.IRoleDao;
import com.neusoft.sdd.businessConsole.role.model.Role;
import com.neusoft.sdd.businessConsole.user.model.User;
import com.neusoft.sdd.util.commonUtil.SHACoder;

@Service
public class LoginServiceImpl extends BaseService implements ILoginService {

	@Autowired
	private ILoginDao loginDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	/**
	 * 部门：软件开发事业部
	 * 功能：登录
	 * 描述：略
	 * 作成者：胡威
	 * 作成时间：2017-10-18
	 */
	@Override
	public JsonResult getLoginUser(User user, HttpSession session) throws Exception {
		JsonResult result = new JsonResult();
		result.setSuccess(true);

		Map<String,Object> loginUser = null;
		// 手机号+密码登录
		if(null != user.getCode() && !"".equals(user.getCode())){
			loginUser = loginDao.getLoginUserByCode(user);
		} else {// 账号+密码登录
			String password = SHACoder.encodeSHA256(user.getUSER_PWD());
			user.setUSER_PWD(password);
			loginUser = loginDao.getLoginUser(user);//查询在本地库中是否存在本条数据
			if(null == loginUser){
				result.setSuccess(false);
				result.setMsg("登录失败，用户不存在！");//失败原因
				
				return result;
			}
			if(null != loginUser && !password.equals(loginUser.get("USER_PWD"))){
				result.setSuccess(false);
				result.setMsg("登录失败，密码错误！");//失败原因
				
				return result;
			}
		}
		
		if(result.isSuccess()){
			session.setAttribute(GlobalConstant.LOGIN_USER, loginUser);// 将用户信息放入session
			// 查询用户默认角色，并将默认角色放到session
			Role role = roleDao.getUserDefaultRole(String.valueOf(loginUser.get("USER_ID")));
			session.setAttribute(GlobalConstant.LOGIN_USER_DEFAULT_ROLE, role);
		}
		
		return result;
		
	}
}

