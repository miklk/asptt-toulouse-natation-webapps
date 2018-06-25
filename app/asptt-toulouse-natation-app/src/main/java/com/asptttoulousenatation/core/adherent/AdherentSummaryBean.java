package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class AdherentSummaryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2391263861420779457L;
	
	private Long id;
	private String nom;
	private String prenom;
	private String dateDeNaissance;
	private String groupe;
	private Set<String> creneaux;
	
	public AdherentSummaryBean() {
		id = 0l;
		nom = StringUtils.EMPTY;
		prenom = StringUtils.EMPTY;
		dateDeNaissance = StringUtils.EMPTY;
		groupe = StringUtils.EMPTY;
		creneaux = Collections.emptySet();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String pNom) {
		nom = pNom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String pPrenom) {
		prenom = pPrenom;
	}

	public String getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(String pDateDeNaissance) {
		dateDeNaissance = pDateDeNaissance;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String pGroupe) {
		groupe = pGroupe;
	}

	public Set<String> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(Set<String> pCreneaux) {
		creneaux = pCreneaux;
	}

}