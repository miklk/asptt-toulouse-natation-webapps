package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;

public class CreneauResumeBean implements Serializable {

	private static final long serialVersionUID = -519221937238047033L;
	private String heure;
	private int effectif;
	private int total;
	private boolean exists;
	private boolean complet;
	
	public CreneauResumeBean() {
		exists = false;
		complet = true;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String pHeure) {
		heure = pHeure;
	}

	public int getEffectif() {
		return effectif;
	}

	public void setEffectif(int pEffectif) {
		effectif = pEffectif;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int pTotal) {
		total = pTotal;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean pExists) {
		exists = pExists;
	}

	public boolean isComplet() {
		return complet;
	}

	public void setComplet(boolean pComplet) {
		complet = pComplet;
	}
	
}