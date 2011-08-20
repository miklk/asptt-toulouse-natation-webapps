package com.asptttoulousenatation.core.server.dao.user;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;

public class UserDataDao extends DaoBase<UserDataEntity> {

	@Override
	public Class<UserDataEntity> getEntityClass() {
		return UserDataEntity.class;
	}
}