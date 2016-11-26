package com.truxapiv2.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.Driver;
import com.truxapiv2.model.DriverDeviceVehicleMapping;
import com.truxapiv2.model.DriverLoginHistory;

@Repository
public class DriverLoginHistoryDAOImpl implements DriverLoginHistoryDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DriverLoginHistoryDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	
	public int saveDriverLoginHistory(DriverLoginHistory dto) {
		
		if(dto.getPunchIngStatus()==1){
			if(!this.isDriverLogedInByDate(dto.getDriverMobile(), new Date())){			
				Session session=sessionFactory.openSession();	
				Transaction tx=session.beginTransaction();
				int loginId = 1;
				try{
				
				loginId = (Integer) session.save(dto);		
				tx.commit();
				if(loginId!=0){
					logger.info("Login Data Saved loginId:"+loginId);
					session.close();
					return loginId;
				}
				}catch(Exception er){
					System.out.println(er.getMessage().toString()); 
					session.close();
					return loginId;
				}
				
				//return loginId;
			}else{
				return 0;
			}
		}
		if(dto.getPunchIngStatus()==0){
			if(!this.isDriverLogedOutByDate(dto.getDriverMobile(), new Date())){			
				int loginId = 1;
				Session session=sessionFactory.openSession();	
				Transaction tx=session.beginTransaction();
				try{					
					loginId = (Integer) session.save(dto);		
					tx.commit();
					if(loginId!=0){
					logger.info("Login Data Saved loginId:"+loginId);
					session.close();
					return loginId;
					}
				}catch(Exception er){
					System.out.println(er.getMessage().toString());
					session.close();
					return loginId;
				}			
				
			}else{
				
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String current_date_str = sdf.format(new Date());
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					String current_date_str2 = sdf2.format(new Date());
					Session sessionC = this.sessionFactory.getCurrentSession();
					Query query=sessionC.createSQLQuery("update driver_loginhistory set datetime='"+current_date_str+"' where driver_id='"+dto.getDriverMobile()+"' AND punch_status=0 AND DATE(DATETIME)='"+current_date_str2+"'");
					//query.setDate("datetimeL", new Date());
					//query.setString("phone", dto.getDriverMobile());
					int modifications=query.executeUpdate();
					if(modifications!=0){
						return 1;
					}else{
						return 0;
					}
					
				}catch(Exception er){
					System.out.println(er.getMessage().toString()); return 0;
				} 
			}
		}
		return 0;
		
	}
	
	public boolean isDriverLogedInByDate(String phone, Date current_date) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String current_date_str = sdf.format(current_date);
			List<?> dataList=session.createSQLQuery("SELECT login_id FROM driver_loginhistory WHERE punch_status=1 AND driver_id='"+phone+"' AND DATE_FORMAT(DATETIME,'%Y-%m-%d') ='"+current_date_str+"'").list();
	     
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return true;
	        } else{
	        	return false;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		}
	}
	public boolean isDriverLogedOutByDate(String phone, Date current_date) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String current_date_str = sdf.format(current_date);
			List<?> dataList=session.createSQLQuery("SELECT login_id FROM driver_loginhistory WHERE punch_status=0 AND driver_id='"+phone+"' AND DATE_FORMAT(DATETIME,'%Y-%m-%d') ='"+current_date_str+"'").list();
	     
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
	public DriverLoginHistory getDriverLoginHistory(DriverLoginHistory dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DriverLoginHistory> getDriverLoginHistoryList(DriverLoginHistory dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
