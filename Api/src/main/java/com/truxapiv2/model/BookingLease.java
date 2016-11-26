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
import javax.persistence.Transient;

@Entity
@Table(name = "booking_lease")
public class BookingLease {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookingLeaseId")
	private Integer bookingLeaseId;

	@Column(name = "driverId")
	private Integer driverId;

	@Column(name = "vehicleId")
	private Integer vehicleId;

	@Column(name = "companyId")
	private Integer companyId;

	@Column(name = "journeyStartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date journeyStartDate;

	@Column(name = "journeyEndDate", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date journeyEndDate;

	@Column(name = "fromJrLat")
	private Double fromJrLat;

	@Column(name = "fromJrLong")
	private Double fromJrLong;

	@Column(name = "fromJrLocation")
	private String fromJrLocation;

	@Column(name = "toJrLat")
	private Double toJrLat;

	@Column(name = "toJrLong")
	private Double toJrLong;

	@Column(name = "toJrLocation")
	private String toJrLocation;

	@Column(name = "totalDistance")
	private Double totalDistance;

	@Column(name = "totalDuration")
	private Long totalDuration;

	@Column(name = "numberOfDrop")
	private Integer bookingLsStatus;

	@Column(name = "createdDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime;
	
	@Column(name = "createdby")
	private Integer createdBy;

	@Column(name = "updatedDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDateTime;
	
	@Column(name = "modifiedBy")
	private Integer modifiedBy;

	@Column(name = "driverMobile")
	private String driverMobile;
	
	@Column(name = "deviceBookingLeasesId")
	private String deviceBookingLeasesId;
	
	@Column(name = "clientOrderNumber")
	private String clientOrderNumber;
	
	@Column(name = "clientOrderDocUrl")
	private String clientOrderDocUrl;
	
	@Column(name = "clientOrderDocUrl2")
	private String clientOrderDocUrl2;
	@Column(name = "clientOrderDocUrl3")
	private String clientOrderDocUrl3;
	@Column(name = "clientOrderDocUrl4")
	private String clientOrderDocUrl4;
	@Column(name = "client_request_deployment_id")
	private Integer clientRequestId;

	@Transient
	private String errorCode;

	@Transient
	private String errorMessage;

	public BookingLease() {
		super();
	}

	public BookingLease(Integer driverId, Integer vehicleId, Integer companyId,
			Date journeyStartDate, Date journeyEndDate, Double fromJrLat,
			Double fromJrLong, String fromJrLocation, Double toJrLat,
			Double toJrLong, String toJrLocation, Double totalDistance,
			Long totalDuration, Integer bookingLsStatus) {
		super();
		this.driverId = driverId;
		this.vehicleId = vehicleId;
		this.companyId = companyId;
		this.journeyStartDate = journeyStartDate;
		this.journeyEndDate = journeyEndDate;
		this.fromJrLat = fromJrLat;
		this.fromJrLong = fromJrLong;
		this.fromJrLocation = fromJrLocation;
		this.toJrLat = toJrLat;
		this.toJrLong = toJrLong;
		this.toJrLocation = toJrLocation;
		this.totalDistance = totalDistance;
		this.totalDuration = totalDuration;
		this.bookingLsStatus = bookingLsStatus;
	}

	public Integer getBookingLeaseId() {
		return bookingLeaseId;
	}

	public void setBookingLeaseId(Integer bookingLeaseId) {
		this.bookingLeaseId = bookingLeaseId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getJourneyStartDate() {
		return journeyStartDate;
	}

	public void setJourneyStartDate(Date journeyStartDate) {
		this.journeyStartDate = journeyStartDate;
	}

	public Double getFromJrLat() {
		return fromJrLat;
	}

	public void setFromJrLat(Double fromJrLat) {
		this.fromJrLat = fromJrLat;
	}

	public Double getFromJrLong() {
		return fromJrLong;
	}

	public void setFromJrLong(Double fromJrLong) {
		this.fromJrLong = fromJrLong;
	}

	public String getFromJrLocation() {
		return fromJrLocation;
	}

	public void setFromJrLocation(String fromJrLocation) {
		this.fromJrLocation = fromJrLocation;
	}

	public Double getToJrLat() {
		return toJrLat;
	}

	public void setToJrLat(Double toJrLat) {
		this.toJrLat = toJrLat;
	}

	public Double getToJrLong() {
		return toJrLong;
	}

	public void setToJrLong(Double toJrLong) {
		this.toJrLong = toJrLong;
	}

	public String getToJrLocation() {
		return toJrLocation;
	}

	public void setToJrLocation(String toJrLocation) {
		this.toJrLocation = toJrLocation;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Integer getBookingLsStatus() {
		return bookingLsStatus;
	}

	public void setBookingLsStatus(Integer bookingLsStatus) {
		this.bookingLsStatus = bookingLsStatus;
	}

	public Date getJourneyEndDate() {
		return journeyEndDate;
	}

	public void setJourneyEndDate(Date journeyEndDate) {
		this.journeyEndDate = journeyEndDate;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public BookingLease(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDeviceBookingLeasesId() {
		return deviceBookingLeasesId;
	}

	public void setDeviceBookingLeasesId(String deviceBookingLeasesId) {
		this.deviceBookingLeasesId = deviceBookingLeasesId;
	}

	public String getClientOrderNumber() {
		return clientOrderNumber;
	}

	public void setClientOrderNumber(String clientOrderNumber) {
		this.clientOrderNumber = clientOrderNumber;
	}

	public String getClientOrderDocUrl() {
		return clientOrderDocUrl;
	}

	public void setClientOrderDocUrl(String clientOrderDocUrl) {
		this.clientOrderDocUrl = clientOrderDocUrl;
	}

	public String getClientOrderDocUrl2() {
		return clientOrderDocUrl2;
	}

	public void setClientOrderDocUrl2(String clientOrderDocUrl2) {
		this.clientOrderDocUrl2 = clientOrderDocUrl2;
	}

	public String getClientOrderDocUrl3() {
		return clientOrderDocUrl3;
	}

	public void setClientOrderDocUrl3(String clientOrderDocUrl3) {
		this.clientOrderDocUrl3 = clientOrderDocUrl3;
	}

	public String getClientOrderDocUrl4() {
		return clientOrderDocUrl4;
	}

	public void setClientOrderDocUrl4(String clientOrderDocUrl4) {
		this.clientOrderDocUrl4 = clientOrderDocUrl4;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getClientRequestId() {
		return clientRequestId;
	}

	public void setClientRequestId(Integer clientRequestId) {
		this.clientRequestId = clientRequestId;
	}
	
}
