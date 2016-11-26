package com.truxapiv2.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truxapiv2.model.CommunicationEmail;
import com.truxapiv2.model.CommunicationEmailArchive;
import com.truxapiv2.model.CommunicationSMS;
import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.model.TransporterClientOrderMapping;
import com.truxapiv2.model.TransporterClientOrders;
import com.truxapiv2.model.TransporterFreightChart;
import com.truxapiv2.model.TransporterLoginHistory;
import com.truxapiv2.model.TransporterOrderFollowUp;
import com.truxapiv2.model.TransporterRegistration;
import com.truxapiv2.model.TransporterVehicleRegistration;

public class TransporterRegistrationDAOImpl implements TransporterRegistrationDAO {

	private static final Logger logger = LoggerFactory.getLogger(TransportBankDetailsDAOImpl.class);

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public ControllerDAOTracker saveTransporterRegistration(TransporterRegistration dto) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterRegistration> objList = session.createQuery(
				"FROM TransporterRegistration WHERE mobileNumber='" + dto.getMobileNumber() + "' AND id=" + dto.getId())
				.list();
		// "FROM TransporterRegistration WHERE mobileNumber ='" + mobileNumber +
		// "'"
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterRegistration at : objList) {
					if (dto != null && dto.getTransporterCompanyName() != null) {
						if (!dto.getTransporterCompanyName().equals(""))
							at.setTransporterCompanyName(dto.getTransporterCompanyName());
					}
					if (dto != null && dto.getContactPersonName() != null) {
						if (!dto.getContactPersonName().equals(""))
							at.setContactPersonName(dto.getContactPersonName());
					}
					if (dto != null && dto.getMobileNumber() != null) {
						if (!dto.getMobileNumber().equals(""))
							at.setMobileNumber(dto.getMobileNumber());
					}
					if (dto != null && dto.getPassword() != null) {
						at.setPassword(dto.getPassword());
					}
					if (dto != null && dto.getEmail() != null) {
						if (!dto.getEmail().equals(""))
							at.setEmail(dto.getEmail());
					}
					if (dto != null && dto.getCity() != null) {
						at.setCity(dto.getCity());
					}
					if (dto != null && dto.getState() != null) {
						at.setState(dto.getState());
					}
					if (dto != null && dto.getPincode() != null) {
						at.setPincode(dto.getPincode());
					}
					if (dto != null && dto.getVehicleCategory() != null) {
						if (!dto.getVehicleCategory().equals(""))
							at.setVehicleCategory(dto.getVehicleCategory());
					}
					if (dto != null && dto.getCreatedBy() != null) {
						at.setCreatedBy(dto.getCreatedBy());
					}
					if (dto != null && dto.getCreatedOn() != null) {
						at.setCreatedOn(dto.getCreatedOn());
					}
					if (dto != null && dto.getModifiedBy() != null) {
						at.setModifiedBy(dto.getModifiedBy());
					}
					if (dto != null && dto.getModifiedOn() != null) {
						at.setModifiedOn(dto.getModifiedOn());
					}
					if (dto != null && dto.getIsActive() != null) {
						at.setIsActive(dto.getIsActive());
					}
					at.setModifiedOn(new Date());
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterRegistration.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterRegistration dts = (TransporterRegistration) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Transporter updated successfully");
					cdt.setData(dts);
					return cdt;

				}
			} catch (Exception er) {
				er.printStackTrace();
			}
		} else {

			Session sessions = sessionFactory.openSession();
			try {
				Transaction tx = sessions.beginTransaction();
				sessions.saveOrUpdate(dto);
				tx.commit();
				DetachedCriteria maxId = DetachedCriteria.forClass(TransporterRegistration.class)
						.setProjection(Projections.max("id"));
				List<TransporterRegistration> cmdList = sessions.createCriteria(TransporterRegistration.class)
						.add(Property.forName("id").eq(maxId)).list();
				sessions.clear();
				sessions.close();
				if (cmdList != null && cmdList.size() > 0) {
					TransporterRegistration dts = cmdList.get(0);
					cdt.setSuccess(true);
					cdt.setErrorCode("100");
					cdt.setErrorMessage("Transporter saved successfully");
					cdt.setData(dts);
					return cdt;
				} else {
					TransporterRegistration dts = cmdList.get(0);
					cdt.setSuccess(false);
					cdt.setErrorCode("101");
					cdt.setErrorMessage("Request does not processed !");
					cdt.setData(dts);
					return cdt;
				}
			} catch (Exception er) {

				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;
	}

	@Override
	public ControllerDAOTracker getTransporterByMobile(String mobileNumber) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<TransporterRegistration> dList = session
					.createQuery("FROM TransporterRegistration WHERE mobileNumber='" + mobileNumber + "'").list();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setTransporterRegistration(dList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Transporter not found/registered.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}

	}

	@Override
	public ControllerDAOTracker getTransporterDetails(String mobileNumber) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<TransporterRegistration> dList = session
					.createQuery("FROM TransporterRegistration WHERE mobileNumber='" + mobileNumber + "'").list();

			if (dList.size() > 0) {

				////////////////////// Fetching city, state and country
				////////////////////// ////////////////

				Session session2 = sessionFactory.openSession();
				List<?> as = null;

				String query = "SELECT c.`city_id`, c.`city`, s.`state_id`, s.`state_name`, cn.`id`, cn.`value` FROM countries AS cn LEFT JOIN `states` AS s ON cn.`id`=s.`country_id` LEFT JOIN `cities` AS c ON s.`state_id`=c.`state_id` WHERE s.`state_id`="
						+ dList.get(0).getState() + " AND c.`city_id`=" + dList.get(0).getCity();
				as = session2.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
				session2.close();

				// if(as != null && as.size() >0){
				// cdt.setCsc(as.get(0));
				// }

				////////////////////// Fetching city, state and country
				////////////////////// ////////////////

				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				if (as != null && as.size() > 0)
					dList.get(0).setCsc(as.get(0));
				cdt.setData(dList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Transporter not found/registered.");
				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker updateTransporterRegistration(TransporterRegistration dto) {

		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterRegistration> objList = session.createQuery(
				"FROM TransporterRegistration WHERE mobileNumber='" + dto.getMobileNumber() + "' AND id=" + dto.getId())
				.list();

		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterRegistration at : objList) {
					if (dto != null && dto.getTransporterCompanyName() != null) {
						if (!dto.getTransporterCompanyName().equals(""))
							at.setTransporterCompanyName(dto.getTransporterCompanyName());
					}
					if (dto != null && dto.getContactPersonName() != null) {
						if (!dto.getContactPersonName().equals(""))
							at.setContactPersonName(dto.getContactPersonName());
					}
					if (dto != null && dto.getMobileNumber() != null) {
						if (!dto.getMobileNumber().equals(""))
							at.setMobileNumber(dto.getMobileNumber());
					}
					if (dto != null && dto.getPassword() != null) {
						at.setPassword(dto.getPassword());
					}
					if (dto != null && dto.getEmail() != null) {
						if (!dto.getEmail().equals(""))
							at.setEmail(dto.getEmail());
					}
					if (dto != null && dto.getCity() != null) {
						at.setCity(dto.getCity());
					}
					if (dto != null && dto.getState() != null) {
						at.setState(dto.getState());
					}
					if (dto != null && dto.getPincode() != null) {
						at.setPincode(dto.getPincode());
					}
					if (dto != null && dto.getVehicleCategory() != null) {
						if (!dto.getVehicleCategory().equals(""))
							at.setVehicleCategory(dto.getVehicleCategory());
					}
					if (dto != null && dto.getCreatedBy() != null) {
						at.setCreatedBy(dto.getCreatedBy());
					}
					if (dto != null && dto.getCreatedOn() != null) {
						at.setCreatedOn(dto.getCreatedOn());
					}
					if (dto != null && dto.getModifiedBy() != null) {
						at.setModifiedBy(dto.getModifiedBy());
					}
					if (dto != null && dto.getModifiedOn() != null) {
						at.setModifiedOn(dto.getModifiedOn());
					}
					if (dto != null && dto.getIsActive() != null) {
						at.setIsActive(dto.getIsActive());
					}
					if (dto != null && dto.getProfileImage() != null) {
						if (!dto.getProfileImage().equals(""))
							at.setProfileImage(dto.getProfileImage());
					}
					if (dto != null && dto.getPanNumber() != null) {
						if (!dto.getPanNumber().equals(""))
							at.setPanNumber(dto.getPanNumber());
					}
					at.setModifiedOn(new Date());
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterRegistration.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterRegistration dts = (TransporterRegistration) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Transporter updated successfully");
					cdt.setTransporterRegistration(dts);
					return cdt;

				}
			} catch (Exception er) {
				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;

	}

	@Override
	public ControllerDAOTracker getUserDetailsWithGcmId(TransporterRegistration tr) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<TransporterRegistration> dList = session
					.createQuery("FROM TransporterRegistration WHERE mobileNumber='" + tr.getMobileNumber()
							+ "' AND password=" + tr.getPassword() + " AND gcmId='" + tr.getGcmId() + "'")
					.list();

			if (dList.size() > 0) {

				////////////////////// Fetching city, state and country
				////////////////////// ////////////////

				Session session2 = sessionFactory.openSession();
				List<?> as = null;

				String query = "SELECT c.`city_id` cityId, c.`city` cityName, s.`state_id` stateId, s.`state_name` stateName, cn.`id` countryId, cn.`value` countryName FROM countries AS cn LEFT JOIN `states` AS s ON cn.`id`=s.`country_id` LEFT JOIN `cities` AS c ON s.`state_id`=c.`state_id` WHERE s.`state_id`="
						+ dList.get(0).getState() + " AND c.`city_id`=" + dList.get(0).getCity();
				as = session2.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
				session2.close();

				// if(as != null && as.size() >0){
				// cdt.setCsc(as.get(0));
				// }

				////////////////////// Fetching city, state and country
				////////////////////// ////////////////

				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				if (as != null && as.size() > 0)
					dList.get(0).setCsc(as.get(0));
				cdt.setTransporterRegistration(dList.get(0));
				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Invalid credentials.");
				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker saveTransporterLoginHistory(TransporterLoginHistory tlh) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(tlh);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(TransporterLoginHistory.class)
					.setProjection(Projections.max("id"));
			@SuppressWarnings("unchecked")
			List<TransporterLoginHistory> clusterList = session.createCriteria(TransporterLoginHistory.class)
					.add(Property.forName("id").eq(maxId)).list();
			session.close();
			if (clusterList != null && clusterList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Record saved successfully.");
				cdt.setData(clusterList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Record not saved.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker getVehicleNumber(String vehicleNumber) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<TransporterVehicleRegistration> dList = session
					.createQuery("FROM TransporterVehicleRegistration WHERE vehicleNumber='" + vehicleNumber + "'")
					.list();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setTransporterVehicleRegistration(dList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Vehicle not found/registered.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker saveTransporterVehicle(TransporterVehicleRegistration tvr) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();

		Session sessions = sessionFactory.openSession();
		try {
			Transaction tx = sessions.beginTransaction();
			sessions.saveOrUpdate(tvr);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(TransporterVehicleRegistration.class)
					.setProjection(Projections.max("id"));
			List<TransporterVehicleRegistration> cmdList = sessions.createCriteria(TransporterVehicleRegistration.class)
					.add(Property.forName("id").eq(maxId)).list();
			sessions.clear();
			sessions.close();
			if (cmdList != null && cmdList.size() > 0) {
				TransporterVehicleRegistration dts = cmdList.get(0);
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Transporter saved successfully");
				cdt.setTransporterVehicleRegistration(dts);
				return cdt;
			} else {
				TransporterVehicleRegistration dts = cmdList.get(0);
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Request does not processed !");
				cdt.setData(dts);
				return cdt;
			}
		} catch (Exception er) {

			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker getTransporterVehicle(Integer trsptrRegistrationId) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<?> dList = null;

			String query = "SELECT tv.`id` id, vt.`vehicleName` vehicleName, tv.`vehicle_number` vehicleNumber, tv.`vehicle_type_id` vehicleTypeId, tv.`vehicle_body` vehicleBody, tv.`model_year` modelYear, tv.`insurance_expiry` insuranceExpiry, tv.`status` FROM trsptr_vehicle AS tv LEFT JOIN vehicle_type AS vt ON tv.`vehicle_type_id`=vt.`id` WHERE tv.`trsptr_registration_id`="
					+ trsptrRegistrationId;
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			session.close();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setData(dList);

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("No vehicles registered for the corresponding transporter.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker updateTransporterVehicle(TransporterVehicleRegistration dto) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterVehicleRegistration> objList = session
				.createQuery("FROM TransporterVehicleRegistration WHERE vehicleNumber='" + dto.getVehicleNumber()
						+ "' AND id=" + dto.getId())
				.list();
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterVehicleRegistration at : objList) {
					if (dto != null && dto.getVehicleTypeId() != null) {
						at.setVehicleTypeId(dto.getVehicleTypeId());
					}
					if (dto != null && dto.getVehicleBody() != null) {
						if (!dto.getVehicleBody().equals(""))
							at.setVehicleBody(dto.getVehicleBody());
					}
					if (dto != null && dto.getModelYear() != null) {
						if (!dto.getModelYear().equals(""))
							at.setModelYear(dto.getModelYear());
					}
					if (dto != null && dto.getInsuranceExpiry() != null) {
						at.setInsuranceExpiry(dto.getInsuranceExpiry());
					}
					if (dto != null && dto.getStatus() != null) {
						if (!dto.getStatus().equals(""))
							at.setStatus(dto.getStatus());
					}
					if (dto != null && dto.getCreatedBy() != null) {
						at.setCreatedBy(dto.getCreatedBy());
					}
					if (dto != null && dto.getCreatedOn() != null) {
						at.setCreatedOn(dto.getCreatedOn());
					}
					if (dto != null && dto.getModifiedBy() != null) {
						at.setModifiedBy(dto.getModifiedBy());
					}
					if (dto != null && dto.getModifiedOn() != null) {
						at.setModifiedOn(dto.getModifiedOn());
					}
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterVehicleRegistration.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterVehicleRegistration dts = (TransporterVehicleRegistration) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Vehicle updated successfully");
					cdt.setTransporterVehicleRegistration(dts);
					return cdt;

				}
			} catch (Exception er) {
				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setSuccess(false);
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;
	}

	@Override
	public ControllerDAOTracker getClientOrders(Integer city, String vehicleCategory) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<?> dList = null;

			String query;

			if (city == 0) {

				if (vehicleCategory.equals("MCV")) {
					query = "SELECT tco.`id` id, (SELECT GROUP_CONCAT(DISTINCT(`trsptr_registration_id`)) flagTransporterId FROM `trsptr_order_follow_up` WHERE `trsptr_client_orders_id`=tco.`id` GROUP BY `trsptr_client_orders_id`) AS flagTransporterId, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE vt.`capacity_category` IN ('SCV', 'MCV') AND tco.`is_active`=1";
				} else if (vehicleCategory.equals("LCV")) {
					query = "SELECT tco.`id` id, (SELECT GROUP_CONCAT(DISTINCT(`trsptr_registration_id`)) flagTransporterId FROM `trsptr_order_follow_up` WHERE `trsptr_client_orders_id`=tco.`id` GROUP BY `trsptr_client_orders_id`) AS flagTransporterId, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE vt.`capacity_category` IN ('LCV', 'HCV') AND tco.`is_active`=1";
				} else {
					query = "SELECT tco.`id` id, (SELECT GROUP_CONCAT(DISTINCT(`trsptr_registration_id`)) flagTransporterId FROM `trsptr_order_follow_up` WHERE `trsptr_client_orders_id`=tco.`id` GROUP BY `trsptr_client_orders_id`) AS flagTransporterId, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE vt.`capacity_category` IN ('SCV', 'MCV', 'LCV', 'HCV') AND tco.`is_active`=1";
				}

			} else {

				if (vehicleCategory.equals("MCV")) {
					query = "SELECT tco.`id` id, (SELECT GROUP_CONCAT(DISTINCT(`trsptr_registration_id`)) flagTransporterId FROM `trsptr_order_follow_up` WHERE `trsptr_client_orders_id`=tco.`id` GROUP BY `trsptr_client_orders_id`) AS flagTransporterId, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tco.`from_city_id`="
							+ city + " AND vt.`capacity_category` IN ('SCV', 'MCV') AND tco.`is_active`=1";
				} else if (vehicleCategory.equals("LCV")) {
					query = "SELECT tco.`id` id, (SELECT GROUP_CONCAT(DISTINCT(`trsptr_registration_id`)) flagTransporterId FROM `trsptr_order_follow_up` WHERE `trsptr_client_orders_id`=tco.`id` GROUP BY `trsptr_client_orders_id`) AS flagTransporterId, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tco.`from_city_id`="
							+ city + " AND vt.`capacity_category` IN ('LCV', 'HCV') AND tco.`is_active`=1";
				} else {
					query = "SELECT tco.`id` id, (SELECT GROUP_CONCAT(DISTINCT(`trsptr_registration_id`)) flagTransporterId FROM `trsptr_order_follow_up` WHERE `trsptr_client_orders_id`=tco.`id` GROUP BY `trsptr_client_orders_id`) AS flagTransporterId, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tco.`from_city_id`="
							+ city
							+ " AND vt.`capacity_category` IN ('SCV', 'MCV', 'LCV', 'HCV') AND tco.`is_active`=1";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			session.close();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setData(dList);

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("No client order for the corresponding transporter.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker clientOrderConfirmation(TransporterClientOrderMapping tcom) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();

		Session sessions = sessionFactory.openSession();
		try {
			Transaction tx = sessions.beginTransaction();
			sessions.saveOrUpdate(tcom);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(TransporterClientOrderMapping.class)
					.setProjection(Projections.max("id"));
			List<TransporterClientOrderMapping> cmdList = sessions.createCriteria(TransporterClientOrderMapping.class)
					.add(Property.forName("id").eq(maxId)).list();
			sessions.clear();
			sessions.close();
			if (cmdList != null && cmdList.size() > 0) {
				TransporterClientOrderMapping dts = cmdList.get(0);
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request saved successfully");
				cdt.setTransporterClientOrderMapping(dts);
				return cdt;
			} else {
				TransporterClientOrderMapping dts = cmdList.get(0);
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Request does not processed !");
				cdt.setTransporterClientOrderMapping(dts);
				return cdt;
			}
		} catch (Exception er) {

			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker orderFollowUp(TransporterOrderFollowUp tofu) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();

		Session session = this.sessionFactory.openSession();
		List<TransporterOrderFollowUp> objList = session.createQuery(
				"FROM TransporterOrderFollowUp WHERE trsptrClientOrdersId=" + tofu.getTrsptrClientOrdersId()
						+ " AND trsptrRegistrationId=" + tofu.getTrsptrRegistrationId())
				.list();
		session.close();

		if (objList.size() > 0) {
			cdt.setSuccess(false);
			cdt.setErrorCode("101");
			cdt.setErrorMessage("Order already followed by the transporter.");

			return cdt;
		} else {

			Session sessions = sessionFactory.openSession();
			try {
				Transaction tx = sessions.beginTransaction();
				sessions.saveOrUpdate(tofu);
				tx.commit();
				DetachedCriteria maxId = DetachedCriteria.forClass(TransporterOrderFollowUp.class)
						.setProjection(Projections.max("id"));
				List<TransporterOrderFollowUp> cmdList = sessions.createCriteria(TransporterOrderFollowUp.class)
						.add(Property.forName("id").eq(maxId)).list();
				sessions.clear();
				sessions.close();
				if (cmdList != null && cmdList.size() > 0) {
					TransporterOrderFollowUp dts = cmdList.get(0);
					cdt.setSuccess(true);
					cdt.setErrorCode("100");
					cdt.setErrorMessage("Request saved successfully");
					cdt.setTransporterOrderFollowUp(dts);
					return cdt;
				} else {
					TransporterOrderFollowUp dts = cmdList.get(0);
					cdt.setSuccess(false);
					cdt.setErrorCode("101");
					cdt.setErrorMessage("Request does not processed !");
					cdt.setTransporterOrderFollowUp(dts);
					return cdt;
				}
			} catch (Exception er) {

				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
	}

	@Override
	public ControllerDAOTracker getFollowUpOrders(Integer trsptrRegistrationId) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<TransporterOrderFollowUp> dList = session
					.createQuery("FROM TransporterOrderFollowUp WHERE trsptrRegistrationId=" + trsptrRegistrationId
							+ "AND isActive=1")
					.list();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setData(dList);

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Order not found for corresponding transporter.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker transporterClientIsActiveUpdate(TransporterClientOrders dto) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterClientOrders> objList = session
				.createQuery("FROM TransporterClientOrders WHERE id=" + dto.getId()).list();
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterClientOrders at : objList) {
					if (dto != null && dto.getIsActive() != null) {
						at.setIsActive(dto.getIsActive());
					}
					if (dto != null && dto.getModifiedBy() != null) {
						at.setModifiedBy(dto.getModifiedBy());
					}
					if (dto != null && dto.getModifiedOn() != null) {
						at.setModifiedOn(dto.getModifiedOn());
					}
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterClientOrders.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterClientOrders dts = (TransporterClientOrders) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Record updated successfully");
					cdt.setTransporterClientOrders(dts);
					return cdt;

				}
			} catch (Exception er) {
				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setSuccess(false);
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;
	}

	@Override
	public ControllerDAOTracker getClientOrdersHistory(Integer trsptrRegistrationId, String status,
			String vehicleCategory) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<?> dList = null;

			String query;
			if (vehicleCategory.equals("MCV")) {
				if (status.equals("Pending")) {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Pending' AND vt.`capacity_category` IN ('SCV', 'MCV') AND tcom.`is_active`=1";
				} else if (status.equals("Confirmed")) {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Confirmed' AND vt.`capacity_category` IN ('SCV', 'MCV') AND tcom.`is_active`=1";
				} else {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Cancelled' AND vt.`capacity_category` IN ('SCV', 'MCV') AND tcom.`is_active`=1";
				}
			} else if (vehicleCategory.equals("LCV")) {
				if (status.equals("Pending")) {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Pending' AND vt.`capacity_category` IN ('LCV', 'HCV') AND tcom.`is_active`=1";
				} else if (status.equals("Confirmed")) {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Confirmed' AND vt.`capacity_category` IN ('LCV', 'HCV') AND tcom.`is_active`=1";
				} else {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Cancelled' AND vt.`capacity_category` IN ('LCV', 'HCV') AND tcom.`is_active`=1";
				}
			} else {
				if (status.equals("Pending")) {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Pending' AND vt.`capacity_category` IN ('SCV', 'MCV', 'LCV', 'HCV') AND tcom.`is_active`=1";
				} else if (status.equals("Confirmed")) {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Confirmed' AND vt.`capacity_category` IN ('SCV', 'MCV', 'LCV', 'HCV') AND tcom.`is_active`=1";
				} else {
					query = "SELECT tco.`id` id, tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime FROM `trsptr_client_orders` AS tco LEFT JOIN `trsptr_client_order_mapping` AS tcom ON tco.`id`=tcom.`trsptr_client_orders_id` LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` WHERE tcom.`trsptr_registration_id`="
							+ trsptrRegistrationId
							+ " AND tcom.`status`='Cancelled' AND vt.`capacity_category` IN ('SCV', 'MCV', 'LCV', 'HCV') AND tcom.`is_active`=1";
				}
			}
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			session.close();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setData(dList);

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("No client order for the corresponding transporter.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker changeTransporterStatus(TransporterClientOrderMapping dto) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterClientOrderMapping> objList = session.createQuery(
				"FROM TransporterClientOrderMapping WHERE trsptrClientOrdersId=" + dto.getTrsptrClientOrdersId())
				.list();
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterClientOrderMapping at : objList) {
					if (dto != null && dto.getStatus() != null) {
						at.setStatus(dto.getStatus());
					}
					if (dto != null && dto.getModifiedBy() != null) {
						at.setModifiedBy(dto.getModifiedBy());
					}
					if (dto != null && dto.getModifiedOn() != null) {
						at.setModifiedOn(dto.getModifiedOn());
					}
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterClientOrderMapping.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterClientOrderMapping dts = (TransporterClientOrderMapping) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Record updated successfully");
					cdt.setTransporterClientOrderMapping(dts);
					return cdt;

				}
			} catch (Exception er) {
				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setSuccess(false);
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;
	}

	@Override
	public ControllerDAOTracker changeTransporterStatus(TransporterOrderFollowUp dto) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterOrderFollowUp> objList = session
				.createQuery(
						"FROM TransporterOrderFollowUp WHERE trsptrClientOrdersId=" + dto.getTrsptrClientOrdersId())
				.list();
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterOrderFollowUp at : objList) {
					if (dto != null && dto.getIsActive() != null) {
						at.setIsActive(dto.getIsActive());
					}
					if (dto != null && dto.getModifiedBy() != null) {
						at.setModifiedBy(dto.getModifiedBy());
					}
					if (dto != null && dto.getModifiedOn() != null) {
						at.setModifiedOn(dto.getModifiedOn());
					}
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterOrderFollowUp.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterOrderFollowUp dts = (TransporterOrderFollowUp) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Record updated successfully");
					cdt.setTransporterOrderFollowUp(dts);
					return cdt;

				}
			} catch (Exception er) {
				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setSuccess(false);
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;
	}

	@Override
	public ControllerDAOTracker getTransporterFreightChart(TransporterFreightChart tfc) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<TransporterFreightChart> dList = session
					.createQuery("FROM TransporterFreightChart WHERE trsptrRegistrationId="
							+ tfc.getTrsptrRegistrationId() + " AND sourceCityId=" + tfc.getSourceCityId()
							+ " AND destinationCityId=" + tfc.getDestinationCityId() + " AND vehicleTypeId="
							+ tfc.getVehicleTypeId() + " AND bodyType='" + tfc.getBodyType() + "'")
					.list();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setTransporterFreightChart(dList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("No freight rate for corresponding transporter or city exist.");
				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker saveTransporterFreightRate(TransporterFreightChart dto) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();

			Session session = sessionFactory.openSession();
			List<TransporterFreightChart> objList = session
					.createQuery("FROM TransporterFreightChart WHERE trsptrRegistrationId="
							+ dto.getTrsptrRegistrationId() + " AND sourceCityId=" + dto.getSourceCityId()
							+ " AND destinationCityId=" + dto.getDestinationCityId() + " AND vehicleTypeId="
							+ dto.getVehicleTypeId() + " AND bodyType='" + dto.getBodyType() + "'")
					.list();
			session.close();

			if (objList != null && objList.size() > 0) {
				try {
					for (TransporterFreightChart at : objList) {
						if (dto != null && dto.getTrsptrRegistrationId() != null) {
							if (!dto.getTrsptrRegistrationId().equals(""))
								at.setTrsptrRegistrationId(dto.getTrsptrRegistrationId());
						}
						if (dto != null && dto.getSourceCityId() != null) {
							if (!dto.getSourceCityId().equals(""))
								at.setSourceCityId(dto.getSourceCityId());
						}
						if (dto != null && dto.getDestinationCityId() != null) {
							if (!dto.getDestinationCityId().equals(""))
								at.setDestinationCityId(dto.getDestinationCityId());
						}
						if (dto != null && dto.getVehicleTypeId() != null) {
							if (!dto.getVehicleTypeId().equals(""))
								at.setVehicleTypeId(dto.getVehicleTypeId());
						}
						if (dto != null && dto.getBodyType() != null) {
							if (!dto.getBodyType().equals(""))
								at.setBodyType(dto.getBodyType());
						}
						if (dto != null && dto.getFreightRate() != null) {
							if (!dto.getFreightRate().equals(""))
								at.setFreightRate(dto.getFreightRate());
						}
						if (dto != null && dto.getCreatedBy() != null) {
							if (!dto.getCreatedBy().equals(""))
								at.setCreatedBy(dto.getCreatedBy());
						}

						at.setModifiedOn(new Date());
						Session session1 = this.sessionFactory.openSession();
						session1.get(TransporterFreightChart.class, at.getId());
						Transaction txs = session1.beginTransaction();
						TransporterFreightChart dts = (TransporterFreightChart) session1.merge(at);
						txs.commit();
						session1.close();
						cdt.setSuccess(true);
						cdt.setErrorCode("100");
						cdt.setErrorMessage("Updated successfully");
						cdt.setData(dts);

						return cdt;
					}
				} catch (Exception er) {
					ControllerDAOTracker cdte = new ControllerDAOTracker();
					cdte.setSuccess(false);
					cdte.setErrorCode("101");
					cdte.setErrorMessage(er.toString());

					return cdte;
				}
			} else {
				Session sessions = sessionFactory.openSession();
				try {
					Transaction tx = sessions.beginTransaction();
					sessions.saveOrUpdate(dto);
					tx.commit();
					DetachedCriteria maxId = DetachedCriteria.forClass(TransporterFreightChart.class)
							.setProjection(Projections.max("id"));
					List<TransporterFreightChart> cmdList = sessions.createCriteria(TransporterFreightChart.class)
							.add(Property.forName("id").eq(maxId)).list();
					sessions.clear();
					sessions.close();
					if (cmdList != null && cmdList.size() > 0) {
						TransporterFreightChart dts = cmdList.get(0);
						cdt.setSuccess(true);
						cdt.setErrorCode("100");
						cdt.setErrorMessage("Saved successfully");
						cdt.setData(dts);
						return cdt;
					} else {
						TransporterFreightChart dts = cmdList.get(0);
						cdt.setSuccess(false);
						cdt.setErrorCode("101");
						cdt.setErrorMessage("Request does not processed !");
						cdt.setData(dts);
						return cdt;
					}
				} catch (Exception er) {

					ControllerDAOTracker cdte = new ControllerDAOTracker();
					cdte.setSuccess(false);
					cdte.setErrorCode("101");
					cdte.setErrorMessage(er.toString());

					return cdte;
				}
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setSuccess(false);
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Something went wrong");

		return cdte;
	}

	@Override
	public ControllerDAOTracker getOrderCityList() {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<?> dList = null;

			String query;
			query = "SELECT DISTINCT(tco.`from_city_id`) fromCityId, c.`city` fromCityName FROM `trsptr_client_orders` AS tco LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` WHERE tco.`is_active`=1";
			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			session.close();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setData(dList);

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("No orders available.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker saveSMSRecord(CommunicationSMS cSms) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(cSms);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(CommunicationSMS.class)
					.setProjection(Projections.max("id"));
			@SuppressWarnings("unchecked")
			List<CommunicationSMS> clusterList = session.createCriteria(CommunicationSMS.class)
					.add(Property.forName("id").eq(maxId)).list();
			session.close();
			if (clusterList != null && clusterList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Record saved successfully.");
				cdt.setData(clusterList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Record not saved.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker getClientOrdersWithTransporter(Integer trsptrRegistrationId, Integer cityId,
			String vehicleCategory) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();
			List<?> dList = null;

			String query = "";

			query += "SELECT ";
			query += "tco.`id` id, ";
			query += "(SELECT GROUP_CONCAT(DISTINCT(`vehicle_number`)) vehicleNumber FROM `trsptr_vehicle` WHERE `trsptr_registration_id`="
					+ trsptrRegistrationId + " && `status`='Active' ORDER BY id DESC) AS vehicleNumber, ";

			// query += "(SELECT GROUP_CONCAT(`status`) vStatus FROM
			// `trsptr_vehicle` WHERE `trsptr_registration_id`=" +
			// trsptrRegistrationId + " ORDER BY id DESC) AS vStatus, ";

			query += "(SELECT COUNT(`trsptr_client_orders_id`) followCount FROM `trsptr_order_follow_up` WHERE `trsptr_client_orders_id`=tco.`id` ) AS followCount, ";
			query += "(SELECT COUNT(`id`) isFollow FROM `trsptr_order_follow_up` WHERE `trsptr_registration_id`="
					+ trsptrRegistrationId + " && `trsptr_client_orders_id`=tco.`id` ) AS isFollow, ";
			query += "tco.`from_city_id` fromCityId, c.`city` fromCityName, tco.`to_city_id` toCityId, c2.`city` toCityName, tco.`vehicle_type_id` vehicleTypeId, vt.`vehicleName` vehicleTypeName, tco.`vehicle_body` vehicleBody, tco.`price` amount, tco.`deploy_date_time` deployDateTime ";
			query += "FROM ";
			query += "`trsptr_client_orders` AS tco ";
			query += "LEFT JOIN `vehicle_type` AS vt ON tco.`vehicle_type_id`=vt.`id` ";
			query += "LEFT JOIN `cities` AS c ON tco.`from_city_id`=c.`city_id` ";
			query += "LEFT JOIN `cities` AS c2 ON tco.`to_city_id`=c2.`city_id` ";
			query += "WHERE ";
			if (cityId != 0)
				query += "tco.`from_city_id`=" + cityId + " AND ";

			if (vehicleCategory.equals("MCV"))
				query += "vt.`capacity_category` IN ('SCV', 'MCV') ";
			else if (vehicleCategory.equals("LCV"))
				query += "vt.`capacity_category` IN ('LCV', 'HCV') ";
			else
				query += "vt.`capacity_category` IN ('SCV', 'MCV', 'LCV', 'HCV') ";

			query += "AND tco.`is_active`=1";// AND tco.`is_published=1`

			dList = session.createSQLQuery(query).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			session.close();

			if (dList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setData(dList);

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("No client order for the corresponding transporter.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public TransporterRegistration getById(int id) {
		try {
			Session session = sessionFactory.openSession();
			List<TransporterRegistration> objList = session.createQuery("FROM TransporterRegistration WHERE id=" + id)
					.list();
			session.close();

			// Session session = this.sessionFactory.openSession();
			// String sql = "SELECT * FROM trsptr_registration WHERE id='" + id
			// + "' ";
			// List<Object[]> dataList =
			// session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
			// .list();

			if (objList != null && !objList.isEmpty()) {
				return objList.get(0);
			} else {
				return null;
			}
		} catch (Exception er) {
			System.out.println(er.getMessage().toString());
			return null;
		}
	}

	@Override
	public Object getByPassword(Integer password) {

		try {
			Session session = this.sessionFactory.openSession();
			String sql = "SELECT * FROM trsptr_registration WHERE password=" + password;
			List<Object[]> dataList = session.createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
					.list();
			if (dataList != null && !dataList.isEmpty()) {
				return dataList;
			} else {
				return null;
			}
		} catch (Exception er) {
			System.out.println(er.getMessage().toString());
			return null;
		}

	}

	@Override
	public TransporterRegistration updatePassword(TransporterRegistration dto) {

		Session session = this.sessionFactory.openSession();
		List<TransporterRegistration> objList = session
				.createQuery("FROM TransporterRegistration WHERE id=" + dto.getId()).list();
		// "FROM TransporterRegistration WHERE mobileNumber ='" + mobileNumber +
		// "'"
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterRegistration at : objList) {
					if (dto != null && dto.getPassword() != null) {
						if (!dto.getPassword().equals(""))
							at.setPassword(dto.getPassword());
					}
					at.setModifiedOn(new Date());
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterRegistration.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterRegistration dts = (TransporterRegistration) session1.merge(at);
					txs.commit();
					session1.close();

					dts.setErrorCode("100");
					dts.setErrorMesaage("Password updated successfully");
					return dts;

				}
			} catch (Exception er) {
				er.printStackTrace();
				return new TransporterRegistration();
			}

		}
		return new TransporterRegistration();

	}

	@Override
	public TransporterRegistration updateImage(TransporterRegistration dto) {

		Session session = this.sessionFactory.openSession();
		List<TransporterRegistration> objList = session
				.createQuery("FROM TransporterRegistration WHERE id=" + dto.getId()).list();
		// "FROM TransporterRegistration WHERE mobileNumber ='" + mobileNumber +
		// "'"
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterRegistration at : objList) {
					if (dto != null && dto.getProfileImage() != null) {
						if (!dto.getProfileImage().equals(""))
							at.setProfileImage(dto.getProfileImage());
					}
					at.setModifiedOn(new Date());
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterRegistration.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterRegistration dts = (TransporterRegistration) session1.merge(at);
					txs.commit();
					session1.close();

					dts.setErrorCode("100");
					dts.setErrorMesaage("Password updated successfully");
					return dts;

				}
			} catch (Exception er) {
				er.printStackTrace();
				return new TransporterRegistration();
			}

		}
		return new TransporterRegistration();

	}

	@Override
	public TransporterBankDetails addBankDetails(TransporterBankDetails p) {
		// Session session = this.sessionFactory.openSession();
		// Session session = this.sessionFactory.getCurrentSession();
		// session.save(p);
		// session.persist(p);
		// logger.info("BankDetails saved successfully, BankDetails=" + p);

		Session sessions = sessionFactory.openSession();
		sessions.saveOrUpdate(p);

		DetachedCriteria maxId = DetachedCriteria.forClass(TransporterBankDetails.class)
				.setProjection(Projections.max("id"));
		List<TransporterBankDetails> cmdList = sessions.createCriteria(TransporterBankDetails.class)
				.add(Property.forName("id").eq(maxId)).list();
		sessions.clear();
		sessions.close();

		return cmdList.get(0);

	}

	@Override
	public TransporterBankDetails getBankDetailsById(Integer id) {
		Session session = this.sessionFactory.openSession();
		TransporterBankDetails p = (TransporterBankDetails) session.load(TransporterBankDetails.class, new Integer(id));

		logger.info("BankDetails loaded successfully, BankDetails details=" + p);
		if (p != null)
			return p;
		else
			return null;
	}

	@Override
	public ControllerDAOTracker saveEmailRecord(CommunicationEmail cSms) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(cSms);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(CommunicationEmail.class)
					.setProjection(Projections.max("id"));
			@SuppressWarnings("unchecked")
			List<CommunicationEmail> clusterList = session.createCriteria(CommunicationEmail.class)
					.add(Property.forName("id").eq(maxId)).list();
			session.close();
			if (clusterList != null && clusterList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Record saved successfully.");
				cdt.setData(clusterList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Record not saved.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker sendEmailRecord(CommunicationEmailArchive cSms) {
		try {
			ControllerDAOTracker cdt = new ControllerDAOTracker();

			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(cSms);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(CommunicationEmailArchive.class)
					.setProjection(Projections.max("id"));
			@SuppressWarnings("unchecked")
			List<CommunicationEmailArchive> clusterList = session.createCriteria(CommunicationEmailArchive.class)
					.add(Property.forName("id").eq(maxId)).list();
			session.close();
			if (clusterList != null && clusterList.size() > 0) {
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Record saved successfully.");
				cdt.setData(clusterList.get(0));

				return cdt;
			} else {
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Record not saved.");

				return cdt;
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public ControllerDAOTracker findbyId(String id) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<CommunicationEmail> communicationEmails = session.createQuery("FROM CommunicationEmail").list();
			for (Iterator<CommunicationEmail> iterator = communicationEmails.iterator(); iterator.hasNext();) {
				CommunicationEmail email = (CommunicationEmail) iterator.next();
				System.out.print("Email Id : " + email.getEmailId() + " ");
				System.out.print("Emai Text: " + email.getEmailText() + " ");
				System.out.println("Subject: " + email.getSubject() + " ");

				CommunicationEmailArchive archive = new CommunicationEmailArchive();
				archive.setSendAt(new Date());
				// archive.setServerResponse(serverResponse);

				String hqlInsert = "insert into CommunicationEmailArchive ('" + email.getId() + "','"
						+ email.getEmailId() + "', '" + email.getEmailText() + "','" + email.getSubject() + "','"
						+ email.getEmailProvider() + "','" + email.getRequestDate() + "','" + email.getRequestProcess()
						+ "','" + new Date() + "') select c.id, c.name from CommunicationEmail c where id="
						+ email.getId();
				session.createQuery(hqlInsert).executeUpdate();
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker saveEmail(CommunicationEmail dto) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<CommunicationEmail> objList = session
				.createQuery("FROM CommunicationEmail WHERE emailId='" + dto.getEmailId()).list();
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (CommunicationEmail at : objList) {
					if (dto != null && dto.getEmailId() != null) {
						if (!dto.getEmailId().equals(""))
							at.setEmailId(dto.getEmailId());
					}
					if (dto != null && dto.getSubject() != null) {
						if (!dto.getSubject().equals(""))
							at.setSubject(dto.getSubject());
					}
					if (dto != null && dto.getEmailText() != null) {
						if (!dto.getEmailText().equals(""))
							at.setEmailText(dto.getEmailText());
					}
					if (dto != null && dto.getEmailProvider() != null) {
						at.setEmailProvider(dto.getEmailProvider());
					}

					if (dto != null && dto.getRequestDate() != null) {
						at.setRequestDate(dto.getRequestDate());
					}
					if (dto != null && dto.getRequestProcess() != null) {
						at.setRequestProcess(dto.getRequestProcess());
					}

					// at.setModifiedOn(new Date());
					Session session1 = this.sessionFactory.openSession();
					session1.get(CommunicationEmail.class, at.getId());
					Transaction txs = session1.beginTransaction();
					CommunicationEmail dts = (CommunicationEmail) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("updated successfully");
					cdt.setData(dts);
					return cdt;

				}
			} catch (Exception er) {
				er.printStackTrace();
			}
		} else {

			Session sessions = sessionFactory.openSession();
			try {
				Transaction tx = sessions.beginTransaction();
				sessions.saveOrUpdate(dto);
				tx.commit();
				DetachedCriteria maxId = DetachedCriteria.forClass(CommunicationEmailArchive.class)
						.setProjection(Projections.max("id"));
				List<CommunicationEmailArchive> cmdList = sessions.createCriteria(CommunicationEmailArchive.class)
						.add(Property.forName("id").eq(maxId)).list();
				sessions.clear();
				sessions.close();
				if (cmdList != null && cmdList.size() > 0) {
					CommunicationEmailArchive dts = cmdList.get(0);
					cdt.setSuccess(true);
					cdt.setErrorCode("100");
					cdt.setErrorMessage("saved successfully");
					cdt.setData(dts);
					return cdt;
				} else {
					CommunicationEmailArchive dts = cmdList.get(0);
					cdt.setSuccess(false);
					cdt.setErrorCode("101");
					cdt.setErrorMessage("Request does not processed !");
					cdt.setData(dts);
					return cdt;
				}
			} catch (Exception er) {

				ControllerDAOTracker cdte = new ControllerDAOTracker();
				cdte.setSuccess(false);
				cdte.setErrorCode("101");
				cdte.setErrorMessage(er.toString());

				return cdte;
			}
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker getCommunicationByEmail(String emailId) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {

			Session session = sessionFactory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				List<CommunicationEmail> dList = session.createQuery("FROM CommunicationEmail").list();
				for (Iterator<CommunicationEmail> iterator = dList.iterator(); iterator.hasNext();) {
					CommunicationEmail employee = (CommunicationEmail) iterator.next();

					Integer id = employee.getId();
					String toEmail = employee.getEmailId();
					String subject = employee.getSubject();
					String emailtext = employee.getEmailText();
					Date requestDate = employee.getRequestDate();
					Date requestProcess = employee.getRequestProcess();
					sendEmail(toEmail, subject, emailtext);

				}
				tx.commit();
				if (dList.size() > 0) {
					cdt.setSuccess(true);
					cdt.setErrorCode("100");
					cdt.setErrorMessage("Request successful.");
					cdt.setEmail(dList.get(0));
					return cdt;
				} else {
					cdt.setSuccess(false);
					cdt.setErrorCode("101");
					cdt.setErrorMessage("Record not registered.");

					return cdt;
				}
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
		return cdt;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker saveCommunicationEmailArchive(CommunicationEmailArchive dto) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session sessions = sessionFactory.openSession();
		try {
			Transaction tx = sessions.beginTransaction();
			sessions.saveOrUpdate(dto);
			tx.commit();
			DetachedCriteria maxId = DetachedCriteria.forClass(CommunicationEmailArchive.class)
					.setProjection(Projections.max("id"));
			List<CommunicationEmailArchive> cmdList = sessions.createCriteria(CommunicationEmailArchive.class)
					.add(Property.forName("id").eq(maxId)).list();
			sessions.clear();
			sessions.close();
			if (cmdList != null && cmdList.size() > 0) {
				CommunicationEmailArchive dts = cmdList.get(0);
				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("saved successfully");
				cdt.setData(dts);
				return cdt;
			} else {
				CommunicationEmailArchive dts = cmdList.get(0);
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("can't be processed !");
				cdt.setData(dts);
				return cdt;
			}
		} catch (Exception er) {

			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
	}

	@Override
	public void sendMail() {
	}

	@Override
	public ControllerDAOTracker sendEmail(String emailId, String subject, String emailText) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("shahzad.hussain@truxapp.com", "India@123$");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("shahzad.hussain@truxapp.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
			message.setSubject(subject);
			message.setText(emailText);

			Transport.send(message);

			System.out.println("Mail Send Succesfully");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker sendByEmail() {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {

			Session session = sessionFactory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				List<CommunicationEmail> dList = session.createQuery("FROM CommunicationEmail").list();
				for (Iterator<CommunicationEmail> iterator = dList.iterator(); iterator.hasNext();) {
					CommunicationEmail employee = (CommunicationEmail) iterator.next();

					Integer id = employee.getId();
					String toEmail = employee.getEmailId();
					String subject = employee.getSubject();
					String emailtext = employee.getEmailText();
					Date requestDate = employee.getRequestDate();
					Date requestProcess = employee.getRequestProcess();
					sendEmail(toEmail, subject, emailtext);

					/*
					 * Session session1 = this.sessionFactory.openSession();
					 * session1.get(TransporterRegistration.class, at.getId());
					 * Transaction txs = session1.beginTransaction();
					 * TransporterRegistration dts = (TransporterRegistration)
					 * session1.merge(at);
					 */

					CommunicationEmailArchive archive = new CommunicationEmailArchive();
					/*
					 * archive.setId(id); archive.setEmailId(toEmail);
					 * archive.setEmailProvider(employee.getEmailProvider());
					 */
					archive.setEmailProviderResponse("Success");
					archive.setSendAt(new Date());
					archive.setServerResponse("Success");

					session.saveOrUpdate(archive);

					// CommunicationEmailArchive employee1 =
					// (CommunicationEmailArchive)session.save(employee);

					/* This is for deleting record one by one */

					CommunicationEmail e2 = (CommunicationEmail) session.get(CommunicationEmail.class, id);
					session.delete(e2);

				}
				tx.commit();
				if (dList.size() > 0) {
					cdt.setSuccess(true);
					cdt.setErrorCode("100");
					cdt.setErrorMessage("Request successful.");
					cdt.setEmail(dList.get(0));
					return cdt;
				} else {
					CommunicationEmailArchive archive = new CommunicationEmailArchive();
					archive.setEmailProviderResponse("Failed");
					archive.setSendAt(new Date());
					archive.setServerResponse("Failed");
					session.saveOrUpdate(archive);
					cdt.setSuccess(false);
					cdt.setErrorCode("101");
					cdt.setErrorMessage("Email Sending Failed....!!!");

					return cdt;
				}
			} catch (Exception e) {
				// } catch (HibernateException e) {
				// if (tx != null)
				// tx.rollback();
				CommunicationEmailArchive archive = new CommunicationEmailArchive();
				archive.setEmailProviderResponse("Failed");
				archive.setSendAt(new Date());
				archive.setServerResponse("Failed");
				session.saveOrUpdate(archive);
				e.printStackTrace();
			} finally {
				session.close();
			}
		} catch (Exception er) {
			ControllerDAOTracker cdte = new ControllerDAOTracker();
			cdte.setSuccess(false);
			cdte.setErrorCode("101");
			cdte.setErrorMessage(er.toString());

			return cdte;
		}
		return cdt;

	}
}
