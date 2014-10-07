package com.asptttoulousenatation.core.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class GaeUserAuthentication implements Authentication {

	
	public GaeUserAuthentication(GaeUser pUser, Object pDetails) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "toto";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAuthenticated(boolean pArg0) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

}
