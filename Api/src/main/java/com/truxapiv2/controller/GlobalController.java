package com.truxapiv2.controller;

import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.truxapiv2.model.ContactUs;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.RestResponce;
import com.truxapiv2.model.SubscriptionEmail;
import com.truxapiv2.model.TransporterClientOrderMapping;
import com.truxapiv2.model.TransporterClientOrders;
import com.truxapiv2.service.GlobalService;

@Controller
@RequestMapping(value = "/global")
public class GlobalController {

	@Autowired
	private GlobalService globalService;

	/** FOR SUBSCRIPTION EMAIL */
	@ResponseBody
	@RequestMapping(value = "/subscription", method = RequestMethod.POST)
	public RestResponce subscription(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String email) throws ParseException {
		try {
			RestResponce restResponce = new RestResponce();
			SubscriptionEmail subscriptionEmail = new SubscriptionEmail();
			subscriptionEmail.setEmail(email);
			subscriptionEmail.setProcess("corp-www");
			subscriptionEmail.setIsSubscribe(1);
			subscriptionEmail.setCreatedDate(new Date());
			ControllerDAOTracker TR = globalService.subscription(subscriptionEmail);
			if (TR.isSuccess()) {
				restResponce.setErrorCode(TR.getErrorCode());
				restResponce.setErrorMesaage(TR.getErrorMessage());
				restResponce.setData(TR.getTransporterClientOrderMapping());
				return restResponce;
			} else {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage(TR.getErrorMessage());
				return restResponce;
			}
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
	}

	/** FOR CONTACT US */
	@ResponseBody
	@RequestMapping(value = "/contactUs", method = RequestMethod.POST)
	public RestResponce contactUs(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String requestFor, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String email, @RequestParam String message) throws ParseException {
		try {
			RestResponce restResponce = new RestResponce();
			ContactUs contactUs = new ContactUs();
			contactUs.setRequest(requestFor);
			contactUs.setFirstName(firstName);
			contactUs.setLastName(lastName);
			contactUs.setEmail(email);
			contactUs.setMessage(message);
			contactUs.setCreatedDate(new Date());
			ControllerDAOTracker TR = globalService.contactUs(contactUs);
			if (TR.isSuccess()) {
				restResponce.setErrorCode(TR.getErrorCode());
				restResponce.setErrorMesaage(TR.getErrorMessage());
				restResponce.setData(TR.getTransporterClientOrderMapping());
				return restResponce;
			} else {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage(TR.getErrorMessage());
				return restResponce;
			}
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
	}
}
