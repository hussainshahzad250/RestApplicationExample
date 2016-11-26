package com.truxapiv2.dao;

import com.truxapiv2.model.CassandraTrack;
import com.truxapiv2.model.ClientLatLong;
import com.truxapiv2.model.CurrentLocation;
import com.truxapiv2.model.DriverDeviceVehicleMapping;

public interface DriverDeviceVehicleMappingDAO {

	public DriverDeviceVehicleMapping getDriverById(int id);
	public DriverDeviceVehicleMapping getDriverByPhone(String phone);
	public boolean updateLoginStatus(int flag, String phone);
	public boolean updateJourneyStatus(int flag, String phone);
	public boolean insertCassndraTrack(CassandraTrack cto);
	public boolean insertCurrentVehicleLocation(CurrentLocation clo);
	public DriverDeviceVehicleMapping getDriverByTrackingDeviceId(int trackingDeviceId);
	public ClientLatLong getClientLatLong(int clientId);
	boolean updateLoginStatus(int flag, int id);
	boolean updateJourneyStatusId(int id, String phone);
	DriverDeviceVehicleMapping getDriverByVehicleNo(String vehicleno);
	boolean checkDeviceWithVehicleNo(String vehicleNo, String deviceUUID);
	boolean updateDeviceUUIDByVehicleNo(String vehicleNo, String deviceUUID);
	public ClientLatLong getClientLatLongByVehicleNo(String vehicleNo);
	public boolean updateTrackDeviceBookingId(String deviceBookingId, Integer bookingId);
	public boolean insertCassndraTrack2(CassandraTrack cto);
	public boolean updateJourneyStatusByDriverId(int flag, int bookingId, int driverId);
	public boolean updateJourneyStatusByVehicleAndDriver(int flag, int vehicleId, int driverId);
}
