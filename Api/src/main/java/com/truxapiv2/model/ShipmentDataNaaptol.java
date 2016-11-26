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
 * ShipmentDataNaaptol
 */
@Entity
@Table(name="naaptol_shipment_data")
public class ShipmentDataNaaptol {	

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "awb_number")
	private String awbNumber;
	
	@Column(name = "order_no")
	private String orderNumber;
	
	@Column(name = "consignee_name")
	private String consigneeName;
	
	@Column(name = "consignee_contact")
	private String consigneeContact;
	
	@Column(name = "awb_date")
	private String awbDate;
	
	@Column(name = "awb_amount")
	private String awbAmount;
	
	@Column(name = "ndr")
	private String ndr;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDateTime = new Date() ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAwbNumber() {
		return awbNumber;
	}

	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeContact() {
		return consigneeContact;
	}

	public void setConsigneeContact(String consigneeContact) {
		this.consigneeContact = consigneeContact;
	}

	public String getAwbDate() {
		return awbDate;
	}

	public void setAwbDate(String awbDate) {
		this.awbDate = awbDate;
	}

	public String getAwbAmount() {
		return awbAmount;
	}

	public void setAwbAmount(String awbAmount) {
		this.awbAmount = awbAmount;
	}

	public String getNdr() {
		return ndr;
	}

	public void setNdr(String ndr) {
		this.ndr = ndr;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

}
