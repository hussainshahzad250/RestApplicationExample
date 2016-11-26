package com.truxapiv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truxapiv2.dao.TransporterRegistrationDAO;
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

@Service
public class TransporterRegistrationService {
	@Autowired
	private TransporterRegistrationDAO transporterRegistrationDAO;
	
	

	public ControllerDAOTracker saveTransporterRegistration(TransporterRegistration tr) {

		return transporterRegistrationDAO.saveTransporterRegistration(tr);
	}

	public ControllerDAOTracker getTransporterByMobile(String mobileNumber) {
		return transporterRegistrationDAO.getTransporterByMobile(mobileNumber);
	}
	
	public ControllerDAOTracker getTransporterDetails(String mobileNumber) {
		return transporterRegistrationDAO.getTransporterDetails(mobileNumber);
	}

	public ControllerDAOTracker updateTransporterRegistration(TransporterRegistration tr) {
		return transporterRegistrationDAO.updateTransporterRegistration(tr);
	}

	public ControllerDAOTracker getUserDetailsWithGcmId(TransporterRegistration tr) {
		return transporterRegistrationDAO.getUserDetailsWithGcmId(tr);
	}

	public ControllerDAOTracker saveTransporterLoginHistory(TransporterLoginHistory tlh) {
		return transporterRegistrationDAO.saveTransporterLoginHistory(tlh);
	}

	public ControllerDAOTracker getVehicleNumber(String vehicleNumber) {
		return transporterRegistrationDAO.getVehicleNumber(vehicleNumber);
	}

	public ControllerDAOTracker saveTransporterVehicle(TransporterVehicleRegistration tvr) {
		return transporterRegistrationDAO.saveTransporterVehicle(tvr);
	}

	public ControllerDAOTracker getTransporterVehicle(Integer trsptrRegistrationId) {
		return transporterRegistrationDAO.getTransporterVehicle(trsptrRegistrationId);
	}

	public ControllerDAOTracker updateTransporterVehicle(TransporterVehicleRegistration tvr) {
		return transporterRegistrationDAO.updateTransporterVehicle(tvr);
	}

	public ControllerDAOTracker getClientOrders(Integer cityId, String vehicleCategory) {
		return transporterRegistrationDAO.getClientOrders(cityId,vehicleCategory);
	}

	public ControllerDAOTracker clientOrderConfirmation(TransporterClientOrderMapping tcom) {
		return transporterRegistrationDAO.clientOrderConfirmation(tcom);
	}

	public ControllerDAOTracker orderFollowUp(TransporterOrderFollowUp tofu) {
		return transporterRegistrationDAO.orderFollowUp(tofu);
	}

	public ControllerDAOTracker getFollowUpOrders(Integer trsptrRegistrationId) {
		return transporterRegistrationDAO.getFollowUpOrders(trsptrRegistrationId);
	}

	public ControllerDAOTracker transporterClientIsActiveUpdate(TransporterClientOrders tco) {
		return transporterRegistrationDAO.transporterClientIsActiveUpdate(tco);
	}

	public ControllerDAOTracker getClientOrdersHistory(Integer trsptrRegistrationId, String status,
			String vehicleCategory) {
		return transporterRegistrationDAO.getClientOrdersHistory(trsptrRegistrationId, status, vehicleCategory);
	}

	public ControllerDAOTracker changeTransporterStatus(TransporterClientOrderMapping tcom) {
		return transporterRegistrationDAO.changeTransporterStatus(tcom);
	}

	public ControllerDAOTracker transporterFollowUpActiveUpdate(TransporterOrderFollowUp tofu) {
		return transporterRegistrationDAO.changeTransporterStatus(tofu);
	}

	public ControllerDAOTracker getTransporterFreightChart(TransporterFreightChart tfc) {
		return transporterRegistrationDAO.getTransporterFreightChart(tfc);
	}

	public ControllerDAOTracker saveTransporterFreightRate(TransporterFreightChart tr) {
		return transporterRegistrationDAO.saveTransporterFreightRate(tr);
	}

	public ControllerDAOTracker getOrderCityList() {
		return transporterRegistrationDAO.getOrderCityList();
	}
	
	public ControllerDAOTracker saveSMSRecord(CommunicationSMS cSms) {
		return transporterRegistrationDAO.saveSMSRecord(cSms);
	}

	public ControllerDAOTracker getClientOrdersWithTransporter(Integer trsptrRegistrationId, Integer cityId,
			String vehicleCategory) {
		return transporterRegistrationDAO.getClientOrdersWithTransporter(trsptrRegistrationId,cityId,vehicleCategory);
	}
	
	/*Added By Shahzad Hussain*/
	public TransporterRegistration getById(int id) {
		return transporterRegistrationDAO.getById(id);
	}
	public Object getByPassword(Integer password) {
		return transporterRegistrationDAO.getByPassword(password);
	}

	public TransporterRegistration updatePassword(TransporterRegistration registration2) {
		return transporterRegistrationDAO.updatePassword(registration2);		
	}

	public TransporterRegistration updateImage(TransporterRegistration registration2) {
		return transporterRegistrationDAO.updateImage(registration2);		
	}

	public TransporterBankDetails getBankDetailsById(Integer trsptrRegistrationId) {
		return transporterRegistrationDAO.getBankDetailsById(trsptrRegistrationId);
	}

	public TransporterBankDetails addBankDetails(TransporterBankDetails tr) {
		return transporterRegistrationDAO.addBankDetails(tr);
	}

	public ControllerDAOTracker saveEmailRecord(CommunicationEmail cSms) {
		
		return transporterRegistrationDAO.saveEmailRecord(cSms);
	}

	public ControllerDAOTracker sendEmailRecord(CommunicationEmailArchive cSms) {
		return transporterRegistrationDAO.sendEmailRecord(cSms);
		
	}

	public ControllerDAOTracker sendEmail(String emailId, String subject, String emailText) {
		
		return transporterRegistrationDAO.sendEmail(emailId,subject,emailText);
	}

	public ControllerDAOTracker findbyId(String id) {
		return transporterRegistrationDAO.findbyId(id);
	}	

	public ControllerDAOTracker getCommunicationByEmail(String emailId) {
		return transporterRegistrationDAO.getCommunicationByEmail(emailId);
	}

	public ControllerDAOTracker saveEmail(CommunicationEmail tr) {
		return transporterRegistrationDAO.saveEmail(tr);
	}
	public ControllerDAOTracker saveCommunicationEmailArchive(CommunicationEmailArchive tr) {
		
		return transporterRegistrationDAO.saveCommunicationEmailArchive(tr);
	}

	public ControllerDAOTracker sendByEmail() {
		
		return transporterRegistrationDAO.sendByEmail();
	}
	

	
	
	
}
