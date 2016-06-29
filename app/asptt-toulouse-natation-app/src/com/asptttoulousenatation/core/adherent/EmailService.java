package com.asptttoulousenatation.core.adherent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.PiscineDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.PiscineEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/email")
@Produces("application/json")
public class EmailService {
	
	private static final Logger LOG = Logger.getLogger(EmailService.class
			.getName());

	private static final int EMAIL_PAQUET = 10;
	
	private DossierDao dossierDao = new DossierDao();
	private DossierNageurDao dao = new DossierNageurDao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();
	private PiscineDao piscineDao = new PiscineDao();
	
	private static Map<String, String> EMAILS = new HashMap<>();

	@Path("/send")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public List<String> email(FormDataMultiPart multiPart) {
		String destinataireParam = multiPart.getField("destinataire")
				.getValue();
		String groupesAsString = multiPart.getField("groupes").getValue();
		Long creneau = multiPart.getField("creneau").getValueAs(Long.class);
		Long piscine = multiPart.getField("piscine").getValueAs(Long.class);
		String to = multiPart.getField("messageTo").getValue();
		String from = multiPart.getField("messageFrom").getValue();
		String subject = multiPart.getField("messageSubject").getValue();
		String corps = multiPart.getField("messageContent").getValue();
		String copie = multiPart.getField("messageCc").getValue();
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
			
			final List<String> destinataires;
			if (to.contains("@")) {
				Set<String> destinatairesTmp = new HashSet<>();
				for(String toEmail: to.split(";")) {
					if(StringUtils.isNotBlank(toEmail)) {
						destinatairesTmp.add(toEmail);
					}
				}
				destinataires = new ArrayList<>(destinatairesTmp);
			} else {
				destinataires = getDestinataires(destinataireParam, groupes, creneaux, piscine);
			}
			
			for (int i = 0; i < destinataires.size(); i += EMAIL_PAQUET) {
				try {
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(corps, "text/html");
					mp.addBodyPart(htmlPart);
					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(
							"webmaster@asptt-toulouse-natation.com",
							"ASPTT Toulouse Natation"));
					Address[] replyTo = { new InternetAddress(
							from,
							"ASPTT Toulouse Natation") };
					msg.setReplyTo(replyTo);
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(
									"support@asptt-toulouse-natation.com"));
					if(StringUtils.isNotBlank(copie)) {
						for(String cc: copie.split(",")) {
							msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
						}
					}
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
			Set<Long> creneaux, Long piscine) {
		List<String> destinataires = new ArrayList<>();
		switch (destinataire) {
		case "all": {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(
					DossierEntityFields.STATUT, DossierStatutEnum.INSCRIT.name(),
					Operator.EQUAL));
			List<DossierEntity> dossiers = dossierDao.find(criteria);
			for (DossierEntity dossier : dossiers) {
				fillDestinataires(destinataires, dossier);
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
			destinataires.addAll(getDestinatairesByGroupe(groupesId,
					new HashSet<Long>()));
		};
			break;
		case "competiteur": {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
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
			PiscineEntity piscineEntity = piscineDao.get(piscine);
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<String>(SlotEntityFields.SWIMMINGPOOL, piscineEntity.getIntitule(),
					Operator.EQUAL));
			List<SlotEntity> creneauEntities = slotDao.find(criteria);
			Set<Long> creneauFromPiscine = new HashSet<>();
			Set<Long> groupePiscineEntities = new HashSet<>();
			for (SlotEntity creneauEntity : creneauEntities) {
				creneauFromPiscine.add(creneauEntity.getId());
				groupePiscineEntities.add(creneauEntity.getGroup());
			}
			destinataires.addAll(getDestinatairesByGroupe(groupePiscineEntities, creneauFromPiscine));
		}
			;
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
					DossierNageurEntityFields.GROUPE, groupe,
					Operator.EQUAL));
		}
		List<DossierNageurEntity> nageurs = dao.find(criteria, Operator.OR,
				null);
		@SuppressWarnings("unchecked")
		Collection<DossierNageurEntity> nageursSelected = CollectionUtils
				.select(nageurs, new Predicate() {

					@Override
					public boolean evaluate(Object pArg0) {
						DossierNageurEntity nageur = (DossierNageurEntity) pArg0;
						final boolean valid;
						if (CollectionUtils.isNotEmpty(creneaux)) {
							boolean found = false;
							Iterator<Long> it = creneaux.iterator();
							while (it.hasNext() && !found) {
								found = StringUtils.contains(nageur
										.getCreneaux(), it.next().toString());
							}
							valid = found;
						} else {
							valid = true;
						}
						return valid;
					}
				});
		for (DossierNageurEntity nageur : nageursSelected) {
			DossierEntity dossier = dossierDao.get(nageur.getDossier());
			fillDestinataires(destinataires, dossier);
		}
		return destinataires;
	}
	
	private void fillDestinataires(Collection<String> destinataires, DossierEntity dossier) {
		if(DossierStatutEnum.INSCRIT.name().equals(dossier.getStatut())) {
			if (StringUtils.isNotBlank(dossier.getEmail())) {
				destinataires.add(dossier.getEmail());
			}
			if(StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
				destinataires.add(dossier.getEmailsecondaire());
			}
		}
	}
	
	@Path("/initEmail")
	@GET
	public Map<String, String> initEmail() {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON, 1L, Operator.EQUAL));
		List<DossierNageurEntity> nageurs = dao.find(criteria);
		for (DossierNageurEntity nageur : nageurs) {
			DossierEntity dossier = dossierDao.get(nageur.getDossier());
			if (dossier != null) {
				if (dossier.getStatut().equals(DossierStatutEnum.INSCRIT.name())) {
					EMAILS.put(nageur.getNom(), dossier.getEmail());
				}
			} else {
				LOG.log(Level.WARNING,
						"Pas de dossier pour le nageur #" + nageur.getId() + " (" + nageur.getNom() + ")");
			}
		}
		return EMAILS;
	}
}
