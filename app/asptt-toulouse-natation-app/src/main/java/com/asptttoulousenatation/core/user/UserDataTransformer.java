package com.asptttoulousenatation.core.user;

import com.asptttoulousenatation.core.lang.AbstractEntityTransformer;
import com.asptttoulousenatation.core.server.dao.entity.user.UserDataEntity;
import com.asptttoulousenatation.core.shared.user.UserDataUi;

public class UserDataTransformer extends
		AbstractEntityTransformer<UserDataUi, UserDataEntity> {

	@Override
	public UserDataUi toUi(UserDataEntity pEntity) {
		UserDataUi lUi = new UserDataUi(pEntity.getFirstName(),
				pEntity.getLastName(), pEntity.getBirthday(), pEntity.getBirthdayPlace(), pEntity.getNationality(),
				pEntity.getPhonenumber(), pEntity.getAddressRoad(),
				pEntity.getAddressAdditional(), pEntity.getAddressCode(),
				pEntity.getAddressCity(), pEntity.getGender(),
				pEntity.getMeasurementSwimsuit(),
				pEntity.getMeasurementTshirt(), pEntity.getMeasurementShort(), pEntity.getContactLastName(), pEntity.getContactFirstName());
		return lUi;
	}

}