package com.asptttoulousenatation.web.creneau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;

public class CreneauListResultBean implements Serializable {

	private static final long serialVersionUID = 6233655392860850100L;
	private String jour;
	private List<String> creneauHeures;
	private Map<String, CoupleValue<String, List<CreneauResumeBean>>> creneaux;

	public CreneauListResultBean() {
		creneauHeures = new ArrayList<String>();
		creneaux = new HashMap<String, CoupleValue<String, List<CreneauResumeBean>>>(0);
	}
	
	public void addHoraire(Integer debut) {
		String creneauDebut = debut / 60 + "h" + StringUtils.rightPad(debut % 60 + "", 2, "0");
		if(!creneauHeures.contains(creneauDebut)) {
			creneauHeures.add(creneauDebut);
		}
	}
	
	public void addCreneau(Integer debut, String groupe, int effectif, String day, SlotEntity pEntity) {
		String creneauDebut = debut / 60 + "h" + StringUtils.rightPad(debut % 60 + "", 2, "0");
		final CoupleValue<String, List<CreneauResumeBean>> groupes;
		if(creneaux.containsKey(groupe)) {
			groupes = creneaux.get(groupe);
		} else {
			groupes = new CoupleValue<String, List<CreneauResumeBean>>();
			groupes.setFirst(groupe);
			groupes.setSecond(initCreneaux(groupe));
			creneaux.put(groupe, groupes);
		}
		CreneauResumeBean bean = groupes.getSecond().get(creneauHeures.indexOf(creneauDebut));
		bean.setExists(true);
		bean.setEffectif(effectif);
		if(pEntity.getPlaceDisponible() != null) {
			bean.setTotal(pEntity.getPlaceDisponible());
		}
		if(pEntity.getPlaceRestante() != null) {
			bean.setComplet(pEntity.getPlaceRestante() == 0);
		}
		bean.setHeure(pEntity.getBegin() / 60 + "h" + StringUtils.rightPad(pEntity.getBegin() % 60 + "", 2, "0"));
	}
	
	private List<CreneauResumeBean> initCreneaux(String groupe) {
		List<CreneauResumeBean> groupeCreneaux = new ArrayList<CreneauResumeBean>(creneauHeures.size());
		Iterator<String> it = creneauHeures.listIterator();
		while(it.hasNext()) {
			CreneauResumeBean bean = new CreneauResumeBean();
			bean.setHeure(it.next());
			groupeCreneaux.add(bean);
		}
		return groupeCreneaux;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String pJour) {
		jour = pJour;
	}

	public List<String> getCreneauHeures() {
		return creneauHeures;
	}

	public void setCreneauHeures(List<String> pCreneauHeures) {
		creneauHeures = pCreneauHeures;
	}

	public Map<String, CoupleValue<String, List<CreneauResumeBean>>> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(
			Map<String, CoupleValue<String, List<CreneauResumeBean>>> pCreneaux) {
		creneaux = pCreneaux;
	}
}