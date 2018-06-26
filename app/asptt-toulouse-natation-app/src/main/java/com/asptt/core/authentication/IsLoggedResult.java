package com.asptt.core.authentication;

import java.io.Serializable;

public class IsLoggedResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isLogged;
	public boolean isLogged() {
		return isLogged;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
}
