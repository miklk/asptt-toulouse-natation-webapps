package com.asptttoulousenatation.core.server.dao.boutique;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.boutique.OrderEntity;

public class OrderDao extends DaoBase<OrderEntity> {

	@Override
	public Class<OrderEntity> getEntityClass() {
		return OrderEntity.class;
	}

	@Override
	public String getAlias() {
		return "orderEntity";
	}

	@Override
	public Object getKey(OrderEntity pEntity) {
		return pEntity.getId();
	}
}
