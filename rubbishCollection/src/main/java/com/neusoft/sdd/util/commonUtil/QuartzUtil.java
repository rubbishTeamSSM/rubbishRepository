package com.neusoft.sdd.util.commonUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.businessConsole.task.model.JobExec;

public class QuartzUtil {
	  private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	  private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	  private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
	 
	  /**
	   * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	   * 
	   * @param jobName
	   *            任务名
	   * @param cls
	   *            任务
	   * @param time
	   *            时间设置，参考quartz说明文档
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:47:44
	   * @version V2.0
	   */
	@SuppressWarnings("rawtypes")
	public static void addJob(String jobName, Class cls, String time) {
		  
	    try {
	      Scheduler sched = gSchedulerFactory.getScheduler();
	      JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, cls);// 任务名，任务组，任务执行类
	      // 触发器
	      CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
	      trigger.setCronExpression(time);// 触发器时间设定
	      sched.scheduleJob(jobDetail, trigger);
	      // 启动
	      if (!sched.isShutdown()) {
	        sched.start();
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }
	  
	  /**
	   * @Description: 添加一个简单任务，使用默认的任务组名，触发器名，触发器组名
	   * 
	   * @param jobName
	   *            任务名
	   * @param cls
	   *            任务
	   * @param time
	   *            时间设置，参考quartz说明文档
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:47:44
	   * @version V2.0
	   */
	@SuppressWarnings("rawtypes")
	public static void addSimpleJob(String jobName, Class cls, String time) {
		// 通过SchedulerFactory来获取一个调度器
		Scheduler scheduler;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			// 引进作业程序
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, cls);

			// new一个触发器
			SimpleTrigger simpleTrigger = new SimpleTrigger("simpleTrigger",
					JOB_GROUP_NAME);

			// 设置作业启动时间

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			simpleTrigger.setStartTime(sdf.parse(time));

	        //设置作业执行间隔 
	        //simpleTrigger.setRepeatInterval(31536);

	        //设置作业执行次数
	        //simpleTrigger.setRepeatCount(1);

	        //设置作业执行优先级默认为5
	        //simpleTrigger.setPriority(10);

			// 作业和触发器设置到调度器中
			scheduler.scheduleJob(jobDetail, simpleTrigger);

			// 启动调度器
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}


