package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class DossierResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nageurId;
	private String email;
	private String motdepasse;
	private String nom;
	private String prenom;
	private Date naissance;
	private String groupe;
	private Set<String> creneaux;
	private String etat;
	private Date updated;
	public Long getNageurId() {
		return nageurId;
	}
	public void setNageurId(Long nageurId) {
		this.nageurId = nageurId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotdepasse() {
		return motdepasse;
	}
	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
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
	public Date getNaissance() {
		return naissance;
	}
	public void setNaissance(Date naissance) {
		this.naissance = naissance;
	}
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	public Set<String> getCreneaux() {
		return creneaux;
	}
	public void setCreneaux(Set<String> creneaux) {
		this.creneaux = creneaux;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
}
