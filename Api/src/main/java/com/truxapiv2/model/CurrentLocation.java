package com.truxapiv2.model;

public class CurrentLocation {

	private String lattitude;
	
	private String logitude;
	
	private String datetime;
	
	private String deviceid;

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getLogitude() {
		return logitude;
	}

	public void setLogitude(String logitude) {
		this.logitude = logitude;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	@Override
	public String toString() {
		return "CurrentLocation [lattitude=" + lattitude + ", logitude=" + logitude + ", datetime=" + datetime
				+ ", deviceid=" + deviceid + "]";
	}
	
	
}
