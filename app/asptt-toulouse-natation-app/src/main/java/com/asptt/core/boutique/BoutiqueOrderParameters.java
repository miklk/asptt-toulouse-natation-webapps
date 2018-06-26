package com.asptt.core.boutique;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BoutiqueOrderParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long dossier;
	private String nom;
	private String prenom;
	private String email;
	private List<ProductUi> panier;
	public Long getDossier() {
		return dossier;
	}
	public void setDossier(Long dossier) {
		this.dossier = dossier;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<ProductUi> getPanier() {
		return panier;
	}
	public void setPanier(List<ProductUi> panier) {
		this.panier = panier;
	}
}
