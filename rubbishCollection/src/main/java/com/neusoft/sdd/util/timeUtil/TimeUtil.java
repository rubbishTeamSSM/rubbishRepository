package com.neusoft.sdd.util.timeUtil;

import java.util.Calendar;

public class TimeUtil {
	/**
	 * 
	* 部门：软件开发事业部
	* 功能：日期新增
	* 描述：
	* 作成者：许洋阳
	* 作成时间：2017-11-3
	 */
	public static String dateAdd(String addTime){
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。  
		cal.add(Calendar.MINUTE, Integer.valueOf(addTime));//取当前日期的前一天.
		//通过格式化输出日期
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(cal.getTime());
	}
}
