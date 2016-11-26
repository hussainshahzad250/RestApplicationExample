package com.truxapiv2.model;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

public class CassandraTrack {

	@Transient
	private String ls_vh_loct_id;
	
	@Transient
	private int driverId;
	@Transient
	private int bookingId;
	
	@Transient
	private int dropId;
	@Transient
	private String driverMobile;
	
	@Transient
	private int companySubId;
	
	@Transient
	private double vehicleLat;
	
	@Transient
	private String vehicleNo;
	
	@Transient
	private double vehicleDistance;
	
	@Transient
	private double vehicleLong;
	
	@Transient
	private double speed;	
	
	@Transient
	private int punchIn = 0;
	
	@Transient
	private int isIdle = 0;

	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	private Date loct_time;
	
	@Transient
	private String deviceBookingId;
	
	@Transient
	private String deviceDropId;
	
	@Transient
	private String currentTMS;
	
	@Transient
	private String deviceId;

	public String getLs_vh_loct_id() {
		return ls_vh_loct_id;
	}

	public void setLs_vh_loct_id(String ls_vh_loct_id) {
		this.ls_vh_loct_id = ls_vh_loct_id;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public int getCompanySubId() {
		return companySubId;
	}

	public void setCompanySubId(int companySubId) {
		this.companySubId = companySubId;
	}

	public double getVehicleLat() {
		return vehicleLat;
	}

	public void setVehicleLat(double vehicleLat) {
		this.vehicleLat = vehicleLat;
	}

	public double getVehicleLong() {
		return vehicleLong;
	}

	public void setVehicleLong(double vehicleLong) {
		this.vehicleLong = vehicleLong;
	}

	public Date getLoct_time() {
		return loct_time;
	}

	public void setLoct_time(Date loct_time) {
		this.loct_time = loct_time;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public double getVehicleDistance() {
		return vehicleDistance;
	}

	public void setVehicleDistance(double vehicleDistance) {
		this.vehicleDistance = vehicleDistance;
	}

	public int getDropId() {
		return dropId;
	}

	public void setDropId(int dropId) {
		this.dropId = dropId;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getPunchIn() {
		return punchIn;
	}

	public void setPunchIn(int punchIn) {
		this.punchIn = punchIn;
	}

	public int getIsIdle() {
		return isIdle;
	}

	public void setIsIdle(int isIdle) {
		this.isIdle = isIdle;
	}
	
	public String getDeviceBookingId() {
		return deviceBookingId;
	}

	public void setDeviceBookingId(String deviceBookingId) {
		this.deviceBookingId = deviceBookingId;
	}
	
	public String getDeviceDropId() {
		return deviceDropId;
	}

	public void setDeviceDropId(String deviceDropId) {
		this.deviceDropId = deviceDropId;
	}

	public String getCurrentTMS() {
		return currentTMS;
	}

	public void setCurrentTMS(String currentTMS) {
		this.currentTMS = currentTMS;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "CassandraTrack [ls_vh_loct_id=" + ls_vh_loct_id + ", driverId=" + driverId + ", bookingId=" + bookingId
				+ ", dropId=" + dropId + ", driverMobile=" + driverMobile + ", companySubId=" + companySubId
				+ ", vehicleLat=" + vehicleLat + ", vehicleNo=" + vehicleNo + ", vehicleDistance=" + vehicleDistance
				+ ", vehicleLong=" + vehicleLong + ", speed=" + speed + ", punchIn=" + punchIn + ", isIdle=" + isIdle
				+ ", loct_time=" + loct_time + ", deviceBookingId=" + deviceBookingId + ", deviceDropId=" + deviceDropId
				+ ", currentTMS=" + currentTMS + ", deviceId=" + deviceId + "]";
	}

	
}
