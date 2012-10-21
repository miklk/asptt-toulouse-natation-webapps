package com.asptttoulousenatation.core.shared.club.subscription;

import net.customware.gwt.dispatch.shared.Result;


public class GetPriceResult implements Result {

	private SubscriptionPriceUi price;
	
	public GetPriceResult() {
		
	}

	public SubscriptionPriceUi getPrice() {
		return price;
	}

	public void setPrice(SubscriptionPriceUi pPrice) {
		price = pPrice;
	}
}