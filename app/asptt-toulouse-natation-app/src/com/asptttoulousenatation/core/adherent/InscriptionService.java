package com.asptttoulousenatation.core.adherent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Blob;

@Path("/inscription")
public class InscriptionService {
	
	private static final Logger LOG = Logger.getLogger(InscriptionService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private Inscription2Dao dao = new Inscription2Dao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();
	
	@Path("/price")
	@POST
	@Consumes("application/json")
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

	@Path("inscrire")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public InscriptionDossiersUi inscrire(@DefaultValue("true") @FormDataParam("enabled") boolean enabled, @FormDataParam("file") List<FormDataBodyPart> certificats, @FormDataParam("action") String pAction) {
		InscriptionDossiersUi result = null;
		try {
		String unscape = URLDecoder.decode(pAction, "UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		InscriptionDossiersUi dossiers = mapper.readValue(unscape,
				InscriptionDossiersUi.class);
		InscriptionDossierUi principal = dossiers.getPrincipal();
		if(principal.getDossier().getNouveauGroupe() == null) {
			LOG.severe("Pas de groupe pour " + principal.getDossier().getEmail());
		}
		buildDossier(principal);
		dao.save(principal.getDossier());
		
		//Add certificats
		if(MapUtils.isNotEmpty(dossiers.getCertificats())) {
			for(FormDataBodyPart certificatPart: certificats) {
				try {
					Blob certificat = new Blob(
							IOUtils.toByteArray(certificatPart.getValueAs(InputStream.class)));
					String fileName = certificatPart.getContentDisposition().getFileName();
					Long dossierId = Long.valueOf(dossiers.getCertificats().get(fileName).split("_")[1]);
					dossiers.getDossier(dossierId).getDossier().setCertificatmedical(certificat);
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "Récupération des certificats", e);
				}
				
			}
		}
		
		Set<String> links = new HashSet<String>();
		for (InscriptionDossierUi dossier : dossiers.getDossiers()) {
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
		result = dossiers;
		}catch(IOException e) {
			LOG.log(Level.SEVERE, "Error parsing JSON dossiers", e);
		}
		
		
		return result;
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