package com.truxapiv2.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.RestResponce;
import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.service.TransporterBankDetailsService;

@Controller
@RequestMapping(value = "/bankDetails")
public class TransporterBankDetailsController {

	@Autowired
	private TransporterBankDetailsService transporterBankDetailsService;

	/* This Method is create for saving the Bank Details of Transporter */
	@ResponseBody
	@RequestMapping(value = "/saveBankDetails", method = RequestMethod.POST)
	public RestResponce saveBankDetails(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer trsptrRegistrationId, @RequestParam String accountHolderName,
			@RequestParam String bankName, @RequestParam String ifscCode, @RequestParam String accountNumber,
			@RequestParam Integer createdBy) {
		try {
			RestResponce restResponce = new RestResponce();

			TransporterBankDetails tr = new TransporterBankDetails();
			tr.setTrsptrRegistrationId(trsptrRegistrationId);
			tr.setAccountHolderName(accountHolderName);
			tr.setBankName(bankName);
			tr.setAccountNumber(accountNumber);
			tr.setIfscCode(ifscCode);
			tr.setAccountStatus("New");
			tr.setCreatedBy(createdBy);
			tr.setCreatedOn(new Date());
			tr.setModifiedBy(createdBy);
			tr.setModifiedOn(new Date());
			tr.setIsActive(1);

			ControllerDAOTracker saveBack;

			ControllerDAOTracker TR = transporterBankDetailsService.getTransporterRegistrationId(trsptrRegistrationId);

			if (TR.isSuccess()) {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage("Bank Details already registered");
				return restResponce;
			} else {

				saveBack = transporterBankDetailsService.saveTransporterBankDetails(tr);

				if (saveBack.getErrorCode().equals("100")) {
					restResponce.setErrorCode(saveBack.getErrorCode());
					restResponce.setErrorMesaage(saveBack.getErrorMessage());
					System.out.println("Bank Details Saved SuccessFully");
					restResponce.setData(saveBack.getData());
				} else if (saveBack.getErrorCode().equals("200")) {
					restResponce.setErrorCode(saveBack.getErrorCode());
					restResponce.setErrorMesaage(saveBack.getErrorMessage());
					restResponce.setData(saveBack.getData());
				} else {
					restResponce.setErrorCode(saveBack.getErrorCode());
					restResponce.setErrorMesaage(saveBack.getErrorMessage());
					restResponce.setData(saveBack.getData());
				}

				return restResponce;
			}
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/verifyAccountByTransporterRegistrationId", method = RequestMethod.POST)
	public RestResponce verifyAccountByTransporterRegistrationId(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Integer trsptrRegistrationId,
			@RequestParam Double varificationAmount) {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterBankDetailsService
					.getTransporterbankDetailsByTransporterRegistrationId(trsptrRegistrationId);

			TransporterBankDetails bankDetails = cdt.getTransporterBankDetails();

			Double amount = bankDetails.getVarificationAmount();
			if (varificationAmount.equals(amount)) {

				ControllerDAOTracker cdte = transporterBankDetailsService
						.updateVerificationStatus(bankDetails.getTrsptrRegistrationId(), "Verified");

				if (cdte.isSuccess()) {
					restResponce.setErrorCode("100");
					restResponce.setErrorMesaage("Your Account is Verified successfully");
				}

			} else {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage("Verification amount incorrect.");
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}
}
