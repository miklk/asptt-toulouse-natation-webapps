package com.asptttoulousenatation.core.adherent.statistique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.adherent.AdherentBean;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.web.creneau.CoupleValue;

public class FamilleBean {

	private AdherentBean parent;
	private List<AdherentBean> enfants;
	private List<SlotUi> creneaux;

	public void addEnfant(AdherentBean enfant) {
		if (enfants == null) {
			enfants = new ArrayList<>(5);
		}
		enfants.add(enfant);
	}

	public void addCreneaux(Collection<SlotUi> creneauxToAdd) {
		if (creneaux == null) {
			creneaux = new ArrayList<>();
		}
		creneaux.addAll(creneauxToAdd);
	}

	public String getPiscine() {
		Map<String, Integer> piscines = new HashMap<>();
		buildPiscine(piscines, parent);
		for (AdherentBean adherent : enfants) {
			buildPiscine(piscines, adherent);
		}
		CoupleValue<String, Integer> piscine = new CoupleValue<String, Integer>(
				StringUtils.EMPTY, 0);
		for (Map.Entry<String, Integer> piscineEntry : piscines.entrySet()) {
			if (piscineEntry.getValue() > piscine.getSecond()) {
				piscine.setFirst(piscineEntry.getKey());
				piscine.setSecond(piscineEntry.getValue());
			}
		}
		return piscine.getFirst();
	}

	private void buildPiscine(Map<String, Integer> piscines,
			AdherentBean adherent) {
		for (SlotUi creneau : adherent.getCreneaux()) {
			if (!creneau.isSecond()) {
				int nb = 0;
				if (piscines.containsKey(creneau.getSwimmingPool())) {
					nb = piscines.get(creneau.getSwimmingPool());
				}
				nb++;
				piscines.put(creneau.getSwimmingPool(), nb);
			}
		}
	}

	public Map<String, Integer> countGroupes() {
		Map<String, Integer> groupes = new HashMap<String, Integer>();
		groupes.put(parent.getGroupe().getTitle(), 1);
		for (AdherentBean adherent : enfants) {
			int nb = 0;
			if (adherent.getGroupe() != null) {
				if (groupes.containsKey(adherent.getGroupe().getTitle())) {
					nb = groupes.get(adherent.getGroupe().getTitle());
				}
				nb++;
				groupes.put(adherent.getGroupe().getTitle(), nb);
			}
		}
		return groupes;
	}

	public AdherentBean getParent() {
		return parent;
	}

	public void setParent(AdherentBean pParent) {
		parent = pParent;
	}

	public List<AdherentBean> getEnfants() {
		return enfants;
	}

	public void setEnfants(List<AdherentBean> pEnfants) {
		enfants = pEnfants;
	}

	public List<SlotUi> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<SlotUi> pCreneaux) {
		creneaux = pCreneaux;
	}
}