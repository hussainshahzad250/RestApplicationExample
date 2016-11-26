package com.truxapiv2.service;

import java.util.List;

import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.model.TransporterRegistration;

public interface TransporterBankDetailsService {
	
	public TransporterBankDetails addBankDetails(TransporterBankDetails p);
	public void updateBankDetails(TransporterBankDetails p);
	public List<TransporterBankDetails> listDetails();
	public TransporterBankDetails getBankDetailsById(Integer trsptrRegistrationId);
	public void removeBankDetails(int id);
	public TransporterBankDetails getBankDetailsById(TransporterBankDetails tr);
	
	public ControllerDAOTracker getTransporterRegistrationId(Integer trsptrRegistrationId);
	
	public ControllerDAOTracker saveTransporterBankDetails(TransporterBankDetails tr);//this method is used to saved the BANK DETAILS OF Trannsporter
	
	
	public ControllerDAOTracker verifyTransporterBankDetails(TransporterBankDetails tr);
	public ControllerDAOTracker getTransporterbankDetailsByTransporterRegistrationId(Integer trsptrRegistrationId);
	public ControllerDAOTracker updateVerificationStatus(Integer integer, String string);

}
