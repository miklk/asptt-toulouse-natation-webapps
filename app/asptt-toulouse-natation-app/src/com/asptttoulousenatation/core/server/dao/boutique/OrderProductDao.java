package com.asptttoulousenatation.core.server.dao.boutique;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.boutique.OrderProductEntity;

public class OrderProductDao extends DaoBase<OrderProductEntity> {

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
