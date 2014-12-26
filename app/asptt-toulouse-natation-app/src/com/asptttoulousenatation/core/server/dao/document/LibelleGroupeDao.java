package com.asptttoulousenatation.core.server.dao.document;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentLibelleJEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.LibelleGroupeEntity;

public class LibelleGroupeDao extends DaoBase<LibelleGroupeEntity> {

	@Override
	public Class<LibelleGroupeEntity> getEntityClass() {
		return LibelleGroupeEntity.class;
	}

	@Override
	public String getAlias() {
		return "libellegroupe";
	}
	
	@Override
	public Object getKey(LibelleGroupeEntity pEntity) {
		return pEntity.getId();
	}
}