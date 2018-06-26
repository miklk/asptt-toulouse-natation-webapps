package com.asptt.core.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.asptt.core.lang.HasDay;

public class DayComparator implements Comparator<HasDay> {

	private static Map<String, Integer> JOURS;

	static {
		JOURS = new HashMap<String, Integer>();
		JOURS.put("Lundi", 0);
		JOURS.put("Mardi", 1);
		JOURS.put("Mercredi", 2);
		JOURS.put("Jeudi", 3);
		JOURS.put("Vendredi", 4);
		JOURS.put("Samedi", 5);
		JOURS.put("Dimanche", 6);
	}

	@Override
	public int compare(HasDay o1, HasDay o2) {
		Integer jour1 = JOURS.get(o1.getDayOfWeek());
		Integer jour2 = JOURS.get(o2.getDayOfWeek());
		return jour1.compareTo(jour2);
	}

}
