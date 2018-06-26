package com.asptt.core.server.dao.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.user.UserAuthorizationEntity;
import com.asptt.core.server.service.EMF;

public class UserAuthorizationDao extends DaoBase<UserAuthorizationEntity> {

	public List<UserAuthorizationEntity> findByUser(Long user) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("SELECT " + getAlias() + " FROM " + getEntityClass().getSimpleName() + " as "
				+ getAlias() + " WHERE " + getAlias() + ".user =:user");
		query.setParameter("user", user);
		final List<UserAuthorizationEntity> lResult;
		try {
			lResult = new ArrayList<UserAuthorizationEntity>(query.getResultList());
		} finally {
			em.close();
		}
		return lResult;
	}
	
	public List<UserAuthorizationEntity> findByUserAndAccess(Long user, String access) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("SELECT " + getAlias() + " FROM " + getEntityClass().getSimpleName() + " as "
				+ getAlias() + " WHERE " + getAlias() + ".user =:user AND " + getAlias() + ".access =:access");
		query.setParameter("user", user);
		query.setParameter("access", access);
		final List<UserAuthorizationEntity> lResult;
		try {
			lResult = new ArrayList<UserAuthorizationEntity>(query.getResultList());
		} finally {
			em.close();
		}
		return lResult;
	}

	public void deleteByUser(Long user) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("DELETE FROM " + getEntityClass().getSimpleName() + " as " + getAlias() + " WHERE "
				+ getAlias() + ".user =:user");
		query.setParameter("user", user);
		try {
			query.executeUpdate();
		} finally {
			em.close();
		}
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