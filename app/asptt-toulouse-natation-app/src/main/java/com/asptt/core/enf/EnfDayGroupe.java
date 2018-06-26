package com.asptt.core.enf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asptt.core.lang.HasDay;

public class EnfDayGroupe implements HasDay, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dayOfWeek;
	private List<EnfGroupeCreneau> groupes;
	private Map<String, EnfGroupeCreneau> groupesMap;
	
	public EnfDayGroupe() {
		groupes = new ArrayList<>();
		groupesMap = new HashMap<>();
	}
	
	public void addGroupe(EnfGroupeCreneau groupe) {
		if(groupesMap.containsKey(groupe.getGroupe())) {
			groupesMap.get(groupe.getGroupe()).getCreneaux().addAll(groupe.getCreneaux());
		} else {
			groupesMap.put(groupe.getGroupe(), groupe);
			groupes.add(groupe);
		}
		
	}
	
	public void sort() {
		Collections.sort(groupes, new Comparator<EnfGroupeCreneau>() {

			@Override
			public int compare(EnfGroupeCreneau o1, EnfGroupeCreneau o2) {
				return o1.getGroupe().compareTo(o2.getGroupe());
			}
		});
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public List<EnfGroupeCreneau> getGroupes() {
		return groupes;
	}
	public void setGroupes(List<EnfGroupeCreneau> groupes) {
		this.groupes = groupes;
	}

	public Map<String, EnfGroupeCreneau> getGroupesMap() {
		return groupesMap;
	}

	public void setGroupesMap(Map<String, EnfGroupeCreneau> groupesMap) {
		this.groupesMap = groupesMap;
	}

}
