package com.asptttoulousenatation.shared.init;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.shared.Result;

import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.shared.event.UiEvent;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

public class InitResult implements Result {

	private List<ActuUi> actu;
	private Map<String, AreaUi> area;
	private Map<Date, List<UiEvent>> events;
	
	private String[] photo;
	
	public InitResult() {
		
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
	
	public AreaUi getArea(final String pAreaTitle) {
		return area.get(pAreaTitle);
	}

	public Map<Date, List<UiEvent>> getEvents() {
		return events;
	}

	public void setEvents(Map<Date, List<UiEvent>> pEvents) {
		events = pEvents;
	}

	public String[] getPhoto() {
		return photo;
	}

	public void setPhoto(String[] pPhoto) {
		photo = pPhoto;
	}
	
}