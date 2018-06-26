package com.asptt.core.server.dao.competition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.asptt.core.server.dao.DaoBase;
import com.asptt.core.server.dao.entity.competition.CompetitionEpreuveEntity;
import com.asptt.core.server.service.EMF;

public class CompetitionEpreuveDao extends DaoBase<CompetitionEpreuveEntity> {

	public List<CompetitionEpreuveEntity> findByCompetition(Long competition) {
		EntityManager em = EMF.get().createEntityManager();
		List<CompetitionEpreuveEntity> entities = new ArrayList<>();
		StringBuilder queryAsString = new StringBuilder(
				"SELECT epreuve FROM CompetitionEpreuveEntity epreuve WHERE epreuve.competition=:competition");
		try {
			TypedQuery<CompetitionEpreuveEntity> query = em.createQuery(queryAsString.toString(),
					CompetitionEpreuveEntity.class);
			query.setParameter("competition", competition);
			entities = query.getResultList();
		} finally {
			em.close();
		}

		return entities;
	}

	@Override
	public Class<CompetitionEpreuveEntity> getEntityClass() {
		return CompetitionEpreuveEntity.class;
	}

	@Override
	public String getAlias() {
		return "competitionEpreuve";
	}

	@Override
	public Object getKey(CompetitionEpreuveEntity pEntity) {
		return pEntity.getId();
	}

}
