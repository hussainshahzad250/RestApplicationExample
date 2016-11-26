package com.truxapiv2.dao;

import java.util.List;

import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.TransporterBankDetails;

public interface TransporterBankDetailsDAO {
	
	public TransporterBankDetails addBankDetails(TransporterBankDetails p);
	public ControllerDAOTracker updateBankDetails(TransporterBankDetails p);
	public List<TransporterBankDetails> listDetails();
	public TransporterBankDetails getBankDetailsById(Integer id);
	public void removeBankDetails(int id);
	
	public TransporterBankDetails getByConfirmationAmount(TransporterBankDetails dt);
	public TransporterBankDetails getBankDetailsById(TransporterBankDetails tr);
	public ControllerDAOTracker getTransporterRegistrationId(Integer trsptrRegistrationId);
	
	public ControllerDAOTracker saveTransporterBankDetails(TransporterBankDetails tr);
	
	public ControllerDAOTracker verifyTransporterBankDetails(TransporterBankDetails tr);
	public ControllerDAOTracker updateVerificationStatus(Integer id, String string);

}
