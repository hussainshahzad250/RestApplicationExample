package com.truxapiv2.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 
 * Client Mandate Vehicle Deployment
 */
@Entity
@Table(name="client_vehicle_deployment")
public class ClientMandateVehicleDeployment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "mandate_detail_id")
	private int mandateDetailId;
	
	@Column(name = "vehicle_no")
	private String vehicleNo;
	
	@Column(name = "reporting_time")
	private String reportingTime;
	
	@Column(name = "is_active")	
	private int isActive = 1;
		
	@Column
	private Date modifieddate = new Date();
	@Column
	private int modifiedby;
	
	@Transient
	private String deploymentRemark;
	
	@Column(name = "by_client")	
	private int byClient; 
	
	@Column(name = "client_request_id")	
	private int clientRequestId;
	
	@Column(name = "mandate_type")	
	private String mandateType; 
	
	@Column(name = "vehicle_type")	
	private String vehicleType; 
	
	@Column(name = "body_type")	
	private String bodyType; 
	
	@Column(name = "createddate")	
	private Date createdDate = new Date(); 
	
	@Column(name = "createdby")	
	private int createdBy; 
	 
	@Column(name = "costToDriver")	
	private Double costToDriver; 
	
	@Column(name = "advancePayment")	
	private Double advancePayment; 
	
	@Column(name = "revenueToCompany")	
	private Double revenueToCompany; 
	
	@Transient
	private String requestDate;
	 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMandateDetailId() {
		return mandateDetailId;
	}
	public void setMandateDetailId(int mandateDetailId) {
		this.mandateDetailId = mandateDetailId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getReportingTime() {
		return reportingTime;
	}
	public void setReportingTime(String reportingTime) {
		this.reportingTime = reportingTime;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public Date getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}
	public int getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}	
	public String getDeploymentRemark() {
		return deploymentRemark;
	}
	public void setDeploymentRemark(String deploymentRemark) {
		this.deploymentRemark = deploymentRemark;
	}
	
	public int getByClient() {
		return byClient;
	}
	public void setByClient(int byClient) {
		this.byClient = byClient;
	}
	public int getClientRequestId() {
		return clientRequestId;
	}
	public void setClientRequestId(int clientRequestId) {
		this.clientRequestId = clientRequestId;
	}
	
	public String getMandateType() {
		return mandateType;
	}
	public void setMandateType(String mandateType) {
		this.mandateType = mandateType;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	public Double getCostToDriver() {
		return costToDriver;
	}
	public void setCostToDriver(Double costToDriver) {
		this.costToDriver = costToDriver;
	}
	public Double getAdvancePayment() {
		return advancePayment;
	}
	public void setAdvancePayment(Double advancePayment) {
		this.advancePayment = advancePayment;
	}
	
	public Double getRevenueToCompany() {
		return revenueToCompany;
	}
	public void setRevenueToCompany(Double revenueToCompany) {
		this.revenueToCompany = revenueToCompany;
	}
	
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	
	@Override
	public String toString() {
		return "ClientMandateVehicleDeployment [id=" + id + ", mandateDetailId=" + mandateDetailId + ", vehicleNo="
				+ vehicleNo + ", reportingTime=" + reportingTime + ", isActive=" + isActive + ", modifieddate="
				+ modifieddate + ", modifiedby=" + modifiedby + ", deploymentRemark=" + deploymentRemark + "]";
	}
	
	
}
