package com.neusoft.sdd.util.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import com.neusoft.sdd.util.commonUtil.PropertiesUtil;

public class WriterLogs {
	//兼容Windows和Linux系统的路径分隔符
	public static final String separator = File.separator;
    public static void writeToTxt(Map<String,Object> msg){
    	Properties dbProp = PropertiesUtil.loadProperties("wxConfig.properties");
    	String filePath = null;//文件保存路径
    	if("\\".equals(separator)){
    		filePath = dbProp.getProperty("windowsPath");
    	}else if("/".equals(separator)){
    		filePath = dbProp.getProperty("linuxPath");
    	}
    	//设置日期格式
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//获取系统时间
    	String da = df.format(new Date());
    	//文件夹路径
    	String path = filePath+separator+da.substring(0, 10).replaceAll("-", "");
    	//文件名
    	String name = msg.get("moduleName").toString()+".txt";
    	File file = new File(path);
        //文件路径
    	File fileNames = new File(path+separator+name);
    	PrintWriter writer = null;
    	Boolean flag = true;//新建文件夹是否成功标记
        try {
        	if(!file.exists()){
            	//文件夹不存在则新建
            	flag = file.mkdirs();
            }
        	if(flag){
        		
        		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileNames,true),"UTF-8")));
                writer.println("****************************************************");//换行
                writer.println("---"+da+"---");//换行
                //操作的值
                writer.println("---"+msg.get("logs").toString()+"---");//换行
        	}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(null != writer){
        		writer.close();
        	}
        }
    }
}
