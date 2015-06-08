package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.groupe.GroupUi;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

@XmlRootElement
public class InscriptionDossierUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218965418459608943L;
	private DossierNageurEntity dossier;
	private boolean choix;
	private GroupUi groupe;
	private LoadingSlotsUi creneaux;
	private boolean creneauSelected;
	private boolean supprimer;
	private String naissanceAsString;
	
	
	public InscriptionDossierUi() {
		choix = false;
	}
	
	public InscriptionDossierUi(DossierNageurEntity pAdherent) {
		dossier = pAdherent;
	}

	public DossierNageurEntity getDossier() {
		return dossier;
	}

	public void setDossier(DossierNageurEntity dossier) {
		this.dossier = dossier;
	}

	public boolean isChoix() {
		return choix;
	}

	public void setChoix(boolean pChoix) {
		choix = pChoix;
	}


	public GroupUi getGroupe() {
		return groupe;
	}

	public void setGroupe(GroupUi pGroupe) {
		groupe = pGroupe;
	}

	public LoadingSlotsUi getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(LoadingSlotsUi pCreneaux) {
		creneaux = pCreneaux;
	}

	public boolean isCreneauSelected() {
		return creneauSelected;
	}

	public void setCreneauSelected(boolean pCreneauSelected) {
		creneauSelected = pCreneauSelected;
	}

	public boolean isSupprimer() {
		return supprimer;
	}

	public void setSupprimer(boolean pSupprimer) {
		supprimer = pSupprimer;
	}

	public String getNaissanceAsString() {
		return naissanceAsString;
	}

	public void setNaissanceAsString(String naissanceAsString) {
		this.naissanceAsString = naissanceAsString;
	}
	
}