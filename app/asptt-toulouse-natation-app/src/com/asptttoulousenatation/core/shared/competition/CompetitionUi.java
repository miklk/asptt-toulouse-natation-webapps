package com.asptttoulousenatation.core.shared.competition;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CompetitionUi implements IsSerializable {
	private Long id;
	private String title;
	private String place;
	private Date begin;
	private Date end;
	private String saison;
	private Set<CompetitionDayUi> days = new HashSet<CompetitionDayUi>();
	
	public CompetitionUi() {
		
	}

	public CompetitionUi(Long pId, String pTitle, String pPlace, Date pBegin,
			Date pEnd, String pSaison, Set<CompetitionDayUi> pDays) {
		super();
		id = pId;
		title = pTitle;
		place = pPlace;
		begin = pBegin;
		end = pEnd;
		saison = pSaison;
		days = pDays;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
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

	public String getSaison() {
		return saison;
	}

	public void setSaison(String pSaison) {
		saison = pSaison;
	}

	public Set<CompetitionDayUi> getDays() {
		return days;
	}

	public void setDays(Set<CompetitionDayUi> pDays) {
		days = pDays;
	}
}