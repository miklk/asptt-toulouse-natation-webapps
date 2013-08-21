package com.asptttoulousenatation;

import java.io.Serializable;

public class ImportInscriptionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8486654952011151695L;
	private String nom;
	private String prenom;
	private String naissance;
	private String groupe;
	
	public ImportInscriptionBean() {
		
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

	public String getNaissance() {
		return naissance;
	}

	public void setNaissance(String pNaissance) {
		naissance = pNaissance;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String pGroupe) {
		groupe = pGroupe;
	}
	
}
