package com.asptttoulousenatation.core.shared.user;

import net.customware.gwt.dispatch.shared.Action;

public class PasswordForgetAction implements Action<PasswordForgetResult> {

	private String emailAddress;
	
	public PasswordForgetAction() {
		
	}

	public PasswordForgetAction(String pEmailAddress) {
		super();
		emailAddress = pEmailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String pEmailAddress) {
		emailAddress = pEmailAddress;
	}
	
}
