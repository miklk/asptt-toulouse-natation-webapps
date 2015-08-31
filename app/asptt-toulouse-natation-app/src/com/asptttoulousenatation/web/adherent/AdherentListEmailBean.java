package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;

public class AdherentListEmailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4468044892482381684L;
	
	private String from;
	private String sujet;
	private String corps;
	private boolean all;
	private boolean allEnf;
	private boolean allCompetition;
	
	public AdherentListEmailBean() {
		all = false;
	}

	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
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

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean pAll) {
		all = pAll;
	}

	public boolean isAllEnf() {
		return allEnf;
	}

	public void setAllEnf(boolean pAllEnf) {
		allEnf = pAllEnf;
	}

	public boolean isAllCompetition() {
		return allCompetition;
	}

	public void setAllCompetition(boolean pAllCompetition) {
		allCompetition = pAllCompetition;
	}
	
}