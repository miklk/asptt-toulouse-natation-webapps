package com.asptttoulousenatation.core.authentication;

import java.io.Serializable;

import com.asptttoulousenatation.core.shared.user.UserUi;

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
	
	public LoginResult() {
		logged = false;
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
}