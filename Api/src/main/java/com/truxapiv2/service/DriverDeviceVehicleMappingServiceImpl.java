package com.truxapiv2.service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.truxapiv2.dao.DriverDeviceVehicleMappingDAO;
import com.truxapiv2.dao.PersonDAOImpl;
import com.truxapiv2.model.CassandraTrack;
import com.truxapiv2.model.ClientLatLong;
import com.truxapiv2.model.CurrentLocation;
import com.truxapiv2.model.DriverDeviceVehicleMapping;
import com.truxapiv2.model.DriverLoginJson;
import com.truxapiv2.model.ResultObject;

@Service
public class DriverDeviceVehicleMappingServiceImpl implements DriverDeviceVehicleMappingService {
	
	private static final Logger logger = LoggerFactory.getLogger(DriverDeviceVehicleMappingServiceImpl.class);
	private DriverDeviceVehicleMappingDAO driverDeviceVehicleMappingDAO;
	
	public void setDriverDeviceVehicleMappingDAO(DriverDeviceVehicleMappingDAO driverDeviceVehicleMappingDAO) {
		this.driverDeviceVehicleMappingDAO = driverDeviceVehicleMappingDAO;
	}

	@Override
	@Transactional
	public DriverDeviceVehicleMapping getDriverById(int id) {
		return this.driverDeviceVehicleMappingDAO.getDriverById(id);
	}

	@Override
	@Transactional
	public DriverDeviceVehicleMapping getDriverByPhone(String phone) {
		return this.driverDeviceVehicleMappingDAO.getDriverByPhone(phone);
	}
	
	@Override
	@Transactional
	public DriverDeviceVehicleMapping getDriverByVehicleNo(String vehicleno) {
		return this.driverDeviceVehicleMappingDAO.getDriverByVehicleNo(vehicleno);
	}
	
	@Override
	@Transactional
	public boolean updateLoginStatus(int flag, String phone) {
		return this.driverDeviceVehicleMappingDAO.updateLoginStatus(flag, phone);
	}
	
	@Override
	@Transactional
	public boolean updateLoginStatusById(int flag, int id) {
		return this.driverDeviceVehicleMappingDAO.updateLoginStatus(flag, id);
	}
	
	@Override
	@Transactional
	public boolean updateJourneyStatus(int flag, String phone) {
		return this.driverDeviceVehicleMappingDAO.updateJourneyStatus(flag, phone);
	}
	
	@Override
	@Transactional
	public boolean updateJourneyStatusId(int id, String phone) {
		return this.driverDeviceVehicleMappingDAO.updateJourneyStatusId(id, phone);
	}

	@Override
	public boolean insertCassndraTrack(CassandraTrack cto) {
		return this.driverDeviceVehicleMappingDAO.insertCassndraTrack(cto);
	}
	@Override
	public boolean insertCassndraTrack2(CassandraTrack cto) {
		return this.driverDeviceVehicleMappingDAO.insertCassndraTrack2(cto);
	}
	@Override
	public boolean updateTrackDeviceBookingId(String deviceBookingId, Integer bookingId) {
		return this.driverDeviceVehicleMappingDAO.updateTrackDeviceBookingId(deviceBookingId, bookingId);
	}

	@Override
	@Transactional
	public DriverDeviceVehicleMapping getDriverByTrackingDeviceId(int trackingDeviceId) {
		return this.driverDeviceVehicleMappingDAO.getDriverByTrackingDeviceId(trackingDeviceId);
	}

	@Override
	public boolean insertCurrentVehicleLocation(CurrentLocation clo) {
		return this.driverDeviceVehicleMappingDAO.insertCurrentVehicleLocation(clo);
	}

