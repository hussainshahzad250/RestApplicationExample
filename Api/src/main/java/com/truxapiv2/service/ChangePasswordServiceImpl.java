package com.truxapiv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truxapiv2.dao.TransporterRegistrationDAO;
import com.truxapiv2.model.TransporterRegistration;
@Service
public class ChangePasswordServiceImpl implements ChangePasswordService{

	@Autowired
	private TransporterRegistrationDAO transporterRegistrationDAO;

	@Override
	public TransporterRegistration getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
