package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.lang.StatistiqueBase;

public class DossierStatistiques implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;
	private long complets;
	private long nonpayes;
	private long payes;
	private long nageurs;
	private long potentiel;
	private List<StatistiqueBase> dossiers;
	private List<StatistiqueBase> nageursDetail;
	
	public DossierStatistiques() {
		dossiers = new ArrayList<>(3);
		nageursDetail = new ArrayList<>(2);
	}
	
	public void addPaye(long paye) {
		payes+=paye;
	}
	
	public void addComplet() {
		complets++;
	}
	
	public void addNonPayes() {
		nonpayes++;
	}
	
	public void addDossier(StatistiqueBase stat) {
		dossiers.add(stat);
	}
	
	public void addNageur(StatistiqueBase stat) {
		nageursDetail.add(stat);
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getComplets() {
		return complets;
	}

	public void setComplets(long complets) {
		this.complets = complets;
	}

	public long getNonpayes() {
		return nonpayes;
	}

	public void setNonpayes(long nonpayes) {
		this.nonpayes = nonpayes;
	}

	public long getPayes() {
		return payes;
	}

	public void setPayes(long payes) {
		this.payes = payes;
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
	
}