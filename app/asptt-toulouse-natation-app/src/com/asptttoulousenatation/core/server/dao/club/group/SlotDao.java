package com.asptttoulousenatation.core.server.dao.club.group;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;

public class SlotDao extends DaoBase<SlotEntity> {

	@Override
	public Class<SlotEntity> getEntityClass() {
		return SlotEntity.class;
	}
}