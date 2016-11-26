package com.truxapiv2.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

//import com.datastax.driver.core.Cluster;

import com.truxapiv2.model.DriverPartPayment;
@Repository
public class DriverPartPaymentMappingDAOImpl implements DriverPartPaymentMappingDAO{
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	public boolean insertDriverPartPayment(DriverPartPayment dpp) {
		/*com.datastax.driver.core.Session cassandraSession = null;
		
	 	Cluster.Builder cassandraCluster = Cluster.builder().addContactPoints("54.169.184.149").withPort(9042).withCredentials("cassandra", "password@123");
	    cassandraSession = cassandraCluster.build().connect("hb");
		try {
			 
			String driver_mobile  = dpp.getDriverMobile();
			String amount_paid    = dpp.getAmountPaid();
			String vehicle_number = dpp.getVehicleNumber();
			String comment        = dpp.getComment();
			String date_paid_on   = dpp.getDatePaidOn();
			String paid_by        = dpp.getPaidBy();
			
			String query1 = "INSERT INTO driver_part_payment (driver_mobile, amount_paid, vehicle_number, comment, date_paid_on, paid_by) VALUES("+driver_mobile+", '"+amount_paid+"', "+vehicle_number+",'"+comment+",'"+date_paid_on+",'"+paid_by+"');" ;
			System.out.println(query1);
		    cassandraSession.execute(query1);
		    cassandraSession.close();
		    return true;
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());			
		    cassandraSession.close();
			return false;
			
		}	*/
		
		
		Session session = this.sessionFactory.getCurrentSession();
		session.save(dpp);
		
		return true;
		
	}

}
