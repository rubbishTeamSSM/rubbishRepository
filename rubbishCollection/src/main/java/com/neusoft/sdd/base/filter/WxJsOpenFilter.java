/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :WxJsFilter.java
 * 创建人  :Administrator
 * 创建时间:2015年5月29日
 */

package com.neusoft.sdd.base.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.neusoft.sdd.base.model.wx.dto.APPUser;
import com.neusoft.sdd.util.wx.PropertiesUtils;
import com.neusoft.sdd.util.wx.SignOpen;
import com.neusoft.sdd.util.wx.WxOpenInterfaceUtils;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月29日
 * @since Neusoft 001
 */
public class WxJsOpenFilter implements Filter {

	protected String appId = null;
	protected FilterConfig filterConfig = null;
	private static final Logger LOG = Logger.getLogger(WxJsOpenFilter.class);
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		LOG.debug("Enter WxJsOpenFilter.doFilter()................");
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String url = null;
		if (StringUtils.isNotBlank(httpRequest.getQueryString())) {
			url = httpRequest.getRequestURL().toString() + "?"
					+ httpRequest.getQueryString();
		} else {
			url = httpRequest.getRequestURL().toString();
		}

		System.out.println(url);

		// JS_SDK使用参数
		try {
			Map<String, String> signMap = SignOpen.sign(url);
			request.setAttribute("nonceStr", signMap.get("nonceStr"));
			request.setAttribute("timestamp", signMap.get("timestamp"));
			request.setAttribute("signature", signMap.get("signature"));
			request.setAttribute("appId", PropertiesUtils.getValue("appId"));
		} catch (NullPointerException e) {
			LOG.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// OAuth2.0验证接口来获取成员的身份信息
		APPUser appUser = (APPUser) httpRequest.getSession().getAttribute("appUser");
		String code = request.getParameter("code");
		LOG.debug("OAuth2.0验证接口来获取成员的身份信息................");
		LOG.debug("code is [" + code + "]");

		if ((null == appUser || StringUtils.isBlank(appUser.getOpenid()))
				&& StringUtils.isNotBlank(code)) {
			try {
				// OAuth2.0验证接口来获取成员的身份信息
				APPUser userinfo = WxOpenInterfaceUtils.getOauthUser(code);

				if (StringUtils.isNotBlank(userinfo.getOpenid())
						&& StringUtils.isNotBlank(userinfo
								.getAccess_token())) {
					
					//获取access_token
					//String accessToken = WxOpenInterfaceUtils.getOpenToken();

					// 获取用户详细信息
					//appUser = WxOpenInterfaceUtils.getAppUserInfo(accessToken,userinfo.getOpenid());
					
					appUser = WxOpenInterfaceUtils.getAppUserBy(userinfo.getAccess_token(), userinfo.getOpenid());
					
					
					appUser.setOpenid(userinfo.getOpenid());
					httpRequest.getSession().setAttribute("appUser", appUser);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		LOG.debug("Exit WxJsOpenFilter.doFilter()................");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("WxJsFilter is init...........");

		this.filterConfig = arg0;
		this.appId = filterConfig.getInitParameter("appId");
		System.out.println(appId);
		System.out.println("初始化完成。。。。。。。。");

	}

}
