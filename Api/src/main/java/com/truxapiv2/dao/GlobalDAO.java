package com.truxapiv2.dao;

import com.truxapiv2.model.ContactUs;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.SubscriptionEmail;

public interface GlobalDAO {

	ControllerDAOTracker subscription(SubscriptionEmail subscriptionEmail);

	ControllerDAOTracker contactUs(ContactUs contactUs);

}
