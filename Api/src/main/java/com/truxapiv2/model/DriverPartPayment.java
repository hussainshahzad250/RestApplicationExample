package com.truxapiv2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="driver_part_payment")
public class DriverPartPayment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="driver_mobile")
	private String driver_mobile;
	
	@Column(name="amount_paid")
	private String amount_paid;
	
	@Column(name="vehicle_number")
	private String vehicle_number;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="date_paid_on")
	private String date_paid_on;
	
	@Column(name="paid_by")
	private String paid_by;
	
	public String getId() {
		return driver_mobile;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getDriverMobile() {
		return driver_mobile;
	}

	public void setDriverMobile(String driver_mobile) {
		this.driver_mobile = driver_mobile;
	}

	public String getAmountPaid() {
		return amount_paid;
	}

	public void setAmountPaid(String amount_paid) {
		this.amount_paid = amount_paid;
	}

	public String getVehicleNumber() {
		return vehicle_number;
	}

	public void setVehicleNumber(String vehicle_number) {
		this.vehicle_number = vehicle_number;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getDatePaidOn() {
		return date_paid_on;
	}

	public void setDatePaidOn(String date_paid_on) {
		this.date_paid_on = date_paid_on;
	}

	public String getPaidBy() {
		return paid_by;
	}

	public void setPaidBy(String paid_by) {
		this.paid_by = paid_by;
	}
	
	

}
