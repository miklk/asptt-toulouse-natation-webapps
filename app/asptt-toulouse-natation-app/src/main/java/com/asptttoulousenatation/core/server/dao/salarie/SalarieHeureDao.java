package com.asptttoulousenatation.core.server.dao.salarie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.salarie.SalarieHeureEntity;
import com.asptttoulousenatation.core.server.service.EMF;

public class SalarieHeureDao extends DaoBase<SalarieHeureEntity> {

	public List<SalarieHeureEntity> findByBeginEndAndUser(Date begin, Date end, Long user) {
		EntityManager em = EMF.get().createEntityManager();
		List<SalarieHeureEntity> entities = new ArrayList<SalarieHeureEntity>();
		StringBuilder queryAsString = new StringBuilder("SELECT heure FROM SalarieHeureEntity heure WHERE ");
		queryAsString.append("heure.begin BETWEEN :begin AND :end AND user =:user");
		try {
			TypedQuery<SalarieHeureEntity> query = em.createQuery(queryAsString.toString(), SalarieHeureEntity.class);
			query.setParameter("begin", begin);
			query.setParameter("end", end);
			query.setParameter("user", user);
			entities = query.getResultList();
		} finally {
			em.close();
		}

		return entities;
	}
	
	public List<SalarieHeureEntity> findByBeginEnd(Date begin, Date end, Long user) {
		EntityManager em = EMF.get().createEntityManager();
		List<SalarieHeureEntity> entities = new ArrayList<SalarieHeureEntity>();
		StringBuilder queryAsString = new StringBuilder("SELECT heure FROM SalarieHeureEntity heure WHERE ");
		queryAsString.append("heure.begin BETWEEN :begin AND :end");
		if(user > 0) {
			queryAsString.append(" AND heure.user = :user");
		}
		try {
			TypedQuery<SalarieHeureEntity> query = em.createQuery(queryAsString.toString(), SalarieHeureEntity.class);
			query.setParameter("begin", begin);
			query.setParameter("end", end);
			if(user > 0) {
				query.setParameter("user", user);
			}
			entities = query.getResultList();
		} finally {
			em.close();
		}

		return entities;
	}
	
	public List<Long> findUsers() {
		EntityManager em = EMF.get().createEntityManager();
		List<Long> entities = new ArrayList<Long>();
		StringBuilder queryAsString = new StringBuilder("SELECT DISTINCT "+ getAlias() + ".user FROM SalarieHeureEntity " + getAlias());
		try {
			TypedQuery<Long> query = em.createQuery(queryAsString.toString(), Long.class);
			entities = query.getResultList();
		} finally {
			em.close();
		}

		return entities;
	}

	@Override
	public Class<SalarieHeureEntity> getEntityClass() {
		return SalarieHeureEntity.class;
	}

	@Override
	public String getAlias() {
		return "salarieH";
	}

	@Override
	public Object getKey(SalarieHeureEntity pEntity) {
		return pEntity.getId();
	}
}