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

@Entity
@Table(name = "trsptr_login_history")
public class TransporterLoginHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "trsptr_registration_id")
	private Integer trsptrRegistrationId;
	
	@Column(name = "login_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginDate;
	
	@Column(name = "device_mac")
	private String deviceMac;
	
	@Column(name = "current_app_version")
	private String currentAppVersion;
	
	@Column(name = "is_login")
	private Integer isLogin;
	
	@Column(name = "is_success")
	private Integer isSuccess;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTrsptrRegistrationId() {
		return trsptrRegistrationId;
	}

	public void setTrsptrRegistrationId(Integer trsptrRegistrationId) {
		this.trsptrRegistrationId = trsptrRegistrationId;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getCurrentAppVersion() {
		return currentAppVersion;
	}

	public void setCurrentAppVersion(String currentAppVersion) {
		this.currentAppVersion = currentAppVersion;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
}
