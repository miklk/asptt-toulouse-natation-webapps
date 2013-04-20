package com.asptttoulousenatation.core.server.service;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {

	private static final PersistenceManagerFactory persistenceManagerFactory = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	private PMF() {

	}

	public static PersistenceManager getPersistenceManager() {
		return persistenceManagerFactory.getPersistenceManager();
	}
}
