package com.truxapiv2.model;

public class ControllerDAOTracker {
	private boolean success;
	private String errorCode;
	private String errorMessage;
	private Object data;
	private TransporterRegistration transporterRegistration;
	private TransporterVehicleRegistration transporterVehicleRegistration;
	private TransporterClientOrderMapping transporterClientOrderMapping;
	private TransporterOrderFollowUp transporterOrderFollowUp;
	private TransporterClientOrders transporterClientOrders;
	private TransporterFreightChart transporterFreightChart;
	private SubscriptionEmail subscriptionEmail;
	private ContactUs contactUs;
	private Object csc;
	/* Added By shahzad Hussain */
	private TransporterBankDetails transporterBankDetails;
	private CommunicationEmail communicationEmail;
	private CommunicationEmailArchive communicationEmailArchive;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public TransporterRegistration getTransporterRegistration() {
		return transporterRegistration;
	}

	public void setTransporterRegistration(TransporterRegistration transporterRegistration) {
		this.transporterRegistration = transporterRegistration;
	}

	public Object getCsc() {
		return csc;
	}

	public void setCsc(Object csc) {
		this.csc = csc;
	}

	public TransporterVehicleRegistration getTransporterVehicleRegistration() {
		return transporterVehicleRegistration;
	}

	public void setTransporterVehicleRegistration(TransporterVehicleRegistration transporterVehicleRegistration) {
		this.transporterVehicleRegistration = transporterVehicleRegistration;
	}

	public TransporterClientOrderMapping getTransporterClientOrderMapping() {
		return transporterClientOrderMapping;
	}

	public void setTransporterClientOrderMapping(TransporterClientOrderMapping transporterClientOrderMapping) {
		this.transporterClientOrderMapping = transporterClientOrderMapping;
	}

	public TransporterOrderFollowUp getTransporterOrderFollowUp() {
		return transporterOrderFollowUp;
	}

	public void setTransporterOrderFollowUp(TransporterOrderFollowUp transporterOrderFollowUp) {
		this.transporterOrderFollowUp = transporterOrderFollowUp;
	}

	public TransporterClientOrders getTransporterClientOrders() {
		return transporterClientOrders;
	}

	public void setTransporterClientOrders(TransporterClientOrders transporterClientOrders) {
		this.transporterClientOrders = transporterClientOrders;
	}

	public TransporterFreightChart getTransporterFreightChart() {
		return transporterFreightChart;
	}

	public void setTransporterFreightChart(TransporterFreightChart transporterFreightChart) {
		this.transporterFreightChart = transporterFreightChart;
	}

	public SubscriptionEmail getSubscriptionEmail() {
		return subscriptionEmail;
	}

	public void setSubscriptionEmail(SubscriptionEmail subscriptionEmail) {
		this.subscriptionEmail = subscriptionEmail;
	}

	public ContactUs getContactUs() {
		return contactUs;
	}

	public void setContactUs(ContactUs contactUs) {
		this.contactUs = contactUs;
	}

	public void setTransporterBankDetails(TransporterBankDetails transporterBankDetails) {
		this.transporterBankDetails = transporterBankDetails;
	}

	public TransporterBankDetails getTransporterBankDetails() {
		return transporterBankDetails;
	}

	public void setEmail(CommunicationEmail communicationEmail) {
		this.communicationEmail = communicationEmail;

	}

	public CommunicationEmail getCommunicationEmail() {
		return communicationEmail;
	}

	public void setCommunicationEmailArchive(CommunicationEmailArchive communicationEmailArchive) {
		this.communicationEmailArchive = communicationEmailArchive;
	}

	public CommunicationEmailArchive getCommunicationEmailArchive() {
		return communicationEmailArchive;
	}
}
