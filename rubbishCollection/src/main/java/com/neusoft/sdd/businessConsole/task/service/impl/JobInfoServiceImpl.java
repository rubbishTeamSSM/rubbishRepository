package com.neusoft.sdd.businessConsole.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.businessConsole.task.dao.JobInfoDAO;
import com.neusoft.sdd.businessConsole.task.model.JobClassInfo;
import com.neusoft.sdd.businessConsole.task.model.JobExec;
import com.neusoft.sdd.businessConsole.task.model.JobInfo;
import com.neusoft.sdd.businessConsole.task.service.JobInfoService;
import com.neusoft.sdd.util.commonUtil.QuartzUtil;

@Service
//@Transactional
public class JobInfoServiceImpl implements JobInfoService
{
    @Autowired
    private JobInfoDAO jobInfoDao;

    /**
     * 获取检查排班列表
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @Override
    public Grid<JobInfo> getJobInfoList(JobInfo object)
    {
        Grid<JobInfo> grid = new Grid<JobInfo>();
        PageHelper.startPage(object.getPage(), object.getRows());
        List<JobInfo> list = jobInfoDao.getJobInfoList(object);
		grid.setRows(list);
	    grid.setTotal((int) new PageInfo<JobInfo>(list).getTotal());
        return grid;
    }

    /**
     * 检查登记 
     * 部门：软件开发事业部
     * 描述：略
     * 作成者：严惠惠
     * 作成时间：2017-8-31
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void addJobInfo(JobInfo rwjbxx, String display)
    {
        jobInfoDao.addJobInfo(rwjbxx);
        if (rwjbxx.getEXEC_FLAG().equals("1"))
        {
            String job_name = rwjbxx.getJOB_NAME();
            String EXEC_CLASS = rwjbxx.getEXEC_CLASS();
            String CRON_EXPRESS = rwjbxx.getCRON_EXPRESS();
            Class clazz;
            try
            {
                clazz = Class.forName(EXEC_CLASS);
                Object obj = clazz.newInstance();
                // System.out.println(obj.getClass().equals(QuartzJob.class));
                if (display.equals("Simple"))
                {
                    QuartzUtil.addSimpleJob(job_name, obj.getClass(), CRON_EXPRESS);
                }
                else
                {
                    QuartzUtil.addJob(job_name, obj.getClass(), CRON_EXPRESS);
                }

            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void addJOB_EXEC_CLASS(JobClassInfo rwEXEC_CLASS)
    {
        jobInfoDao.addJOB_EXEC_CLASS(rwEXEC_CLASS);
    }

    @Override
    public void addJobClass(JobExec JobClass)
    {
        jobInfoDao.addJobClass(JobClass);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void updateJobInfo(JobInfo rwjbxx, String display, String HIDDEN_JOB_NAME)
    {
        jobInfoDao.updateJobInfo(rwjbxx);
        String job_name = rwjbxx.getJOB_NAME();
        if (rwjbxx.getEXEC_FLAG().equals("1"))
        {
            String EXEC_CLASS = rwjbxx.getEXEC_CLASS();
            String CRON_EXPRESS = rwjbxx.getCRON_EXPRESS();
            Class clazz;
            try
            {
                clazz = Class.forName(EXEC_CLASS);
                Object obj = clazz.newInstance();
                // System.out.println(obj.getClass().equals(QuartzJob.class));
                QuartzUtil.removeJob(HIDDEN_JOB_NAME);
                QuartzUtil.addJob(job_name, obj.getClass(), CRON_EXPRESS);
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
            }
        }
        else
        {
            QuartzUtil.removeJob(job_name);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void updateJobInfoState(JobInfo rwjbxx)
    {
        String DEL_FLAG = rwjbxx.getDEL_FLAG();
        String UUID = rwjbxx.getUUID();
        String job_name = rwjbxx.getJOB_NAME();
        if (DEL_FLAG.equals("0") || DEL_FLAG.equals("2"))
        {
            QuartzUtil.removeJob(job_name);
        }
        else
        {
            String EXEC_CLASS = rwjbxx.getEXEC_CLASS();
            String CRON_EXPRESS = rwjbxx.getCRON_EXPRESS();
            Class clazz;
            try
            {
                clazz = Class.forName(EXEC_CLASS);
                Object obj = clazz.newInstance();
                if (QuartzUtil.isValidDate(CRON_EXPRESS))
                {
                    QuartzUtil.addSimpleJob(job_name, obj.getClass(), CRON_EXPRESS);
                }
                else
                {
                    QuartzUtil.addJob(job_name, obj.getClass(), CRON_EXPRESS);
                }
                GlobalConstant.map.put("JOB_NAME", job_name);
                GlobalConstant.map.put("UUID", UUID);
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        //更新任务基本信息表的启动状态
        jobInfoDao.updateJobInfoState(rwjbxx);
    }

    @Override
    public void updateJOB_EXEC_CLASS(JobClassInfo rwEXEC_CLASS)
    {
        jobInfoDao.updateJOB_EXEC_CLASS(rwEXEC_CLASS);
    }

    @Override
    public List<JobInfo> findData(JobInfo object)
    {
        return jobInfoDao.findData(object);
    }

    @Override
    public void updateJobClass(JobExec object)
    {
        jobInfoDao.updateJobClass(object);
    }

    @Override
    public List<JobClassInfo> findJobExec(JobClassInfo object)
    {
        return jobInfoDao.findJobExec(object);
    }

    @Override
    public Grid<JobExec> findJobClass(JobExec object)
    {
        Grid<JobExec> grid = new Grid<JobExec>();
        PageHelper.startPage(object.getPage(), object.getRows());
        List<JobExec> list = jobInfoDao.findJobClass(object);
        // 数据列
        grid.setRows(list);
        // 数据总数
        grid.setTotal((int) new PageInfo<JobExec>(list).getTotal());
        return grid;
    }

    @Override
    public List<JobInfo> findJobState(JobInfo rwjbxx)
    {
        return jobInfoDao.findData(rwjbxx);
    }

    /**
     * 查询任务名称
      * 部门：软件开发事业部	 
      * 描述：略
      * 作成者：严惠惠
      * 作成时间：2015-11-2
     */
	@Override
	public String getJOB_CODE(String EXEC_CLASS_CODE) {
		return jobInfoDao.getJOB_CODE(EXEC_CLASS_CODE);
	}
	
	@Override
	public  void addKslog(String key,String JOB_CODE) {
		JobExec rwyxjl = new JobExec();
		rwyxjl.setUUID(key);
		rwyxjl.setJOB_CODE(JOB_CODE);
		rwyxjl.setLOG_INFO("执行成功");
		rwyxjl.setCREATED_BY("admin");
		String JOBCODE = rwyxjl.getJOB_CODE();
		jobInfoDao.insertrwyxjl(rwyxjl);
		jobInfoDao.updaterwjbxx(JOBCODE);
	}

	@Override
	public  void addJslog(String key, String flag,String JOB_CODE) {
		String job_code = JOB_CODE;
		if (flag.equals("1")) {
			jobInfoDao.updatekey(key);
		} else {
			jobInfoDao.updatekey1(key);
		}
		jobInfoDao.updatekey2(job_code);
	}

}
