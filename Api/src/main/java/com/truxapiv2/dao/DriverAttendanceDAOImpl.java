package com.truxapiv2.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.DriverAttendance;
import com.truxapiv2.model.Person;

@Repository
public class DriverAttendanceDAOImpl implements DriverAttendanceDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DriverAttendanceDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	
	public int saveDriverAttendance(DriverAttendance dto) {
		try {
			int getLogoutableId = this.getLogoutableId(dto.getDriverId(), new Date());
			if(dto.getPunchIngStatus()==1){
				if(dto.getCheckPending()==true){
					if(getLogoutableId!=0){ 
						return 5; // Punch Out Pending from last login
					}
				}
				System.out.println("Attendance IN ?? "+this.isDriverLogedInByDate(dto.getDriverId(), new Date()));
				if(this.isDriverLogedInByDate(dto.getDriverId(), new Date())==0){			
					dto.setFromApp(1);
					if(dto.getClientSubId()==null || dto.getDriverId()==null){
					Object[] vehicleDetail = this.getVehicleDetailByDriverId(dto.getDriverId());
        			if(vehicleDetail!=null){
        				if(vehicleDetail[0]!=null){
        					dto.setVehicleNumber(vehicleDetail[0].toString());        					
        				}
        				if(vehicleDetail[1]!=null){
        					dto.setClientSubId(Integer.parseInt(vehicleDetail[1].toString()));
        				}
        			}
					}
					Session session = this.sessionFactory.getCurrentSession();
					int login_id = (Integer) session.save(dto);
					System.out.println("Attendance saved successfully, Attendance Details="+dto);
					this.updateLoginStatusInDDVM(dto.getPunchIngStatus(), dto.getDriverId(), new Date(), login_id);
					return 1; // logged in 
					
				}else{
					return 2; // already logged in for the day
				}
			}
			if(dto.getPunchIngStatus()==0){
				System.out.println("Attendance OUT ?? "+this.getLogoutableId(dto.getDriverId(), new Date()));
				if(getLogoutableId!=0){						
					
					DriverAttendance daa = this.getDriverAttendanceById(getLogoutableId);
					daa.setPunchOut(dto.getPunchOut());
					daa.setModifiedDate(new Date());
					daa.setModifiedBy(dto.getModifiedBy());
					if(dto.getClosingKilometer()!=null && dto.getClosingKilometer()!=0) daa.setClosingKilometer(dto.getClosingKilometer());
					Session session = this.sessionFactory.getCurrentSession();
					session.update(daa);
					this.updateLoginStatusInDDVM(dto.getPunchIngStatus(), dto.getDriverId(), dto.getPunchOut(), 0);
					System.out.println("Attendance updated successfully, Attendance Details="+dto);	
					return 3; // logged out
					
				}else{
					
					return 4; // Already logged out for the day
				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); return 0;
		}
		return 0;	
		
	}
	
	public int isDriverLogedInByDate(int driverId, Date current_date) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String current_date_str = sdf.format(current_date);
			List<?> dataList=session.createSQLQuery("SELECT id FROM driver_attendance_leased_vehicles WHERE punchIn IS NOT NULL AND driverId='"+driverId+"' AND DATE_FORMAT(punchIn,'%Y-%m-%d') ='"+current_date_str+"' && from_app=1 ").list();
	     
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return (Integer) dataList.get(0);
	        } else{
	        	return 0;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return 0;
		}
	}
	public int getLogoutableId(int driverId, Date current_date) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String current_date_str = sdf.format(current_date);
			List<?> dataList=session.createSQLQuery("SELECT id FROM driver_attendance_leased_vehicles WHERE punchOut IS NULL AND driverId='"+driverId+"'  && from_app=1 ORDER BY id DESC LIMIT 1 ").list();
	     
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return (Integer) dataList.get(0);
	        } else{
	        	return 0;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return 0;
		}
	}	
	public DriverAttendance getDriverAttendanceById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		DriverAttendance p = (DriverAttendance) session.load(DriverAttendance.class, new Integer(id));
		return p;
	}


	@Override
	public boolean updateStartKM(int driver_id, Date curdate, int km) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String current_date_str2 = sdf2.format(curdate);
			Query query=session.createSQLQuery("update driver_attendance_leased_vehicles set openingkm="+km+" where driverId="+driver_id+" AND punchOut IS NULL && from_app=1 ORDER BY id DESC LIMIT 1");
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
	public boolean updateEndKM(int driver_id, Date curdate, int km) {
		try {
			int getLogoutableId = this.getLogoutableId(driver_id, curdate);
			DriverAttendance daa = this.getDriverAttendanceById(getLogoutableId);
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createQuery("update DriverAttendance set closingKilometer=:clKM where id=:id ");
			query.setInteger("id", daa.getId());
			query.setInteger("clKM", km);
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
	public boolean updateLoginStatusInDDVM(int flag, int driver_id, Date crDate, int login_id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query;
			if(flag==0){
				query=session.createQuery("update DriverDeviceVehicleMapping set loginId=:login_id, lastLogoutTime=:lastLoginLogoutTime, loginstatus=:flag where driverId=:driver_id");				
			}
			else{
				query=session.createQuery("update DriverDeviceVehicleMapping set loginId=:login_id, lastLoginTime=:lastLoginLogoutTime, loginstatus=:flag where driverId=:driver_id");
			}
			
			query.setInteger("flag", flag);
			query.setInteger("driver_id", driver_id);
			query.setInteger("login_id", login_id);
			query.setParameter("lastLoginLogoutTime", crDate);
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
	public Object[] getVehicleDetailByDriverId(int driverId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<Object[]> dataList=session.createSQLQuery("SELECT vehicle_number, subclient_id FROM vehicle_registration WHERE driver_id="+driverId+" ").list();
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
	public List<Object[]> getNonClosedPunchIns(int userId, Date crDate){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "SELECT CURRENT_DATE() AS currentServerDate, DALV.`id`, TIME(DALV.`punchIn`) AS last_login_time, DATE(DALV.`punchIn`) AS punchInDate, DALV.`driverId`, DDVM.`driverName`, DDVM.`vehicle_number`,DDVM.`vehicleType`, DDVM.`driver_phone_no`, IF(DR.`driverPhoto` IS NULL, 'http://www.truxapp.com/img/driver-default-pic.png', DR.`driverPhoto`) AS driverPhoto, CSM.`subName` AS clientName ";
			sql = sql + " , (SELECT COUNT(*) FROM `booking_lease` AS BL WHERE DATE(BL.`journeyStartDate`)= DATE(DALV.`punchIn`) AND BL.`driverId`=DALV.`driverId` AND`journeyEndDate` IS NULL) AS hasJourney, (SELECT CONCAT(DATE(BL.`journeyStartDate`),' ', TIME(BL.`journeyStartDate`)) FROM `booking_lease` AS BL WHERE DATE(BL.`journeyStartDate`)= DATE(DALV.`punchIn`) AND BL.`driverId`=DALV.`driverId` AND`journeyEndDate` IS NULL) AS hasJourneyDate  FROM `driver_attendance_leased_vehicles` AS DALV LEFT JOIN driver_device_vehicle_mapping AS DDVM ON (DALV.`driverId`=DDVM.`driver_id` AND DALV.`vehicle_number`=DDVM.`vehicle_number`) LEFT JOIN `driver_registration` AS DR ON DR.`id`=DDVM.`driver_id` LEFT JOIN client_sub_master AS CSM ON DALV.`clientsubid`=CSM.`idClientSubMaster` WHERE 1 ";
			if(crDate != null){ sql = sql + " && DATE(DALV.`punchIn`)='"+sdf.format(crDate)+"' "; }
			sql = sql + " && DALV.`punchOut` IS NULL && DALV.`from_app`=1 && DALV.`clientsubid` IN  (SELECT CTUM.`clientsubid` FROM client_truxuser_mapping AS CTUM WHERE CTUM.`userid`="+userId+") ORDER BY DALV.`id` DESC, DDVM.`driverName` ASC ";
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
}
