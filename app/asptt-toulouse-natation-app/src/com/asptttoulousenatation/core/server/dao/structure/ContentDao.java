package com.asptttoulousenatation.core.server.dao.structure;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;

public class ContentDao extends DaoBase<ContentEntity> {

	@Override
	public Class<ContentEntity> getEntityClass() {
		return ContentEntity.class;
	}
}