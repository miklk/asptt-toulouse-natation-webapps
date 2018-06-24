package com.asptttoulousenatation.core.server.dao.structure;

import java.util.ArrayList;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class MenuDao extends DaoBase<MenuEntity> {
	
	public List<MenuEntity> findByArea(Long area) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(MenuEntityFields.AREA, area, Operator.EQUAL));
		List<MenuEntity> menus = find(criteria);
		return menus;
	}

	@Override
	public Class<MenuEntity> getEntityClass() {
		return MenuEntity.class;
	}

	@Override
	public String getAlias() {
		return "menu";
	}
	
	@Override
	public Object getKey(MenuEntity pEntity) {
		return pEntity.getId();
	}

}
