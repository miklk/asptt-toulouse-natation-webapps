package com.asptttoulousenatation.core.adherent.statistique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

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

	@Path("/sexe-age")
	@GET
	public AdherentSexeAgeResult getSexeAge() {
		AdherentSexeAgeResult result = new AdherentSexeAgeResult();
		int currentMajeur = DateTime.now().year().get() - 18;

		List<DossierNageurEntity> adherents = nageurDao.getAll();
		result.setNbAdherents(adherents.size());
		SexeAgeStatBean homme = new SexeAgeStatBean();
		homme.setSexe("Homme");
		result.addSexeAgeStat(homme);
		SexeAgeStatBean femme = new SexeAgeStatBean();
		femme.setSexe("Femme");
		result.addSexeAgeStat(femme);

		Map<String, LocalisationStatBean> localisations = new HashMap<>();
		Map<String, ProfessionStatBean> professions = new HashMap<>();

		for (DossierNageurEntity adherent : adherents) {
			DossierEntity dossier = dao.get(adherent.getDossier());
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
					result.addAge(DossierUtils.getDateNaissanceAsDateTime(adherent).year().get());
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

		List<DossierEntity> dossiers = dao.getAll();
		List<FamilleBean> familles = new ArrayList<FamilleBean>(
				dossiers.size());
		for (DossierEntity dossier : dossiers) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(
					DossierNageurEntityFields.DOSSIER, dossier.getId(),
					Operator.EQUAL));
			List<DossierNageurEntity> enfants = nageurDao.find(criteria);
			if(CollectionUtils.isNotEmpty(enfants) && enfants.size() > 1) {//Uniquement familles
				try {
					FamilleBean famille = new FamilleBean();
							/**famille.setEnfants(AdherentBeanTransformer
									.getInstance().get(enfants));
							result.addPiscine(famille);
							result.addGroupe(famille);**/
							familles.add(famille);
				} catch (Exception e) {
					LOG.severe(e.getMessage());
					LOG.log(Level.SEVERE, e.getMessage(), e);
	
				}
			}
		}
		result.setNbFamilles(familles.size());
		result.toList();

		return result;
	}
}