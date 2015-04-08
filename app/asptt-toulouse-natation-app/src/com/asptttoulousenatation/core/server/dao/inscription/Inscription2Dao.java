package com.asptttoulousenatation.core.server.dao.inscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.service.EMF;

public class Inscription2Dao extends DaoBase<InscriptionEntity2> {

	@Override
	public Class<InscriptionEntity2> getEntityClass() {
		return InscriptionEntity2.class;
	}

	@Override
	public String getAlias() {
		return "adherent";
	}

	@Override
	public Object getKey(InscriptionEntity2 pEntity) {
		return pEntity.getId();
	}

	public Collection<Long> getPrincipal() {
		EntityManager em = EMF.get().createEntityManager();
		List<Long> lAllList = new ArrayList<Long>();
		try {
			TypedQuery<Long> query = em.createQuery("SELECT DISTINCT "
					+ getAlias() + ".principal FROM "
					+ getEntityClass().getSimpleName() + " " + getAlias(),
					Long.class);
			lAllList = query.getResultList();
		} finally {
			em.close();
		}
		Set<Long> result = new HashSet<>(lAllList);
		return result;
	}

	public Collection<String> getDateNaissances() {
		EntityManager em = EMF.get().createEntityManager();
		List<String> lAllList = new ArrayList<>();
		try {
			TypedQuery<String> query = em.createQuery("SELECT DISTINCT "
					+ getAlias() + ".datenaissance FROM "
					+ getEntityClass().getSimpleName() + " " + getAlias(),
					String.class);
			lAllList = query.getResultList();
		} finally {
			em.close();
		}
		return lAllList;
	}
}