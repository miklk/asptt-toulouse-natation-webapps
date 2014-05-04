package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.shared.competition.CompetitionUi;

public class CompetitionTransformer extends
		AbstractEntityTransformer<CompetitionUi, CompetitionEntity> {

	@Override
	public CompetitionUi toUi(CompetitionEntity pEntity) {
		CompetitionUi lUi = new CompetitionUi();
		lUi.setId(pEntity.getId());
		lUi.setTitle(pEntity.getTitle());
		lUi.setPlace(pEntity.getPlace());
		lUi.setBegin(pEntity.getBegin());
		lUi.setEnd(pEntity.getEnd());
		lUi.setSaison(pEntity.getSaison());
		return lUi;
	}
}