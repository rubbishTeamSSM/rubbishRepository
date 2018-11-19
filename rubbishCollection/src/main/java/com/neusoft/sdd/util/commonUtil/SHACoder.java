package com.neusoft.sdd.util.commonUtil;

import java.security.MessageDigest;

/**
 * 
 * <p>
 * Description: [实现SHA算法的加密处理，该加密处理]
 * </p>
 */
public class SHACoder {
	private static final String ALGORITHM_256 = "SHA-256";

	private static final String ALGORITHM_1 = "SHA-1";

	public static byte[] encodeSHA256(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(ALGORITHM_256);
		return md.digest(data);
	}

	public static String encodeSHA256(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(ALGORITHM_256);
		return byte2hex(md.digest(data.getBytes()));
	}

	public static String encodeSHA1(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance(ALGORITHM_1);
		return byte2hex(md.digest(hex2byte(data.getBytes())));
	}

	private static String byte2hex(byte[] b) { // 一个字节的数，
		// 转成16进制字符串
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase(); // 转成大写
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

}
