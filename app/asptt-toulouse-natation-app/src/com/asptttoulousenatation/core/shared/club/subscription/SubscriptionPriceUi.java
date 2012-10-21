package com.asptttoulousenatation.core.shared.club.subscription;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SubscriptionPriceUi implements IsSerializable {

	private Long id;
	private float price;
	
	public SubscriptionPriceUi() {
		
	}

	public SubscriptionPriceUi(Long pId, float pPrice) {
		this();
		id = pId;
		price = pPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float pPrice) {
		price = pPrice;
	}
}