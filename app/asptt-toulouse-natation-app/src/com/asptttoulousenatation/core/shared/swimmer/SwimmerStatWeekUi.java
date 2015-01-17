package com.asptttoulousenatation.core.shared.swimmer;

import java.util.ArrayList;
import java.util.List;

public class SwimmerStatWeekUi {

	private String nom;
	private String prenom;
	private int totalDistance;
	private int totalPresence;
	private List<Integer[]> distances;
	private Boolean[] presences;

	public SwimmerStatWeekUi() {
		distances = new ArrayList<Integer[]>(7);
		presences = new Boolean[] {Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE , Boolean.FALSE};
		for (int i = 0; i < 6; i++) {
			distances.add(new Integer[] { 0, 0, 0, 0});
		}
	}

	public void addDistance(int day, int index, int pDistance) {
		distances.get(day)[index] = pDistance;
	}
	
	public void addPresence(int day, Boolean pPresence) {
		presences[day] = pPresence;
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
			for (Boolean presence: presences) {
				if(presence) {
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
	
	public Boolean[] getPresences() {
		return presences;
	}

	public void setPresences(Boolean[] pPresences) {
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