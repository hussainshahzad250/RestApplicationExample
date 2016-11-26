package com.truxapiv2.service;

import com.truxapiv2.model.CassandraTrack;
import com.truxapiv2.model.ClientLatLong;
import com.truxapiv2.model.CurrentLocation;
import com.truxapiv2.model.DriverDeviceVehicleMapping;
import com.truxapiv2.model.DriverLoginJson;
import com.truxapiv2.model.ResultObject;

public interface DriverDeviceVehicleMappingService {

	public DriverDeviceVehicleMapping getDriverById(int id);
	public DriverDeviceVehicleMapping getDriverByPhone(String phone);
	public boolean updateLoginStatus(int flag, String phone);
	public boolean updateJourneyStatus(int flag, String phone);
	public boolean insertCassndraTrack(CassandraTrack cto);
	public boolean insertCurrentVehicleLocation(CurrentLocation clo);
	public DriverDeviceVehicleMapping getDriverByTrackingDeviceId(int trackingDeviceId);
	public ClientLatLong getClientLatLong(int clientId);
	public boolean checkGeoFencing(int clientId, int deviceId);
	public ClientLatLong getDeviceLatlong(int deviceId);
	boolean updateLoginStatusById(int flag, int id);
	boolean updateJourneyStatusId(int id, String phone);
	public DriverDeviceVehicleMapping getDriverByVehicleNo(String vehicleno);
	public ResultObject checkGeoFencing2(ClientLatLong clientSubLL, ClientLatLong driverDeviceLL);
	public ResultObject loginLVTDriver(DriverLoginJson dlj);
	public boolean updateTrackDeviceBookingId(String deviceBookingId, Integer bookingId);
	public boolean insertCassndraTrack2(CassandraTrack cto);
	public boolean updateJourneyStatusByVehicleAndDriver(int flag, int vehicleId, int driverId);
}
