package com.truxapiv2.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.ClientBookingDoc;

@Repository
public class ClientBookingDocDAOImpl implements ClientBookingDocDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientBookingDocDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Override
	public int addClientBookingDoc(ClientBookingDoc cbd) {
		int instAutoId =0 ;
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(cbd);
		logger.info("Doc saved successfully, Doc Details="+cbd);
		return Integer.parseInt(cbd.getId());
		
	}
	

}
