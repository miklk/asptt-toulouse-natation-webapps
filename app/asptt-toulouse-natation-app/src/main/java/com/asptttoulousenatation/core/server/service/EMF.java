package com.asptttoulousenatation.core.server.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {

	private static final EntityManagerFactory INSTANCE;
	
	static {
		INSTANCE = Persistence.createEntityManagerFactory("transactions-optional");
	}
	
	public static EntityManagerFactory get() {
		return INSTANCE;
	}
}
