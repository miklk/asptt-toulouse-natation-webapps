package com.asptt.core.adherent;

import java.util.List;

import com.asptt.core.groupe.GroupTransformer;
import com.asptt.core.server.dao.club.group.GroupDao;
import com.asptt.core.server.dao.entity.club.group.GroupEntity;

public class InscriptionDossierTransfomer {
	
	private static InscriptionDossierTransfomer INSTANCE;

	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();

	public static InscriptionDossierTransfomer getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new InscriptionDossierTransfomer();
		}
		return INSTANCE;
	}
	
	public void updateGroupe(InscriptionDossierUi dossier) {
		if(dossier.getDossier().getGroupe() != null) {
			GroupEntity group = groupDao.get(dossier.getDossier()
					.getGroupe());
			dossier.setGroupe(groupTransformer.toUi(group));
		}
	}
	
	public void updateGroupes(List<InscriptionDossierUi> dossiers) {
		for(InscriptionDossierUi dossier: dossiers) {
			updateGroupe(dossier);
		}
	}
}
