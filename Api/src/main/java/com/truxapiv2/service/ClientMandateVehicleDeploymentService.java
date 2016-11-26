package com.truxapiv2.service;


import java.util.List;

import com.truxapiv2.model.ClientMandateVehicleDeployment;

public interface ClientMandateVehicleDeploymentService {

	public boolean updateDeployment(ClientMandateVehicleDeployment cmvd);
	public List<ClientMandateVehicleDeployment> getDeploymentListByMandateDetailId(int mandateId);
	public List<Object[]> getSubClientIdMappingByUserId(int userId);
	public List<Object[]> getMandateBySubclient(int subclientId);
	public List<Object[]> getMandateDetailsByMandateId(int mandateId);
	public List<Object[]> getNonDeployedVehcleBySubclient(int subclientId, String vehicleType, String bodyType);
	public List<Object[]> getNonDeployedVehcleBySubclient2(int subclientId, String vehicleType, String bodyType);
	public List<Object[]> getMandateStatByAgentId(int agentId);
	public List<Object[]> getClientMandateRequests(int agentId, int level);
	public boolean updateClientMandateRequests(int requestId, int userId, Integer nov);
	public List<ClientMandateVehicleDeployment> getDeploymentListByClientRequestId(int requestId);
	public Integer getRequestIdByVehicleId(int vehicleId);
	public List<ClientMandateVehicleDeployment> getDeploymentListByMandateDetailIdAndRequestDate(int mandateId,
			String request_date);
	public Boolean updateDeploymentVehicleId(int deploymentId);
}
