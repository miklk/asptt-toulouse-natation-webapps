package com.asptttoulousenatation.server.userspace.admin.entity;

import org.apache.commons.lang3.BooleanUtils;

import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;

public class GroupTransformer extends
		AbstractEntityTransformer<GroupUi, GroupEntity> {

	@Override
	public GroupUi toUi(GroupEntity pEntity) {
		GroupUi lUi = new GroupUi(pEntity.getId(), pEntity.getTitle(), BooleanUtils.toBoolean(pEntity.getLicenceFfn()));
		return lUi;
	}

}
