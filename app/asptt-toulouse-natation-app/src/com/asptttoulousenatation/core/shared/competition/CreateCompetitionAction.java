package com.asptttoulousenatation.core.shared.competition;

import java.util.Date;
import java.util.Set;

import net.customware.gwt.dispatch.shared.Action;

public class CreateCompetitionAction implements Action<CreateCompetitionResult> {

	private String saison;
	private String title;
	private String place;
	private Date begin;
	private Date end;
	private Set<CompetitionDayUi> days;
	
	public CreateCompetitionAction() {
		
	}

	public CreateCompetitionAction(String pSaison, String pTitle,
			String pPlace, Date pBegin, Date pEnd, Set<CompetitionDayUi> pDays) {
		super();
		saison = pSaison;
		title = pTitle;
		place = pPlace;
		begin = pBegin;
		end = pEnd;
		days = pDays;
	}

	public String getSaison() {
		return saison;
	}

	public void setSaison(String pSaison) {
		saison = pSaison;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String pTitle) {
		title = pTitle;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String pPlace) {
		place = pPlace;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date pBegin) {
		begin = pBegin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date pEnd) {
		end = pEnd;
	}

	public Set<CompetitionDayUi> getDays() {
		return days;
	}

	public void setDays(Set<CompetitionDayUi> pDays) {
		days = pDays;
	}
}