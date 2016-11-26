package com.truxapiv2.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.datastax.driver.core.Cluster;

public class TruxAppServletContextListener implements ServletContextListener {

	com.datastax.driver.core.Session cassandraSession = null;
	Cluster.Builder cassandraCluster;
	private ServletContext servletContext;

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("cassandra destroyed");
		cassandraSession.close();
		cassandraCluster.build().close();
	}

	// Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("cassandra started");
		cassandraCluster = Cluster.builder().addContactPoints("54.169.184.149").withPort(9042)
				.withCredentials("devcasuser", "cas@trux#2016");
		// cassandraCluster =
		// Cluster.builder().addContactPoints("192.168.0.102").withPort(9042).withCredentials("cassandra",
		// "cassandra");
		cassandraSession = cassandraCluster.build().connect("hb");
		servletContextEvent.getServletContext().setAttribute("cassandraSession", cassandraSession);
		// servletContext.setAttribute("cassandraSession", cassandraSession);
	}

}
