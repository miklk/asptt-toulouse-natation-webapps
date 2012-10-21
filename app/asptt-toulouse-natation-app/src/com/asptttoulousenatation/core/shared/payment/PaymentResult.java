package com.asptttoulousenatation.core.shared.payment;

import net.customware.gwt.dispatch.shared.Result;

public class PaymentResult implements Result {

	private String message;
	
	public PaymentResult() {
		
	}
	

	public PaymentResult(String pMessage) {
		super();
		message = pMessage;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String pMessage) {
		message = pMessage;
	}
	
}
