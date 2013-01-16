package com.asptttoulousenatation.core.shared.competition;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

public class GetAllCompetitionAction implements Action<GetAllCompetitionResult> {

	private Date day;
	
	public GetAllCompetitionAction() {
		
	}

	public GetAllCompetitionAction(Date pDay) {
		super();
		day = pDay;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date pDay) {
		day = pDay;
	}
}