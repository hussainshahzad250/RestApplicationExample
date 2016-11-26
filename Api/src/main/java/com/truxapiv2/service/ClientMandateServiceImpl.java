package com.truxapiv2.service;

import java.util.List;

import com.truxapiv2.dao.ClientMandateDao;
import com.truxapiv2.model.ClientMandateVehicleDeployment;
import com.truxapiv2.service.ClientMandateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientMandateServiceImpl implements ClientMandateService {

	@Autowired
	private ClientMandateDao clientmandateDao;

	@Transactional
	public void saveClientMandate(ClientMandateVehicleDeployment clientmandate) {
		clientmandateDao.saveClientMandate(clientmandate);
	}

	@Transactional(readOnly = true)
	public List<ClientMandateVehicleDeployment> listClientMandates() {
		return clientmandateDao.listClientMandates();
	}

	@Transactional(readOnly = true)
	public ClientMandateVehicleDeployment getClientMandate(Long id) {
		return clientmandateDao.getClientMandate(id);
	}

	@Transactional
	public void deleteClientMandate(Long id) {
		clientmandateDao.deleteClientMandate(id);

	}

}
