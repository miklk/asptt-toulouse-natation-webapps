package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;

public class DossierStatistiques implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;
	private long complets;
	private long nonpayes;
	private long payes;
	
	public void addPaye(long paye) {
		payes+=paye;
	}
	
	public void addComplet() {
		complets++;
	}
	
	public void addNonPayes() {
		nonpayes++;
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

}