package com.truxapiv2.service;

import java.util.List;

import com.truxapiv2.model.ClientMandateVehicleDeployment;

public interface ClientMandateService {

	/*
	 * CREATE and UPDATE 
	 */
	public void saveClientMandate(ClientMandateVehicleDeployment clientmandate);

	/*
	 * READ
	 */
	public List<ClientMandateVehicleDeployment> listClientMandates();
	public ClientMandateVehicleDeployment getClientMandate(Long id);

	/*
	 * DELETE
	 */
	public void deleteClientMandate(Long id);

}
