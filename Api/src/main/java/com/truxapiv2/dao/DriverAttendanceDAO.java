package com.truxapiv2.dao;

import java.util.Date;
import java.util.List;

import com.truxapiv2.model.DriverAttendance;

public interface DriverAttendanceDAO {

	public int saveDriverAttendance(DriverAttendance dto);
	public boolean updateStartKM(int driver_id, Date curdate, int km);
	public boolean updateEndKM(int driver_id, Date curdate, int km);
	boolean updateLoginStatusInDDVM(int flag, int driver_id, Date crDate, int login_id);
	public List<Object[]> getNonClosedPunchIns(int userId, Date crDate);
	
}
