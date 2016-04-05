package com.asptttoulousenatation.core.swimmer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;

public class SwimmerStatPeriodeUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4222671616427336055L;
	private String nom;
	private String prenom;
	private List<SwimmerStatEntity> stats;
	private List<SwimmerStatEntity> musculations;

	public SwimmerStatPeriodeUi() {
		stats = new ArrayList<>();
		musculations = new ArrayList<>();
	}
	
	public void addMusculation(SwimmerStatEntity stat) {
		musculations.add(stat);
	}
	
	public void addStat(SwimmerStatEntity stat) {
		stats.add(stat);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<SwimmerStatEntity> getStats() {
		return stats;
	}

	public void setStats(List<SwimmerStatEntity> stats) {
		this.stats = stats;
	}

	public List<SwimmerStatEntity> getMusculations() {
		return musculations;
	}

	public void setMusculations(List<SwimmerStatEntity> musculations) {
		this.musculations = musculations;
	}
	
}