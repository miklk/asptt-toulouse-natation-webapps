package com.asptttoulousenatation.core.server.dao.club.subscription;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.club.subscription.SubscriptionPrice;

public class SubscriptionPriceDao extends DaoBase<SubscriptionPrice> {

	@Override
	public Class<SubscriptionPrice> getEntityClass() {
		return SubscriptionPrice.class;
	}
}