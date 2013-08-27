package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreneauListResultBean implements Serializable {

	private static final long serialVersionUID = 6233655392860850100L;
	private String jour;
	private Map<Integer, List<CoupleValue<String, Integer>>> creneaux;

	public CreneauListResultBean() {
		creneaux = new HashMap<Integer, List<CoupleValue<String, Integer>>>(0);
	}
	public void addCreneau(Integer debut, String groupe, int effectif) {
		final List<CoupleValue<String, Integer>> groupes;
		if(creneaux.containsKey(debut)) {
			groupes = creneaux.get(debut);
		} else {
			groupes = new ArrayList<CoupleValue<String, Integer>>();
			creneaux.put(debut, groupes);
		}
		groupes.add(new CoupleValue<String, Integer>(groupe, effectif));
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String pJour) {
		jour = pJour;
	}
	
	public Map<Integer, List<CoupleValue<String, Integer>>> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(
			Map<Integer, List<CoupleValue<String, Integer>>> pCreneaux) {
		creneaux = pCreneaux;
	}
}