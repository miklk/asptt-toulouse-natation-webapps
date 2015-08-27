package com.asptttoulousenatation.core.server.dao.inscription;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierFactureEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierFactureEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierFactureEnum;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class DossierFactureDao extends DaoBase<DossierFactureEntity> {

	public DossierFactureEntity findByDossier(Long dossier) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(DossierFactureEntityFields.DOSSIER, dossier, Operator.EQUAL));
		List<DossierFactureEntity> factures = find(criteria);
		final DossierFactureEntity facture;
		if (CollectionUtils.isNotEmpty(factures)) {
			facture = factures.get(0);
		} else {
			facture = null;
		}
		return facture;
	}
	
	public DossierFactureEntity findByDossierAndStatut(Long dossier, DossierFactureEnum statut) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(2);
		criteria.add(new CriterionDao<Long>(DossierFactureEntityFields.DOSSIER, dossier, Operator.EQUAL));
		criteria.add(new CriterionDao<String>(DossierFactureEntityFields.STATUT, statut.name(), Operator.EQUAL));
		List<DossierFactureEntity> factures = find(criteria);
		final DossierFactureEntity facture;
		if (CollectionUtils.isNotEmpty(factures)) {
			facture = factures.get(0);
		} else {
			facture = null;
		}
		return facture;
	}
	
	public boolean existsByDossier(Long dossier) {
		return findByDossier(dossier) != null;
	}
	
	public boolean existsByDossierAndStatut(Long dossier, DossierFactureEnum statut) {
		return findByDossierAndStatut(dossier, statut) != null;
	}

	@Override
	public Class<DossierFactureEntity> getEntityClass() {
		return DossierFactureEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossierFacture";
	}

	@Override
	public Object getKey(DossierFactureEntity pEntity) {
		return pEntity.getId();
	}

}
