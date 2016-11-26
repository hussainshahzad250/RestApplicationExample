package com.truxapiv2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truxapiv2.dao.DriverDAO;
import com.truxapiv2.model.Driver;

@Service
public class DriverServiceImpl implements DriverService {
	
	private DriverDAO DriverDAO;

	public void setDriverDAO(DriverDAO DriverDAO) {
		this.DriverDAO = DriverDAO;
	}


	@Override
	@Transactional
	public Driver getDriverByPhone(String phone) {
		return this.DriverDAO.getDriverByPhone(phone);
	}
	@Override
	@Transactional
	public Driver getDriverById(int id) {
		return this.DriverDAO.getDriverById(id);
	}


}