	@Override
	@Transactional
	public ClientLatLong getClientLatLong(int clientId) {
		return this.driverDeviceVehicleMappingDAO.getClientLatLong(clientId);
	}
	@Override
	public boolean checkGeoFencing(int clientId, int deviceId) {
		
		try {
			//ClientLatLong cl = this.getClientLatLong(clientId);
			//ClientLatLong dl = this.getDeviceLatlong(deviceId);
			ClientLatLong dl = new ClientLatLong(28.507195, 77.199879);
			ClientLatLong cl = new ClientLatLong(28.507195, 77.199879);
			if(cl!=null){
				//check for radius
				double dist = this.distance(cl.getClientLat(), cl.getClientLong(), dl.getClientLat(), dl.getClientLong(), "K");
				logger.info("Dist ::"+dist);
				System.out.println ("Dist ::"+dist);
				if(dist > cl.getLoginDistanceCircle()){				
					return false;
				}else{
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public ResultObject checkGeoFencing2(ClientLatLong clientSubLL, ClientLatLong driverDeviceLL) {
		ResultObject resultObject = new ResultObject();
		try {
			if(clientSubLL!=null && driverDeviceLL!=null){
				//check for radius
				if(clientSubLL.getLoginDistanceCircle()==-1 || clientSubLL.getLoginDistanceCircle()==0 ){
					resultObject.setResultStatus(true);
					resultObject.setResultMesaage("Geofencing Not Requried");
					return resultObject; // no geofencing for -1 and 0
				} else {
					double dist = this.distance(clientSubLL.getClientLat(), clientSubLL.getClientLong(), driverDeviceLL.getClientLat(), driverDeviceLL.getClientLong(), "K");
					logger.info("Dist ::"+dist);
					System.out.println ("Dist ::"+dist);
					if(dist > clientSubLL.getLoginDistanceCircle()){				
						resultObject.setResultStatus(false);
						resultObject.setResultMesaage(""+(dist - clientSubLL.getLoginDistanceCircle()));
						return resultObject;
					}else{
						resultObject.setResultStatus(true);
						resultObject.setResultMesaage("OK");
						return resultObject;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); return resultObject;
		}
		return resultObject;
	}
	@Override
	@Transactional
	public ResultObject loginLVTDriver(DriverLoginJson dlj){
		ResultObject resultObject = new ResultObject();
		boolean validForLogin = false;
		if(dlj.getLgLat()==null || dlj.getLgLong()==null){
			resultObject.setResultStatus(false);
			resultObject.setResultMesaage("Lat/Long Missing.");
			return resultObject;
		}
		if(this.driverDeviceVehicleMappingDAO.checkDeviceWithVehicleNo(dlj.vehicleNo, dlj.getDeviceId())){
			validForLogin = true;
		} else {
			if(this.driverDeviceVehicleMappingDAO.updateDeviceUUIDByVehicleNo(dlj.vehicleNo, dlj.getDeviceId())){
				validForLogin = true;
			} else {
				validForLogin = false;
				resultObject.setResultStatus(false);
				resultObject.setResultMesaage("This Vehicle Number Is Not Mapped To This Device.");
				return resultObject;
			}
		}
		if(validForLogin){
			ClientLatLong clientSubLL = this.driverDeviceVehicleMappingDAO.getClientLatLongByVehicleNo(dlj.vehicleNo);
			ClientLatLong driverDeviceLL = new ClientLatLong(dlj.getLgLat(), dlj.lgLong);
			ResultObject geoFencingData = this.checkGeoFencing2(clientSubLL, driverDeviceLL);
			if(geoFencingData.isResultStatus()){
				resultObject.setResultStatus(true);
				resultObject.setResultMesaage("OK");
				
			} else {
				resultObject.setResultStatus(false);
				resultObject.setResultMesaage("GeoFencing Failed!! Driver needs to be within the range of "+clientSubLL.getLoginDistanceCircle()+" KM from the stand . Your approx distance from your stand is "+String.format("%1.2f", Double.parseDouble(geoFencingData.getResultMesaage()))+" KM.");
			}
		}
		if(dlj.getPunchIn()==0){
			resultObject.setResultStatus(true);
			resultObject.setResultMesaage("OK");
		}
		return resultObject;
		
		
	}
	public ClientLatLong getDeviceLatlong(int deviceId) {
		
		String url = "http://180.179.213.218/vaveapi/GettraxData.aspx?username=trux&password=t12345&deviceid="+deviceId;
		try
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);
 
            doc.getDocumentElement().normalize();
            System.out.println ("Root element: " + 
                        doc.getDocumentElement().getNodeName());       
            
            NodeList items = doc.getElementsByTagName("DATA");
            Node n1 = items.item(0);
            Element e = (Element) n1;
            if(e.getAttribute("DEVICEID").equals(""+deviceId)){
            	ClientLatLong deviceLatLong = new ClientLatLong(Double.parseDouble(e.getAttribute("LAT")), Double.parseDouble(e.getAttribute("LONG")));
            	return deviceLatLong;
            }
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage().toString());
            return null;
        }
		return null;
	}
	private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	@Override
	@Transactional
	public boolean updateJourneyStatusByVehicleAndDriver(int flag, int vehicleId, int driverId) {
		// TODO Auto-generated method stub
		return this.driverDeviceVehicleMappingDAO.updateJourneyStatusByVehicleAndDriver(flag, vehicleId, driverId);
	}


}
