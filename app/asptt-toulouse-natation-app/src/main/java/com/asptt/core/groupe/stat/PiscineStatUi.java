package com.asptt.core.groupe.stat;

import java.util.ArrayList;
import java.util.List;


public class PiscineStatUi extends StatBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private List<JourStat> jours;
	
	public PiscineStatUi() {
		super();
	}
	
	public void addJour(JourStat jour) {
		addCapacite(jour.getCapacite());
		addDisponibles(jour.getDisponibles());
		if(jours == null) {
			jours = new ArrayList<JourStat>();
		}
		jours.add(jour);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<JourStat> getJours() {
		return jours;
	}

	public void setJours(List<JourStat> jours) {
		this.jours = jours;
	}
}