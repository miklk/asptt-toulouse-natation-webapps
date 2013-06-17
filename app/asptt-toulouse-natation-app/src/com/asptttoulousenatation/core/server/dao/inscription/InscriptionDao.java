package com.asptttoulousenatation.core.server.dao.inscription;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;

public class InscriptionDao extends DaoBase<InscriptionEntity> {

	@Override
	public Class<InscriptionEntity> getEntityClass() {
		return InscriptionEntity.class;
	}

}