package com.truxapiv2.model;

public class ResultObject {

	private Boolean resultStatus = false;
	
	private String resultMesaage="Something went wrong!!";
	
	private Object resultData;
	
	private Integer resultCode;
	
	
	public Boolean isResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Boolean resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResultMesaage() {
		return resultMesaage;
	}

	public void setResultMesaage(String resultMesaage) {
		this.resultMesaage = resultMesaage;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	
	
}
