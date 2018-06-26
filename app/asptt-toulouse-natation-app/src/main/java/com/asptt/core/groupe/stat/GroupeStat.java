package com.asptt.core.groupe.stat;

import java.util.ArrayList;
import java.util.List;

public class GroupeStat extends StatBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CreneauStat> creneaux;

	public void addCreneau(CreneauStat creneau) {
		addCapacite(creneau.getCapacite());
		addDisponibles(creneau.getDisponibles());
		
		if(creneaux == null) {
			creneaux = new ArrayList<CreneauStat>();
		}
		creneaux.add(creneau);
	}

	public List<CreneauStat> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<CreneauStat> creneaux) {
		this.creneaux = creneaux;
	}
	

}
