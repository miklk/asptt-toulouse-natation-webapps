package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreneauResumeBean implements Serializable {

	private static final long serialVersionUID = -519221937238047033L;
	private int debut;
	private int fin;
	private List<CoupleValue<String, Integer>> groupes;
	
	public CreneauResumeBean() {
		groupes = new ArrayList<CoupleValue<String,Integer>>(3);
	}
	
	public void addGroupe(String nom, int effectif) {
		groupes.add(new CoupleValue<String, Integer>(nom, effectif));
	}

	public int getDebut() {
		return debut;
	}

	public void setDebut(int pDebut) {
		debut = pDebut;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int pFin) {
		fin = pFin;
	}

	public List<CoupleValue<String, Integer>> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<CoupleValue<String, Integer>> pGroupes) {
		groupes = pGroupes;
	}
}