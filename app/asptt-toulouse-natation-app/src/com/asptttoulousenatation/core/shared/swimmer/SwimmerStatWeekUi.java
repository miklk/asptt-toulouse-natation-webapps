package com.asptttoulousenatation.core.shared.swimmer;

import java.util.ArrayList;
import java.util.List;

public class SwimmerStatWeekUi implements ISwimmerStatUi {

	private String swimmer;
	private int total;
	private List<Integer[]> distances;

	public SwimmerStatWeekUi() {
		distances = new ArrayList<Integer[]>(7);
		for (int i = 0; i < 6; i++) {
			distances.add(new Integer[] { 0, 0, 0 });
		}
	}

	public void addDistance(int day, int index, int pDistance) {
		distances.get(day)[index] = pDistance;
	}

	public String getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(String pSwimmer) {
		swimmer = pSwimmer;
	}

	public List<Integer[]> getDistances() {
		return distances;
	}

	public void setDistances(List<Integer[]> pDistances) {
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
			for (Integer[] distance : distances) {
				lTotal += distance[0] + distance[1] + distance[2];
			}
			total = lTotal;
		}
		return total;
	}

	public void setTotal(int pTotal) {
		total = pTotal;
	}
}