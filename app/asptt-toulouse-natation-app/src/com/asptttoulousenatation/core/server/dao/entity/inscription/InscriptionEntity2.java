package com.asptttoulousenatation.core.server.dao.entity.inscription;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.Entity;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@XmlRootElement
public class InscriptionEntity2 implements Entity {

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
	private String emailsecondaire;
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
	private String mineur;
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
	
	@Persistent
	private String motdepasse;
	@Persistent
	private Boolean complet;
	@Persistent
	private Boolean nouveau;
	@Persistent
	private Date inscriptiondt;
	
	@Persistent
	private Boolean certificat;
	@Persistent
	private Boolean paiement;
	
	@Persistent
	private Integer tarif;
	
	public InscriptionEntity2() {
		
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

	public String getCodepostal() {
		return codepostal;
	}

	public void setCodepostal(String pCodepostal) {
		codepostal = pCodepostal;
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

	public String getEmailsecondaire() {
		return emailsecondaire;
	}

	public void setEmailsecondaire(String pEmailsecondaire) {
		emailsecondaire = pEmailsecondaire;
	}

	public Boolean getNouveau() {
		return nouveau;
	}

	public void setNouveau(Boolean pNouveau) {
		nouveau = pNouveau;
	}
	
	
	public String getMineur() {
		return mineur;
	}

	public void setMineur(String pMineur) {
		mineur = pMineur;
	}

	public Date getInscriptiondt() {
		return inscriptiondt;
	}

	public void setInscriptiondt(Date pInscriptiondt) {
		inscriptiondt = pInscriptiondt;
	}

	public Boolean getCertificat() {
		return certificat;
	}

	public void setCertificat(Boolean pCertificat) {
		certificat = pCertificat;
	}

	public Boolean getPaiement() {
		return paiement;
	}

	public void setPaiement(Boolean pPaiement) {
		paiement = pPaiement;
	}
	
	public Integer getTarif() {
		return tarif;
	}

	public void setTarif(Integer pTarif) {
		tarif = pTarif;
	}

	public void copy(InscriptionEntity2 pEntity) {
		pEntity.setAccidentNom1(accidentNom1);
		pEntity.setAccidentNom2(accidentNom2);
		pEntity.setAccidentPrenom1(accidentPrenom1);
		pEntity.setAccidentPrenom2(accidentPrenom2);
		pEntity.setAccidentTelephone1(accidentTelephone1);
		pEntity.setAccidentTelephone2(accidentTelephone2);
		pEntity.setAccordNomPrenom(accordNomPrenom);
		pEntity.setAdresse(adresse);
		pEntity.setCodepostal(codepostal);
		pEntity.setEmail(email);
		pEntity.setEmailsecondaire(emailsecondaire);
		pEntity.setFacture(facture);
		pEntity.setProfession(profession);
		pEntity.setProfessionTextMere(professionTextMere);
		pEntity.setProfessionTextPere(professionTextPere);
		pEntity.setTelephone(telephone);
		pEntity.setTelephoneMere(telephoneMere);
		pEntity.setTelephoneMerePro(telephoneMerePro);
		pEntity.setTelephonePere(telephonePere);
		pEntity.setTelephonePerePro(telephonePerePro);
		pEntity.setVille(ville);
	}
	
}