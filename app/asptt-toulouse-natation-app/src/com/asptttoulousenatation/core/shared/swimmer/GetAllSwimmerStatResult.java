package com.asptttoulousenatation.core.shared.swimmer;

import java.util.Date;
import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class GetAllSwimmerStatResult<S extends ISwimmerStatUi> implements Result {

	private Date currentDay;
	private List<S> results;
	private String currentDayText;
	
	public GetAllSwimmerStatResult() {
		
	}

	public GetAllSwimmerStatResult(Date pCurrentDay, String pCurrentDayText, List<S> pResults) {
		super();
		results = pResults;
		currentDay = pCurrentDay;
		currentDayText = pCurrentDayText;
	}

	public Date getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Date pCurrentDay) {
		currentDay = pCurrentDay;
	}

	public List<S> getResults() {
		return results;
	}

	public void setResults(List<S> pResults) {
		results = pResults;
	}

	public String getCurrentDayText() {
		return currentDayText;
	}

	public void setCurrentDayText(String pCurrentDayText) {
		currentDayText = pCurrentDayText;
	}
}