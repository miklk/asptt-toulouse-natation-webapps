package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreneauListResultBean implements Serializable {

	private static final long serialVersionUID = 6233655392860850100L;
	private String jour;
	private Map<Integer, CoupleValue<String, List<CreneauResumeBean>>> creneaux;

	public CreneauListResultBean() {
		creneaux = new HashMap<Integer, CoupleValue<String, List<CreneauResumeBean>>>(0);
	}
	public void addCreneau(Integer debut, String groupe, int effectif, String day) {
		final CoupleValue<String, List<CreneauResumeBean>> groupes;
		if(creneaux.containsKey(debut)) {
			groupes = creneaux.get(debut);
		} else {
			groupes = new CoupleValue<String, List<CreneauResumeBean>>();
			groupes.setFirst(Long.toString(TimeUnit.HOURS.convert(debut, TimeUnit.MINUTES)));
			groupes.setSecond(new ArrayList<CreneauResumeBean>());
			creneaux.put(debut, groupes);
		}
		CreneauResumeBean bean = new CreneauResumeBean();
		bean.setEffectif(effectif);
		bean.setGroupe(groupe);
		bean.setJour(day);
		bean.buildId();
		groupes.getSecond().add(bean);
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String pJour) {
		jour = pJour;
	}
	public Map<Integer, CoupleValue<String, List<CreneauResumeBean>>> getCreneaux() {
		return creneaux;
	}
	public void setCreneaux(
			Map<Integer, CoupleValue<String, List<CreneauResumeBean>>> pCreneaux) {
		creneaux = pCreneaux;
	}
	


}