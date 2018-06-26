package com.asptt.core.server.dao.reference;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.field.ParamEntityFields;
import com.asptt.core.server.dao.entity.reference.ParamEntity;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;

public class ParamDao extends DaoBase<ParamEntity> {

	@Override
	public Class<ParamEntity> getEntityClass() {
		return ParamEntity.class;
	}

	@Override
	public String getAlias() {
		return "param";
	}

	@Override
	public Object getKey(ParamEntity pEntity) {
		return pEntity.getId();
	}

	public ParamEntity findByKey(String key) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<String>(ParamEntityFields.KEY, key, Operator.EQUAL));
		List<ParamEntity> entities = find(criteria);
		final ParamEntity result;
		if (CollectionUtils.isNotEmpty(entities)) {
			result = entities.get(0);
		} else {
			result = null;
		}
		return result;
	}
	
	public List<ParamEntity> findByGroupe(String groupe) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<String>(ParamEntityFields.GROUPE, groupe, Operator.EQUAL));
		List<ParamEntity> entities = find(criteria);
		return entities;
	}
}
