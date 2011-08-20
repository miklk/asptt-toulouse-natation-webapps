package com.asptttoulousenatation.core.shared.user;

import net.customware.gwt.dispatch.shared.Action;

public class AuthenticationAction implements Action<AuthenticationResult> {

	private String emailAddress;
	private String password;
	
	public AuthenticationAction() {
		
	}

	public AuthenticationAction(String pEmailAddress, String pPassword) {
		emailAddress = pEmailAddress;
		password = pPassword;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String pEmailAddress) {
		emailAddress = pEmailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pPassword) {
		password = pPassword;
	}
}