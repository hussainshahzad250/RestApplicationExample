package com.truxapiv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.truxapiv2.dao.BookingLeaseDAO;
import com.truxapiv2.dao.ClientBookingDocDAO;
import com.truxapiv2.dao.DriverDeviceVehicleMappingDAO;
import com.truxapiv2.dao.DropLeaseDAO;
import com.truxapiv2.dao.TransporterRegistrationDAO;
import com.truxapiv2.model.BookingJsonFromDevice;
import com.truxapiv2.model.BookingLease;
import com.truxapiv2.model.ClientBookingDoc;
import com.truxapiv2.model.ClientLatLong;
import com.truxapiv2.model.CommunicationSMS;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.DropJsonFromDevice;
import com.truxapiv2.model.DropLease;
import com.truxapiv2.model.TrackBooking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class BookingLeaseServiceImpl implements BookingLeaseService {

	private BookingLeaseDAO bookingLeaseDAO;

	@Autowired
	private TransporterRegistrationDAO transporterRegistrationDAO;

	public void setBookingLeaseDAO(BookingLeaseDAO bookingLeaseDAO) {
		this.bookingLeaseDAO = bookingLeaseDAO;
	}

	@Autowired
	private DropLeaseDAO dropLeaseDAO;

	@Autowired
	private DriverDeviceVehicleMappingDAO driverDeviceVehicleMappingDAO;

	@Autowired
	private ClientBookingDocDAO clientBookingDocDAO;

	@Override
	@Transactional
	public int saveBooking(BookingLease dto) {
		return this.bookingLeaseDAO.saveBooking(dto);
	}

	@Override
	@Transactional
	public boolean saveOrUpdateBooking(BookingLease dto) {
		return this.bookingLeaseDAO.saveOrUpdateBooking(dto);
	}

	@Override
	@Transactional
	public int saveDropLease(DropLease dto) {
		return this.dropLeaseDAO.saveDropLease(dto);
	}

	@Override
	@Transactional
	public boolean updateBooking(BookingLease dto) {
		return this.bookingLeaseDAO.updateBooking(dto);
	}

	@Override
	@Transactional
	public boolean updateBookingClientDocs(BookingLease dto) {
		return this.bookingLeaseDAO.updateBookingClientDocs(dto);
	}

	@Override
	@Transactional
	public int saveBookingClientDoc(ClientBookingDoc cbd) {

		return this.clientBookingDocDAO.addClientBookingDoc(cbd);
	}

	@Override
	@Transactional
	public BookingLease getBookingLeaseById(int id) {

		return this.bookingLeaseDAO.getBookingLeaseById(id);
	}

	@Override
	@Transactional
	public List<Object[]> getNonClosedBookingByUserId(int userId, Date crDate) {
		return this.bookingLeaseDAO.getNonClosedBookingByUserId(userId, crDate);
	}

	@Override
	@Transactional
	public List<Object[]> getNonClosedBookingByUserId(int userId) {
		return this.bookingLeaseDAO.getNonClosedBookingByUserId(userId);
	}

	@Override
	@Transactional
	public Object getAppVersion(String app_name) {
		return this.bookingLeaseDAO.getAppVersion(app_name);
	}

	@Override
	@Transactional
	public boolean isBookingFromDeviceExists(String deviceBookingLeasesId) {
		return this.bookingLeaseDAO.isBookingFromDeviceExists(deviceBookingLeasesId);
	}

	@Override
	@Transactional
	public boolean isDropFromDeviceExists(String deviceDropLeasesId) {
		return this.dropLeaseDAO.isDropFromDeviceExists(deviceDropLeasesId);
	}

	@Override
	@Transactional
	public boolean updateJourneyStatusByDriverId(int flag, int bookingId, int driverId) {
		return this.driverDeviceVehicleMappingDAO.updateJourneyStatusByDriverId(flag, bookingId, driverId);
	}

	@Override
	public boolean hitTrackingService(TrackBooking tbo) throws MalformedURLException {
		int deviceid = tbo.getDeviceid();
		int bookingId = tbo.getTripid();
		int punchStaus = tbo.getIs_Punch_in();
		int dropId = tbo.getDropid();
		String url = "http://180.179.213.218/vaveapi/UpdateTraxTrip.aspx?username=trux&password=t12345&deviceid="
				+ deviceid + "&Is_Punch_in=" + punchStaus + "&tripid=" + bookingId + "&dropid=" + dropId + "";
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(url);

			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

			NodeList items = doc.getElementsByTagName("DATA");
			Node n1 = items.item(0);
			Element e = (Element) n1;
			if (e.getAttribute("STATUS").equals("SUCCESS")) {
				System.out.println("vave api called: " + e.getAttribute("STATUS") + " mapping (device id-tripid) = ("
						+ deviceid + "-" + bookingId + ")");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			return false;
		}
		return false;
	}

	@Override
	public double getDeviceLatestDistance(int deviceId) {
		double dist = 0.0;
		String url = "http://180.179.213.218/vaveapi/GettraxData.aspx?username=trux&password=t12345&deviceid="
				+ deviceId + "&test=t";
		System.out.println("URL=" + url);
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(url);

			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

			NodeList items = doc.getElementsByTagName("DATA");
			Node n1 = items.item(0);
			Element e = (Element) n1;
			if (e.getAttribute("DEVICEID").equals("" + deviceId)) {
				dist = Double.parseDouble(e.getAttribute("DISTANCE"));
				System.out.println("vave api called: (device id-distance) = (" + deviceId + "-" + dist + ")");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
		}
		return dist;
	}

	@Override
	@Transactional
	public int saveMapBkgJsonToBkgLease(BookingLease bl, BookingJsonFromDevice bjfd) {
		int bookingAutoId = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			bl.setFromJrLat(Double.parseDouble(bjfd.getBookingStartLatitude()));
			bl.setFromJrLong(Double.parseDouble(bjfd.getBookingStartLogitude()));
			bl.setToJrLat(Double.parseDouble(bjfd.getBookingEndLatitude()));
			bl.setToJrLong(Double.parseDouble(bjfd.getBookingEndLogitude()));
			bl.setDriverMobile(bjfd.getBookingDriverMobileNumber());
			bl.setDriverId(Integer.parseInt(bjfd.getBookingDriverId()));
			bl.setJourneyStartDate(sdf.parse(bjfd.getBookingStartTime()));
			bl.setJourneyEndDate(sdf.parse(bjfd.getBookingEndTime()));
			bl.setDeviceBookingLeasesId(bjfd.getBookingid());
			bl.setCompanyId(Integer.parseInt(bjfd.getBookingCompanyId()));
			bl.setVehicleId(Integer.parseInt(bjfd.getBookingVehicleId()));
			bl.setCreatedDateTime(new Date());
			bl.setCreatedBy(Integer.parseInt(bjfd.getBookingDriverId()));
			bl.setTotalDistance(Double.parseDouble(bjfd.getBookingTotalDistance()));

			boolean isBookingFromDeviceExistsId = this.isBookingFromDeviceExists(bjfd.getBookingid());
			System.out.println(isBookingFromDeviceExistsId);
			if (isBookingFromDeviceExistsId) {
				bookingAutoId = 1;
			} else {
				bookingAutoId = this.saveBooking(bl);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage().toString());
		} catch (Exception er) {
			System.out.println(er.getMessage().toString());
		}
		return bookingAutoId;
	}

	@Override
	@Transactional
	public int saveMapBkgJsonToBkgLease2(BookingLease bl, BookingJsonFromDevice bjfd) {
		int bookingAutoId = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (bjfd.getBookingServerId() != null && Integer.parseInt(bjfd.getBookingServerId()) != 0)
				bl.setBookingLeaseId(Integer.parseInt(bjfd.getBookingServerId()));
			// boolean isBookingFromDeviceExistsId =
			// this.isBookingFromDeviceExists(bjfd.getBookingid());

			if (bl.getBookingLeaseId() != null && Integer.parseInt(bjfd.getBookingServerId()) != 0) {
				BookingLease blDao = getBookingLeaseById(Integer.parseInt(bjfd.getBookingServerId()));
				if (bjfd.getBookingTotalDistance() != null)
					blDao.setTotalDistance(Double.parseDouble(bjfd.getBookingTotalDistance()));
				if (bjfd.getBookingEndTime() != null)
					blDao.setJourneyEndDate(sdf.parse(bjfd.getBookingEndTime()));
				if (bjfd.getBookingEndLatitude() != null)
					blDao.setToJrLat(Double.parseDouble(bjfd.getBookingEndLatitude()));
				if (bjfd.getBookingEndLogitude() != null)
					blDao.setToJrLong(Double.parseDouble(bjfd.getBookingEndLogitude()));
				if (blDao != null) {
					boolean updBooking = this.saveOrUpdateBooking(blDao);
					if (updBooking) {
						bookingAutoId = bl.getBookingLeaseId();
						this.updateJourneyStatusByDriverId(0, 0, blDao.getDriverId());
					}
				}
			} else {
				if (bjfd.getBookingStartLatitude() != null)
					bl.setFromJrLat(Double.parseDouble(bjfd.getBookingStartLatitude()));
				if (bjfd.getBookingStartLogitude() != null)
					bl.setFromJrLong(Double.parseDouble(bjfd.getBookingStartLogitude()));
				if (bjfd.getBookingDriverMobileNumber() != null)
					bl.setDriverMobile(bjfd.getBookingDriverMobileNumber());
				if (bjfd.getBookingDriverId() != null)
					bl.setDriverId(Integer.parseInt(bjfd.getBookingDriverId()));
				if (bjfd.getBookingStartTime() != null)
					bl.setJourneyStartDate(sdf.parse(bjfd.getBookingStartTime()));
				if (bjfd.getBookingid() != null)
					bl.setDeviceBookingLeasesId(bjfd.getBookingid());
				if (bjfd.getBookingCompanyId() != null)
					bl.setCompanyId(Integer.parseInt(bjfd.getBookingCompanyId()));
				if (bjfd.getBookingVehicleId() != null)
					bl.setVehicleId(Integer.parseInt(bjfd.getBookingVehicleId()));
				bl.setCreatedDateTime(new Date());
				if (bjfd.getBookingDriverId() != null)
					bl.setCreatedBy(Integer.parseInt(bjfd.getBookingDriverId()));
				boolean isBookingFromDeviceExistsId = this.isBookingFromDeviceExists(bjfd.getBookingid());
				System.out.println(isBookingFromDeviceExistsId);
				if (isBookingFromDeviceExistsId) {
					bookingAutoId = 1;
				} else {
					bookingAutoId = this.saveBooking(bl);
					if (bookingAutoId != 1) {
						this.updateJourneyStatusByDriverId(1, bookingAutoId, bl.getDriverId());
					}
				}
			}

		} catch (Exception er) {
			er.printStackTrace();
			return bookingAutoId;
		}
		return bookingAutoId;
	}

	@Override
	@Transactional
	public int saveMapBkgDropJsonToBkgLeaseDrop(DropLease dl, DropJsonFromDevice djfd) {
		int dropAutoId = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (djfd.getDropBookingServerId() != null)
				dl.setBookingLeaseId(Integer.parseInt(djfd.getDropBookingServerId()));
			if (djfd.getDropStartLatitude() != null)
				dl.setStopLat(Double.parseDouble(djfd.getDropStartLatitude()));
			if (djfd.getDropStartLogitude() != null)
				dl.setStopLong(Double.parseDouble(djfd.getDropStartLogitude()));
			if (djfd.getDropStartTime() != null)
				dl.setDropLocationReachTime(sdf.parse(djfd.getDropStartTime()));
			if (djfd.getDropEndTime() != null)
				dl.setAfterDropStartTime(sdf.parse(djfd.getDropEndTime()));
			if (djfd.getDropid() != null)
				dl.setDeviceDropLeasesId(djfd.getDropid());
			if (djfd.getDropedBoxes() != null)
				dl.setDroped_boxes(Integer.parseInt(djfd.getDropedBoxes()));
			if (djfd.getDropLocation() != null)
				dl.setDropLocation(djfd.getDropLocation());
			if (djfd.getDropNdrResponce() != null)
				dl.setNdrResponce(djfd.getDropNdrResponce());
			if (djfd.getDistanceBetweenStops() != null)
				dl.setDistanceBetweenStops(djfd.getDistanceBetweenStops());
			if (djfd.getClientOrderNo() != null)
				dl.setClientOrderNo(djfd.getClientOrderNo());
			dl.setCreatedTime(new Date());
			boolean isDropFromDeviceExistsId = this.isDropFromDeviceExists(djfd.getDropid());
			System.out.println(isDropFromDeviceExistsId);
			if (isDropFromDeviceExistsId) {
				dropAutoId = 1;
			} else {
				dropAutoId = this.saveDropLease(dl);
			}
		} catch (Exception er) {
			er.printStackTrace();
			return dropAutoId;
		}
		return dropAutoId;
	}

	@SuppressWarnings("null")
	@Override
	@Transactional
	public HashMap<String, List<?>> getVehicleType() {

		List<Object[]> rData = this.bookingLeaseDAO.getVehicleType();
		ArrayList<String> bodyType = new ArrayList<String>();
		bodyType.add("Open body");
		bodyType.add("Containerized");

		ArrayList<String> status = new ArrayList<String>();
		status.add("Active");
		status.add("Busy");
		status.add("Out of Service");

		HashMap<String, List<?>> hData = new HashMap<String, List<?>>();
		hData.put("rData", rData);
		hData.put("bodyType", bodyType);
		hData.put("status", status);

		// System.out.println(hData.get("rData"));
		return hData;
	}

	@Override
	public Object searchVehicle(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association,
			String body_type, Integer paging) {
		return bookingLeaseDAO.searchVehicle(city, hub, cluster, vehicle_type, association, body_type, paging);
	}

	@Override
	@Transactional
	public boolean updateBooking2(BookingLease dto) {
		// TODO Auto-generated method stub
		return this.bookingLeaseDAO.updateBooking2(dto);
	}

	@Override
	@Transactional
	public BookingLease getPendingBookingLease(BookingLease dto) {
		// TODO Auto-generated method stub
		return this.bookingLeaseDAO.getPendingBookingLease(dto);
	}

	@Override
	public List<?> countRecord(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association,
			String body_type) {
		return this.bookingLeaseDAO.countRecord(city, hub, cluster, vehicle_type, association, body_type);
	}

	@Override
	public HashMap<String, List<?>> sendVehicleSMS(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language) {

		List<?> vBody = this.bookingLeaseDAO.searchVehicleBody(city, hub, cluster, vehicle_type, association, body_type,
				language);
		List<?> oNum = this.bookingLeaseDAO.searchOwnerNumber(city, hub, cluster, vehicle_type, association, body_type,
				language);
		List<?> vNum = this.bookingLeaseDAO.searchVehicleNumber(city, hub, cluster, vehicle_type, association,
				body_type, language);
		List<?> vType = this.bookingLeaseDAO.searchVehicleType(city, hub, cluster, vehicle_type, association, body_type,
				language);

		HashMap<String, List<?>> hData = new HashMap<String, List<?>>();
		hData.put("vBody", vBody);
		hData.put("oNum", oNum);
		hData.put("vNum", vNum);
		hData.put("vType", vType);

		return hData;
	}

	@Override
	public ControllerDAOTracker fillCommunicationSms(HashMap<String, List<?>> cdt, String language) {
		ControllerDAOTracker c = new ControllerDAOTracker();
		boolean flag = false;

		for (int i = 0; i < cdt.get("vBody").size(); i++) {
			CommunicationSMS cSms = new CommunicationSMS();
			cSms.setSenderMask("IM");
			cSms.setSmsProvider("GUPSHUP");
			cSms.setMobileNumber(cdt.get("oNum").get(i).toString().replace(" ", ""));
			String smsText;
			if (language.equals("hi")) {
				smsText = "प्रिय वाहन मालिक, अपने वाहन " + cdt.get("vNum").get(i).toString().replace(" ", "") + ", "
						+ cdt.get("vType").get(i).toString() + " (" + cdt.get("vBody").get(i).toString()
						+ ") को हमारे साथ भाड़े पर लगाने के लिए तुरंत 7838490077 पर कॉल करें - Trux";
			} else {
				smsText = "Dear Vehicle Owner, we require your vehicle " + cdt.get("vNum").get(i).toString() + ", "
						+ cdt.get("vType").get(i).toString() + " (" + cdt.get("vBody").get(i).toString()
						+ ") on urgent basis. Please call on 7838490077 - Trux";
			}
			cSms.setSmsText(smsText);
			cSms.setRequestDate(new Date());
			cSms.setRequestProcess(new Date());

			ControllerDAOTracker ce = transporterRegistrationDAO.saveSMSRecord(cSms);

			if (ce.isSuccess()) {
				flag = true;
			} else {
				flag = false;
				break;
			}
		}

		if (flag == true) {
			c.setSuccess(true);
			c.setErrorCode("100");
			c.setErrorMessage("All entries has been processed for SMS.");
		} else {
			c.setSuccess(false);
			c.setErrorCode("101");
			c.setErrorMessage("All entries has not been processed for SMS.");
		}

		return c;

	}

}
