package com.neusoft.sdd.util.commonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件初始化
 * @author Test
 *
 */
public class PropertiesUtil {
	public static Properties loadProperties(String propertiesFilePath) {
        Properties p = new Properties();
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFilePath);
        
        if(in==null){
            return p ;
        }
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
    		if (in != null){
    			try {
    				in.close();
    				} 
    			catch (IOException e) 
    			{
    				e.printStackTrace();
    			}
    			}
    	}
        
        return p;
    }

}
