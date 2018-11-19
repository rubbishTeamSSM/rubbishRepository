package com.neusoft.sdd.util.listToString;

import java.util.List;

public class ListToString {
	/**
	 * 
	* 部门：软件开发事业部
	* 功能：list转String
	* 描述：
	* 作成者：许洋阳
	* 作成时间：2017-5-10
	 */
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append("'"+string+"'");
        }
        return result.toString();
    }
}
