package com.asptt.core.inscription;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FindDossierParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String texteLibre;
	private Long groupe;
	private String complet;
	private String certificat;
	private String paiement;
	public String getTexteLibre() {
		return texteLibre;
	}
	public void setTexteLibre(String texteLibre) {
		this.texteLibre = texteLibre;
	}
	public Long getGroupe() {
		return groupe;
	}
	public void setGroupe(Long groupe) {
		this.groupe = groupe;
	}
	public String getComplet() {
		return complet;
	}
	public void setComplet(String complet) {
		this.complet = complet;
	}
	public String getCertificat() {
		return certificat;
	}
	public void setCertificat(String certificat) {
		this.certificat = certificat;
	}
	public String getPaiement() {
		return paiement;
	}
	public void setPaiement(String paiement) {
		this.paiement = paiement;
	}
}