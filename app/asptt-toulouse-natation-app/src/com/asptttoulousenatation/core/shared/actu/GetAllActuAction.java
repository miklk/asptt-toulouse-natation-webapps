package com.asptttoulousenatation.core.shared.actu;


import net.customware.gwt.dispatch.shared.Action;

public class GetAllActuAction implements Action<GetAllActuResult> {
	
	private long limitStart;
	private long limitEnd;

	public GetAllActuAction() {
		limitStart = 0;
		limitEnd = Long.MAX_VALUE;
	}

	public GetAllActuAction(long pLimitStart, long pLimitEnd) {
		super();
		limitStart = pLimitStart;
		limitEnd = pLimitEnd;
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