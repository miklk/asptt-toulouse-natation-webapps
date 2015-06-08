package com.asptttoulousenatation.core.adherent;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import com.asptttoulousenatation.core.groupe.GroupUi;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;

public class AdherentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218965418459608943L;
	private InscriptionEntity2 dossier;
	private GroupUi groupe;
	private Set<SlotUi> creneaux;
	
	
	public AdherentBean() {
		creneaux = Collections.emptySet();
	}
	
	public AdherentBean(InscriptionEntity2 pAdherent) {
		this();
		dossier = pAdherent;
	}

	public InscriptionEntity2 getDossier() {
		return dossier;
	}

	public void setDossier(InscriptionEntity2 pDossier) {
		dossier = pDossier;
	}

	public GroupUi getGroupe() {
		return groupe;
	}

	public void setGroupe(GroupUi pGroupe) {
		groupe = pGroupe;
	}

	public Set<SlotUi> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(Set<SlotUi> pCreneaux) {
		creneaux = pCreneaux;
	}
	
}