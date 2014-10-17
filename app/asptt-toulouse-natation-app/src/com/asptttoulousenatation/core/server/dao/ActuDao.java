package com.asptttoulousenatation.core.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriteriaUtils;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.service.PMF;

public class ActuDao extends DaoBase<ActuEntity> {

	@Override
	public Class<ActuEntity> getEntityClass() {
		return ActuEntity.class;
	}
	
	public List<ActuEntity> find(List<CriterionDao<? extends Object>> pCriteria, long limitStart, long limitEnd) {
		final PersistenceManager lPersistenceManager = PMF
				.getPersistenceManager();
		String lQueryAsString = "SELECT FROM " + getEntityClass().getName();
		if (CollectionUtils.isNotEmpty(pCriteria)) {
			lQueryAsString += " WHERE "
					+ CriteriaUtils.getWhereClause(pCriteria, Operator.AND);
		}
		final Query lQuery = lPersistenceManager.newQuery(lQueryAsString);
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
	
	public List<ActuEntity> getAll(long limitStart, long limitEnd) {
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