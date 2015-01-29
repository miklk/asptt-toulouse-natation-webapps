package com.asptttoulousenatation.core.server.entity;

import java.util.HashSet;

import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.asptttoulousenatation.server.userspace.admin.entity.AbstractEntityTransformer;

public class UserTransformer extends
		AbstractEntityTransformer<UserUi, UserEntity> {

	@Override
	public UserUi toUi(UserEntity pEntity) {
		UserUi lUi = new UserUi(pEntity.getId(), pEntity.getEmailaddress(),
				pEntity.isValidated(), new HashSet<String>(
						pEntity.getProfiles()), null, null);
		if (pEntity.getSlots() != null) {
			lUi.setSlots(new HashSet<Long>(pEntity.getSlots()));
		}
		return lUi;
	}
}