package com.neusoft.sdd.base.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.util.commonUtil.PropertiesUtil;

public class ConversationFilter implements Filter {

	
	private static String[] ignoreUrl = null;
	
	public void init(FilterConfig arg0) throws ServletException {
		/**
		 * web.xml的加载顺序是context-param -> listener -> filter -> servlet 
		 * 因为filter初始化时，注解的bean还没初始化，没法注入
		 * 通过ServletContext对象获取ApplicationContext对象，然后在通过它获取需要的类实例
		 * 这种方式适合于采用Spring框架的B/S系统
		 */
		/* add by zhu.qf 2015-02-06 start */
		GlobalConstant.WEB = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		/* add by zhu.qf 2015-02-06 end */
		
		Properties p =  PropertiesUtil.loadProperties("conf/filterPath.properties");
		String urls = p.getProperty("filter_url");
		if(null != urls)
		{
			ignoreUrl = urls.split(",");
		}
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if(isIntercept(req)){
			//ajax请求
            if (req.getHeader("x-requested-with") != null
                    && req.getHeader("x-requested-with")
                    .equalsIgnoreCase("XMLHttpRequest")) {
               // res.setHeader("sessionstatus", "sessionOut");
                res.sendError(403);
                return;
            }else{
                res.sendRedirect(req.getContextPath() + "/sessionError.jsp");
            }
		}else{
			
			chain.doFilter(request, response);
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean isIntercept(HttpServletRequest req){
		HttpSession session = req.getSession();
		String requestUrl = req.getRequestURL().toString();
		String queryString = req.getQueryString();
		if (queryString != null && queryString.length() > 0) {
			requestUrl += "?" + queryString;
		}
		String contextPath = req.getContextPath();
		requestUrl = requestUrl.substring(requestUrl.indexOf(contextPath)
				+ contextPath.length());
		
		if(requestUrl.indexOf(".do") > 0 || requestUrl.indexOf(".jsp") >0){
			/*if(requestUrl.indexOf(ignoreUrl[0]) >=0 
					||requestUrl.indexOf(ignoreUrl[1]) >=0 
					|| requestUrl.indexOf(ignoreUrl[2]) >=0
					|| requestUrl.indexOf(ignoreUrl[3]) >=0){
				return false;
			}else{
				Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
				if(null == loginUser){
					return true;
				}
			}*/
			
			for (int i = 0; i < ignoreUrl.length; i++) {
				if(requestUrl.indexOf(ignoreUrl[i]) >=0 ){
					return false;
				}
			}
			
			Map<String,Object> loginUser = (Map<String, Object>) session.getAttribute(GlobalConstant.LOGIN_USER);
			if(null == loginUser){
				return true;
			}
			
		}else{
			return false;
		}
		return false;
	}

	public void destroy() {
	}

}
