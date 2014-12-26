package com.asptttoulousenatation.core.server.dao.document;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentLibelleJEntity;

public class DocumentLibelleJDao extends DaoBase<DocumentLibelleJEntity> {

	@Override
	public Class<DocumentLibelleJEntity> getEntityClass() {
		return DocumentLibelleJEntity.class;
	}

	@Override
	public String getAlias() {
		return "documentlibelle";
	}
	
	@Override
	public Object getKey(DocumentLibelleJEntity pEntity) {
		return pEntity.getId();
	}
}