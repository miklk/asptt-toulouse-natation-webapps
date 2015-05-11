package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;

@XmlRootElement
public class InscriptionDossierUi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218965418459608943L;
	private InscriptionEntity2 dossier;
	private boolean choix;
	private GroupUi groupe;
	private LoadingSlotsUi creneaux;
	private boolean creneauSelected;
	private boolean supprimer;
	
	
	public InscriptionDossierUi() {
		choix = false;
	}
	
	public InscriptionDossierUi(InscriptionEntity2 pAdherent) {
		dossier = pAdherent;
	}

	public InscriptionEntity2 getDossier() {
		return dossier;
	}

	public void setDossier(InscriptionEntity2 pDossier) {
		dossier = pDossier;
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
	
}