package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentUI;

public class ContentTransformer extends
		AbstractEntityTransformer<ContentUI, ContentEntity> {

	@Override
	public ContentUI toUi(ContentEntity pEntity) {
		ContentUI lUi = new ContentUI(pEntity.getId(),
				pEntity.getSummary(), pEntity.getData().getBytes(),
				ContentDataKindEnum.valueOf(pEntity.getKind()));
		return lUi;
	}
}