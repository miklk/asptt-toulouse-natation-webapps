package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;

public class GroupTransformer extends
		AbstractEntityTransformer<GroupUi, GroupEntity> {

	@Override
	public GroupUi toUi(GroupEntity pEntity) {
		GroupUi lUi = new GroupUi(pEntity.getId(), pEntity.getTitle());
		return lUi;
	}

}
