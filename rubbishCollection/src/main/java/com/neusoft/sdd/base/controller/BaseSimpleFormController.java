package com.neusoft.sdd.base.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.sdd.util.commonUtil.DateUtil;
import com.neusoft.sdd.util.commonUtil.HttpUtil;


public abstract class BaseSimpleFormController{


	  public String getSysDate()
	  {
	    return DateUtil.getSysDate();
	  }

	  public Timestamp getSysTimestamp()
	  {
	    return DateUtil.getSysTimestamp();
	  }

	  public Timestamp getTimestamp(String strDate)
	  {
	    return DateUtil.getTimestamp(strDate);
	  }

	  public void printHttpServletResponse(Object obj,HttpServletResponse response)
	  {
	    HttpUtil.printHttpServletResponse(obj,response);
	  }
	  
	  public static void download(HttpServletRequest request, HttpServletResponse response, String filePath, String backFilname) {
			File file = null;
			FileInputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				// Linux系统用
				if (filePath.lastIndexOf("/") > 0) {
					file = new File(filePath);
				} else {
					// windows系统用
					file = new File(System.getProperty("java.io.tmpdir"), filePath);
				}

				inputStream = new FileInputStream(file);
				outputStream = response.getOutputStream();

				response.setCharacterEncoding("UTF-8");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("application/x-download charset=UTF-8");

				setContentDisposition(request, response, backFilname);

				byte[] buffer = new byte[1024];
				int total;

				while ((total = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, total);
				}

				outputStream.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != outputStream) {
						outputStream.close();
					}

					if (null != inputStream) {
						inputStream.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				file.delete();
			}
		}

	private static void setContentDisposition(HttpServletRequest request,
			HttpServletResponse response, String showName)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		String contentDisposition = "";
		if (userAgent != null) {
			if (userAgent.indexOf("msie") >= 0) {
				contentDisposition = "attachment; filename="
						+ URLEncoder.encode(showName, "UTF-8").replace("+",
								"%20");

			} else if (userAgent.indexOf("mozilla") >= 0) {
				contentDisposition = "attachment; filename*=UTF-8''"
						+ URLEncoder.encode(showName, "UTF-8").replace("+",
								"%20");

			} else if (userAgent.indexOf("applewebkit") >= 0) {
				contentDisposition = "attachment; filename="
						+ URLEncoder.encode(showName, "UTF-8").replace("+",
								"%20");
			} else if (userAgent.indexOf("safari") >= 0) {
				contentDisposition = "attachment; filename="
						+ new String(showName.getBytes("UTF-8"), "ISO8859-1");

			} else if (userAgent.indexOf("opera") >= 0) {
				contentDisposition = "attachment; filename*=UTF-8''"
						+ URLEncoder.encode(showName, "UTF-8").replace("+",
								"%20");

			} else {
				contentDisposition = "attachment; filename*=UTF-8''"
						+ URLEncoder.encode(showName, "UTF-8").replace("+",
								"%20");
			}
		}
		response.setHeader("Content-Disposition", contentDisposition);
	}
}
