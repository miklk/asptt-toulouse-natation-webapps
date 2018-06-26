package com.asptt.core.server.dao.document;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.document.DocumentEntity;
import com.asptt.core.server.dao.entity.field.DocumentEntityFields;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;

public class DocumentDao extends DaoBase<DocumentEntity> {
	
	public DocumentEntity findByMenu(Long menu) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(DocumentEntityFields.MENU, menu, Operator.EQUAL));
		List<DocumentEntity> entities = find(criteria);
		final DocumentEntity entity;
		if(CollectionUtils.isNotEmpty(entities)) {
			entity = entities.get(0);
		} else {
			entity = null;
		}
		return entity;
	}

	@Override
	public Class<DocumentEntity> getEntityClass() {
		return DocumentEntity.class;
	}

	@Override
	public String getAlias() {
		return "document";
	}
	
	@Override
	public Object getKey(DocumentEntity pEntity) {
		return pEntity.getId();
	}
}