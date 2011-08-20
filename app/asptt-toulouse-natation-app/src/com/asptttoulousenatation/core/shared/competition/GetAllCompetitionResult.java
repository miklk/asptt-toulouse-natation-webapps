package com.asptttoulousenatation.core.shared.competition;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class GetAllCompetitionResult implements Result {

	private List<CompetitionUi> competitions;
	
	public GetAllCompetitionResult() {
		
	}

	public GetAllCompetitionResult(List<CompetitionUi> pCompetitions) {
		super();
		competitions = pCompetitions;
	}

	public List<CompetitionUi> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<CompetitionUi> pCompetitions) {
		competitions = pCompetitions;
	}
}
