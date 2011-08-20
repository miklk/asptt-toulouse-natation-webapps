package com.asptttoulousenatation.core.server.dao;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {

	private static final PersistenceManagerFactory persistenceManagerFactory = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	private static PersistenceManager PM;

	private PMF() {

	}

	public static PersistenceManagerFactory get() {
		return persistenceManagerFactory;
	}
	
	public static PersistenceManager getPersistenceManager() {
		if(PM == null || PM.isClosed()) {
			PM = persistenceManagerFactory.getPersistenceManager();
		}
		return PM;
	}
}
