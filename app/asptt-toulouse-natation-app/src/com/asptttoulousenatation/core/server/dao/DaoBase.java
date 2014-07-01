package com.asptttoulousenatation.core.server.dao;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.asptttoulousenatation.core.server.dao.entity.Entity;
import com.asptttoulousenatation.core.server.dao.search.CriteriaUtils;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.service.PMF;
import com.google.appengine.api.datastore.Key;

public abstract class DaoBase<E extends Entity> {

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
		PersistenceManager lPersistenceManager = PMF.getPersistenceManager();

		E lResult = null;
		try {
			lResult = (E) lPersistenceManager.makePersistent(pEntity);
		} finally {
			lPersistenceManager.close();
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
		PersistenceManager lPersistenceManager = PMF.getPersistenceManager();

		Extent<E> lAll = null;
		List<E> lAllList = new LinkedList<E>();
		try {
			lAll = lPersistenceManager.getExtent(pClass);
			for (E lE : lAll) {
				try {
				lAllList.add(lPersistenceManager.detachCopy(lE));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			lPersistenceManager.close();
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
	private E get(Class<E> pClass, Object pId) {
		E lResult = null;
		PersistenceManager lPersistenceManager = PMF.getPersistenceManager();
		try {
			E lRes = (E) lPersistenceManager.getObjectById(pClass, pId);
			lResult = lPersistenceManager.detachCopy(lRes);
		} finally {
			lPersistenceManager.close();
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
		PersistenceManager lPersistenceManager = PMF.getPersistenceManager();
		try {
			lPersistenceManager.deletePersistent(pEntity);
		} finally {
			lPersistenceManager.close();
		}
	}

	private List<E> find(Class<E> pClass,
			List<CriterionDao<? extends Object>> pCriteria, Operator pOperator, OrderDao pOrder) {
		final PersistenceManager lPersistenceManager = PMF
				.getPersistenceManager();
		String lQueryAsString = "SELECT FROM " + pClass.getName() + " WHERE "
				+ CriteriaUtils.getWhereClause(pCriteria, pOperator);
		final Query lQuery = lPersistenceManager.newQuery(lQueryAsString);
		if(pOrder != null) {
			lQuery.setOrdering(pOrder.getField().toString().toLowerCase() + " " + pOrder.getOperator().toString());
		}
		final List<E> lResult;
		try {
			lResult = (List<E>) lPersistenceManager
					.detachCopyAll((List<E>) lQuery.execute());

		} finally {
			lQuery.closeAll();
			if (!lPersistenceManager.isClosed()) {
				lPersistenceManager.close();
			}
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
	
	public List<E> find(List<CriterionDao<? extends Object>> pCriteria, OrderDao pOrder) {
		return find(getEntityClass(), pCriteria, Operator.AND, pOrder);
	}

	public abstract Class<E> getEntityClass();
}
