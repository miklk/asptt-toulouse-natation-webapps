package com.asptt.core.server.dao.club.group;

import java.util.ArrayList;
import java.util.List;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.club.group.GroupEntity;
import com.asptt.core.server.dao.entity.field.GroupEntityFields;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;

public class GroupDao extends DaoBase<GroupEntity> {

	public List<GroupEntity> findEnf() {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Boolean>(GroupEntityFields.ENF, Boolean.TRUE, Operator.EQUAL));
		List<GroupEntity> entities = find(criteria);
		List<GroupEntity> result = new ArrayList<>(entities);
		return result;
	}
	
	public List<GroupEntity> findByTitle(String title) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<String>(GroupEntityFields.TITLE, title, Operator.EQUAL));
		List<GroupEntity> entities = find(criteria);
		List<GroupEntity> result = new ArrayList<>(entities);
		return result;
	}

	@Override
	public Class<GroupEntity> getEntityClass() {
		return GroupEntity.class;
	}

	@Override
	public String getAlias() {
		return "group";
	}

	@Override
	public Object getKey(GroupEntity pEntity) {
		return pEntity.getId();
	}

}
