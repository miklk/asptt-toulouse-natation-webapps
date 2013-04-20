package com.asptttoulousenatation.core.shared.actu;


import net.customware.gwt.dispatch.shared.Action;

public class GetAllActuAction implements Action<GetAllActuResult> {
	
	private int limitStart;
	private int limitEnd;

	public GetAllActuAction() {
		limitStart = -1;
		limitEnd = -1;
	}

	public GetAllActuAction(int pLimitStart, int pLimitEnd) {
		super();
		limitStart = pLimitStart;
		limitEnd = pLimitEnd;
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
