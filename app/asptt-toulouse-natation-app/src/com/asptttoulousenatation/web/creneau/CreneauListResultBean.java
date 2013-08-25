package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreneauListResultBean implements Serializable {

	private static final long serialVersionUID = 6233655392860850100L;
	private String jour;
	private int debut;
	private int fin;
	private List<CoupleValue<String, Integer>> groupes;
	
	public CreneauListResultBean() {
		groupes = new ArrayList<CoupleValue<String,Integer>>(3);
	}
	
	public void addGroupe(String nom, int effectif) {
		groupes.add(new CoupleValue<String, Integer>(nom, effectif));
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String pJour) {
		jour = pJour;
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