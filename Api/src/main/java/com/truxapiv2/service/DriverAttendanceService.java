package com.truxapiv2.service;


import java.util.Date;
import java.util.List;

import com.truxapiv2.model.DriverAttenAttributes;
import com.truxapiv2.model.DriverAttendance;
import com.truxapiv2.model.ResultObject;

public interface DriverAttendanceService {

	public int saveDriverAttendance(DriverAttendance dto);
	//public ResultObject processDriverAttendance(int driver_id, int p_flag, int agent_id, long timestamp);
	public boolean updateStartKM(int driver_id, Date curdate, int km);
	public boolean updateEndKM(int driver_id, Date curdate, int km);
	public ResultObject processDriverAttendance(DriverAttenAttributes daa);
	public List<Object[]> getNonClosedPunchIns(int userId, Date crDate);
	
}
