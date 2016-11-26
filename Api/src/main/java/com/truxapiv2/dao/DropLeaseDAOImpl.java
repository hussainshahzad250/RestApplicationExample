package com.truxapiv2.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.DropLease;

@Repository
public class DropLeaseDAOImpl implements DropLeaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DropLeaseDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public int saveDropLease(DropLease dto) { 
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
				System.out.println(er.getMessage().toString());
				return bkId;
			}
		session.close();
		return bkId;
	}
	public boolean isDropFromDeviceExists(String deviceDropLeasesId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createQuery("SELECT deviceDropLeasesId FROM DropLease WHERE deviceDropLeasesId=:dblId ");
			query.setParameter("dblId", deviceDropLeasesId);
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
}
