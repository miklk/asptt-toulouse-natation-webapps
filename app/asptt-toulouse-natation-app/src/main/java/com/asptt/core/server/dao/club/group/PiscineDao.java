package com.asptt.core.server.dao.club.group;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.club.group.PiscineEntity;

public class PiscineDao extends DaoBase<PiscineEntity> {

	@Override
	public Class<PiscineEntity> getEntityClass() {
		return PiscineEntity.class;
	}

	@Override
	public String getAlias() {
		return "piscine";
	}

	@Override
	public Object getKey(PiscineEntity pEntity) {
		return pEntity.getId();
	}
}