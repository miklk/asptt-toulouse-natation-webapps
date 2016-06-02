package com.asptttoulousenatation.core.server.dao.entity.inscription.stage;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class DossierStageEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer session;
	private String nom;
	private String prenom;
	private String civilite;
	private Integer day;
	private Integer month;
	private Integer year;
	private Boolean adherent;
	private String parent1Nom;
	private String parent1Prenom;
	private String parent2Nom;
	private String parent2Prenom;
	private String adresse;
	private String codepostal;
	private String ville;
	private String email;
	private String emailsecondaire;
	private Date created;
	private Date updated;
	
	@PreUpdate
	public void onUpdate() {
		updated = new Date();
	}
	
	@PrePersist
	public void onPersist() {
		created = new Date();
		updated = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSession() {
		return session;
	}
	public void setSession(Integer session) {
		this.session = session;
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
	public String getCivilite() {
		return civilite;
	}
	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Boolean getAdherent() {
		return adherent;
	}
	public void setAdherent(Boolean adherent) {
		this.adherent = adherent;
	}
	public String getParent1Nom() {
		return parent1Nom;
	}
	public void setParent1Nom(String parent1Nom) {
		this.parent1Nom = parent1Nom;
	}
	public String getParent1Prenom() {
		return parent1Prenom;
	}
	public void setParent1Prenom(String parent1Prenom) {
		this.parent1Prenom = parent1Prenom;
	}
	public String getParent2Nom() {
		return parent2Nom;
	}
	public void setParent2Nom(String parent2Nom) {
		this.parent2Nom = parent2Nom;
	}
	public String getParent2Prenom() {
		return parent2Prenom;
	}
	public void setParent2Prenom(String parent2Prenom) {
		this.parent2Prenom = parent2Prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCodepostal() {
		return codepostal;
	}
	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailsecondaire() {
		return emailsecondaire;
	}
	public void setEmailsecondaire(String emailsecondaire) {
		this.emailsecondaire = emailsecondaire;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
}
