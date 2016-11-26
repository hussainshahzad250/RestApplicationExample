package com.truxapiv2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truxapiv2.dao.TransporterBankDetailsDAO;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.model.TransporterRegistration;
@Service
public class TransporterBankDetailsServiceImpl implements TransporterBankDetailsService{
	
	@Autowired
	private TransporterBankDetailsDAO transporterBankDetailsDAO;
	
	@Override
	@Transactional
	public TransporterBankDetails addBankDetails(TransporterBankDetails p) {
		return transporterBankDetailsDAO.addBankDetails(p);
	}

	@Override
	@Transactional
	public void updateBankDetails(TransporterBankDetails p) {
		transporterBankDetailsDAO.updateBankDetails(p);
		
	}

	@Override
	@Transactional
	public List<TransporterBankDetails> listDetails() {
		
		return transporterBankDetailsDAO.listDetails();
	}

	@Override
	@Transactional
	public TransporterBankDetails getBankDetailsById(Integer id) {
		return transporterBankDetailsDAO.getBankDetailsById(id);
	}

	@Override
	@Transactional
	public void removeBankDetails(int id) {
		transporterBankDetailsDAO.removeBankDetails(id);
		
	}

	@Override
	public TransporterBankDetails getBankDetailsById(TransporterBankDetails tr) {
		return transporterBankDetailsDAO.getBankDetailsById(tr);
	}

	@Override
	public ControllerDAOTracker getTransporterRegistrationId(Integer trsptrRegistrationId) {
		
		return transporterBankDetailsDAO.getTransporterRegistrationId(trsptrRegistrationId);
	}

	@Override
	public ControllerDAOTracker saveTransporterBankDetails(TransporterBankDetails tr) {
		return transporterBankDetailsDAO.saveTransporterBankDetails(tr);
	}

	@Override
	public ControllerDAOTracker verifyTransporterBankDetails(TransporterBankDetails tr) {
		
		return transporterBankDetailsDAO.verifyTransporterBankDetails(tr);
	}

	@Override
	public ControllerDAOTracker getTransporterbankDetailsByTransporterRegistrationId(Integer trsptrRegistrationId) {
		
		return transporterBankDetailsDAO.getTransporterRegistrationId(trsptrRegistrationId);
	}

	@Override
	public ControllerDAOTracker updateVerificationStatus(Integer id,String string) {
		return transporterBankDetailsDAO.updateVerificationStatus(id,string);
	}
}
