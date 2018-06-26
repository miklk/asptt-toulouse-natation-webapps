package com.asptt.core.piscine;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class PiscineListResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3963669782268805607L;
	private String creneaux;
	private String intitule;
	
	public PiscineListResultBean() {
	}
	
	public void addCreneau(Long creneau) {
		if(StringUtils.isNotBlank(creneaux)) {
			creneaux = creneaux + ";" + creneau;
		} else {
			creneaux = creneau.toString();
		}
	}

	public String getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(String pCreneaux) {
		creneaux = pCreneaux;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String pIntitule) {
		intitule = pIntitule;
	}	
}