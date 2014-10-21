package com.asptttoulousenatation.core.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement
public class UserCreateAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2930108834372968155L;
	private String email;
	private String nom;
	private String prenom;
	private Set<String> profiles;
	
	public UserCreateAction() {
		
	}
	

	public UserCreateAction(String pEmail, String pNom, String pPrenom,
			Set<String> pProfiles) {
		super();
		email = pEmail;
		nom = pNom;
		prenom = pPrenom;
		profiles = pProfiles;
	}


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

	public Set<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<String> pProfiles) {
		profiles = pProfiles;
	}
	
	public static UserCreateAction valueOf(String pValue) {
		UserCreateAction action = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			action = mapper.readValue(pValue, UserCreateAction.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return action;
	}
}
