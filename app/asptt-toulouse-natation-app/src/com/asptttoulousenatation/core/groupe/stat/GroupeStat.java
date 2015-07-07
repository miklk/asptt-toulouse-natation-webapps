package com.asptttoulousenatation.core.groupe.stat;

import java.util.ArrayList;
import java.util.List;

public class GroupeStat extends StatBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<StatBase> creneaux;

	public void addCreneau(StatBase creneau) {
		addCapacite(creneau.getCapacite());
		addDisponibles(creneau.getDisponibles());
		
		if(creneaux == null) {
			creneaux = new ArrayList<StatBase>();
		}
		creneaux.add(creneau);
	}

	public List<StatBase> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<StatBase> creneaux) {
		this.creneaux = creneaux;
	}
	

}
