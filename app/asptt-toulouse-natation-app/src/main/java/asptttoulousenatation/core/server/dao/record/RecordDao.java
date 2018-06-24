package com.asptttoulousenatation.core.server.dao.record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.record.RecordEntity;
import com.asptttoulousenatation.core.server.service.EMF;

public class RecordDao extends DaoBase<RecordEntity> {
	
	public List<RecordEntity> findByEpreuve(Long epreuve) {
		EntityManager em = EMF.get().createEntityManager();
		List<RecordEntity> list = new ArrayList<>();
		try {
			TypedQuery<RecordEntity> query = em.createQuery("SELECT " + getAlias() + " FROM "
					+ getEntityClass().getSimpleName() + " " + getAlias() + " WHERE " + getAlias() + ".epreuve=:epreuve", RecordEntity.class);
			query.setParameter("epreuve", epreuve);
			list = query.getResultList();
		} finally {
			em.close();
		}
		return list;
	}
	
	public List<RecordEntity> findByEpreuveAndAge(Long epreuve, String age) {
		EntityManager em = EMF.get().createEntityManager();
		List<RecordEntity> list = new ArrayList<>();
		try {
			TypedQuery<RecordEntity> query = em.createQuery("SELECT " + getAlias() + " FROM "
					+ getEntityClass().getSimpleName() + " " + getAlias() + " WHERE " + getAlias() + ".epreuve=:epreuve AND " + getAlias() + ".age=:age", RecordEntity.class);
			query.setParameter("epreuve", epreuve);
			query.setParameter("age", age);
			list = query.getResultList();
		} finally {
			em.close();
		}
		return list;
	}
	
	public Date findMaxUpdated() {
		EntityManager em = EMF.get().createEntityManager();
		Date max = null;
		try {
			TypedQuery<Date> query = em.createQuery("SELECT MAX(" + getAlias() + ".updated) FROM "
					+ getEntityClass().getSimpleName() + " " + getAlias(), Date.class);
			max = query.getSingleResult();
		} finally {
			em.close();
		}
		return max;
	}

	@Override
	public Class<RecordEntity> getEntityClass() {
		return RecordEntity.class;
	}

	@Override
	public String getAlias() {
		return "record";
	}

	@Override
	public Object getKey(RecordEntity pEntity) {
		return pEntity.getId();
	}

}
