package com.truxapiv2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import com.truxapiv2.model.ContactUs;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.SubscriptionEmail;

public class GlobalDAOImpl implements GlobalDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker subscription(SubscriptionEmail subscriptionEmail) {
		try {
		Session sessions = sessionFactory.openSession();
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		
			Transaction tx = sessions.beginTransaction();
			sessions.saveOrUpdate(subscriptionEmail);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(SubscriptionEmail.class)
					.setProjection(Projections.max("id"));
			List<SubscriptionEmail> cmdList = sessions.createCriteria(SubscriptionEmail.class)
					.add(Property.forName("id").eq(maxId)).list();
			sessions.clear();
			sessions.close();
			if (cmdList != null && cmdList.size() > 0) {
				SubscriptionEmail dts = cmdList.get(0);
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Subscribed");
				cdt.setSubscriptionEmail(dts);
				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Subscription not successful");
				return cdt;
			}
		} catch (Exception er) {

			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker contactUs(ContactUs contactUs) {
		try {
			Session sessions = sessionFactory.openSession();
			ControllerDAOTracker cdt = new ControllerDAOTracker();
			
				Transaction tx = sessions.beginTransaction();
				sessions.saveOrUpdate(contactUs);
				tx.commit();
				DetachedCriteria maxId = DetachedCriteria.forClass(ContactUs.class)
						.setProjection(Projections.max("id"));
				List<ContactUs> cmdList = sessions.createCriteria(ContactUs.class)
						.add(Property.forName("id").eq(maxId)).list();
				sessions.clear();
				sessions.close();
				if (cmdList != null && cmdList.size() > 0) {
					ContactUs dts = cmdList.get(0);
					cdt.setSuccess(true);
					cdt.setErrorCode("100");
					cdt.setErrorMessage("Request saved successfully.");
					cdt.setContactUs(dts);
					return cdt;
				} else {
					cdt.setSuccess(false);
					cdt.setErrorCode("101");
					cdt.setErrorMessage("Request not saved successfully.");
					return cdt;
				}
			} catch (Exception er) {

				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
	}

}
