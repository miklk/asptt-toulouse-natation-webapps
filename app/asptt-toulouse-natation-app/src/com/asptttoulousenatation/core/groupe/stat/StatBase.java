package com.asptttoulousenatation.core.groupe.stat;

import java.io.Serializable;

public class StatBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statTitle;
	private Integer capacite;
	private Integer disponibles;
	
	public StatBase() {
		capacite = 0;
		disponibles = 0;
	}
	
	public void addCapacite(Integer pCapacite) {
		capacite += pCapacite;
	}

	public void addDisponibles(Integer pDisponibles) {
		disponibles += pDisponibles;
	}
	
	public String getStatTitle() {
		return statTitle;
	}
	public void setStatTitle(String statTitle) {
		this.statTitle = statTitle;
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
