/*
 * Neusoft Corporation Copyright 2015-2025, All rights reserved.
 * 文件名  :JsonUtils.java
 * 创建人  :Administrator
 * 创建时间:2015年5月19日
 */

package com.neusoft.sdd.util.wx;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月19日
 * @since Neusoft 001
 */
public class JsonUtils {
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = mapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings({"deprecation", "unchecked"})
	public static <T> T parseJSON2Obj(String jsonStr, Class<T> t) {
		String[] strarr = jsonStr.split("ZW" + "\":");
		String[] strarr2 = strarr[1].split(",\"FB_SJ");
		// 对url加密的富文本字符进行解密
		String enStr = URLEncoder.encode(strarr2[0]);
		jsonStr = strarr[0] + "ZW\":\"" + enStr + "\",\"FB_SJ" + strarr2[1];

		String[] strarr_zt = jsonStr.split("ZT" + "\":");
		String[] strarr2_zt = strarr_zt[1].split(",\"ZY\"");
		// 对url加密的富文本字符进行解密
		enStr = URLEncoder.encode(strarr2_zt[0]);
		jsonStr = strarr_zt[0] + "ZT\":\"" + enStr + "\",\"ZY\""
				+ strarr2_zt[1];

		String[] strarr_zy = jsonStr.split("ZY" + "\":");
		String[] strarr2_zy = strarr_zy[1].split(",\"ZYTP_URL");
		// 对url加密的富文本字符进行解密
		enStr = URLEncoder.encode(strarr2_zy[0]);
		jsonStr = strarr_zy[0] + "ZY\":\"" + enStr + "\",\"ZYTP_URL"
				+ strarr2_zy[1];
		// jsonStr = URLDecoder.decode(jsonStr);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> tempResult = new HashMap<String, Object>();
		try {
			result = mapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Entry<String, Object>> resultSet = result.entrySet();

		for (Entry<String, Object> entry : resultSet) {
			tempResult.put(entry.getKey().toLowerCase(), entry.getValue());

		}

		T obj = null;

		try {
			obj = t.newInstance();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field tempField : fields) {
				tempField.setAccessible(true);

				if (tempField.getName().equalsIgnoreCase("uuid")) {
					tempField.set(obj, UUID.randomUUID().toString());
					continue;
				}
				// 对url加密的富文本字符进行解密
				if (tempField.getName().equalsIgnoreCase("zw")) {
					tempField.set(obj, URLDecoder.decode((String) tempResult
							.get(tempField.getName().toLowerCase())));
					continue;
				}
				// 对url加密的富文本字符进行解密
				if (tempField.getName().equalsIgnoreCase("zt")) {
					tempField.set(obj, URLDecoder.decode((String) tempResult
							.get(tempField.getName().toLowerCase())));
					continue;
				}
				// 对url加密的富文本字符进行解密
				if (tempField.getName().equalsIgnoreCase("zy")) {
					tempField.set(obj, URLDecoder.decode((String) tempResult
							.get(tempField.getName().toLowerCase())));
					continue;
				}
				tempField.set(obj,
						tempResult.get(tempField.getName().toLowerCase()));
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return obj;
	}

	@SuppressWarnings("unchecked")
	public static <T> T parseJSON2AllStrObj(String jsonStr, Class<T> t) {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> tempResult = new HashMap<String, Object>();
		try {
			result = mapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Entry<String, Object>> resultSet = result.entrySet();

		for (Entry<String, Object> entry : resultSet) {
			tempResult.put(entry.getKey().toLowerCase(),
					String.valueOf(entry.getValue()));

		}

		T obj = null;

		try {
			obj = t.newInstance();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field tempField : fields) {
				tempField.setAccessible(true);

				if (tempField.getName().equalsIgnoreCase("uuid")) {
					tempField.set(obj, UUID.randomUUID().toString());
					continue;
				}
				tempField.set(obj,
						tempResult.get(tempField.getName().toLowerCase()));
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static String parseObject2JSON(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

}
