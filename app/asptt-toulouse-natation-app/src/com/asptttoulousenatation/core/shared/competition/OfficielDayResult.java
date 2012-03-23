package com.asptttoulousenatation.core.shared.competition;

import net.customware.gwt.dispatch.shared.Result;

public class OfficielDayResult implements Result {
	
	CompetitionDayUi competitionDayUi;

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
}