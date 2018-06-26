package com.asptt.core.groupe.stat;

import java.io.Serializable;

public class GroupeStatUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupeTitle;
	private Integer capacite;
	private Integer disponibles;
	
	public GroupeStatUi() {
		capacite = 0;
		disponibles = 0;
	}

	public void addCapacite(Integer pCapacite) {
		capacite += pCapacite;
	}

	public void addDisponibles(Integer pDisponibles) {
		disponibles += pDisponibles;
	}

	public String getGroupeTitle() {
		return groupeTitle;
	}

	public void setGroupeTitle(String groupeTitle) {
		this.groupeTitle = groupeTitle;
	}

	public Integer getCapacite() {
		return capacite;
	}

	public void setCapacite(Integer capacite) {
		this.capacite = capacite;
	}

	public Integer getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(Integer disponibles) {
		this.disponibles = disponibles;
	}
}