package com.asptttoulousenatation.core.server.dao.inscription;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;

public class Inscription2Dao extends DaoBase<InscriptionEntity2> {

	@Override
	public Class<InscriptionEntity2> getEntityClass() {
		return InscriptionEntity2.class;
	}

}