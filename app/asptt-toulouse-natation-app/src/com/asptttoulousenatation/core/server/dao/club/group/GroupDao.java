package com.asptttoulousenatation.core.server.dao.club.group;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;

public class GroupDao extends DaoBase<GroupEntity> {

	@Override
	public Class<GroupEntity> getEntityClass() {
		return GroupEntity.class;
	}

	@Override
	public String getAlias() {
		return "group";
	}
	
	@Override
	public Object getKey(GroupEntity pEntity) {
		return pEntity.getId();
	}

}
