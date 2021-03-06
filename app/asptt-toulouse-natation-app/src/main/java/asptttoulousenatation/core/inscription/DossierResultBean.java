package com.asptttoulousenatation.core.inscription;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

public class DossierResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nageurId;
	private Long dossierId;
	private String email;
	private String motdepasse;
	private String nom;
	private String prenom;
	private Date naissance;
	private String groupe;
	private Set<String> creneaux;
	private String etat;
	private Date updated;
	private String comment;
	private Integer montant;
	
	private DossierNageurEntity nageur;
	
	private boolean selected = false;
	
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
	public Long getDossierId() {
		return dossierId;
	}
	public void setDossierId(Long dossierId) {
		this.dossierId = dossierId;
	}
	public DossierNageurEntity getNageur() {
		return nageur;
	}
	public void setNageur(DossierNageurEntity nageur) {
		this.nageur = nageur;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Integer getMontant() {
		return montant;
	}
	public void setMontant(Integer montant) {
		this.montant = montant;
	}
	
}