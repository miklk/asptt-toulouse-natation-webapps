package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;

public class AdherentListForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1388609378452601726L;
	private String searchNom;
	private String searchNomMinus;
	private String searchPrenom;
	private String searchEmail;
	private Long searchGroupe;
	private String searchCreneau;
	private String searchPiscine;
	private boolean searchDossier;
	private boolean searchSaisie;
	private boolean searchPaiement;
	private boolean searchCertificat;
	private Long[] selections;
	private AdherentListEmailBean sendEmail;
	private AdherentAffecterBean affecter;
	
	public AdherentListForm() {
		selections = new Long[0];
		sendEmail = new AdherentListEmailBean();
		affecter = new AdherentAffecterBean();
	}

	public String getSearchNom() {
		return searchNom;
	}


	public void setSearchNom(String pSearchNom) {
		searchNom = pSearchNom;
	}


	public String getSearchPrenom() {
		return searchPrenom;
	}


	public void setSearchPrenom(String pSearchPrenom) {
		searchPrenom = pSearchPrenom;
	}


	public Long getSearchGroupe() {
		return searchGroupe;
	}


	public void setSearchGroupe(Long pSearchGroupe) {
		searchGroupe = pSearchGroupe;
	}


	public String getSearchCreneau() {
		return searchCreneau;
	}


	public void setSearchCreneau(String pSearchCreneau) {
		searchCreneau = pSearchCreneau;
	}


	public boolean isSearchDossier() {
		return searchDossier;
	}


	public void setSearchDossier(boolean pSearchDossier) {
		searchDossier = pSearchDossier;
	}


	public boolean isSearchSaisie() {
		return searchSaisie;
	}


	public void setSearchSaisie(boolean pSearchSaisie) {
		searchSaisie = pSearchSaisie;
	}


	public AdherentListEmailBean getSendEmail() {
		return sendEmail;
	}

	public void setEmail(AdherentListEmailBean pSendEmail) {
		sendEmail = pSendEmail;
	}

	public AdherentAffecterBean getAffecter() {
		return affecter;
	}

	public void setAffecter(AdherentAffecterBean pAffecter) {
		affecter = pAffecter;
	}

	public boolean isSaisie() {
		return searchSaisie;
	}

	public void setSaisie(boolean pSaisie) {
		searchSaisie = pSaisie;
	}

	public String getSearchPiscine() {
		return searchPiscine;
	}

	public void setSearchPiscine(String pSearchPiscine) {
		searchPiscine = pSearchPiscine;
	}

	public Long[] getSelections() {
		return selections;
	}

	public void setSelections(Long[] pSelections) {
		selections = pSelections;
	}

	public String getSearchEmail() {
		return searchEmail;
	}

	public void setSearchEmail(String pSearchEmail) {
		searchEmail = pSearchEmail;
	}

	public String getSearchNomMinus() {
		return searchNomMinus;
	}

	public void setSearchNomMinus(String pSearchNomMinus) {
		searchNomMinus = pSearchNomMinus;
	}

	public boolean isSearchPaiement() {
		return searchPaiement;
	}

	public void setSearchPaiement(boolean pSearchPaiement) {
		searchPaiement = pSearchPaiement;
	}

	public boolean isSearchCertificat() {
		return searchCertificat;
	}

	public void setSearchCertificat(boolean pSearchCertificat) {
		searchCertificat = pSearchCertificat;
	}

}