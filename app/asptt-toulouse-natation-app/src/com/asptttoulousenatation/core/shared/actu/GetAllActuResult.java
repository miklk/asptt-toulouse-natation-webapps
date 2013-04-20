package com.asptttoulousenatation.core.shared.actu;

import java.util.List;


import net.customware.gwt.dispatch.shared.Result;

public class GetAllActuResult implements Result {

	private List<ActuUi> result;
	private int limitStart;
	private int limitEnd;

	public GetAllActuResult() {
		limitStart = -1;
		limitEnd = -1;
	}
	
	public GetAllActuResult(List<ActuUi> pResult) {
		this();
		result = pResult;
	}

	public GetAllActuResult(List<ActuUi> pResult, int pLimitStart, int pLimitEnd) {
		super();
		result = pResult;
		limitStart = pLimitStart;
		limitEnd = pLimitEnd;
	}

	public List<ActuUi> getResult() {
		return result;
	}

	public void setResult(List<ActuUi> pResult) {
		result = pResult;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int pLimitStart) {
		limitStart = pLimitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int pLimitEnd) {
		limitEnd = pLimitEnd;
	}
	
}