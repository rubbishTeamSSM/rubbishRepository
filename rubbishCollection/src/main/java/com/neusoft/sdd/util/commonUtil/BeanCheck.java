package com.neusoft.sdd.util.commonUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class BeanCheck {
	/*
	**
	* 获得javabean 对应的中文注释
	*/
	public static List<Map> getChineseNameByTableName() throws Exception{
	   List<Map> chineseNameList = new ArrayList<Map>();
	   String className = "com.neusoft.sdd.component.dymb.model.DataObject";
	   Class clazz = Class.forName(className);
	   Method[] methods = clazz.getDeclaredMethods();
	  
	   for(Method method : methods){
	    //该方法是否使用了ExcelImportAnnotation这个注解类       
	    boolean isExist = method.isAnnotationPresent(ExcelImportAnnotation.class) ; 
	    if(isExist){
	     ExcelImportAnnotation annotation = method.getAnnotation(ExcelImportAnnotation.class); 
	     //排除自增主键，自增主键不需要赋值
	     if(!annotation.autoIncrementKey()){
	    	 Map map=new HashMap();
	    	 map.put(annotation.chineseName(), annotation.fieldName());
	      chineseNameList.add(map);
	     }
	    }
	   }
	   return chineseNameList;
	}

	//测试方法

	public static void main(String[] args) throws Exception {
	   List list = getChineseNameByTableName();

	   for (int i = 0; i < list.size(); i++) {
	    System.out.println(list.get(i));
	   }
	  
	}


}
