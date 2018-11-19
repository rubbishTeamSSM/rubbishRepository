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

import com.neusoft.sdd.base.model.wx.dto.UserDto;
import com.neusoft.sdd.util.wx.SignEnterprise;
import com.neusoft.sdd.util.wx.WxEnterpriseInterfaceUtils;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月29日
 * @since Neusoft 001
 */
public class WxJsEnterpriseFilter implements Filter {

	protected String appId = null;
	protected FilterConfig filterConfig = null;
	private static final Logger LOG = Logger.getLogger(WxJsEnterpriseFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		LOG.debug("Enter WxJsEnterpriseFilter.doFilter()................");
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String url = null;
		if (StringUtils.isNotBlank(httpRequest.getQueryString())) {
			url = httpRequest.getRequestURL().toString() + "?"
					+ httpRequest.getQueryString();
		} else {
			url = httpRequest.getRequestURL().toString();
		}

		System.out.println(url);

		try {
			Map<String, String> signMap = SignEnterprise.sign(url);
			request.setAttribute("nonceStr", signMap.get("nonceStr"));
			request.setAttribute("timestamp", signMap.get("timestamp"));
			request.setAttribute("signature", signMap.get("signature"));
			request.setAttribute("appId", appId);
		} catch (NullPointerException e) {
			LOG.error("企业号：" + e);
			e.printStackTrace();
		}

		// OAuth2.0验证接口来获取成员的身份信息。
		UserDto userDto = (UserDto) httpRequest.getSession().getAttribute(
				"userInfo");
		String code = request.getParameter("code");
		String agentId = request.getParameter("agentId");
		LOG.debug("OAuth2.0验证接口来获取成员的身份信息................");
		LOG.debug("code is [" + code + "]");
		LOG.debug("agentId is [" + agentId + "]");
		if ((null == userDto || StringUtils.isBlank(userDto.getId()))
				&& StringUtils.isNotBlank(code)
				&& StringUtils.isNotBlank(agentId)) {
			try {
				Map<String, Object> codeMap = WxEnterpriseInterfaceUtils.getUserByCode(code, agentId);

				/* modify by zhu.qf 2015-06-23 start */
				userDto = new UserDto();
				if (null != codeMap.get("UserId")) {
					String userId = codeMap.get("UserId").toString();
					LOG.debug("userId is [" + userId + "]");

					Map<String, Object> userMap = WxEnterpriseInterfaceUtils.getUserInfoById(userId);
					userDto.setId(userMap.get("userid") == null ? "" : userMap.get("userid").toString());
					userDto.setName(userMap.get("name") == null ? "" : userMap.get("name").toString());
					userDto.setEmail(userMap.get("email") == null ? "" : userMap.get("email").toString());
					userDto.setPhone(userMap.get("mobile") == null ? "" : userMap.get("mobile").toString());
					userDto.setStation(userMap.get("position") == null ? "" : userMap.get("position").toString());
					userDto.setAvatar(userMap.get("avatar") == null ? "" : userMap.get("avatar").toString());
				}
				if (null != codeMap.get("OpenId")) {
					String openId = codeMap.get("OpenId").toString();
					userDto.setOpenId(openId);
				}
				/* modify by zhu.qf 2015-06-23 end */

				LOG.debug("userInfo is [" + userDto + "]");
				httpRequest.getSession().setAttribute("userInfo", userDto);

			} catch (Exception e) {
				LOG.error(e);
				e.printStackTrace();
			}
		}
		
		LOG.debug("Exit WxJsEnterpriseFilter.doFilter()................");
		chain.doFilter(request, response);
	}

	/**
	 * [简要描述]:<br/>
	 * [详细描述]:<br/>
	 * 
	 * @param arg0
	 * @throws ServletException
	 * @exception
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("WxJsEnterpriseFilter is init...........");

		this.filterConfig = arg0;
		this.appId = filterConfig.getInitParameter("appId");
		System.out.println(appId);
		System.out.println("初始化完成。。。。。。。。");

	}

}
