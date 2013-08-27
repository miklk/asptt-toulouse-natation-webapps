package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;

public class CreneauResumeBean implements Serializable {

	private static final long serialVersionUID = -519221937238047033L;
	private String groupe;
	private int effectif;
	
	public CreneauResumeBean() {

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
	
}