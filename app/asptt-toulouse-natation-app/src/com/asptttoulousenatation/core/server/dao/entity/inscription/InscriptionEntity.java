package com.asptttoulousenatation.core.server.dao.entity.inscription;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.asptttoulousenatation.core.server.dao.entity.Entity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class InscriptionEntity implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207564448323062425L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String civilite;
	@Persistent
	private String nom;
	@Persistent
	private String prenom;
	@Persistent
	private String datenaissance;
	@Persistent
	private String lieunaissance;
	@Persistent
	private String nationalite;
	@Persistent
	private String adresse;
	@Persistent
	private String codepostal;
	@Persistent
	private String ville;
	@Persistent
	private String telephone;
	@Persistent
	private String telephonePere;
	@Persistent
	private String telephonePerePro;
	@Persistent
	private String telephoneMere;
	@Persistent
	private String telephoneMerePro;
	@Persistent
	private String email;
	@Persistent
	private String licenceFFN;
	@Persistent
	private String competition;
	@Persistent
	private String maillot;
	@Persistent
	private String tshirt;
	@Persistent
	private String shortPantalon;
	@Persistent
	private String adherent;
	@Persistent
	private String president;
	@Persistent
	private String tresorier;
	@Persistent
	private String secretaire;
	@Persistent
	private String bureau;
	@Persistent
	private String cadre;
	@Persistent
	private String officiel;
	@Persistent
	private String conseil;
	@Persistent
	private String profession;
	@Persistent
	private String professionTextPere;
	@Persistent
	private String professionTextMere;
	@Persistent
	private String accordNomPrenom;
	@Persistent
	private String mineurParent;
	@Persistent
	private String accidentNom1;
	@Persistent
	private String accidentPrenom1;
	@Persistent
	private String accidentTelephone1;
	@Persistent
	private String accidentNom2;
	@Persistent
	private String accidentPrenom2;
	@Persistent
	private String accidentTelephone2;
	@Persistent
	private String typeLicence;
	@Persistent
	private String creneaux;
	@Persistent
	private Long principal;
	@Persistent
	private Long groupe;
	@Persistent
	private Long nouveauGroupe;
	@Persistent
	private String facture;
	@Persistent
	private Boolean saisie;
	
	@NotPersistent
	private boolean supprimer;
	@NotPersistent
	private GroupEntity groupEntity;
	
	@Persistent
	private String motdepasse;
	@Persistent
	private Boolean complet;
	
	public InscriptionEntity() {
		
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
	

		public String getDatenaissance() {
		return datenaissance;
	}

	public void setDatenaissance(String pDatenaissance) {
		datenaissance = pDatenaissance;
	}

	public String getLieunaissance() {
		return lieunaissance;
	}

	public void setLieunaissance(String pLieunaissance) {
		lieunaissance = pLieunaissance;
	}

	public String getCodepostal() {
		return codepostal;
	}

	public void setCodepostal(String pCodepostal) {
		codepostal = pCodepostal;
	}

		public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String pNationalite) {
		nationalite = pNationalite;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String pAdresse) {
		adresse = pAdresse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String pVille) {
		ville = pVille;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String pTelephone) {
		telephone = pTelephone;
	}

	public String getTelephonePere() {
		return telephonePere;
	}

	public void setTelephonePere(String pTelephonePere) {
		telephonePere = pTelephonePere;
	}

	public String getTelephonePerePro() {
		return telephonePerePro;
	}

	public void setTelephonePerePro(String pTelephonePerePro) {
		telephonePerePro = pTelephonePerePro;
	}

	public String getTelephoneMere() {
		return telephoneMere;
	}

	public void setTelephoneMere(String pTelephoneMere) {
		telephoneMere = pTelephoneMere;
	}

	public String getTelephoneMerePro() {
		return telephoneMerePro;
	}

	public void setTelephoneMerePro(String pTelephoneMerePro) {
		telephoneMerePro = pTelephoneMerePro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String pEmail) {
		email = pEmail;
	}

	public String getLicenceFFN() {
		return licenceFFN;
	}

	public void setLicenceFFN(String pLicenceFFN) {
		licenceFFN = pLicenceFFN;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String pCompetition) {
		competition = pCompetition;
	}

	public String getMaillot() {
		return maillot;
	}

	public void setMaillot(String pMaillot) {
		maillot = pMaillot;
	}

	public String getTshirt() {
		return tshirt;
	}

	public void setTshirt(String pTshirt) {
		tshirt = pTshirt;
	}

	public String getShortPantalon() {
		return shortPantalon;
	}

	public void setShortPantalon(String pShortPantalon) {
		shortPantalon = pShortPantalon;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String pPresident) {
		president = pPresident;
	}

	public String getTresorier() {
		return tresorier;
	}

	public void setTresorier(String pTresorier) {
		tresorier = pTresorier;
	}

	public String getSecretaire() {
		return secretaire;
	}

	public void setSecretaire(String pSecretaire) {
		secretaire = pSecretaire;
	}

	public String getBureau() {
		return bureau;
	}

	public void setBureau(String pBureau) {
		bureau = pBureau;
	}

	public String getCadre() {
		return cadre;
	}

	public void setCadre(String pCadre) {
		cadre = pCadre;
	}

	public String getOfficiel() {
		return officiel;
	}

	public void setOfficiel(String pOfficiel) {
		officiel = pOfficiel;
	}

	public String getConseil() {
		return conseil;
	}

	public void setConseil(String pConseil) {
		conseil = pConseil;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String pProfession) {
		profession = pProfession;
	}

	public String getAccordNomPrenom() {
		return accordNomPrenom;
	}

	public void setAccordNomPrenom(String pAccordNomPrenom) {
		accordNomPrenom = pAccordNomPrenom;
	}

	public String getMineurParent() {
		return mineurParent;
	}

	public void setMineurParent(String pMineurParent) {
		mineurParent = pMineurParent;
	}

	public String getAccidentNom1() {
		return accidentNom1;
	}

	public void setAccidentNom1(String pAccidentNom1) {
		accidentNom1 = pAccidentNom1;
	}

	public String getAccidentPrenom1() {
		return accidentPrenom1;
	}

	public void setAccidentPrenom1(String pAccidentPrenom1) {
		accidentPrenom1 = pAccidentPrenom1;
	}

	public String getAccidentTelephone1() {
		return accidentTelephone1;
	}

	public void setAccidentTelephone1(String pAccidentTelephone1) {
		accidentTelephone1 = pAccidentTelephone1;
	}

	public String getAccidentNom2() {
		return accidentNom2;
	}

	public void setAccidentNom2(String pAccidentNom2) {
		accidentNom2 = pAccidentNom2;
	}

	public String getAccidentPrenom2() {
		return accidentPrenom2;
	}

	public void setAccidentPrenom2(String pAccidentPrenom2) {
		accidentPrenom2 = pAccidentPrenom2;
	}

	public String getAccidentTelephone2() {
		return accidentTelephone2;
	}

	public void setAccidentTelephone2(String pAccidentTelephone2) {
		accidentTelephone2 = pAccidentTelephone2;
	}

	public String getTypeLicence() {
		return typeLicence;
	}

	public void setTypeLicence(String pTypeLicence) {
		typeLicence = pTypeLicence;
	}

	public String getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(String pCreneaux) {
		creneaux = pCreneaux;
	}

	public Long getPrincipal() {
		return principal;
	}

	public void setPrincipal(Long pPrincipal) {
		principal = pPrincipal;
	}

	public String getAdherent() {
		return adherent;
	}

	public void setAdherent(String pAdherent) {
		adherent = pAdherent;
	}

	public String getProfessionTextPere() {
		return professionTextPere;
	}

	public void setProfessionTextPere(String pProfessionTextPere) {
		professionTextPere = pProfessionTextPere;
	}

	public String getProfessionTextMere() {
		return professionTextMere;
	}

	public void setProfessionTextMere(String pProfessionTextMere) {
		professionTextMere = pProfessionTextMere;
	}

	public Long getGroupe() {
		return groupe;
	}

	public void setGroupe(Long pGroupe) {
		groupe = pGroupe;
	}

	public Long getNouveauGroupe() {
		return nouveauGroupe;
	}

	public void setNouveauGroupe(Long pNouveauGroupe) {
		nouveauGroupe = pNouveauGroupe;
	}

	public boolean isSupprimer() {
		return supprimer;
	}

	public void setSupprimer(boolean pSupprimer) {
		supprimer = pSupprimer;
	}

	public GroupEntity getGroupEntity() {
		return groupEntity;
	}

	public void setGroupEntity(GroupEntity pGroupEntity) {
		groupEntity = pGroupEntity;
	}

	public String getFacture() {
		return facture;
	}

	public void setFacture(String facture) {
		this.facture = facture;
	}

	public Boolean getSaisie() {
		return saisie;
	}

	public void setSaisie(Boolean pSaisie) {
		saisie = pSaisie;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String pMotdepasse) {
		motdepasse = pMotdepasse;
	}

	public Boolean getComplet() {
		return complet;
	}

	public void setComplet(Boolean pComplet) {
		complet = pComplet;
	}
	
}