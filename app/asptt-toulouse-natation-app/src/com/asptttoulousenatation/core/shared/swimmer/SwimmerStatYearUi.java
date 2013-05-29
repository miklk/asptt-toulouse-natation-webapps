package com.asptttoulousenatation.core.shared.swimmer;

import java.util.ArrayList;
import java.util.List;

public class SwimmerStatYearUi implements ISwimmerStatUi {

	private String swimmer;
	private int total;
	private List<Integer> distances;

	public SwimmerStatYearUi() {
		distances = new ArrayList<Integer>(12);
		for (int i = 0; i < 12; i++) {
			distances.add(0);
		}
	}

	public void addDistance(int month, int pDistance) {
		distances.set(month, distances.get(month) + pDistance);
	}

	public String getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(String pSwimmer) {
		swimmer = pSwimmer;
	}

	public List<Integer> getDistances() {
		return distances;
	}

	public void setDistances(List<Integer> pDistances) {
		distances = pDistances;
	}

	public Long getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUser(Long pUser) {
		// TODO Auto-generated method stub

	}

	public int getTotal() {
		if (total == 0) {
			int lTotal = 0;
			for (Integer distance : distances) {
				lTotal += distance;
			}
			total = lTotal;
		}
		return total;
	}

	public void setTotal(int pTotal) {
		total = pTotal;
	}
}