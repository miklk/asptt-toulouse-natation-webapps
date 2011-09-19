package com.asptttoulousenatation.core.shared.competition;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.user.client.rpc.IsSerializable;

public class CompetitionDayUi implements IsSerializable {
	private Long id;
	private int day;
	private Date begin;
	private Date end;
	private int needed;
	private Set<UserUi> officiels = new HashSet<UserUi>();
	private CompetitionUi competitionUi;
	
	public CompetitionDayUi() {
		
	}

	public CompetitionDayUi(Long pId, int pDay, Date pBegin, Date pEnd,
			int pNeeded, Set<UserUi> pOfficiels) {
		super();
		id = pId;
		day = pDay;
		begin = pBegin;
		end = pEnd;
		needed = pNeeded;
		officiels = pOfficiels;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long pId) {
		id = pId;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int pDay) {
		day = pDay;
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

	public int getNeeded() {
		return needed;
	}

	public void setNeeded(int pNeeded) {
		needed = pNeeded;
	}

	public Set<UserUi> getOfficiels() {
		return officiels;
	}

	public void setOfficiels(Set<UserUi> pOfficiels) {
		officiels = pOfficiels;
	}

	public CompetitionUi getCompetitionUi() {
		return competitionUi;
	}

	public void setCompetitionUi(CompetitionUi pCompetitionUi) {
		competitionUi = pCompetitionUi;
	}
	
	public Set<Date> getCalendarEntries() {
		Set<Date> lEntries = new HashSet<Date>();
		//Interval
		int lInterval = getEnd().getDate() - getBegin().getDate();
		Date lDate = (Date) getBegin().clone();
		for(int i = 0; i <= lInterval; i++) {
			Date lCurrentDate = (Date) lDate.clone();
			lCurrentDate.setDate(lDate.getDate() + i);
			lCurrentDate.setHours(0);
			lCurrentDate.setMinutes(0);
			lCurrentDate.setSeconds(0);
			lEntries.add(lCurrentDate);
		}
		return lEntries;
	}
	
	public boolean isOfficiel(UserUi pUser) {
		boolean lFound = false;
		Iterator<UserUi> lIt = getOfficiels().iterator();
		while(lIt.hasNext() && !lFound) {
			lFound = lIt.next().getId().equals(pUser.getId());
		}
		return lFound;
	}
}