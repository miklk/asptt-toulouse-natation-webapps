package com.asptttoulousenatation.core.shared.user;

import net.customware.gwt.dispatch.shared.Result;

public class AuthenticationResult implements Result {

	private boolean authenticated;
	private UserUi user;
	
	public AuthenticationResult() {
		
	}

	public AuthenticationResult(boolean pAuthenticated, UserUi pUser) {
		authenticated = pAuthenticated;
		user = pUser;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean pAuthenticated) {
		authenticated = pAuthenticated;
	}

	public UserUi getUser() {
		return user;
	}

	public void setUser(UserUi pUser) {
		user = pUser;
	}
}
