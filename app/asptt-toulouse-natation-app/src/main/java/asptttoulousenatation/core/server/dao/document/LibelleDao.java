package com.asptttoulousenatation.core.server.dao.document;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.document.LibelleEntity;

public class LibelleDao extends DaoBase<LibelleEntity> {

	@Override
	public Class<LibelleEntity> getEntityClass() {
		return LibelleEntity.class;
	}

	@Override
	public String getAlias() {
		return "libelle";
	}
	
	@Override
	public Object getKey(LibelleEntity pEntity) {
		return pEntity.getId();
	}
}