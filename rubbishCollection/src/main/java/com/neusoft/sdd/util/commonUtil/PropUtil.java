package com.neusoft.sdd.util.commonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置信息
 * @author work-admin
 *
 */
public class PropUtil {

	private Properties properties = null;
	
	private PropUtil(){
		
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("seas_config.properties");	
			if (inputStream == null)
				throw new IllegalArgumentException("Properties file not found in classpath: ");
			properties = new Properties();
			properties.load(new InputStreamReader(inputStream, "utf-8"));
		} catch (IOException e) {
			throw new RuntimeException("Error loading properties file.", e);
		}
		finally {
			if (inputStream != null){
				try {
					inputStream.close();
					} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				}
		}
	}
	
	private static PropUtil instance = new PropUtil();
	
	public static PropUtil getInstance(){
		return instance;
	}
	
	
	public static void main(String[] args) {
		PropUtil p = PropUtil.getInstance();

		System.out.println(	p.get("dw_dm"));
	}
	
	public String get(String key) {
		return properties.getProperty(key);
	}
}
