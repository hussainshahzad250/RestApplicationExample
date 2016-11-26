package com.truxapiv2.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DriverLoginJson {
	
	public String vehicleNo;
	public Double lgLat;
	public Double lgLong;
	public Integer punchIn;
	@Temporal(TemporalType.TIMESTAMP)
	public Date punchTime;
	public String deviceId;
	public String lastLogoutTime;
	public String apkVersion;
	public Integer openingKm = 0;
	public Integer closingKm = 0;
	
	public Integer getOpeningKm() {
		return openingKm;
	}
	public void setOpeningKm(Integer openingKm) {
		this.openingKm = openingKm;
	}
	public Integer getClosingKm() {
		return closingKm;
	}
	public void setClosingKm(Integer closingKm) {
		this.closingKm = closingKm;
	}
	public String getApkVersion() {
		return apkVersion;
	}
	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}
	public String getLastLogoutTime() {
		return lastLogoutTime;
	}
	public void setLastLogoutTime(String lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public Double getLgLat() {
		return lgLat;
	}
	public void setLgLat(Double lgLat) {
		this.lgLat = lgLat;
	}
	public Double getLgLong() {
		return lgLong;
	}
	public void setLgLong(Double lgLong) {
		this.lgLong = lgLong;
	}
	public Integer getPunchIn() {
		return punchIn;
	}
	public void setPunchIn(Integer punchIn) {
		this.punchIn = punchIn;
	}
	public Date getPunchTime() {
		return punchTime;
	}
	public void setPunchTime(Date punchTime) {
		this.punchTime = punchTime;
	}
	
}
