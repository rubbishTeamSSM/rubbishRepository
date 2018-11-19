package com.neusoft.sdd.businessConsole.ftp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.sdd.base.constant.GlobalConstant;
import com.neusoft.sdd.businessConsole.ftp.dao.IFtpDao;
import com.neusoft.sdd.businessConsole.ftp.model.Xtjzcs;
import com.neusoft.sdd.businessConsole.ftp.model.Xtjzmx;
import com.neusoft.sdd.businessConsole.ftp.service.IFtpService;
import com.neusoft.sdd.util.commonUtil.UUIDUtil;
import com.neusoft.sdd.util.file.ContinueFTP;
import com.neusoft.sdd.util.file.HttpFileUtil;

//@Transactional
@Service
public class FtpServiceImpl implements IFtpService {

	private static final long serialVersionUID = -7348502929870725913L;
	
	@Autowired
	private IFtpDao ftpDao;

	/**
	 * 部门：软件开发事业部
	 * 功能：文件上传
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-10-30
	 */
	public Map<String,Object> uploadFile(InputStream fileInputStream, String fileName,
			String wjjdm, String lrryDm, String sjUUID)
			throws NumberFormatException, IOException {
		//获取现用介质参数
		Xtjzcs xtjzcs = ftpDao.getCurrentJzcs();
		
		ContinueFTP continueFTP = new ContinueFTP();
		
		//将介质路径分割成IP和端口
		String[] adress = xtjzcs.getJzlj().split(":");
		
		continueFTP.connect(adress[0], Integer.parseInt(adress[1]),
				xtjzcs.getJzzh(), xtjzcs.getJzmm());

		//文件格式
		String wjgs = fileName.substring(fileName.lastIndexOf(".")+1);
		//文件服务器名
		String wjfwqm = UUIDUtil.uuidStr() + "." + wjgs;
		//文件URL--用nginx文件下载
//		String wjUrl = "http://" + adress[0] + "/wechat/download/" + wjjdm
//				+ "/" + wjfwqm;

		continueFTP.uploadFile(fileInputStream, "/" + wjjdm + "/" + wjfwqm);
		
		Xtjzmx xtjzmx = new Xtjzmx();
		
		xtjzmx.setWj_dm(UUIDUtil.uuidStr());//文件代码
		xtjzmx.setSj_uuid(sjUUID);//数据uuid
		xtjzmx.setWjj_dm(wjjdm);//文件夹代码
		if(fileName.indexOf("\\")!=-1){
			fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
		}
		if(fileName.indexOf("/")!=-1){
			fileName = fileName.replace("/", "");
		}
		xtjzmx.setWjym(fileName);//文件原名
		xtjzmx.setWjfwqm(wjfwqm);//文件服务器名
		xtjzmx.setWjgs(wjgs);//文件格式
		//xtjzmx.setWj_url(wjUrl);//文件URL
		xtjzmx.setJz_dm(xtjzcs.getJz_dm());//介质代码
		xtjzmx.setZf_bj(GlobalConstant.ZF_BJ);//作废标记
		xtjzmx.setLrry_dm(lrryDm);//录入人员代码
		
		//插入介质明细
		ftpDao.insertXtjzmx(xtjzmx);
		
		String url = getWjURL(xtjzmx.getWj_dm());
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("WJDM", xtjzmx.getWj_dm());
		result.put("URL", url);
		return result;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：更新作废的文件的作废标记
	 * 描述：新上传的文件是作废的，在完成操作时更新为为作废
	 * 作成者：朱庆锋
	 * 作成时间：2015-10-30
	 */
	public void updateWjzfbj(String wj_dm, String zf_bj){
		Map<String, String> param = new HashMap<String, String>();
		param.put("wj_dm", wj_dm);//文件代码
		param.put("zf_bj", zf_bj);//作废标记
		
		//更新作废标记
		ftpDao.updateWjzfbj(param);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取介质详细信息
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-10-30
	 */
	public Xtjzmx getXtjzmxBy(String wj_dm, String sj_uuid){
		Xtjzmx obj = new Xtjzmx();
		
		obj.setWj_dm(wj_dm);
		obj.setSj_uuid(sj_uuid);
		
		//获取介质详细信息
		return ftpDao.getXtjzmxBy(obj);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取介质详细信息集合
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-10-30
	 */
	public List<Xtjzmx> getXtjzmxBySjuuid(String sj_uuid){
		Xtjzmx obj = new Xtjzmx();
		obj.setSj_uuid(sj_uuid);
		//获取介质详细信息
		return ftpDao.getXtjzmxBySjuuid(obj);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取文件URL
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-11-2
	 */
	public String getWjURL(String wj_dm){
		Map<String, String> param = new HashMap<String, String>();
		param.put("wj_dm", wj_dm);//文件代码
		
		return ftpDao.getWjURL(param);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：更新数据uuid和作废标记
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-11-16
	 */
	public void UpdateZfbjAndSjuuid(String wj_dm, String zf_bj, String sj_uuid){
		Map<String, String> param = new HashMap<String, String>();
		param.put("wj_dm", wj_dm);//文件代码
		param.put("zf_bj", zf_bj);//作废标记
		param.put("sj_uuid", sj_uuid);//数据uuid
		
		ftpDao.UpdateZfbjAndSjuuid(param);
	}
	
	@Override
	public void updateZfbjAndSjuuidByfwqm(String url, String zf_bj,
			String sj_uuid) {
		Map<String, String> param = new HashMap<String, String>();
		String[] temp = url.split("/");
		param.put("wjfwqm", temp[temp.length-1]);//文件代码
		param.put("zf_bj", zf_bj);//作废标记
		param.put("sj_uuid", sj_uuid);//数据uuid
		ftpDao.updateZfbjAndSjuuidByfwqm(param);
	}

	@Override
	public void updateWjzfbj(String sj_uuid, String wj_dm, String zf_bj) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("wj_dm", wj_dm);//文件代码
		param.put("zf_bj", zf_bj);//作废标记
		param.put("sj_uuid", sj_uuid);//数据UUID
		//更新作废标记
		ftpDao.updateWjzfbj(param);
		
	}
	
	@Override
	public Xtjzcs getXtjzcs(){
		//获取现用介质参数
		Xtjzcs xtjzcs = ftpDao.getCurrentJzcs();
		return xtjzcs;
	}
	
	@Override
	public  List<Map<String, Object>> uploadFileList(String jzdm,List<Map<String, Object>> fileList,String wjjdm, String lrryDm, String sjUUID){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(null!=fileList&&0<fileList.size()){
			for (int i = 0; i < fileList.size(); i++) {
				Map<String, Object> fileMap = fileList.get(i);
				if(null!=fileMap){
					String fileName = String.valueOf(fileMap.get("fileName"));
					//文件格式
					String wjgs = fileName.substring(fileName.lastIndexOf(".")+1);
					//文件服务器名
					String wjfwqm = String.valueOf(fileMap.get("wjfwqm"));
			        Xtjzmx xtjzmx = new Xtjzmx();
			        String wjdm = UUIDUtil.uuidStr();
					xtjzmx.setWj_dm(wjdm);//文件代码
					xtjzmx.setSj_uuid(sjUUID);//数据uuid
					xtjzmx.setWjj_dm(wjjdm);//文件夹代码
					if(fileName.indexOf("\\")!=-1){
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					}
					if(fileName.indexOf("/")!=-1){
						fileName = fileName.replace("/", "");
					}
					xtjzmx.setWjym(fileName);//文件原名
					xtjzmx.setWjfwqm(wjfwqm);//文件服务器名
					xtjzmx.setWjgs(wjgs);//文件格式
					//xtjzmx.setWj_url(wjUrl);//文件URL
					xtjzmx.setJz_dm(jzdm);//介质代码
					xtjzmx.setZf_bj(GlobalConstant.ZF_BJ);//作废标记
					xtjzmx.setLrry_dm(lrryDm);//录入人员代码
					xtjzmx.setWjdx(String.valueOf(fileMap.get("hwidth")));//录入人员代码
					//插入介质明细
					ftpDao.insertXtjzmx(xtjzmx);
					String url = getWjURL(xtjzmx.getWj_dm());
					Map<String, Object> wj = new HashMap<String, Object>();
					wj.put("URL", url);
					wj.put("WJDM", wjdm);
					wj.put("MSG", "上传成功");
					result.add(wj);
				}
			}
		}
		return result;
	}
	
	/**
	 *  部门：软件开发事业部 
	 *  功能：复制文件操作
	 *  描述：略 
	 *  作成者：王晓宇
	 */
	@Override
    public Map<String,Object> copyFile(File srcFile, String destFileName,boolean overlay) { 
		Map<String,Object> result = new HashMap<String, Object>();
	
	    // 判断源文件是否存在  
	    if (!srcFile.exists()) {  
	    	result.put("trfa", false);
			result.put("msg", "找不到目标源文件"); 
	        return result;  
	    } else if (!srcFile.isFile()) {  
	    	result.put("trfa", false);
			result.put("msg", "目标源文件不是一个文件"); 
	        return result;  
	    }  
	
	    // 判断目标文件是否存在  
	    File destFile = new File(destFileName);  
	    if (destFile.exists()) {  
	        // 如果目标文件存在并允许覆盖  
	        if (overlay) {  
	            // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
	            new File(destFileName).delete();  
	        }  
	    } else {  
	        // 如果目标文件所在目录不存在，则创建目录  
	        if (!destFile.getParentFile().exists()) {  
	            // 目标文件所在目录不存在  
	            if (!destFile.getParentFile().mkdirs()) {  
	                // 复制文件失败：创建目标文件所在目录失败  
	            	result.put("trfa", false);
					result.put("msg", "创建目标文件所在目录失败");
	                return result;  
	            }  
	        }  
	    }  
	
	    // 复制文件  
	    int byteread = 0; // 读取的字节数  
	    InputStream in = null;  
	    OutputStream out = null;  
	
	    try {  
	        in = new FileInputStream(srcFile);  
	        out = new FileOutputStream(destFile);  
	        byte[] buffer = new byte[1024];  
	
	        while ((byteread = in.read(buffer)) != -1) {  
	            out.write(buffer, 0, byteread);  
	        } 
	        result.put("trfa", true);
			result.put("msg", "复制文件成功");
	        return result;  
	    } catch (FileNotFoundException e) {  
	    	result.put("trfa", false);
			result.put("msg", "复制文件失败");
	        return result;
	    } catch (IOException e) {  
	    	result.put("trfa", false);
			result.put("msg", "复制文件失败");
	        return result; 
	    } finally {  
	        try {  
	            if (out != null)  
	                out.close();  
	            if (in != null)  
	                in.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	}  

	@Override
	public File getHttpDownload(String whlj,String fileName){
		return HttpFileUtil.downFile(whlj,fileName);
	}

	@Override
	public void updMoreWjZf(List<String> url,String Zj){
		List<Xtjzmx> oldjzmx = getXtjzmxBySjuuid(Zj);
		if(null!=oldjzmx&&0<oldjzmx.size()){
			for(int i = 0; i < oldjzmx.size(); i++) {
				updateWjzfbj(Zj,oldjzmx.get(i).getWj_dm(), "1");
			}
		}
		if(null!=url&&0<url.size()){
			for (int i = 0; i < url.size(); i++) {
			    updateZfbjAndSjuuidByfwqm(url.get(i) ,"0", Zj);
			}
		}
	}
	@Override
	public void delWjZf(String Zj){
		List<Xtjzmx> oldjzmx = getXtjzmxBySjuuid(Zj);
		if(null!=oldjzmx&&0<oldjzmx.size()){
			for(int i = 0; i < oldjzmx.size(); i++) {
				updateWjzfbj(Zj,oldjzmx.get(i).getWj_dm(), "1");
			}
		}
	}
}
