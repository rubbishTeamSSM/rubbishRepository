package com.neusoft.sdd.util.commonUtil;

//public class SignUtil {
//
//}

//package com.chinaums.netpay.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

public class SignUtil {
    static public String buildSignString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key) || "sign_type".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();

    }

    static public String signWithMd5(Map<String, String> params, String md5Key,
                                     String charset) {
        String prestr = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        return signWithMd5(prestr, md5Key, charset);
    }

    static public String signWithMd5(String originStr, String md5Key, String charset) {
        String text = originStr + md5Key;
        return DigestUtils.md5Hex(getContentBytes(text, charset)).toUpperCase();
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
                    + charset);
        }
    }

}
