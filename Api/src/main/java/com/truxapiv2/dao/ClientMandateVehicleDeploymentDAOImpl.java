package com.truxapiv2.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.ClientMandateVehicleDeployment;
import com.truxapiv2.model.ClientMandateVehicleRequest;

@Repository
public class ClientMandateVehicleDeploymentDAOImpl implements ClientMandateVehicleDeploymentDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientMandateVehicleDeploymentDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@Override
	public boolean updateDeployment(ClientMandateVehicleDeployment cmvd) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if(cmvd.getId()!=0){
				String strSql= "update ClientMandateVehicleDeployment set vehicleNo=:vn, reportingTime=:rt, isActive=:ia, modifieddate=:md, modifiedby=:mb ";
				if(cmvd.getVehicleType()!=null){
					strSql=strSql+" ,vehicleType=:vt ";
				}
				if(cmvd.getBodyType()!=null){
					strSql=strSql+" ,bodyType=:bt ";
				}
				if(cmvd.getAdvancePayment()!=null){
					strSql=strSql+" ,advancePayment=:ap ";
				}
				if(cmvd.getCostToDriver()!=null){
					strSql=strSql+" ,costToDriver=:ctd ";
				}
				if(cmvd.getRevenueToCompany()!=null){
					strSql=strSql+" ,revenue=:rv ";
				}
				strSql=strSql+" where id=:id "; 
				Query query=session.createQuery(strSql);
				query.setInteger("id", cmvd.getId());
				query.setParameter("vn", cmvd.getVehicleNo());
				query.setParameter("rt", cmvd.getReportingTime());
				query.setParameter("ia", cmvd.getIsActive());
				query.setParameter("md", cmvd.getModifieddate());
				query.setParameter("mb", cmvd.getModifiedby());
				if(cmvd.getVehicleType()!=null){
					query.setParameter("vt", cmvd.getVehicleType());
				}
				if(cmvd.getBodyType()!=null){
					query.setParameter("bt", cmvd.getBodyType());
				}
				if(cmvd.getAdvancePayment()!=null){
					query.setParameter("ap", cmvd.getAdvancePayment());
				}
				if(cmvd.getCostToDriver()!=null){
					query.setParameter("ctd", cmvd.getCostToDriver());
				}
				if(cmvd.getRevenueToCompany()!=null){
					query.setParameter("rv", cmvd.getRevenueToCompany());
				}
				int modifications=query.executeUpdate();			
				if(modifications!=0){
					if(cmvd.getDeploymentRemark()!=null){
						if(cmvd.getDeploymentRemark().trim() != null){
							Query query_d=session.createSQLQuery("UPDATE driver_registration AS DR JOIN `vehicle_registration` AS VR ON DR.`id`=VR.driver_id SET DR.`deployment_remark`='"+cmvd.getDeploymentRemark().trim()+"' WHERE VR.vehicle_number='"+cmvd.getVehicleNo().trim()+"'");
							query_d.executeUpdate();
						}
					}
					if(cmvd.getIsActive()==0 && cmvd.getMandateDetailId()!=0){
						Query query_up_cmd=session.createSQLQuery("UPDATE client_mandate_detail SET no_of_vehicles = no_of_vehicles-1, modifiedDate=:md, modifiedBy=:mb WHERE mandatedetailid=:man_dtl_id");
						query_up_cmd.setInteger("man_dtl_id", cmvd.getMandateDetailId());
						query_up_cmd.setParameter("md", new Date());
						query_up_cmd.setParameter("mb", cmvd.getModifiedby());
						query_up_cmd.executeUpdate();
					}
					return true;
				}else{
					return false;
				}
			} else {
				session.save(cmvd);
				if(cmvd.getMandateDetailId()!=0){
				Query query_up_cmd=session.createSQLQuery("UPDATE client_mandate_detail SET no_of_vehicles = no_of_vehicles+1, modifiedDate=:md, modifiedBy=:mb WHERE mandatedetailid=:man_dtl_id");
				query_up_cmd.setInteger("man_dtl_id", cmvd.getMandateDetailId());
				query_up_cmd.setParameter("md", new Date());
				query_up_cmd.setParameter("mb", cmvd.getModifiedby());
				query_up_cmd.executeUpdate();
				}
			}
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		}
		return false;
	}
	
	public List<ClientMandateVehicleDeployment> getDeploymentListByMandateDetailId(int mandateId){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createQuery("select id AS ID, mandateDetailId AS mandateDetailId, vehicleNo AS vehicleNo, reportingTime AS reportingTime, isActive AS isActive, modifieddate AS modifiedDate, modifiedby AS modifiedBy, vehicleType AS vehicleType, bodyType AS bodyType, clientRequestId AS clientRequestId, costToDriver AS costToDriver, advancePayment as advancePayment, revenueToCompany as revenueToCompany from ClientMandateVehicleDeployment WHERE mandateDetailId=:mdId AND isActive=1").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.setParameter("mdId", mandateId);
			List<?> dataList = query.list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return (List<ClientMandateVehicleDeployment>) dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}		
	}
	@Override
	public List<ClientMandateVehicleDeployment> getDeploymentListByMandateDetailIdAndRequestDate(int mandateId, String request_date){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "SELECT CVD.`id` AS ID, CVD.`mandate_detail_id` AS mandateDetailId, CVD.`vehicle_no` AS vehicleNo, CVD.`reporting_time` AS reportingTime, IF(CVD.`is_active`=1, 1, 0) AS isActive, CVD.`modifieddate` AS modifiedDate, CVD.`modifiedby` AS modifiedBy, CVD.`vehicle_type` AS vehicleType, CVD.`body_type` AS bodyType, CVD.`client_request_id` AS clientRequestId, CVD.`costToDriver` AS costToDriver, CVD.`advancePayment` AS advancePayment, CVD.`revenueToCompany` AS revenueToCompany ";
			sql = sql+" FROM `client_vehicle_deployment` AS CVD LEFT JOIN `client_mandate_request` AS CMR ON CVD.`client_request_id`=CMR.`request_id`  WHERE CVD.`mandate_detail_id`="+mandateId+" AND is_active=1 ";
			Long timestampL = Long.parseLong(request_date);
			if(timestampL!=100){
				Date requestDate = new Date(timestampL); 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String req_date_str = sdf.format(requestDate);
				sql = sql + " AND DATE(CMR.`created_date`)='"+req_date_str+"'";
			} else {
				
			}
			
			Query query=session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			List<?> dataList = query.list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return (List<ClientMandateVehicleDeployment>) dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}		
	}
	@Override
	public List<ClientMandateVehicleDeployment> getDeploymentListByClientRequestId(int requestId){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createQuery("select id AS ID, mandateDetailId AS mandateDetailId, vehicleNo AS vehicleNo, reportingTime AS reportingTime, isActive AS isActive, modifieddate AS modifiedDate, modifiedby AS modifiedBy, vehicleType AS vehicleType, bodyType AS bodyType, clientRequestId AS clientRequestId, costToDriver AS costToDriver, advancePayment as advancePayment, revenueToCompany as revenueToCompany from ClientMandateVehicleDeployment WHERE clientRequestId=:crId AND isActive=1").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.setParameter("crId", requestId);
			List<?> dataList = query.list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return (List<ClientMandateVehicleDeployment>) dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
		
	}
	public List<Object[]> getNonDeployedVehcleBySubclient(int subclientId, String vehicleType, String bodyType){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "SELECT VR.`vehicle_number`, VR.`vehicle_type`, VR.`vehicleBody` FROM vehicle_registration AS VR WHERE VR.`subclient_id`="+subclientId+" ";
			if(vehicleType != null && !vehicleType.isEmpty()){
				if(!vehicleType.equalsIgnoreCase("no_vehicle")){
					sql = sql + " AND VR.`vehicle_type`= '"+vehicleType+"' ";
				}
			}
			if(bodyType != null && !bodyType.isEmpty()){
				if(!bodyType.equalsIgnoreCase("no_body")){
					sql = sql + " AND VR.`vehicleBody`= '"+bodyType+"' ";
				}
			}
			sql = sql + " AND VR.`vehicle_number` NOT IN (SELECT CVD.`vehicle_no` FROM client_vehicle_deployment AS CVD WHERE  CVD.`is_active`=1 AND CVD.`vehicle_no` IS NOT NULL) ";
			List<Object[]> dataList=session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			if(dataList != null && !dataList.isEmpty()){	        	
	        	return dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
		
	}
	public List<Object[]> getNonDeployedVehcleBySubclient2(int subclientId, String vehicleType, String bodyType){
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String sql = "SELECT GROUP_CONCAT(VR.`vehicle_number`)  AS vehicle_number, VR.`vehicle_type`, VR.`vehicleBody` FROM vehicle_registration AS VR WHERE VR.`subclient_id`="+subclientId+" ";
			if(vehicleType != null && !vehicleType.isEmpty()){
				if(!vehicleType.equalsIgnoreCase("no_vehicle")){
					sql = sql + " AND VR.`vehicle_type`= '"+vehicleType+"' ";
				}
			}
			if(bodyType != null && !bodyType.isEmpty()){
				if(!bodyType.equalsIgnoreCase("no_body")){
					sql = sql + " AND VR.`vehicleBody`= '"+bodyType+"' ";
				}
			}
			sql = sql + " AND VR.`vehicleStatus`='free' AND VR.`vehicle_number` NOT IN (SELECT CVD.`vehicle_no` FROM client_vehicle_deployment AS CVD WHERE  CVD.`is_active`=1 AND CVD.`vehicle_no` IS NOT NULL)  GROUP BY VR.`vehicle_type`, VR.`vehicleBody` ";
			List<Object[]> dataList=session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			if(dataList != null && !dataList.isEmpty()){
				
	        	return dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
		
	}
	public List<Object[]> getSubClientIdMappingByUserId(int userId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createSQLQuery("SELECT clientsubid AS clientSubId, CSM.`subName` FROM client_truxuser_mapping AS CTM INNER JOIN client_sub_master AS CSM ON CTM.`clientsubid`=CSM.`idClientSubMaster` WHERE userid=:uId && is_active=1 GROUP BY CTM.`clientsubid` ORDER BY CSM.`subName` ASC").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.setParameter("uId", userId);
			List<Object[]> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return  dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	public List<Object[]> getMandateBySubclient(int subclientId) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createSQLQuery("SELECT CM.`clientmandateid` AS mandateId, CM.`mandate_type` AS mandateType FROM client_mandate AS CM WHERE CM.`clientsubid`="+subclientId+" AND '"+sdf.format(new Date())+"' BETWEEN DATE(CM.`mandate_start_date`) AND DATE(CM.`mandate_end_date`)").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Object[]> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return  dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	public List<Object[]> getMandateDetailsByMandateId(int mandateId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query=session.createSQLQuery("SELECT * FROM client_mandate_detail WHERE mandateid="+mandateId+" AND is_active=1 ORDER BY vehicle_type, body_type ASC").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Object[]> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return  dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	public List<Object[]> getMandateStatByAgentId(int agentId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String sql_man=" SELECT CSM.subname, CM.`mandate_type`, CMD.`vehicle_type`, CMD.`body_type`, CMD.`mandatedetailid`,COUNT(IF((CVD.vehicle_no IS NULL OR CVD.vehicle_no=''), CVD.id, NULL)) AS nov_needed, COUNT(CVD.id) AS nov_in_deployment, CMD.`no_of_vehicles` AS nov_in_mandate, (COUNT(CVD.id) - CMD.`no_of_vehicles`) AS nov_need_to_be_removed, COUNT(IF((CVD.`by_client`=1 && CVD.`is_active`=1), CVD.id, NULL)) AS nov_by_client ";
			sql_man=sql_man+ " FROM `client_vehicle_deployment` AS CVD LEFT JOIN `client_mandate_detail` AS CMD ON CVD.`mandate_detail_id` = CMD.`mandatedetailid` LEFT JOIN `client_mandate` AS CM ON CMD.`mandateid`=CM.clientmandateid LEFT JOIN client_sub_master AS CSM ON CSM.`idClientSubMaster`=CM.clientsubid ";			  
			sql_man=sql_man+ " WHERE CVD.is_active=1 AND '"+sdf.format(new Date())+"' BETWEEN DATE(CM.`mandate_start_date`) AND DATE(CM.`mandate_end_date`) AND CM.`mandate_type` NOT IN ('Box', 'Weight') AND CM.clientsubid IN ";
			sql_man=sql_man+ " ( SELECT clientsubid FROM client_truxuser_mapping AS CTM  WHERE userid="+agentId+" && is_active=1 GROUP BY CTM.`clientsubid` ORDER BY CSM.`subName` ASC ) ";
			sql_man=sql_man+ " GROUP BY CMD.`mandatedetailid` HAVING (nov_need_to_be_removed > 0 OR nov_needed > 0 ) ORDER BY nov_by_client DESC ";
			Query query=session.createSQLQuery(sql_man).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Object[]> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return  dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	@Override
	public List<Object[]> getClientMandateRequests(int agentId, int level) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String man_sql="SELECT CMR.`request_id`, CMR.`box_weight`, CMR.`vehicle_type`, CMR.`body_type`, CMR.`mandate_type`, CSM.`subName`, CMR.`no_of_vehicles`, CMR.mandate_detail_id, CMR.client_sub_id, CMR.destination, CMR.source, (SELECT COUNT(CVD.id) FROM client_vehicle_deployment AS CVD WHERE (CVD.vehicle_no IS NULL OR CVD.vehicle_no='') AND CVD.client_request_id=CMR.`request_id`  AND CVD.is_active=1) AS to_be_deployed FROM client_mandate_request AS CMR LEFT JOIN client_sub_master AS CSM ON CMR.`client_sub_id`=CSM.`idClientSubMaster` WHERE 1 ";
			if(level==1){
				man_sql = man_sql + " AND CMR.`mandate_type` IN ('Box', 'Weight') ";
			}
			if(level==2){
				man_sql = man_sql + " ";
			}
			man_sql = man_sql + " AND CMR.request_status=1 AND CMR.`client_sub_id` IN  ( SELECT clientsubid FROM client_truxuser_mapping AS CTM  WHERE userid="+agentId+" && is_active=1 GROUP BY CTM.`clientsubid` ORDER BY CSM.`subName` ASC ) HAVING (to_be_deployed >0 OR CMR.`no_of_vehicles`=0) ";
			Query query=session.createSQLQuery(man_sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<?> dataList = query.list();
	        if(dataList != null && !dataList.isEmpty()){
	        	
	        	for (Object record : dataList) {
	        		Map dataListMap = (Map) record;
	        		if(dataListMap.get("request_id")!=null){
						Query queryD=session.createQuery("select id AS ID, mandateDetailId AS mandateDetailId, vehicleNo AS vehicleNo, reportingTime AS reportingTime, isActive AS isActive, modifieddate AS modifiedDate, modifiedby AS modifiedBy, vehicleType AS vehicleType, bodyType AS bodyType, clientRequestId AS clientRequestId, costToDriver AS costToDriver, advancePayment as advancePayment, revenueToCompany as revenueToCompany from ClientMandateVehicleDeployment WHERE clientRequestId=:crId AND isActive=1 AND (vehicleNo IS NULL OR vehicleNo='') ").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
						queryD.setParameter("crId", dataListMap.get("request_id"));
						List<?> dataListD = queryD.list();
						 if(dataListD != null && !dataListD.isEmpty()){
							 dataListMap.put("deployment_data", dataListD);
						}
	        		}
	        	}
	        	
	        	return  (List<Object[]>) dataList;
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}
	@Override
	public boolean updateClientMandateRequests(int requestId, int userId, Integer nov) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			ClientMandateVehicleRequest cmvr =  (ClientMandateVehicleRequest) session.get(ClientMandateVehicleRequest.class, requestId);
			
			int insertId=0;
			if(nov!=null){
				Query query_up_cmvr=session.createQuery("update ClientMandateVehicleRequest set noOfVehicles=noOfVehicles+"+nov+", modifiedDate=:md, modifiedBy=:mb, requestStatus=1  where id=:request_id ");
				query_up_cmvr.setInteger("request_id", cmvr.getId());
				//query_up_cmvr.setInteger("nov", nov);
				query_up_cmvr.setParameter("md", new Date());
				query_up_cmvr.setParameter("mb", userId);
				int modifications_cmvr=query_up_cmvr.executeUpdate();
				if(modifications_cmvr!=0){
					Query query_up_cmd=session.createSQLQuery("UPDATE client_mandate_detail SET no_of_vehicles = no_of_vehicles+"+nov+", modifiedDate=:md, modifiedBy=:mb WHERE mandatedetailid=:man_dtl_id");
					query_up_cmd.setInteger("man_dtl_id", cmvr.getMandateDetailId());
					query_up_cmd.setParameter("md", new Date());
					query_up_cmd.setParameter("mb", userId);
					int modifications=query_up_cmd.executeUpdate();
					if(modifications!=0){
						for(int i=0;i<nov;i++){
							System.out.println("save="+i);
							ClientMandateVehicleDeployment cvd = new ClientMandateVehicleDeployment();
							cvd.setMandateDetailId(cmvr.getMandateDetailId());
							cvd.setMandateType(cmvr.getMandate_type());
							cvd.setVehicleType(cmvr.getVehicleType());
							cvd.setBodyType(cmvr.getBodyType());
							cvd.setReportingTime(cmvr.getReportingTime());
							cvd.setCreatedBy(cmvr.getCreatedBy());
							cvd.setIsActive(1);
							cvd.setByClient(1);
							cvd.setClientRequestId(cmvr.getId());
							cvd.setModifieddate(null);
							insertId = (Integer) session.save(cvd);
						}
					}
				}
				
			}
			return (insertId==0)?(false):(true);
			
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		}
	}
	@Override
	public Integer getRequestIdByVehicleId(int vehicleId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();			
			List<?> dataList=session.createSQLQuery("SELECT CVD.`id` FROM `client_vehicle_deployment` AS CVD LEFT JOIN `vehicle_registration` VR ON CVD.`vehicle_no`=VR.`vehicle_number` WHERE  CVD.`is_active`=1 AND CVD.`client_request_id`!=0  AND VR.`id`="+vehicleId+"").list();
			//AND CURRENT_DATE() BETWEEN DATE(CVD.`createddate`) AND DATE(CVD.`createddate`)+1
	     
	        if(dataList != null && !dataList.isEmpty()){	        	
	        	return (Integer) dataList.get(0);
	        } else{
	        	return null;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return null;
		}
	}	
	@Override
	public Boolean updateDeploymentVehicleId(int deploymentId) {
		try { System.out.println("called updateDeploymentVehicleId");
			Session session = this.sessionFactory.getCurrentSession();	
			int modification = 0;
			Query query=session.createSQLQuery("UPDATE client_vehicle_deployment AS CVD JOIN `client_mandate_detail` AS CMD ON CVD.`mandate_detail_id`=CMD.`mandatedetailid` SET CVD.is_active = 0, CMD.`no_of_vehicles`=CMD.`no_of_vehicles`-1 WHERE id=:deployment_id");
			query.setInteger("deployment_id", deploymentId);		
			
			modification = query.executeUpdate();	
			
	        if(modification!=0){	        	
	        	return true;
	        } else{
	        	return false;
	        }
		}catch(Exception er){
			System.out.println(er.getMessage().toString()); return false;
		}
	}
	
}
