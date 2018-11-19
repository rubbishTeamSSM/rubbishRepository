package com.neusoft.sdd.businessConsole.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.businessConsole.login.service.ILoginService;
import com.neusoft.sdd.businessConsole.user.model.User;
import com.neusoft.sdd.util.commonUtil.GsonUtil;
import com.neusoft.sdd.util.commonUtil.RandomUtil;
import com.neusoft.sdd.util.file.SmsDemo;

@Controller
@RequestMapping("businessConsole/login")
public class LoginCntroller extends BaseSimpleFormController {

	@Autowired
	private ILoginService loginService;
	
	/**
	 * 部门：软件开发事业部
	 * 功能：登录
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-18
	 */
	@RequestMapping("/getLoginUser")
	@ResponseBody
	public JsonResult getLoginUser(HttpSession session, User user,HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setSuccess(true);
		try{
			jsonResult = loginService.getLoginUser(user, session);
		 }catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> getLoginUser() Exception : "+ e);
		}
		return jsonResult;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转首页
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-18
	 */
	@RequestMapping("/showViews")
	public String showViews(HttpServletRequest request){
		//返回登录用户所有角色
//		User loginUser = (User) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
//		
//		List<Role> userRoles = roleService.getUserRoles(loginUser.get("USER_ID"));
//		
//		request.setAttribute("userRoles",userRoles);
		return "admin";
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：账号密码登录
	 * 描述：略
	 * 作成者：印政权
	 * 作成时间：2017-11-14
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		return "index";
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：跳转手机号登录
	 * 描述：略
	 * 作成者：印政权
	 * 作成时间：2017-11-14
	 */
	@RequestMapping("/loginMobile")
	public String loginMobile(HttpServletRequest request){
		return "loginMobile";
	}
	/**
	 * 部门：软件开发事业部
	 * 功能： 短信验证码登录
	 * 描述：略
	 * 作成者：胡威
	 * 作成时间：2017-10-18
	 */
	@RequestMapping("/getLoginCode")
	public void  getLoginCode(HttpServletResponse response,HttpSession session,HttpServletRequest request)
	{
		String data = request.getParameter("data");
		Map<String, String> param = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
		String mobile = String.valueOf(param.get("USER_ACCNT"));//获取账号信息
		Map<String,String> resultParam=new HashMap<String, String>();
		if(null!=mobile){
		String note=RandomUtil.radmonkey(6);//获取验证码随机数
		resultParam.put("mobile", mobile);
		resultParam.put("note", note);
		try {
			 SmsDemo.sendSms(mobile, "'"+note+"'");
		     resultParam.put("msg", "success");
		} catch (ClientException e) {
			 e.printStackTrace();
			 resultParam.put("msg", "false");
		}
	   }else{
			resultParam.put("msg", "false");
		}
		 printHttpServletResponse(GsonUtil.toJson(resultParam), response);
	}
	/**
	 * 部门：软件开发事业部
	 * 功能：退出
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2017-10-18
	 */
	@RequestMapping("/getloginOut")
	public String getloginOut(HttpSession session){
		session.removeAttribute(GlobalConstant.LOGIN_USER);
		session.removeAttribute(GlobalConstant.LOGIN_USER_DEFAULT_ROLE);
		session.removeAttribute(GlobalConstant.LOGIN_USER_CURRENT_ROLE);
		return "redirect:showIndex.do";
	}
}
