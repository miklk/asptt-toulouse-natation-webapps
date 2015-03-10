package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;

import com.asptttoulousenatation.core.piscine.PiscineListResultBean;

public class GetAdherentAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1071839151019001606L;
	private String search;
	private Long groupe;
	private Long creneau;
	private PiscineListResultBean piscine;
	
	public GetAdherentAction() {
		
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String pSearch) {
		search = pSearch;
	}

	public Long getGroupe() {
		return groupe;
	}

	public void setGroupe(Long pGroupe) {
		groupe = pGroupe;
	}

	public Long getCreneau() {
		return creneau;
	}

	public void setCreneau(Long pCreneau) {
		creneau = pCreneau;
	}

	public PiscineListResultBean getPiscine() {
		return piscine;
	}

	public void setPiscine(PiscineListResultBean pPiscine) {
		piscine = pPiscine;
	}
}