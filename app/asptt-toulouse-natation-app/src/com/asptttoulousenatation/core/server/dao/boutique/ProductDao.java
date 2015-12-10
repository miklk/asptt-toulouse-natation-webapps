package com.asptttoulousenatation.core.server.dao.boutique;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.boutique.ProductEntity;

public class ProductDao extends DaoBase<ProductEntity> {

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
