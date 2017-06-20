package com.asptttoulousenatation.core.adherent.statistique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.adherent.AdherentBeanTransformer;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.inscription.DossierService;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.util.DossierUtils;

@Path("/adherentsStat")
@Produces("application/json")
public class AdherentStatService {

	private static final Logger LOG = Logger
			.getLogger(AdherentStatService.class.getName());

	private DossierDao dao = new DossierDao();
	private DossierNageurDao nageurDao = new DossierNageurDao();
	private GroupDao groupDao = new GroupDao();

	@Path("/sexe-age")
	@GET
	public AdherentSexeAgeResult getSexeAge() {
		AdherentSexeAgeResult result = new AdherentSexeAgeResult();
		int currentMajeur = DateTime.now().year().get() - 18;

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(
				DossierNageurEntityFields.SAISON, DossierService.NEW_SAISON,
				Operator.EQUAL));
		List<DossierNageurEntity> adherents = nageurDao.find(criteria);
		result.setNbAdherents(adherents.size());
		SexeAgeStatBean homme = new SexeAgeStatBean();
		homme.setSexe("Homme");
		result.addSexeAgeStat(homme);
		SexeAgeStatBean femme = new SexeAgeStatBean();
		femme.setSexe("Femme");
		result.addSexeAgeStat(femme);

		Map<String, LocalisationStatBean> localisations = new HashMap<>();
		Map<String, ProfessionStatBean> professions = new HashMap<>();
		
		int currentYear = DateTime.now().year().get();
		int sumAge = 0;
		int nbAdherents = 0;
		int tranche1 = 0;
		int tranche2 = 0;

		for (DossierNageurEntity adherent : adherents) {
			DossierEntity dossier = dao.get(adherent.getDossier());
			if(dossier != null) {
				String statut = StringUtils.defaultString(dossier.getStatut(), "Pas de statut");
				result.addStatus(statut);
			}
			if(dossier != null && DossierUtils.isValid(dossier)) {
				if (StringUtils.isNotBlank(adherent.getCivilite())) {
					switch (adherent.getCivilite()) {
					case "0":
						setAge(adherent, homme, currentMajeur);
						break;
					case "1":
					case "2":
						setAge(adherent, femme, currentMajeur);
						break;
					default:
						LOG.severe("Mauvaise civilité: "
								+ adherent.getCivilite());
					}
				} else {
					LOG.severe("Pas de civilité pour " + adherent.getId());
				}

				// Localisation
				String codepostal = dossier.getCodepostal();
				if (StringUtils.isNotBlank(codepostal)
						&& StringUtils.isNumeric(codepostal)
						&& codepostal.length() == 5) {
					final LocalisationStatBean localisationStatBean;
					if (localisations.containsKey(codepostal)) {
						localisationStatBean = localisations.get(codepostal);
					} else {
						localisationStatBean = new LocalisationStatBean();
						localisationStatBean.setCodepostal(codepostal);
						localisations.put(codepostal, localisationStatBean);
					}
					localisationStatBean.add();
				}

				// Profession
				countProfession(professions, dossier, adherent, null);
				countProfession(professions, dossier, adherent, true);
				countProfession(professions, dossier, adherent, false);

				// Age
				if(adherent.getNaissance() != null) {
					int annee = DossierUtils.getDateNaissanceAsDateTime(adherent).year().get();
					result.addAge(annee);
					if(annee > 1900 && annee < currentYear) {
						int age = currentYear - annee;
						sumAge+=age;
						nbAdherents++;
						if(age >=4 && age <= 10) {
							tranche1++;
						} else if(age >= 11 && age <= 18) {
							tranche2++;
						}
					}
				} else {
					LOG.log(Level.WARNING, "Pas de naissance pour " + adherent.getId());
				}
				
				if(DossierStatutEnum.INSCRIT.name().equals(dossier.getStatut())) {
					result.addComplet();
				}
				if(BooleanUtils.isTrue(adherent.getNouveau())) {
					result.addNouveau();
				} else {
					result.addRenouvellement();
				}
			}
		}
		result.computeLocalisationToulouse(localisations);
		result.computeAges();
		result.getProfessions().addAll(professions.values());
		result.setAverageAge(sumAge / nbAdherents);
		result.setTranche1(tranche1);
		result.setTranche2(tranche2);
		return result;
	}

	private void countProfession(Map<String, ProfessionStatBean> professions, DossierEntity dossier, DossierNageurEntity pAdherent, Boolean type) {
		String csp = AdherentSexeAgeResult.getCsp(dossier, pAdherent, type);
		if (StringUtils.isNotBlank(csp)) {
			final ProfessionStatBean professionStatBean;
			if (professions.containsKey(csp)) {
				professionStatBean = professions.get(csp);
			} else {
				professionStatBean = new ProfessionStatBean();
				professionStatBean.setProfession(csp);
				professions.put(csp, professionStatBean);
			}
			professionStatBean.add();
		}
	}

	private void setAge(DossierNageurEntity adherent, SexeAgeStatBean sexe, int yearMajeur) {
		if (adherent.getNaissance() == null) {
			LOG.log(Level.WARNING, "Pas de naissance pour " + adherent.getId());
		} else {
			int annee = DossierUtils.getDateNaissanceAsDateTime(adherent).year().get();
			if (annee < yearMajeur) {
				sexe.addMineur();
			} else {
				sexe.addMajeur();
			}
		}
	}

	@Path("/familles")
	@GET
	public AdherentFamilleStatResult getFamilles() {
		AdherentFamilleStatResult result = new AdherentFamilleStatResult();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(
				DossierEntityFields.SAISON, DossierService.NEW_SAISON,
				Operator.EQUAL));
		List<DossierEntity> dossiers = dao.find(criteria);
		for (DossierEntity dossier : dossiers) {
			criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(
					DossierNageurEntityFields.DOSSIER, dossier.getId(),
					Operator.EQUAL));
			List<DossierNageurEntity> enfants = nageurDao.find(criteria);
			if(CollectionUtils.isNotEmpty(enfants) && enfants.size() > 1) {//Uniquement familles
				result.addFamille();
				Set<String> piscines = new HashSet<>();
				Set<String> groupes = new HashSet<>(); 
				for(DossierNageurEntity enfant: enfants) {
					if(enfant.getGroupe() != null) {
						GroupEntity groupEntity = groupDao.get(enfant.getGroupe());
						groupes.add(groupEntity.getTitle());
						Set<SlotUi> creneaux = AdherentBeanTransformer.getInstance().getCreneaux(enfant.getCreneaux());
						for(SlotUi creneau: creneaux) {
							piscines.add(creneau.getSwimmingPool());
						}
					}
				}
				for(String piscine: piscines) {
					result.addPiscine(piscine);
				}
				for(String groupe: groupes) {
					result.addGroupe(groupe);
				}
			}
		}
		result.toList();

		return result;
	}
}