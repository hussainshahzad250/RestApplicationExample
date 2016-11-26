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
@Table(name = "communication_sms")
public class CommunicationSMS {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "sender_mask")
	private String senderMask;
	
	@Column(name = "sms_provider")
	private String smsProvider;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "sms_text")
	private String smsText;
	
	@Column(name = "request_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date requestDate;
	
	@Column(name = "request_process")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date requestProcess;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenderMask() {
		return senderMask;
	}

	public void setSenderMask(String senderMask) {
		this.senderMask = senderMask;
	}

	public String getSmsProvider() {
		return smsProvider;
	}

	public void setSmsProvider(String smsProvider) {
		this.smsProvider = smsProvider;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getRequestProcess() {
		return requestProcess;
	}

	public void setRequestProcess(Date requestProcess) {
		this.requestProcess = requestProcess;
	}

	
	
}
