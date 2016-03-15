package com.asptttoulousenatation.core.server.dao.structure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class ContentDao extends DaoBase<ContentEntity> {

	public ContentEntity findByMenu(Long menu) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(ContentEntityFields.MENU, menu, Operator.EQUAL));
		List<ContentEntity> entities = find(criteria);
		final ContentEntity entity;
		if(CollectionUtils.isNotEmpty(entities)) {
			entity = entities.get(0);
		} else {
			entity = null;
		}
		return entity;
	}
	
	@Override
	public Class<ContentEntity> getEntityClass() {
		return ContentEntity.class;
	}

	@Override
	public String getAlias() {
		return "content";
	}
	
	@Override
	public Object getKey(ContentEntity pEntity) {
		return pEntity.getId();
	}
}