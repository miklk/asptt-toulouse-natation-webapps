package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SwimmerStatWeekPrevuResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<List<Set<String>>> presences;
	
	public SwimmerStatWeekPrevuResult() {
		presences = new ArrayList<>(7);
		for(int i = 0; i < 7; i++) {
			List<Set<String>> times = new ArrayList<Set<String>>(4);
			presences.add(times);
			for(int j = 0; j < 4; j++) {
				times.add(new HashSet<String>());
			}
		}
	}
	
	public void addNageur(int day, int time, String nom, String prenom) {
		presences.get(day).get(time).add(nom + " " + prenom);
	}

	public List<List<Set<String>>> getPresences() {
		return presences;
	}

	public void setPresences(List<List<Set<String>>> presences) {
		this.presences = presences;
	}
	
}
