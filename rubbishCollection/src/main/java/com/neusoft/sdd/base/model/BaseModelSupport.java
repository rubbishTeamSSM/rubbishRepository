package com.neusoft.sdd.base.model;

public abstract class BaseModelSupport {
	private String sortName;
	private String direction;
	private String langCode;
	private String checked;
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLangCode() {
		return this.langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String fieldName) {
		this.sortName = fieldName;
	}
	
	
	private transient int page;
	private transient int rows;
	private transient String sort;
	private transient String order;
	private int startIndex;
	private int endIndex;
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}

	public void setStartIndex() {
		this.startIndex = (page - 1)* rows;
	}


	public void setEndIndex() {
		this.endIndex = page * rows;;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
}