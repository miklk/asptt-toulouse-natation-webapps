package com.asptttoulousenatation.core.server.dao.inscription;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;

public class DossierNageurDao extends DaoBase<DossierNageurEntity> {

	@Override
	public Class<DossierNageurEntity> getEntityClass() {
		return DossierNageurEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossierNageur";
	}

	@Override
	public Object getKey(DossierNageurEntity pEntity) {
		return pEntity.getId();
	}

}