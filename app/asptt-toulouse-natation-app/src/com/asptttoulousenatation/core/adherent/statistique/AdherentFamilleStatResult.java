package com.asptttoulousenatation.core.adherent.statistique;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.web.creneau.CoupleValue;

public class AdherentFamilleStatResult {

	private int nbFamilles;
	private Map<String, Integer> piscines;
	private Map<String, Integer> groupes;
	private Collection<CoupleValue<String, Integer>> piscineArray;
	private Collection<CoupleValue<String, Integer>> groupeArray;

	public AdherentFamilleStatResult() {
		piscines = new HashMap<String, Integer>();
		groupes = new HashMap<String, Integer>();
	}
	
	public void addFamille() {
		nbFamilles++;
	}

	public void addPiscine(String piscine) {
		if (StringUtils.isNotBlank(piscine)) {
			Integer nb = 0;
			if (piscines.containsKey(piscine)) {
				nb = piscines.get(piscine);
			}
			nb++;
			piscines.put(piscine, nb);
		}
	}

	public void addGroupe(String groupe) {
		int nb = 0;
		if (groupes.containsKey(groupe)) {
			nb = groupes.get(groupe);
		}
		nb++;
		groupes.put(groupe, nb);
	}

	public void toList() {
		piscineArray = new HashSet<>();
		for (Map.Entry<String, Integer> piscine : piscines.entrySet()) {
			piscineArray.add(new CoupleValue<>(piscine.getKey(), piscine
					.getValue()));
		}

		groupeArray = new HashSet<>();
		for (Map.Entry<String, Integer> groupe : groupes.entrySet()) {
			groupeArray.add(new CoupleValue<>(groupe.getKey(), groupe
					.getValue()));
		}
	}

	public Map<String, Integer> getPiscines() {
		return piscines;
	}

	public void setPiscines(Map<String, Integer> pPiscines) {
		piscines = pPiscines;
	}

	public Map<String, Integer> getGroupes() {
		return groupes;
	}

	public void setGroupes(Map<String, Integer> pGroupes) {
		groupes = pGroupes;
	}

	public Collection<CoupleValue<String, Integer>> getPiscineArray() {
		return piscineArray;
	}

	public void setPiscineArray(
			Collection<CoupleValue<String, Integer>> pPiscineArray) {
		piscineArray = pPiscineArray;
	}

	public Collection<CoupleValue<String, Integer>> getGroupeArray() {
		return groupeArray;
	}

	public void setGroupeArray(
			Collection<CoupleValue<String, Integer>> pGroupeArray) {
		groupeArray = pGroupeArray;
	}

	public int getNbFamilles() {
		return nbFamilles;
	}

	public void setNbFamilles(int pNbFamilles) {
		nbFamilles = pNbFamilles;
	}

}