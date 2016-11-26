package com.truxapiv2.service;

import com.truxapiv2.model.Driver;

public interface DriverService {

	public Driver getDriverByPhone(String phone);
	public Driver getDriverById(int id);
	
}
