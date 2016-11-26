package com.truxapiv2.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.truxapiv2.model.ControllerDAOTracker;
import com.truxapiv2.model.TransporterBankDetails;
import com.truxapiv2.model.TransporterRegistration;

@Repository
public class TransportBankDetailsDAOImpl implements TransporterBankDetailsDAO {
	private static final Logger logger = LoggerFactory.getLogger(TransportBankDetailsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public TransporterBankDetails addBankDetails(TransporterBankDetails p) {
		// Session session = this.sessionFactory.openSession();
		// Session session = this.sessionFactory.getCurrentSession();
		// session.save(p);
		// session.persist(p);
		// logger.info("BankDetails saved successfully, BankDetails=" + p);

		Session sessions = sessionFactory.openSession();
		Transaction tx = sessions.beginTransaction();

		sessions.saveOrUpdate(p);

		DetachedCriteria maxId = DetachedCriteria.forClass(TransporterBankDetails.class)
				.setProjection(Projections.max("trsptrRegistrationId"));
		@SuppressWarnings("unchecked")
		/*
		 * List<TransporterBankDetails> cmdList =
		 * sessions.createCriteria(TransporterBankDetails.class)
		 * .add(Property.forName("trsptrRegistrationId").eq(maxId)).list();
		 */

		Criteria ct = sessions.createCriteria(TransporterBankDetails.class);
		List<TransporterBankDetails> cmdList = ct.list();

		logger.info("Bank Details Successfully Saved " + p);
		tx.commit();
		sessions.clear();
		sessions.close();

		return cmdList.get(0);

	}

	@Override
	public ControllerDAOTracker updateBankDetails(TransporterBankDetails dto) {

		return null;

		/*
		 * Session session = this.sessionFactory.getCurrentSession(); Query
		 * query = session.createQuery(
		 * "update TransporterBankDetails set accountStatus=:c where varificationAmount="
		 * + dto.getVarificationAmount()); String amount =
		 * dto.getVarificationAmount(); try { if (amount != null &&
		 * amount.equals("1")) { query.setParameter("c", 1);
		 * query.executeUpdate(); } else { System.out.println(
		 * "Please Enter Verification Amount"); } } catch (Exception ex) {
		 * ex.printStackTrace(); }
		 */
	}

	@Override
	public List<TransporterBankDetails> listDetails() {
		Session session = this.sessionFactory.getCurrentSession();
		List<TransporterBankDetails> bankDetailsList = session.createQuery("from TransporterBankDetails").list();
		for (TransporterBankDetails p : bankDetailsList) {
			logger.info("BankDetails List::" + p);
		}
		return bankDetailsList;
	}

	@Override
	public TransporterBankDetails getBankDetailsById(Integer id) {
		Session session = this.sessionFactory.getCurrentSession();
		TransporterBankDetails p = (TransporterBankDetails) session.load(TransporterBankDetails.class, new Integer(id));

		logger.info("BankDetails loaded successfully, BankDetails details=" + p);
		if (p != null)
			return p;
		else
			return null;
	}

	@Override
	public void removeBankDetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TransporterBankDetails p = (TransporterBankDetails) session.load(TransporterBankDetails.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		logger.info("BankDetails deleted successfully, BankDetails details=" + p);

	}

	/* For Account Verification */
	@Override
	public TransporterBankDetails getByConfirmationAmount(TransporterBankDetails dto) {
		Session session = this.sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();
		Query q = session.createQuery("update TransporterBankDetails set accountStatus=:n where id=:i");
		Double amount = dto.getVarificationAmount();
		if (dto.getVarificationAmount() != null) {
			q.setParameter("n", 1);
			q.setParameter("i", dto.getId());

			int status = q.executeUpdate();
			System.out.println(status);
			tx.commit();
			/*
			 * if(amount==){
			 * 
			 * 
			 * } else{ System.out.println(
			 * "you have entered Wrong Verification Amount"); }
			 */
		} else {
			System.out.println("please Check Your Verification Amount");
		}
		return dto;

		/*
		 * 
		 * ControllerDAOTracker cdt = new ControllerDAOTracker(); Session
		 * session = this.sessionFactory.openSession();
		 * 
		 * @SuppressWarnings("unchecked") List<TransporterBankDetails> objList =
		 * session .createQuery("FROM TransporterBankDetails WHERE id=" +
		 * dto.getId()).list(); Transaction tx = null; try { tx =
		 * session.beginTransaction(); TransporterBankDetails details =
		 * this.getByConfirmationAmount(dto);
		 * if(details.getVarificationAmount().equals(details)){
		 * 
		 * details.setAccountStatus(1); session.save(details); tx.commit(); }
		 * 
		 * } catch (HibernateException e) { System.out.println(
		 * "Your Account is Not Verified"); } finally { session.close(); }
		 * 
		 * 
		 * return dto;
		 */}

	@Override
	public TransporterBankDetails getBankDetailsById(TransporterBankDetails tr) {
		Session session = this.sessionFactory.openSession();
		TransporterBankDetails p = (TransporterBankDetails) session.load(TransporterBankDetails.class, tr);

		logger.info("BankDetails loaded successfully, BankDetails details=" + p);
		if (p != null)
			return p;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker getTransporterRegistrationId(Integer trsptrRegistrationId) {
		
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		try {
			Session session = this.sessionFactory.openSession();

			List<TransporterBankDetails> dList = session
					.createQuery("FROM TransporterBankDetails WHERE trsptrRegistrationId=" + trsptrRegistrationId)
					.list();
			if (dList.size() > 0) {

				cdt.setSuccess(true);
				cdt.setErrorCode("100");
				cdt.setErrorMessage("Request successful.");
				cdt.setTransporterBankDetails(dList.get(0));

				return cdt;
			} else {
				
				cdt.setSuccess(false);
				cdt.setErrorCode("101");
				cdt.setErrorMessage("Bank Details not found/registered.");

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

	@SuppressWarnings("unchecked")
	@Override
	public ControllerDAOTracker saveTransporterBankDetails(TransporterBankDetails dto) {

		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterBankDetails> objList = session
				.createQuery("FROM TransporterBankDetails WHERE trsptrRegistrationId='" + dto.getTrsptrRegistrationId()
						+ "' AND id=" + dto.getId())
				.list();
		// "FROM TransporterRegistration WHERE mobileNumber ='" + mobileNumber +
		// "'"
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterBankDetails at : objList) {
					if (dto != null && dto.getTrsptrRegistrationId() != null) {
						if (!dto.getTrsptrRegistrationId().equals(""))
							at.setTrsptrRegistrationId(dto.getTrsptrRegistrationId());
					}
					if (dto != null && dto.getAccountHolderName() != null) {
						if (!dto.getAccountHolderName().equals(""))
							at.setAccountHolderName(dto.getAccountHolderName());
					}
					if (dto != null && dto.getBankName() != null) {
						if (!dto.getBankName().equals(""))
							at.setBankName(dto.getBankName());
					}
					if (dto != null && dto.getAccountNumber() != null) {
						if (!dto.getAccountNumber().equals(""))
							at.setAccountNumber(dto.getAccountNumber());
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
					session1.get(TransporterBankDetails.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterBankDetails dts = (TransporterBankDetails) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Bank Details Saved successfully");
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
				DetachedCriteria maxId = DetachedCriteria.forClass(TransporterBankDetails.class)
						.setProjection(Projections.max("id"));
				List<TransporterBankDetails> cmdList = sessions.createCriteria(TransporterBankDetails.class)
						.add(Property.forName("id").eq(maxId)).list();
				sessions.clear();
				sessions.close();
				if (cmdList != null && cmdList.size() > 0) {
					TransporterBankDetails dts = cmdList.get(0);
					cdt.setSuccess(true);
					cdt.setErrorCode("100");
					cdt.setErrorMessage("Bank Details saved successfully");
					cdt.setData(dts);
					return cdt;
				} else {
					TransporterBankDetails dts = cmdList.get(0);
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
	public ControllerDAOTracker verifyTransporterBankDetails(TransporterBankDetails dto) {

		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterBankDetails> objList = session
				.createQuery("FROM TransporterBankDetails WHERE trsptrRegistrationId='" + dto.getTrsptrRegistrationId()
						+ "' AND id=" + dto.getId())
				.list();
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterBankDetails at : objList) {
					if (dto != null && dto.getTrsptrRegistrationId() != null) {
						if (!dto.getTrsptrRegistrationId().equals(""))
							at.setTrsptrRegistrationId(dto.getTrsptrRegistrationId());
					}
					if (dto != null && dto.getAccountHolderName() != null) {
						if (!dto.getAccountHolderName().equals(""))
							at.setAccountHolderName(dto.getAccountHolderName());
					}
					if (dto != null && dto.getBankName() != null) {
						if (!dto.getBankName().equals(""))
							at.setBankName(dto.getBankName());
					}
					if (dto != null && dto.getAccountNumber() != null) {
						if (!dto.getAccountNumber().equals(""))
							at.setAccountNumber(dto.getAccountNumber());
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
					session1.get(TransporterBankDetails.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterBankDetails dts = (TransporterBankDetails) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Your Account is Verified successfully");
					cdt.setData(dts);
					return cdt;

				}
			} catch (Exception er) {
				er.printStackTrace();
			}
		} else {

			System.out.println("Account Verification failed");
			/*
			 * 
			 * Session sessions = sessionFactory.openSession(); try {
			 * Transaction tx = sessions.beginTransaction();
			 * sessions.saveOrUpdate(dto); tx.commit(); DetachedCriteria maxId =
			 * DetachedCriteria.forClass(TransporterBankDetails.class)
			 * .setProjection(Projections.max("id"));
			 * List<TransporterBankDetails> cmdList =
			 * sessions.createCriteria(TransporterBankDetails.class)
			 * .add(Property.forName("id").eq(maxId)).list(); sessions.clear();
			 * sessions.close(); if (cmdList != null && cmdList.size() > 0) {
			 * TransporterBankDetails dts = cmdList.get(0);
			 * cdt.setSuccess(true); cdt.setErrorCode("100");
			 * cdt.setErrorMessage("Bank Details saved successfully");
			 * cdt.setData(dts); return cdt; } else { TransporterBankDetails dts
			 * = cmdList.get(0); cdt.setSuccess(false); cdt.setErrorCode("101");
			 * cdt.setErrorMessage("Request does not processed !");
			 * cdt.setData(dts); return cdt; } } catch (Exception er) {
			 * 
			 * ControllerDAOTracker cdte = new ControllerDAOTracker();
			 * cdte.setSuccess(false); cdte.setErrorCode("101");
			 * cdte.setErrorMessage(er.toString());
			 * 
			 * return cdte; }
			 */}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;

	}

	@Override
	public ControllerDAOTracker updateVerificationStatus(Integer id, String string) {
		ControllerDAOTracker cdt = new ControllerDAOTracker();
		Session session = this.sessionFactory.openSession();
		List<TransporterBankDetails> objList = session
				.createQuery("FROM TransporterBankDetails WHERE trsptrRegistrationId=" + id).list();
		session.close();
		if (objList != null && objList.size() > 0) {
			try {
				for (TransporterBankDetails at : objList) {
					at.setAccountStatus(string);

					at.setModifiedOn(new Date());
					Session session1 = this.sessionFactory.openSession();
					session1.get(TransporterBankDetails.class, at.getId());
					Transaction txs = session1.beginTransaction();
					TransporterBankDetails dts = (TransporterBankDetails) session1.merge(at);
					txs.commit();
					session1.close();

					cdt.setSuccess(true);
					cdt.setErrorCode("200");
					cdt.setErrorMessage("Your Account is Verified successfully");
					cdt.setData(dts);
					return cdt;

				}
			} catch (Exception er) {
				er.printStackTrace();
			}
		} else {

			cdt.setSuccess(false);
			cdt.setErrorCode("101");
			cdt.setErrorMessage("Your Account is not Verified");
			return cdt;
		}
		ControllerDAOTracker cdte = new ControllerDAOTracker();
		cdte.setErrorCode("101");
		cdte.setErrorMessage("Your request does not processed !");
		return cdte;
	}

}
