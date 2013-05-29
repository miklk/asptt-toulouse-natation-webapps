package com.asptttoulousenatation.core.shared.actu;

import java.util.List;


import net.customware.gwt.dispatch.shared.Result;

public class GetAllActuResult implements Result {

	private List<ActuUi> result;
	private long limitStart;
	private long limitEnd;

	public GetAllActuResult() {
		limitStart = -1;
		limitEnd = -1;
	}
	
	public GetAllActuResult(List<ActuUi> pResult) {
		this();
		result = pResult;
	}

	public GetAllActuResult(List<ActuUi> pResult, long pLimitStart, long pLimitEnd) {
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

	public long getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(long pLimitStart) {
		limitStart = pLimitStart;
	}

	public long getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(long pLimitEnd) {
		limitEnd = pLimitEnd;
	}
}