package com.truxapiv2.dao;

import java.util.List;

import com.truxapiv2.dao.ClientMandateDao;
import com.truxapiv2.model.ClientMandateVehicleDeployment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientMandateDaoImpl implements ClientMandateDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void saveClientMandate(ClientMandateVehicleDeployment clientmandate) {
		getSession().merge(clientmandate);

	}

	@SuppressWarnings("unchecked")
	public List<ClientMandateVehicleDeployment> listClientMandates() {

		return getSession().createCriteria(ClientMandateVehicleDeployment.class).list();
	}

	public ClientMandateVehicleDeployment getClientMandate(Long id) {
		return (ClientMandateVehicleDeployment) getSession().get(ClientMandateVehicleDeployment.class, id);
	}

	public void deleteClientMandate(Long id) {

		ClientMandateVehicleDeployment clientmandate = getClientMandate(id);

		if (null != clientmandate) {
			getSession().delete(clientmandate);
		}

	}

	private Session getSession() {
		Session sess = getSessionFactory().getCurrentSession();
		if (sess == null) {
			sess = getSessionFactory().openSession();
		}
		return sess;
	}

	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
