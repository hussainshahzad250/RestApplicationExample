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
@Table(name = "trsptr_freight_chart")
public class TransporterFreightChart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "trsptr_registration_id")
	private Integer trsptrRegistrationId;
	
	@Column(name = "source_city_id")
	private Integer sourceCityId;
	
	@Column(name = "destination_city_id")
	private Integer destinationCityId;
	
	@Column(name = "vehicle_type_id")
	private Integer vehicleTypeId;
	
	@Column(name = "body_type")
	private String bodyType;
	
	@Column(name = "freight_rate")
	private Integer freightRate;
	
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

	public Integer getSourceCityId() {
		return sourceCityId;
	}

	public void setSourceCityId(Integer sourceCityId) {
		this.sourceCityId = sourceCityId;
	}

	public Integer getDestinationCityId() {
		return destinationCityId;
	}

	public void setDestinationCityId(Integer destinationCityId) {
		this.destinationCityId = destinationCityId;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public Integer getFreightRate() {
		return freightRate;
	}

	public void setFreightRate(Integer freightRate) {
		this.freightRate = freightRate;
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
