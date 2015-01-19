package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;

public class SwimmerStatListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SwimmerStatUi> swimmers;
	
	public SwimmerStatListResult() {
		swimmers = new ArrayList<>();
	}
	
	public void addStat(SwimmerStatUi stat) {
			swimmers.add(stat);
	}

	public List<SwimmerStatUi> getSwimmers() {
		return swimmers;
	}

	public void setSwimmers(List<SwimmerStatUi> pSwimmers) {
		swimmers = pSwimmers;
	}
}