package com.asptttoulousenatation.core.server.dao.boutique;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.boutique.OrderProductEntity;
import com.asptttoulousenatation.core.server.service.EMF;

public class OrderProductDao extends DaoBase<OrderProductEntity> {
	
	public List<OrderProductEntity> findByOrder(Long order) {
		final EntityManager em = EMF.get().createEntityManager();
		Query query = em.createQuery("SELECT " + getAlias() + " FROM " + getEntityClass().getSimpleName() + " as "
				+ getAlias() + " WHERE " + getAlias() + ".order =:order");
		query.setParameter("order", order);
		final List<OrderProductEntity> lResult;
		try {
			lResult = new ArrayList<OrderProductEntity>(query.getResultList());
		} finally {
			em.close();
		}
		return lResult;
	}

	@Override
	public Class<OrderProductEntity> getEntityClass() {
		return OrderProductEntity.class;
	}

	@Override
	public String getAlias() {
		return "orderproduct";
	}

	@Override
	public Object getKey(OrderProductEntity pEntity) {
		return pEntity.getId();
	}

}
