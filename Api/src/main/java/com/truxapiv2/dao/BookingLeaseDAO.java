package com.truxapiv2.dao;

import java.util.Date;
import java.util.List;

import com.truxapiv2.model.BookingLease;
import com.truxapiv2.model.ControllerDAOTracker;
public interface BookingLeaseDAO {

	public int saveBooking(BookingLease dto);
	public boolean updateBooking(BookingLease dto);
	public boolean updateBookingClientDocs(BookingLease dto);
	public boolean isBookingFromDeviceExists(String deviceBookingLeasesId);
	public List<Object[]> getNonClosedBookingByUserId(int userId, Date crDate);
	public Object getAppVersion(String app_name);
	public List<Object[]> getNonClosedBookingByUserId(int userId);
	public boolean saveOrUpdateBooking(BookingLease dto);
	public BookingLease getBookingLeaseById(int id);
	public List<Object[]> getVehicleType();
	public Object searchVehicle(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association,
			String body_type, Integer paging);
	public boolean updateBooking2(BookingLease dto);
	public BookingLease getPendingBookingLease(BookingLease dto);
	public List<?> countRecord(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association,
			String body_type);
	public List<?> searchVehicleBody(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language);
	public List<?> searchOwnerNumber(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language);
	public List<?> searchVehicleNumber(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language);
	public List<?> searchVehicleType(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language);
	
}
