package com.truxapiv2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truxapiv2.dao.ClientMandateVehicleDeploymentDAO;
import com.truxapiv2.model.ClientMandateVehicleDeployment;
import com.truxapiv2.model.Person;

@Service
public class ClientMandateVehicleDeploymentServiceImpl implements ClientMandateVehicleDeploymentService {
	
	@Autowired
	private ClientMandateVehicleDeploymentDAO clientMandateVehicleDeploymentDAO;

	
	@Override
	@Transactional
	public boolean updateDeployment(ClientMandateVehicleDeployment cmvd) {
		return this.clientMandateVehicleDeploymentDAO.updateDeployment(cmvd);
	}
	@Override
	@Transactional
	public List<ClientMandateVehicleDeployment> getDeploymentListByMandateDetailId(int mandateId) {
		return this.clientMandateVehicleDeploymentDAO.getDeploymentListByMandateDetailId(mandateId);
	}
	@Override
	@Transactional
	public List<Object[]> getSubClientIdMappingByUserId(int userId) {
		return this.clientMandateVehicleDeploymentDAO.getSubClientIdMappingByUserId(userId);
	}
	@Override
	@Transactional
	public List<Object[]> getMandateBySubclient(int subclientId) {
		return this.clientMandateVehicleDeploymentDAO.getMandateBySubclient(subclientId);
	}
	@Override
	@Transactional
	public List<Object[]> getMandateDetailsByMandateId(int mandateId) {
		return this.clientMandateVehicleDeploymentDAO.getMandateDetailsByMandateId(mandateId);
	}
	@Override
	@Transactional
	public List<Object[]> getNonDeployedVehcleBySubclient(int subclientId, String vehicleType, String bodyType) {
		return this.clientMandateVehicleDeploymentDAO.getNonDeployedVehcleBySubclient(subclientId, vehicleType, bodyType);
	}
	@Override
	@Transactional
	public List<Object[]> getNonDeployedVehcleBySubclient2(int subclientId, String vehicleType, String bodyType) {
		return this.clientMandateVehicleDeploymentDAO.getNonDeployedVehcleBySubclient2(subclientId, vehicleType, bodyType);
	}
	@Override
	@Transactional
	public List<Object[]> getMandateStatByAgentId(int agentId) {
		return this.clientMandateVehicleDeploymentDAO.getMandateStatByAgentId(agentId);
	}
	@Override
	@Transactional
	public List<Object[]> getClientMandateRequests(int agentId, int level) {
		return this.clientMandateVehicleDeploymentDAO.getClientMandateRequests(agentId, level);
	}
	@Override
	@Transactional
	public boolean updateClientMandateRequests(int requestId, int userId, Integer nov) {
		return this.clientMandateVehicleDeploymentDAO.updateClientMandateRequests(requestId, userId, nov);
	}
	@Override
	@Transactional
	public List<ClientMandateVehicleDeployment> getDeploymentListByClientRequestId(int requestId) {
		return this.clientMandateVehicleDeploymentDAO.getDeploymentListByClientRequestId(requestId);
	}
	@Override
	@Transactional
	public Integer getRequestIdByVehicleId(int vehicleId) {
		// TODO Auto-generated method stub
		return this.clientMandateVehicleDeploymentDAO.getRequestIdByVehicleId(vehicleId);
	}
	@Override
	@Transactional
	public List<ClientMandateVehicleDeployment> getDeploymentListByMandateDetailIdAndRequestDate(int mandateId,
			String request_date) {
		// TODO Auto-generated method stub
		return this.clientMandateVehicleDeploymentDAO.getDeploymentListByMandateDetailIdAndRequestDate(mandateId, request_date);
	}
	@Override
	@Transactional
	public Boolean updateDeploymentVehicleId(int deploymentId) {
		// TODO Auto-generated method stub
		return this.clientMandateVehicleDeploymentDAO.updateDeploymentVehicleId(deploymentId);
	}
	

}
