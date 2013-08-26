package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreneauListResultBean implements Serializable {

	private static final long serialVersionUID = 6233655392860850100L;
	private String jour;
	private Map<Integer, CreneauResumeBean> creneaux;

	public CreneauListResultBean() {
		creneaux = new HashMap<Integer, CreneauResumeBean>(0);
	}
	public void addCreneau(CreneauResumeBean creneau) {
		final CreneauResumeBean currentCreneau;
		if(creneaux.containsKey(creneau.getDebut())) {
			currentCreneau = creneaux.get(creneau.getDebut());
		} else {
			currentCreneau = creneau;
			creneaux.put(creneau.getDebut(), currentCreneau);
		}
		currentCreneau.addGroupe(nom, effectif)
		creneaux.add(creneau);
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String pJour) {
		jour = pJour;
	}

	public List<CreneauResumeBean> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<CreneauResumeBean> pCreneaux) {
		creneaux = pCreneaux;
	}
}