package com.truxapiv2.dao;

import com.truxapiv2.model.DropLease;
public interface DropLeaseDAO {

	public int saveDropLease(DropLease dto);
	public boolean isDropFromDeviceExists(String deviceDropLeasesId);
}
