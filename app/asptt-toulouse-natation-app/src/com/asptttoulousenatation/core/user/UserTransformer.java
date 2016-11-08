package com.asptttoulousenatation.core.user;

import java.util.HashSet;

import com.asptttoulousenatation.core.lang.AbstractEntityTransformer;
import com.asptttoulousenatation.core.server.dao.entity.user.UserEntity;
import com.asptttoulousenatation.core.shared.user.UserUi;

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