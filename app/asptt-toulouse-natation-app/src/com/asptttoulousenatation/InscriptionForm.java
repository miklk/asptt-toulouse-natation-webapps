package com.asptttoulousenatation;

import java.io.Serializable;

public class InscriptionForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8058949054507753535L;
	private String civilite;
	private String nom;
	private String prenom;
	
	public InscriptionForm() {
		
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String pCivilite) {
		civilite = pCivilite;
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
	
}