package com.asptttoulousenatation.core.authentication;

import java.io.Serializable;

public class AuthenticationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6520778994150078443L;
	private String providerUrl;
	
	public AuthenticationResult() {
		
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String pProviderUrl) {
		providerUrl = pProviderUrl;
	}
	
}
