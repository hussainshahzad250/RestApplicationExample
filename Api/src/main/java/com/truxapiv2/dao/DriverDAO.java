package com.truxapiv2.dao;

import com.truxapiv2.model.Driver;

public interface DriverDAO {

	public Driver getDriverByPhone(String phone);
	public Driver getDriverById(int id);
}
