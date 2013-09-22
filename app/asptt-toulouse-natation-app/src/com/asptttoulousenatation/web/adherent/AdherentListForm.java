package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;

public class AdherentListForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1388609378452601726L;
	private String nom;
	private String prenom;
	private Long groupe;
	private String creneau;
	private boolean dossier;
	private AdherentListEmailBean email;
	private AdherentAffecterBean affecter;
	
	public AdherentListForm() {
		email = new AdherentListEmailBean();
		affecter = new AdherentAffecterBean();
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

	public Long getGroupe() {
		return groupe;
	}

	public void setGroupe(Long pGroupe) {
		groupe = pGroupe;
	}

	public String getCreneau() {
		return creneau;
	}

	public void setCreneau(String pCreneau) {
		creneau = pCreneau;
	}

	public boolean isDossier() {
		return dossier;
	}

	public void setDossier(boolean pDossier) {
		dossier = pDossier;
	}

	public AdherentListEmailBean getEmail() {
		return email;
	}

	public void setEmail(AdherentListEmailBean pEmail) {
		email = pEmail;
	}

	public AdherentAffecterBean getAffecter() {
		return affecter;
	}

	public void setAffecter(AdherentAffecterBean pAffecter) {
		affecter = pAffecter;
	}
	
}