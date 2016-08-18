package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.lang.StatistiqueBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;

public class DossierStatistiques implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long nageurs;
	private long potentiel;
	private long places;
	private List<StatistiqueBase> dossiers;
	private List<StatistiqueBase> nageursDetail;
	private Map<DossierStatutEnum, Integer> counts;
	
	public DossierStatistiques() {
		dossiers = new ArrayList<>(3);
		nageursDetail = new ArrayList<>(2);
	}
	
	public void addDossier(StatistiqueBase stat) {
		dossiers.add(stat);
	}
	
	public void addNageur(StatistiqueBase stat) {
		nageursDetail.add(stat);
	}

	public List<StatistiqueBase> getDossiers() {
		return dossiers;
	}

	public void setDossiers(List<StatistiqueBase> dossiers) {
		this.dossiers = dossiers;
	}

	public long getNageurs() {
		return nageurs;
	}

	public void setNageurs(long nageurs) {
		this.nageurs = nageurs;
	}

	public List<StatistiqueBase> getNageursDetail() {
		return nageursDetail;
	}

	public void setNageursDetail(List<StatistiqueBase> nageursDetail) {
		this.nageursDetail = nageursDetail;
	}

	public long getPotentiel() {
		return potentiel;
	}

	public void setPotentiel(long potentiel) {
		this.potentiel = potentiel;
	}

	public long getPlaces() {
		return places;
	}

	public void setPlaces(long places) {
		this.places = places;
	}

	public Map<DossierStatutEnum, Integer> getCounts() {
		return counts;
	}

	public void setCounts(Map<DossierStatutEnum, Integer> counts) {
		this.counts = counts;
	}
	
}