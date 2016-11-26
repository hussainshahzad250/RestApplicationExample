package com.truxapiv2.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "driver_attendance_leased_vehicles")
public class DriverAttendance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;	
 	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "companyId")
	private Integer companyId;
	 
	@Column(name = "attendanceDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date AttendanceDate;

	@Column(name = "punchIn")   
    @Temporal(TemporalType.TIMESTAMP)
	private Date punchIn;

	@Column(name = "punchOut")  
    @Temporal(TemporalType.TIMESTAMP)
	private Date punchOut;

	@Column(name = "createdDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "createdBy")
	private Integer createdBy;

	@Column(name = "modifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	

	@Column(name = "modifiedBy")
	private Integer modifiedBy;
	
	@Column(name = "openingkm")
	private Integer openingKilometer;
	
	@Column(name = "closingkm")
	private Integer closingKilometer;
	
	@Column(name = "tolltax")
	private Integer tolltax;
	
	@Column(name = "noofboxes")
	private Integer noofboxes;
	

	@Column(name = "clientsubid")
	private Integer clientSubId;
	
	@Column(name = "from_app")
	private Integer fromApp;
	
	@Column(name = "vehicle_number")
	private String vehicleNumber;
	
	@Column(name = "driver_apk_version")
	private String driverAPKVersion;
	
	@Transient
	private Boolean checkPending=false;
	
	public String getDriverAPKVersion() {
		return driverAPKVersion;
	}

	public void setDriverAPKVersion(String driverAPKVersion) {
		this.driverAPKVersion = driverAPKVersion;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Integer getFromApp() {
		return fromApp;
	}

	public void setFromApp(Integer fromApp) {
		this.fromApp = fromApp;
	}

	@Transient
	private String statusMessage;
	
	@Transient
	private int punchIngStatus;

	public DriverAttendance() {
		super();
	}
	
	

	public DriverAttendance(Integer driverId, Integer companyId,
			Integer standId, Date attendanceDate, Date punchIn, Date punchOut,
			Date createdDate, Integer createdBy, Date modifiedDate,
			Integer modifiedBy) {
		super(); 
		this.driverId = driverId;
		this.companyId = companyId;
		this.AttendanceDate = attendanceDate;
		this.punchIn = punchIn;
		this.punchOut = punchOut;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

 	public Date getAttendanceDate() {
		return AttendanceDate;
	}

	public void setAttendanceDate(Date attendanceDate) {
		AttendanceDate = attendanceDate;
	}

	public Date getPunchIn() {
		return punchIn;
	}

	public void setPunchIn(Date punchIn) {
		this.punchIn = punchIn;
	}

	public Date getPunchOut() {
		return punchOut;
	}

	public void setPunchOut(Date punchOut) {
		this.punchOut = punchOut;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	public Integer getOpeningKilometer() {
		return openingKilometer;
	}



	public void setOpeningKilometer(Integer openingKilometer) {
		this.openingKilometer = openingKilometer;
	}



	public Integer getClosingKilometer() {
		return closingKilometer;
	}



	public void setClosingKilometer(Integer closingKilometer) {
		this.closingKilometer = closingKilometer;
	}



	public Integer getTolltax() {
		return tolltax;
	}



	public void setTolltax(Integer tolltax) {
		this.tolltax = tolltax;
	}



	public String getStatusMessage() {
		return statusMessage;
	}



	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}



	public Integer getNoofboxes() {
		return noofboxes;
	}



	public void setNoofboxes(Integer noofboxes) {
		this.noofboxes = noofboxes;
	}



	public Integer getClientSubId() {
		return clientSubId;
	}



	public void setClientSubId(Integer clientSubId) {
		this.clientSubId = clientSubId;
	}



	public int getPunchIngStatus() {
		return punchIngStatus;
	}



	public void setPunchIngStatus(int punchIngStatus) {
		this.punchIngStatus = punchIngStatus;
	}

	public Boolean getCheckPending() {
		return checkPending;
	}

	public void setCheckPending(Boolean checkPending) {
		this.checkPending = checkPending;
	}
	
	

}
