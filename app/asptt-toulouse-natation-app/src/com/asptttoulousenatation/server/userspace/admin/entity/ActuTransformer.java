package com.asptttoulousenatation.server.userspace.admin.entity;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.shared.actu.ActuUi;

public class ActuTransformer extends
		AbstractEntityTransformer<ActuUi, ActuEntity> {

	@Override
	public ActuUi toUi(ActuEntity pEntity) {
		ActuUi lUi = new ActuUi(pEntity.getId(), pEntity.getTitle(),
				pEntity.getSummary(), pEntity.getCreationDate(),
				pEntity.getContent().getValue());
		if(StringUtils.isBlank(pEntity.getSummary())) {
			lUi.setSummary("Détail...");
		}
		return lUi;
	}
}