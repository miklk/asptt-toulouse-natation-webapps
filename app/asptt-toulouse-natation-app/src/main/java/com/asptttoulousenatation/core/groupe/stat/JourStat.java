package com.asptttoulousenatation.core.groupe.stat;

import java.util.ArrayList;
import java.util.List;

public class JourStat extends StatBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<StatBase> groupes;

	public void addGroupe(StatBase groupe) {
		addCapacite(groupe.getCapacite());
		addDisponibles(groupe.getDisponibles());
		
		if(groupes == null) {
			groupes = new ArrayList<StatBase>();
		}
		groupes.add(groupe);
	}

	public List<StatBase> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<StatBase> groupes) {
		this.groupes = groupes;
	}
}