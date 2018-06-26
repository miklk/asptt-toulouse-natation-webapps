package com.asptt.core.swimmer;

import java.io.Serializable;
import java.util.Map;

public class SwimmerStatUpdateAllAction implements Serializable {
	
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Long, SwimmerStatUi> swimmers;
	private DayTimeEnum dayTime;
	private Long day;
	private int valeur;
	private boolean presence;
	
	public SwimmerStatUpdateAllAction() {
		
	}

	public Map<Long, SwimmerStatUi> getSwimmers() {
		return swimmers;
	}

	public void setSwimmers(Map<Long, SwimmerStatUi> pSwimmers) {
		swimmers = pSwimmers;
	}

	public DayTimeEnum getDayTime() {
		return dayTime;
	}

	public void setDayTime(DayTimeEnum pDayTime) {
		dayTime = pDayTime;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long pDay) {
		day = pDay;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int pValeur) {
		valeur = pValeur;
	}

	public boolean isPresence() {
		return presence;
	}

	public void setPresence(boolean pPresence) {
		presence = pPresence;
	}
	
}