package com.asptt.core.server.dao.inscription;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.inscription.DossierEntity;
import com.asptt.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptt.core.server.service.EMF;

public class DossierDao extends DaoBase<DossierEntity> {
	
	public List<DossierEntity> findByStatus(DossierStatutEnum...status) {
		EntityManager em = EMF.get().createEntityManager();
		List<DossierEntity> entities = new ArrayList<DossierEntity>();
		StringBuilder queryAsString = new StringBuilder("SELECT dossier FROM DossierEntity dossier WHERE ");
		for(int i = 0; i < status.length; i++) {
			queryAsString.append("dossier.statut =:statut" + i);
			if(i != status.length -1) {
				queryAsString.append(" OR ");
			}
		}
		try {
			TypedQuery<DossierEntity> query = em.createQuery(queryAsString.toString(),
					DossierEntity.class);
			for(int i = 0; i < status.length; i++) {
				query.setParameter("statut" + i, status[i]);
			}
			entities = query.getResultList();
		} finally {
			em.close();
		}

		return entities;	
	}

	@Override
	public Class<DossierEntity> getEntityClass() {
		return DossierEntity.class;
	}

	@Override
	public String getAlias() {
		return "dossierEntity";
	}

	@Override
	public Object getKey(DossierEntity pEntity) {
		return pEntity.getId();
	}
}