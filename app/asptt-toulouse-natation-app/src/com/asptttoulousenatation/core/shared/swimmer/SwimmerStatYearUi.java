package com.asptttoulousenatation.core.shared.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SwimmerStatYearUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8097882747203552428L;
	private String nom;
	private String prenom;
	private int totalDistance;
	private int totalPresence;
	private List<Integer> distances;
	private List<Integer> presences;

	public SwimmerStatYearUi() {
		distances = new ArrayList<>(12);
		presences = new ArrayList<>(12);
		for (int i = 0; i < 12; i++) {
			distances.add(0);
			presences.add(0);
		}
	}
	
	public void addDistance(int month, int pDistance) {
		distances.set(month, distances.get(month) + pDistance);
	}
	
	public void addPresence(int month, int pPresence) {
		presences.set(month, presences.get(month) + pPresence);
	}
	
	public void computeTotalDistance() {
		if (totalDistance == 0) {
			int lTotal = 0;
			for (Integer distance : distances) {
				lTotal += distance;
			}
			totalDistance = lTotal;
		}
	}
	
	public void computeTotalPresence() {
		if (totalPresence == 0) {
			int lTotal = 0;
			for (Integer presence: presences) {
				if(presence > 0) {
					lTotal++;
				}
			}
			totalPresence = lTotal;
		}
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
	
	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int pTotalDistance) {
		totalDistance = pTotalDistance;
	}

	public int getTotalPresence() {
		return totalPresence;
	}

	public void setTotalPresence(int pTotalPresence) {
		totalPresence = pTotalPresence;
	}

	public List<Integer> getDistances() {
		return distances;
	}

	public void setDistances(List<Integer> pDistances) {
		distances = pDistances;
	}

	public List<Integer> getPresences() {
		return presences;
	}

	public void setPresences(List<Integer> pPresences) {
		presences = pPresences;
	}
}