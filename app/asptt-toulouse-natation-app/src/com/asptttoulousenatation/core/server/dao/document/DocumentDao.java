package com.asptttoulousenatation.core.server.dao.document;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;

public class DocumentDao extends DaoBase<DocumentEntity> {

	@Override
	public Class<DocumentEntity> getEntityClass() {
		return DocumentEntity.class;
	}

	@Override
	public String getAlias() {
		return "document";
	}
	
	@Override
	public Object getKey(DocumentEntity pEntity) {
		return pEntity.getId();
	}
}