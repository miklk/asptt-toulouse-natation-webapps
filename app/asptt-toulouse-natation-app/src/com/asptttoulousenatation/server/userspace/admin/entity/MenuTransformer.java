package com.asptttoulousenatation.server.userspace.admin.entity;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

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
		lUi.setDivider(BooleanUtils.toBoolean(pEntity.getDivider()));
		lUi.setAlone(BooleanUtils.toBoolean(pEntity.getAlone()));
		if(StringUtils.isBlank(pEntity.getIdentifier())) {
			lUi.setIdentifier(pEntity.getTitle());
		} else {
			lUi.setIdentifier(pEntity.getIdentifier());
		}
		return lUi;
	}

}
