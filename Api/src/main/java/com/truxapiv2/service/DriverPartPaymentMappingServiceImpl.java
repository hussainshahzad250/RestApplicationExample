package com.truxapiv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truxapiv2.dao.DriverPartPaymentMappingDAO;
import com.truxapiv2.model.DriverPartPayment;

@Service
public class DriverPartPaymentMappingServiceImpl implements DriverPartPaymentMappingService{
	
	@Autowired
	private DriverPartPaymentMappingDAO driverPartPaymentMappingDAO;
	
	public void setDriverPartPaymentMappingDAO(DriverPartPaymentMappingDAO driverPartPaymentMappingDAO) {
		this.driverPartPaymentMappingDAO = driverPartPaymentMappingDAO;
	}

	@Transactional
	public boolean insertDriverPartPayment(DriverPartPayment dpp) {
		return this.driverPartPaymentMappingDAO.insertDriverPartPayment(dpp);
	}

}
