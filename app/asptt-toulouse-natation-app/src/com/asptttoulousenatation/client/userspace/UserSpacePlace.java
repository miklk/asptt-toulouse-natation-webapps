package com.asptttoulousenatation.client.userspace;

import com.asptttoulousenatation.core.client.BasicPlace;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UserSpacePlace extends BasicPlace<UserUi> {

	public static class Tokenizer implements PlaceTokenizer<UserSpacePlace> {

		public UserSpacePlace getPlace(String pToken) {
			return new UserSpacePlace();
		}

		public String getToken(UserSpacePlace pPlace) {
			return null;
		}
		
	}
	public UserSpacePlace() {
		super();
	}

	public UserSpacePlace(UserUi pObject) {
		super(pObject);
	}
	
}
