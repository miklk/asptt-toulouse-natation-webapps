package com.asptttoulousenatation.core.server.dao.inscription;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurPhotoEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurPhotoEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class DossierNageurPhotoDao extends DaoBase<DossierNageurPhotoEntity> {

	@Override
	public Class<DossierNageurPhotoEntity> getEntityClass() {
		return DossierNageurPhotoEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossiernageurphoto";
	}

	@Override
	public Object getKey(DossierNageurPhotoEntity pEntity) {
		return pEntity.getId();
	}
	
	public DossierNageurPhotoEntity findByDossier(Long dossier) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(DossierNageurPhotoEntityFields.DOSSIER, dossier, Operator.EQUAL));
		List<DossierNageurPhotoEntity> entities = find(criteria);
		final DossierNageurPhotoEntity result;
		if (CollectionUtils.isNotEmpty(entities)) {
			result = entities.get(0);
		} else {
			result = null;
		}
		return result;
	}
}