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

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.web.creneau.CoupleValue;

@XmlRootElement
public class AdherentSexeAgeResult {

	private static final Set<String> CODE_POSTAUX_TOULOUSE;
	private static final String LOCALISATION_TOULOUSE_AUTRE = "Périphérie";
	public static final Map<String, String> CSP;
	private List<SexeAgeStatBean> sexeAges;
	private List<LocalisationStatBean> localisationsToulouse;
	private List<ProfessionStatBean> professions;
	private int nbAdherents;
	private int complets;
	private int nouveau;
	private int renouvellement;

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

		CSP = new HashMap<>();
		CSP.put("", "Scolaire");
		CSP.put("", "Agriculteurs exploitants");
		CSP.put("", "Artisans, commerçants et chefs d'entreprise");
		CSP.put("resp. qualité", "Cadres et professions intellectuelles supérieures");
		CSP.put("attaché territorial", "Cadres et professions intellectuelles supérieures");
		CSP.put("chanteuse professeur de chant", "Cadres et professions intellectuelles supérieures");
		CSP.put("directrice i.e.m", "Cadres et professions intellectuelles supérieures");
		CSP.put("business development manager", "Cadres et professions intellectuelles supérieures");
		CSP.put("directrice association", "Cadres et professions intellectuelles supérieures");
		CSP.put("cadre administratif", "Cadres et professions intellectuelles supérieures");
		CSP.put("economiste de la construction", "Cadres et professions intellectuelles supérieures");
		
		CSP.put("infirmière", "Professions Intermédiaires");
		CSP.put("informatitien", "Professions Intermédiaires");
		CSP.put("éducatrice spécialisée", "Professions Intermédiaires");
		CSP.put("cadreur video", "Professions Intermédiaires");
		
		CSP.put("gestionnaire comptable", "Employés");
		CSP.put("aide menagère", "Employés");
		CSP.put("aide-soignante", "Employés");
		CSP.put("employé", "Employés");
		CSP.put("intendante", "Employés");
		CSP.put("interimaire", "Employés");
		CSP.put("chargée de veille", "Employés");
		CSP.put("employée la poste", "Employés");
		
		CSP.put("luthier", "Ouvriers");
		CSP.put("", "Retraités");
		CSP.put("chomeur", "Autres personnes sans activité professionnelle");
	}

	public AdherentSexeAgeResult() {
		sexeAges = new ArrayList<SexeAgeStatBean>();
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
			ageArray.add(new CoupleValue<Integer, Integer>(entry.getKey(),
					entry.getValue()));
		}
		Collections.sort(ageArray,
				new Comparator<CoupleValue<Integer, Integer>>() {

					@Override
					public int compare(CoupleValue<Integer, Integer> pO1,
							CoupleValue<Integer, Integer> pO2) {
						return pO2.getFirst().compareTo(pO1.getFirst());
					}

				});
	}

	public void addComplet() {
		complets++;
	}

	public void addNouveau() {
		nouveau++;
	}

	public void addRenouvellement() {
		renouvellement++;
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

	public int getComplets() {
		return complets;
	}

	public void setComplets(int pComplets) {
		complets = pComplets;
	}

	public int getNouveau() {
		return nouveau;
	}

	public void setNouveau(int pNouveau) {
		nouveau = pNouveau;
	}

	public int getRenouvellement() {
		return renouvellement;
	}

	public void setRenouvellement(int pRenouvellement) {
		renouvellement = pRenouvellement;
	}

	public static String getCsp(InscriptionEntity2 pAdherent, Boolean type) {
		final String csp;
		if (StringUtils.isNotBlank(pAdherent.getCsp())) {
			csp = pAdherent.getCsp();
		} else {
			final String profession;
			if (type == null) {
				profession = pAdherent.getProfession();
			} else if (type) {
				profession = pAdherent.getProfessionTextMere();
			} else {
				profession = pAdherent.getProfessionTextPere();
			}
			if (StringUtils.isNotBlank(profession) && CSP.containsKey(profession.trim())) {
				csp = CSP.get(profession);
			} else {
				csp = "";
			}
		}
		return csp;
	}
}