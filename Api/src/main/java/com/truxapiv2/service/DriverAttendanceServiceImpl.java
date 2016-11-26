package com.truxapiv2.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truxapiv2.dao.DriverAttendanceDAO;
import com.truxapiv2.model.DriverAttenAttributes;
import com.truxapiv2.model.DriverAttendance;
import com.truxapiv2.model.ResultObject;

@Service
public class DriverAttendanceServiceImpl implements DriverAttendanceService {
	
	@Autowired
	private DriverAttendanceDAO driverAttendanceDAO;
	
	public void setDriverAttendanceDAO(DriverAttendanceDAO driverLoginHistoryDAO) {
		this.driverAttendanceDAO = driverLoginHistoryDAO;
	}
	
	@Override
	@Transactional
	public int saveDriverAttendance(DriverAttendance dto) {
		return this.driverAttendanceDAO.saveDriverAttendance(dto);
	}


	@Override
	@Transactional
	public ResultObject processDriverAttendance(DriverAttenAttributes daa) {
		
		String rslt_str = "";
		int rslt=0;
		if(daa.getpFlag()==1){
			DriverAttendance da = new DriverAttendance();
		da.setDriverId(daa.getDriverId());
		da.setAttendanceDate(new Date());
		da.setCreatedDate(new Date());
		da.setPunchIn(new Date());
		da.setPunchIngStatus(daa.getpFlag());
		da.setCreatedBy(daa.getAgentId());
		if(daa.getDriverAPKVersion()!=null) da.setDriverAPKVersion(daa.getDriverAPKVersion());
		if(daa.getCheckPending()!=null && daa.getCheckPending()==true) da.setCheckPending(true);
		if(daa.getOpeningKm()!=null) da.setOpeningKilometer(daa.getOpeningKm());
		if(daa.getVehicleNumber()!=null) da.setVehicleNumber(daa.getVehicleNumber());
		if(daa.getClientId()!=null) da.setClientSubId(daa.getClientId());
		rslt = this.saveDriverAttendance(da);
		}
		if(daa.getpFlag()==0){
			Date punchOutDate ;
			if(daa.getTimestamp()!=100){
				punchOutDate = new Date(daa.getTimestamp()); //System.out.println(timestamp);
			} else {
				punchOutDate = new Date(); 
			}
			DriverAttendance da = new DriverAttendance();
			da.setDriverId(daa.getDriverId());
			da.setPunchOut(punchOutDate);
			da.setModifiedDate(new Date());
			da.setPunchIngStatus(daa.getpFlag());
			da.setModifiedBy(daa.getAgentId());
			if(daa.getCheckPending()!=null && daa.getCheckPending()==true) da.setCheckPending(true);
			if(daa.getClosingKm()!=null) da.setClosingKilometer(daa.getClosingKm());
			System.out.println(daa.getClosingKm());
			rslt = this.saveDriverAttendance(da);
		}
		ResultObject resultObject = new ResultObject();
		if(rslt==1){ resultObject.setResultStatus(true); resultObject.setResultMesaage("Logged In");  }
		else if (rslt==2){ resultObject.setResultStatus(false); resultObject.setResultMesaage("Already Logged In For The Day."); }
		else if (rslt==3){ resultObject.setResultStatus(true); resultObject.setResultMesaage("Logged Out"); }
		else if (rslt==4){ resultObject.setResultStatus(false); resultObject.setResultMesaage("Already Logged Out For The Day Or No Log Out Pending."); }
		else if (rslt==5){ resultObject.setResultStatus(false); resultObject.setResultMesaage("Punch Out Pending From Last Login."); }
		else { resultObject.setResultStatus(false); resultObject.setResultMesaage("Something Wrong."); }
		return resultObject;

	}

	@Override
	@Transactional
	public boolean updateStartKM(int driver_id, Date curdate, int km) {
		return this.driverAttendanceDAO.updateStartKM(driver_id, curdate, km);
	}

	@Override
	@Transactional
	public boolean updateEndKM(int driver_id, Date curdate, int km) {
		return this.driverAttendanceDAO.updateEndKM(driver_id, curdate, km);
	}

	@Override
	@Transactional
	public List<Object[]> getNonClosedPunchIns(int userId, Date crDate) {
		// TODO Auto-generated method stub
		return this.driverAttendanceDAO.getNonClosedPunchIns(userId, crDate);
	}

	

}
