package com.truxapiv2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * Client Mandate Vehicle Deployment
 */
@Entity
@Table(name="client_mandate_request")
public class ClientMandateVehicleRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "request_id")
	private Integer id;
	
	@Column(name = "client_sub_id")
	private Integer clientSubId;
	
	@Column(name = "mandate_type")
	private String mandate_type;
	
	@Column(name = "vehicle_type")
	private String vehicleType;
	
	@Column(name = "body_type")
	private String bodyType;
	
	@Column(name = "destination")
	private String destination;
	
	@Column(name = "no_of_vehicles")
	private Integer noOfVehicles;
	
	@Column(name = "box_weight")
	private Integer boxWeight;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "request_status")
	private Integer requestStatus;
	
	@Column(name = "reporting_time")
	private String reportingTime;
	
	@Column(name = "mandate_detail_id")
	private Integer mandateDetailId;
	
	@Column(name = "modified_date")
	private Date modifiedDate = new Date();
	
	@Column(name = "modified_by")
	private Integer modifiedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClientSubId() {
		return clientSubId;
	}

	public void setClientSubId(Integer clientSubId) {
		this.clientSubId = clientSubId;
	}

	public String getMandate_type() {
		return mandate_type;
	}

	public void setMandate_type(String mandate_type) {
		this.mandate_type = mandate_type;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getNoOfVehicles() {
		return noOfVehicles;
	}

	public void setNoOfVehicles(Integer noOfVehicles) {
		this.noOfVehicles = noOfVehicles;
	}

	public Integer getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(Integer boxWeight) {
		this.boxWeight = boxWeight;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(Integer requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getReportingTime() {
		return reportingTime;
	}

	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}

	public Integer getMandateDetailId() {
		return mandateDetailId;
	}

	public void setMandateDetailId(Integer mandateDetailId) {
		this.mandateDetailId = mandateDetailId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
}
