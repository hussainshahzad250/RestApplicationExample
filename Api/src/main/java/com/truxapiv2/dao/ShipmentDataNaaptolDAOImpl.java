package com.truxapiv2.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.DriverDeviceVehicleMapping;
import com.truxapiv2.model.Person;
import com.truxapiv2.model.ShipmentDataNaaptol;

@Repository
public class ShipmentDataNaaptolDAOImpl implements ShipmentDataNaaptolDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ShipmentDataNaaptolDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public ShipmentDataNaaptol getShipmentDataByAWBNumber(String awbNumber) {
		try {
		Session session=sessionFactory.openSession();
		 Query query = session.createQuery("from ShipmentDataNaaptol where awbNumber = :awbNumber ");
	     query.setParameter("awbNumber", awbNumber);
	        List<?> dList = query.list();
	        if(dList != null && !dList.isEmpty()){
	        	ShipmentDataNaaptol D = (ShipmentDataNaaptol)dList.get(0);
	        	return D;
	        } else{
	        	ShipmentDataNaaptol sdn = new ShipmentDataNaaptol();
	        	sdn.setAwbNumber(awbNumber);
	        	session.save(sdn);
	        	return sdn;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}		
	}
	@Override
	public boolean updateShipmentDataByAWBNumberNDR(ShipmentDataNaaptol sdn) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			
			Query query=session.createQuery("update ShipmentDataNaaptol set ndr=:ndr  where awbNumber=:awbNumber ");
			query.setParameter("awbNumber", sdn.getAwbNumber().trim());
			query.setParameter("ndr", sdn.getNdr().trim());
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
