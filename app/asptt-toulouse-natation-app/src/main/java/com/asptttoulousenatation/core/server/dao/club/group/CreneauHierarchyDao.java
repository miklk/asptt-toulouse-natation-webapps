package com.asptttoulousenatation.core.server.dao.club.group;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.club.group.CreneauHierarchyEntity;

public class CreneauHierarchyDao extends DaoBase<CreneauHierarchyEntity> {

	@Override
	public Class<CreneauHierarchyEntity> getEntityClass() {
		return CreneauHierarchyEntity.class;
	}

	@Override
	public String getAlias() {
		return "creneauH";
	}

	@Override
	public Object getKey(CreneauHierarchyEntity pEntity) {
		return pEntity.getId();
	}
}