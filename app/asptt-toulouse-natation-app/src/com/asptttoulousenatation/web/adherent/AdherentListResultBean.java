package com.asptttoulousenatation.web.adherent;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AdherentListResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6186915651376131574L;
	private Long id;
	private String nom;
	private String prenom;
	private String dateNaissance;
	private String groupe;
	private Set<String> creneaux;
	private String dossier;
	private String email;
	private String motdepasse;
	
	public AdherentListResultBean() {
		
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

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String pDateNaissance) {
		dateNaissance = pDateNaissance;
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

	public String getDossier() {
		return dossier;
	}

	public void setDossier(String pDossier) {
		dossier = pDossier;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String pEmail) {
		email = pEmail;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String pMotdepasse) {
		motdepasse = pMotdepasse;
	}
	
}
