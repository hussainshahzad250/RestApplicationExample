package com.truxapiv2.controller;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truxapiv2.model.ChangePassword;
import com.truxapiv2.model.CommunicationEmail;
import com.truxapiv2.model.CommunicationSMS;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.RestResponce;
import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.model.TransporterClientOrderMapping;
import com.truxapiv2.model.TransporterClientOrders;
import com.truxapiv2.model.TransporterFreightChart;
import com.truxapiv2.model.TransporterLoginHistory;
import com.truxapiv2.model.TransporterOrderFollowUp;
import com.truxapiv2.model.TransporterRegistration;
import com.truxapiv2.model.TransporterVehicleRegistration;
import com.truxapiv2.service.TransporterBankDetailsService;
import com.truxapiv2.service.TransporterRegistrationService;
import com.truxapiv2.util.TruxUtils;

@Controller
@RequestMapping(value = "/transport")
public class TransporterController {

	@Autowired
	private TransporterRegistrationService transporterRegistrationService;
	@Autowired
	private TransporterBankDetailsService transporterBankDetailsService;

	/** TO SAVE TRANSPORTER */
	@ResponseBody
	@RequestMapping(value = "/saveTransporterRegistration", method = RequestMethod.POST)
	public RestResponce saveTransporterRegistration(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String transportCompanyName, @RequestParam String contactPersonName,
			@RequestParam String mobileNumber, @RequestParam(required = false) String email, @RequestParam Integer city,
			@RequestParam Integer state, @RequestParam Integer pincode, @RequestParam String vehicleCategory,
			@RequestParam Integer createdBy, @RequestParam String profileImage, @RequestParam String panNumber)
					throws ParseException {
		try {
			RestResponce restResponce = new RestResponce();

			TransporterRegistration tr = new TransporterRegistration();

			tr.setTransporterCompanyName(transportCompanyName);
			tr.setContactPersonName(contactPersonName);
			tr.setMobileNumber(mobileNumber.replace(" ", "").trim());
			tr.setPassword(TruxUtils.getRandomNumber(100000, 999999));
			tr.setEmail(email);
			tr.setProfileImage(profileImage);
			tr.setPanNumber(panNumber);
			tr.setCity(city);
			tr.setState(state);
			tr.setPincode(pincode);
			tr.setVehicleCategory(vehicleCategory);
			tr.setCreatedBy(createdBy);
			tr.setCreatedOn(new Date());
			// tr.setModifiedBy(createdBy);
			// tr.setModifiedOn(new Date());
			// tr.setGcmId("987654321");
			tr.setIsActive(1);

			ControllerDAOTracker saveBack;

			ControllerDAOTracker TR = transporterRegistrationService
					.getTransporterByMobile(mobileNumber.replace(" ", "").trim());

			if (TR.isSuccess()) {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage("Transporter already registered");
				return restResponce;
			} else {

				saveBack = transporterRegistrationService.saveTransporterRegistration(tr);

				if (saveBack.getErrorCode().equals("100")) {
					restResponce.setErrorCode(saveBack.getErrorCode());
					restResponce.setErrorMesaage(saveBack.getErrorMessage());
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

	/** FOR SEARCHING TRANSPORTER/GET TRANSPORTER BY MOBILE NUMBER */
	@ResponseBody
	@RequestMapping("/getTransporterByMobile/{mobileNumber}")
	public RestResponce getTransporterByMobile(@PathVariable("mobileNumber") String mobileNumber) {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterRegistrationService.getTransporterDetails(mobileNumber);

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getData());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;

		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** UPDATE REGISTERED TRANSPORTER */
	@ResponseBody
	@RequestMapping(value = "/updateTransporterRegistration", method = RequestMethod.POST)
	public RestResponce updateTransporterRegistration(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer id, @RequestParam String transportCompanyName, @RequestParam String contactPersonName,
			@RequestParam String mobileNumber, @RequestParam(required = false) String email, @RequestParam Integer city,
			@RequestParam Integer state, @RequestParam Integer pincode, @RequestParam String vehicleCategory,
			@RequestParam Integer createdBy, @RequestParam String profileImage, @RequestParam String panNumber)
					throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			TransporterRegistration tr = new TransporterRegistration();
			tr.setId(id);
			tr.setTransporterCompanyName(transportCompanyName);
			tr.setContactPersonName(contactPersonName);
			tr.setMobileNumber(mobileNumber.replace(" ", "").trim());
			// tr.setPassword(TruxUtils.getRandomNumber(100000, 999999));
			tr.setEmail(email);
			tr.setCity(city);
			tr.setState(state);
			tr.setPincode(pincode);
			tr.setVehicleCategory(vehicleCategory);
			tr.setCreatedBy(createdBy);
			tr.setCreatedOn(new Date());
			tr.setModifiedBy(createdBy);
			tr.setModifiedOn(new Date());
			tr.setIsActive(1);

			tr.setPanNumber(panNumber);
			tr.setProfileImage(profileImage);

			ControllerDAOTracker saveBack;

			saveBack = transporterRegistrationService.updateTransporterRegistration(tr);

			if (saveBack.getErrorCode().equals("100")) {
				restResponce.setErrorCode(saveBack.getErrorCode());
				restResponce.setErrorMesaage(saveBack.getErrorMessage());
				restResponce.setData(saveBack.getData());
			} else if (saveBack.getErrorCode().equals("200")) {
				restResponce.setErrorCode(saveBack.getErrorCode());
				restResponce.setErrorMesaage(saveBack.getErrorMessage());
				restResponce.setData(saveBack.getData());
				restResponce.setData(saveBack.getTransporterRegistration());
			} else {
				restResponce.setErrorCode(saveBack.getErrorCode());
				restResponce.setErrorMesaage(saveBack.getErrorMessage());
				restResponce.setData(saveBack.getData());
			}
			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
	}

	/** TRANSPORTER APP LOGIN/LOGOUT */
	@ResponseBody
	@RequestMapping(value = "/getLoginAuthentication", method = RequestMethod.GET)
	public RestResponce getLoginAuthentication(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String mobileNumber, @RequestParam(required = false) Integer password,
			@RequestParam Integer loginStatus, @RequestParam String gcmId,
			@RequestParam(required = false) String deviceMAC,
			@RequestParam(required = false) String currentApkVersion) {
		try {

			RestResponce restResponce = new RestResponce();

			TransporterRegistration tr = new TransporterRegistration();
			tr.setMobileNumber(mobileNumber);
			tr.setPassword(password);
			tr.setGcmId(gcmId);

			if (loginStatus.equals(0)) {
				ControllerDAOTracker transporterDetail = transporterRegistrationService
						.getTransporterByMobile(mobileNumber);

				TransporterRegistration auth = transporterDetail.getTransporterRegistration();

				TransporterLoginHistory tlh = new TransporterLoginHistory();

				tlh.setTrsptrRegistrationId(auth.getId());
				tlh.setLoginDate(new Date());
				tlh.setDeviceMac(deviceMAC);
				tlh.setIsLogin(loginStatus);
				tlh.setIsSuccess(1);
				tlh.setCurrentAppVersion(currentApkVersion);

				ControllerDAOTracker cdt = transporterRegistrationService.saveTransporterLoginHistory(tlh);

				if (cdt.isSuccess()) {
					restResponce.setErrorCode("100");
					restResponce.setErrorMesaage("Logged out successfully.");

					return restResponce;
				} else {
					restResponce.setErrorCode(cdt.getErrorCode());
					restResponce.setErrorMesaage(cdt.getErrorMessage());

					return restResponce;
				}
			}

			ControllerDAOTracker transporterDetail = transporterRegistrationService.getUserDetailsWithGcmId(tr);

			if (transporterDetail.isSuccess()) {
				if (mobileNumber != null && password != null && !mobileNumber.isEmpty()) {
					if (transporterDetail != null && transporterDetail.isSuccess()) {

						TransporterRegistration auth = transporterDetail.getTransporterRegistration();

						if (auth.getPassword().equals(password)) {
							if (auth.getIsActive().equals(1)) {

								TransporterLoginHistory tlh = new TransporterLoginHistory();

								tlh.setTrsptrRegistrationId(auth.getId());
								tlh.setLoginDate(new Date());
								tlh.setDeviceMac(deviceMAC);
								tlh.setIsLogin(loginStatus);
								tlh.setIsSuccess(1);
								tlh.setCurrentAppVersion(currentApkVersion);

								ControllerDAOTracker cdt = transporterRegistrationService
										.saveTransporterLoginHistory(tlh);

								TransporterRegistration trr = new TransporterRegistration();

								if (cdt.isSuccess()) {
									trr.setId(auth.getId());
									trr.setTransporterCompanyName(auth.getTransporterCompanyName());
									trr.setContactPersonName(auth.getContactPersonName());
									trr.setMobileNumber(auth.getMobileNumber());
									trr.setEmail(auth.getEmail());
									trr.setVehicleCategory(auth.getVehicleCategory());
									trr.setCity(auth.getCity());
									trr.setState(auth.getState());
									trr.setPincode(auth.getPincode());
									trr.setCreatedBy(auth.getCreatedBy());
									trr.setCreatedOn(auth.getCreatedOn());
									trr.setModifiedBy(auth.getModifiedBy());
									trr.setModifiedOn(auth.getModifiedOn());
									trr.setIsActive(auth.getIsActive());
									trr.setGcmId(auth.getGcmId());
									trr.setCsc(auth.getCsc());

									restResponce.setErrorCode(cdt.getErrorCode());
									restResponce.setErrorMesaage("Logged in successfully.");
									restResponce.setData(trr);

									return restResponce;
								} else {
									restResponce.setErrorCode(cdt.getErrorCode());
									restResponce.setErrorMesaage(cdt.getErrorMessage());

									return restResponce;
								}
							} else {
								restResponce.setErrorCode("101");
								restResponce.setErrorMesaage("Your account is locked. Please call administrator.");

								return restResponce;
							}
						} else {
							if (auth.getIsActive().equals(1)) {

								TransporterLoginHistory tlh = new TransporterLoginHistory();

								tlh.setTrsptrRegistrationId(auth.getId());
								tlh.setLoginDate(new Date());
								tlh.setDeviceMac(deviceMAC);
								tlh.setIsLogin(0);
								tlh.setIsSuccess(0);
								tlh.setCurrentAppVersion(currentApkVersion);

								ControllerDAOTracker cdt = transporterRegistrationService
										.saveTransporterLoginHistory(tlh);

								if (cdt.isSuccess()) {
									restResponce.setErrorCode("101");
									restResponce.setErrorMesaage("Invalid Credentials.");

									return restResponce;
								} else {
									restResponce.setErrorCode(cdt.getErrorCode());
									restResponce.setErrorMesaage(cdt.getErrorMessage());

									return restResponce;
								}
							} else {
								restResponce.setErrorCode("101");
								restResponce.setErrorMesaage("Your account is locked. Please call administrator.");

								return restResponce;
							}
						}
					} else {

						restResponce.setErrorCode("101");
						restResponce.setErrorMesaage("Transporter not registered.");

						return restResponce;
					}

				} else {
					restResponce.setErrorCode("101");
					restResponce.setErrorMesaage("Credentials missing.");

					return restResponce;
				}
			} else {
				TransporterLoginHistory tlh = new TransporterLoginHistory();

				ControllerDAOTracker cdt = transporterRegistrationService.getTransporterByMobile(mobileNumber);

				if (cdt.isSuccess()) {

					TransporterRegistration auth = cdt.getTransporterRegistration();

					tlh.setTrsptrRegistrationId(auth.getId());
					tlh.setLoginDate(new Date());
					tlh.setDeviceMac(deviceMAC);
					tlh.setIsLogin(0);
					tlh.setIsSuccess(0);
					tlh.setCurrentAppVersion(currentApkVersion);

					ControllerDAOTracker cdt1 = transporterRegistrationService.saveTransporterLoginHistory(tlh);

					if (cdt1.isSuccess()) {
						restResponce.setErrorCode("101");
						restResponce.setErrorMesaage("Invalid Credentials.");

						return restResponce;
					} else {
						restResponce.setErrorCode(cdt1.getErrorCode());
						restResponce.setErrorMesaage(cdt1.getErrorMessage());

						return restResponce;
					}
				} else {
					restResponce.setErrorCode("101");
					restResponce.setErrorMesaage("Transporter not registered.");

					return restResponce;
				}
			}

		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
	}

	/** ADDING TRUCKS TRANSPORTERS ARE HAVING */
	@ResponseBody
	@RequestMapping(value = "/addTruck", method = RequestMethod.POST)
	public RestResponce addTruck(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer trsptrRegistrationId, @RequestParam String vehicleNumber,
			@RequestParam Integer vehicleTypeId, @RequestParam String vehicleBody, @RequestParam String modelYear,
			@RequestParam Date insuranceExpiry, @RequestParam String status, @RequestParam Integer createdBy)
					throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			if (trsptrRegistrationId == null || vehicleNumber == null || vehicleTypeId == null || vehicleBody == null
					|| modelYear == null || insuranceExpiry == null || status == null || createdBy == null) {

				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage("Parameters missing");

				return restResponce;
			} else {
				TransporterVehicleRegistration tvr = new TransporterVehicleRegistration();

				tvr.setTrsptrRegistrationId(trsptrRegistrationId);
				vehicleNumber = vehicleNumber.replace(" ", "");
				tvr.setVehicleNumber(vehicleNumber);
				tvr.setVehicleTypeId(vehicleTypeId);
				tvr.setVehicleBody(vehicleBody);
				tvr.setModelYear(modelYear);
				tvr.setInsuranceExpiry(insuranceExpiry);
				tvr.setStatus(status);
				tvr.setCreatedBy(createdBy);
				tvr.setCreatedOn(new Date());
				tvr.setModifiedBy(createdBy);
				tvr.setModifiedOn(new Date());
				tvr.setIsActive(1);

				ControllerDAOTracker saveBack;

				ControllerDAOTracker TR = transporterRegistrationService.getVehicleNumber(vehicleNumber);

				if (TR.isSuccess()) {
					restResponce.setErrorCode("101");
					restResponce.setErrorMesaage("Vehicle already registered");
					return restResponce;
				} else {

					saveBack = transporterRegistrationService.saveTransporterVehicle(tvr);

					if (saveBack.getErrorCode().equals("100")) {
						restResponce.setErrorCode(saveBack.getErrorCode());
						restResponce.setErrorMesaage(saveBack.getErrorMessage());
						restResponce.setData(saveBack.getTransporterVehicleRegistration());
					} else {
						restResponce.setErrorCode(saveBack.getErrorCode());
						restResponce.setErrorMesaage(saveBack.getErrorMessage());
					}

					return restResponce;
				}
			}
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** FOR SEARCHING TRANSPORTER VEHICLES */
	@ResponseBody
	@RequestMapping("/getTransporterVehicle/{trsptrRegistrationId}")
	public RestResponce getTransporterVehicle(@PathVariable("trsptrRegistrationId") Integer trsptrRegistrationId) {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterRegistrationService.getTransporterVehicle(trsptrRegistrationId);

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getData());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** UPDATE REGISTERED TRANSPORTER VEHICLE */
	@ResponseBody
	@RequestMapping(value = "/updateTransporterVehicle", method = RequestMethod.POST)
	public RestResponce updateTransporterVehicle(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer id, @RequestParam Integer trsptrRegistrationId, @RequestParam String vehicleNumber,
			@RequestParam Integer vehicleTypeId, @RequestParam String vehicleBody, @RequestParam String modelYear,
			@RequestParam Date insuranceExpiry, @RequestParam String status, @RequestParam Integer modifiedBy)
					throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			TransporterVehicleRegistration tvr = new TransporterVehicleRegistration();

			tvr.setId(id);
			tvr.setTrsptrRegistrationId(trsptrRegistrationId);
			vehicleNumber = vehicleNumber.replace(" ", "");
			tvr.setVehicleNumber(vehicleNumber);
			tvr.setVehicleTypeId(vehicleTypeId);
			tvr.setVehicleBody(vehicleBody);
			tvr.setModelYear(modelYear);
			tvr.setInsuranceExpiry(insuranceExpiry);
			tvr.setStatus(status);
			tvr.setModifiedBy(modifiedBy);
			tvr.setModifiedOn(new Date());

			ControllerDAOTracker saveBack;

			saveBack = transporterRegistrationService.updateTransporterVehicle(tvr);

			if (saveBack.getErrorCode().equals("200")) {
				restResponce.setErrorCode(saveBack.getErrorCode());
				restResponce.setErrorMesaage(saveBack.getErrorMessage());
				restResponce.setData(saveBack.getTransporterVehicleRegistration());
			} else {
				restResponce.setErrorCode(saveBack.getErrorCode());
				restResponce.setErrorMesaage(saveBack.getErrorMessage());
				restResponce.setData(saveBack.getData());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
	}

	/** FOR GETTING CLIENT ORDER */
	@ResponseBody
	@RequestMapping("/getClientOrders/{cityId}/{vehicleCategory}")
	public RestResponce getClientOrders(@PathVariable("cityId") Integer cityId,
			@PathVariable("vehicleCategory") String vehicleCategory) {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterRegistrationService.getClientOrders(cityId, vehicleCategory);

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getData());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** FOR GETTING CLIENT ORDER */
	@ResponseBody
	@RequestMapping("/getClientOrdersWithTransporter/{trsptrRegistrationId}/{cityId}/{vehicleCategory}")
	public RestResponce getClientOrdersWithTransporter(
			@PathVariable("trsptrRegistrationId") Integer trsptrRegistrationId, @PathVariable("cityId") Integer cityId,
			@PathVariable("vehicleCategory") String vehicleCategory) {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterRegistrationService
					.getClientOrdersWithTransporter(trsptrRegistrationId, cityId, vehicleCategory);

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getData());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** TO ACCEPT CLIENT ORDER (ORDER CONFIRMATION) */
	@ResponseBody
	@RequestMapping(value = "/clientOrderConfirmation", method = RequestMethod.POST)
	public RestResponce clientOrderConfirmation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer trsptrClientOrdersId, @RequestParam Integer trsptrRegistrationId,
			@RequestParam String vehicleNumber, @RequestParam String driverName,
			@RequestParam String driverMobileNumber, @RequestParam Integer createdBy) throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			TransporterClientOrderMapping tcom = new TransporterClientOrderMapping();

			tcom.setTrsptrClientOrdersId(trsptrClientOrdersId);
			tcom.setTrsptrRegistrationId(trsptrRegistrationId);
			tcom.setVehicleNumber(vehicleNumber);
			tcom.setDriverName(driverName);
			tcom.setDriverMobileNumber(driverMobileNumber);
			tcom.setStatus("Pending");
			tcom.setCreatedBy(createdBy);
			tcom.setCreatedOn(new Date());
			tcom.setModifiedBy(createdBy);
			tcom.setModifiedOn(new Date());
			tcom.setIsActive(1);

			TransporterClientOrders tco = new TransporterClientOrders();

			tco.setId(trsptrClientOrdersId);
			tco.setIsActive(0);
			tco.setModifiedBy(createdBy);
			tco.setModifiedOn(new Date());

			ControllerDAOTracker saveBack;

			ControllerDAOTracker TR = transporterRegistrationService.clientOrderConfirmation(tcom);

			if (TR.isSuccess()) {
				if (TR.getErrorCode().equals("100")) {

					ControllerDAOTracker TRFU = transporterRegistrationService.transporterClientIsActiveUpdate(tco);

					if (TRFU.isSuccess()) {
						restResponce.setErrorCode(TR.getErrorCode());
						restResponce.setErrorMesaage(TR.getErrorMessage());
						restResponce.setData(TR.getTransporterClientOrderMapping());
					} else {
						restResponce.setErrorCode(TR.getErrorCode());
						restResponce.setErrorMesaage(TR.getErrorMessage());
					}
				} else {
					restResponce.setErrorCode(TR.getErrorCode());
					restResponce.setErrorMesaage(TR.getErrorMessage());
				}

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

	/** TO CHANGE IS ACTIVE UPDATE ON CRM */
	@ResponseBody
	@RequestMapping(value = "/changeClientIsActiveUpdate", method = RequestMethod.POST)
	public RestResponce changeClientIsActiveUpdate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer trsptrClientOrdersId, @RequestParam String status, @RequestParam Integer modifiedBy)
					throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			TransporterClientOrders tco = new TransporterClientOrders();

			tco.setId(trsptrClientOrdersId);
			tco.setIsActive(1);
			tco.setModifiedBy(modifiedBy);
			tco.setModifiedOn(new Date());

			TransporterClientOrderMapping tcom = new TransporterClientOrderMapping();

			tcom.setTrsptrClientOrdersId(trsptrClientOrdersId);
			tcom.setStatus(status);
			tcom.setModifiedBy(modifiedBy);
			tcom.setModifiedOn(new Date());

			TransporterOrderFollowUp tofu = new TransporterOrderFollowUp();
			tofu.setTrsptrClientOrdersId(trsptrClientOrdersId);
			tofu.setModifiedBy(modifiedBy);
			tofu.setModifiedOn(new Date());
			tofu.setIsActive(0);

			ControllerDAOTracker saveBack;

			saveBack = transporterRegistrationService.transporterClientIsActiveUpdate(tco);

			if (saveBack.isSuccess()) {

				ControllerDAOTracker ctcom = transporterRegistrationService.changeTransporterStatus(tcom);

				if (ctcom.isSuccess()) {

					ControllerDAOTracker tfau = transporterRegistrationService.transporterFollowUpActiveUpdate(tofu);

					if (tfau.isSuccess()) {
						restResponce.setErrorCode(tfau.getErrorCode());
						restResponce.setErrorMesaage(tfau.getErrorMessage());
						// restResponce.setData(saveBack.getTransporterClientOrders());
					} else {
						restResponce.setErrorCode(tfau.getErrorCode());
						restResponce.setErrorMesaage(tfau.getErrorMessage());
					}
				} else {
					restResponce.setErrorCode(ctcom.getErrorCode());
					restResponce.setErrorMesaage(ctcom.getErrorMessage());
				}
			} else {
				restResponce.setErrorCode(saveBack.getErrorCode());
				restResponce.setErrorMesaage(saveBack.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
	}

	/** TO SAVE CLIENT FOLLOW UP ORDERS */
	@ResponseBody
	@RequestMapping(value = "/saveClientFollowUpOrders", method = RequestMethod.POST)
	public RestResponce saveClientFollowUpOrders(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer trsptrClientOrdersId, @RequestParam Integer trsptrRegistrationId,
			@RequestParam Integer createdBy) throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			TransporterOrderFollowUp tofu = new TransporterOrderFollowUp();

			tofu.setTrsptrClientOrdersId(trsptrClientOrdersId);
			tofu.setTrsptrRegistrationId(trsptrRegistrationId);
			tofu.setCreatedBy(createdBy);
			tofu.setCreatedOn(new Date());
			tofu.setModifiedBy(createdBy);
			tofu.setModifiedOn(new Date());
			tofu.setIsActive(1);

			ControllerDAOTracker saveBack;

			ControllerDAOTracker TRF = transporterRegistrationService.orderFollowUp(tofu);

			if (TRF.isSuccess()) {

				restResponce.setErrorCode(TRF.getErrorCode());
				restResponce.setErrorMesaage(TRF.getErrorMessage());
				restResponce.setData(TRF.getTransporterOrderFollowUp());
			} else {
				restResponce.setErrorCode(TRF.getErrorCode());
				restResponce.setErrorMesaage(TRF.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** FOR GETTING CLIENT FOLLOW UP ORDERS */
	/** FOR SHOWING CLIENT FOLLOW UP ORDERS */
	@ResponseBody
	@RequestMapping("/getFollowUpOrders/{trsptrRegistrationId}")
	public RestResponce getFollowUpOrders(@PathVariable("trsptrRegistrationId") Integer trsptrRegistrationId) {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterRegistrationService.getFollowUpOrders(trsptrRegistrationId);

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getData());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** FOR GETTING TRANSPORTER ORDER HISTORY */

	/** FOR SHOWING TRANSPORTER PENDING ORDERS */
	@ResponseBody
	@RequestMapping("/getClientOrdersHistory/{trsptrRegistrationId}/{status}/{vehicleCategory}")
	public RestResponce getClientOrdersHistory(@PathVariable("trsptrRegistrationId") Integer trsptrRegistrationId,
			@PathVariable("status") String status, @PathVariable("vehicleCategory") String vehicleCategory) {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterRegistrationService.getClientOrdersHistory(trsptrRegistrationId,
					status, vehicleCategory);

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getData());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** FOR SHOWING TRANSPORTER FREIGHT CHART */
	@ResponseBody
	@RequestMapping("/getTransporterFreightChart/{trsptrRegistrationId}/{sourceCityId}/{destinationCityId}/{vehicleTypeId}/{bodyType}")
	public RestResponce getTransporterFreightChart(@PathVariable("trsptrRegistrationId") Integer trsptrRegistrationId,
			@PathVariable("sourceCityId") Integer sourceCityId,
			@PathVariable("destinationCityId") Integer destinationCityId,
			@PathVariable("vehicleTypeId") Integer vehicleTypeId, @PathVariable("bodyType") String bodyType) {

		try {
			RestResponce restResponce = new RestResponce();

			TransporterFreightChart tfc = new TransporterFreightChart();
			tfc.setTrsptrRegistrationId(trsptrRegistrationId);
			tfc.setSourceCityId(sourceCityId);
			tfc.setDestinationCityId(destinationCityId);
			tfc.setVehicleTypeId(vehicleTypeId);
			tfc.setBodyType(bodyType);

			ControllerDAOTracker cdt = transporterRegistrationService.getTransporterFreightChart(tfc);

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getTransporterFreightChart());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** TO SAVE FREIGHT RATE CORRESPONDING TRANSPORTER */
	@ResponseBody
	@RequestMapping(value = "/saveTransporterFreightRate", method = RequestMethod.POST)
	public RestResponce saveTransporterFreightRate(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer trsptrRegistrationId, @RequestParam Integer sourceCityId,
			@RequestParam Integer destinationCityId, @RequestParam Integer vehicleTypeId, @RequestParam String bodyType,
			@RequestParam Integer freightRate, @RequestParam Integer createdBy) throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			TransporterFreightChart tr = new TransporterFreightChart();

			tr.setTrsptrRegistrationId(trsptrRegistrationId);
			tr.setSourceCityId(sourceCityId);
			tr.setDestinationCityId(destinationCityId);
			tr.setVehicleTypeId(vehicleTypeId);
			tr.setBodyType(bodyType);
			tr.setFreightRate(freightRate);
			tr.setCreatedBy(createdBy);
			tr.setCreatedOn(new Date());
			tr.setModifiedBy(createdBy);
			tr.setModifiedOn(new Date());
			tr.setIsActive(1);

			ControllerDAOTracker TR = transporterRegistrationService.saveTransporterFreightRate(tr);

			if (TR.isSuccess()) {
				restResponce.setErrorCode(TR.getErrorCode());
				restResponce.setErrorMesaage(TR.getErrorMessage());
				restResponce.setData(TR.getData());
				return restResponce;
			} else {

				restResponce.setErrorCode(TR.getErrorCode());
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

	/** FOR GETTING CLIENT ORDER CITY LIST */
	@ResponseBody
	@RequestMapping("/getOrderCityList")
	public RestResponce getOrderCityList() {

		try {
			RestResponce restResponce = new RestResponce();

			ControllerDAOTracker cdt = transporterRegistrationService.getOrderCityList();

			if (cdt.isSuccess()) {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
				restResponce.setData(cdt.getData());
			} else {
				restResponce.setErrorCode(cdt.getErrorCode());
				restResponce.setErrorMesaage(cdt.getErrorMessage());
			}

			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}

	}

	/** TO SAVE FREIGHT RATE CORRESPONDING TRANSPORTER */
	@ResponseBody
	@RequestMapping(value = "dHJ1eGFwcCBwdnQgbHRk/saveSMSRecord", method = RequestMethod.POST)
	public RestResponce saveSMSRecord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String senderMask, @RequestParam String smsProvider, @RequestParam String mobileNumber,
			@RequestParam String smsText) throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			CommunicationSMS cSms = new CommunicationSMS();
			cSms.setSenderMask(senderMask);
			cSms.setSmsProvider(smsProvider);
			cSms.setMobileNumber(mobileNumber);
			cSms.setSmsText(smsText);
			cSms.setRequestDate(new Date());
			cSms.setRequestProcess(new Date());

			ControllerDAOTracker TR = transporterRegistrationService.saveSMSRecord(cSms);

			if (TR.isSuccess()) {
				restResponce.setErrorCode(TR.getErrorCode());
				restResponce.setErrorMesaage(TR.getErrorMessage());
				restResponce.setData(TR.getData());
				return restResponce;
			} else {

				restResponce.setErrorCode(TR.getErrorCode());
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

	/** TO SAVE FREIGHT RATE CORRESPONDING TRANSPORTER */
	@ResponseBody
	@RequestMapping(value = "/saveEmailRecord", method = RequestMethod.POST)
	public RestResponce saveEmailRecord(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String emailId, @RequestParam String emailProvider, @RequestParam String emailText,
			@RequestParam String subject) throws ParseException {

		try {

			RestResponce restResponce = new RestResponce();

			CommunicationEmail cSms = new CommunicationEmail();
			cSms.setEmailId(emailId);
			cSms.setEmailProvider(emailProvider);
			cSms.setEmailText(emailText);
			cSms.setSubject(subject);
			cSms.setRequestDate(new Date());
			cSms.setRequestProcess(new Date());

			ControllerDAOTracker TR = transporterRegistrationService.saveEmailRecord(cSms);

			if (TR.isSuccess()) {
				restResponce.setErrorCode(TR.getErrorCode());
				restResponce.setErrorMesaage(TR.getErrorMessage());
				restResponce.setData(TR.getData());
				return restResponce;
			} else {

				restResponce.setErrorCode(TR.getErrorCode());
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

	@ResponseBody
	@RequestMapping(value = "/sendEmail")
	public RestResponce sendEmail(HttpServletRequest request) {
		try {
			RestResponce restResponce = new RestResponce();

			// ControllerDAOTracker TR =
			// transporterRegistrationService.getCommunicationByEmail(emailId);
			ControllerDAOTracker TR = transporterRegistrationService.sendByEmail();
			if (TR.isSuccess()) {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage("Email Send Successfully");
				return restResponce;
			} else {
				restResponce.setErrorCode("100");
				restResponce.setErrorMesaage("Email Not Sended");
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
	@RequestMapping(value = "changePassword", method = RequestMethod.POST, consumes = { "application/json" })
	public ChangePassword changePassword(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam String currentpassword, @PathVariable("password") String password,
			@RequestParam String changepassword, @RequestParam String confirmpassword) throws ParseException {
		ChangePassword changePassword = new ChangePassword();

		/*
		 * if(currentpassword.equals(password)){
		 * if(confirmpassword.equals(changepassword)) {
		 * changePassword.setPassword(confirmpassword); System.out.println(
		 * "Password Change Successfully"); model.addAttribute("changePassword",
		 * "Password Change Successfully"); }else{ System.out.println(
		 * "confirm should be same as change password"); } } else {
		 * System.out.println("invalid password"); }
		 */
		changePassword.setErrorCode("101");
		changePassword.setErrorMesaage("something is not right");
		try {
			if (currentpassword.equals(password) && changepassword.equals(confirmpassword)) {
				changePassword.setErrorCode("100");
				changePassword.setErrorMesaage("Password update Successfully");
				// changePassword.setData(this.changePassword(model, request,
				// response, currentpassword, password, changepassword,
				// confirmpassword));
				// changePassword.setData(this.transporterRegistrationService.updateTransporterRegistration("password"));

			} else {
				changePassword.setErrorCode("101");
				changePassword.setErrorMesaage("something is not right");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return changePassword;
	}
	/* Added By Shahzad Hussain */

	@ResponseBody
	@RequestMapping("/changepassword/{id}/{current_password}/{new_password}/{confirm_password}")
	public RestResponce view(@PathVariable("id") int id, @PathVariable("current_password") int currentPassword,
			@PathVariable("new_password") int newPassword, @PathVariable("confirm_password") int confirmPassword) {

		RestResponce registration = new RestResponce();

		if (newPassword == confirmPassword) {

			TransporterRegistration transporterRegistration = this.transporterRegistrationService.getById(id);

			if (transporterRegistration.getPassword().equals(currentPassword)) {
				TransporterRegistration registration2 = new TransporterRegistration();
				registration2.setId(id);
				registration2.setPassword(newPassword);

				if (this.transporterRegistrationService.updatePassword(registration2).getErrorCode().equals("100")) {
					registration.setErrorCode("100");
					registration.setErrorMesaage(
							this.transporterRegistrationService.updatePassword(registration2).getErrorMesaage());
					registration.setData(this.transporterRegistrationService.updatePassword(registration2));
				}

			} else {
				registration.setErrorCode("101");
				registration.setErrorMesaage("You have entered wrong password.");
			}

		} else {
			registration.setErrorCode("101");
			registration.setErrorMesaage("New Password and Confirm Password do not match.");
		}
		return registration;
	}

	@ResponseBody
	@RequestMapping("/password/{password}")
	public RestResponce getPassword(@PathVariable("password") Integer password) {

		RestResponce restRsp = new RestResponce();
		restRsp.setErrorCode("101");
		restRsp.setErrorMesaage("Something went wrong!!");
		try {
			if (password != null) {
				restRsp.setErrorCode("100");
				restRsp.setErrorMesaage("sucesss");
				restRsp.setData(this.transporterRegistrationService.getByPassword(password));

			} else {
				restRsp.setErrorCode("101");
				restRsp.setErrorMesaage("failure! No Data Sent");
			}

		} catch (Exception er) {
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}

	@ResponseBody
	@RequestMapping("/updatePassword/{password}/{newpassword}/{confirmpassword}")
	public RestResponce updatePassword(@PathVariable("password") Integer password,
			@PathVariable("newpassword") Integer newpassword,
			@PathVariable("confirmpassword") Integer confirmpassword) {

		RestResponce restRsp = new RestResponce();
		restRsp.setErrorCode("101");
		restRsp.setErrorMesaage("Something went wrong!!");
		try {
			if (password != null) {
				restRsp.setErrorCode("100");
				restRsp.setErrorMesaage("sucesss");
				// restRsp.setData(this.transporterRegistrationService.updatePassword(password,
				// newpassword, confirmpassword));

			} else {
				restRsp.setErrorCode("101");
				restRsp.setErrorMesaage("failure! No Data Sent");
			}

		} catch (Exception er) {
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}

	@ResponseBody
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public RestResponce verifyDetails(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Integer trsptrRegistrationId, @RequestParam String accountHolderName,
			@RequestParam String bankName, @RequestParam String ifscCode, @RequestParam String accountNumber,
			@RequestParam Integer createdBy) {
		try {

			RestResponce restResponce = new RestResponce();

			TransporterBankDetails tr = new TransporterBankDetails();

			tr.setTrsptrRegistrationId(trsptrRegistrationId);
			tr.setAccountHolderName(accountHolderName);
			tr.setAccountNumber(accountNumber);
			tr.setBankName(bankName);/*
										 * Password(TruxUtils.getRandomNumber(
										 * 100000, 999999));
										 */
			tr.setIfscCode(ifscCode);
			tr.setAccountStatus("New");
			tr.setCreatedBy(createdBy);
			tr.setCreatedOn(new Date());
			tr.setModifiedBy(createdBy);
			tr.setModifiedOn(new Date());
			tr.setIsActive(1);

			TransporterBankDetails saveBack = null;

			TransporterBankDetails TR = transporterRegistrationService.getBankDetailsById(trsptrRegistrationId);

			if (TR != null) {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage("Account already registered");
				restResponce.setData(TR);
				return restResponce;
			} else {
				saveBack = transporterRegistrationService.addBankDetails(TR);
				restResponce.setErrorCode("100");
				restResponce.setErrorMesaage("Account Added Successfully");
				restResponce.setData(TR);
				return restResponce;

			}
			/*
			 * if (TR != null) { restResponce.setErrorCode("101");
			 * restResponce.setErrorMesaage("Account already registered");
			 * restResponce.setData(TR); return restResponce; } else {
			 * 
			 * saveBack = transporterRegistrationService.addBankDetails(tr);
			 * 
			 * if (saveBack!=null) { restResponce.setErrorCode("100");
			 * restResponce.setErrorMesaage("Success");
			 * restResponce.setData(saveBack); } else {
			 * restResponce.setErrorCode("101");
			 * restResponce.setErrorMesaage("Failure"); }
			 * 
			 * return restResponce; }
			 */

		} catch (Exception ex) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(ex.toString());
			return restResponcee;
		}
	}

	/* Added By Shahzad Hussain */
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
			tr.setCreatedBy(createdBy);
			tr.setCreatedOn(new Date());
			tr.setModifiedBy(createdBy);
			tr.setModifiedOn(new Date());
			tr.setIsActive(1);

			ControllerDAOTracker saveBack;

			ControllerDAOTracker TR = transporterBankDetailsService.getTransporterRegistrationId(trsptrRegistrationId);

			if (TR.isSuccess()) {
				restResponce.setErrorCode("101");
				restResponce.setErrorMesaage("Transporter already registered");
				return restResponce;
			} else {
				saveBack = transporterBankDetailsService.saveTransporterBankDetails(tr);
				if (saveBack.getErrorCode().equals("100")) {
					restResponce.setErrorCode(saveBack.getErrorCode());
					restResponce.setErrorMesaage(saveBack.getErrorMessage());
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
}
