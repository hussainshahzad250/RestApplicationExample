package com.truxapiv2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.truxapiv2.model.RestResponce;
import com.truxapiv2.model.ResultObject;
import com.truxapiv2.model.ShipmentDataNaaptol;
import com.truxapiv2.service.ShipmentDataNaaptolService;

@Controller  
@RequestMapping("/clientcustom")  
public class ClientCustomController {
	
	@Autowired
	private ShipmentDataNaaptolService shipmentDataNaaptolService;
	
	@ResponseBody
	@RequestMapping("/getawbdata/{awbnumber}")
    public RestResponce getAWBData(@PathVariable("awbnumber") String awbnumber){
		RestResponce restRsp = new RestResponce();
		ResultObject ro = this.shipmentDataNaaptolService.getShipmentDataByAWBNumber(awbnumber);
		if(ro.isResultStatus()){
			restRsp.setData(ro.getResultData());
			restRsp.setErrorCode("100");
			restRsp.setErrorMesaage("success");
		} else{
			restRsp.setErrorCode("101");
			restRsp.setErrorMesaage(ro.getResultMesaage());
		}
        return restRsp;
    }
	@ResponseBody
	@RequestMapping(value="/updateAWBData", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	private RestResponce updateAWBData(@RequestBody ShipmentDataNaaptol SDN){ 
		RestResponce restRsp = new RestResponce();
		ResultObject ro = this.shipmentDataNaaptolService.updateShipmentDataByAWBNumberNDR(SDN);
		if(ro.isResultStatus()){
			restRsp.setErrorCode("100");
			restRsp.setErrorMesaage("success");
		} else{
			restRsp.setErrorCode("101");
			restRsp.setErrorMesaage("AWB Number IS Not Available.");
		}
        return restRsp;
    }
	

}