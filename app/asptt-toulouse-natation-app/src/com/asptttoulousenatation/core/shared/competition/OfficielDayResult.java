package com.asptttoulousenatation.core.shared.competition;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class OfficielDayResult implements Result {
	
	private CompetitionDayUi competitionDayUi;
	private List<CompetitionUi> competitions;

	public OfficielDayResult() {
		
	}

	public OfficielDayResult(CompetitionDayUi pCompetitionDayUi) {
		super();
		competitionDayUi = pCompetitionDayUi;
	}

	public CompetitionDayUi getCompetitionDayUi() {
		return competitionDayUi;
	}

	public void setCompetitionDayUi(CompetitionDayUi pCompetitionDayUi) {
		competitionDayUi = pCompetitionDayUi;
	}

	public List<CompetitionUi> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<CompetitionUi> pCompetitions) {
		competitions = pCompetitions;
	}
}