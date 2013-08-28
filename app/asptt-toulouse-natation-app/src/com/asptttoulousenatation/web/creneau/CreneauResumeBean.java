package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class CreneauResumeBean implements Serializable {

	private static final long serialVersionUID = -519221937238047033L;
	private String id;
	private String groupe;
	private String jour;
	private int effectif;
	
	public CreneauResumeBean() {

	}
	
	public void buildId() {
		String identifier = StringUtils.join(groupe.split(" "), "_");
		identifier = identifier + jour.trim();
		id = identifier;
	}

	public String getId() {
		return id;
	}


	public void setId(String pId) {
		id = pId;
	}


	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String pGroupe) {
		groupe = pGroupe;
	}

	public int getEffectif() {
		return effectif;
	}

	public void setEffectif(int pEffectif) {
		effectif = pEffectif;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String pJour) {
		jour = pJour;
	}
	
	
}