package com.asptt.core.server.dao.boutique;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.boutique.ProductEntity;
import com.asptt.core.server.service.EMF;

public class ProductDao extends DaoBase<ProductEntity> {

	
	
	@Override
	public List<ProductEntity> getAll() {
		EntityManager em = EMF.get().createEntityManager();
		List<ProductEntity> lAllList = new ArrayList<ProductEntity>();
		try {
			TypedQuery<ProductEntity> query = em.createQuery("SELECT " + getAlias()
					+ " FROM " + getEntityClass().getSimpleName() + " " + getAlias() + " ORDER BY " + getAlias() + ".title ASC",
					getEntityClass());
			lAllList = query.getResultList();
		} finally {
			em.close();
		}

		return lAllList;
	}

	@Override
	public Class<ProductEntity> getEntityClass() {
		return ProductEntity.class;
	}

	@Override
	public String getAlias() {
		return "product";
	}

	@Override
	public Object getKey(ProductEntity pEntity) {
		return pEntity.getId();
	}

}
