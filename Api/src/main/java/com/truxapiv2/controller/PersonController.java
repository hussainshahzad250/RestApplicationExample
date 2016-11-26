package com.truxapiv2.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truxapiv2.model.BookingLease;
import com.truxapiv2.model.CassandraTrack;
import com.truxapiv2.model.ClientBookingDoc;
import com.truxapiv2.model.ClientBookingDocs;
import com.truxapiv2.model.DriverDeviceVehicleMapping;
import com.truxapiv2.model.DriverLoginHistory;
import com.truxapiv2.model.DriverPartPayment;
import com.truxapiv2.model.Person;
import com.truxapiv2.model.RestResponce;
import com.truxapiv2.model.S3SignedURL;
import com.truxapiv2.model.TrackBooking;
import com.truxapiv2.service.BookingLeaseService;
import com.truxapiv2.service.ClientMandateVehicleDeploymentService;
import com.truxapiv2.service.DriverDeviceVehicleMappingService;
import com.truxapiv2.service.DriverLoginHistoryService;
import com.truxapiv2.service.DriverPartPaymentMappingService;
import com.truxapiv2.service.PersonService;
import com.truxapiv2.util.S3;

@Controller
public class PersonController {
	
	private PersonService personService;	
	
	private DriverDeviceVehicleMappingService driverDeviceVehicleMappingService;
	
	private DriverLoginHistoryService driverLoginHistoryService;
	
	private BookingLeaseService bookingLeaseService;
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DriverPartPaymentMappingService driverPartPaymentMappingService;
	
	@Autowired
	private ClientMandateVehicleDeploymentService clientMandateVehicleDeploymentService;
	
	@Autowired(required=true)
	@Qualifier(value="bookingLeaseService")
	public void setBookingLeaseServices(BookingLeaseService bl){
		this.bookingLeaseService = bl;
	}
	
	@Autowired(required=true)
	@Qualifier(value="driverLoginHistoryService")
	public void setDriverLoginHistoryService(DriverLoginHistoryService dh){
		this.driverLoginHistoryService = dh;
	}
	
	@Autowired(required=true)
	@Qualifier(value="driverDeviceVehicleMappingService")
	public void setDriverDeviceVehicleMappingService(DriverDeviceVehicleMappingService ds){
		this.driverDeviceVehicleMappingService = ds;
	}
	
	@Autowired(required=true)
	@Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public String listPersons(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	/*@ResponseBody
	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public List<Person> listPersons(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return this.personService.listPersons();
	}*/
	
	//For add and update person both
	@RequestMapping(value= "/person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") Person p){
		
		if(p.getId() == 0){
			//new person, add it
			this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
		}
		
		return "redirect:/persons";
		
	}
	
