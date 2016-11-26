package com.truxapiv2.dao;

import java.util.List;
import com.truxapiv2.model.ClientMandateVehicleDeployment;

public interface ClientMandateDao {

	/*
	 * CREATE and UPDATE
	 */
	public void saveClientMandate(ClientMandateVehicleDeployment clientmandate); // create and update

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
