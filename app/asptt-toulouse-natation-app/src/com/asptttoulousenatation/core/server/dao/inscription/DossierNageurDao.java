package com.asptttoulousenatation.core.server.dao.inscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.service.EMF;

public class DossierNageurDao extends DaoBase<DossierNageurEntity> {
	
	public Collection<Date> getDateNaissances() {
		EntityManager em = EMF.get().createEntityManager();
		List<Date> lAllList = new ArrayList<>();
		try {
			TypedQuery<Date> query = em.createQuery("SELECT DISTINCT "
					+ getAlias() + ".naissance FROM "
					+ getEntityClass().getSimpleName() + " " + getAlias(),
					Date.class);
			lAllList = query.getResultList();
		} finally {
			em.close();
		}
		return lAllList;
	}
	
	public List<DossierNageurEntity> findByDossier(Long dossier) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER, dossier, Operator.EQUAL));
		List<DossierNageurEntity> entities = find(criteria);
		List<DossierNageurEntity> result = new ArrayList<>(entities);
		return result;
	}

	@Override
	public Class<DossierNageurEntity> getEntityClass() {
		return DossierNageurEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossierNageur";
	}

	@Override
	public Object getKey(DossierNageurEntity pEntity) {
		return pEntity.getId();
	}
}