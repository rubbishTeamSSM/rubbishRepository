package com.neusoft.sdd.util.commonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.sdd.base.constant.GlobalConstant;

public class HttpUtil {

    private static SimpleDateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z", Locale.US);

    public static InputStream getResourceAsStream(String path) {
        return GlobalConstant.WEB.getServletContext().getResourceAsStream(path);
    }

    public static String formatHttpDate(Date date) {
        synchronized (httpDateFormat) {
            return httpDateFormat.format(date);
        }
    }

    public static String convertEncode(String str) {
        String encodeValue = null;
        try {
            encodeValue = URLEncoder.encode(str, "UTF-8");
            encodeValue = StringUtil.replace(encodeValue, "+", "%20");
            if (encodeValue.length() > 150) {
                encodeValue = new String(str.getBytes("UTF-8"), "ISO8859-1");
                encodeValue = StringUtil.replace(encodeValue, "+", "%20");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeValue;
    }

    public static String convertEncode(String str, String charset) {
        String encodeValue = null;
        try {
            encodeValue = URLEncoder.encode(str, "UTF-8");
            encodeValue = StringUtil.replace(encodeValue, "+", "%20");
            if (encodeValue.length() > 150) {
                encodeValue = new String(str.getBytes(charset), "ISO8859-1");
                encodeValue = StringUtil.replace(encodeValue, "+", "%20");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeValue;
    }


    public static ServletContext getServletContext() {
        return GlobalConstant.WEB.getServletContext();
    }


    public static void printHttpServletResponse(Object obj,HttpServletResponse response) {
        PrintWriter prw = null;
        try {
        	response.setContentType("text/html;charset=utf-8");
        	
            prw = response.getWriter();
            prw.print(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if(null != prw)
        		prw.close();
        }
    }

    public static String encodeURL(String url, String encoding) {
        try {
            return URLEncoder.encode(url, encoding);
        } catch (Exception exception) {
            return url;
        }
    }

    public static String encodePathInfo(String pathinfo) {
        String s = encodeURL(pathinfo, "UTF-8");
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        char c = '\0';
        for (int i = 0; i < chars.length; ++i) {
            c = chars[i];
            if (c == '+')
                sb.append("%20");
            else
                sb.append(c);
        }

        return sb.toString();
    }

    public static String decodeURL(String url, String encoding) {
        try {
            return URLDecoder.decode(url, encoding);
        } catch (Exception exception) {
            return url;
        }
    }

    static {
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
}