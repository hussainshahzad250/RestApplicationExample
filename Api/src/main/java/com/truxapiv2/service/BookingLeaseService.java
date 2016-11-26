package com.truxapiv2.service;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.truxapiv2.model.BookingJsonFromDevice;
import com.truxapiv2.model.BookingLease;
import com.truxapiv2.model.ClientBookingDoc;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.DropJsonFromDevice;
import com.truxapiv2.model.DropLease;
import com.truxapiv2.model.TrackBooking;

public interface BookingLeaseService {

	public int saveBooking(BookingLease dto);
	public boolean updateBooking(BookingLease dto);
	public int saveDropLease(DropLease dto);
	public boolean hitTrackingService(TrackBooking tbo) throws MalformedURLException;
	public double getDeviceLatestDistance(int deviceId);
	public int saveMapBkgJsonToBkgLease(BookingLease bl, BookingJsonFromDevice bjfd);
	public boolean updateBookingClientDocs(BookingLease dto);
	public boolean isBookingFromDeviceExists(String deviceBookingLeasesId);
	public int saveMapBkgDropJsonToBkgLeaseDrop(DropLease dl, DropJsonFromDevice djfd);
	public boolean isDropFromDeviceExists(String deviceDropLeasesId);
	public List<Object[]> getNonClosedBookingByUserId(int userId, Date crDate);
	public Object getAppVersion(String app_name);
	List<Object[]> getNonClosedBookingByUserId(int userId);
	int saveBookingClientDoc(ClientBookingDoc cbd);
	public boolean saveOrUpdateBooking(BookingLease dto);
	int saveMapBkgJsonToBkgLease2(BookingLease bl, BookingJsonFromDevice bjfd);
	public BookingLease getBookingLeaseById(int id);
	public boolean updateJourneyStatusByDriverId(int flag, int bookingId, int driverId);
	public Object getVehicleType();
	public boolean updateBooking2(BookingLease dto);
	public BookingLease getPendingBookingLease(BookingLease dto);
	public List<?> countRecord(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association,
			String body_type);
	public Object searchVehicle(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association,
			String body_type, Integer paging);
	public HashMap<String, List<?>> sendVehicleSMS(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language);
	public ControllerDAOTracker fillCommunicationSms(HashMap<String, List<?>> cdt, String language);
	
}
