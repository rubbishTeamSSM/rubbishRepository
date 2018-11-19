package com.neusoft.sdd.base.model;


public class JsonResult {

	private boolean success;
	
	private String code;

	private String data;
	
	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private Object msg;
	
	private String service_id;

	
	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

}
