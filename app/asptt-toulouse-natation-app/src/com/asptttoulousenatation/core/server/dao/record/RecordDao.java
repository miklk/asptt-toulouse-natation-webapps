package com.asptttoulousenatation.core.server.dao.record;

import java.util.ArrayList;
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
			TypedQuery<RecordEntity> query = em.createQuery("SELECT * FROM "
					+ getEntityClass().getSimpleName() + " " + getAlias() + " WHERE " + getAlias() + ".epreuve=:epreuve", RecordEntity.class);
			list = query.getResultList();
		} finally {
			em.close();
		}
		return list;
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
