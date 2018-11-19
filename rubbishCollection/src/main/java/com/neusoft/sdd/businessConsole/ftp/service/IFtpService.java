package com.neusoft.sdd.businessConsole.ftp.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.neusoft.sdd.businessConsole.ftp.model.Xtjzcs;
import com.neusoft.sdd.businessConsole.ftp.model.Xtjzmx;

public interface IFtpService extends Serializable{
	
	public Map<String,Object> uploadFile(InputStream fileInputStream, String fileName,
			String wjjdm, String lrryDm,String sjUUID) throws NumberFormatException,
			IOException;
	
	public void updateWjzfbj(String wj_dm, String zf_bj);
	public void updateWjzfbj(String sj_uuid,String wj_dm, String zf_bj);
	
	public Xtjzmx getXtjzmxBy(String wj_dm, String sj_uuid);
	public List<Xtjzmx> getXtjzmxBySjuuid(String sj_uuid);
	
	public String getWjURL(String wj_dm);
	
	public void UpdateZfbjAndSjuuid(String wj_dm, String zf_bj, String sj_uuid);
	
	public Xtjzcs getXtjzcs();
	
	public List<Map<String, Object>> uploadFileList(String jzdm,List<Map<String, Object>> fileList ,String wjjdm, String lrryDm, String sjUUID);
    
	public Map<String,Object> copyFile(File srcFile, String destFileName,boolean overlay);
	
	public File getHttpDownload(String whlj,String fileName);
	
	public void updateZfbjAndSjuuidByfwqm(String url, String zf_bj, String sj_uuid);
	
	public void updMoreWjZf(List<String> url,String Zj);
	
	public void delWjZf(String Zj);
}
