package com.asptttoulousenatation.core.groupe.stat;

import java.io.Serializable;

public class PiscineStatUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String piscineTitle;
	private Integer capacite;
	private Integer disponibles;
	
	public PiscineStatUi() {
		capacite = 0;
		disponibles = 0;
	}

	public void addCapacite(Integer pCapacite) {
		capacite += pCapacite;
	}

	public void addDisponibles(Integer pDisponibles) {
		disponibles += pDisponibles;
	}

	public String getPiscineTitle() {
		return piscineTitle;
	}

	public void setPiscineTitle(String piscineTitle) {
		this.piscineTitle = piscineTitle;
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