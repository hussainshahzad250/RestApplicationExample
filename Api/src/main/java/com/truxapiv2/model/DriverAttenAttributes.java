package com.truxapiv2.model;

import javax.persistence.Entity;

/** 
 * DriverAttenAttributes
 */
@Entity
public class DriverAttenAttributes {

	private Integer driverId;
	
	private Integer pFlag;
	
	private Integer agentId;
	
	private Long timestamp;
	
	private String driverAPKVersion;
	
	private Boolean checkPending;
	
	private String vehicleNumber;
	
	private Integer closingKm;
	
	private Integer openingKm;
	
	private Integer clientId;
	
	
	
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getClosingKm() {
		return closingKm;
	}

	public void setClosingKm(Integer closingKm) {
		this.closingKm = closingKm;
	}

	public Integer getOpeningKm() {
		return openingKm;
	}

	public void setOpeningKm(Integer openingKm) {
		this.openingKm = openingKm;
	}

	public Boolean getCheckPending() {
		return checkPending;
	}

	public void setCheckPending(Boolean checkPending) {
		this.checkPending = checkPending;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Integer getpFlag() {
		return pFlag;
	}

	public void setpFlag(Integer pFlag) {
		this.pFlag = pFlag;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDriverAPKVersion() {
		return driverAPKVersion;
	}

	public void setDriverAPKVersion(String driverAPKVersion) {
		this.driverAPKVersion = driverAPKVersion;
	}
	
	
}