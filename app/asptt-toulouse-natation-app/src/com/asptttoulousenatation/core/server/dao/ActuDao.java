package com.asptttoulousenatation.core.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriteriaUtils;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.service.EMF;

public class ActuDao extends DaoBase<ActuEntity> {

	@Override
	public Class<ActuEntity> getEntityClass() {
		return ActuEntity.class;
	}

	public List<ActuEntity> find(
			List<CriterionDao<? extends Object>> pCriteria, int limitStart,
			int limitEnd) {
		final EntityManager em = EMF.get().createEntityManager();
		String lQueryAsString = "SELECT " + getAlias() + " FROM "
				+ getEntityClass().getName() + " " + getAlias();
		if (CollectionUtils.isNotEmpty(pCriteria)) {
			lQueryAsString += " WHERE "
					+ CriteriaUtils.getWhereClause(pCriteria, Operator.AND,
							getAlias());
		}
		lQueryAsString += " " + getAlias() + ".creationDate DESC";
		final Query lQuery = em.createQuery(lQueryAsString);
		lQuery.setFirstResult(limitStart);
		lQuery.setMaxResults(limitEnd);
		final List<ActuEntity> lResult;
		try {
			lResult = (List<ActuEntity>) lQuery.getResultList();
		} finally {
			em.close();
		}
		return lResult;
	}

	public List<ActuEntity> getAll(int limitStart, int limitEnd) {
		final EntityManager em = EMF.get().createEntityManager();
		Query lQuery = em.createQuery("SELECT " + getAlias() + " FROM "
				+ ActuEntity.class.getSimpleName() + " " + getAlias() + " ORDER BY " + getAlias() + ".creationDate DESC");
		lQuery.setFirstResult(limitStart);
		lQuery.setMaxResults(limitEnd);
		final List<ActuEntity> lResult;
		try {
			lResult = (List<ActuEntity>) lQuery.getResultList();

		} finally {
			em.close();
		}
		return lResult;
	}

	public List<ActuEntity> getAll() {
		return getAll(0, 100);
	}

	@Override
	public String getAlias() {
		return "actu";
	}

	@Override
	public Object getKey(ActuEntity pEntity) {
		return pEntity.getId();
	}
}