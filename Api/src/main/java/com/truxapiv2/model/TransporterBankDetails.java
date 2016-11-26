package com.truxapiv2.model;
import java.io.Serializable;
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

@SuppressWarnings("serial")
@Entity
@Table(name = "trsptr_bank_detail")
public class TransporterBankDetails implements Serializable{

	/**
	 * 
	 */
//	private static final long serialVersionUID = 3751273990934348474L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "trsptr_registration_id")
	private Integer trsptrRegistrationId;
	
	@Column(name = "account_holder_name")
	private String accountHolderName;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "ifsc_code")
	private String ifscCode;
	
	@Column(name = "account_number")
	private String accountNumber;
	
	@Column(name = "varification_amount")
	private Double varificationAmount;
	
	@Column(name = "account_status")
	private String accountStatus;
	
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
	
	
	
	@Transient
	private String errorCode="100";
	
	@Transient
	private String errorMesaage="Bank Details Saved Successfully";
	
	@Transient
	private Object data;

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

	public Double getVarificationAmount() {
		return varificationAmount;
	}

	public void setVarificationAmount(Double varificationAmount) {
		this.varificationAmount = varificationAmount;
	}

	
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

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String string) {
		this.accountStatus = string;
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
