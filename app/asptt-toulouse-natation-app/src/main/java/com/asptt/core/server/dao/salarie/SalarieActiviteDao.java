package com.asptt.core.server.dao.salarie;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.salarie.SalarieActiviteEntity;

public class SalarieActiviteDao extends DaoBase<SalarieActiviteEntity> {

	@Override
	public Class<SalarieActiviteEntity> getEntityClass() {
		return SalarieActiviteEntity.class;
	}

	@Override
	public String getAlias() {
		return "salarieact";
	}
	@Override
	public Object getKey(SalarieActiviteEntity pEntity) {
		return pEntity.getId();
	}
}