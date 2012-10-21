package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.club.subscription.SubscriptionPrice;
import com.asptttoulousenatation.core.shared.club.subscription.SubscriptionPriceUi;

public class SubscriptionPriceTransformer extends AbstractEntityTransformer<SubscriptionPriceUi, SubscriptionPrice> {

	@Override
	public SubscriptionPriceUi toUi(SubscriptionPrice pEntity) {
		final SubscriptionPriceUi lUi = new SubscriptionPriceUi();
		lUi.setId(pEntity.getId());
		lUi.setPrice(pEntity.getPrice());
		return lUi;
	}

}
