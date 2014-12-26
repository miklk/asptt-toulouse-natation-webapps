package com.asptttoulousenatation.core.server.dao.document;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentLibelleJEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.LibelleGroupeJEntity;

public class LibelleGroupeJDao extends DaoBase<LibelleGroupeJEntity> {

	@Override
	public Class<LibelleGroupeJEntity> getEntityClass() {
		return LibelleGroupeJEntity.class;
	}

	@Override
	public String getAlias() {
		return "libellegroupej";
	}
	
	@Override
	public Object getKey(LibelleGroupeJEntity pEntity) {
		return pEntity.getId();
	}
}