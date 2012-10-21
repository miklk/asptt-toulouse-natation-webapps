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
	
	public List<ActuEntity> getAll() {
		PersistenceManager lPersistenceManager = PMF.getPersistenceManager();
		Query lQuery = lPersistenceManager.newQuery(ActuEntity.class);
		lQuery.setOrdering("creationDate DESC");
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
}