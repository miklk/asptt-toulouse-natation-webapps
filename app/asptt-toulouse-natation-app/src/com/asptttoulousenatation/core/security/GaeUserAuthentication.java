package com.asptttoulousenatation.core.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;

public class GaeUserAuthentication implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2771510831327668830L;
	private UserEntity user;
	private boolean authenticated;
	
	public GaeUserAuthentication(UserEntity pUser, Object pDetails) {
		user = pUser;
	}

	@Override
	public String getName() {
		return user.getEmailaddress();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<AppRole> authorities = new HashSet<>();
		for(String profile: user.getProfiles()) {
			authorities.add(AppRole.valueOf(profile));
		}
		return authorities;
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
		return user;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean pArg0) throws IllegalArgumentException {
		authenticated = pArg0;
	}
}