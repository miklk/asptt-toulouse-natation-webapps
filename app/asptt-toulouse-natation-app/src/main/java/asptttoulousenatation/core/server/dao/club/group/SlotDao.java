package com.asptttoulousenatation.core.server.dao.club.group;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.service.EMF;

public class SlotDao extends DaoBase<SlotEntity> {

	@Override
	public Class<SlotEntity> getEntityClass() {
		return SlotEntity.class;
	}
	
	public List<SlotEntity> findByGroup(Long group) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, group, Operator.EQUAL));
		List<SlotEntity> entities = find(criteria);
		List<SlotEntity> result = new ArrayList<>(entities);
		return result;
	}
	
	public List<String> getPiscines() {
		LinkedHashSet<String> piscines = new LinkedHashSet<String>();
		for(SlotEntity entity: getAll()) {
			piscines.add(entity.getSwimmingPool());
		}
		return new ArrayList<String>(piscines);
	}
	
	public List<String> getDays(String piscine) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("SELECT DISTINCT " + getAlias()
				+ ".dayOfWeek FROM " + getEntityClass().getSimpleName()
				+ " as " + getAlias() + " WHERE " + getAlias()
				+ ".swimmingPool =:piscine");
		query.setParameter("piscine", piscine);
		final List<String> lResult;
		try {
			lResult = new ArrayList<String>(query.getResultList());
		} finally {
			em.close();
		}
		return lResult;
	}
	
	public List<Long> getGroupesDays(String piscine, String dayOfWeek) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("SELECT DISTINCT " + getAlias()
				+ ".group FROM " + getEntityClass().getSimpleName()
				+ " as " + getAlias() + " WHERE " + getAlias()
				+ ".swimmingPool =:piscine AND " + getAlias() +".dayOfWeek=:dayOfWeek");
		query.setParameter("piscine", piscine);
		query.setParameter("dayOfWeek", dayOfWeek);
		final List<Long> lResult;
		try {
			lResult = new ArrayList<>(query.getResultList());
		} finally {
			em.close();
		}
		return lResult;
	}

	@Override
	public String getAlias() {
		return "slot";
	}
	
	@Override
	public Object getKey(SlotEntity pEntity) {
		return pEntity.getId();
	}
}