	@RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id){
		
        this.personService.removePerson(id);
        return "redirect:/persons";
    }
 
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }
    @ResponseBody
    @RequestMapping("/drivers/{id}")
    public DriverDeviceVehicleMapping getDriver(@PathVariable("id") int id){
        
        return this.driverDeviceVehicleMappingService.getDriverById(id);
    }
    
    @ResponseBody
    @RequestMapping("/driver/punch/{phone}/{p_flag}")
    public RestResponce trackPunchDriverWithLatLong(@PathVariable("phone") String phone, @PathVariable("p_flag") int punch_status){
    	RestResponce restRsp = new RestResponce();

    	DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByPhone(phone);
    	if(DDVM!=null){
	    	DriverLoginHistory loginData = new DriverLoginHistory();
	    	loginData.setDriverMobile(phone);
	    	loginData.setPunchIngStatus(punch_status);
	    	loginData.setDatetime(new Date());
	    	
	    	if(punch_status==1){
	    		if(DDVM.getTrackingDeviced()!=null){
		    		try {
						if(DDVM.getDriverClientId()!=0){
							if(this.driverDeviceVehicleMappingService.checkGeoFencing(DDVM.getDriverClientId(), DDVM.getTrackingDeviced())){
								
								int loginDataId = this.driverLoginHistoryService.saveDriverLoginHistory(loginData);
						    	
						    	if(loginDataId!=0){
						    		this.driverDeviceVehicleMappingService.updateLoginStatus(punch_status, phone);
						    		restRsp.setErrorCode("100");
						    		restRsp.setErrorMesaage("sucesss");
						    		restRsp.setData(this.driverDeviceVehicleMappingService.getDriverByPhone(phone));
						    		TrackBooking tbo = new TrackBooking();
									tbo.setTripid(0);
									tbo.setIs_Punch_in(1);
									tbo.setDeviceid(DDVM.getTrackingDeviced());
									try{
										bookingLeaseService.hitTrackingService(tbo);
									}catch(Exception er){
										System.out.println(er.getMessage().toString());
									}
						    	}
						    	else{
						    		restRsp.setErrorCode("101");
						        	restRsp.setErrorMesaage("Already loggedin for today");
						    	}
								
							}
							else{
								restRsp.setErrorCode("101");
					        	restRsp.setErrorMesaage("Geofencing failed!");
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}  else{
					restRsp.setErrorCode("101");
		        	restRsp.setErrorMesaage("Not mapped to tracking device!");
				}  		
	    	}
	    	if(punch_status==0){
	    		//if(DDVM.getTrackingDeviced()!=null){
	    			int loginDataId = this.driverLoginHistoryService.saveDriverLoginHistory(loginData);
	    			this.driverDeviceVehicleMappingService.updateLoginStatus(punch_status, phone);
	    			TrackBooking tbo = new TrackBooking();
	    			tbo.setTripid(0);
	    			tbo.setIs_Punch_in(0);
	    			tbo.setDeviceid(DDVM.getTrackingDeviced());
	    			if(loginDataId!=0){
		    			restRsp.setErrorCode("100");
			    		restRsp.setErrorMesaage("sucesss");
			    		restRsp.setData(this.driverDeviceVehicleMappingService.getDriverByPhone(phone));
			    		try{
		        			bookingLeaseService.hitTrackingService(tbo);
		        		}catch(Exception er){
		        			System.out.println(er.getMessage().toString());
		        		}
	    			}
	    			else{
	    				restRsp.setErrorCode("101");
	    	    		restRsp.setErrorMesaage("Unable to logout!");
    				}
	        		
	    		//}
	    	}
	    	
    	}else{
    		restRsp.setErrorCode("101");
    		restRsp.setErrorMesaage("Invalid Number!");
        	//restRsp.setErrorMesaage(this.messageSource.getMessage("greeting", null, "Required", null));
    	}
    	return restRsp;   
    }
    @ResponseBody
    @RequestMapping("/driver/journey/{phone}/{jrn_flag}")
    public RestResponce trackJourneyDriver(@PathVariable("phone") String phone, @PathVariable("jrn_flag") int jrn_status){
    	RestResponce restRsp = new RestResponce();
    	boolean rtnJrSt = false;
    	DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByPhone(phone);
		if(jrn_status==1 && DDVM.getDriverStatus()==1){
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("Please Close Pending Journey First.");
        	restRsp.setData(DDVM);
        	return restRsp; 
		}
		if(jrn_status==0 && DDVM.getDriverStatus()==0){
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("No Journey to Close.");
        	restRsp.setData(DDVM);
        	return restRsp; 
		}
    	rtnJrSt = this.driverDeviceVehicleMappingService.updateJourneyStatus(jrn_status, phone);
    	if(rtnJrSt == true){
    		try {
				
				BookingLease bl = new BookingLease();
				bl.setDriverId(DDVM.getDriverId());
				bl.setVehicleId(DDVM.getVehicleId());
				bl.setCompanyId(DDVM.getDriverClientId());
				bl.setDriverMobile(DDVM.getDriverPhoneNumber());
				bl.setJourneyStartDate(new Date());
				bl.setUpdatedDateTime(new Date());
				bl.setCreatedDateTime(new Date());
				bl.setJourneyEndDate(null);
				bl.setBookingLsStatus(0);
				if(jrn_status==1){
					int bookingId = this.bookingLeaseService.saveBooking(bl);
					if(bookingId!=0){
						HashMap<String, String> additionalProperties = new HashMap<String, String>();
						additionalProperties.put("OrederID", ""+bookingId);
						DDVM.setAdditionalProperties(additionalProperties);
						this.driverDeviceVehicleMappingService.updateJourneyStatusId(bookingId, phone);
						DDVM.setBookingId(bookingId);
						DDVM.setDriverStatus(1);
						TrackBooking tbo = new TrackBooking();
						tbo.setTripid(bookingId);
						tbo.setIs_Punch_in(1);
						tbo.setDeviceid(DDVM.getTrackingDeviced());
						tbo.setDropid(1);
						restRsp.setErrorCode("100");
						restRsp.setErrorMesaage("sucesss");
						restRsp.setData(DDVM);
						try{
							bookingLeaseService.hitTrackingService(tbo);
						}catch(Exception er){
							System.out.println(er.getMessage().toString());
						}
					}else{
			    		restRsp.setErrorCode("101");
			        	restRsp.setErrorMesaage("failure");
			    	}					
				}
				if(jrn_status==0){
					double totalDistance = this.bookingLeaseService.getDeviceLatestDistance(DDVM.getTrackingDeviced());
					bl.setTotalDistance(totalDistance);
					System.out.println ("Tracking device ID = "+DDVM.getTrackingDeviced()+" Distance on controller:"+totalDistance);
					boolean isUpdate = this.bookingLeaseService.updateBooking(bl);
					if(isUpdate){
						this.driverDeviceVehicleMappingService.updateJourneyStatusId(0, phone);
						DDVM.setBookingId(0);
						DDVM.setDriverStatus(0);
						TrackBooking tbo = new TrackBooking();
						tbo.setTripid(0);
						tbo.setIs_Punch_in(1);
						tbo.setDeviceid(DDVM.getTrackingDeviced());
						tbo.setDropid(1);
						restRsp.setErrorCode("100");
						restRsp.setErrorMesaage("sucesss");
						restRsp.setData(DDVM);
						try{
							bookingLeaseService.hitTrackingService(tbo);
						}catch(Exception er){
							System.out.println(er.getMessage().toString());
						}
					}else{
			    		restRsp.setErrorCode("101");
			        	restRsp.setErrorMesaage("failure to end booking");
			    	}	
				}
				
			} catch (Exception e) {
				restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
				e.printStackTrace();
			}
    	}
    	else{
    		restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("failure");
    	}
    	return restRsp;   
    }
    @ResponseBody
    @RequestMapping("/driver/journey/{v_number}/{jrn_flag}/{by_id}/{timestamp}")
    public RestResponce trackJourneyDriver3(@PathVariable("v_number") String vehicleno, @PathVariable("jrn_flag") int jrn_status, @PathVariable("by_id") int by_id, @PathVariable("timestamp") String timestamp){
    	RestResponce restRsp = new RestResponce();
    	boolean rtnJrSt = false;
    	DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByVehicleNo(vehicleno);
    	Long timestampL = Long.parseLong(timestamp);
		if(DDVM!=null){
    	if(jrn_status==1 && DDVM.getDriverStatus()==1){
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("Please Close Pending Journey First.");
        	restRsp.setData(DDVM);
        	return restRsp; 
		}
		if(jrn_status==0 && DDVM.getDriverStatus()==0){
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("No Journey to Close.");
        	restRsp.setData(DDVM);
        	return restRsp; 
		}
    	
		try {
			
			BookingLease bl = new BookingLease();
			bl.setDriverId(DDVM.getDriverId());
			bl.setVehicleId(DDVM.getVehicleId());
			bl.setCompanyId(DDVM.getDriverClientId());
			bl.setDriverMobile(DDVM.getDriverPhoneNumber());
			//bl.setJourneyStartDate(new Date());
			//bl.setJourneyEndDate(null);
			bl.setBookingLsStatus(0);
			Integer clientRequestId = this.clientMandateVehicleDeploymentService.getRequestIdByVehicleId(DDVM.getVehicleId());
			if(clientRequestId!=null){
				bl.setClientRequestId(clientRequestId);
			}
			if(jrn_status==1){
				bl.setCreatedBy(by_id);
				if(timestampL!=100){					
					bl.setJourneyStartDate(new Date(timestampL));						
				} 
				else {
					bl.setJourneyStartDate(new Date());
				}
				bl.setCreatedDateTime(new Date());
				
				int bookingId = this.bookingLeaseService.saveBooking(bl);
				if(bookingId!=0){
					HashMap<String, String> additionalProperties = new HashMap<String, String>();
					additionalProperties.put("OrederID", ""+bookingId);
					DDVM.setAdditionalProperties(additionalProperties);
					this.driverDeviceVehicleMappingService.updateJourneyStatusByVehicleAndDriver(jrn_status, DDVM.getVehicleId(), DDVM.getDriverId());
					DDVM.setBookingId(bookingId);
					DDVM.setDriverStatus(1);
					TrackBooking tbo = new TrackBooking();
					tbo.setTripid(bookingId);
					tbo.setIs_Punch_in(1);
					tbo.setDeviceid(DDVM.getTrackingDeviced());
					tbo.setDropid(1);
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");
					restRsp.setData(DDVM);
					try{
						bookingLeaseService.hitTrackingService(tbo);
					}catch(Exception er){
						System.out.println(er.getMessage().toString());
					}
				}else{
		    		restRsp.setErrorCode("101");
		        	restRsp.setErrorMesaage("failure");
		    	}					
			}
			if(jrn_status==0){
				bl.setModifiedBy(by_id);
				if(timestampL!=100){					
					bl.setJourneyEndDate(new Date(timestampL));						
				} 
				else {
					bl.setJourneyEndDate(new Date());
				}
				bl.setUpdatedDateTime(new Date());
				double totalDistance = 0;
				if(DDVM.getTrackingDeviced()!=0){
					totalDistance = this.bookingLeaseService.getDeviceLatestDistance(DDVM.getTrackingDeviced());
					bl.setTotalDistance(totalDistance);
					System.out.println ("Tracking device ID = "+DDVM.getTrackingDeviced()+" Distance on controller:"+totalDistance);
				}
				BookingLease pendingBL = this.bookingLeaseService.getPendingBookingLease(bl);
				
				boolean isUpdate = this.bookingLeaseService.updateBooking2(bl);
				
				if(isUpdate){
					if(pendingBL!=null){
						if(pendingBL.getClientRequestId()!=null){
							this.clientMandateVehicleDeploymentService.updateDeploymentVehicleId(pendingBL.getClientRequestId());
						}
					}
					this.driverDeviceVehicleMappingService.updateJourneyStatusByVehicleAndDriver(jrn_status, DDVM.getVehicleId(), DDVM.getDriverId());
					DDVM.setBookingId(0);
					DDVM.setDriverStatus(0);
					TrackBooking tbo = new TrackBooking();
					tbo.setTripid(0);
					tbo.setIs_Punch_in(1);
					tbo.setDeviceid(DDVM.getTrackingDeviced());
					tbo.setDropid(1);
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");
					restRsp.setData(DDVM);
					try{
						bookingLeaseService.hitTrackingService(tbo);
					}catch(Exception er){
						System.out.println(er.getMessage().toString());
					}
				}else{
		    		restRsp.setErrorCode("101");
		        	restRsp.setErrorMesaage("failure to end booking");
		    	}	
			}
			
		} catch (Exception e) {
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("failure");
			e.printStackTrace();
		}
		} else{
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("No Vehicle Found!!");
		}
    	return restRsp;   
    }
    @ResponseBody
    @RequestMapping("/driver/journey/{phone}/{jrn_flag}/{by_id}")
    public RestResponce trackJourneyDriver2(@PathVariable("phone") String phone, @PathVariable("jrn_flag") int jrn_status, @PathVariable("by_id") int by_id){
    	RestResponce restRsp = new RestResponce();
    	boolean rtnJrSt = false;
    	DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByPhone(phone);
		if(jrn_status==1 && DDVM.getDriverStatus()==1){
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("Please Close Pending Journey First.");
        	restRsp.setData(DDVM);
        	return restRsp; 
		}
		if(jrn_status==0 && DDVM.getDriverStatus()==0){
			restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("No Journey to Close.");
        	restRsp.setData(DDVM);
        	return restRsp; 
		}
    	rtnJrSt = this.driverDeviceVehicleMappingService.updateJourneyStatus(jrn_status, phone);
    	if(rtnJrSt == true){
    		try {
				
				BookingLease bl = new BookingLease();
				bl.setDriverId(DDVM.getDriverId());
				bl.setVehicleId(DDVM.getVehicleId());
				bl.setCompanyId(DDVM.getDriverClientId());
				bl.setDriverMobile(DDVM.getDriverPhoneNumber());
				bl.setJourneyStartDate(new Date());
				bl.setJourneyEndDate(null);
				bl.setBookingLsStatus(0);
				Integer clientRequestId = this.clientMandateVehicleDeploymentService.getRequestIdByVehicleId(DDVM.getVehicleId());
				if(clientRequestId!=null){
					bl.setClientRequestId(clientRequestId);
				}
				if(jrn_status==1){
					bl.setCreatedBy(by_id);
					bl.setCreatedDateTime(new Date());
					int bookingId = this.bookingLeaseService.saveBooking(bl);
					if(bookingId!=0){
						HashMap<String, String> additionalProperties = new HashMap<String, String>();
						additionalProperties.put("OrederID", ""+bookingId);
						DDVM.setAdditionalProperties(additionalProperties);
						this.driverDeviceVehicleMappingService.updateJourneyStatusId(bookingId, phone);
						DDVM.setBookingId(bookingId);
						DDVM.setDriverStatus(1);
						TrackBooking tbo = new TrackBooking();
						tbo.setTripid(bookingId);
						tbo.setIs_Punch_in(1);
						tbo.setDeviceid(DDVM.getTrackingDeviced());
						tbo.setDropid(1);
						restRsp.setErrorCode("100");
						restRsp.setErrorMesaage("sucesss");
						restRsp.setData(DDVM);
						try{
							bookingLeaseService.hitTrackingService(tbo);
						}catch(Exception er){
							System.out.println(er.getMessage().toString());
						}
					}else{
			    		restRsp.setErrorCode("101");
			        	restRsp.setErrorMesaage("failure");
			    	}					
				}
				if(jrn_status==0){
					bl.setModifiedBy(by_id);
					bl.setUpdatedDateTime(new Date());
					double totalDistance = this.bookingLeaseService.getDeviceLatestDistance(DDVM.getTrackingDeviced());
					bl.setTotalDistance(totalDistance);
					System.out.println ("Tracking device ID = "+DDVM.getTrackingDeviced()+" Distance on controller:"+totalDistance);
					BookingLease pendingBL = this.bookingLeaseService.getPendingBookingLease(bl);
					
					boolean isUpdate = this.bookingLeaseService.updateBooking(bl);
					if(isUpdate){
						if(pendingBL!=null){
							if(pendingBL.getClientRequestId()!=null){
								this.clientMandateVehicleDeploymentService.updateDeploymentVehicleId(pendingBL.getClientRequestId());
							}
						}
						this.driverDeviceVehicleMappingService.updateJourneyStatusId(0, phone);
						DDVM.setBookingId(0);
						DDVM.setDriverStatus(0);
						TrackBooking tbo = new TrackBooking();
						tbo.setTripid(0);
						tbo.setIs_Punch_in(1);
						tbo.setDeviceid(DDVM.getTrackingDeviced());
						tbo.setDropid(1);
						restRsp.setErrorCode("100");
						restRsp.setErrorMesaage("sucesss");
						restRsp.setData(DDVM);
						try{
							bookingLeaseService.hitTrackingService(tbo);
						}catch(Exception er){
							System.out.println(er.getMessage().toString());
						}
					}else{
			    		restRsp.setErrorCode("101");
			        	restRsp.setErrorMesaage("failure to end booking");
			    	}	
				}
				
			} catch (Exception e) {
				restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
				e.printStackTrace();
			}
    	}
    	else{
    		restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("failure");
    	}
    	return restRsp;   
    }
    
    @ResponseBody
	@RequestMapping(value="/driver/getsignedurls3", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public RestResponce getSignedURLS3(@RequestBody S3SignedURL ssu){
    	RestResponce restRsp = new RestResponce();
    	BookingLease bl = new BookingLease();
    	try {
    		
	    	if(ssu!=null){
	    		URL signedURL = S3.getSignedURL(ssu.getBucket(), ssu.getPath());
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");
	        	restRsp.setData(signedURL);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }   
    	}catch(Exception er){
    		er.printStackTrace(); 
    		restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("failure");
    	}
    	
    	return restRsp;
    }
    @ResponseBody
	@RequestMapping(value="/driver/journey/updateclientdoc/", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public RestResponce updateBookingClientDocs(@RequestBody ClientBookingDocs cbd){
    	RestResponce restRsp = new RestResponce();
    	BookingLease bl = new BookingLease();
    	try {
    		bl.setBookingLeaseId(Integer.parseInt(cbd.getOrderId()));
    		bl.setClientOrderNumber(cbd.getClientOrderNumber());
    		bl.setClientOrderDocUrl(cbd.getClientOrderDocUrl());
    		bl.setClientOrderDocUrl2(cbd.getClientOrderDocUrl2());
    		bl.setClientOrderDocUrl3(cbd.getClientOrderDocUrl3());
    		bl.setClientOrderDocUrl4(cbd.getClientOrderDocUrl4());
    		
    		boolean isUpdate = this.bookingLeaseService.updateBookingClientDocs(bl);
	    	if(isUpdate){
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");
	        	restRsp.setData(cbd);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }   
    	}catch(Exception er){
    		er.printStackTrace(); 
    		restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("failure");
    	}
    	
    	return restRsp;
    }
    @ResponseBody
	@RequestMapping(value="/driver/journey/updateclientdocsingle", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public RestResponce updateBookingClientDocSingle(@RequestBody ClientBookingDoc cbd){
    	RestResponce restRsp = new RestResponce();
    	BookingLease bl = new BookingLease();
    	try {
    		/*bl.setBookingLeaseId(Integer.parseInt(cbd.getOrderId()));
    		bl.setClientOrderNumber(cbd.getClientOrderNumber());
    		bl.setClientOrderDocUrl(cbd.getClientOrderDocUrl());
    		
    		boolean isUpdate = this.bookingLeaseService.updateBookingClientDocs(bl);*/
	    	if(cbd!=null){
	    		if(cbd.getClientOrderDocUrl()!=null && !cbd.getClientOrderDocUrl().trim().isEmpty()){
		    		int autoInsIdCBD = this.bookingLeaseService.saveBookingClientDoc(cbd);
		    		if(autoInsIdCBD!=0){
		    			cbd.setId(autoInsIdCBD+"");
			    		restRsp.setErrorCode("100");
			        	restRsp.setErrorMesaage("sucesss");
			        	restRsp.setData(cbd.toString());
		    		} else {
			        	restRsp.setErrorCode("101");
			        	restRsp.setErrorMesaage("failed to upload!!");
			        }  
	    		} else {
		        	restRsp.setErrorCode("101");
		        	restRsp.setErrorMesaage("No File in Input!!");
		        }
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }   
    	}catch(Exception er){
    		er.printStackTrace(); 
    		restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("failure");
        	return restRsp;
    	}
    	
    	return restRsp;
    }
    @ResponseBody
    @RequestMapping("/driver/{phone}")
    public RestResponce getDriverByPhone(@PathVariable("phone") String phone){
    	RestResponce restRsp = new RestResponce();
    	try {
    		DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByPhone(phone);
	    	if(DDVM!=null){
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");
	        	restRsp.setData(DDVM);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }   
	    	}catch(Exception er){
	    		er.printStackTrace(); 
	    		restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	    	}
    	
    	return restRsp;
    }
    @ResponseBody
    @RequestMapping("/driver/byvehicleno/{vehicleno}")
    public RestResponce getDriverByVehicleNo(@PathVariable("vehicleno") String vehicleno){
    	RestResponce restRsp = new RestResponce();
    	try {
    		DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByVehicleNo(vehicleno);
	    	if(DDVM!=null){
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");
	        	restRsp.setData(DDVM);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("No Vehicle Found!!");
	        }   
	    	}catch(Exception er){
	    		er.printStackTrace(); 
	    		restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	    	}
    	
    	return restRsp;
    }
    @ResponseBody
    @RequestMapping("/key/{salt}/driver/heartbeat/{DEVICEID}/{VEHICLENO}/{LAT}/{LONG}/{DISTANCE}/{TRIPID}/{DROPID}/{SPEED}/{PUNCHIN}/{ISIDLE}")
    public RestResponce insertCassandraTrack(
    		@PathVariable("salt") String salt,
    		@PathVariable("DEVICEID") int DEVICEID,
    		@PathVariable("VEHICLENO") String vehicleNo,
    		@PathVariable("LAT") double vehicleLat,
    		@PathVariable("LONG") double vehicleLong,    		
    		@PathVariable("DISTANCE") double vehicleDistance,
    		@PathVariable("TRIPID") int bookingId,
    		@PathVariable("DROPID") int dropId,
    		@PathVariable("SPEED") double speed,
    		@PathVariable("PUNCHIN") int punchIn,
    		@PathVariable("ISIDLE") int isIdle){
    	RestResponce restRsp = new RestResponce();
    	boolean isAauthenticated = false ;
    	if(salt!=null && !salt.equals("")){
			if(salt.equals("SHDx3iPIJ3HS7xiVP9hJ")){
				isAauthenticated = true; 
			}
			else{
				restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("Authentication Failed!");
	        	return restRsp;
			}
		}
    	try {
    		
	    	if(DEVICEID!=0 && isAauthenticated){
	    		//DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByTrackingDeviceId(DEVICEID);
	    		//if(DDVM!=null){
		    		CassandraTrack cto = new CassandraTrack();
		    		/*cto.setDriverId(DDVM.getDriverId());
		        	cto.setCompanySubId(DDVM.getDriverClientId());
		        	cto.setBookingId(bookingId);
		        	cto.setDriverMobile(DDVM.getDriverPhoneNumber());
		        	cto.setVehicleLat(vehicleLat);
		        	cto.setVehicleLong(vehicleLong);
		        	cto.setVehicleNo(vehicleNo);
		        	cto.setVehicleDistance(vehicleDistance);
		        	cto.setDropId(dropId);*/
		    		cto.setDriverId(DEVICEID);
		        	cto.setCompanySubId(DEVICEID);
		        	cto.setBookingId(bookingId);
		        	cto.setDriverMobile(""+DEVICEID);
		        	cto.setVehicleLat(vehicleLat);
		        	cto.setVehicleLong(vehicleLong);
		        	cto.setVehicleNo(vehicleNo);
		        	cto.setVehicleDistance(vehicleDistance);
		        	cto.setDropId(dropId);
		        	cto.setPunchIn(punchIn);
		        	cto.setIsIdle(isIdle);
		        	cto.setSpeed(speed);
		        	cto.setDeviceId(""+DEVICEID);
		        	cto.setCurrentTMS(null);
		        	cto.setDeviceBookingId(null);
		        	if(this.driverDeviceVehicleMappingService.insertCassndraTrack(cto)!=false){
			    		restRsp.setErrorCode("100");
			        	restRsp.setErrorMesaage("sucesss");	        	
			        } else {
			        	restRsp.setErrorCode("101");
			        	restRsp.setErrorMesaage("failure");
			        } 
	    		//}else {
		        //	restRsp.setErrorCode("101");
		        //	restRsp.setErrorMesaage("Device Id Not Mapped To Any Vehicle!!");
		       // } 
	    	}
    	
	    	  
    	}catch(Exception er){
    		er.printStackTrace(); 
    		restRsp.setErrorCode("101");
        	restRsp.setErrorMesaage("failure");
        	return restRsp;
    	}
    	
    	return restRsp;
    }
    @ResponseBody
	@RequestMapping(value="/postCurrentLocations", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce postCurrentLocation(@RequestBody List<CassandraTrack> ct){ //
		
		
    	RestResponce restRsp = new RestResponce();
		try{
			if(ct!=null){
				HashMap<String, HashMap<String, String>> respBookingDeviceAllMap = new HashMap<String, HashMap<String, String>>();  
				for (CassandraTrack locationElement : ct) // or sArray
                {
					HashMap<String, String> respBookingDeviceSingleMap = new HashMap<String, String>();
					if(this.driverDeviceVehicleMappingService.insertCassndraTrack2(locationElement)){
					//int k=1;
					//int j=1;
					//if(j==k){
						respBookingDeviceSingleMap.put("responceCode", "100");
						respBookingDeviceSingleMap.put("responceMessage", "Success");
					} else{
						respBookingDeviceSingleMap.put("responceCode", "101");
						respBookingDeviceSingleMap.put("responceMessage", "Failure");
					}					
					respBookingDeviceAllMap.put(locationElement.getCurrentTMS(), respBookingDeviceSingleMap);
                }
				//writer.println(cls.toString());
				//writer.close();
				
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");	 
	        	restRsp.setData(respBookingDeviceAllMap);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure! No Data Sent");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
    @ResponseBody
    @RequestMapping("/updatetrackdevicebookingid/{devicebookingid}/{bookingid}")
    public RestResponce updateTrackDeviceBookingId(@PathVariable("devicebookingid") String deviceBookingId,
    		@PathVariable("bookingid") Integer bookingId){
    	RestResponce restRsp = new RestResponce();
    	try {
    		boolean utd = this.driverDeviceVehicleMappingService.updateTrackDeviceBookingId(deviceBookingId, bookingId);
	    	if(utd!=false){
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }   
	    	}catch(Exception er){
	    		er.printStackTrace(); 
	    		restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	    	}
    	
    	return restRsp;
    }
    @ResponseBody
	@RequestMapping(value="/driverPartPayment/{amount_paid}/{vehicle_number}/{comment}/{paid_by}/{driver_mobile}", method = RequestMethod.GET)
	private RestResponce driverPartPayment(@PathVariable("driver_mobile") String driver_mobile, @PathVariable("amount_paid") String amount_paid, @PathVariable("vehicle_number") String vehicle_number, @PathVariable("comment") String comment, @PathVariable("paid_by") String paid_by){
		
		
    	RestResponce restRsp = new RestResponce();
    	DriverPartPayment dpp=new DriverPartPayment();
    	dpp.setDriverMobile(driver_mobile);
    	dpp.setAmountPaid(amount_paid);
    	dpp.setVehicleNumber(vehicle_number);
    	dpp.setComment(comment);
    	dpp.setPaidBy(paid_by);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date_paid_on = sdf.format(new Date());
    	dpp.setDatePaidOn(date_paid_on);
    	
		
    	/*boolean rtnJrSt = false;
    	rtnJrSt = this.driverPartPaymentMappingService.insertDriverPartPayment(dpp);*/
    	
		try{
			if(dpp!=null){
				/*	PrintWriter writer = new PrintWriter("D:/code/trux-api-v2/data.txt", "UTF-8");
				
				for (DriverPartPayment partPaymentElement : dpp) // or sArray
                {
					writer.println(partPaymentElement.toString());*/
					this.driverPartPaymentMappingService.insertDriverPartPayment(dpp);
                /*}
				//select * from  current_vehicle_location (device_id, loct_time, loct_lattitude, loct_logitude) values (1, '2015-11-01T09:00+1300', 24.9999, 77.234235)
				writer.println(dpp.toString());
				writer.close();*/
				
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");	 
	        	//restRsp.setData(dpp.toString());
	        } 
			else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure! No Data Sent");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
}
