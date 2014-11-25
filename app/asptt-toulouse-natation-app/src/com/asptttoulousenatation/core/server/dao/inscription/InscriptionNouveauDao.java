package com.asptttoulousenatation.core.server.dao.inscription;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionNouveauEntity;

public class InscriptionNouveauDao extends DaoBase<InscriptionNouveauEntity> {

	@Override
	public Class<InscriptionNouveauEntity> getEntityClass() {
		return InscriptionNouveauEntity.class;
	}

	@Override
	public String getAlias() {
		return "inscriptionNouveau";
	}
	
	@Override
	public Object getKey(InscriptionNouveauEntity pEntity) {
		return pEntity.getId();
	}

}