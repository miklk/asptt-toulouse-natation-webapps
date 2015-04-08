package com.asptttoulousenatation.core.adherent.statistique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.adherent.AdherentBeanTransformer;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/adherentsStat")
@Produces("application/json")
public class AdherentStatService {

	private static final Logger LOG = Logger
			.getLogger(AdherentStatService.class.getName());

	private Inscription2Dao dao = new Inscription2Dao();
	

	@Path("/sexe-age")
	@GET
	public AdherentSexeAgeResult getSexeAge() {
		AdherentSexeAgeResult result = new AdherentSexeAgeResult();
		int currentMajeur = DateTime.now().year().get() - 18;

		List<InscriptionEntity2> adherents = dao.getAll();
		result.setNbAdherents(adherents.size());
		SexeAgeStatBean homme = new SexeAgeStatBean();
		homme.setSexe("Homme");
		result.addSexeAgeStat(homme);
		SexeAgeStatBean femme = new SexeAgeStatBean();
		femme.setSexe("Femme");
		result.addSexeAgeStat(femme);
		
		Map<String, LocalisationStatBean> localisations = new HashMap<>();
		Map<String, ProfessionStatBean> professions = new HashMap<>();

		for (InscriptionEntity2 adherent : adherents) {
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
					LOG.severe("Mauvaise civilité: " + adherent.getCivilite());
				}
			} else {
				LOG.severe("Pas de civilité pour " + adherent.getId());
			}
			
			//Localisation
			String codepostal = adherent.getCodepostal();
			if(StringUtils.isNotBlank(codepostal) && StringUtils.isNumeric(codepostal) && codepostal.length() == 5) {
				final LocalisationStatBean localisationStatBean;
				if(localisations.containsKey(codepostal)) {
					localisationStatBean = localisations.get(codepostal);
				} else {
					localisationStatBean = new LocalisationStatBean();
					localisationStatBean.setCodepostal(codepostal);
					localisations.put(codepostal, localisationStatBean);
				}
				localisationStatBean.add();
			}
			
			//Profession
			countProfession(professions, adherent.getProfession());
			countProfession(professions, adherent.getProfessionTextPere());
			countProfession(professions, adherent.getProfessionTextMere());
			
			//Age
			String anneeNaissance = getAnneeNaissance(adherent);
			if (StringUtils.isNumeric(anneeNaissance)) {
				result.addAge(Integer.valueOf(anneeNaissance));
			}
		}
		result.computeLocalisationToulouse(localisations);
		result.computeAges();
		result.getLocalisations().addAll(localisations.values());
		result.getProfessions().addAll(professions.values());
		return result;
	}

	private void countProfession(Map<String, ProfessionStatBean> professions,
			String profession) {
		if(StringUtils.isNotBlank(profession)) {
			final ProfessionStatBean professionStatBean;
			if(professions.containsKey(profession.toLowerCase())) {
				professionStatBean = professions.get(profession.toLowerCase());
			} else {
				professionStatBean = new ProfessionStatBean();
				professionStatBean.setProfession(profession.toLowerCase());
				professions.put(profession.toLowerCase(), professionStatBean);
			}
			professionStatBean.add();
		}
	}

	private void setAge(InscriptionEntity2 adherent, SexeAgeStatBean sexe,
			int yearMajeur) {
		String anneeAsString = getAnneeNaissance(adherent);
		if (StringUtils.isNumeric(anneeAsString)) {
			int annee = Integer.valueOf(anneeAsString);
			if (annee < yearMajeur) {
				sexe.addMineur();
			} else {
				sexe.addMajeur();
			}
		}
	}

	private String getAnneeNaissance(InscriptionEntity2 adherent) {
		String anneeAsString = StringUtils.substring(
				adherent.getDatenaissance(), 0, 4);
		return anneeAsString;
	}

	@Path("/familles")
	@GET
	public AdherentFamilleStatResult getFamilles() {
		AdherentFamilleStatResult result = new AdherentFamilleStatResult();

		Collection<Long> famillesId = dao.getPrincipal();
		List<FamilleBean> familles = new ArrayList<FamilleBean>(
				famillesId.size());
		for (Long parentId : famillesId) {
			try {
				FamilleBean famille = new FamilleBean();
				InscriptionEntity2 parent = dao.get(parentId);
				if (parent != null) {
					famille.setParent(AdherentBeanTransformer.getInstance()
							.get(parent));
					List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
							1);
					criteria.add(new CriterionDao<Long>(
							InscriptionEntityFields.PRINCIPAL, parentId,
							Operator.EQUAL));
					List<InscriptionEntity2> enfants = dao.find(criteria);
					if (CollectionUtils.isNotEmpty(enfants)) {
						famille.setEnfants(AdherentBeanTransformer
								.getInstance().get(enfants));
						result.addPiscine(famille);
						result.addGroupe(famille);
						familles.add(famille);
					}
				} else {
					LOG.warning("No parent with id " + parentId);
				}
			} catch (Exception e) {
				LOG.severe(e.getMessage());
				LOG.log(Level.SEVERE, e.getMessage(), e);

			}
		}
		result.setNbFamilles(familles.size());
		result.toList();

		return result;
	}
	
	@Path("/annees")
	@GET
	public Collection<String> getAnnees() {
		return dao.getDateNaissances();
	}
}