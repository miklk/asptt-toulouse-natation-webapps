package com.asptttoulousenatation.core.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;
import com.asptttoulousenatation.core.server.dao.search.CriteriaUtils;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.service.EMF;
import com.google.appengine.api.datastore.Key;

public abstract class DaoBase<E extends IEntity> {

	private static final Logger LOG = Logger.getLogger(DaoBase.class
			.getSimpleName());

	/**
	 * Create the entity into the DB.
	 * 
	 * @param <E>
	 *            Class of the entity to create.
	 * @param pEntity
	 *            Entity to create
	 * @return The created entity.
	 */
	public E save(E pEntity) {
		EntityManager em = EMF.get().createEntityManager();
		E lResult = null;
		EntityTransaction tx = em.getTransaction();
		try {
			// Try to find existing
			Object key = getKey(pEntity);
			final E existingEntity;
			if (key != null) {
				existingEntity = em.find(getEntityClass(), getKey(pEntity));
			} else {
				existingEntity = null;
			}
			tx.begin();
			if (existingEntity != null) {
				lResult = em.merge(pEntity);

			} else {
				em.persist(pEntity);
				lResult = pEntity;
			}
			tx.commit();

		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error saving entity", e);
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}

		return lResult;
	}

	/**
	 * Get all entity of the specified class
	 * 
	 * @param <E>
	 *            Class of the entities
	 * @param pClass
	 *            Class of the entities
	 * @return Entities
	 */
	private List<E> getAll(Class<E> pClass) {
		EntityManager em = EMF.get().createEntityManager();
		List<E> lAllList = new ArrayList<E>();
		try {
			TypedQuery<E> query = em.createQuery("SELECT " + getAlias()
					+ " FROM " + pClass.getSimpleName() + " " + getAlias(),
					pClass);
			lAllList = query.getResultList();
		} finally {
			em.close();
		}

		return lAllList;
	}

	/**
	 * Get an entity from its technical identifier.
	 * 
	 * @param <E>
	 *            Class of the entity
	 * @param pClass
	 *            Class of the entity
	 * @param pId
	 *            Identity of the entity to retrieve.
	 * @return Entity
	 */
	protected E get(Class<E> pClass, Object pId) {
		E lResult = null;
		if (pId != null) {
			EntityManager em = EMF.get().createEntityManager();
			try {
				lResult = (E) em.find(pClass, pId);
			} finally {
				em.close();
			}
		} else {
			LOG.severe(pClass + " retrieving object is null");
		}
		return lResult;
	}

	public E get(Long pId) {
		return get(getEntityClass(), pId);
	}

	public E get(Key pId) {
		return get(getEntityClass(), pId);
	}

	public List<E> getAll() {
		return getAll(getEntityClass());
	}

	public void delete(E pEntity) {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			// Try to find existing
			Object key = getKey(pEntity);
			final E existingEntity;
			if (key != null) {
				existingEntity = em.find(getEntityClass(), getKey(pEntity));
			} else {
				existingEntity = null;
			}
			tx.begin();
			if (existingEntity != null) {
				em.remove(existingEntity);
			}
			tx.commit();
		} catch (Exception e) {
			LOG.severe(e.getMessage());
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

	public void delete(Long pId) {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			E entity = (E) em.find(getEntityClass(), pId);
			tx.begin();
			em.remove(entity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			em.close();
		}
	}

	private List<E> find(Class<E> pClass,
			List<CriterionDao<? extends Object>> pCriteria, Operator pOperator,
			OrderDao pOrder) {
		return find(pClass, pCriteria, pOperator, pOrder, 0, 0);
	}
	
	private List<E> find(Class<E> pClass,
			List<CriterionDao<? extends Object>> pCriteria, Operator pOperator,
			OrderDao pOrder, int limitFrom, int limitTo) {
		final EntityManager em = EMF.get().createEntityManager();
		String lQueryAsString = "SELECT "
				+ getAlias()
				+ " FROM "
				+ pClass.getSimpleName()
				+ " "
				+ getAlias()
				+ " WHERE "
				+ CriteriaUtils
						.getWhereClause(pCriteria, pOperator, getAlias());
		if (pOrder != null) {
			lQueryAsString += " ORDER BY " + pOrder.getField().getFieldName()
					+ " " + pOrder.getOperator().toString();
		}
		final TypedQuery<E> lQuery = em.createQuery(lQueryAsString, pClass);
		lQuery.setFirstResult(limitFrom);
		if (limitTo > 0) {
			lQuery.setMaxResults(limitTo);
		}
		final List<E> lResult;
		try {
			lResult = lQuery.getResultList();

		} finally {
			em.close();
		}
		return lResult;
	}

	public List<E> find(List<CriterionDao<? extends Object>> pCriteria,
			Operator pOperator, OrderDao pOrder) {
		return find(getEntityClass(), pCriteria, pOperator, pOrder);
	}

	public List<E> find(List<CriterionDao<? extends Object>> pCriteria) {
		return find(getEntityClass(), pCriteria, Operator.AND, null);
	}

	public List<E> find(List<CriterionDao<? extends Object>> pCriteria,
			OrderDao pOrder) {
		return find(getEntityClass(), pCriteria, Operator.AND, pOrder);
	}
	
	public List<E> find(List<CriterionDao<? extends Object>> pCriteria, int limitFrom, int limitTo) {
		return find(getEntityClass(), pCriteria, Operator.AND, null, limitFrom, limitTo);
	}
	
	public Long count(List<CriterionDao<? extends Object>> pCriteria) {
		final EntityManager em = EMF.get().createEntityManager();
		String lQueryAsString = "SELECT count("
				+ getAlias()
				+ ") FROM "
				+ getEntityClass().getSimpleName()
				+ " "
				+ getAlias()
				+ " WHERE "
				+ CriteriaUtils
						.getWhereClause(pCriteria, Operator.AND, getAlias());
		final TypedQuery<Long> lQuery = em.createQuery(lQueryAsString, Long.class);
		final Long lResult;
		try {
			lResult = lQuery.getSingleResult();

		} finally {
			em.close();
		}
		return lResult;
	}
	
	public Long countAll() {
		EntityManager em = EMF.get().createEntityManager();
		Long result;
		try {
			TypedQuery<Long> query = em.createQuery("SELECT count(" + getAlias()
					+ ") FROM " + getEntityClass().getSimpleName() + " " + getAlias(),
					Long.class);
			result = query.getSingleResult();
		} finally {
			em.close();
		}

		return result;
	}

	public abstract Class<E> getEntityClass();

	public abstract String getAlias();

	public abstract Object getKey(E pEntity);
}
