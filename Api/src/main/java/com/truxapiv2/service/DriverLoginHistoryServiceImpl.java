package com.truxapiv2.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truxapiv2.dao.DriverLoginHistoryDAO;
import com.truxapiv2.model.DriverLoginHistory;

@Service
public class DriverLoginHistoryServiceImpl implements DriverLoginHistoryService {
	
	private DriverLoginHistoryDAO driverLoginHistoryDAO;
	
	public void setDriverLoginHistoryDAO(DriverLoginHistoryDAO driverLoginHistoryDAO) {
		this.driverLoginHistoryDAO = driverLoginHistoryDAO;
	}


	@Override
	@Transactional
	public int saveDriverLoginHistory(DriverLoginHistory dto) {
		return this.driverLoginHistoryDAO.saveDriverLoginHistory(dto);
	}

	@Override
	@Transactional
	public DriverLoginHistory getDriverLoginHistory(DriverLoginHistory dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<DriverLoginHistory> getDriverLoginHistoryList(DriverLoginHistory dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
