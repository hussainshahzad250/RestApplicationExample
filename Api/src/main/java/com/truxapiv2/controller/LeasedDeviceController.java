package com.truxapiv2.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.truxapiv2.model.CurrentLocation;
import com.truxapiv2.model.DriverAttenAttributes;
import com.truxapiv2.model.DriverDeviceVehicleMapping;
import com.truxapiv2.model.DriverLoginJson;
import com.truxapiv2.model.DropJsonFromDevice;
import com.truxapiv2.model.DropLease;
import com.truxapiv2.model.AppVersion;
import com.truxapiv2.model.BookingJsonFromDevice;
import com.truxapiv2.model.BookingLease;
import com.truxapiv2.model.CassandraTrack;
import com.truxapiv2.model.ClientBookingDoc;
import com.truxapiv2.model.ClientMandateVehicleDeployment;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.service.BookingLeaseService;
import com.truxapiv2.service.ClientMandateVehicleDeploymentService;
import com.truxapiv2.service.DriverAttendanceService;
import com.truxapiv2.service.DriverDeviceVehicleMappingService;
import com.truxapiv2.service.ShipmentDataNaaptolService;
import com.truxapiv2.model.RestResponce;
import com.truxapiv2.model.TrackBooking;
import com.truxapiv2.model.ResultObject;
import com.truxapiv2.model.ShipmentDataNaaptol;

@Controller  
@RequestMapping("/leasedvicecontroller")  
public class LeasedDeviceController {
	
