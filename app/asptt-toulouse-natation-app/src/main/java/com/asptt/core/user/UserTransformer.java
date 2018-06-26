package com.asptt.core.user;

import java.util.HashSet;

import com.asptt.core.lang.AbstractEntityTransformer;
import com.asptt.core.server.dao.entity.user.UserEntity;
import com.asptt.core.shared.user.UserUi;

public class UserTransformer extends
		AbstractEntityTransformer<UserUi, UserEntity> {

	@Override
	public UserUi toUi(UserEntity pEntity) {
		UserUi lUi = new UserUi(pEntity.getId(), pEntity.getEmailaddress(),
				pEntity.isValidated(), new HashSet<String>(), null, null);
		if (pEntity.getSlots() != null) {
			lUi.setSlots(new HashSet<Long>(pEntity.getSlots()));
		}
		return lUi;
	}
}