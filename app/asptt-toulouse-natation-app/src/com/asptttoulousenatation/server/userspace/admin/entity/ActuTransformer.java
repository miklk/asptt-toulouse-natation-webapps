package com.asptttoulousenatation.server.userspace.admin.entity;

import java.util.Date;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.shared.actu.ActuUi;

public class ActuTransformer extends
		AbstractEntityTransformer<ActuUi, ActuEntity> {

	@Override
	public ActuUi toUi(ActuEntity pEntity) {
		ActuUi lUi = new ActuUi(pEntity.getId(), pEntity.getTitle(), new Date(pEntity.getCreationDate().getTime()),
				pEntity.getContent().getValue(), StringUtils.defaultString(pEntity.getImageUrl(), "img/actu_defaut.jpg"), BooleanUtils.toBoolean(pEntity.getCompetition()), pEntity.getExpiration());
		if(pEntity.getExpiration() != null) {
			lUi.setExpiration(new Date(pEntity.getExpiration().getTime()));
		}
		lUi.setCreated(pEntity.getCreated());
		lUi.setUpdated(pEntity.getUpdated());
		lUi.setCreatedBy(pEntity.getCreatedBy());
		lUi.setUpdatedBy(pEntity.getUpdatedBy());
		return lUi;
	}
}