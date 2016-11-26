package com.truxapiv2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="driver_registration")
public class Driver {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="hubId")
	private Integer hubId;
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="driverStatus")
	private String driverStatus;
	
	@Column(name="driverName")
	private String driverName;
	
	@Column(name="vehicleOwnerName")
	private String vehicleOwnerName;
	
	@Column(name="drivingExperience")
	private String drivingExperience;
	
	@Column(name="localAddress")
	private String localAddress;
	
	@Column(name="permanentAddress")
	private String permanentAddress;
	
	@Column(name="driverContactNumber")
	private String driverContactNumber;
	
	@Column(name="standDetails")
	private Integer standDetails;
	
	@Column(name="imageOfDL")
	private String imageOfDL ;

	@Column(name="imageOfpanCard")
	private String imageOfpanCard;
	
	@Column(name="imageOfpoliceVerificationReport")
	private String imageOfpoliceVerificationReport;	
	
	@Column(name="driverPhoto")
	private String driverPhoto;	
	
	
	@Column(name="agentId")
	private Integer agentId;
	
	@Column(name="assosiated_by")
	private String assosiatedBy;
	
	@Column(name="cluster_id")
	private Integer cluster;
	@Column(name="sub_org_client")
	private Integer subOrgClient;
	@Column(name="deployment_remark")
	private String deploymentRemark ;
	
	@Transient
	private String errorCode;
	
	@Transient
	private String errorMesaage;
	
	@Transient
	private Boolean isValidDriver;

	
	public Driver() {
	}
	
	 

	public Driver(
			Integer hubId, 
			String city, 
			String state,
			String country, 
			String gender,
			String driverName,
			String drivingExperience,
			String localAddress,
			String permanentAddress, 
			String driverContactNumber,
			Integer standDetails, 
			String imageOfDL,
			String imageOfpanCard,
			String imageOfpoliceVerificationReport, 
			String driverPhoto,
			String assosiatedBy) {
		super();
		this.hubId = hubId;
		this.city = city;
		this.state = state;
		this.country = country;
		this.gender = gender;
		this.driverName = driverName;
		this.drivingExperience = drivingExperience;
		this.localAddress = localAddress;
		this.permanentAddress = permanentAddress;
		this.driverContactNumber = driverContactNumber;
		this.standDetails = standDetails;
		this.imageOfDL = imageOfDL;
		this.imageOfpanCard = imageOfpanCard;
		this.imageOfpoliceVerificationReport = imageOfpoliceVerificationReport;
		this.driverPhoto = driverPhoto;
		this.assosiatedBy = assosiatedBy;
	}



	public Driver( String driverName,
			String vehicleOwnerName, String drivingExperience,
			String localAddress, String permanentAddress,
			String driverContactNumber, Integer standDetails, String imageOfDL,
			String imageOfpanCard, String imageOfpoliceVerificationReport,
			Integer agentId) {
		super(); 
		this.driverName = driverName;
		this.vehicleOwnerName = vehicleOwnerName;
		this.drivingExperience = drivingExperience;
		this.localAddress = localAddress;
		this.permanentAddress = permanentAddress;
		this.driverContactNumber = driverContactNumber;
		this.standDetails = standDetails;
		this.imageOfDL = imageOfDL;
		this.imageOfpanCard = imageOfpanCard;
		this.imageOfpoliceVerificationReport = imageOfpoliceVerificationReport;
		this.agentId = agentId;
	}



	public Driver(String firstName, String middleName,
			String lastName, String address, String city, String state,
			String country, String gender, String phoneNumber, String driverStatus) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.driverStatus = driverStatus;
	}

	
	public Driver(String errorCode, String errorMesaage) {
		super();
		this.errorCode = errorCode;
		this.errorMesaage = errorMesaage;
	}

	
	public Boolean getIsValidDriver() {
		return isValidDriver;
	}

	public void setIsValidDriver(Boolean isValidDriver) {
		this.isValidDriver = isValidDriver;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMesaage() {
		return errorMesaage;
	}

	public void setErrorMesaage(String errorMesaage) {
		this.errorMesaage = errorMesaage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDriverStatus() {
		return driverStatus;
	}

	public void setDriverStatus(String driverStatus) {
		this.driverStatus = driverStatus;
	}

	public String getImageOfDL() {
		return imageOfDL;
	}

	public void setImageOfDL(String imageOfDL) {
		this.imageOfDL = imageOfDL;
	}

	public String getImageOfpanCard() {
		return imageOfpanCard;
	}

	public void setImageOfpanCard(String imageOfpanCard) {
		this.imageOfpanCard = imageOfpanCard;
	}

	public String getImageOfpoliceVerificationReport() {
		return imageOfpoliceVerificationReport;
	}

	public void setImageOfpoliceVerificationReport(
			String imageOfpoliceVerificationReport) {
		this.imageOfpoliceVerificationReport = imageOfpoliceVerificationReport;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}



	public String getDriverName() {
		return driverName;
	}



	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}



	public String getVehicleOwnerName() {
		return vehicleOwnerName;
	}



	public void setVehicleOwnerName(String vehicleOwnerName) {
		this.vehicleOwnerName = vehicleOwnerName;
	}



	public String getDrivingExperience() {
		return drivingExperience;
	}



	public void setDrivingExperience(String drivingExperience) {
		this.drivingExperience = drivingExperience;
	}



	public String getLocalAddress() {
		return localAddress;
	}



	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}



	public String getPermanentAddress() {
		return permanentAddress;
	}



	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}



	public String getDriverContactNumber() {
		return driverContactNumber;
	}



	public void setDriverContactNumber(String driverContactNumber) {
		this.driverContactNumber = driverContactNumber;
	}



	public Integer getStandDetails() {
		return standDetails;
	}



	public void setStandDetails(Integer standDetails) {
		this.standDetails = standDetails;
	}



	public String getAssosiatedBy() {
		return assosiatedBy;
	}



	public void setAssosiatedBy(String assosiatedBy) {
		this.assosiatedBy = assosiatedBy;
	}



	public Integer getHubId() {
		return hubId;
	}



	public void setHubId(Integer hubId) {
		this.hubId = hubId;
	}



	public String getDriverPhoto() {
		return driverPhoto;
	}


	public void setDriverPhoto(String driverPhoto) {
		this.driverPhoto = driverPhoto;
	}
 
	public Integer getCluster() {
		return cluster;
	}
 
	public void setCluster(Integer cluster) {
		this.cluster = cluster;
	}



	public Integer getSubOrgClient() {
		return subOrgClient;
	}



	public void setSubOrgClient(Integer subOrgClient) {
		this.subOrgClient = subOrgClient;
	}



	public String getDeploymentRemark() {
		return deploymentRemark;
	}



	public void setDeploymentRemark(String deploymentRemark) {
		this.deploymentRemark = deploymentRemark;
	}
	
	
}
