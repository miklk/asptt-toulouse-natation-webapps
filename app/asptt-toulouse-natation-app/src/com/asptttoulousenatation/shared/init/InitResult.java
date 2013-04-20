package com.asptttoulousenatation.shared.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
	
	private int actuStart;
	private int actuEnd;
	
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
	
	public List<AreaUi> getMenu() {
		Collection<AreaUi> lAreas = area.values();
		List<AreaUi> result = new ArrayList<AreaUi>(lAreas.size());
		for(AreaUi lArea: lAreas) {
			if(lArea.getOrder() != -1) {
				result.add(lArea);
			}
		}
		
		Collections.sort(result, new Comparator<AreaUi>() {

			public int compare(AreaUi pO1, AreaUi pO2) {
				final int result;
				if(pO1.getOrder() == pO2.getOrder()) {
					result = 0;
				} else if(pO1.getOrder() > pO2.getOrder()) {
					result = 1;
				} else {
					result = -1;
				}
				return result;
			}
		});
		return result;
	}

	public int getActuStart() {
		return actuStart;
	}

	public void setActuStart(int pActuStart) {
		actuStart = pActuStart;
	}

	public int getActuEnd() {
		return actuEnd;
	}

	public void setActuEnd(int pActuEnd) {
		actuEnd = pActuEnd;
	}
}