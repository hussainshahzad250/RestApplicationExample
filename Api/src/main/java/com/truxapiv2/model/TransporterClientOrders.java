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
@Table(name = "trsptr_client_orders")
public class TransporterClientOrders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "from_city_id")
	private Integer fromCityId;
	
	@Column(name = "to_city_id")
	private Integer toCityId;
	
	@Column(name = "vehicle_type_id")
	private Integer vehicleTypeId;
	
	@Column(name = "vehicle_body")
	private String vehicleBody;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "deploy_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date deployDateTime;
	
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

	public Integer getFromCityId() {
		return fromCityId;
	}

	public void setFromCityId(Integer fromCityId) {
		this.fromCityId = fromCityId;
	}

	public Integer getToCityId() {
		return toCityId;
	}

	public void setToCityId(Integer toCityId) {
		this.toCityId = toCityId;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getDeployDateTime() {
		return deployDateTime;
	}

	public void setDeployDateTime(Date deployDateTime) {
		this.deployDateTime = deployDateTime;
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
