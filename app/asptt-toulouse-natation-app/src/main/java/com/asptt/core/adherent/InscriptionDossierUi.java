package com.asptt.core.adherent;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptt.core.groupe.GroupUi;
import com.asptt.core.groupe.SlotUi;
import com.asptt.core.server.dao.entity.inscription.DossierNageurEntity;

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
	//Simple mapping avec IHM
	private Integer day;
	private Integer month;
	private Integer year;
	private boolean hasPhoto;
	
	
	public InscriptionDossierUi() {
		choix = false;
	}
	
	public InscriptionDossierUi(DossierNageurEntity pAdherent) {
		dossier = pAdherent;
	}
	
	public boolean hasMultipleCreneaux() {
		int count = 0;
		if(creneaux != null && CollectionUtils.isNotEmpty(creneaux.getSlots())) {
			for(LoadingSlotUi creneau: creneaux.getSlots()) {
				count+=countCreneau(creneau.getLundi());
				count+=countCreneau(creneau.getMardi());
				count+=countCreneau(creneau.getMercredi());
				count+=countCreneau(creneau.getJeudi());
				count+=countCreneau(creneau.getVendredi());
				count+=countCreneau(creneau.getSamedi());
			}
		} else if(StringUtils.isNotBlank(dossier.getCreneaux())) {
			String[] creneauSplit = dossier.getCreneaux().split(";");
			count = creneauSplit.length;
		}
		return count > 1;
	}
	
	private int countCreneau(List<SlotUi> creneaux) {
		int count = 0;
		for(SlotUi creneau: creneaux) {
			if(creneau.isSelected()) {
				count++;
			}
		}
		return count;
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

	public boolean isHasPhoto() {
		return hasPhoto;
	}

	public void setHasPhoto(boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}
}