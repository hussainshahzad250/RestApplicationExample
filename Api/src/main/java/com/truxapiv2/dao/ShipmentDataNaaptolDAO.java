package com.truxapiv2.dao;

import com.truxapiv2.model.ShipmentDataNaaptol;

public interface ShipmentDataNaaptolDAO {

	public ShipmentDataNaaptol getShipmentDataByAWBNumber(String awbNumber);

	public boolean updateShipmentDataByAWBNumberNDR(ShipmentDataNaaptol sdn);
}
