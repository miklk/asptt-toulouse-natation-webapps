package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

public class AreaTransformer extends
		AbstractEntityTransformer<AreaUi, AreaEntity> {

	@Override
	public AreaUi toUi(AreaEntity pEntity) {
		AreaUi lUi = new AreaUi(pEntity.getId(), pEntity.getTitle(), pEntity.isShortcut());
		return lUi;
	}

}
