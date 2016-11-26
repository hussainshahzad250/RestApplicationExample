package com.truxapiv2.service;

import java.util.List;

import com.truxapiv2.model.DriverLoginHistory;

public interface DriverLoginHistoryService {

	public int saveDriverLoginHistory(DriverLoginHistory dto);
	public DriverLoginHistory getDriverLoginHistory(DriverLoginHistory dto);
	public List<DriverLoginHistory> getDriverLoginHistoryList(DriverLoginHistory dto);
	
}
