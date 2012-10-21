package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerEntity;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerUi;

public class SwimmerTransformer extends AbstractEntityTransformer<SwimmerUi, SwimmerEntity> {

	@Override
	public SwimmerUi toUi(SwimmerEntity pEntity) {
		SwimmerUi lUi = new SwimmerUi();
		lUi.setId(pEntity.getId());
		lUi.setStat(pEntity.isStat());
		return lUi;
	}

	
}
