package com.truxapiv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truxapiv2.dao.GlobalDAO;
import com.truxapiv2.model.ContactUs;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.SubscriptionEmail;

@Service
public class GlobalService {
	
	@Autowired
	private GlobalDAO globalDAO;

	public ControllerDAOTracker subscription(SubscriptionEmail subscriptionEmail) {
		return this.globalDAO.subscription(subscriptionEmail);
	}

	public ControllerDAOTracker contactUs(ContactUs contactUs) {
		return this.globalDAO.contactUs(contactUs);
	}

}
