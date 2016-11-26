package com.truxapiv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truxapiv2.dao.ShipmentDataNaaptolDAO;
import com.truxapiv2.model.ResultObject;
import com.truxapiv2.model.ShipmentDataNaaptol;

@Service
public class ShipmentDataNaaptolServiceImpl implements ShipmentDataNaaptolService {
	
	@Autowired
	private ShipmentDataNaaptolDAO shipmentDataNaaptolDAO;


	@Override
	@Transactional
	public ResultObject getShipmentDataByAWBNumber(String awbNumber) {
		ResultObject resultObject = new ResultObject();
		if(awbNumber==null){
			resultObject.setResultStatus(false);
			resultObject.setResultMesaage("AWB Number IS NOT Valid OR No Input.");
			return resultObject;
		}
		ShipmentDataNaaptol sdn = shipmentDataNaaptolDAO.getShipmentDataByAWBNumber(awbNumber);
		if(sdn!=null){
			resultObject.setResultStatus(true);
			resultObject.setResultData(sdn);
		} else{
			resultObject.setResultStatus(false);
			resultObject.setResultMesaage("No Data Found!!");
		}
		return resultObject;
	}

	@Override
	@Transactional
	public ResultObject updateShipmentDataByAWBNumberNDR(ShipmentDataNaaptol sdn) {
		ResultObject resultObject = new ResultObject();
		boolean isUpdated = shipmentDataNaaptolDAO.updateShipmentDataByAWBNumberNDR(sdn);
		if(isUpdated==true){
			resultObject.setResultStatus(true);
		} else{
			resultObject.setResultStatus(false);
		}
		return resultObject;
	}

}
