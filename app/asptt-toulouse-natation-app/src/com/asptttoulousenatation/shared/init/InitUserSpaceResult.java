package com.asptttoulousenatation.shared.init;

import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

import net.customware.gwt.dispatch.shared.Result;

public class InitUserSpaceResult implements Result {

	private List<ActuUi> actu;
	private Map<String, AreaUi> area;
	
	public InitUserSpaceResult() {
		
	}

	public List<ActuUi> getActu() {
		return actu;
	}

	public void setActu(List<ActuUi> pActu) {
		actu = pActu;
	}

	public Map<String, AreaUi> getArea() {
		return area;
	}

	public void setArea(Map<String, AreaUi> pArea) {
		area = pArea;
	}
}