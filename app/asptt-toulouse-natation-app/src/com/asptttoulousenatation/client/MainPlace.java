package com.asptttoulousenatation.client;

import com.asptttoulousenatation.core.client.BasicPlace;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.place.shared.PlaceTokenizer;

public class MainPlace extends BasicPlace<UserUi> {
	public static class Tokenizer implements PlaceTokenizer<MainPlace> {

		public MainPlace getPlace(String pToken) {
			return new MainPlace();
		}

		public String getToken(MainPlace pPlace) {
			return "";
		}
		
	}
	
	public MainPlace() {
		super();
	}

	public MainPlace(UserUi pObject) {
		super(pObject);
	}

	
}
