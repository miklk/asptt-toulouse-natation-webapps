package com.asptttoulousenatation.core.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.service.PMF;

public class ActuDao extends DaoBase<ActuEntity> {

	@Override
	public Class<ActuEntity> getEntityClass() {
		return ActuEntity.class;
	}
	
	public List<ActuEntity> getAll(int limitStart, int limitEnd) {
		PersistenceManager lPersistenceManager = PMF.getPersistenceManager();
		Query lQuery = lPersistenceManager.newQuery(ActuEntity.class);
		lQuery.setOrdering("creationDate DESC");
		lQuery.setRange(limitStart, limitEnd);
		final List<ActuEntity> lResult;
		try {
			lResult = (List<ActuEntity>) lPersistenceManager
					.detachCopyAll((List<ActuEntity>) lQuery.execute());

		} finally {
			lQuery.closeAll();
			if (!lPersistenceManager.isClosed()) {
				lPersistenceManager.close();
			}
		}
		return lResult;
	}
	
	public List<ActuEntity> getAll() {
		return getAll(0, 100);
	}
}