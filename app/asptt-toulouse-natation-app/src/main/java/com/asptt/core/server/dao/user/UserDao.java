package com.asptt.core.server.dao.user;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.user.UserEntity;

public class UserDao extends DaoBase<UserEntity> {

	@Override
	public Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

	@Override
	public String getAlias() {
		return "user";
	}
	@Override
	public Object getKey(UserEntity pEntity) {
		return pEntity.getId();
	}
}