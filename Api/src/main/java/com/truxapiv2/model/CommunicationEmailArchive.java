package com.truxapiv2.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
@SuppressWarnings("serial")
@Entity
@Table(name="communication_email_archive")
public class CommunicationEmailArchive implements Serializable{
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "communication_email_id")
	private String communicationEmailId;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private CommunicationEmail communicationEmail;
	
	
	public CommunicationEmailArchive() {
	}
	
	
	
	/*@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "email_provider")
	private String emailProvider;
	
	@Column(name = "email_text")
	private String emailText;
	
	@Column(name = "request_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date requestDate;
	
	@Column(name = "request_process")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date requestProcess;*/
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CommunicationEmail getCommunicationEmail() {
		return communicationEmail;
	}

	public void setCommunicationEmail(CommunicationEmail communicationEmail) {
		this.communicationEmail = communicationEmail;
	}



	@Column(name = "sent_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm", timezone="IST")
	private Date sendAt;

	@Column(name = "server_response")
	private String serverResponse;
	
	@Column(name = "email_provider_response")
	private String emailProviderResponse;

	
	public String getCommunicationEmailId() {
		return communicationEmailId;
	}

	public void setCommunicationEmailId(String communicationEmailId) {
		this.communicationEmailId = communicationEmailId;
	}

	

	public Date getSendAt() {
		return sendAt;
	}

	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}

	public String getServerResponse() {
		return serverResponse;
	}

	public void setServerResponse(String serverResponse) {
		this.serverResponse = serverResponse;
	}

	public String getEmailProviderResponse() {
		return emailProviderResponse;
	}

	public void setEmailProviderResponse(String emailProviderResponse) {
		this.emailProviderResponse = emailProviderResponse;
	}
	
	
}
