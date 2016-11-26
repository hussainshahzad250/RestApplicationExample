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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "trsptr_vehicle")
public class TransporterVehicleRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "trsptr_registration_id")
	private Integer trsptrRegistrationId;
	
	@Column(name = "vehicle_number")
	private String vehicleNumber;
	
	@Column(name = "vehicle_type_id")
	private Integer vehicleTypeId;
	
	@Column(name = "vehicle_body")
	private String vehicleBody;
	
	@Column(name = "model_year")
	private String modelYear;
	
	@Column(name = "insurance_expiry")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd", timezone="IST")
	private Date insuranceExpiry;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date createdOn;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "modified_on")
	@Temporal(TemporalType.TIMESTAMP)
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date modifiedOn;
	
	@Column(name = "is_active")
	private Integer isActive;

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

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getVehicleBody() {
		return vehicleBody;
	}

	public void setVehicleBody(String vehicleBody) {
		this.vehicleBody = vehicleBody;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public Date getInsuranceExpiry() {
		return insuranceExpiry;
	}

	public void setInsuranceExpiry(Date insuranceExpiry) {
		this.insuranceExpiry = insuranceExpiry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
}
