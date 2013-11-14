package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;

public class AdherentListEmailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4468044892482381684L;
	private String sujet;
	private String corps;
	private String[] destinataires;
	
	public AdherentListEmailBean() {
		destinataires = new String[0];
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String pSujet) {
		sujet = pSujet;
	}

	public String getCorps() {
		return corps;
	}

	public void setCorps(String pCorps) {
		corps = pCorps;
	}

	public String[] getDestinataires() {
		return destinataires;
	}

	public void setDestinataires(String[] pDestinataires) {
		destinataires = pDestinataires;
	}
}