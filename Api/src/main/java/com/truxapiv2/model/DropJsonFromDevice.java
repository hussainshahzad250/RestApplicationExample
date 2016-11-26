package com.truxapiv2.model;


public class DropJsonFromDevice {

private String dropEndLogitude;
private String dropBookingId;
private String dropEndLatitude;
private String dropid;
private String dropServerStatus;
private String dropEndTime;
private String dropStartLatitude;
private String dropStartTime;
private String dropStartLogitude;
private String dropLocation;
private String dropedBoxes;
private String dropNdrResponce;
private String dropBookingServerId;
private Double distanceBetweenStops;
private String clientOrderNo;
private String awbNumber;


public String getAwbNumber() {
	return awbNumber;
}
public void setAwbNumber(String awbNumber) {
	this.awbNumber = awbNumber;
}
public String getClientOrderNo() {
	return clientOrderNo;
}
public void setClientOrderNo(String clientOrderNo) {
	this.clientOrderNo = clientOrderNo;
}
public Double getDistanceBetweenStops() {
	return distanceBetweenStops;
}
public void setDistanceBetweenStops(Double distanceBetweenStops) {
	this.distanceBetweenStops = distanceBetweenStops;
}
public String getDropBookingServerId() {
	return dropBookingServerId;
}
public void setDropBookingServerId(String dropBookingServerId) {
	this.dropBookingServerId = dropBookingServerId;
}
public String getDropNdrResponce() {
	return dropNdrResponce;
}
public void setDropNdrResponce(String dropNdrResponce) {
	this.dropNdrResponce = dropNdrResponce;
}
public String getDropedBoxes() {
	return dropedBoxes;
}
public void setDropedBoxes(String dropedBoxes) {
	this.dropedBoxes = dropedBoxes;
}
public String getDropLocation() {
	return dropLocation;
}
public void setDropLocation(String dropLocation) {
	this.dropLocation = dropLocation;
}
public String getDropEndLogitude() {
	return dropEndLogitude;
}
public void setDropEndLogitude(String dropEndLogitude) {
	this.dropEndLogitude = dropEndLogitude;
}
public String getDropBookingId() {
	return dropBookingId;
}
public void setDropBookingId(String dropBookingId) {
	this.dropBookingId = dropBookingId;
}
public String getDropEndLatitude() {
	return dropEndLatitude;
}
public void setDropEndLatitude(String dropEndLatitude) {
	this.dropEndLatitude = dropEndLatitude;
}
public String getDropid() {
	return dropid;
}
public void setDropid(String dropid) {
	this.dropid = dropid;
}
public String getDropServerStatus() {
	return dropServerStatus;
}
public void setDropServerStatus(String dropServerStatus) {
	this.dropServerStatus = dropServerStatus;
}
public String getDropEndTime() {
	return dropEndTime;
}
public void setDropEndTime(String dropEndTime) {
	this.dropEndTime = dropEndTime;
}
public String getDropStartLatitude() {
	return dropStartLatitude;
}
public void setDropStartLatitude(String dropStartLatitude) {
	this.dropStartLatitude = dropStartLatitude;
}
public String getDropStartTime() {
	return dropStartTime;
}
public void setDropStartTime(String dropStartTime) {
	this.dropStartTime = dropStartTime;
}
public String getDropStartLogitude() {
	return dropStartLogitude;
}
public void setDropStartLogitude(String dropStartLogitude) {
	this.dropStartLogitude = dropStartLogitude;
}
@Override
public String toString() {
	return "DropJsonFromDevice [dropEndLogitude=" + dropEndLogitude + ", dropBookingId=" + dropBookingId
			+ ", dropEndLatitude=" + dropEndLatitude + ", dropid=" + dropid + ", dropServerStatus=" + dropServerStatus
			+ ", dropEndTime=" + dropEndTime + ", dropStartLatitude=" + dropStartLatitude + ", dropStartTime="
			+ dropStartTime + ", dropStartLogitude=" + dropStartLogitude + "]";
}


}