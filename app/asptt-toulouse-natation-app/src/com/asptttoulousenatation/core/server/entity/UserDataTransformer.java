package com.asptttoulousenatation.core.server.entity;

import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.shared.user.UserDataUi;
import com.asptttoulousenatation.server.userspace.admin.entity.AbstractEntityTransformer;

public class UserDataTransformer extends
		AbstractEntityTransformer<UserDataUi, UserDataEntity> {

	@Override
	public UserDataUi toUi(UserDataEntity pEntity) {
		UserDataUi lUi = new UserDataUi(pEntity.getFirstName(),
				pEntity.getLastName(), pEntity.getBirthday(),
				pEntity.getPhonenumber());
		return lUi;
	}

}
