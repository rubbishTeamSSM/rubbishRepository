package com.neusoft.sdd.base.systemLog;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.logManage.service.ILogMgrService;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;
    
/**  
 * 切点类  
 * @author tiangai  
 * @since 2014-08-05 Pm 20:35  
 * @version 1.0  
 */    
@Aspect
/*@Order(1) */ //设置切面的优先级：如果有多个切面，可通过设置优先级控制切面的执行顺序（数值越小，优先级越高）
@Component    
public  class SystemLogAspect {    
    //注入Service用于把日志保存数据库    
    @Resource    
     private ILogMgrService logService;    
    //本地异常日志记录对象    
     private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);    

    //Service层切点    
     @Pointcut("execution (* com.neusoft.sdd..controller.*.*(..))")    
     public  void serviceAspect() {    
    }    
    

    //Controller层切点    
    @Pointcut("execution (* com.neusoft.sdd..controller.*.add*(..)) || " +
    		  "execution (* com.neusoft.sdd..controller.*.mod*(..)) ||" +
    		  "execution (* com.neusoft.sdd..controller.*.del*(..)) ||" +
    		  "execution (* com.neusoft.sdd..controller.*.update*(..)) ||" +
    		  "execution (* com.neusoft.sdd..controller.*.save*(..)) ||" +
    		  "execution (* com.neusoft.sdd..controller.*.insert*(..)) ||" +
    		  "execution (* com.neusoft.sdd..controller.*.remove*(..)) ||" +
    		  "execution (* com.neusoft.sdd..controller.*.edit*(..)) ||" +
    		  "execution (* com.neusoft.sdd..controller.*.change*(..)) "+
    		  "execution (* com.neusoft.sdd..controller.*.set*(..)) ")    
     public  void controllerAspect() {    
    }    
    
    
    
    /**  
     * 前置通知 用于拦截Controller层记录用户的操作  
     *  
     * @param joinPoint 切点  
     */    
    @SuppressWarnings("unchecked")
	@Before("controllerAspect()")    
     public  void doBefore(JoinPoint joinPoint) {    
    	  try {    
	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
	        //读取session中的用户    
	        Map<String,Object> user = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER); 
	        //请求的IP    
	        String ip = request.getRemoteAddr();   
	        
	        Map<String, String> paramMap = new HashMap<String, String>();
	        paramMap.put("IP", ip);//请求IP
	        paramMap.put("FUN_NAME", (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));//请求方法
	        paramMap.put("FUN_DESC", getControllerMethodDescription(joinPoint));//方法描述
	        paramMap.put("CREATED_BY", String.valueOf(user.get("USER_ID")));//请求人
	        paramMap.put("FUN_PARAM", request.getParameter("data"));//请求参数
	        paramMap.put("OP_TYPE", "0");//0 操作日志 1异常日志
	        paramMap.put("ID", UUIDUtil.uuidStr());
	        //paramMap.put("ISOLATION_CODE", String.valueOf(user.get("ISOLATION_CODE")));
	        
            logService.addLog(paramMap);
        }  catch (Exception e) {    
            //记录本地异常日志    
            logger.error("==前置通知异常==");    
            logger.error("异常信息:{}", e.getMessage());    
        }    
    }    
    
    
    /**  
     * 异常通知 用于拦截service层记录异常日志  
     *  
     * @param joinPoint  
     * @param e  
     */    
    @SuppressWarnings("unchecked")
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")    
     public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {  
    	try{
    		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
 	        //读取session中的用户    
 	        Map<String,Object> user = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER); 
 	        //请求的IP    
 	        String ip = request.getRemoteAddr();   
        
	        Map<String, String> paramMap = new HashMap<String, String>();
	        paramMap.put("IP", ip);//请求IP
	        paramMap.put("FUN_NAME", (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));//请求方法
	        paramMap.put("FUN_DESC", getControllerMethodDescription(joinPoint));//方法描述
	        paramMap.put("CREATED_BY", String.valueOf(user.get("USER_ID")));//请求人
	        paramMap.put("FUN_PARAM", request.getParameter("data"));//请求参数
	        paramMap.put("OP_TYPE", "1");//0 操作日志 1异常日志
	        paramMap.put("ID", UUIDUtil.uuidStr());
	        //paramMap.put("ISOLATION_CODE", String.valueOf(user.get("ISOLATION_CODE")));  
	        paramMap.put("DETAIL", e.getMessage());//异常信息
	        
            logService.addLog(paramMap);
        
        }  catch (Exception ex) {    
            //记录本地异常日志    
            logger.error("==异常通知异常==");    
            logger.error("异常信息:{}", ex.getMessage());    
        }    
    
    }    
    
    
    /**  
     * 获取注解中对方法的描述信息 用于service层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static String getServiceMthodDescription(JoinPoint joinPoint)    
             throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) { 
                	 if(null == method.getAnnotation(SystemControllerLog. class)){
               		  return "";
               	     }
                    description = method.getAnnotation(SystemServiceLog. class).description();    
                     break;    
                }    
            }    
        }    
         return description;    
    }    
     
     
     /**  
      * 获取注解中对方法的描述信息 用于Controller层注解  
      *  
      * @param joinPoint 切点  
      * @return 方法描述  
      * @throws Exception  
      */    
      public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {    
         String targetName = joinPoint.getTarget().getClass().getName();    
         String methodName = joinPoint.getSignature().getName();    
         Object[] arguments = joinPoint.getArgs();    
         Class targetClass = Class.forName(targetName);    
         Method[] methods = targetClass.getMethods();    
         String description = "";    
          for (Method method : methods) {    
              if (method.getName().equals(methodName)) {    
                 Class[] clazzs = method.getParameterTypes();    
                  if (clazzs.length == arguments.length) { 
                	  if(null == method.getAnnotation(SystemControllerLog. class)){
                		  return "";
                	  }
                	  
                     description = method.getAnnotation(SystemControllerLog. class).description();    
                     break;    
                 }    
             }    
         }    
          return description;    
     }    
        
}    
