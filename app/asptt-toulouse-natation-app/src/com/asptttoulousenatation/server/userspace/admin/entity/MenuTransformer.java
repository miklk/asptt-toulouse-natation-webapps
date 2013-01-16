package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.shared.structure.MenuUi;

public class MenuTransformer extends
		AbstractEntityTransformer<MenuUi, MenuEntity> {
	
	@Override
	public MenuUi toUi(MenuEntity pEntity) {
		MenuUi lUi = new MenuUi();
		lUi.setId(pEntity.getId());
		lUi.setTitle(pEntity.getTitle());
		lUi.setMenuKey(pEntity.getMenuKey());
		lUi.setRoot(true);
		lUi.setShortcut(pEntity.isShortcut());
		lUi.setDisplay(pEntity.isDisplay());
		lUi.setOrder(pEntity.getOrder());
		return lUi;
	}

}
