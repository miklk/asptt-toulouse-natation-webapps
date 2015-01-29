package com.asptttoulousenatation.core.swimmer;

import java.util.ArrayList;
import java.util.List;

public class SwimmerStatWeekUi {

	private String nom;
	private String prenom;
	private int totalDistance;
	private int totalPresence;
	private List<Integer[]> distances;
	private Integer[] presences;

	public SwimmerStatWeekUi() {
		distances = new ArrayList<Integer[]>(7);
		presences = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < 7; i++) {
			distances.add(new Integer[] { 0, 0, 0, 0 });
		}
	}

	public void addDistance(int day, int index, int pDistance) {
		distances.get(day)[index] = pDistance;
	}

	public void addPresence(int day, Boolean pPresence) {
		presences[day] = presences[day] + (pPresence ? 1 : 0);
	}

	public List<Integer[]> getDistances() {
		return distances;
	}

	public void setDistances(List<Integer[]> pDistances) {
		distances = pDistances;
	}

	public void computeTotalDistance() {
		if (totalDistance == 0) {
			int lTotal = 0;
			for (Integer[] distance : distances) {
				int sousTotal = distance[0] + distance[1] + distance[2];
				distance[3] = sousTotal;
				lTotal += sousTotal;
			}
			totalDistance = lTotal;
		}
	}

	public void computeTotalPresence() {
		if (totalPresence == 0) {
			int lTotal = 0;
			for (Integer presence : presences) {
				lTotal += presence;
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

	public Integer[] getPresences() {
		return presences;
	}

	public void setPresences(Integer[] pPresences) {
		presences = pPresences;
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

}