	  /**
	   * @Description: 添加一个定时任务
	   * 
	   * @param jobName
	   *            任务名
	   * @param jobGroupName
	   *            任务组名
	   * @param triggerName
	   *            触发器名
	   * @param triggerGroupName
	   *            触发器组名
	   * @param jobClass
	   *            任务
	   * @param time
	   *            时间设置，参考quartz说明文档
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:48:15
	   * @version V2.0
	   */
	@SuppressWarnings("rawtypes")
	public static void addJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName, Class jobClass,
			String time) {
	    try {
	      Scheduler sched = gSchedulerFactory.getScheduler();
	      JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
	      // 触发器
	      CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
	      trigger.setCronExpression(time);// 触发器时间设定
	      sched.scheduleJob(jobDetail, trigger);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }

	  /**
	   * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	   * 
	   * @param jobName
	   * @param time
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:49:21
	   * @version V2.0
	   */
	@SuppressWarnings("rawtypes")
	public static void modifyJobTime(String jobName, String time) {
	    try {
	      Scheduler sched = gSchedulerFactory.getScheduler();
	      CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
	      if (trigger == null) {
	        return;
	      }
	      String oldTime = trigger.getCronExpression();
	      if (!oldTime.equalsIgnoreCase(time)) {
	        JobDetail jobDetail = sched.getJobDetail(jobName,JOB_GROUP_NAME);
	        Class objJobClass = jobDetail.getJobClass();
	        removeJob(jobName);
	        addJob(jobName, objJobClass, time);
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }

	  /**
	   * @Description: 修改一个任务的触发时间
	   * 
	   * @param triggerName
	   * @param triggerGroupName
	   * @param time
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:49:37
	   * @version V2.0
	   */
	public static void modifyJobTime(String triggerName,
			String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName,
					triggerGroupName);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.setCronExpression(time);
				// 重启触发器
				sched.resumeTrigger(triggerName, triggerGroupName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	  /**
	   * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	   * 
	   * @param jobName
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:49:51
	   * @version V2.0
	   */
	public static void removeJob(String jobName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
			sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
			sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	  /**
	   * @Description: 移除一个任务
	   * 
	   * @param jobName
	   * @param jobGroupName
	   * @param triggerName
	   * @param triggerGroupName
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:50:01
	   * @version V2.0
	   */
	public static void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
			sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
			sched.deleteJob(jobName, jobGroupName);// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	  /**
	   * @Description:启动所有定时任务
	   * 
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:50:18
	   * @version V2.0
	   */
	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	  /**
	   * @Description:关闭所有定时任务
	   * 
	   * 
	   * @Title: QuartzManager.java
	   * @Copyright: Copyright (c) 2014
	   * 
	   * @author Comsys-LZP
	   * @date 2014-6-26 下午03:50:26
	   * @version V2.0
	   */
	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void addKslog(String key,String rw_dm) {
		/*SqlMapClient  sqlMapClient = GlobalConstant.sqlMapClient;
		JobExec jobExec = new JobExec();
		jobExec.setUUID(key);
		jobExec.setJOB_CODE(rw_dm);
		jobExec.setLOG_INFO("执行成功");
		jobExec.setCREATED_BY("admin");
		try {
			sqlMapClient.insert("insertrwyxjl", jobExec);
			sqlMapClient.update("updaterwjbxx", rw_dm);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}*/
		  
		 Connection conn=null;  
         Statement st=null;  
         ResultSet resultset=null;  			          
         try {  
           //2.建立连接  
           conn=JdbcUtil.getConnection();  
           //3.创建语句  
           st=conn.createStatement();  
           //4.执行语句  
           String sql="INSERT INTO T_JOB_EXEC_DETAIL (UUID,JOB_CODE,START_TIME,END_TIME,SUCCESS_FLAG,LOG_INFO,CREATED_TIME,CREATED_BY,UPDATED_TIME,UPDATED_BY,DEL_FLAG,SORT_NO)"+
           		"VALUES('"+key+"','"+String.valueOf(GlobalConstant.map.get("uuid"))+"',NOW(),NOW(),'1','执行成功',NOW(),'admin')";  
           st.executeUpdate(sql);   
           String sql2="update T_JOB_INFO set EXEC_FLAG='1' where JOB_CODE='"+String.valueOf(GlobalConstant.map.get("uuid"))+"'";
           st.executeUpdate(sql2);   
         } catch (SQLException e) {
				e.printStackTrace();
	      } finally{  
           JdbcUtil.free(resultset, st, conn);  
	      }
	}

	public static void addJslog(String key, String flag) {
		 /*SqlMapClient  sqlMapClient = GlobalConstant.sqlMapClient;
		
		String rw_dm = String.valueOf(GlobalConstant.map.get("UUID"));
		try {
			if (flag.equals("1")) {
				sqlMapClient.update("updatekey", key);
			} else {
				sqlMapClient.update("updatekey1", key);
			}
			sqlMapClient.update("updatekey2", rw_dm);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}*/
		  
		 Connection conn=null;  
         Statement st=null;  
         ResultSet resultset=null;  			          
         try {  
           //2.建立连接  
           conn=JdbcUtil.getConnection();  
           //3.创建语句  
           st=conn.createStatement();  
           //4.执行语句  
           String sql="";
           if(flag.equals("1")){
           	sql="update T_JOB_EXEC_DETAIL set END_TIME=NOW(),SUCCESS_FLAG='1',LOG_INFO='执行成功' where UUID='"+key+"'";   
           }else{
           	sql="update T_JOB_EXEC_DETAIL set END_TIME=NOW(),SUCCESS_FLAG='0',LOG_INFO='执行失败' where UUID='"+key+"'"; 
           }
           st.executeUpdate(sql);   
           String sql2="update T_JOB_INFO set EXEC_FLAG='0' where JOB_CODE='"+String.valueOf(GlobalConstant.map.get("uuid"))+"'";
           st.executeUpdate(sql2); 
         } catch (SQLException e) {
				e.printStackTrace();
	      } finally{  
           JdbcUtil.free(resultset, st, conn);  
	      }
	}  
	//初始化部门
	@SuppressWarnings("rawtypes")
	public void initQuartzJob() throws InstantiationException,
		IllegalAccessException {
		Connection conn = null;
		Statement st = null;
		ResultSet result = null;
		try {
			// 2.建立连接
			conn = JdbcUtil.getConnection();
			// 3.创建语句
			st = conn.createStatement();
			// 4.执行语句
			//ResultSet result = null;//表示接受数据库查询到的结果
			String sql ="SELECT JBXX.JOB_CODE UUID,\n" +
						"       JBXX.JOB_MC RWMC,\n" + 
						"       JBXX.EXEC_CLASS_CODE ZXLID,\n" + 
						"       JBXX.CRON_EXPRESS AS SJBDS,\n" + 
						"       JBXX.DEL_FLAG QYBJ,\n" + 
						"       JBXX.EXEC_FLAG ZXBJ,\n" + 
						"       DATE_FORMAT(JBXX.NEXT_EXEC_DATE, '%Y-%m-%d %H:%i:%S') XCTIME,\n" + 
						"       RWL.EXEC_CLASS_MC ZXLMC,\n" + 
						"       RWL.EXEC_CLASS AS ZXL\n" + 
						"  FROM T_JOB_INFO JBXX, T_JOB_CLASS_INFO RWL\n" + 
						" WHERE JBXX.EXEC_CLASS_CODE = RWL.EXEC_CLASS_CODE\n" + 
						"   AND JBXX.DEL_FLAG = '1'";
			
			result = st.executeQuery(sql);// 执行sql语句，结果集放在result中 
			        
			while (result.next()) {// 判断是否还有下一行
			    String job_name = result.getString("RWMC");// 获取数据库person表中name字段的值
			    String time = result.getString("SJBDS");
			    String zxl = result.getString("ZXL");
			    Class clazz = Class.forName(zxl);
			    Object obj = clazz.newInstance();
			    QuartzUtil.removeJob(job_name);
			    
			    if (clazz != null) {
					if (QuartzUtil.isValidDate(time)) {
						QuartzUtil.addSimpleJob(job_name, obj.getClass(), time);
					} else {
						QuartzUtil.addJob(job_name, obj.getClass(), time);
					}
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.free(result, st, conn);
		}
	}
	
	
	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

}
