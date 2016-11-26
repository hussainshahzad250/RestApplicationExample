package com.truxapiv2.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.Driver;

@Repository
public class DriverDAOImpl implements DriverDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DriverDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public Driver getDriverByPhone(String phone) {
		try {
			Session session=sessionFactory.openSession();
			 Query query = session.createQuery("from Driver where phoneNumber = :phone ");
		     query.setParameter("phone", phone);
		     List<?> dList = query.list();
		     if(dList != null && !dList.isEmpty()){
		        Driver Dr = (Driver)dList.get(0);	       
		        logger.info("Driver loaded successfully fron Driver Model, Driver  details="+Dr);
		        session.close();
		        return Dr;
		     } else{
		    	session.close();
		        return null;
		     }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		} 
	}
	@Override
	public Driver getDriverById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Driver d = (Driver) session.load(Driver.class, new Integer(id));
		logger.info("Driver loaded successfully, Driver details="+d);
		return d;
	}


}
