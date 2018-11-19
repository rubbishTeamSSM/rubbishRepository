package com.neusoft.sdd.util.wx;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * [简要描述]:获取企业号签名<br/>
 * [详细描述]:<br/>
 *
 * @version [revision],2015-10-23
 * @since Neusoft 001
 */
public class SignEnterprise {
	/**
	 * 
	 * 部门：软件开发事业部
	 * 功能：获取企业号微信js-sdk的签名
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-10-23
	 */
	public static Map<String, String> sign(String url) {
		String jsapi_ticket = WxEnterpriseInterfaceUtils.getJsTicket();
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		System.out.println("signature   ========= " + signature);
		System.out.println("nonceStr   ========= " + nonce_str);
		System.out.println("timestamp   ========= " + timestamp);
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

}
