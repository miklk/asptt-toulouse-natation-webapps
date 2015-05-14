package com.asptttoulousenatation.core.adherent;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;

@Path("/inscrire")
@Consumes("application/json")
public class InscriptionService {
	
	private static final Logger LOG = Logger.getLogger(InscriptionService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private Inscription2Dao dao = new Inscription2Dao();
	private SlotDao slotDao = new SlotDao();

	@POST
	public String inscrire(InscriptionDossiersUi pDossiers) {
		InscriptionDossierUi principal = pDossiers.getPrincipal();
		if(principal.getDossier().getNouveauGroupe() == null) {
			LOG.severe("Pas de groupe pour " + principal.getDossier().getEmail());
		}
		buildDossier(principal);
		dao.save(principal.getDossier());
		
		Set<String> links = new HashSet<String>();
		for (InscriptionDossierUi dossier : pDossiers.getDossiers()) {
			if(!dossier.isSupprimer()) {
				buildDossier(dossier);
				if(dossier.getDossier().getPrincipal() == null) {
					// Copier les données du principal dans ce dossier
					principal.getDossier().copy(dossier.getDossier());
				}
				dao.save(dossier.getDossier());
				links.add("v2/InscriptionAction.do?action=imprimerNew&numero="
						+ dossier.getDossier().getId().toString());
			}
		}

		StrBuilder linksAsString = new StrBuilder();
		linksAsString.appendWithSeparators(links, ";");
		return linksAsString.toString();

	}

	private void buildDossier(InscriptionDossierUi pDossier) {
		pDossier.getDossier().setSaisie(true);
		pDossier.getDossier().setCertificat(false);
		pDossier.getDossier().setPaiement(false);
		pDossier.getDossier().setInscriptiondt(new Date());
		
		buildMineur(pDossier.getDossier());
		
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
		// Upper case
		InscriptionEntity2 adherent = pDossier.getDossier();
		adherent.setAccidentNom1(StringUtils.upperCase(adherent
				.getAccidentNom1()));
		adherent.setAccidentNom2(StringUtils.upperCase(adherent
				.getAccidentNom2()));
		adherent.setAccidentPrenom1(StringUtils.upperCase(adherent
				.getAccidentPrenom1()));
		adherent.setAccidentPrenom2(StringUtils.upperCase(adherent
				.getAccidentPrenom2()));
		adherent.setAccordNomPrenom(StringUtils.upperCase(adherent
				.getAccordNomPrenom()));
		adherent.setAdresse(StringUtils.upperCase(adherent.getAdresse()));
		adherent.setNom(StringUtils.upperCase(adherent.getNom()));
		adherent.setPrenom(StringUtils.upperCase(adherent.getPrenom()));
		adherent.setProfession(StringUtils.upperCase(adherent.getProfession()));
		adherent.setProfessionTextMere(StringUtils.upperCase(adherent
				.getProfessionTextMere()));
		adherent.setProfessionTextPere(StringUtils.upperCase(adherent
				.getProfessionTextPere()));
		adherent.setShortPantalon(StringUtils.upperCase(adherent
				.getShortPantalon()));
		adherent.setTshirt(StringUtils.upperCase(adherent.getTshirt()));
		adherent.setVille(StringUtils.upperCase(adherent.getVille()));
	}

	private void buildCreneau(Set<Long> pCreneaux, List<SlotUi> pSlots,
			String pOldCreneaux) {
		for (SlotUi slot : pSlots) {
			if (slot.isSelected()) {
				pCreneaux.add(slot.getId());
				if (!StringUtils
						.contains(pOldCreneaux, slot.getId().toString())) {
					SlotEntity slotEntity = slotDao.get(slot.getId());
					slotEntity
							.setPlaceRestante(slotEntity.getPlaceRestante() - 1);
					slotDao.save(slotEntity);
				}
			}
		}
	}
	
	private void buildPrincipal(InscriptionEntity2 pPrincipal, InscriptionEntity2 adherent) {
		adherent.setCivilite(pPrincipal.getCivilite());
		adherent.setEmail(pPrincipal.getEmail());
		adherent.setEmailsecondaire(pPrincipal.getEmailsecondaire());
		adherent.setMotdepasse(pPrincipal.getMotdepasse());
		if(adherent.getGroupe() == null) {
		adherent.setGroupe(pPrincipal.getGroupe());
		} else {
			pPrincipal.setGroupe(adherent.getGroupe());
		}
		if(StringUtils.isBlank(adherent.getCreneaux())) {
			adherent.setCreneaux(pPrincipal.getCreneaux());
		} else {
			pPrincipal.setCreneaux(adherent.getCreneaux());
		}
		adherent.setAccidentNom1(StringUtils.upperCase(pPrincipal.getAccidentNom1()));
		adherent.setAccidentNom2(StringUtils.upperCase(pPrincipal.getAccidentNom2()));
		adherent.setAccidentPrenom1(StringUtils.upperCase(pPrincipal.getAccidentPrenom1()));
		adherent.setAccidentPrenom2(StringUtils.upperCase(pPrincipal.getAccidentPrenom2()));
		adherent.setAccordNomPrenom(StringUtils.upperCase(pPrincipal.getAccordNomPrenom()));
		adherent.setAdresse(StringUtils.upperCase(pPrincipal.getAdresse()));
		adherent.setNom(StringUtils.upperCase(pPrincipal.getNom()));
		adherent.setPrenom(StringUtils.upperCase(pPrincipal.getPrenom()));
		adherent.setProfession(StringUtils.upperCase(pPrincipal.getProfession()));
		adherent.setProfessionTextMere(StringUtils.upperCase(pPrincipal.getProfessionTextMere()));
		adherent.setProfessionTextPere(StringUtils.upperCase(pPrincipal.getProfessionTextPere()));
		adherent.setShortPantalon(StringUtils.upperCase(pPrincipal.getShortPantalon()));
		adherent.setTshirt(StringUtils.upperCase(pPrincipal.getTshirt()));
		adherent.setVille(StringUtils.upperCase(pPrincipal.getVille()));
	}
	
	private void buildMineur(InscriptionEntity2 adherent) {
		//Determine si l'adherent est mineur ou majeur
		LocalDate adherentAge = new LocalDate(adherent.getNaissance());
		int year = Years.yearsBetween(adherentAge, LocalDate.now()).getYears();
		if(year < 18) {
			adherent.setMineur(adherent.getNom() + " " + adherent.getPrenom());
			//TODO parent 1 / parent 2
			//adherent.setMineurParent(adherent.get);
		} else {
			adherent.setMineur(StringUtils.EMPTY);
			adherent.setMineurParent(StringUtils.EMPTY);
		}
		
	}
}