package com.neusoft.sdd.util.commonUtil;

import java.util.UUID;

public class UUIDUtil {

	public static String uuidStr(){
		return  UUID.randomUUID().toString().replaceAll("-", "");
	}
}
