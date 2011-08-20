package com.asptttoulousenatation.core.shared.actu;

import java.util.List;


import net.customware.gwt.dispatch.shared.Result;

public class GetAllActuResult implements Result {

	private List<ActuUi> result;

	public GetAllActuResult() {
		
	}
	
	public GetAllActuResult(List<ActuUi> pResult) {
		result = pResult;
	}

	public List<ActuUi> getResult() {
		return result;
	}

	public void setResult(List<ActuUi> pResult) {
		result = pResult;
	}
	
}