	@Autowired
	private BookingLeaseService bookingLeaseService;
	@Autowired
	private DriverAttendanceService driverAttendanceService;
	@Autowired
	private ClientMandateVehicleDeploymentService clientMandateVehicleDeploymentService;
	@Autowired
	private ShipmentDataNaaptolService shipmentDataNaaptolService;
	@Autowired
	private MessageSource messageSource;
	private DriverDeviceVehicleMappingService driverDeviceVehicleMappingService;
	@Autowired(required=true)
	@Qualifier(value="driverDeviceVehicleMappingService")
	public void setDriverDeviceVehicleMappingService(DriverDeviceVehicleMappingService ds){
		this.driverDeviceVehicleMappingService = ds;
	} 
	@ResponseBody
	@RequestMapping(value="/insertbooking", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce insertBookingFromDevice(@RequestBody List<BookingJsonFromDevice> bjfd){ 
		
		
    	RestResponce restRsp = new RestResponce();
		try{
			BookingLease bl = new BookingLease();			
			JSONObject respBookingDeviceAll = new JSONObject();
			
			if(bjfd!=null){
				
				HashMap<String, HashMap<String, String>> respBookingDeviceAllMap = new HashMap<String, HashMap<String, String>>();
				for (BookingJsonFromDevice bjfdElement : bjfd) // or sArray
	            {
					HashMap<String, String> respBookingDeviceSingleMap = new HashMap<String, String>();
					String responceCode = "100";
					String responceMessage = "Success";
					int bkPkId = this.bookingLeaseService.saveMapBkgJsonToBkgLease(bl, bjfdElement);
					if(bkPkId>1){
						DropJsonFromDevice[] dropListA = bjfdElement.getDeliveryStartEndPointRecord();
						if(dropListA.length >0){
							List<DropJsonFromDevice> dropList = Arrays.asList((DropJsonFromDevice[])dropListA);
							
							if(dropList.size()>0){ System.out.println(dropList.size());
								DropLease dl = new DropLease(); dl.setBookingLeaseId(bkPkId);
								HashMap<String, HashMap<String, String>> respDropDeviceAllMap = new HashMap<String, HashMap<String, String>>();
								for (DropJsonFromDevice dropD : dropList) { 
									HashMap<String, String> respDropSingle = new HashMap<String, String>();
									int dropPkId = this.bookingLeaseService.saveMapBkgDropJsonToBkgLeaseDrop(dl, dropD);
									if(dropPkId==0){ responceCode = "101"; responceMessage = "Failure"; }
									respDropSingle.put("dropServerId", ""+dropPkId);
									respDropSingle.put("responceCode", responceCode);
									respDropSingle.put("responceMessage", responceMessage);
									respDropDeviceAllMap.put(dropD.getDropid(), respDropSingle);
									//System.out.println(respDropDeviceAllMap);
									respBookingDeviceSingleMap.put("drops", respDropDeviceAllMap.toString());
								}
								
							}
						}
					}
					if(bkPkId>1){
						ClientBookingDoc[] docListA = bjfdElement.getBookingDocs();
						if(docListA.length >0){
							List<ClientBookingDoc> docList = Arrays.asList((ClientBookingDoc[])docListA);
							
							if(docList.size()>0){ System.out.println(docList.size());
								for (ClientBookingDoc docBk : docList) { 
									docBk.setOrderId(""+bkPkId);
									int autoInsIdCBD = this.bookingLeaseService.saveBookingClientDoc(docBk);
								}
								
							}
						}
					}
					if(bkPkId==0){ responceCode = "101"; responceMessage = "Failure"; }
					else if(bkPkId==1){ responceCode = "102"; responceMessage = "Already Inserted"; }
					respBookingDeviceSingleMap.put("bookingServerId", ""+bkPkId);
					respBookingDeviceSingleMap.put("responceCode", responceCode);
					respBookingDeviceSingleMap.put("responceMessage", responceMessage);
					respBookingDeviceAllMap.put(bjfdElement.getBookingid(), respBookingDeviceSingleMap);
	            }
				
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");	 
	        	restRsp.setData(respBookingDeviceAllMap);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
	@ResponseBody
	@RequestMapping(value="/insertbooking2", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce insertBookingFromDevice2(@RequestBody List<BookingJsonFromDevice> bjfd){ 
		
		
    	RestResponce restRsp = new RestResponce();
		try{
			BookingLease bl = new BookingLease();			
			JSONObject respBookingDeviceAll = new JSONObject();
			
			if(bjfd!=null){
				
				HashMap<String, HashMap<String, String>> respBookingDeviceAllMap = new HashMap<String, HashMap<String, String>>();
				for (BookingJsonFromDevice bjfdElement : bjfd) // or sArray
	            {
					HashMap<String, String> respBookingDeviceSingleMap = new HashMap<String, String>();
					String responceCode = "100";
					String responceMessage = "Success";
					int bkPkId = this.bookingLeaseService.saveMapBkgJsonToBkgLease2(bl, bjfdElement);
					if(bjfdElement.getDeliveryStartEndPointRecord()!=null){
						DropJsonFromDevice[] dropListA = bjfdElement.getDeliveryStartEndPointRecord();
						if(dropListA.length >0){
							List<DropJsonFromDevice> dropList = Arrays.asList((DropJsonFromDevice[])dropListA);
							
							if(dropList.size()>0){ System.out.println(dropList.size());
								DropLease dl = new DropLease(); dl.setBookingLeaseId(bkPkId);
								HashMap<String, HashMap<String, String>> respDropDeviceAllMap = new HashMap<String, HashMap<String, String>>();
								for (DropJsonFromDevice dropD : dropList) { 
									HashMap<String, String> respDropSingle = new HashMap<String, String>();
									int dropPkId = this.bookingLeaseService.saveMapBkgDropJsonToBkgLeaseDrop(dl, dropD);
									if(dropPkId==0){ responceCode = "101"; responceMessage = "Failure"; }
									respDropSingle.put("dropServerId", ""+dropPkId);
									respDropSingle.put("responceCode", responceCode);
									respDropSingle.put("responceMessage", responceMessage);
									respDropDeviceAllMap.put(dropD.getDropid(), respDropSingle);
									//System.out.println(respDropDeviceAllMap);
									respBookingDeviceSingleMap.put("drops", respDropDeviceAllMap.toString());
								}
								
							}
						}
	            	}
					if(bjfdElement.getBookingDocs()!=null){
						ClientBookingDoc[] docListA = bjfdElement.getBookingDocs();
						if(docListA.length >0){
							List<ClientBookingDoc> docList = Arrays.asList((ClientBookingDoc[])docListA);							
							if(docList.size()>0){ System.out.println(docList.size());
								for (ClientBookingDoc docBk : docList) { 
									docBk.setOrderId(""+bkPkId);
									int autoInsIdCBD = this.bookingLeaseService.saveBookingClientDoc(docBk);
								}							
							}
						}
					}
					if(bkPkId==0){
						restRsp.setErrorCode("101");
			        	restRsp.setErrorMesaage("Booking Server ID Not Found!!");
			        	return restRsp;
					} else {
						if(bkPkId==1){ responceCode = "102"; responceMessage = "Already Inserted"; }
						respBookingDeviceSingleMap.put("bookingServerId", ""+bkPkId);
						respBookingDeviceSingleMap.put("responceCode", responceCode);
						respBookingDeviceSingleMap.put("responceMessage", responceMessage);
						respBookingDeviceAllMap.put((bjfdElement.getBookingServerId()!=null && Integer.parseInt(bjfdElement.getBookingServerId())!=0 )?(bjfdElement.getBookingServerId()):(bjfdElement.getBookingid()), respBookingDeviceSingleMap);
					}
					
	            }
				
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");	 
	        	restRsp.setData(respBookingDeviceAllMap);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
	@ResponseBody
	@RequestMapping(value="/insertbookingstops", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce insertBookingStops(@RequestBody List<DropJsonFromDevice> djfd){ 
		
		
    	RestResponce restRsp = new RestResponce();
		try{
						
			if(djfd!=null){
				
				ArrayList<HashMap<String, String>> respStopList = new ArrayList<HashMap<String, String>>();
				for (DropJsonFromDevice bjfdElement : djfd) // or sArray
	            {
					DropLease dl = new DropLease();
					HashMap<String, String> respBookingDeviceSingleMap = new HashMap<String, String>();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					dl.setBookingLeaseId(Integer.parseInt(bjfdElement.getDropBookingId()));dl.setDropLocation(bjfdElement.getDropLocation());dl.setDroped_boxes(Integer.parseInt(bjfdElement.getDropedBoxes()));
					dl.setCreatedTime(sdf.parse(sdf.format(new Date())));
					String responceCode = "100";
					String responceMessage = "Success";
					int drPkId = this.bookingLeaseService.saveDropLease(dl);
					
					if(drPkId==0){ responceCode = "101"; responceMessage = "Failure"; }
					else if(drPkId==1){ responceCode = "102"; responceMessage = "Already Inserted"; }
					respBookingDeviceSingleMap.put("bookingServerId", ""+drPkId);
					respBookingDeviceSingleMap.put("responceCode", responceCode);
					respBookingDeviceSingleMap.put("responceMessage", responceMessage);
					respStopList.add(respBookingDeviceSingleMap);
	            }
				
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");	 
	        	restRsp.setData(respStopList);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
	@ResponseBody
	@RequestMapping(value="/insertbookingstops2", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce insertBookingStops2(@RequestBody List<DropJsonFromDevice> djfd){ 
		
		
    	RestResponce restRsp = new RestResponce();
		try{
						
			if(djfd!=null){
				
				//ArrayList<HashMap<String, String>> respStopList = new ArrayList<HashMap<String, String>>();
				HashMap<String, HashMap<String, String>> respBookingDeviceAllMap = new HashMap<String, HashMap<String, String>>();
				String responceCode = "100";
				String responceMessage = "Success";
				for (DropJsonFromDevice dropD : djfd) // or sArray
	            {
					DropLease dl = new DropLease();
					HashMap<String, String> respBookingDeviceSingleMap = new HashMap<String, String>();
					HashMap<String, String> respDropSingle = new HashMap<String, String>();
					int drPkId = this.bookingLeaseService.saveMapBkgDropJsonToBkgLeaseDrop(dl, dropD);
					if(drPkId!=1 && drPkId!=0 && dropD.getAwbNumber()!=null){
						ShipmentDataNaaptol sdn = new ShipmentDataNaaptol();
						sdn.setAwbNumber(dropD.getAwbNumber());
						sdn.setNdr(dropD.getDropNdrResponce());
						this.shipmentDataNaaptolService.updateShipmentDataByAWBNumberNDR(sdn);
					}
					
					if(drPkId==0){ responceCode = "101"; responceMessage = "Failure"; }
					else if(drPkId==1){ responceCode = "102"; responceMessage = "Already Inserted"; }
					respBookingDeviceSingleMap.put("dropServerId", ""+drPkId);
					respBookingDeviceSingleMap.put("responceCode", responceCode);
					respBookingDeviceSingleMap.put("responceMessage", responceMessage);
					respBookingDeviceAllMap.put(dropD.getDropid(), respBookingDeviceSingleMap);
	            }
				
	    		restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("sucesss");	 
	        	restRsp.setData(respBookingDeviceAllMap);
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("failure");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
	/**  For getting non closed Orders */
    @ResponseBody
	@RequestMapping(value="/getnonclosedbookings", method = RequestMethod.GET)
	private RestResponce getNonClosedBookings(HttpServletRequest request,HttpServletResponse response,
			@RequestParam int userId,
			@RequestParam(required = false) String currentDate){ //List<CurrentLocation>
		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(userId!=0){
				
	        	List<?> bookingData;
	        	if(currentDate!=null){
	        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        	Date crDate = sdf.parse(currentDate);
	        		bookingData = this.bookingLeaseService.getNonClosedBookingByUserId(userId, crDate);
	        	}else{
	        		bookingData = this.bookingLeaseService.getNonClosedBookingByUserId(userId);
	        	}
				
	        	if(bookingData!= null && !bookingData.isEmpty()){				
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(bookingData);						
		        } else {
		        	restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("All Trips Have Been Closed For the Day or No Data Found");
		        }
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("Wrong Inputs!!");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
    /**  For getting non closed PunchIns */
    @ResponseBody
	@RequestMapping(value="/getnonclosedpunchins", method = RequestMethod.GET)
	private RestResponce getNonClosedPunchIns(HttpServletRequest request,HttpServletResponse response,
			@RequestParam int userId,
			@RequestParam(required = false) String sDate){ //List<CurrentLocation>
		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(userId!=0){
				Date crDate = null;
				if(sDate!=null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					crDate = sdf.parse(sDate);
				}
	        	List<?> punchInData = this.driverAttendanceService.getNonClosedPunchIns(userId, crDate);
	        	
				
	        	if(punchInData!= null && !punchInData.isEmpty()){				
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(punchInData);						
		        } else {
		        	restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Pending PunchIn Found");
		        }
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("Wrong Inputs!!");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
    /**  For punching in and out for driver_attendance_leased_vehicles */
    @ResponseBody
    @RequestMapping("/driver/punch/{vehicle_no}/{p_flag}/{agent_id}/{timestamp}")
    public RestResponce trackPunchDriverWithLatLong(@PathVariable("vehicle_no") String vehicle_no, @PathVariable("p_flag") int punch_status, @PathVariable("agent_id") int agent_id, @PathVariable("timestamp") String timestamp){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByVehicleNo(vehicle_no);
			if(punch_status == (int)punch_status){
				//this.driverDeviceVehicleMappingService.updateLoginStatusById(punch_status, driver_id);
				Long timestampL = Long.parseLong(timestamp);
				DriverAttenAttributes daa = new DriverAttenAttributes(); 
				daa.setAgentId(agent_id);
				daa.setDriverAPKVersion(null);
				daa.setDriverId(DDVM.getDriverId());
				daa.setpFlag(punch_status);
				daa.setTimestamp(timestampL);
				daa.setVehicleNumber(DDVM.getVehicleNumber());
				daa.setCheckPending(true);
				daa.setClientId(DDVM.getSubClientId());
				//DDVM.setLoginStatus(punch_status);
				ResultObject bookingData = this.driverAttendanceService.processDriverAttendance(daa);
	        	if(bookingData!=null){
					
					if(bookingData.isResultStatus()==false){
						restRsp.setErrorCode("101");
						restRsp.setErrorMesaage(bookingData.getResultMesaage());	
					}
					else{
						if(DDVM.getTrackingDeviced()!=null && DDVM.getTrackingDeviced()!=0 ){
							TrackBooking tbo = new TrackBooking();
							tbo.setTripid(0);
							tbo.setDropid(0);
							tbo.setIs_Punch_in(punch_status);
							tbo.setDeviceid(DDVM.getTrackingDeviced());
							try{
								bookingLeaseService.hitTrackingService(tbo);
							}catch(Exception er){
								System.out.println(er.getMessage().toString());
								restRsp.setErrorCode("101");
								restRsp.setErrorMesaage("Tracking Device Error!!");
								return restRsp;
							}
						}
						restRsp.setErrorCode("100");
						restRsp.setErrorMesaage("sucesss");	
					}
					DriverDeviceVehicleMapping DDVM2 = this.driverDeviceVehicleMappingService.getDriverByVehicleNo(vehicle_no);
					restRsp.setData(DDVM2);						
		        } else {
		        	restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("Something Went Wrong.");
		        }
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
    /**  For punching in Start KM */
    @ResponseBody
    @RequestMapping("/driver/punch/updatestartkm/{driver_id}/{km}")
    public RestResponce updatePunchStartKm(@PathVariable("driver_id") int driver_id, @PathVariable("km") int km){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(km == (int)km){
				
				if(this.driverAttendanceService.updateStartKM(driver_id, new Date(), km)){				
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(this.driverDeviceVehicleMappingService.getDriverById(driver_id));						
		        } else {
		        	restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("Please Do PunchIn Before Adding Start KM.");	  
					restRsp.setData(this.driverDeviceVehicleMappingService.getDriverById(driver_id));						
		        }
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
    /**  For punching in Start KM */
    @ResponseBody
    @RequestMapping("/driver/punch/updateendkm/{driver_id}/{km}")
    public RestResponce updatePunchEndKm(@PathVariable("driver_id") int driver_id, @PathVariable("km") int km){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(km == (int)km){
				
				if(this.driverAttendanceService.updateEndKM(driver_id, new Date(), km)){				
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(this.driverDeviceVehicleMappingService.getDriverById(driver_id));						
		        } else {
		        	restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("Please Do PunchIn Before Adding End KM.");	  
					restRsp.setData(this.driverDeviceVehicleMappingService.getDriverById(driver_id));						
		        }
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
    /**  For getting App Versions */
    @ResponseBody
    @RequestMapping("/getappversion/{app_name}")
    public RestResponce getAppVersions(@PathVariable("app_name") String app_name){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(app_name!=null){			
				restRsp.setErrorCode("100");
				restRsp.setErrorMesaage("sucesss");	  
				restRsp.setData(this.bookingLeaseService.getAppVersion(app_name));						
		        
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
    /**  For getting Assigned Sub Clients to user/agent */
    @ResponseBody
    @RequestMapping("/getassignendsubclients/{id}")
    public RestResponce getAssignendSubclients(@PathVariable("id") int userId){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(userId == (int)userId){		
				List subData = this.clientMandateVehicleDeploymentService.getSubClientIdMappingByUserId(userId);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    /**  For getting Valid Mandate By Sub client */
    @ResponseBody
    @RequestMapping("/getvalidmandate/{id}")
    public RestResponce getValidMandates(@PathVariable("id") int subclientId){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(subclientId == (int)subclientId){		
				List subData = this.clientMandateVehicleDeploymentService.getMandateBySubclient(subclientId);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    /**  For mandate detail By mandate id */
    @ResponseBody
    @RequestMapping("/getmandatedetailbyid/{id}")
    public RestResponce getMandateDetailById(@PathVariable("id") int mandateId){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(mandateId == (int)mandateId){		
				List subData = this.clientMandateVehicleDeploymentService.getMandateDetailsByMandateId(mandateId);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    /**  For mandate detail By mandate id */
    @ResponseBody
    @RequestMapping("/getmandatedeployedlist/{id}")
    public RestResponce getMandateDeployedList1(@PathVariable("id") int mandateDetailId){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(mandateDetailId == (int)mandateDetailId){	
				
				List subData = this.clientMandateVehicleDeploymentService.getDeploymentListByMandateDetailId(mandateDetailId);
				
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    @RequestMapping("/getmandatedeployedlist/{id}/{request_date}")
    public RestResponce getMandateDeployedList(@PathVariable("id") int mandateDetailId, @PathVariable("request_date") String request_date){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(mandateDetailId == (int)mandateDetailId){	
				Long timestampL = Long.parseLong(request_date);
				//List subData = this.clientMandateVehicleDeploymentService.getDeploymentListByMandateDetailId(mandateDetailId);
				List subData = this.clientMandateVehicleDeploymentService.getDeploymentListByMandateDetailIdAndRequestDate(mandateDetailId, request_date);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    /**  For mandate detail By mandate id */
    @ResponseBody
    @RequestMapping("/getnonneployednehclenynubclient/{id}/{vehicle_type}/{body_type}")
    public RestResponce getNonDeployedVehcleBySubclient(@PathVariable("id") int subclientId, @PathVariable("vehicle_type") String vehicleType, @PathVariable("body_type") String bodyType){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(subclientId == (int)subclientId){		
				List subData = this.clientMandateVehicleDeploymentService.getNonDeployedVehcleBySubclient(subclientId, vehicleType, bodyType);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    /**  For mandate detail By mandate id */
    @ResponseBody
    @RequestMapping("/getnonneployednehclenynubclient2/{id}/{vehicle_type}/{body_type}")
    public RestResponce getNonDeployedVehcleBySubclient2(@PathVariable("id") int subclientId, @PathVariable("vehicle_type") String vehicleType, @PathVariable("body_type") String bodyType){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(subclientId == (int)subclientId){		
				List subData = this.clientMandateVehicleDeploymentService.getNonDeployedVehcleBySubclient2(subclientId, vehicleType, bodyType);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    /**  For mandate stat by agent id */
    @ResponseBody
    @RequestMapping("/getmandatestatbyagentid/{id}")
    public RestResponce getMandateStatByAgentId(@PathVariable("id") int agentId){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(agentId == (int)agentId){		
				List subData = this.clientMandateVehicleDeploymentService.getMandateStatByAgentId(agentId);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    /**  For updating mandate vehicle */
    @ResponseBody
	@RequestMapping(value="/updatingmandatevehicle", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce updatingMandateVehicle(@RequestBody List<ClientMandateVehicleDeployment> cmvdList){ //List<CurrentLocation>
		
		
    	RestResponce restRsp = new RestResponce();
		try{ 
		
			if(cmvdList!=null){		
				int mandateDeatilId = 0 ;
				int clientRequestId = 0;
				String requestDate = "";
				for (ClientMandateVehicleDeployment cmvd : cmvdList) // or sArray
	            {
					this.clientMandateVehicleDeploymentService.updateDeployment(cmvd);
					mandateDeatilId = cmvd.getMandateDetailId();
					requestDate = cmvd.getRequestDate();
					clientRequestId = cmvd.getClientRequestId();
	            }
				System.out.println(mandateDeatilId);
	        	restRsp.setErrorCode("100");
	        	restRsp.setErrorMesaage("Vehicle Deployed Successfully");
	        	if(clientRequestId!=0){
	        		restRsp.setData(this.clientMandateVehicleDeploymentService.getDeploymentListByClientRequestId(clientRequestId));
	        	} else {
	        		if(requestDate!=null){
	        			restRsp.setData(this.clientMandateVehicleDeploymentService.getDeploymentListByMandateDetailIdAndRequestDate(mandateDeatilId, requestDate));
	        		} else {
	        			restRsp.setData(this.clientMandateVehicleDeploymentService.getDeploymentListByMandateDetailId(mandateDeatilId));
	        		}
	        	}	        	
		      
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
    /**  For getting clients mandate requests by agent id */
    @ResponseBody
    @RequestMapping("/getclientmandaterequests/{id}/{level}")
    public RestResponce getClientMandateRequests(@PathVariable("id") int agentId, @PathVariable("level") int level){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if(agentId == (int)agentId){		
				List subData = this.clientMandateVehicleDeploymentService.getClientMandateRequests(agentId, level);
				if(subData != null && subData.size()>0 ){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(subData);	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
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
    
    /**  For getting clients mandate requests by agent id */
    @ResponseBody
    @RequestMapping("/updateclientmandaterequests/{request_id}/{id}/{nov}")
    public RestResponce updateClientMandateRequests(@PathVariable("request_id") int requestId, @PathVariable("id") int userId, @PathVariable("nov") int nov){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try{
			if( (requestId == (int)requestId) && (userId == (int)userId) && (nov == (int)nov) ){		
				boolean subData = this.clientMandateVehicleDeploymentService.updateClientMandateRequests(requestId, userId, nov);
				if(subData != false){
					restRsp.setErrorCode("100");
					restRsp.setErrorMesaage("sucesss");	  
					restRsp.setData(this.clientMandateVehicleDeploymentService.getClientMandateRequests(userId, 1));	
				} else{
					restRsp.setErrorCode("101");
					restRsp.setErrorMesaage("No Data Found");	
				}
		        
	        } else {
	        	restRsp.setErrorCode("101");
	        	restRsp.setErrorMesaage("Invalid Inputs!!");
	        }	
		
		}catch(Exception er){
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
    
	/**  For vehicle location and booking id mapping */
    @ResponseBody
	@RequestMapping(value="/postCurrentLocation", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce postCurrentLocation(@RequestBody CurrentLocation cls){ //List<CurrentLocation>
		
		
    	RestResponce restRsp = new RestResponce();
		try{
			if(cls!=null){
				
				CassandraTrack cto = new CassandraTrack();
				int deviceid = Integer.parseInt(cls.getDeviceid());
	    		
				cto.setDriverId(deviceid);
	        	cto.setCompanySubId(deviceid);
	        	cto.setBookingId(deviceid);
	        	cto.setDriverMobile(""+deviceid);
	        	cto.setVehicleLat(Double.parseDouble(cls.getLattitude()));
	        	cto.setVehicleLong(Double.parseDouble(cls.getLogitude()));
	        	cto.setVehicleNo("0000");
	        	cto.setVehicleDistance(0.00);
	        	cto.setDropId(0);
	        	if(this.driverDeviceVehicleMappingService.insertCassndraTrack(cto)!=false){
		    		restRsp.setErrorCode("100");
		        	restRsp.setErrorMesaage("sucesss");	        	
		        } else {
		        	restRsp.setErrorCode("101");
		        	restRsp.setErrorMesaage("failure");
		        }
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
    // for punchIn from LVT
    @ResponseBody
	@RequestMapping(value="/postAttendanceLogin", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce postAttendanceLogin(@RequestBody DriverLoginJson DLJ){ 
    	
    	RestResponce restRsp = new RestResponce();
		try{
			if(DLJ!=null){		
				DriverDeviceVehicleMapping DDVM = this.driverDeviceVehicleMappingService.getDriverByVehicleNo(DLJ.getVehicleNo());
				if(DDVM!=null){
					
					ResultObject loginLVTDriver  = this.driverDeviceVehicleMappingService.loginLVTDriver(DLJ);
					if(loginLVTDriver.isResultStatus()){
						ResultObject bookingData;
						DriverAttenAttributes daa = new DriverAttenAttributes(); 
						daa.setAgentId(0);
						daa.setDriverAPKVersion(DLJ.getApkVersion());
						daa.setDriverId(DDVM.getDriverId());
						daa.setpFlag(DLJ.getPunchIn());
						daa.setOpeningKm(DLJ.getOpeningKm());
						daa.setClosingKm(DLJ.getClosingKm());
						daa.setClientId(DDVM.getSubClientId());
						daa.setVehicleNumber(DDVM.getVehicleNumber());
						if(DLJ.getLastLogoutTime()!=null){
							Long timestampL = Long.parseLong(DLJ.getLastLogoutTime().trim());
							daa.setTimestamp(timestampL);
							bookingData = this.driverAttendanceService.processDriverAttendance(daa);
						} else{
							daa.setTimestamp((long) 100);
							bookingData = this.driverAttendanceService.processDriverAttendance(daa);
						}
						
						if(bookingData!=null){
							
							if(bookingData.isResultStatus()==false){
								restRsp.setErrorCode("101");
								restRsp.setErrorMesaage(bookingData.getResultMesaage());	
							}else{						
								restRsp.setErrorCode("100");
								restRsp.setErrorMesaage("sucesss");	
							}							  
							restRsp.setData(this.driverDeviceVehicleMappingService.getDriverByVehicleNo(DLJ.getVehicleNo()));						
				        } else {
				        	restRsp.setErrorCode("101");
							restRsp.setErrorMesaage("Something Went Wrong.");
				        }
					} else{
						restRsp.setErrorCode("101");
						restRsp.setErrorMesaage(loginLVTDriver.getResultMesaage());
					}
				} else {
		        	restRsp.setErrorCode("101");
		        	restRsp.setErrorMesaage("No Vehicle Found!!");
		        }
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
    
    /**  For getting Vehicle Type */
    @ResponseBody
    @RequestMapping("/getVehicleType")
    public RestResponce getVehicleType(){		
		
    	RestResponce restRsp = new RestResponce();
    	restRsp.setErrorCode("101");
    	restRsp.setErrorMesaage("Something went wrong!!");
		try {
			restRsp.setErrorCode("100");
			restRsp.setErrorMesaage("sucesss");
			restRsp.setData(this.bookingLeaseService.getVehicleType());

		} catch (Exception er) {
			er.printStackTrace();
			return restRsp;
		}
		return restRsp;
	}
    
    /** Search Vehicle */
    @ResponseBody
	@RequestMapping("/searchVehicle/{city}/{hub}/{cluster}/{vehicle_type}/{association}/{body_type}/{paging}")
	public RestResponce searchVehicle(@PathVariable("city") Integer city, @PathVariable("hub") Integer hub, @PathVariable("cluster") Integer cluster, @PathVariable("vehicle_type") Integer vehicle_type, @PathVariable("association") String association, @PathVariable("body_type") String body_type, @PathVariable("paging") Integer paging) {
		try{
			RestResponce restResponce = new RestResponce();
			
			restResponce.setErrorCode("100");
			restResponce.setErrorMesaage("Success");
			
			List<?> count = (List<?>) bookingLeaseService.countRecord(city,hub,cluster,vehicle_type,association,body_type);
			
			restResponce.setCount(count.size());
			restResponce.setData(bookingLeaseService.searchVehicle(city,hub,cluster,vehicle_type,association,body_type,paging));
			
			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
    }
    
    /** Search Vehicle send SMS*/
    @ResponseBody
	@RequestMapping("/sendVehicleSMS/{city}/{hub}/{cluster}/{vehicle_type}/{association}/{body_type}/{language}")
	public RestResponce sendVehicleSMS(@PathVariable("city") Integer city, @PathVariable("hub") Integer hub, @PathVariable("cluster") Integer cluster, @PathVariable("vehicle_type") Integer vehicle_type, @PathVariable("association") String association, @PathVariable("body_type") String body_type, @PathVariable("language") String language) {
		try{
			RestResponce restResponce = new RestResponce();
			
			restResponce.setErrorCode("100");
			restResponce.setErrorMesaage("Success");
			
			HashMap<String, List<?>> cdt = bookingLeaseService.sendVehicleSMS(city,hub,cluster,vehicle_type,association,body_type,language); 
			
			
			
			ControllerDAOTracker c = bookingLeaseService.fillCommunicationSms(cdt,language);
			
				restResponce.setErrorCode(c.getErrorCode());
				restResponce.setErrorMesaage(c.getErrorMessage());
			
			return restResponce;
		} catch (Exception e) {
			RestResponce restResponcee = new RestResponce();
			restResponcee.setErrorCode("101");
			restResponcee.setErrorMesaage(e.toString());
			return restResponcee;
		}
    }
    
    } // End Controller
