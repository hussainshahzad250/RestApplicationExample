package com.truxapiv2.model;

import javax.persistence.Transient;

public class RestResponce {

	@Transient
	private String errorCode="101";
	
	@Transient
	private String errorMesaage="Something went wrong!!";
	
	@Transient
	private Object data;
	
	@Transient
	private Integer count;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMesaage() {
		return errorMesaage;
	}

	public void setErrorMesaage(String errorMesaage) {
		this.errorMesaage = errorMesaage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
