package com.asptttoulousenatation.core.shared.user;

import net.customware.gwt.dispatch.shared.Result;

public class PasswordForgetResult implements Result {

	private boolean sended;
	
	public PasswordForgetResult() {
		
	}

	public PasswordForgetResult(boolean pSended) {
		super();
		sended = pSended;
	}

	public boolean isSended() {
		return sended;
	}

	public void setSended(boolean pSended) {
		sended = pSended;
	}

}