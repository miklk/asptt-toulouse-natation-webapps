package com.asptttoulousenatation.core.adherent.statistique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.asptttoulousenatation.web.creneau.CoupleValue;

@XmlRootElement
public class AdherentSexeAgeResult {

	private static final Set<String> CODE_POSTAUX_TOULOUSE;
	private static final String LOCALISATION_TOULOUSE_AUTRE = "Périphérie";
	private List<SexeAgeStatBean> sexeAges;
	private List<LocalisationStatBean> localisations;
	private List<LocalisationStatBean> localisationsToulouse;
	private List<ProfessionStatBean> professions;
	private int nbAdherents;

	private Map<Integer, Integer> ages;
	private List<CoupleValue<Integer, Integer>> ageArray;

	static {
		CODE_POSTAUX_TOULOUSE = new HashSet<>();
		CODE_POSTAUX_TOULOUSE.add("31000");
		CODE_POSTAUX_TOULOUSE.add("31100");
		CODE_POSTAUX_TOULOUSE.add("31200");
		CODE_POSTAUX_TOULOUSE.add("31300");
		CODE_POSTAUX_TOULOUSE.add("31400");
		CODE_POSTAUX_TOULOUSE.add("31500");
	}

	public AdherentSexeAgeResult() {
		sexeAges = new ArrayList<SexeAgeStatBean>();
		localisations = new ArrayList<>();
		localisationsToulouse = new ArrayList<>();
		professions = new ArrayList<>();
		ages = new HashMap<>();
	}

	public void addSexeAgeStat(SexeAgeStatBean stat) {
		sexeAges.add(stat);
	}

	public void computeLocalisationToulouse(
			Map<String, LocalisationStatBean> localisationMap) {
		Map<String, LocalisationStatBean> localisationToulouseMap = new HashMap<>();
		LocalisationStatBean autreLocalisation = new LocalisationStatBean();
		autreLocalisation.setCodepostal(LOCALISATION_TOULOUSE_AUTRE);
		localisationToulouseMap.put(LOCALISATION_TOULOUSE_AUTRE,
				autreLocalisation);
		for (Map.Entry<String, LocalisationStatBean> localisationEntry : localisationMap
				.entrySet()) {
			if (CODE_POSTAUX_TOULOUSE.contains(localisationEntry.getKey())) {
				localisationToulouseMap.put(localisationEntry.getKey(),
						localisationEntry.getValue());
			} else {
				autreLocalisation.add();
			}
		}
		localisationsToulouse.addAll(localisationToulouseMap.values());
	}

	public void addAge(Integer annee) {
		int nb = 0;
		if (ages.containsKey(annee)) {
			nb = ages.get(annee);
		}
		nb++;
		ages.put(annee, nb);
	}

	public void computeAges() {
		ageArray = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : ages.entrySet()) {
			ageArray.add(new CoupleValue<Integer, Integer>(entry.getKey(), entry
					.getValue()));
		}
		Collections.sort(ageArray, new Comparator<CoupleValue<Integer, Integer>>() {

			@Override
			public int compare(CoupleValue<Integer, Integer> pO1,
					CoupleValue<Integer, Integer> pO2) {
				return pO2.getFirst().compareTo(pO1.getFirst());
			}
			
		});
	}

	public int getNbAdherents() {
		return nbAdherents;
	}

	public void setNbAdherents(int pNbAdherents) {
		nbAdherents = pNbAdherents;
	}

	public List<SexeAgeStatBean> getSexeAges() {
		return sexeAges;
	}

	public void setSexeAges(List<SexeAgeStatBean> pSexeAges) {
		sexeAges = pSexeAges;
	}

	public List<LocalisationStatBean> getLocalisations() {
		return localisations;
	}

	public void setLocalisations(List<LocalisationStatBean> pLocalisations) {
		localisations = pLocalisations;
	}

	public List<ProfessionStatBean> getProfessions() {
		return professions;
	}

	public void setProfessions(List<ProfessionStatBean> pProfessions) {
		professions = pProfessions;
	}

	public List<LocalisationStatBean> getLocalisationsToulouse() {
		return localisationsToulouse;
	}

	public void setLocalisationsToulouse(
			List<LocalisationStatBean> pLocalisationsToulouse) {
		localisationsToulouse = pLocalisationsToulouse;
	}

	public Map<Integer, Integer> getAges() {
		return ages;
	}

	public void setAges(Map<Integer, Integer> pAges) {
		ages = pAges;
	}

	public List<CoupleValue<Integer, Integer>> getAgeArray() {
		return ageArray;
	}

	public void setAgeArray(List<CoupleValue<Integer, Integer>> pAgeArray) {
		ageArray = pAgeArray;
	}

}