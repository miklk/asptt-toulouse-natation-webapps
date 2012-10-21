package com.asptttoulousenatation.core.shared.swimmer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import net.customware.gwt.dispatch.shared.Action;

public class UpdateSwimmerStatAction implements Action<UpdateSwimmerStatResult> {

	private List<UpdateSwimmerStatActionData> data;
	
	public UpdateSwimmerStatAction() {
		data = new ArrayList<UpdateSwimmerStatActionData>();
	}

	public UpdateSwimmerStatAction(List<UpdateSwimmerStatActionData> pData) {
		data = pData;
	}

	public List<UpdateSwimmerStatActionData> getData() {
		return data;
	}

	public void setData(List<UpdateSwimmerStatActionData> pData) {
		data = pData;
	}

	public void addData(Long pId, Long pUser, Date pDay,
			DayTimeEnum pDayTime, int pDistance, String pComment) {
		data.add(new UpdateSwimmerStatActionData(pId, pUser, pDay, pDayTime, pDistance, pComment));
	}
}