package com.neusoft.sdd.businessConsole.task.service;

import java.util.List;

import com.neusoft.sdd.base.model.easyui.Grid;
import com.neusoft.sdd.businessConsole.task.model.JobInfo;
import com.neusoft.sdd.businessConsole.task.model.JobClassInfo;
import com.neusoft.sdd.businessConsole.task.model.JobExec;

public interface JobInfoService {
	public Grid<JobInfo> getJobInfoList(JobInfo object);
	
	public void addJobInfo(JobInfo rwjbxx,String display);
	
	public void addJOB_EXEC_CLASS(JobClassInfo rwEXEC_CLASS);
	
	public void addJobClass(JobExec JobClass);
	
	public void updateJobInfo(JobInfo rwjbxx,String display,String HIDDEN_EXEC_CLASS_NAME);
	
	public void updateJobInfoState(JobInfo rwjbxx);
	
	public void updateJOB_EXEC_CLASS(JobClassInfo rwEXEC_CLASS);
	
	public List<JobInfo> findData(JobInfo object);
	
	public void updateJobClass(JobExec object);
	
	public List<JobClassInfo> findJobExec(JobClassInfo object);
		
	public Grid<JobExec> findJobClass(JobExec object);
	
	public List<JobInfo> findJobState(JobInfo rwjbxx);

	public String getJOB_CODE(String exec_class_code);

	void addKslog(String key, String JOB_CODE);

	void addJslog(String key, String flag, String JOB_CODE);
	
}
