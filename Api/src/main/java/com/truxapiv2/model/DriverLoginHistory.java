package com.truxapiv2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="driver_loginhistory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DriverLoginHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="login_id")
	private Integer loginId;
	@Column(name="driver_id")
	private String driverMobile;
	@Column(name="loc_lat")
	private Double loginLat;
	@Column(name="loc_long")
	private Double loginLong;
	@Column(name="device")
	private String device;
	@Column(name="punch_status")
	private int punchIngStatus;
	
	@Column(name="datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime; 
    
 	public DriverLoginHistory() {
		super();
	}

	public DriverLoginHistory(String driverMobile, Double loginLat,
			Double loginLong, String device) {
		super();
		this.driverMobile = driverMobile;
		this.loginLat = loginLat;
		this.loginLong = loginLong;
		this.device = device;
	}

	public Integer getLoginId() {
		return loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public Double getLoginLat() {
		return loginLat;
	}

	public void setLoginLat(Double loginLat) {
		this.loginLat = loginLat;
	}

	public Double getLoginLong() {
		return loginLong;
	}

	public void setLoginLong(Double loginLong) {
		this.loginLong = loginLong;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public int getPunchIngStatus() {
		return punchIngStatus;
	}

	public void setPunchIngStatus(int punchIngStatus) {
		this.punchIngStatus = punchIngStatus;
	} 
}
