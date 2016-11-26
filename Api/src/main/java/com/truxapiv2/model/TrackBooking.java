package com.truxapiv2.model;

import javax.persistence.Transient;

public class TrackBooking {

	@Transient
	private String username="trux";
	
	@Transient
	private String password="t12345";
	
	@Transient
	private int deviceid;
	
	@Transient
	private int Is_Punch_in=1;
	
	@Transient
	private int tripid;
	
	@Transient
	private int dropid = 1;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}

	public int getIs_Punch_in() {
		return Is_Punch_in;
	}

	public void setIs_Punch_in(int is_Punch_in) {
		Is_Punch_in = is_Punch_in;
	}

	public int getTripid() {
		return tripid;
	}

	public void setTripid(int tripid) {
		this.tripid = tripid;
	}

	public int getDropid() {
		return dropid;
	}

	public void setDropid(int dropid) {
		this.dropid = dropid;
	}
	
	
}
