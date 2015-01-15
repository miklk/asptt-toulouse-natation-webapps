package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.Map;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatEnum.SwimmerStatDayTime;

public class SwimmerStatUpdateAllAction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Long, SwimmerStatUi> swimmers;
	private SwimmerStatDayTime dayTime;
	private Long day;
	private int valeur;
	
	public SwimmerStatUpdateAllAction() {
		
	}

	public Map<Long, SwimmerStatUi> getSwimmers() {
		return swimmers;
	}

	public void setSwimmers(Map<Long, SwimmerStatUi> pSwimmers) {
		swimmers = pSwimmers;
	}

	public SwimmerStatDayTime getDayTime() {
		return dayTime;
	}

	public void setDayTime(SwimmerStatDayTime pDayTime) {
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
}