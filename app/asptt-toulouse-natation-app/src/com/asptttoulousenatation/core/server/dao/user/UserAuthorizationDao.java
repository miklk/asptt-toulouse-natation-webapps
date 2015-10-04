package com.asptttoulousenatation.core.server.dao.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.user.UserAuthorizationEntity;
import com.asptttoulousenatation.core.server.service.EMF;

public class UserAuthorizationDao extends DaoBase<UserAuthorizationEntity> {
	
	public List<UserAuthorizationEntity> getAuthorization(Long user) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("SELECT " + getAlias() + " FROM " + getEntityClass().getSimpleName()
				+ " as " + getAlias() + " WHERE " + getAlias()
				+ ".user =:user");
		query.setParameter("user", user);
		final List<UserAuthorizationEntity> lResult;
		try {
			lResult = new ArrayList<UserAuthorizationEntity>(query.getResultList());
		} finally {
			em.close();
		}
		return lResult;
	}
	

	@Override
	public Class<UserAuthorizationEntity> getEntityClass() {
		return UserAuthorizationEntity.class;
	}

	@Override
	public String getAlias() {
		return "userAuth";
	}

	@Override
	public Object getKey(UserAuthorizationEntity pEntity) {
		return pEntity.getId();
	}
}