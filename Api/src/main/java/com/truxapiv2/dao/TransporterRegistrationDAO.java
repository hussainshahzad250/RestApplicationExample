package com.truxapiv2.dao;

import com.truxapiv2.model.CommunicationEmail;
import com.truxapiv2.model.CommunicationEmailArchive;
import com.truxapiv2.model.CommunicationSMS;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.model.TransporterClientOrderMapping;
import com.truxapiv2.model.TransporterClientOrders;
import com.truxapiv2.model.TransporterFreightChart;
import com.truxapiv2.model.TransporterLoginHistory;
import com.truxapiv2.model.TransporterOrderFollowUp;
import com.truxapiv2.model.TransporterRegistration;
import com.truxapiv2.model.TransporterVehicleRegistration;

public interface TransporterRegistrationDAO {

	ControllerDAOTracker saveTransporterRegistration(TransporterRegistration dto);

	ControllerDAOTracker getTransporterByMobile(String mobileNumber);

	ControllerDAOTracker getTransporterDetails(String mobileNumber);

	ControllerDAOTracker updateTransporterRegistration(TransporterRegistration tr);

	ControllerDAOTracker getUserDetailsWithGcmId(TransporterRegistration tr);

	ControllerDAOTracker saveTransporterLoginHistory(TransporterLoginHistory tlh);

	ControllerDAOTracker getVehicleNumber(String vehicleNumber);

	ControllerDAOTracker saveTransporterVehicle(TransporterVehicleRegistration tvr);

	ControllerDAOTracker getTransporterVehicle(Integer trsptrRegistrationId);

	ControllerDAOTracker updateTransporterVehicle(TransporterVehicleRegistration tvr);

	ControllerDAOTracker getClientOrders(Integer cityId, String vehicleCategory);

	ControllerDAOTracker clientOrderConfirmation(TransporterClientOrderMapping tcom);

	ControllerDAOTracker orderFollowUp(TransporterOrderFollowUp tofu);

	ControllerDAOTracker getFollowUpOrders(Integer trsptrRegistrationId);

	ControllerDAOTracker transporterClientIsActiveUpdate(TransporterClientOrders tco);

	ControllerDAOTracker getClientOrdersHistory(Integer trsptrRegistrationId, String status, String vehicleCategory);

	ControllerDAOTracker changeTransporterStatus(TransporterClientOrderMapping tcom);

	ControllerDAOTracker changeTransporterStatus(TransporterOrderFollowUp tofu);

	ControllerDAOTracker getTransporterFreightChart(TransporterFreightChart tfc);

	ControllerDAOTracker saveTransporterFreightRate(TransporterFreightChart tr);

	ControllerDAOTracker getOrderCityList();
	
	ControllerDAOTracker saveSMSRecord(CommunicationSMS cSms);

	ControllerDAOTracker getClientOrdersWithTransporter(Integer trsptrRegistrationId, Integer cityId,
			String vehicleCategory);
	
	
	/*Added By shahzad Hussain*/
	public TransporterRegistration getById(int id);
	
	public Object getByPassword(Integer password);
	
	public TransporterRegistration updatePassword(TransporterRegistration tr);
	
	
	public TransporterRegistration updateImage(TransporterRegistration tr);

	TransporterBankDetails getBankDetailsById(Integer trsptrRegistrationId);

	TransporterBankDetails addBankDetails(TransporterBankDetails tr);

	ControllerDAOTracker saveEmailRecord(CommunicationEmail cSms);

	ControllerDAOTracker sendEmailRecord(CommunicationEmailArchive cSms);

	ControllerDAOTracker sendEmail(String emailId, String subject, String emailText);

	public ControllerDAOTracker findbyId(String id);
	
	ControllerDAOTracker saveEmail(CommunicationEmail dto);

	

	ControllerDAOTracker saveCommunicationEmailArchive(CommunicationEmailArchive tr);

	void sendMail();

	ControllerDAOTracker getCommunicationByEmail(String emailId);

	ControllerDAOTracker sendByEmail();
	
	
	
	

}
