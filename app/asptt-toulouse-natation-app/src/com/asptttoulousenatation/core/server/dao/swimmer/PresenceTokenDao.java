package com.asptttoulousenatation.core.server.dao.swimmer;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.swimmer.PresenceToken;
import com.asptttoulousenatation.core.server.service.EMF;

public class PresenceTokenDao extends DaoBase<PresenceToken> {
	
	public PresenceToken findById(String pId) {
		return get(getEntityClass(), pId);
	}
	
	public void deleteByGroupe(Long groupe) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("DELETE FROM " + getEntityClass().getSimpleName() + " as " + getAlias() + " WHERE "
				+ getAlias() + ".groupe =:groupe");
		query.setParameter("groupe", groupe);
		try {
			query.executeUpdate();
		} finally {
			em.close();
		}
	}

	@Override
	public Class<PresenceToken> getEntityClass() {
		return PresenceToken.class;
	}

	@Override
	public String getAlias() {
		return "presencetoken";
	}

	@Override
	public Object getKey(PresenceToken pEntity) {
		return pEntity.getId();
	}

}
