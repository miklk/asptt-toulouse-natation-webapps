package com.asptt.core.user;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserCreateAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2930108834372968155L;
	private String email;
	private String nom;
	private String prenom;
	private Set<String> authorizations;
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String pEmail) {
		email = pEmail;
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

	public Set<String> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set<String> authorizations) {
		this.authorizations = authorizations;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}