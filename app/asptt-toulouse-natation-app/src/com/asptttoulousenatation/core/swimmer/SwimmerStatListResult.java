package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;

public class SwimmerStatListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Long, SwimmerStatUi> swimmers;
	
	public SwimmerStatListResult() {
		swimmers = new HashMap<>();
	}
	
	public void addStat(Long swimmer, SwimmerStatUi stat) {
			swimmers.put(swimmer, stat);
	}

	public Map<Long, SwimmerStatUi> getSwimmers() {
		return swimmers;
	}

	public void setSwimmers(Map<Long, SwimmerStatUi> pSwimmers) {
		swimmers = pSwimmers;
	}

}
