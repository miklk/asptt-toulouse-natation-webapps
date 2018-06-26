package com.asptt.core.authentication;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.asptt.core.shared.user.UserUi;

public class LoginResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3795452559693583857L;
	private boolean logged;
	private UserUi user;
	
	private String nom;
	private String prenom;
	private String email;
	private Set<String> authorizations;
	private String token;
	
	public LoginResult() {
		authorizations = new HashSet<>();
		logged = false;
	}
	
	public void addAuthorization(String authorization) {
		authorizations.add(authorization);
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean pLogged) {
		logged = pLogged;
	}

	public UserUi getUser() {
		return user;
	}

	public void setUser(UserUi pUser) {
		user = pUser;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String pNom) {
		nom = pNom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String pPrenom) {
		prenom = pPrenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String pEmail) {
		email = pEmail;
	}

	public Set<String> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set<String> authorizations) {
		this.authorizations = authorizations;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}