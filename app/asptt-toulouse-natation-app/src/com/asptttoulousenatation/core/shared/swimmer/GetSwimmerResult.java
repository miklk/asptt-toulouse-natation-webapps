package com.asptttoulousenatation.core.shared.swimmer;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class GetSwimmerResult implements Result {

	private List<SwimmerUi> results;
	
	public GetSwimmerResult() {
		
	}

	public List<SwimmerUi> getResults() {
		return results;
	}

	public void setResults(List<SwimmerUi> pResults) {
		results = pResults;
	}
}