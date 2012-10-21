package com.asptttoulousenatation.core.shared.swimmer;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

public class GetAllSwimmerStatAction implements Action<GetAllSwimmerStatResult<?>> {

	private SwimmerStatEnum period;
	private Date beginDate;
	private Boolean previousNext;
	
	public GetAllSwimmerStatAction() {
		
	}

	public GetAllSwimmerStatAction(SwimmerStatEnum pPeriod, Date pBeginDate, Boolean pPreviousNext) {
		period = pPeriod;
		beginDate = pBeginDate;
		previousNext = pPreviousNext;
	}

	public SwimmerStatEnum getPeriod() {
		return period;
	}

	public void setPeriod(SwimmerStatEnum pPeriod) {
		period = pPeriod;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date pBeginDate) {
		beginDate = pBeginDate;
	}

	public Boolean getPreviousNext() {
		return previousNext;
	}

	public void setPreviousNext(Boolean pPreviousNext) {
		previousNext = pPreviousNext;
	}
}