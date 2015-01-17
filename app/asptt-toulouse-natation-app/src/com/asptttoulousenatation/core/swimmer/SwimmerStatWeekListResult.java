package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatWeekUi;

public class SwimmerStatWeekListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3183819300935138934L;
	private List<SwimmerStatWeekUi> nageurs;
	
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
}