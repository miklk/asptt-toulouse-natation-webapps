package com.asptttoulousenatation.server;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.shared.event.UiEvent;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

public class ApplicationLoader {

	private List<ActuUi> actu;
	private Map<String, AreaUi> area;
	private Map<Date, List<UiEvent>> events;
	
	private static ApplicationLoader INSTANCE;
	
	public ApplicationLoader() {
		
	}
	
	public static ApplicationLoader getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ApplicationLoader();
		}
		return INSTANCE;
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

	public Map<Date, List<UiEvent>> getEvents() {
		return events;
	}

	public void setEvents(Map<Date, List<UiEvent>> pEvents) {
		events = pEvents;
	}
}