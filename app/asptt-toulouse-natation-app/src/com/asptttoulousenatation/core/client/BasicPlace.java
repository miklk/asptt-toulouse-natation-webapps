package com.asptttoulousenatation.core.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BasicPlace<O extends Object> extends Place {
	public static class Tokenizer implements PlaceTokenizer<BasicPlace<?>> {

		public BasicPlace<?> getPlace(String pToken) {
			return new BasicPlace();
		}

		public String getToken(BasicPlace<?> pPlace) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private O object;

	public BasicPlace() {
		super();
	}
	public BasicPlace(O pObject) {
		this();
		object = pObject;
	}

	public O getObject() {
		return object;
	}

	public void setObject(O pObject) {
		object = pObject;
	}
}