package com.asptttoulousenatation.core.adherent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.web.adherent.AdherentListAction;

@Path("/adherents")
@Produces("application/json")
public class AdherentService {
	
	private static final Logger LOG = Logger.getLogger(AdherentService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private Inscription2Dao dao = new Inscription2Dao();
	private SlotDao slotDao = new SlotDao();

	@GET
	public GetAdherentsResult getAdherents(@QueryParam("search") String pSearch) {
		GetAdherentsResult result = new GetAdherentsResult();
		// Separation selon +
		if (StringUtils.isNotBlank(pSearch)) {
			List<InscriptionEntity2> adherents = new ArrayList<InscriptionEntity2>(
					0);
			if (StringUtils.contains(pSearch, "+")) {
				String[] searches = StringUtils.split(pSearch, "+");
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						2);

				criteria.add(new CriterionDao<String>(
						InscriptionEntityFields.NOM, searches[0],
						Operator.EQUAL));
				if (searches.length > 1) {
					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.PRENOM, searches[1],
							Operator.EQUAL));
				}
				adherents.addAll(dao.find(criteria));
				if (CollectionUtils.isEmpty(adherents)) {
					criteria = new ArrayList<CriterionDao<? extends Object>>(1);
					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.NOM, searches[0]
									.toUpperCase(), Operator.EQUAL));
					if (searches.length > 1) {
						criteria.add(new CriterionDao<String>(
								InscriptionEntityFields.PRENOM, searches[1]
										.toUpperCase(), Operator.EQUAL));
					}
					adherents.addAll(dao.find(criteria));
				}
			} else {
				if (StringUtils.contains(pSearch, "@")) {// Email
					List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
							1);

					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.EMAIL, pSearch,
							Operator.EQUAL));
					adherents.addAll(dao.find(criteria));
					criteria = new ArrayList<CriterionDao<? extends Object>>(1);
					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.EMAIL, pSearch
									.toUpperCase(), Operator.EQUAL));
					adherents.addAll(dao.find(criteria));
				} else {// Nom ou prenom
					List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
							1);

					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.NOM, pSearch,
							Operator.EQUAL));
					adherents.addAll(dao.find(criteria));
					if (CollectionUtils.isEmpty(adherents)) {
						criteria = new ArrayList<CriterionDao<? extends Object>>(
								1);
						criteria.add(new CriterionDao<String>(
								InscriptionEntityFields.NOM, pSearch
										.toUpperCase(), Operator.EQUAL));
						adherents.addAll(dao.find(criteria));

						if (CollectionUtils.isEmpty(adherents)) {
							criteria = new ArrayList<CriterionDao<? extends Object>>(
									1);
							criteria.add(new CriterionDao<String>(
									InscriptionEntityFields.PRENOM, pSearch,
									Operator.EQUAL));
							adherents.addAll(dao.find(criteria));
							if (CollectionUtils.isEmpty(adherents)) {
								criteria = new ArrayList<CriterionDao<? extends Object>>(
										1);
								criteria.add(new CriterionDao<String>(
										InscriptionEntityFields.PRENOM, pSearch
												.toUpperCase(), Operator.EQUAL));
								adherents.addAll(dao.find(criteria));
							}
						}
					}
				}
			}
			result.setAdherents(AdherentSummaryBeanTransformer.getInstance()
					.get(adherents));
		}
		return result;
	}

	@Path("{adherentId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public AdherentBean getAdherent(
			@PathParam("adherentId") Long adherentId) {
		return AdherentBeanTransformer.getInstance().get(dao.get(adherentId));
	}
	
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public void updateAdherent(AdherentBean pAdherent) {
		buildAdherent(pAdherent);
		dao.save(pAdherent.getDossier());
	}

	@Path("{adherentId}")
	@DELETE
	public void deleteAdherent(@PathParam("adherentId") Long adherentId) {
		// Positionne l'adherent principal
		InscriptionEntity2 principal = dao.get(adherentId);
		if (principal.getPrincipal() == null) {
			// Recherche des enfants
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.PRINCIPAL, principal.getId(),
					Operator.EQUAL));
			List<InscriptionEntity2> enfants = dao.find(criteria);
			if (CollectionUtils.isNotEmpty(enfants)) {
				InscriptionEntity2 nouveauPrincipal = enfants.get(0);
				nouveauPrincipal.setAccidentNom1(principal.getAccidentNom1());
				nouveauPrincipal.setAccidentNom2(principal.getAccidentNom2());
				nouveauPrincipal.setAccidentPrenom1(principal
						.getAccidentPrenom1());
				nouveauPrincipal.setAccidentPrenom2(principal
						.getAccidentPrenom2());
				nouveauPrincipal.setAccidentTelephone1(principal
						.getAccidentTelephone1());
				nouveauPrincipal.setAccidentTelephone2(principal
						.getAccidentTelephone2());
				nouveauPrincipal.setAccordNomPrenom(principal.getAccordNomPrenom());
				nouveauPrincipal.setAdresse(principal.getAdresse());
				nouveauPrincipal.setCertificat(principal.getCertificat());
				nouveauPrincipal.setCivilite(principal.getCivilite());
				nouveauPrincipal.setCodepostal(principal.getCodepostal());
				nouveauPrincipal.setEmail(principal.getEmail());
				nouveauPrincipal.setEmailsecondaire(principal
						.getEmailsecondaire());
				nouveauPrincipal.setFacture(principal.getFacture());
				nouveauPrincipal.setGroupe(principal.getGroupe());
				nouveauPrincipal.setInscriptiondt(principal.getInscriptiondt());
				nouveauPrincipal.setLicenceFFN(principal.getLicenceFFN());
				nouveauPrincipal.setMaillot(principal.getMaillot());
				nouveauPrincipal.setMineur(principal.getMineur());
				nouveauPrincipal.setMineurParent(principal.getMineurParent());
				nouveauPrincipal.setMotdepasse(principal.getMotdepasse());
				nouveauPrincipal.setNom(principal.getNom());
				nouveauPrincipal.setNouveau(principal.getNouveau());
				nouveauPrincipal.setNouveauGroupe(principal.getNouveauGroupe());
				nouveauPrincipal.setPaiement(principal.getPaiement());
				nouveauPrincipal.setPrenom(principal.getPrenom());
				nouveauPrincipal.setProfessionTextMere(principal
						.getProfessionTextMere());
				nouveauPrincipal.setProfessionTextPere(principal
						.getProfessionTextPere());
				nouveauPrincipal.setSaisie(principal.getSaisie());
				nouveauPrincipal.setShortPantalon(principal.getShortPantalon());
				nouveauPrincipal.setTelephone(principal.getTelephone());
				nouveauPrincipal.setTelephoneMere(principal.getTelephoneMere());
				nouveauPrincipal.setTelephoneMerePro(principal
						.getTelephoneMerePro());
				nouveauPrincipal.setTelephonePere(principal.getTelephonePere());
				nouveauPrincipal.setTelephonePerePro(principal
						.getTelephonePerePro());
				nouveauPrincipal.setTshirt(principal.getTshirt());
				nouveauPrincipal.setTypeLicence(principal.getTypeLicence());
				nouveauPrincipal.setVille(principal.getVille());

				nouveauPrincipal.setPrincipal(null);
				dao.save(nouveauPrincipal);

				for (InscriptionEntity2 enfant : enfants) {
					if (enfant.getId() != nouveauPrincipal.getId()) {
						enfant.setPrincipal(nouveauPrincipal.getId());
						dao.save(enfant);
					}
				}
			}
		}
		dao.delete(adherentId);
	}
	
	private void buildAdherent(AdherentBean pAdherent) {
		if (pAdherent.getGroupe() != null) {
			pAdherent.getDossier()
					.setNouveauGroupe(pAdherent.getGroupe().getId());
		}

		if (CollectionUtils.isNotEmpty(pAdherent.getCreneaux())) {
			Set<Long> creneaux = new HashSet<Long>();
			for (SlotUi slot : pAdherent.getCreneaux()) {
				creneaux.add(slot.getId());
				SlotEntity slotEntity = slotDao.get(slot.getId());
				slotEntity
						.setPlaceRestante(slotEntity.getPlaceRestante() - 1);
				slotDao.save(slotEntity);
			}
			//Ancien creneaux
			if(StringUtils.isNotBlank(pAdherent.getDossier().getCreneaux())) {
				String[] creneauxTab = pAdherent.getDossier().getCreneaux().split(";");
				for(String creneauId: creneauxTab) {
					if(StringUtils.isNotBlank(creneauId) && StringUtils.isNumeric(creneauId)) {
						SlotEntity slotEntity = slotDao.get(Long.valueOf(creneauId));
						slotEntity
								.setPlaceRestante(slotEntity.getPlaceRestante() + 1);
						slotDao.save(slotEntity);	
					}
				}
			}
			
			StrBuilder creneauxAsString = new StrBuilder();
			creneauxAsString.appendWithSeparators(creneaux, ";");
			pAdherent.getDossier().setCreneaux(creneauxAsString.toString());
		}
		// Upper case
		InscriptionEntity2 adherent = pAdherent.getDossier();
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
	
	@Path("{adherentId}")
	@POST
	public void certificat(@PathParam("adherentId") Long adherentId,
			@QueryParam("certificatValue") Boolean pCertificatValue) {
		InscriptionEntity2 entity = dao.get(adherentId);
		entity.setCertificat(pCertificatValue);
		dao.save(entity);

		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"webmaster@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation"));
			Address[] replyTo = { new InternetAddress(
					"contact@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation") };
			msg.setReplyTo(replyTo);

			final String email;
			final String emailSecondaire;
			// Get principal
			if (entity.getPrincipal() != null) {
				InscriptionEntity2 principal = dao.get(entity.getPrincipal());
				email = principal.getEmail();
				emailSecondaire = principal.getEmailsecondaire();
			} else {
				email = entity.getEmail();
				emailSecondaire = entity.getEmailsecondaire();
			}
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));
			if (StringUtils.isNotBlank(emailSecondaire)) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						emailSecondaire));
			}
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					"contact@asptt-toulouse-natation.com"));

			StringBuilder message = new StringBuilder();

			message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons bien réceptionné votre dossier.<br />Cependant il manque le certificat médical.<br />Merci de nous le faire parvenir"
							+ " au plus vite.");
			msg.setSubject("ASPTT Toulouse Natation - Certificat médical",
					"UTF-8");

			message.append("<p>Sportivement,<br />"
					+ "Le secrétariat,<br />"
					+ "ASPTT Grand Toulouse Natation<br />"
					+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);
			msg.setContent(mp);
			Transport.send(msg);
		} catch (AddressException e) {
			LOG.severe("Adherent: " + entity.getNom() + " " + e.getMessage());
		} catch (MessagingException e) {
			LOG.severe("Adherent: " + entity.getNom() + " " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOG.severe("Adherent: " + entity.getNom() + " " + e.getMessage());
		}
	}
}