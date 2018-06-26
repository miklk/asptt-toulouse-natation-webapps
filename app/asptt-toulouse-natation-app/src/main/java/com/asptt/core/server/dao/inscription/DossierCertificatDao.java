package com.asptt.core.server.dao.inscription;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.inscription.DossierCertificatEntity;

public class DossierCertificatDao extends DaoBase<DossierCertificatEntity> {

	@Override
	public Class<DossierCertificatEntity> getEntityClass() {
		return DossierCertificatEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossiercertificat";
	}

	@Override
	public Object getKey(DossierCertificatEntity pEntity) {
		return pEntity.getId();
	}
}