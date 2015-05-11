package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

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

@Path("/ajouterEnfant")
@Produces("application/json")
@Consumes("application/json")
public class AjouterEnfantAdherentService {

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private Inscription2Dao dao = new Inscription2Dao();
	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public AjouterEnfantAdherentServiceResult ajouterEnfant(
			InscriptionDossiersUi pDossiers) {
		// Enregistre tous les dossiers
		buildDossier(pDossiers.getPrincipal());
		dao.save(pDossiers.getPrincipal().getDossier());
		for (InscriptionDossierUi dossier : pDossiers.getDossiers()) {
			if (!dossier.isSupprimer()) {
				buildDossier(dossier);
				if (dossier.getDossier().getPrincipal() == null) {// C'est le
																	// principal
					// Copier les données du principal dans ce dossier

					pDossiers.getPrincipal().getDossier()
							.copy(dossier.getDossier());
				}
				dao.save(dossier.getDossier());
			}
		}

		InscriptionEntity2 enfant = new InscriptionEntity2();
		enfant.setPrincipal(pDossiers.getPrincipal().getDossier().getId());
		enfant.setNouveau(true);
		InscriptionDossierUi dossier = new InscriptionDossierUi(enfant);
		dossier.setChoix(true);
		pDossiers.addDossier(dossier);
		Long idNouveau = dao.save(dossier.getDossier()).getId();

		InscriptionDossiersUi dossiersUpdated = getDossiers(pDossiers
				.getPrincipal().getDossier());
		AjouterEnfantAdherentServiceResult result = new AjouterEnfantAdherentServiceResult();
		result.setDossiers(dossiersUpdated);
		// Recherche de la position du nouveau
		int index = 0;
		boolean trouve = false;
		Iterator<InscriptionDossierUi> it = dossiersUpdated.getDossiers()
				.iterator();
		while (!trouve && it.hasNext()) {
			if (it.next().getDossier().getId() == idNouveau) {
				trouve = true;
			} else {
				index++;
			}

		}
		result.setCurrentIndex(index);

		return result;
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
			// Set date naissance
			if (StringUtils.isNotBlank(dossier.getDossier().getDatenaissance())) {
				dossier.getDossier().setNaissance(LocalDate.parse(dossier.getDossier().getDatenaissance(), DateTimeFormat.forPattern("yyyy-MM-dd")).toDate());
			}

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

	private void buildDossier(InscriptionDossierUi pDossier) {
		pDossier.getDossier().setSaisie(true);
		pDossier.getDossier().setInscriptiondt(new Date());
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