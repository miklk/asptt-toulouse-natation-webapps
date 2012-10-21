package com.asptttoulousenatation.client.subscription;

import com.asptttoulousenatation.core.client.BasicPlace;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SubscriptionPlace extends BasicPlace<Object> {
	
	public SubscriptionPlace() {
		
	}

	public static class Tokenizer implements PlaceTokenizer<SubscriptionPlace> {

		public SubscriptionPlace getPlace(String pToken) {
			return new SubscriptionPlace();
		}

		public String getToken(SubscriptionPlace pPlace) {
			return null;
		}
		
	}
}