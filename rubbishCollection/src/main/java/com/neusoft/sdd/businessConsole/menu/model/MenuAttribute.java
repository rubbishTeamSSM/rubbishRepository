package com.neusoft.sdd.businessConsole.menu.model;

public class MenuAttribute {
	private String url;
	private String AUTH_CODE;
	private int LEVEL;
	private String TYPE;
	private String info;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getAUTH_CODE() {
		return AUTH_CODE;
	}
	public void setAUTH_CODE(String aUTH_CODE) {
		AUTH_CODE = aUTH_CODE;
	}

	public int getLEVEL() {
		return LEVEL;
	}
	public void setLEVEL(int LEVEL) {
		this.LEVEL = LEVEL;
	}

	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String TYPE) {
		this.TYPE = TYPE;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
