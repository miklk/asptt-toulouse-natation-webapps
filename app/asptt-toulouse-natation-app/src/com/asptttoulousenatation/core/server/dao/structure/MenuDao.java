package com.asptttoulousenatation.core.server.dao.structure;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;

public class MenuDao extends DaoBase<MenuEntity> {

	@Override
	public Class<MenuEntity> getEntityClass() {
		return MenuEntity.class;
	}

}
