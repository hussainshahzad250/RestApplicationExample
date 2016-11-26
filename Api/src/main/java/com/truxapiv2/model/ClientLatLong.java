package com.truxapiv2.model;

public class ClientLatLong {

	public ClientLatLong(double clientLat, double clientLong, double loginDistanceCircle) {
		super();
		this.clientLat = clientLat;
		this.clientLong = clientLong;
		this.loginDistanceCircle = loginDistanceCircle;
	}

	public ClientLatLong(double clientLat, double clientLong) {
		super();
		this.clientLat = clientLat;
		this.clientLong = clientLong;
	}

	private double clientLat;
	
	private double clientLong;
	
	private double loginDistanceCircle;
	
	public double getClientLat() {
		return clientLat;
	}

	public void setClientLat(double clientLat) {
		this.clientLat = clientLat;
	}

	public double getClientLong() {
		return clientLong;
	}

	public void setClientLong(double clientLong) {
		this.clientLong = clientLong;
	}

	public double getLoginDistanceCircle() {
		return loginDistanceCircle;
	}

	public void setLoginDistanceCircle(double loginDistanceCircle) {
		this.loginDistanceCircle = loginDistanceCircle;
	}
	
	
}
