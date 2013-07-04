package com.asptttoulousenatation.core.shared.swimmer;

import java.util.ArrayList;
import java.util.List;

public class SwimmerStatMonthUi implements ISwimmerStatUi {

	private String swimmer;
	private int total;
	private List<Integer> distances;

	public SwimmerStatMonthUi() {
		distances = new ArrayList<Integer>(5);
		for (int i = 0; i < 5; i++) {
			distances.add(0);
		}
	}

	public void addDistance(int week, int pDistance) {
		distances.set(week, distances.get(week) + pDistance);
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