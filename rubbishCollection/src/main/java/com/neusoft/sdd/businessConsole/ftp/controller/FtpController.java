package com.neusoft.sdd.businessConsole.ftp.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.base.controller.BaseSimpleFormController;
import com.neusoft.sdd.base.exception.IServiceException;
import com.neusoft.sdd.base.model.JsonResult;
import com.neusoft.sdd.base.systemLog.SystemControllerLog;
import com.neusoft.sdd.businessConsole.ftp.model.Xtjzmx;
import com.neusoft.sdd.businessConsole.ftp.service.IFtpService;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

/**
 * 功能：ftp
 * @author 王晓宇
 *
 */
@Controller
@RequestMapping("/businessConsole/ftp")
public class FtpController  extends BaseSimpleFormController{
  
	@Autowired
	private IFtpService  ftpService;
	
	@RequestMapping("/demo")
	public String getDemo(){
		return "/demo/upload";
	}
	
	@RequestMapping("/demo_2")
	public String getDemo2(){
		return "/demo/upload_yh";
	}
	
	@RequestMapping("/demo_3")
	public String getDemo3(){
		return "/demo/upload_xg";
	}
	
	@RequestMapping("/queryButton")
	public String getQueryButton(){
		return "/demo/queryButton";
	}
	
	@RequestMapping("/queryZButton")
	public String getQueryZButton(){
		return "/demo/queryZButton";
	}
	
	/**
	  * app下载二维码
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：王晓宇 
	  * 作成时间：2017-10-13
	 */
	@RequestMapping("/getAppPic")
	public String getAppPic(){
		return "/systemSetting/appPic";
	}
	
	@RequestMapping("/delWjByWjdm")
	@SystemControllerLog(description="删除ftp文件，根据文件代码")
	public void delWjByWjdm(HttpServletRequest request,HttpServletResponse response){
		try {
			JsonResult result = new JsonResult();
			String data = request.getParameter("data");
			Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
			ftpService.updateWjzfbj(String.valueOf(param.get("WJDM")),"1");
			result.setSuccess(true);
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> delWjByWjdm() Exception : "+ e);
		}
	}
	
	@RequestMapping("/getDownloadWj")
	public void getDownloadWj(HttpServletRequest request,HttpServletResponse response){
        try {
        	String whlj = request.getParameter("wjlj");
            String root =System.getProperty("java.io.tmpdir");
        	JsonResult result = new JsonResult();
        	String[] filelj = whlj.split("/");
    		String fileName = filelj[filelj.length-1];
    		File downLoadFile = ftpService.getHttpDownload(whlj,fileName);
            String newFileUrl = root + fileName;
            ftpService.copyFile(downLoadFile,newFileUrl,true); // 将文件保存到临时文件夹中
            downLoadFile.delete();
            result.setSuccess(true);
            result.setMsg(fileName);
            result.setCode(root);
            printHttpServletResponse(GsonUtil.toJson(result),response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	}
	
	/**
	  * 下载文件
	  * 部门：软件开发事业部	 
	  * 描述：略
	  * 作成者：王晓宇 
	  * 作成时间：2017-10-13
	 */
	 @RequestMapping("/downloadTask")
	 	public void downloadTask(HttpServletRequest request,HttpServletResponse response){
	 		String filePath = request.getParameter("filepath");
	 		String pageName = request.getParameter("pageName");
	 		try {
	 			download(request, response, filePath, URLDecoder.decode(pageName,"utf-8"));
	 		} catch (Exception e) {
	 			throw new IServiceException(this.getClass()	+ " --> downloadTask() Exception : " + e);
	 		}
	 	}
	 
	/**
	 * 部门：软件开发事业部
	 * 功能：获取文件明细
	 * 描述：
	 * 作成者： 王晓宇
	 * 作成时间：2017-10-20 下午4:50:40
	 */
	@RequestMapping("/getXtjzmxBySjuuid")
	public void getXtjzmxBySjuuid(HttpServletRequest request,HttpServletResponse response){
		try {
			String data = request.getParameter("data");
			Map<String, Object> param = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
			List<Xtjzmx> result = ftpService.getXtjzmxBySjuuid(String.valueOf(param.get("SJ_UUID")));
			printHttpServletResponse(GsonUtil.toJson(result),response);
		} catch (Exception e) {
			throw new IServiceException(this.getClass()+ "  --> getXtjzmxBySjuuid() Exception : "+ e);
		}
	}
}
