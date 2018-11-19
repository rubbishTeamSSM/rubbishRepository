package com.neusoft.sdd.businessConsole.task.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.base.systemLog.SystemControllerLog;
import com.neusoft.sdd.businessConsole.task.model.JobClassInfo;
import com.neusoft.sdd.businessConsole.task.model.JobExec;
import com.neusoft.sdd.businessConsole.task.model.JobInfo;
import com.neusoft.sdd.businessConsole.task.service.JobInfoService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;
import com.neusoft.sdd.util.commonUtil.StringUtil;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;

@Controller
@RequestMapping("/businessConsole/jobInfo")
public class JobInfoController extends BaseSimpleFormController
{
	
    @Autowired
    private JobInfoService jobInfoService;
    
    /**
     * 跳转到管理页面
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-30
     */
    @RequestMapping("/jobInfoList")
    public String jobInfoList()
    {
    	return "businessConsole/task/jobInfo";
    }

    /**
     * 跳转到克隆表达式页面
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-10-30
     */
    @RequestMapping("/cronExpress")
    public String cronExpress()
    {
    	return "businessConsole/task/cronExpress";
    }
    
    /**
     * 获取检查排班列表
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @RequestMapping("/getJobInfoList")
    public void getJobInfoList(HttpServletRequest request,HttpServletResponse response,JobInfo model)
    {
        try
        {
            String data = request.getParameter("data");
            String cSEXj = request.getParameter("cSEXj");
            
            JobInfo obj = GsonUtil.toBean(data, JobInfo.class);
            model.setJOB_NAME(obj.getJOB_NAME());
            model.setEXEC_CLASS_NAME(obj.getEXEC_CLASS_NAME());
            model.setCSEXj(cSEXj);
            
            Grid<JobInfo> grid = jobInfoService.getJobInfoList(model);
            printHttpServletResponse(GsonUtil.toJsonWithDateFormat(grid),response);
        }
        catch (Exception e)
        {
        	throw new IServiceException(this.getClass() + " --> getJobInfoList() Exception : " + e);
        }

    }

    /**
     * 跳转到添加八防检查登记页面 
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/toDj")
    public String toDj(HttpServletRequest request)
    {
        //获取session数据
		Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
		//用户代码
        request.setAttribute("CREATED_BY", String.valueOf(loginUser.get("USER_ID")));
        String flag = request.getParameter("flag");
        String cSEXj = request.getParameter("cSEXj");
        if (flag.equals("1"))
        {
            String UUID = request.getParameter("UUID");
            JobInfo pb = new JobInfo();
            pb.setUUID(UUID);
            List<JobInfo> data = jobInfoService.findData(pb);
            if (data != null && data.size() > 0 && data.size() == 1)
            {
                JobInfo jobInfo = (JobInfo) data.get(0);
                request.setAttribute("jobInfo", jobInfo);
            }
        }
        request.setAttribute("flag", flag);
        request.setAttribute("cSEXj", cSEXj);
        return "businessConsole/task/addJob";
    }

    /**
     * 检查登记 
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/addJobInfo")
    @SystemControllerLog(description="登记、修改定时任务")
    public void addJobInfo(HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult result = new JsonResult();
        result.setMsg("");
        result.setSuccess(true);
        try
        {
            //获取session数据
    		Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
    		//用户代码
            request.setAttribute("CREATED_BY", String.valueOf(loginUser.get("USER_ID")));
            String flag = request.getParameter("flag");
            String JOB_NAME = request.getParameter("JOB_NAME");
            String EXEC_CLASS = request.getParameter("EXEC_CLASS");
            String EXEC_CLASS_NAME = request.getParameter("EXEC_CLASS_NAME");
            String CRON_EXPRESS = request.getParameter("CRON_EXPRESS");
            String NEXT_EXEC_DATE = request.getParameter("NEXT_EXEC_DATE");
            String display = request.getParameter("display");
            String EXEC_CLASS_CODE = request.getParameter("EXEC_CLASS_CODE");
            
            JobInfo jobInfo = new JobInfo();
            jobInfo.setJOB_NAME(JOB_NAME);
            jobInfo.setEXEC_FLAG("0");
            jobInfo.setCREATED_BY(String.valueOf(loginUser.get("USER_ID")));
            jobInfo.setCRON_EXPRESS(CRON_EXPRESS);
            jobInfo.setNEXT_EXEC_DATE(NEXT_EXEC_DATE);
            jobInfo.setDEL_FLAG("0");
            jobInfo.setEXEC_CLASS(EXEC_CLASS);
            jobInfo.setEXEC_CLASS_NAME(EXEC_CLASS_NAME);
            jobInfo.setJOB_CODE(StringUtil.getCommonCode(2));
            jobInfo.setUPDATED_BY(String.valueOf(loginUser.get("USER_ID")));

            if (flag.equals("0"))
            {
                jobInfo.setUUID(UUIDUtil.uuidStr());
                jobInfo.setEXEC_CLASS_CODE(EXEC_CLASS_CODE);
                jobInfoService.addJobInfo(jobInfo, display);
            }
            else if (flag.equals("1"))
            {
                String UUID = request.getParameter("UUID");
               // String HIDDEN_EXEC_CLASS_NAME = request.getParameter("HIDDEN_EXEC_CLASS_NAME");
                String HIDDEN_EXEC_CLASS_CODE = request.getParameter("HIDDEN_EXEC_CLASS_CODE");
                String HIDDEN_JOB_NAME = java.net.URLDecoder.decode(request.getParameter("HIDDEN_JOB_NAME"),"UTF-8");
                jobInfo.setUUID(UUID);
                jobInfo.setEXEC_CLASS_CODE(HIDDEN_EXEC_CLASS_CODE);
                
                jobInfoService.updateJobInfo(jobInfo, display, HIDDEN_JOB_NAME);
            }
            printHttpServletResponse(new Gson().toJson(result),response);
        }
        catch (Exception e)
        {
        	throw new IServiceException(this.getClass() + " --> addJobInfo() Exception : " + e);
        }
       
    }

    /**
     * 执行/停止任务 
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/updateJobState")
    @SystemControllerLog(description="停用、启用、作废定时任务")
    public void updateJobState(HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult result = new JsonResult();
        result.setMsg("");
        result.setSuccess(true);
        try
        {
        	//获取session数据
			Map<String,Object> loginUser = (Map<String, Object>) request.getSession().getAttribute(GlobalConstant.LOGIN_USER);
			//用户代码
            String UUID = request.getParameter("UUID");
            String DEL_FLAG = request.getParameter("DEL_FLAG");
            String JOB_NAME = java.net.URLDecoder.decode(request.getParameter("JOB_NAME"),"UTF-8");
            String EXEC_CLASS = request.getParameter("EXEC_CLASS");
            String CRON_EXPRESS = request.getParameter("CRON_EXPRESS");
            JobInfo jobInfo = new JobInfo();
            jobInfo.setUUID(UUID);
            // jobInfo.setEXEC_FLAG(EXEC_FLAG);
            jobInfo.setDEL_FLAG(DEL_FLAG);
            jobInfo.setJOB_NAME(JOB_NAME);
            jobInfo.setEXEC_CLASS(EXEC_CLASS);
            jobInfo.setCRON_EXPRESS(CRON_EXPRESS);
            jobInfo.setUPDATED_BY(String.valueOf(loginUser.get("USER_ID")));
            jobInfoService.updateJobInfoState(jobInfo);

            printHttpServletResponse(new Gson().toJson(result),response);
        }
        catch (Exception e)
        {
        	throw new IServiceException(this.getClass() + " --> updateJobState() Exception : " + e);
        }
    }

    /**
     * 获取任务执行类 
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @RequestMapping("/findJobExec")
    public void findJobExec(HttpServletRequest request,HttpServletResponse response)
    {
        try
        {
            JobClassInfo object = new JobClassInfo();
            String cSEXj = request.getParameter("cSEXj");
            String EXEC_CLASS_CODE = request.getParameter("EXEC_CLASS_CODE");
            object.setCSEXj(cSEXj);
            object.setEXEC_CLASS_CODE(EXEC_CLASS_CODE);
            List<JobClassInfo> data = jobInfoService.findJobExec(object);
            printHttpServletResponse(GsonUtil.toJsonWithDateFormat(data),response);
        }
        catch (Exception e)
        {
        	throw new IServiceException(this.getClass() + " --> findJobExec() Exception : " + e);
        }
    }

    /**
     * 获取任务执行记录 
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @RequestMapping("/findJobClass")
    public void findJobClass(HttpServletRequest request,HttpServletResponse response,JobInfo model)
    {
        try
        {
            String data = request.getParameter("data");
            
            JobExec obj = GsonUtil.toBean(data, JobExec.class);
            
            obj.setPage(model.getPage());
            obj.setRows(model.getRows());
            
            Grid<JobExec> grid = jobInfoService.findJobClass(obj);
            
            printHttpServletResponse(GsonUtil.toJsonWithDateFormat(grid),response);
        }
        catch (Exception e)
        {
        	throw new IServiceException(this.getClass() + " --> findJobClass() Exception : " + e);
        }
    }

    /**
     * 跳转任务执行日志查询页面 
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @RequestMapping("/toFindJobClass")
    public String toFindJobClass(HttpServletRequest request)
    {
    	 String JOB_CODE = request.getParameter("JOB_CODE");
         request.setAttribute("JOB_CODE", JOB_CODE);
         return "businessConsole/task/JobClass";
    }

   /**
    * 获取任务状态 
    * 部门：软件开发事业部
    * 描述：略
    * 作成者：严惠惠
    * 作成时间：2017-8-31
    */
    @RequestMapping("/getJobState")
    public void getJobState(HttpServletRequest request,HttpServletResponse response)
    {
        try
        {
            String UUID = request.getParameter("UUID");
            JobInfo jobInfo = new JobInfo();
            jobInfo.setUUID(UUID);
            List<JobInfo> data = jobInfoService.findJobState(jobInfo);
            if (null != data)
            {
                JobInfo jobState = data.get(0);
                printHttpServletResponse(GsonUtil.toJsonWithDateFormat(jobState),response);
            }
        }
        catch (Exception e)
        {
        	throw new IServiceException(this.getClass() + " --> getJobState() Exception : " + e);
        }
    }
}
