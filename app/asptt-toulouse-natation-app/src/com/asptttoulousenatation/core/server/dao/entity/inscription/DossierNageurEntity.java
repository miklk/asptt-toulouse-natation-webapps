package com.asptttoulousenatation.core.server.dao.entity.inscription;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.IEntity;

@Entity
@XmlRootElement
public class DossierNageurEntity implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207564448323062425L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String civilite;
	
	private String nom;
	
	private String prenom;
	
	private Date naissance;
	
	private String licenceFFN;
	
	private String competition;
	
	private String maillot;
	
	private String tshirt;
	
	private String shortPantalon;
	
	private String profession;
	
	private String typeLicence;
	
	private String creneaux;
	
	private Long dossier;
	
	private Long groupe;
	
	private Boolean nouveau;
	
	private String statut;
	
	private Date created;
	private Date updated;
	
	private Boolean certificat;
	
	private Integer tarif;
	
	private Integer montantreel;
	
	private String csp;
	
	public DossierNageurEntity() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String pCivilite) {
		civilite = pCivilite;
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

	public Date getNaissance() {
		return naissance;
	}

	public void setNaissance(Date naissance) {
		this.naissance = naissance;
	}

	public String getLicenceFFN() {
		return licenceFFN;
	}

	public void setLicenceFFN(String licenceFFN) {
		this.licenceFFN = licenceFFN;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}

	public String getMaillot() {
		return maillot;
	}

	public void setMaillot(String maillot) {
		this.maillot = maillot;
	}

	public String getTshirt() {
		return tshirt;
	}

	public void setTshirt(String tshirt) {
		this.tshirt = tshirt;
	}

	public String getShortPantalon() {
		return shortPantalon;
	}

	public void setShortPantalon(String shortPantalon) {
		this.shortPantalon = shortPantalon;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getTypeLicence() {
		return typeLicence;
	}

	public void setTypeLicence(String typeLicence) {
		this.typeLicence = typeLicence;
	}

	public String getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(String creneaux) {
		this.creneaux = creneaux;
	}

	public Long getDossier() {
		return dossier;
	}

	public void setDossier(Long dossier) {
		this.dossier = dossier;
	}

	public Long getGroupe() {
		return groupe;
	}

	public void setGroupe(Long groupe) {
		this.groupe = groupe;
	}

	public Boolean getNouveau() {
		return nouveau;
	}

	public void setNouveau(Boolean nouveau) {
		this.nouveau = nouveau;
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

	public Boolean getCertificat() {
		return certificat;
	}

	public void setCertificat(Boolean certificat) {
		this.certificat = certificat;
	}

	public Integer getTarif() {
		return tarif;
	}

	public void setTarif(Integer tarif) {
		this.tarif = tarif;
	}

	public String getCsp() {
		return csp;
	}

	public void setCsp(String csp) {
		this.csp = csp;
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

	@PrePersist
	@PreUpdate
	public void toUpperCase() {
		civilite = StringUtils.upperCase(civilite);
		nom = StringUtils.upperCase(nom);
		prenom = StringUtils.upperCase(prenom);
		profession = StringUtils.upperCase(profession);
		updated = new Date();
	}
	
	@PrePersist
	public void onPersist() {
		created = new Date();
	}
}