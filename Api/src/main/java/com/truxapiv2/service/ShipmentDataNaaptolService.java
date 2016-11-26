package com.truxapiv2.service;

import org.springframework.stereotype.Service;

import com.truxapiv2.model.ResultObject;
import com.truxapiv2.model.ShipmentDataNaaptol;

@Service
public interface ShipmentDataNaaptolService {
	
	public ResultObject getShipmentDataByAWBNumber(String AWBNumber);
	public ResultObject updateShipmentDataByAWBNumberNDR(ShipmentDataNaaptol sdn);
}
