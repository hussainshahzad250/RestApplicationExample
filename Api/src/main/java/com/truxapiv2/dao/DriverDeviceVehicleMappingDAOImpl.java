package com.truxapiv2.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.CassandraTrack;
import com.truxapiv2.model.ClientLatLong;
import com.truxapiv2.model.Driver;
import com.truxapiv2.model.DriverDeviceVehicleMapping;
import com.truxapiv2.model.CurrentLocation;

import com.truxapiv2.dao.DriverDAO;
import com.datastax.driver.core.Cluster;


@Repository
public class DriverDeviceVehicleMappingDAOImpl implements DriverDeviceVehicleMappingDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DriverDeviceVehicleMappingDAOImpl.class);

	private SessionFactory sessionFactory;
	@Autowired
	private DriverDAO driverDAO;
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public DriverDeviceVehicleMapping getDriverById(int id) {
		try {
			Session session=this.sessionFactory.getCurrentSession();
			 Query query = session.createQuery("from DriverDeviceVehicleMapping where driverId = :driverId ");
		     query.setParameter("driverId", id);
		        List<?> dList = query.list();
		        if(dList != null && !dList.isEmpty()){
		        	DriverDeviceVehicleMapping D = (DriverDeviceVehicleMapping)dList.get(0);
		        	return this.crntStatDDVM(D);
		        } else{
		        	return null;
		        }
			}catch(Exception er){
				System.out.println(er.getMessage().toString()); return null;
			} 
	}

	@Override
	public DriverDeviceVehicleMapping getDriverByPhone(String phone) {
		try {
			Session session=this.sessionFactory.getCurrentSession();	
		 Query query = session.createQuery("from DriverDeviceVehicleMapping where driverPhoneNumber = :phone ");
	     query.setParameter("phone", phone);
	        List<?> dList = query.list();
	        if(dList != null && !dList.isEmpty()){
	        	DriverDeviceVehicleMapping D = (DriverDeviceVehicleMapping)dList.get(0);
	        	
	        	return this.crntStatDDVM(D);
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		} 
		
	}
	public DriverDeviceVehicleMapping crntStatDDVM(DriverDeviceVehicleMapping ddvm){
		
		Session session=this.sessionFactory.getCurrentSession();
		try {
			String selecteSql = "SELECT DALV.`id`, DR.`driverName`, VR.`subclient_id`, DALV.`punchIn`, DALV.`punchOut`, DALV.`openingkm`, DALV.`closingkm`, VR.`is_barcode_issued`, DR.`driverPhoto`, DALV.`driver_apk_version` "; 
			selecteSql +=", (SELECT BL.`bookingLeaseId` FROM `booking_lease` AS BL WHERE BL.`driverId`=VR.`driver_id` AND BL.`vehicleId`=VR.`id` AND `journeyEndDate` IS NULL ORDER BY `journeyStartDate` DESC LIMIT 1) pendingbookingId";
			selecteSql +=", (SELECT GROUP_CONCAT(userid) FROM client_truxuser_mapping AS CTM WHERE CTM.`clientsubid`=VR.`subclient_id`) allowedUser ";
			selecteSql +=", (SELECT NAME FROM client_master AS CM LEFT JOIN client_sub_master AS CSM ON CM.`idClientMaster`=CSM.`idClientMaster` WHERE CSM.`idClientSubMaster`=VR.`subclient_id`) subClientName ";
			selecteSql +=", (SELECT COUNT(id) FROM `driver_attendance_leased_vehicles` AS DALV WHERE DALV.`driverId`=DR.`id` AND DALV.`vehicle_number`=VR.`vehicle_number` AND DALV.`punchOut` IS NULL AND from_app=1) AS Acnt ";
			selecteSql +=" FROM vehicle_registration AS VR ";
			selecteSql +=" LEFT JOIN `driver_registration` AS DR ON DR.`id`=VR.`driver_id` ";
			selecteSql +=" LEFT JOIN `driver_attendance_leased_vehicles` AS DALV ON DALV.`driverId`=DR.`id` AND DALV.`vehicle_number`=VR.`vehicle_number`";
			selecteSql +=" WHERE VR.`driver_id`="+ddvm.getDriverId()+" AND VR.`id`="+ddvm.getVehicleId()+" HAVING (IF(Acnt>0, DALV.`punchOut` IS NULL, 1))  ORDER BY DALV.id DESC LIMIT 1 ";
			//sql.concat("");
			Query query=session.createSQLQuery(selecteSql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			List<?> dataList = query.list();
						
	        if(dataList != null && !dataList.isEmpty()){
	        	
	        	for (Object record : dataList) {
	        		Map dataListMap = (Map) record;
	        		if(dataListMap.get("driverPhoto")!=null){
	        			ddvm.setDriverImage(""+dataListMap.get("driverPhoto"));
	        		}
	        		if(dataListMap.get("subClientName")!=null){
	        			ddvm.setDriverClientName(""+dataListMap.get("subClientName"));
	        		}
	        		if(dataListMap.get("subclient_id")!=null){
	        			ddvm.setDriverClientId(Integer.parseInt(dataListMap.get("subclient_id").toString()));
	        		}
	        		if(dataListMap.get("openingkm")!=null){
	        			ddvm.setLoginOpeningKM(Integer.parseInt(dataListMap.get("openingkm").toString()));
	        		}
	        		if(dataListMap.get("closingkm")!=null){
	        			ddvm.setLoginClosingKM(Integer.parseInt(dataListMap.get("closingkm").toString()));
	        		}
	        		if(dataListMap.get("is_barcode_issued")!=null){
	        			if(dataListMap.get("is_barcode_issued").toString()=="true"){
	        				ddvm.setIsBarCodeIssued(1);
    					}else{
    						ddvm.setIsBarCodeIssued(0);
    					}
	        		}
	        		if(dataListMap.get("allowedUser")!=null){
	        			List<Integer> allowedUserL = new ArrayList<Integer>();
	        			for (String s : ((String) dataListMap.get("allowedUser")).split(",")){
	        				allowedUserL.add(Integer.valueOf(s));
	        			}
	        			if(allowedUserL!=null){
	        				ddvm.setAllowedUser(allowedUserL);
	        			}
	        		}
	        		if(dataListMap.get("pendingbookingId")!=null){
	        			ddvm.setBookingId(Integer.parseInt(dataListMap.get("pendingbookingId").toString()));
	        			ddvm.setDriverStatus(1);
	        		}
	        		
	        		if(dataListMap.get("pendingbookingId")==null){
	        			ddvm.setDriverStatus(0);
	        			ddvm.setBookingId(0);
	        		}
	        		if(dataListMap.get("punchIn")!=null && dataListMap.get("punchOut")==null){
	        			ddvm.setLoginId(Integer.parseInt(dataListMap.get("id").toString()));
	        			ddvm.setLoginStatus(1);
	        			ddvm.setLastLoginTime((Date) dataListMap.get("punchIn"));
	        		}
	        		if((dataListMap.get("punchIn")!=null && dataListMap.get("punchOut")!=null) || (dataListMap.get("punchIn")==null && dataListMap.get("punchOut")==null)){
	        			ddvm.setLoginId(0);
	        			ddvm.setLoginStatus(0);
	        			if(dataListMap.get("punchOut")!=null){
	        				ddvm.setLastLogoutTime((Date) dataListMap.get("punchOut"));
	        			}	        			
	        		}
	        		if(dataListMap.get("driver_apk_version")!=null){
	        			ddvm.setDriver_apk_version(dataListMap.get("driver_apk_version").toString());
	        		}/**/
	        	}
	        	return ddvm;
	        } else{
	        	return ddvm;
	        }
		}catch(Exception er){
			//session.close();
			System.out.println(er.getMessage().toString()); return ddvm;
		}
		
				
	}
	@Override
	public DriverDeviceVehicleMapping getDriverByVehicleNo(String vehicleno) {
		try {
			
			int currentAssignedDriverid = this.getDriverIdByVehicleNo(vehicleno);
			if(currentAssignedDriverid!=0){
				Session session=this.sessionFactory.getCurrentSession();	
				Query query = session.createQuery("from DriverDeviceVehicleMapping where vehicleNumber = :vehicleno AND driverId = :driverid");
				query.setParameter("vehicleno", vehicleno);
				query.setParameter("driverid", currentAssignedDriverid);
		        List<?> dList = query.list();
		        if(dList != null && !dList.isEmpty()){
		        	
		        	DriverDeviceVehicleMapping D = (DriverDeviceVehicleMapping)dList.get(0);
		        	
		        	/*Driver dr = driverDAO.getDriverByPhone(D.getDriverPhoneNumber());
		        	if(dr != null){
		        		if(dr.getDriverPhoto() != null && !dr.getDriverPhoto().equals("")){
		        			D.setDriverImage(dr.getDriverPhoto());
		        		}        			
		        	}
	        		if(D.getSubClientId()!=null){ 
	        			D.setDriverClientName(this.getClientNameBySubClientId(D.getSubClientId()));
	        			D.setDriverClientId(D.getSubClientId());
	        			List<Integer> allowedUser = this.getTruxUserMappingByClientId(D.getSubClientId());
	            		if(allowedUser!=null){
	            			D.setAllowedUser(allowedUser);
	            		}
	        		}
	        		if(D.getLoginId()!=null && !D.getLoginId().equals("0")){
	        			Object[] LoginOpeningClosingKM = this.getLoginOpeningClosingKM(D.getLoginId());
	        			if(LoginOpeningClosingKM==null){
	        				LoginOpeningClosingKM = getLoginOpeningClosingKMByDriverId(dr.getId());
	        			}
	        			if(LoginOpeningClosingKM!=null){
	        				if(LoginOpeningClosingKM[0]!=null){
	        					D.setLoginOpeningKM(Integer.parseInt(LoginOpeningClosingKM[0].toString()));
	        				}
	        				if(LoginOpeningClosingKM[1]!=null){
	        					D.setLoginClosingKM(Integer.parseInt(LoginOpeningClosingKM[1].toString()));
	        				}
	        			}
	        		}
	        		if(D.getVehicleId()!=null  && D.getVehicleId()!=0){ 
	        			Object[] vehicleInfo = this.getVehicleInfoByVehicleId(D.getVehicleId());
	        			if(vehicleInfo!=null){
	        				if(vehicleInfo[0]!=null){	
	        					if(vehicleInfo[0].toString()=="true"){
	        						D.setIsBarCodeIssued(1);
	        					}else{
	        						D.setIsBarCodeIssued(0);
	        					}
	        				}
	        			}
	        		}
	        		*/
		        	logger.info("Driver loaded successfully, Driver details="+D.toString());
		        	return this.crntStatDDVM(D);
		        	//return D;
		        } else{
		        	return null;
		        }
			} else{
				logger.info("No Driver For This Vehicle="+vehicleno);
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		} 
		
	}
	@Override
	public DriverDeviceVehicleMapping getDriverByTrackingDeviceId(int trackingDeviceId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			Query query = session.createQuery("from DriverDeviceVehicleMapping where trackingDeviced = :tracking_device_id ");
			query.setParameter("tracking_device_id", trackingDeviceId);
			    List<?> dList = query.list();
			    if(dList != null && !dList.isEmpty()){
			    	DriverDeviceVehicleMapping D = (DriverDeviceVehicleMapping)dList.get(0);
			    	return this.crntStatDDVM(D);
			    } else{
			    	return null;
			    }
			}catch(Exception er){
				System.out.println(er.getMessage().toString()); return null;
			}		
	}
	@Override
	public boolean updateLoginStatus(int flag, String phone) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update DriverDeviceVehicleMapping set loginStatus=:flag where driverPhoneNumber=:phone");
			query.setInteger("flag", flag);
			query.setString("phone", phone);
			int modifications=query.executeUpdate();
			if(modifications!=0){
				logger.info("Driver Login Status Updated to "+flag+" successfully For :="+phone);
				return true;
			}else{
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	@Override
	public boolean updateLoginStatus(int flag, int id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update DriverDeviceVehicleMapping set loginStatus=:flag where driver_id=:id");
			query.setInteger("flag", flag);
			query.setInteger("id", id);
			int modifications=query.executeUpdate();
			if(modifications!=0){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}

	@Override
	public boolean updateJourneyStatus(int flag, String phone) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update DriverDeviceVehicleMapping set driverStatus=:flag where driverPhoneNumber=:phone");
			query.setInteger("flag", flag);
			query.setString("phone", phone);
			int modifications=query.executeUpdate();
			if(modifications!=0){
				logger.info("Driver Journey Status Updated to "+flag+" successfully For :="+phone);
				return true;
			}else{
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	@Override
	public boolean updateJourneyStatusByVehicleAndDriver(int flag, int vehicleId, int driverId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update DriverDeviceVehicleMapping set driverStatus=:flag where driverId=:driverId AND vehicleId=:vehicleId");
			query.setInteger("flag", flag);
			query.setInteger("vehicleId", vehicleId);
			query.setInteger("driverId", driverId);
			int modifications=query.executeUpdate();
			if(modifications!=0){
				logger.info("Driver Journey Status Updated to "+flag+" successfully");
				return true;
			}else{
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	@Override
	public boolean updateJourneyStatusId(int id, String phone) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update DriverDeviceVehicleMapping set bookingId=:id where driverPhoneNumber=:phone");
			query.setInteger("id", id);
			query.setString("phone", phone);
			int modifications=query.executeUpdate();
			if(modifications!=0){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	@Override
	public boolean updateJourneyStatusByDriverId(int flag, int bookingId, int driverId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update DriverDeviceVehicleMapping set driverStatus=:flag, bookingId=:booking_id where driverId=:driver_id");
			query.setInteger("flag", flag);
			query.setInteger("booking_id", bookingId);
			query.setInteger("driver_id", driverId);
			int modifications=query.executeUpdate();
			if(modifications!=0){
				logger.info("Driver Journey Status Updated to successfully For driverId ="+driverId);
				return true;
			}else{
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	
	@Autowired
	private ServletContext servletContext;
	@Override
	public boolean insertCassndraTrack(CassandraTrack cto) {
		
		//com.datastax.driver.core.Session cassandraSession = null;
		//Cluster.Builder cassandraCluster = null;
		boolean rtn = false;
		/*if(cassandraCluster.build().isClosed()){
	 		cassandraCluster = Cluster.builder().addContactPoints("54.169.184.149").withPort(9042).withCredentials("cassandra", "password@123");
		}*/
		//Cluster.Builder cassandraCluster = Cluster.builder().addContactPoints("54.169.184.149").withPort(9042).withCredentials("cassandra", "password@123");
		com.datastax.driver.core.Session cassandraSession = (com.datastax.driver.core.Session) servletContext.getAttribute("cassandraSession");
    	
	 	 
		try {
			 
			int bookingId=cto.getBookingId();
			Integer driverId=cto.getDriverId();
			String driverMobile = cto.getDriverMobile();
			int companySubId = cto.getCompanySubId();
			double vehicleLat = cto.getVehicleLat();
			double vehicleLong = cto.getVehicleLong();
			double vehicleDistance = cto.getVehicleDistance();
			String vehicleNumber = cto.getVehicleNo();
			int dropId = cto.getDropId();
			int punchIn = cto.getPunchIn();
			int isIdle = cto.getIsIdle();
			double speed = cto.getSpeed();
			long epochTMS = System.currentTimeMillis()/1000;
			String ls_vh_loct_id =""+epochTMS;
			if(driverId!=null && !driverId.equals("")){
			  ls_vh_loct_id = ls_vh_loct_id+""+driverId;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String current_date_str = sdf.format(new Date());
			String query1 = "INSERT INTO leased_vehicle_location2 (ls_vh_loct_id, booking_id, driver_id, driver_mobile, company_sub_id, vehicle_lat, vehicle_long, loct_time, vehicle_distance, vehicle_no, drop_id, punch_in, is_idle, speed) VALUES("+ls_vh_loct_id+", "+bookingId+","+driverId+", "+driverMobile+", "+companySubId+", "+ vehicleLat+", "+vehicleLong+", '"+current_date_str+"', "+vehicleDistance+",'"+vehicleNumber+"', "+dropId+", "+punchIn+", "+isIdle+", "+speed+");" ;
			System.out.println(query1);
		    cassandraSession.execute(query1);
		    String query2 = "INSERT INTO current_vehicle_location (device_id, loct_time, booking_id, loct_lattitude, loct_logitude, punch_in, is_idle, speed) VALUES("+driverId+", '"+current_date_str+"',"+bookingId+", "+ vehicleLat+", "+vehicleLong+", "+punchIn+", "+isIdle+", "+speed+");" ;
			System.out.println(query2);
		    cassandraSession.execute(query2);
		    String query3 = "INSERT INTO current_vehicle_location_2 (device_id, loct_time, booking_id, loct_lattitude, loct_logitude, punch_in, is_idle, speed) VALUES('"+driverId+"', '"+current_date_str+"','"+bookingId+"', "+ vehicleLat+", "+vehicleLong+", "+punchIn+", "+isIdle+", "+speed+");" ;
			System.out.println(query3);
		    cassandraSession.execute(query3);
		    
		    rtn = true;
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());			
			rtn = false;			
		}
		
		return rtn;
	}
	
	@Override
	public boolean insertCassndraTrack2(CassandraTrack cto) {
		
		//com.datastax.driver.core.Session cassandraSession = null;
		//Cluster.Builder cassandraCluster = null;
		boolean rtn = false;
		/*if(cassandraCluster.build().isClosed()){
	 		cassandraCluster = Cluster.builder().addContactPoints("54.169.184.149").withPort(9042).withCredentials("cassandra", "password@123");
		}*/
		//Cluster.Builder cassandraCluster = Cluster.builder().addContactPoints("54.169.184.149").withPort(9042).withCredentials("cassandra", "password@123");
		com.datastax.driver.core.Session cassandraSession = (com.datastax.driver.core.Session) servletContext.getAttribute("cassandraSession");
    	
	 	 
		try {
			 
			int bookingId=cto.getBookingId();
			Integer driverId=cto.getDriverId();
			String driverMobile = cto.getDriverMobile();
			int companySubId = cto.getCompanySubId();
			double vehicleLat = cto.getVehicleLat();
			double vehicleLong = cto.getVehicleLong();
			double vehicleDistance = cto.getVehicleDistance();
			String vehicleNumber = cto.getVehicleNo();
			int dropId = cto.getDropId();
			int punchIn = cto.getPunchIn();
			int isIdle = cto.getIsIdle();
			double speed = cto.getSpeed();
			String deviceId = cto.getDeviceId();
			long epochTMS = System.currentTimeMillis()/1000;
			String ls_vh_loct_id =""+epochTMS;
			if(driverId!=null && !driverId.equals("")){
			  //ls_vh_loct_id = ls_vh_loct_id+""+driverId;
				ls_vh_loct_id = ls_vh_loct_id+""+driverId+(int)(Math.random()*1000000);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String cTMS = cto.getCurrentTMS();			
			String current_date_str = (cTMS.trim()!=null)?(cTMS):(sdf.format(new Date()));
			String deviceBookingId = cto.getDeviceBookingId();
			String query1 = "INSERT INTO leased_vehicle_location2 (ls_vh_loct_id, booking_id, driver_id, driver_mobile, company_sub_id, vehicle_lat, vehicle_long, loct_time, vehicle_distance, vehicle_no, drop_id, punch_in, is_idle, speed, device_id, device_booking_id) VALUES("+ls_vh_loct_id+", "+bookingId+","+driverId+", "+driverMobile+", "+companySubId+", "+ vehicleLat+", "+vehicleLong+", '"+current_date_str+"', "+vehicleDistance+",'"+vehicleNumber+"', "+dropId+", "+punchIn+", "+isIdle+", "+speed+", '"+deviceId+"', '"+deviceBookingId+"');" ;
			System.out.println(query1);
		    cassandraSession.execute(query1);
		    
			String query3 = "INSERT INTO current_vehicle_location_2 (device_id, loct_time, booking_id, loct_lattitude, loct_logitude, punch_in, is_idle, speed) VALUES('"+deviceId+"', '"+current_date_str+"','"+bookingId+"', "+ vehicleLat+", "+vehicleLong+", "+punchIn+", "+isIdle+", "+speed+");" ;
			System.out.println(query3);
		    cassandraSession.execute(query3);
		    
		    rtn = true;
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());			
			rtn = false;			
		}
		
		return rtn;
	}
	@Override
	public boolean updateTrackDeviceBookingId(String deviceBookingId, Integer bookingId) {
		
		boolean rtn = false;
		com.datastax.driver.core.Session cassandraSession = (com.datastax.driver.core.Session) servletContext.getAttribute("cassandraSession");
    	try {
			
			String query1 = "UPDATE leased_vehicle_location2  booking_id="+bookingId+" WHERE device_booking_id = '"+deviceBookingId+"';" ;
			System.out.println(query1);
		    cassandraSession.execute(query1);
		    
		    rtn = true;
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());			
			rtn = false;			
		}
		
		return rtn;
	}
	@Override
	public boolean insertCurrentVehicleLocation(CurrentLocation clo) {
		com.datastax.driver.core.Session cassandraSession = null;
		
	 	Cluster.Builder cassandraCluster = Cluster.builder().addContactPoints("54.169.184.149").withPort(9042).withCredentials("cassandra", "password@123");
	    cassandraSession = cassandraCluster.build().connect("hb");
		try {
			 
			String deviceId = clo.getDeviceid();
			String loct_lat = clo.getLattitude();
			String loct_long = clo.getLogitude();			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String current_date_str = sdf.format(new Date());
			String query1 = "INSERT INTO current_vehicle_location (device_id, loct_time, loct_lattitude, loct_logitude) VALUES("+deviceId+", '"+current_date_str+"', "+loct_lat+",'"+loct_long+"');" ;
			System.out.println(query1);
		    cassandraSession.execute(query1);
		    cassandraSession.close();
		    return true;
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());			
		    cassandraSession.close();
			return false;
			
		}	
		
	}

	@Override
	public ClientLatLong getClientLatLong(int clientId) {
		System.out.println("called getClientLatLong");
		Session session=sessionFactory.openSession();
		try {
			
			List<Object[]> dataList=session.createSQLQuery("SELECT client_lat, client_lot, driver_login_distance FROM client_sub_master WHERE idClientSubMaster='"+clientId+"' ").list();
			session.close();
			
			HashMap<String,Double> clMap= new HashMap<String,Double>();
			for(Object[] row : dataList){
				clMap.put("lat", Double.parseDouble(row[0].toString()));
				clMap.put("long", Double.parseDouble(row[1].toString()));
				if(row[2]!=null){
					clMap.put("dis", Double.parseDouble(row[2].toString()));				
				} else {
					clMap.put("dis", (double) 0);
				}				
			}
	        if(clMap != null && !clMap.isEmpty()){
	        	ClientLatLong cl = new ClientLatLong(clMap.get("lat"), clMap.get("long"), clMap.get("dis"));
	        	return cl;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			//session.close();
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	@Override
	public ClientLatLong getClientLatLongByVehicleNo(String vehicleNo) {
		System.out.println("called getClientLatLong");
		Session session=sessionFactory.openSession();
		try {
			
			List<Object[]> dataList=session.createSQLQuery("SELECT client_lat, client_lot, driver_login_distance FROM client_sub_master AS CSM LEFT JOIN `vehicle_registration` AS VR ON CSM.`idClientSubMaster`=VR.`subclient_id` WHERE VR.`vehicle_number`='"+vehicleNo+"' ").list();
			session.close();
			
			HashMap<String,Double> clMap= new HashMap<String,Double>();
			for(Object[] row : dataList){
				clMap.put("lat", Double.parseDouble(row[0].toString()));
				clMap.put("long", Double.parseDouble(row[1].toString()));
				if(row[2]!=null){
					clMap.put("dis", Double.parseDouble(row[2].toString()));				
				} else {
					clMap.put("dis", (double) 0);
				}
			}
	        if(clMap != null && !clMap.isEmpty()){
	        	ClientLatLong cl = new ClientLatLong(clMap.get("lat"), clMap.get("long"), clMap.get("dis"));
	        	return cl;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			//session.close();
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	public String getClientNameBySubClientId(int subClientLeasesId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createSQLQuery("SELECT name FROM client_master AS CM LEFT JOIN client_sub_master AS CSM ON CM.`idClientMaster`=CSM.`idClientMaster` WHERE CSM.`idClientSubMaster`=:scId ");
			query.setParameter("scId", subClientLeasesId);
			List<?> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return (String) dataList.get(0);
	        } else{
	        	return "N/A";
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return "N/A";
		}
	}
	public List<Integer> getTruxUserMappingByClientId(int subClientId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createSQLQuery("SELECT userid FROM client_truxuser_mapping WHERE clientsubid=:scId  && is_active=1");
			query.setParameter("scId", subClientId);
			List<?> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return  (List<Integer>) dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	public Object[] getLoginOpeningClosingKM(int loginId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<Object[]> dataList=session.createSQLQuery("SELECT openingkm, closingkm FROM driver_attendance_leased_vehicles WHERE id="+loginId+" ").list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return dataList.get(0);
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}	
	public Object[] getLoginOpeningClosingKMByDriverId(int driverId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<Object[]> dataList=session.createSQLQuery("SELECT openingkm, closingkm FROM driver_attendance_leased_vehicles WHERE driverId="+driverId+" ORDER BY id DESC LIMIT 1 ").list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return dataList.get(0);
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	public int getLoginOpeningKM(int loginId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<?> dataList= session.createSQLQuery("SELECT openingkm FROM driver_attendance_leased_vehicles  WHERE id="+loginId+" ").list();
			
	        if(dataList != null && !dataList.isEmpty()){
	        	if(dataList.get(0)==null){
	        		return 0;
	        	}
	        	return (Integer) dataList.get(0);
	        } else{
	        	return 0;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return 0;
		}
	}
	public int getLoginClosingKM(int loginId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<?> dataList= session.createSQLQuery("SELECT closingkm FROM driver_attendance_leased_vehicles  WHERE id="+loginId+" ").list();
			
	        if(dataList != null && !dataList.isEmpty()){
	        	if(dataList.get(0)==null){
	        		return 0;
	        	}
	        	return (Integer) dataList.get(0);
	        } else{
	        	return 0;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return 0;
		}
	}
	public int getDriverIdByVehicleNo(String vehicle_number) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<?> dataList= session.createSQLQuery("SELECT VR.`driver_id` FROM vehicle_registration AS VR WHERE VR.`vehicle_number`='"+vehicle_number+"' ").list();
			
	        if(dataList != null && !dataList.isEmpty()){
	        	if(dataList.get(0)==null){
	        		return 0;
	        	}
	        	return (Integer) dataList.get(0);
	        } else{
	        	return 0;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return 0;
		}
	}
	public Object[] getVehicleInfoByVehicleId(int vehicleId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<Object[]> dataList= session.createSQLQuery("SELECT VR.`is_barcode_issued`, id FROM vehicle_registration AS VR WHERE VR.`id`='"+vehicleId+"' ").list();
			
			if(dataList != null && !dataList.isEmpty()){
				return dataList.get(0);
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	@Override
	public boolean checkDeviceWithVehicleNo(String vehicleNo, String deviceUUID) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String selecteSql= "SELECT DDVM.`id` FROM `driver_device_vehicle_mapping` AS DDVM LEFT JOIN `vehicle_registration` AS VR ON DDVM.`driver_id`=VR.`driver_id` "; 
			selecteSql = selecteSql+" WHERE DDVM.device_UUID='"+deviceUUID+"' AND VR.`vehicle_number`='"+vehicleNo+"' " ;
			Query query=session.createSQLQuery(selecteSql);

			List<?> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return true;
	        } else{
	        	return false;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		}
	}
	@Override
	public boolean updateDeviceUUIDByVehicleNo(String vehicleNo, String deviceUUID) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			String updSql= "UPDATE  `driver_device_vehicle_mapping` AS DDVM LEFT JOIN `vehicle_registration` AS VR ON DDVM.`driver_id`=VR.`driver_id`"; 
			updSql = updSql+" SET DDVM.device_UUID='"+deviceUUID+"' WHERE VR.`vehicle_number`='"+vehicleNo+"' AND DDVM.device_UUID IS NULL " ;
			Query query=session.createSQLQuery(updSql);
			
			int modifications=query.executeUpdate();
			if(modifications!=0){
				return true;
			}else{
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 	
	}
}
