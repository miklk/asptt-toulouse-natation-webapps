package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.shared.userspace.admin.structure.MenuEditionUI;

public class MenuEditionTransformer extends
		AbstractEntityTransformer<MenuEditionUI, MenuEntity> {

	@Override
	public MenuEditionUI toUi(MenuEntity pEntity) {
		MenuEditionUI lUi = new MenuEditionUI();
		lUi.setId(pEntity.getId().getId());
		lUi.setTitle(pEntity.getTitle());
		lUi.setRoot(true);
		return lUi;
	}
}
