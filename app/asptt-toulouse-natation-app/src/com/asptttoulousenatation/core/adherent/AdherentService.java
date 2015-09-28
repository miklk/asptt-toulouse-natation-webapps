package com.asptttoulousenatation.core.adherent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
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
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/adherents")
@Produces("application/json")
public class AdherentService {

	private static final Logger LOG = Logger.getLogger(AdherentService.class
			.getName());

	private static final int EMAIL_PAQUET = 10;

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private Inscription2Dao dao = new Inscription2Dao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();

	@GET
	public GetAdherentsResult getAdherents(@QueryParam("q") String querySearch,
			@QueryParam("groupes") Set<Long> groupes,
			@QueryParam("creneaux") final Set<Long> creneaux,
			@QueryParam("piscines") final Set<String> piscines) {
		GetAdherentsResult result = new GetAdherentsResult();
		String search = querySearch;
		// Separation selon +
		List<InscriptionEntity2> adherents = new ArrayList<InscriptionEntity2>(
				0);
		if (StringUtils.isNotBlank(search)) {

			if (StringUtils.contains(search, "+")) {
				String[] searches = StringUtils.split(search, "+");
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
				if (StringUtils.contains(search, "@")) {// Email
					List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
							1);

					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.EMAIL, search,
							Operator.EQUAL));
					adherents.addAll(dao.find(criteria));
					criteria = new ArrayList<CriterionDao<? extends Object>>(1);
					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.EMAIL,
							search.toUpperCase(), Operator.EQUAL));
					adherents.addAll(dao.find(criteria));
				} else {// Nom ou prenom
					List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
							1);

					criteria.add(new CriterionDao<String>(
							InscriptionEntityFields.NOM, search, Operator.EQUAL));
					adherents.addAll(dao.find(criteria));
					if (CollectionUtils.isEmpty(adherents)) {
						criteria = new ArrayList<CriterionDao<? extends Object>>(
								1);
						criteria.add(new CriterionDao<String>(
								InscriptionEntityFields.NOM, search
										.toUpperCase(), Operator.EQUAL));
						adherents.addAll(dao.find(criteria));

						if (CollectionUtils.isEmpty(adherents)) {
							criteria = new ArrayList<CriterionDao<? extends Object>>(
									1);
							criteria.add(new CriterionDao<String>(
									InscriptionEntityFields.PRENOM, search,
									Operator.EQUAL));
							adherents.addAll(dao.find(criteria));
							if (CollectionUtils.isEmpty(adherents)) {
								criteria = new ArrayList<CriterionDao<? extends Object>>(
										1);
								criteria.add(new CriterionDao<String>(
										InscriptionEntityFields.PRENOM, search
												.toUpperCase(), Operator.EQUAL));
								adherents.addAll(dao.find(criteria));
							}
						}
					}
				}
			}
		} else {
			// groupes
			if (CollectionUtils.isNotEmpty(groupes)) {
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						groupes.size());
				for (Long groupe : groupes) {
					criteria.add(new CriterionDao<Long>(
							InscriptionEntityFields.NOUVEAUGROUPE, groupe,
							Operator.EQUAL));
				}
				adherents.addAll(dao.find(criteria, Operator.OR, null));
			}
			if (CollectionUtils.isEmpty(adherents)) {
				adherents = dao.getAll();
			}
			if (CollectionUtils.isNotEmpty(creneaux)
					|| CollectionUtils.isNotEmpty(piscines)) {
				final Set<Long> piscinesAsLong = new HashSet<>();
				for (String piscine : piscines) {
					piscinesAsLong.addAll(AdherentBeanTransformer.getInstance()
							.getCreneauIds(piscine));
				}
				adherents = new ArrayList<>(CollectionUtils.select(adherents,
						new Predicate() {
							@Override
							public boolean evaluate(Object pArg0) {
								InscriptionEntity2 adherent = (InscriptionEntity2) pArg0;
								Set<Long> adherentCreneaux = AdherentBeanTransformer
										.getInstance().getCreneauIds(
												adherent.getCreneaux());
								boolean found = false;
								Iterator<Long> it = adherentCreneaux.iterator();
								while (it.hasNext() && !found) {
									Long adherentCreneau = it.next();
									if (creneaux.contains(adherentCreneau)
											|| piscinesAsLong
													.contains(adherentCreneau)) {
										found = true;
									}
								}
								return found;
							}
						}));
			}
		}
		result.setAdherents(AdherentSummaryBeanTransformer.getInstance().get(
				adherents));
		return result;
	}

	@Path("{adherentId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public AdherentBean getAdherent(@PathParam("adherentId") Long adherentId) {
		return AdherentBeanTransformer.getInstance().get(dao.get(adherentId));
	}

	@Path("update")
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
				nouveauPrincipal.setAccordNomPrenom(principal
						.getAccordNomPrenom());
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
			pAdherent.getDossier().setNouveauGroupe(
					pAdherent.getGroupe().getId());
		}

		if (CollectionUtils.isNotEmpty(pAdherent.getCreneaux())) {
			Set<Long> creneaux = new HashSet<Long>();
			for (SlotUi slot : pAdherent.getCreneaux()) {
				creneaux.add(slot.getId());
				SlotEntity slotEntity = slotDao.get(slot.getId());
				slotEntity.setPlaceRestante(slotEntity.getPlaceRestante() - 1);
				slotDao.save(slotEntity);
			}
			// Ancien creneaux
			if (StringUtils.isNotBlank(pAdherent.getDossier().getCreneaux())) {
				String[] creneauxTab = pAdherent.getDossier().getCreneaux()
						.split(";");
				for (String creneauId : creneauxTab) {
					if (StringUtils.isNotBlank(creneauId)
							&& StringUtils.isNumeric(creneauId)) {
						SlotEntity slotEntity = slotDao.get(Long
								.valueOf(creneauId));
						slotEntity.setPlaceRestante(slotEntity
								.getPlaceRestante() + 1);
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

	@Path("/email")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public List<String> email(FormDataMultiPart multiPart) {
		String destinataireParam = multiPart.getField("destinataire")
				.getValue();
		String groupesAsString = multiPart.getField("groupes").getValue();
		Long creneau = multiPart.getField("creneau").getValueAs(Long.class);
		String piscine = multiPart.getField("piscine").getValue();
		String from = multiPart.getField("messageFrom").getValue();
		String subject = multiPart.getField("messageSubject").getValue();
		String corps = multiPart.getField("messageContent").getValue();
		FormDataBodyPart fichierMultiPart = multiPart.getField("file");
		List<String> recipents = new ArrayList<String>();
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			Set<Long> creneaux = Collections.singleton(creneau);
			Set<Long> groupes = new HashSet<>();
			for(String groupeAsString: groupesAsString.split(",")) {
				groupes.add(Long.valueOf(groupeAsString));
			}
			List<String> destinataires = getDestinataires(destinataireParam,
					groupes, creneaux, piscine);
			for (int i = 0; i < destinataires.size(); i += EMAIL_PAQUET) {
				try {
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(corps, "text/html");
					mp.addBodyPart(htmlPart);
					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(
							from,
							"ASPTT Toulouse Natation"));
					Address[] replyTo = { new InternetAddress(
							from,
							"ASPTT Toulouse Natation") };
					msg.setReplyTo(replyTo);
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(
									"support@asptt-toulouse-natation.com"));
					if (fichierMultiPart.getFormDataContentDisposition()
							.getFileName() != null) {
						MimeBodyPart attachment = new MimeBodyPart();
						attachment.setFileName(fichierMultiPart
								.getFormDataContentDisposition().getFileName());
						InputStream data = fichierMultiPart
								.getValueAs(InputStream.class);
						byte[] fileData = IOUtils.toByteArray(data);
						String mediaType = fichierMultiPart.getMediaType()
								.toString();
						attachment.setContent(
								new ByteArrayInputStream(fileData), mediaType);
						mp.addBodyPart(attachment);
					}
					msg.setSubject(subject, "UTF-8");
					msg.setContent(mp);

					int first = i;
					int end = Math.min(first + EMAIL_PAQUET,
							destinataires.size());
					try {
						for (int j = first; j < end; j++) {
							String destinataire = destinataires.get(j);
							if (StringUtils.isNotBlank(destinataire)) {
								LOG.warning("Send to " + destinataire);
								msg.addRecipient(Message.RecipientType.BCC,
										new InternetAddress(destinataire));
								recipents.add(destinataire);
							}
						}
						Transport.send(msg);
					} catch (Exception e) {
						LOG.log(Level.SEVERE, e.getMessage(), e);
					}
				} catch (Exception e) {
					LOG.log(Level.SEVERE, e.getMessage(), e);
				}
			}
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			StrBuilder rapport = new StrBuilder();
			rapport.appendWithSeparators(recipents, "<br />");
			htmlPart.setContent("E-mail envoyé à " + new Date() + "<br />"
					+ rapport.toString(), "text/html");
			mp.addBodyPart(htmlPart);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"webmaster@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"contact@asptt-toulouse-natation.com"));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					"support@asptt-toulouse-natation.com"));
			msg.setSubject("Rapport d'envoi d'e-mail", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);

		} catch (AddressException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);

		} catch (MessagingException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}

		return recipents;
	}
	
	private List<String> getDestinataires(String destinataire, Set<Long> groupes,
			Set<Long> creneaux, String piscine) {
		List<String> destinataires = new ArrayList<>();
		switch (destinataire) {
		case "all": {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Object>(
					InscriptionEntityFields.INSCRIPTIONDT, null,
					Operator.NOT_NULL));
			criteria.add(new CriterionDao<Object>(
					InscriptionEntityFields.PRINCIPAL, null, Operator.NULL));
			List<InscriptionEntity2> adherents = dao.find(criteria);
			for (InscriptionEntity2 adherent : adherents) {
				if (StringUtils.isNotBlank(adherent.getEmail())) {
					destinataires.add(adherent.getEmail());
				}
			}
		};
			break;
		case "enf": {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Boolean>(GroupEntityFields.ENF,
					Boolean.TRUE, Operator.EQUAL));
			List<GroupEntity> groupeEntities = groupDao.find(criteria);
			Set<Long> groupesId = new HashSet<>();
			for (GroupEntity groupeEntity : groupeEntities) {
				groupesId.add(groupeEntity.getId());
			}
		};
			break;
		case "competiteur": {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Boolean>(GroupEntityFields.COMPETITION,
					Boolean.TRUE, Operator.EQUAL));
			List<GroupEntity> groupeEntities = groupDao.find(criteria);
			Set<Long> groupesId = new HashSet<>();
			for (GroupEntity groupeEntity : groupeEntities) {
				groupesId.add(groupeEntity.getId());
			}
			destinataires.addAll(getDestinatairesByGroupe(groupesId,
					new HashSet<Long>()));
		};
			break;
		case "piscine": {
			Set<Long> creneauFromPiscine = AdherentBeanTransformer
					.getInstance().getCreneauIds(piscine);
			Set<Long> groupePiscineEntities = new HashSet<>();
			for (Long creneauId : creneauFromPiscine) {
				SlotEntity slotEntity = slotDao.get(creneauId);
				groupePiscineEntities.add(slotEntity.getGroup());
			}
			destinataires.addAll(getDestinatairesByGroupe(groupePiscineEntities,
					creneauFromPiscine));
		};
			break;
		case "groupes": {
			destinataires.addAll(getDestinatairesByGroupe(groupes,
					new HashSet<Long>()));
		};
			break;
		case "creneau": {
			destinataires.addAll(getDestinatairesByGroupe(groupes,
					creneaux));
		};
		break;
		default:
			LOG.severe(destinataire + " is not handle");
		}
		return destinataires;
	}

	private Set<String> getDestinatairesByGroupe(Set<Long> groupes,
			final Set<Long> creneaux) {
		Set<String> destinataires = new HashSet<>();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				groupes.size());
		for (Long groupe : groupes) {
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, groupe,
					Operator.EQUAL));
		}
		List<InscriptionEntity2> adherents = dao.find(criteria, Operator.OR,
				null);
		@SuppressWarnings("unchecked")
		Collection<InscriptionEntity2> adherentsSelected = CollectionUtils
				.select(adherents, new Predicate() {

					@Override
					public boolean evaluate(Object pArg0) {
						InscriptionEntity2 adherent = (InscriptionEntity2) pArg0;
						final boolean valid;
						if (CollectionUtils.isNotEmpty(creneaux)) {
							boolean found = false;
							Iterator<Long> it = creneaux.iterator();
							while (it.hasNext() && !found) {
								found = StringUtils.contains(adherent
										.getCreneaux(), it.next().toString());
							}
							valid = found;
						} else {
							valid = true;
						}
						return valid;
					}
				});
		for (InscriptionEntity2 adherent : adherentsSelected) {
			if (StringUtils.isNotBlank(adherent.getEmail())) {
				destinataires.add(adherent.getEmail());
			}
		}
		return destinataires;
	}
}