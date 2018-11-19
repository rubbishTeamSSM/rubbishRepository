package com.neusoft.sdd.businessConsole.ftp.model;

import java.io.File;

import com.neusoft.sdd.util.file.ContinueFTP;

/**
 * [简要描述]:系统介质明细<br/>
 * [详细描述]:<br/>
 *
 * @version [revision],2015-10-30
 * @since Neusoft 001
 */
public class Xtjzmx {
	private String wj_dm;//UUID
	private String sj_uuid;//上节点UUID(上层文件级数据UUID):此系统对应团队活动ID
	private String wjj_dm;//文件夹代码（背景图：bjt  文章：wz  消息：xx）
	private String wjym;//文件原名
	private String wjfwqm;//文件服务器名
	private String wjgs;//文件格式(点号后面的字符串，方便以后阅读器识别)
	private String wj_url;//文件URL
	private String wjdx;//文件大小（按字节算）
	private String jz_dm;//介质代码
	private char zf_bj;//作废标记(删除时使用，彻底删除时候就物理删除)
	private String lr_sj;//录入时间
	private String lrry_dm;//录入人员代码
	private String fwdz;//服务地址
	
	
	public String getFwdz() {
		return fwdz;
	}
	public void setFwdz(String fwdz) {
		this.fwdz = fwdz;
	}
	public String getWj_dm() {
		return wj_dm;
	}
	public void setWj_dm(String wj_dm) {
		this.wj_dm = wj_dm;
	}
	public String getSj_uuid() {
		return sj_uuid;
	}
	public void setSj_uuid(String sj_uuid) {
		this.sj_uuid = sj_uuid;
	}
	public String getWjj_dm() {
		return wjj_dm;
	}
	public void setWjj_dm(String wjj_dm) {
		this.wjj_dm = wjj_dm;
	}
	public String getWjym() {
		return wjym;
	}
	public void setWjym(String wjym) {
		this.wjym = wjym;
	}
	public String getWjfwqm() {
		return wjfwqm;
	}
	public void setWjfwqm(String wjfwqm) {
		this.wjfwqm = wjfwqm;
	}
	public String getWjgs() {
		return wjgs;
	}
	public void setWjgs(String wjgs) {
		this.wjgs = wjgs;
	}
	public String getWj_url() {
		return wj_url;
	}
	public void setWj_url(String wj_url) {
		this.wj_url = wj_url;
	}
	public String getWjdx() {
		return wjdx;
	}
	public void setWjdx(String wjdx) {
		this.wjdx = wjdx;
	}
	public String getJz_dm() {
		return jz_dm;
	}
	public void setJz_dm(String jz_dm) {
		this.jz_dm = jz_dm;
	}
	public char getZf_bj() {
		return zf_bj;
	}
	public void setZf_bj(char zf_bj) {
		this.zf_bj = zf_bj;
	}
	public String getLr_sj() {
		return lr_sj;
	}
	public void setLr_sj(String lr_sj) {
		this.lr_sj = lr_sj;
	}
	public String getLrry_dm() {
		return lrry_dm;
	}
	public void setLrry_dm(String lrry_dm) {
		this.lrry_dm = lrry_dm;
	}
	private String jzLj;//介质路径
	private String jzZh;//介质账号
	private String jzMm;//介质密码
	
	
	
	
	public String getJzLj() {
		return jzLj;
	}
	public void setJzLj(String jzLj) {
		this.jzLj = jzLj;
	}
	public String getJzZh() {
		return jzZh;
	}
	public void setJzZh(String jzZh) {
		this.jzZh = jzZh;
	}
	public String getJzMm() {
		return jzMm;
	}
	public void setJzMm(String jzMm) {
		this.jzMm = jzMm;
	}
	public File getFile() throws Exception{
		ContinueFTP ftp = new ContinueFTP();
		
		String[] jzLjDk = jzLj.split(":");
		ftp.connect(jzLjDk[0], Integer.parseInt(jzLjDk[1]), jzZh, jzMm);

		File downLoadFile = null;

		downLoadFile = ftp.downloadFiles(wjj_dm+"/" + wjfwqm);
	
		ftp.disconnect();
		return downLoadFile;
	}
	
	
	
	
}
