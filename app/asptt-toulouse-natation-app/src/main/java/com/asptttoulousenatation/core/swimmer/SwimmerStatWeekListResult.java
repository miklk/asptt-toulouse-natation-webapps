package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SwimmerStatWeekListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3183819300935138934L;
	private List<SwimmerStatWeekUi> nageurs;
	private Long begin;
	private Long end;
	
	public SwimmerStatWeekListResult() {
		nageurs = new ArrayList<>();
	}
	
	public void addNageur(SwimmerStatWeekUi pNageur) {
		nageurs.add(pNageur);
	}

	public List<SwimmerStatWeekUi> getNageurs() {
		return nageurs;
	}

	public void setNageurs(List<SwimmerStatWeekUi> pNageurs) {
		nageurs = pNageurs;
	}

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long pBegin) {
		begin = pBegin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long pEnd) {
		end = pEnd;
	}
}