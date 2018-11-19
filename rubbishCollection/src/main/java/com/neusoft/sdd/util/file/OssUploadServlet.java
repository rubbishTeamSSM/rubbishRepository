package com.neusoft.sdd.util.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.neusoft.sdd.util.commonUtil.GsonUtil;
import com.neusoft.sdd.util.commonUtil.OSSUtil;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class OssUploadServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String filePath = req.getParameter("filePath");//文件路径
        
        if (null == filePath || "".equals(filePath)) {
            filePath = "ywt/ccm";
        }
        
        String root =System.getProperty("java.io.tmpdir");
        //每个文件最大100M,最多10个文件,所以...
        int maxPostSize = 1 * 100 * 1024 * 1024;
        //文件最大只能上传10M
        MultipartRequest mr = new MultipartRequest(req, root, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
//        MultipartRequest mr = new MultipartRequest(req, root, "UTF-8");
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        PrintWriter out = resp.getWriter();
        
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        
        if (isMultipart) {
            
            try {
                
                Enumeration filesname = mr.getFileNames();// 取得上传的所有文件(相当于标识)
                
                while (filesname.hasMoreElements()) {
                    String name = (String) filesname.nextElement();// 标识
                    String fileName = mr.getFilesystemName(name); // 取得文件名
                    
                    File file = mr.getFile(name);
                    
                    //文件后缀
                    String suffix = fileName.substring(fileName.lastIndexOf("."));
                    
                    //阿里云文件key值
                    String key = filePath + "/" + UUIDUtil.uuidStr() + suffix;//阿里云文件key

                    //阿里云文件路径
                    //String ali_path = OSSClientProperties.ossEndPoint + "/" + key;
                    
                    //上传文件到阿里云
                    OSSUtil.putObject(key, file.getAbsolutePath());
                    
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("path", "/" + key);
                    map.put("o_filename", fileName);
                    
                    list.add(map);
                }

                out.print(GsonUtil.toJson(list));
                out.flush();
            } catch(Exception e){
                out.print("{\"path\":\"\", \"msg\":\"文件上传过程中出现异常！\"}");
                e.printStackTrace();
            }
        }
    }

}
