package com.neusoft.sdd.businessConsole.task.dao;

import java.util.List;

import com.neusoft.sdd.businessConsole.task.model.JobInfo;
import com.neusoft.sdd.businessConsole.task.model.JobClassInfo;
import com.neusoft.sdd.businessConsole.task.model.JobExec;

public interface JobInfoDAO {
	public List<JobInfo> getJobInfoList(JobInfo object);
	
	public int getJobInfoCounts(JobInfo object);
	
	public void addJobInfo(JobInfo rwjbxx);
	
	public void addJOB_EXEC_CLASS(JobClassInfo rwEXEC_CLASS);
	
	public void addJobClass(JobExec JobClass);
	
	public void updateJobInfo(JobInfo rwjbxx);
	
	public void updateJobInfoState(JobInfo rwjbxx);
	
	public void updateJOB_EXEC_CLASS(JobClassInfo rwEXEC_CLASS);
	
	public void updateJobClass(JobExec JobClass);
	
	public List<JobInfo> findData(JobInfo rwjbxx);
	
	public List<JobClassInfo> findJobExec(JobClassInfo jobExec);
	
	public List<JobExec> findJobClass(JobExec JobClass);
	
	public int findJobClassCount(JobExec object);
	
	public List<JobInfo> findJobState(JobInfo rwjbxx);

	public void insertrwyxjl(JobExec rwyxjl);

	public void updaterwjbxx(String JOB_CODE);

	public void updatekey(String key);

	public void updatekey1(String key);

	public void updatekey2(String JOB_CODE);
	
	public String getJOB_CODE(String EXEC_CLASS_CODE);
	
	
    
    
    
}
