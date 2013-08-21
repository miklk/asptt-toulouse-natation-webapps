package com.asptttoulousenatation.server.userspace.admin.entity;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;

public class InscriptionTransformer {

	public void update(InscriptionEntity pEntity) {
		pEntity.setNom(pEntity.getNom().toUpperCase());
		pEntity.setPrenom(pEntity.getPrenom().toUpperCase());
		// Handle date
		String dateNaissance = pEntity.getDatenaissance();
		if (dateNaissance.length() == 10
				&& StringUtils.isNumeric(dateNaissance.substring(2, 2))) {
			String separateur = dateNaissance.substring(2, 2);
			String[] dateNaissanceSplit = dateNaissance.split(separateur);
			dateNaissance = dateNaissanceSplit[2] + dateNaissanceSplit[1]
					+ dateNaissanceSplit[0];
			pEntity.setDatenaissance(dateNaissance);
		}
		if (pEntity.getGroupEntity() != null) {
			if (BooleanUtils.isTrue(pEntity.getGroupEntity().getLicenceFfn())) {
				pEntity.setLicenceFFN("on");
				pEntity.setTypeLicence("licenceAdhesion");
			} else {
				pEntity.setLicenceFFN("off");
				pEntity.setTypeLicence("licenceLoisir");
			}
		}
	}
}