package com.neusoft.sdd.util.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.businessConsole.ftp.model.Xtjzcs;
import com.neusoft.sdd.businessConsole.ftp.service.IFtpService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class HttpUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private IFtpService ftpService;
    
   /* private static String ip;
    
    private static String port;
    
    static {
        Properties wjfwqProp = PropertiesUtil.loadProperties("wjfwq_config.properties");
        ip = wjfwqProp.getProperty("wjfwq_ip");
        port = wjfwqProp.getProperty("wjfwq_port");
        System.err.println("\r\n----------------------------------------------");
        System.err.println("ip:"+ip);
        System.err.println("port:"+port);
        System.err.println("----------------------------------------------\r\n");
    }*/
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	ftpService = (IFtpService) GlobalConstant.WEB.getBean("ftpServiceImpl");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String wjLj = req.getParameter("wjjDm");//文件路径
        //String wjlbDm = req.getParameter("wjlbDm");//文件类别代码
        String sjUUID = req.getParameter("sjUUID");//文件路径
        //String sfsl_bj = req.getParameter("sfslBj");//缩略标记
        
        if (null == wjLj || "".equals(wjLj)) {
            wjLj = "default";
        }
        
        String root =System.getProperty("java.io.tmpdir");
        //req.setCharacterEncoding("utf-8");
        
        //文件最大只能上传10M
        MultipartRequest mr = new MultipartRequest(req, root, 30*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);

        String yhDm = "admin";//创建人

        PrintWriter out = resp.getWriter();
        
        if (isMultipart) {
            //获取系统介质参数
            Xtjzcs jzcs = ftpService.getXtjzcs();
            
            //文件服务器地址
            String targetPath = "http://"+jzcs.getFwdz()+":"+jzcs.getDkh()+"/fileservice/FileReceiveServlet.tool?wjLj="+wjLj;
            try {
            	Map<String,Object> loginUser = (Map<String, Object>) req.getSession().getAttribute(GlobalConstant.LOGIN_USER);
                
                
                if (null != loginUser && null != loginUser.get("USER_ID") && !"".equals(loginUser.get("USER_ID")))
                {
                	yhDm = String.valueOf(loginUser.get("USER_ID"));//创建人
                }

                
                Enumeration filesname = mr.getFileNames();// 取得上传的所有文件(相当于标识)
                
                List<Map<String, Object>> fileList = new ArrayList<Map<String,Object>>();
                
                while (filesname.hasMoreElements()) {
                    String name = (String) filesname.nextElement();// 标识
                    String fileName = mr.getFilesystemName(name); // 取得文件名
                    
                    //格式转换
//                  String[] hzmc = fileName.split(".");
//                  if(!"mp3".equals(hzmc[1])){
//                       String path1 =fileName ;  
//                       String path2 = hzmc[0]+".mp3";  
//                       AudioFormatUtil.changeToMp3(path1, path2);
//                       fileName=path2;
//                  }
                    
                    //fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
                    File oldfile = mr.getFile(name);
                    String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
                    String hWidth = "";
                    int fileHeight = 0;
                    int fileWidth = 0;
                    if("bmp".equalsIgnoreCase(fileType)||"gif".equalsIgnoreCase(fileType)||"jpeg".equalsIgnoreCase(fileType)||"jpg".equalsIgnoreCase(fileType)||"png".equalsIgnoreCase(fileType)){
                    	fileHeight = getImgHeight(oldfile);
                    	fileWidth = getImgWidth(oldfile);
                    	hWidth = fileHeight+","+fileWidth;
                    	if(400000<oldfile.length()){
                    		String oldurl = oldfile.getAbsolutePath();
                            File file = new File(root+UUIDUtil.uuidStr()+oldurl.substring(oldurl.lastIndexOf(".")));
                            zipImageFile(oldfile, file, fileWidth, fileHeight, 0.5f);
                            oldfile.delete();
                            Map<String, Object> map = new HashMap<String,Object>();
                            map.put("file", file.getAbsolutePath());
                            map.put("length", file.length());
                            map.put("fileName", fileName);
        					map.put("hwidth", hWidth);
                            fileList.add(map);
                    	}else{
                    		Map<String, Object> map = new HashMap<String,Object>();
                            map.put("file", oldfile.getAbsolutePath());
                            map.put("length", oldfile.length());
                            map.put("fileName", fileName);
        					map.put("hwidth", hWidth);
                            fileList.add(map);
                    	}
                    }else{
                        Map<String, Object> map = new HashMap<String,Object>();
                        map.put("file", oldfile.getAbsolutePath());
                        map.put("length", oldfile.length());
                        map.put("fileName", fileName);
    					map.put("hwidth", hWidth);
                        fileList.add(map);
                    }
                    
                }

                //上传文件
                fileList = HttpFileUtil.uploadFileList(targetPath, fileList);
                
                //将文件信息插入数据库
                List<Map<String,Object>> result = ftpService.uploadFileList(jzcs.getJz_dm() ,fileList, wjLj, yhDm, sjUUID);
                
                out.print(GsonUtil.toJson(result));
                //System.err.println(result);
                out.flush();
            } catch(Exception e){
                out.print("{\"URL\":\"\",\"WJDM\":\"\", \"MSG\":\"文件上传过程中出现异常！\"}");
                e.printStackTrace();
            }
        }
    }
    
    /** 
     * 获取图片宽度 
     * @param file  图片文件 
     * @return 宽度 
     */  
    public static int getImgWidth(File file) {  
        InputStream is = null;  
        BufferedImage src = null;  
        int ret = -1;  
        try {  
            is = new FileInputStream(file);  
            src = javax.imageio.ImageIO.read(is);  
            ret = src.getWidth(null); // 得到源图宽  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ret;  
    }  
        
        
    /** 
     * 获取图片高度 
     * @param file  图片文件 
     * @return 高度 
     */  
    public static int getImgHeight(File file) {  
        InputStream is = null;  
        BufferedImage src = null;  
        int ret = -1;  
        try {  
            is = new FileInputStream(file);  
            src = javax.imageio.ImageIO.read(is);  
            ret = src.getHeight(null); // 得到源图高  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ret;  
    }  
    
    /**  
     * 等比例压缩图片文件<br> 先保存原文件，再压缩、上传  
     * @param oldFile  要进行压缩的文件  
     * @param newFile  新文件  
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放）  
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）  
     * @param quality 质量  
     * @return 返回压缩后的文件的全路径  
     */    
	public static String zipImageFile(File oldFile,File newFile, int width, int height,    
            float quality) {    
        if (oldFile == null) {    
            return null;    
        }    
        try {    
            /** 对服务器上的临时文件进行处理 */    
            Image srcFile = ImageIO.read(oldFile);    
            int w = srcFile.getWidth(null);    
        //  System.out.println(w);    
            int h = srcFile.getHeight(null);    
        //  System.out.println(h);    
            double bili;    
            if(width>0){    
                bili=width/(double)w;    
                height = (int) (h*bili);    
            }else{    
                if(height>0){    
                    bili=height/(double)h;    
                    width = (int) (w*bili);    
                }    
            }    
            /** 宽,高设定 */    
            BufferedImage tag = new BufferedImage(width, height,    
                    BufferedImage.TYPE_INT_RGB);    
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);    
            //String filePrex = oldFile.getName().substring(0, oldFile.getName().indexOf('.'));    
            /** 压缩后的文件名 */    
            //newImage = filePrex + smallIcon+  oldFile.getName().substring(filePrex.length());    
    
            /** 压缩之后临时存放位置 */    
            FileOutputStream out = new FileOutputStream(newFile);    
    
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);    
            /** 压缩质量 */    
            jep.setQuality(quality, true);    
            encoder.encode(tag, jep);    
            out.close();    
    
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
        return newFile.getAbsolutePath();    
    }    
}
