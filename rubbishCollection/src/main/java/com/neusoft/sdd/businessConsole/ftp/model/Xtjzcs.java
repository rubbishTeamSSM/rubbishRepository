package com.neusoft.sdd.businessConsole.ftp.model;

/**
 * 
 * [简要描述]:系统介质参数<br/>
 * [详细描述]:<br/>
 *
 * @version [revision],2015-10-30
 * @since Neusoft 001
 */
public class Xtjzcs {
	private String uuid;//UUID
	private String jz_dm;//介质代码（0001起始，后面自动加一）
	private String jzlj;//介质路径
	private String jzzh;//介质账号
	private String jzmm;//介质密码
	private String jzxsybl;//介质现使用比例(默认是0，每天晚上定时任务更新)
	private String jzyjbl;//介质预警比例(默认80)
	private String jzxy_bj;//介质现用标记(0非现用，1现用，现用只能有一个)
	private String jzrl;//介质容量（以GB为单位）
	private String lr_sj;//录入时间
	private String lrry_dm;//录入人员代码
	private String fwdz;//ftp服务器地址
	private String dkh;//端口号
	private String dqdz;//读取地址
	
	
	public String getDqdz() {
		return dqdz;
	}
	public void setDqdz(String dqdz) {
		this.dqdz = dqdz;
	}
	public String getDkh() {
		return dkh;
	}
	public void setDkh(String dkh) {
		this.dkh = dkh;
	}
	public String getFwdz() {
		return fwdz;
	}
	public void setFwdz(String fwdz) {
		this.fwdz = fwdz;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getJz_dm() {
		return jz_dm;
	}
	public void setJz_dm(String jz_dm) {
		this.jz_dm = jz_dm;
	}
	public String getJzlj() {
		return jzlj;
	}
	public void setJzlj(String jzlj) {
		this.jzlj = jzlj;
	}
	public String getJzzh() {
		return jzzh;
	}
	public void setJzzh(String jzzh) {
		this.jzzh = jzzh;
	}
	public String getJzmm() {
		return jzmm;
	}
	public void setJzmm(String jzmm) {
		this.jzmm = jzmm;
	}
	public String getJzxsybl() {
		return jzxsybl;
	}
	public void setJzxsybl(String jzxsybl) {
		this.jzxsybl = jzxsybl;
	}
	public String getJzyjbl() {
		return jzyjbl;
	}
	public void setJzyjbl(String jzyjbl) {
		this.jzyjbl = jzyjbl;
	}
	public String getJzxy_bj() {
		return jzxy_bj;
	}
	public void setJzxy_bj(String jzxy_bj) {
		this.jzxy_bj = jzxy_bj;
	}
	public String getJzrl() {
		return jzrl;
	}
	public void setJzrl(String jzrl) {
		this.jzrl = jzrl;
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
}
