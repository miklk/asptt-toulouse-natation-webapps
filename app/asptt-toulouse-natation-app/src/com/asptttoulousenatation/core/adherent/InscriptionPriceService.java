package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;

@Path("/price")
@Produces("application/json")
@Consumes("application/json")
public class InscriptionPriceService {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private Inscription2Dao dao = new Inscription2Dao();
	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();

	@POST
	public InscriptionPriceServiceResult price(InscriptionDossiersUi pDossiers) {
		InscriptionDossierUi principal = pDossiers.getPrincipal();
		buildDossier(principal);
		dao.save(principal.getDossier());

		int dossierCount = 0;
		List<InscriptionDossierUi> dossiers = new ArrayList<InscriptionDossierUi>(
				CollectionUtils.select(pDossiers.getDossiers(),
						new Predicate() {

							@Override
							public boolean evaluate(Object pArg0) {
								return !((InscriptionDossierUi) pArg0)
										.isSupprimer();
							}
						}));
		Collections.sort(dossiers, new Comparator<InscriptionDossierUi>() {

			@Override
			public int compare(InscriptionDossierUi pO1,
					InscriptionDossierUi pO2) {
				return pO1.getGroupe().getTarifWeight() >= pO2.getGroupe()
						.getTarifWeight() ? 1 : -1;
			}
		});
		
		InscriptionPriceServiceResult result = new InscriptionPriceServiceResult();
		Set<String> prices = new HashSet<String>();
		for (InscriptionDossierUi dossier : dossiers) {
			dossierCount++;
			// Recherche du tarif
			GroupEntity group = groupDao.get(dossier.getGroupe().getId());
			int tarif = 0; 
			switch (dossierCount) {
			case 1:
				tarif = GroupEntity.getTarif(group.getTarif());
				break;
			case 2:
				tarif = GroupEntity.getTarif(group.getTarif2());
				break;
			case 3:
				tarif = GroupEntity.getTarif(group.getTarif3());
				break;
			case 4:
				tarif = GroupEntity.getTarif(group.getTarif4());
				break;
			default:
				tarif = GroupEntity.getTarif(group.getTarif4());
			}
			pDossiers.addPrice(tarif);
			prices.add(tarif + " ("+group.getTitle()+")");

			// Enregistrement du dossier
			if (dossier.getDossier().getPrincipal() != null) {
				dossier.getDossier().setPrincipal(
						principal.getDossier().getId());

				buildDossier(dossier);
				dao.save(dossier.getDossier());
			} else {//C'est le principal
				// Copier les données du principal dans ce dossier
				buildDossier(dossier);
				principal.getDossier().copy(dossier.getDossier());
				dao.save(dossier.getDossier());
			}
		}
		StrBuilder pricesStr = new StrBuilder();
		result.setPrice(pricesStr.appendWithSeparators(prices, " + ").toString());

		//Reload dossiers
		int price = pDossiers.getPrice();
		result.setDossiers(getDossiers(principal.getDossier()));
		result.getDossiers().setPrice(price);
		
		return result;
	}

	private void buildDossier(InscriptionDossierUi pDossier) {
		
		if (pDossier.getGroupe() != null) {
			pDossier.getDossier()
					.setNouveauGroupe(pDossier.getGroupe().getId());
		}

		if (pDossier.getCreneaux() != null) {
			Set<Long> creneaux = new HashSet<Long>();
			for (LoadingSlotUi slot : pDossier.getCreneaux().getSlots()) {
				buildCreneau(creneaux, slot.getLundi(), pDossier.getDossier()
						.getCreneaux());
				buildCreneau(creneaux, slot.getMardi(), pDossier.getDossier()
						.getCreneaux());
				buildCreneau(creneaux, slot.getMercredi(), pDossier
						.getDossier().getCreneaux());
				buildCreneau(creneaux, slot.getJeudi(), pDossier.getDossier()
						.getCreneaux());
				buildCreneau(creneaux, slot.getVendredi(), pDossier
						.getDossier().getCreneaux());
				buildCreneau(creneaux, slot.getSamedi(), pDossier.getDossier()
						.getCreneaux());
			}
			StrBuilder creneauxAsString = new StrBuilder();
			creneauxAsString.appendWithSeparators(creneaux, ";");
			pDossier.getDossier().setCreneaux(creneauxAsString.toString());
		}
		//Upper case
		InscriptionEntity2 adherent = pDossier.getDossier();
		adherent.setAccidentNom1(StringUtils.upperCase(adherent.getAccidentNom1()));
		adherent.setAccidentNom2(StringUtils.upperCase(adherent.getAccidentNom2()));
		adherent.setAccidentPrenom1(StringUtils.upperCase(adherent.getAccidentPrenom1()));
		adherent.setAccidentPrenom2(StringUtils.upperCase(adherent.getAccidentPrenom2()));
		adherent.setAccordNomPrenom(StringUtils.upperCase(adherent.getAccordNomPrenom()));
		adherent.setAdresse(StringUtils.upperCase(adherent.getAdresse()));
		adherent.setNom(StringUtils.upperCase(adherent.getNom()));
		adherent.setPrenom(StringUtils.upperCase(adherent.getPrenom()));
		adherent.setProfession(StringUtils.upperCase(adherent.getProfession()));
		adherent.setProfessionTextMere(StringUtils.upperCase(adherent.getProfessionTextMere()));
		adherent.setProfessionTextPere(StringUtils.upperCase(adherent.getProfessionTextPere()));
		adherent.setShortPantalon(StringUtils.upperCase(adherent.getShortPantalon()));
		adherent.setTshirt(StringUtils.upperCase(adherent.getTshirt()));
		adherent.setVille(StringUtils.upperCase(adherent.getVille()));
	}
	
	private InscriptionDossiersUi getDossiers(InscriptionEntity2 pPrincipal) {
		InscriptionDossiersUi dossiers = new InscriptionDossiersUi();
		InscriptionEntity2 adherent = dao.get(pPrincipal.getId());
		dossiers.setPrincipal(new InscriptionDossierUi(adherent));
		dossiers.addDossier(new InscriptionDossierUi(adherent));

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(InscriptionEntityFields.PRINCIPAL,
				adherent.getId(), Operator.EQUAL));
		List<InscriptionEntity2> entities = dao.find(criteria);

		dossiers.addDossier(entities);

		// Build UI dossier
		for (InscriptionDossierUi dossier : dossiers.getDossiers()) {
			adherent = dossier.getDossier();

			// Get nouveau groupe et vérifie si on peut changer
			if (adherent.getNouveauGroupe() != null) {
				GroupEntity group = groupDao.get(adherent.getNouveauGroupe());
				dossier.setChoix(group.getInscription());
				dossier.setGroupe(groupTransformer.toUi(group));
			} else {
				dossier.setChoix(true);
			}
			dossier.setCreneauSelected(StringUtils.isNotBlank(adherent
					.getCreneaux()));
		}
		return dossiers;
	}
	
	private void buildCreneau(Set<Long> pCreneaux, List<SlotUi> pSlots,
			String pOldCreneaux) {
		for (SlotUi slot : pSlots) {
			if (slot.isSelected()) {
				pCreneaux.add(slot.getId());
			}
		}
	}
}