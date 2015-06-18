package com.asptttoulousenatation.core.server.dao.entity.inscription;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class DossierEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207564448323062425L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String adresse;
	
	private String codepostal;
	
	private String ville;
	
	private String telephone;
	
	private String telephoneSecondaire;
	
	private String email;
	
	private String emailsecondaire;
	
	private String accordNomPrenom;
	
	private String accidentNom1;
	
	private String accidentPrenom1;
	
	private String accidentTelephone1;
	
	private String accidentNom2;
	
	private String accidentPrenom2;
	
	private String accidentTelephone2;
	
	private String facture;
	
	private String motdepasse;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
	
	private String parent1Nom;
	private String parent1Prenom;
	private String parent2Nom;
	private String parent2Prenom;
	private String parent1Csp;
	private String parent1Profession;
	private String parent2Csp;
	private String parent2Profession;
	private Boolean reminder;
	private Date reminded;
	private String comment;
	private String statut;
	private Integer montantreel;
	
	public DossierEntity() {
		reminder = false;
		statut = DossierStatutEnum.INITIALISE.name();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephoneSecondaire() {
		return telephoneSecondaire;
	}

	public void setTelephoneSecondaire(String telephoneSecondaire) {
		this.telephoneSecondaire = telephoneSecondaire;
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

	public String getAccordNomPrenom() {
		return accordNomPrenom;
	}

	public void setAccordNomPrenom(String accordNomPrenom) {
		this.accordNomPrenom = accordNomPrenom;
	}

	public String getAccidentNom1() {
		return accidentNom1;
	}

	public void setAccidentNom1(String accidentNom1) {
		this.accidentNom1 = accidentNom1;
	}

	public String getAccidentPrenom1() {
		return accidentPrenom1;
	}

	public void setAccidentPrenom1(String accidentPrenom1) {
		this.accidentPrenom1 = accidentPrenom1;
	}

	public String getAccidentTelephone1() {
		return accidentTelephone1;
	}

	public void setAccidentTelephone1(String accidentTelephone1) {
		this.accidentTelephone1 = accidentTelephone1;
	}

	public String getAccidentNom2() {
		return accidentNom2;
	}

	public void setAccidentNom2(String accidentNom2) {
		this.accidentNom2 = accidentNom2;
	}

	public String getAccidentPrenom2() {
		return accidentPrenom2;
	}

	public void setAccidentPrenom2(String accidentPrenom2) {
		this.accidentPrenom2 = accidentPrenom2;
	}

	public String getAccidentTelephone2() {
		return accidentTelephone2;
	}

	public void setAccidentTelephone2(String accidentTelephone2) {
		this.accidentTelephone2 = accidentTelephone2;
	}

	public String getFacture() {
		return facture;
	}

	public void setFacture(String facture) {
		this.facture = facture;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public String getParent1Csp() {
		return parent1Csp;
	}

	public void setParent1Csp(String parent1Csp) {
		this.parent1Csp = parent1Csp;
	}

	public String getParent1Profession() {
		return parent1Profession;
	}

	public void setParent1Profession(String parent1Profession) {
		this.parent1Profession = parent1Profession;
	}

	public String getParent2Csp() {
		return parent2Csp;
	}

	public void setParent2Csp(String parent2Csp) {
		this.parent2Csp = parent2Csp;
	}

	public String getParent2Profession() {
		return parent2Profession;
	}

	public void setParent2Profession(String parent2Profession) {
		this.parent2Profession = parent2Profession;
	}

	
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	

	public Boolean getReminder() {
		return reminder;
	}

	public void setReminder(Boolean reminder) {
		this.reminder = reminder;
	}

	public Date getReminded() {
		return reminded;
	}

	public void setReminded(Date reminded) {
		this.reminded = reminded;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	

	public Integer getMontantreel() {
		return montantreel;
	}

	public void setMontantreel(Integer montantreel) {
		this.montantreel = montantreel;
	}

	@PreUpdate
	public void onUpdate() {
		toUpperCase();
		updated = new Date();
	}
	
	private void toUpperCase() {
		accidentNom1 = StringUtils.upperCase(accidentNom1);
		accidentNom2 = StringUtils.upperCase(accidentNom2);
		accidentPrenom1 = StringUtils.upperCase(accidentPrenom1);
		accidentPrenom2 = StringUtils.upperCase(accidentPrenom2);
		accidentTelephone1 = StringUtils.upperCase(accidentTelephone1);
		accidentTelephone2 = StringUtils.upperCase(accidentTelephone2);
		adresse = StringUtils.upperCase(adresse);
		codepostal = StringUtils.upperCase(codepostal);
		ville = StringUtils.upperCase(ville);
		telephone = StringUtils.upperCase(telephone);
		telephoneSecondaire = StringUtils.upperCase(telephoneSecondaire);
		emailsecondaire = StringUtils.upperCase(emailsecondaire);
		parent1Profession = StringUtils.upperCase(parent1Profession);
		parent2Profession = StringUtils.upperCase(parent2Profession);
		accordNomPrenom = StringUtils.upperCase(accordNomPrenom);
		parent1Nom = StringUtils.upperCase(parent1Nom);
		parent1Prenom = StringUtils.upperCase(parent1Prenom);
		parent2Nom = StringUtils.upperCase(parent2Nom);
		parent2Prenom = StringUtils.upperCase(parent2Prenom);
	}
	
	@PrePersist
	public void onPersist() {
		created = new Date();
		updated = new Date();
		toUpperCase();
	}
}