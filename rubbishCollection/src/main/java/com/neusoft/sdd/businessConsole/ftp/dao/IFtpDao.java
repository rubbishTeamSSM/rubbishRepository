package com.neusoft.sdd.businessConsole.ftp.dao;

import java.util.List;
import java.util.Map;

import com.neusoft.sdd.businessConsole.ftp.model.Xtjzcs;
import com.neusoft.sdd.businessConsole.ftp.model.Xtjzmx;

public interface IFtpDao {

	public Xtjzcs getCurrentJzcs();
	
	public void insertXtjzmx(Xtjzmx obj);
	
	public Xtjzmx getXtjzmxBy(Xtjzmx obj);
	
	public List<Xtjzmx> getXtjzmxBySjuuid(Xtjzmx obj);
	
	public void updateWjzfbj(Map<String, String> param);
	
	public String getWjURL(Map<String, String> param);
	
	public void UpdateZfbjAndSjuuid(Map<String, String> param);
	
	public void updateZfbjAndSjuuidByfwqm(Map<String, String> param);
}
