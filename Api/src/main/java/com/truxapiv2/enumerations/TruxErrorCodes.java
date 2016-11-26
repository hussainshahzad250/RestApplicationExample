package com.truxapiv2.enumerations;


public enum TruxErrorCodes {
	 
	  INVALID_DRIVER("E001", "Invalid Driver"),
	  DRIVER_MULTI_DROP_POINT_MESSAGE("E066","Fail your Ride. Reason behind Driver Phone number Or Agent ID Number not match. "); 


	  private final String code;
	  private final String description;

	  private TruxErrorCodes(String code, String description) {
	    this.code = code;
	    this.description = description;
	  }


	  public String getCode() {
		return code;
	  }

	  public String getDescription() {
		return description;
	  }

	@Override
	  public String toString() {
	    return code + ": " + description;
	  }
	}
