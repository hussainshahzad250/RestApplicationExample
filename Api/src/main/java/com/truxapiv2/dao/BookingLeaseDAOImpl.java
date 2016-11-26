package com.truxapiv2.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.BookingLease;
import com.truxapiv2.model.ClientLatLong;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.Person;

@Repository
public class BookingLeaseDAOImpl implements BookingLeaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(BookingLeaseDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	@Override
	public BookingLease getBookingLeaseById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		BookingLease b = (BookingLease) session.load(BookingLease.class, new Integer(id));
		logger.info("Booking loaded successfully, Booking details="+b);
		return b;
	}
	@Override
	public int saveBooking(BookingLease dto) {
		Session session=sessionFactory.openSession();	
		Transaction tx=session.beginTransaction();
		int bkId = 0;
		try{		
			bkId = (Integer) session.save(dto);		
			tx.commit();
			if(bkId!=0){
				session.close();
				return bkId;
			}
			}catch(Exception er){
				session.close();
				return bkId;
			}
		session.close();
		return bkId;	
	}
	@Override
	public boolean saveOrUpdateBooking(BookingLease dto) {
		Session session = this.sessionFactory.getCurrentSession();	
		try{		
			session.saveOrUpdate(dto);			
		}catch(Exception er){			
			return false;
		}
		
		return true;	
	}
	public boolean updateBooking(BookingLease dto) {
		try { logger.info("called"+dto.toString());
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update BookingLease set journeyEndDate=:jryEndDt, bookingLsStatus=1, totalDistance=:dist, modifiedBy=:modifiedBy, updatedDateTime=:updatedDateTime where driverMobile=:phone and bookingLsStatus = 0");
			//query.setDate("jryEndDt", new Date());
			query.setParameter("jryEndDt", new Date());
			query.setString("phone", dto.getDriverMobile());
			query.setParameter("modifiedBy", dto.getModifiedBy());
			query.setParameter("updatedDateTime", dto.getUpdatedDateTime());
			query.setParameter("dist", dto.getTotalDistance());
			int modifications=query.executeUpdate();
			if(modifications!=0){
				logger.info("Booking Status has been Updated to end :"+dto.getJourneyEndDate()+" successfully For :="+dto.getDriverMobile());
				return true;
			}else{
				logger.info("ghanta");
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	@Override
	public BookingLease getPendingBookingLease(BookingLease dto) {
		try {
			Session session=this.sessionFactory.getCurrentSession();	
			Query query = session.createQuery("from BookingLease where vehicleId=:vehicleId and driverId=:driverId and journeyEndDate IS NULL AND  bookingLsStatus = 0 ");
			query.setParameter("vehicleId", dto.getVehicleId());
			query.setParameter("driverId", dto.getDriverId());
	        List<?> dList = query.list();
	        if(dList != null && !dList.isEmpty()){
	        	System.out.println("called booking--"+dto.getVehicleId());
	        	return (BookingLease) dList.get(0);
			}else{
				return null;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		} 
		
	}
	
	@Override
	public boolean updateBooking2(BookingLease dto) {
		try { logger.info("called"+dto.toString());
			Session session = this.sessionFactory.getCurrentSession();	
			Query query=session.createQuery("update BookingLease set journeyEndDate=:jryEndDt, bookingLsStatus=1, totalDistance=:dist, modifiedBy=:modifiedBy, updatedDateTime=:updatedDateTime where vehicleId=:vehicleId and driverId=:driverId and journeyEndDate IS NULL");
			//query.setDate("jryEndDt", new Date());
			query.setParameter("jryEndDt", dto.getJourneyEndDate());
			query.setParameter("vehicleId", dto.getVehicleId());
			query.setParameter("driverId", dto.getDriverId());
			query.setParameter("modifiedBy", dto.getModifiedBy());
			query.setParameter("updatedDateTime", dto.getUpdatedDateTime());
			query.setParameter("dist", dto.getTotalDistance());
			int modifications=query.executeUpdate();
			if(modifications!=0){
				logger.info("Booking Status has been Updated to end :"+dto.getJourneyEndDate()+" successfully ");
				return true;
			}else{
				logger.info("ghanta");
				return false;
			}
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	public boolean updateBookingClientDocs(BookingLease dto) {
		try {
			Session session = this.sessionFactory.getCurrentSession();	
			String cod2 = dto.getClientOrderDocUrl2();
			String cod3 = dto.getClientOrderDocUrl3();
			String cod4 = dto.getClientOrderDocUrl4();
			String sql = "update BookingLease set clientOrderNumber=:con, clientOrderDocUrl=:cod ";
			if(cod2 != null && !cod2.isEmpty()) { sql = sql + " , clientOrderDocUrl2=:cod2 "; }
			if(cod3 != null && !cod3.isEmpty()) { sql = sql + " , clientOrderDocUrl3=:cod3 "; }
			if(cod4 != null && !cod4.isEmpty()) { sql = sql + " , clientOrderDocUrl4=:cod4 "; }
			sql = sql + " where bookingLeaseId=:bookingLeaseId ";
			Query query=session.createQuery(sql);
			query.setParameter("con", dto.getClientOrderNumber());
			query.setParameter("cod", dto.getClientOrderDocUrl());
			if(cod2 != null && !cod2.isEmpty()) { query.setParameter("cod2", cod2); }
			if(cod3 != null && !cod3.isEmpty()) { query.setParameter("cod3", cod3); }
			if(cod4 != null && !cod4.isEmpty()) { query.setParameter("cod4", cod4); }
			query.setParameter("bookingLeaseId", dto.getBookingLeaseId());
			int modifications=query.executeUpdate();
			if(modifications!=0){
				logger.info("Booking has been Updated for clientOrderNumber :"+dto.getClientOrderNumber()+" successfully.");
				return true;
			}else{
				return false;
			}			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		} 
		
	}
	public boolean isBookingFromDeviceExists(String deviceBookingLeasesId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createQuery("SELECT bookingLeaseId FROM BookingLease WHERE deviceBookingLeasesId=:dblId ");
			query.setParameter("dblId", deviceBookingLeasesId);
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
	public List<Object[]> getNonClosedBookingByUserId(int userId, Date crDate){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "SELECT BL.`bookingLeaseId`, DATE(BL.`journeyStartDate`) AS bookingStartDate, BL.`driverId`,DDVM.`driverName`,DDVM.`driverstatus`,DDVM.`loginstatus`,DDVM.`vehicle_number`,DDVM.`vehicleType`, DDVM.`driver_phone_no`, IF(DR.`driverPhoto` IS NULL, 'http://www.truxapp.com/img/driver-default-pic.png', DR.`driverPhoto`) AS driverPhoto, CSM.`subName` AS clientName FROM booking_lease AS BL LEFT JOIN driver_device_vehicle_mapping AS DDVM ON BL.`driverId`=DDVM.`driver_id` LEFT JOIN `driver_registration` AS DR ON DR.`id`=DDVM.`driver_id` LEFT JOIN client_sub_master AS CSM ON BL.`companyId`=CSM.`idClientSubMaster` WHERE DATE(BL.`journeyStartDate`)='"+sdf.format(crDate)+"' && BL.`journeyEndDate` IS NULL && BL.`companyId` IN ";
			sql = sql + "	(SELECT CTUM.`clientsubid` FROM client_truxuser_mapping AS CTUM WHERE CTUM.`userid`="+userId+") ";
			List<Object[]> dataList=session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
		
	}
	public List<Object[]> getNonClosedBookingByUserId(int userId){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "SELECT BL.`bookingLeaseId`, DATE(BL.`journeyStartDate`) AS bookingStartDate, BL.`driverId`,DDVM.`driverName`,DDVM.`driverstatus`,DDVM.`loginstatus`,DDVM.`vehicle_number`,DDVM.`vehicleType`, DDVM.`driver_phone_no`, IF(DR.`driverPhoto` IS NULL, 'http://www.truxapp.com/img/driver-default-pic.png', DR.`driverPhoto`) AS driverPhoto, CSM.`subName` AS clientName FROM booking_lease AS BL LEFT JOIN driver_device_vehicle_mapping AS DDVM ON BL.`driverId`=DDVM.`driver_id` LEFT JOIN `driver_registration` AS DR ON DR.`id`=DDVM.`driver_id` LEFT JOIN client_sub_master AS CSM ON BL.`companyId`=CSM.`idClientSubMaster` WHERE BL.`journeyEndDate` IS NULL && BL.`companyId` IN ";
			sql = sql + "	(SELECT CTUM.`clientsubid` FROM client_truxuser_mapping AS CTUM WHERE CTUM.`userid`="+userId+") ";
			List<Object[]> dataList=session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
		
	}
	@Override
	public Object getAppVersion(String app_name) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "SELECT * FROM app_versions WHERE app_name='"+app_name+"' ";
			List<Object[]> dataList=session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
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
	public List<Object[]> getVehicleType() {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "SELECT id, vehicleName FROM vehicle_type";
			List<Object[]> dataList=session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	@Override
	public Object searchVehicle(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association, String body_type, Integer paging) {
		try {
			Session session = this.sessionFactory.openSession();
			List<?> dList = null;
			
			String query;
			if (body_type.equals("Open body")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
				}
			} else if (body_type.equals("Containerized")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
				}
			} else {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber, IFNULL(vr.`vehicleBody`,'-') vehicleBody, IFNULL(vr.`owner_name`,'-') ownerName, IFNULL(vr.`owner_phone_number`,'-') ownerNumber, IFNULL(CONCAT(ud.`firstname`,' ',ud.`lastname`),'-') registeredBy, IFNULL(vr.`created_date`,'-') registeredOn, IFNULL(REPLACE(REPLACE(REPLACE(vr.`vehicleStatus`,'Mapped','Leased'),'Free','On-Demand'),'Open Market','Open Market'),'-') truxAssociation, IFNULL(cm.`name`,'-') clientName, IFNULL(sr.`stand_name`,'-') standName FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " ORDER BY vr.`id` DESC LIMIT "+(paging*10)+",10";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			session.close();
			
				return dList;

		} catch (Exception er) {
			return er.toString();

		}
	}
	@Override
	public List<?> countRecord(Integer city, Integer hub, Integer cluster, Integer vehicle_type, String association,
			String body_type) {
		try {
			Session session = this.sessionFactory.openSession();
			List<?> dList = null;
			
			String query;
			if (body_type.equals("Open body")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				}
			} else if (body_type.equals("Containerized")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT vr.`vehicle_number` FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				}
			} else {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT vr.`vehicle_number` countR FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " ORDER BY vr.`id` DESC";
					else
						query = "SELECT vr.`vehicle_number` countR FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT vr.`vehicle_number` countR FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " ORDER BY vr.`id` DESC";
					else
						query = "SELECT vr.`vehicle_number` countR FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " ORDER BY vr.`id` DESC";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			session.close();
			
				return dList;

		} catch (Exception er) {
			return null;

		}
	}
	
	@Override
	public List<?> searchVehicleBody(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();
			Session session = this.sessionFactory.openSession();
			List<String> dList = null;
			
			String query;
			if (body_type.equals("Open body")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				}
			} else if (body_type.equals("Containerized")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				}
			} else {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicleBody`,'-') vehicleBody FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " ORDER BY vr.`id` DESC";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ROOT_ENTITY).list();
			session.close();
			
//			cdt.setData(dList);
			
//			for (int i = 0; i < dList.size(); i++) {
//				System.out.println(dList.get(i).toString());
//			}
			
			
			return dList;

		} catch (Exception er) {
			return null;

		}
	}
	@Override
	public List<?> searchOwnerNumber(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();
			Session session = this.sessionFactory.openSession();
			List<String> dList = null;
			
			String query;
			if (body_type.equals("Open body")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				}
			} else if (body_type.equals("Containerized")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				}
			} else {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`owner_phone_number`,'-') ownerNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " ORDER BY vr.`id` DESC";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ROOT_ENTITY).list();
			session.close();
			
//			cdt.setData(dList);
			
//			for (int i = 0; i < dList.size(); i++) {
//				System.out.println(dList.get(i).toString());
//			}
			
			
			return dList;

		} catch (Exception er) {
			return null;

		}
	}
	@Override
	public List<?> searchVehicleNumber(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();
			Session session = this.sessionFactory.openSession();
			List<String> dList = null;
			
			String query;
			if (body_type.equals("Open body")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				}
			} else if (body_type.equals("Containerized")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				}
			} else {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_number`,'-') vehicleNumber FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " ORDER BY vr.`id` DESC";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ROOT_ENTITY).list();
			session.close();
			
//			cdt.setData(dList);
			
//			for (int i = 0; i < dList.size(); i++) {
//				System.out.println(dList.get(i).toString());
//			}
			
			
			return dList;

		} catch (Exception er) {
			return null;

		}
	}
	@Override
	public List<?> searchVehicleType(Integer city, Integer hub, Integer cluster, Integer vehicle_type,
			String association, String body_type, String language) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();
			Session session = this.sessionFactory.openSession();
			List<String> dList = null;
			
			String query;
			if (body_type.equals("Open body")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Open body' ORDER BY vr.`id` DESC";
				}
			} else if (body_type.equals("Containerized")) {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " AND vr.`vehicleBody`='Containerized' ORDER BY vr.`id` DESC";
				}
			} else {
				if (cluster.equals(0)) {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vt.`id`=" + vehicle_type
								+ " AND vr.`vehicleStatus`='" + association + "'" + " ORDER BY vr.`id` DESC";
				} else {
					if (association.equals("ALL"))
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " ORDER BY vr.`id` DESC";
					else
						query = "SELECT IFNULL(vr.`vehicle_type`,'-') vehicleType FROM `vehicle_registration` AS vr LEFT JOIN `client_master` AS cm ON vr.`client_id`=cm.`idClientMaster` LEFT JOIN `stand_registration` AS sr ON vr.`stand_id`=sr.`stand_id` LEFT JOIN `vehicle_type` AS vt ON vr.`vehicle_type`=vt.`vehicleName` LEFT JOIN `userDetails` AS ud ON vr.`created_by`=ud.`id` WHERE vr.`city_id`="
								+ city + " AND vr.`hub_id`=" + hub + " AND vr.`cluster_id`=" + cluster + " AND vt.`id`="
								+ vehicle_type + " AND vr.`vehicleStatus`='" + association + "'"
								+ " ORDER BY vr.`id` DESC";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ROOT_ENTITY).list();
			session.close();
			
//			cdt.setData(dList);
			
//			for (int i = 0; i < dList.size(); i++) {
//				System.out.println(dList.get(i).toString());
//			}
			
			
			return dList;

		} catch (Exception er) {
			return null;

		}
	}

}
