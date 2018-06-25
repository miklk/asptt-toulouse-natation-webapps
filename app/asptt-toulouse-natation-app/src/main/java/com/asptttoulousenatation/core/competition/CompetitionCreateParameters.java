package com.asptttoulousenatation.core.competition;

import java.io.Serializable;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEntity;
import com.asptttoulousenatation.core.server.dao.entity.competition.CompetitionEpreuveEntity;

public class CompetitionCreateParameters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CompetitionEntity competition;
	private List<CompetitionEpreuveEntity> epreuves;
	public CompetitionEntity getCompetition() {
		return competition;
	}
	public void setCompetition(CompetitionEntity competition) {
		this.competition = competition;
	}
	public List<CompetitionEpreuveEntity> getEpreuves() {
		return epreuves;
	}
	public void setEpreuves(List<CompetitionEpreuveEntity> epreuves) {
		this.epreuves = epreuves;
	}
}
