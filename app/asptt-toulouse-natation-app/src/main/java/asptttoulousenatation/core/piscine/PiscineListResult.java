package com.asptttoulousenatation.core.piscine;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class PiscineListResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4956665335183200015L;
	private Set<PiscineListResultBean> piscines;
	
	public PiscineListResult() {
		piscines = new TreeSet<>(new Comparator<PiscineListResultBean>() {

			@Override
			public int compare(PiscineListResultBean pO1,
					PiscineListResultBean pO2) {
				return pO1.getIntitule().compareToIgnoreCase(pO2.getIntitule());
			}
		});
	}
	
	public void addPiscines(Collection<PiscineListResultBean> pPiscines) {
		piscines.addAll(pPiscines);
	}

	public Set<PiscineListResultBean> getPiscines() {
		return piscines;
	}

	public void setPiscines(Set<PiscineListResultBean> pPiscines) {
		piscines = pPiscines;
	}
}