package com.asptttoulousenatation.core.security;

import java.io.Serializable;
import java.util.Set;

public class GaeUser implements Serializable {
	  private  String userId;
	  private  String email;
	  private  String nickname;
	  private  String forename;
	  private  String surname;
	  private  Set<AppRole> authorities;
	  private  boolean enabled;
	  
	  
	  
	public GaeUser(String pUserId, String pEmail, String pNickname) {
		super();
		userId = pUserId;
		email = pEmail;
		nickname = pNickname;
	}
	public GaeUser(String pUserId, String pEmail, String pNickname,
			String pForename, String pSurname, Set<AppRole> pAuthorities,
			boolean pEnabled) {
		super();
		userId = pUserId;
		email = pEmail;
		nickname = pNickname;
		forename = pForename;
		surname = pSurname;
		authorities = pAuthorities;
		enabled = pEnabled;
	}
	public String getUserId() {
		return userId;
	}
	public String getEmail() {
		return email;
	}
	public String getNickname() {
		return nickname;
	}
	public String getForename() {
		return forename;
	}
	public String getSurname() {
		return surname;
	}
	public Set<AppRole> getAuthorities() {
		return authorities;
	}
	public boolean isEnabled() {
		return enabled;
	}
	  
	  
}
