package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.shared.document.DocumentUi;

public class DocumentTransformer extends
		AbstractEntityTransformer<DocumentUi, DocumentEntity> {

	@Override
	public DocumentUi toUi(DocumentEntity pEntity) {
		DocumentUi lUi = new DocumentUi(pEntity.getId(), pEntity.getTitle(),
				pEntity.getSummary(), pEntity.getMimeType(),
				pEntity.getFileName(), pEntity.getCreationDate(),
				pEntity.getDataId());
		return lUi;
	}

}
