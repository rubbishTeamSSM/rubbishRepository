package com.neusoft.sdd.businessConsole.login.service;


import javax.servlet.http.HttpSession;

import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.businessConsole.user.model.User;

public interface ILoginService {
	JsonResult getLoginUser(User user, HttpSession session) throws Exception ;
}
