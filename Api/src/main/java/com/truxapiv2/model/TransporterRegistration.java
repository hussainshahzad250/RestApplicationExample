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

import com.mysql.jdbc.Blob;

@Entity
@Table(name = "trsptr_registration")
public class TransporterRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "transporter_company_name")
	private String transporterCompanyName;

	@Column(name = "contact_person_name")
	private String contactPersonName;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "password")
	private Integer password;

	@Column(name = "email")
	private String email;

	@Column(name = "city")
	private Integer city;

	@Column(name = "state")
	private Integer state;

	@Column(name = "pincode")
	private Integer pincode;

	@Column(name = "vehicle_category")
	private String vehicleCategory;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	// @JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm",
	// timezone="IST")
	private Date createdOn;

	@Column(name = "modified_by")
	private Integer modifiedBy;

	@Column(name = "modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	// @JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm",
	// timezone="IST")
	private Date modifiedOn;

	@Column(name = "is_active")
	private Integer isActive;

	@Column(name = "gcm_id")
	private String gcmId;

	@Transient
	private Object csc;

	/* Added By shahzad profile_image,pan_number */
	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "pan_number")
	private String panNumber;

	@Transient
	private String errorCode = "101";

	@Transient
	private String errorMesaage = "Something went wrong!!";

	@Transient
	private Object data;

	/* End by shahzad */

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Object getCsc() {
		return csc;
	}

	public void setCsc(Object csc) {
		this.csc = csc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTransporterCompanyName() {
		return transporterCompanyName;
	}

	public void setTransporterCompanyName(String transporterCompanyName) {
		this.transporterCompanyName = transporterCompanyName;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public String getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(String vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
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

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

}
