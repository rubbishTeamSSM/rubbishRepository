package com.neusoft.sdd.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HttpDownloadServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    //private IFtpService ftpService;
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {

        WebApplicationContext appContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext());

        //ftpService = (IFtpService) GlobalConstant.WEB.getBean("ftpServiceImpl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String whlj = req.getParameter("wjlj");
        
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        File downLoadFile = null;
        try {
        	String[] filelj = whlj.split("/");
    		String fileName = filelj[filelj.length-1];
    		
            downLoadFile = HttpFileUtil.downFile(whlj,fileName);
            
            inputStream =  new FileInputStream(downLoadFile);
            outputStream = resp.getOutputStream();
            
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Pragma", "no-cache");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setDateHeader("Expires", 0);
            resp.setContentType("application/octet-stream");
            
            setContentDisposition(req, resp, fileName);
            
            byte[] buffer = new byte[1024];
            int total;
            while ((total = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, total);
                outputStream.flush();
            }
            outputStream.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
            if(null != downLoadFile){
                downLoadFile.delete();
            }
        }
    }
    private void setContentDisposition(HttpServletRequest request,
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
