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

/** 
 * ClientBookingDoc
 */
@Entity
@Table(name="booking_lease_docs")
public class ClientBookingDoc {	

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(name = "booking_lease_id")
	private String orderId;
	
	@Column(name = "doc_number")
	private String clientOrderNumber;
	
	@Column(name = "doc_url")
	private String clientOrderDocUrl;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime = new Date() ;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	@Override
	public String toString() {
		return "ClientBookingDoc [id=" + id + ", orderId=" + orderId + ", clientOrderNumber=" + clientOrderNumber
				+ ", clientOrderDocUrl=" + clientOrderDocUrl + ", createdDateTime=" + createdDateTime + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
}
