package com.asptttoulousenatation.server.userspace.admin.entity;

import org.apache.commons.lang3.BooleanUtils;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;

public class InscriptionTransformer {

	public void update(InscriptionEntity pEntity) {
		if(BooleanUtils.isTrue(pEntity.getGroupEntity().getLicenceFfn())) {
			pEntity.setLicenceFFN("on");
			pEntity.setTypeLicence("licenceAdhesion");
		} else {
			pEntity.setLicenceFFN("off");
			pEntity.setTypeLicence("licenceLoisir");
		}
	}
}