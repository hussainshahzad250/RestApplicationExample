package com.truxapiv2.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingJsonFromDevice {


private String bookingStartLatitude;
private String bookingStartLogitude;
private String bookingServerId;
private String bookingFlag;
private String bookingServerStatus;
private String bookingEndLatitude;
private String bookingEndLogitude;
//@JsonProperty("deliveryStartEndPointRecord")
private DropJsonFromDevice[] deliveryStartEndPointRecord;
private ClientBookingDoc[] bookingDocs;
private String bookingStartTime;
private String bookingid;
private String bookingEndTime;
private String bookingDriverId;
private String bookingDriverMobileNumber;
private String bookingCompanyId;
private String bookingVehicleId;
private String bookingTotalDistance;

public String getBookingEndLogitude() {
	return bookingEndLogitude;
}
public void setBookingEndLogitude(String bookingEndLogitude) {
	this.bookingEndLogitude = bookingEndLogitude;
}
public String getBookingStartLatitude() {
	return bookingStartLatitude;
}
public void setBookingStartLatitude(String bookingStartLatitude) {
	this.bookingStartLatitude = bookingStartLatitude;
}
public String getBookingServerId() {
	return bookingServerId;
}
public void setBookingServerId(String bookingServerId) {
	this.bookingServerId = bookingServerId;
}
public String getBookingFlag() {
	return bookingFlag;
}
public void setBookingFlag(String bookingFlag) {
	this.bookingFlag = bookingFlag;
}
public String getBookingServerStatus() {
	return bookingServerStatus;
}
public void setBookingServerStatus(String bookingServerStatus) {
	this.bookingServerStatus = bookingServerStatus;
}
public String getBookingEndLatitude() {
	return bookingEndLatitude;
}
public void setBookingEndLatitude(String bookingEndLatitude) {
	this.bookingEndLatitude = bookingEndLatitude;
}
public String getBookingStartTime() {
	return bookingStartTime;
}
public void setBookingStartTime(String bookingStartTime) {
	this.bookingStartTime = bookingStartTime;
}
public String getBookingid() {
	return bookingid;
}
public void setBookingid(String bookingid) {
	this.bookingid = bookingid;
}
public String getBookingEndTime() {
	return bookingEndTime;
}
public void setBookingEndTime(String bookingEndTime) {
	this.bookingEndTime = bookingEndTime;
}
public String getBookingDriverId() {
	return bookingDriverId;
}
public void setBookingDriverId(String bookingDriverId) {
	this.bookingDriverId = bookingDriverId;
}
public String getBookingStartLogitude() {
	return bookingStartLogitude;
}
public void setBookingStartLogitude(String bookingStartLogitude) {
	this.bookingStartLogitude = bookingStartLogitude;
}
public String getBookingDriverMobileNumber() {
	return bookingDriverMobileNumber;
}
public void setBookingDriverMobileNumber(String bookingDriverMobileNumber) {
	this.bookingDriverMobileNumber = bookingDriverMobileNumber;
}

public DropJsonFromDevice[] getDeliveryStartEndPointRecord() {
	return deliveryStartEndPointRecord;
}
public void setDeliveryStartEndPointRecord(DropJsonFromDevice[] deliveryStartEndPointRecord) {
	this.deliveryStartEndPointRecord = deliveryStartEndPointRecord;
}

public ClientBookingDoc[] getBookingDocs() {
	return bookingDocs;
}
public void setBookingDocs(ClientBookingDoc[] bookingDocs) {
	this.bookingDocs = bookingDocs;
}
public String getBookingCompanyId() {
	return bookingCompanyId;
}
public void setBookingCompanyId(String bookingCompanyId) {
	this.bookingCompanyId = bookingCompanyId;
}
public String getBookingVehicleId() {
	return bookingVehicleId;
}
public void setBookingVehicleId(String bookingVehicleId) {
	this.bookingVehicleId = bookingVehicleId;
}
public String getBookingTotalDistance() {
	return bookingTotalDistance;
}
public void setBookingTotalDistance(String bookingTotalDistance) {
	this.bookingTotalDistance = bookingTotalDistance;
}


}
