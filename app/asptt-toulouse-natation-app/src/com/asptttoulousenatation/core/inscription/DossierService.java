package com.asptttoulousenatation.core.inscription;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierCertificatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierCertificatEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierCertificatDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.web.adherent.AdherentListResultBeanTransformer;

@Path("/dossiers")
@Produces("application/json")
public class DossierService {
	
	private static final Logger LOG = Logger.getLogger(DossierService.class
			.getName());

	private DossierNageurDao dao = new DossierNageurDao();
	private DossierDao dossierDao = new DossierDao();
	private GroupDao groupeDao = new GroupDao();
	private DossierCertificatDao certificatDao = new DossierCertificatDao();
	private SlotDao slotDao = new SlotDao();
	
	
	@Path("/find")
	@GET
	@Consumes("application/json")
	public List<DossierResultBean> find(@QueryParam("query") String texteLibre, @QueryParam("groupe") Long groupe, @QueryParam("sansGroupe") Boolean sansGroupe) {
		List<DossierResultBean> result = Collections.emptyList();
		List<DossierNageurEntity> nageurs = new ArrayList<DossierNageurEntity>();
		if(StringUtils.isNotBlank(texteLibre)) {
			/**Nom prenom email
			String[] splitGuillemets = texteLibre.split("\"");
			for(String slitGuillemets: splitGuillemets) {
			}**/
			if(texteLibre.contains("@")) {
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL,
						texteLibre, Operator.EQUAL));
				List<DossierEntity> entities = dossierDao.find(criteria);
				for(DossierEntity dossier: entities) {
					List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
							1);
					criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
							dossier.getId(), Operator.EQUAL));
					nageurs.addAll(dao.find(criteriaNageur));
				}
			} else {
				List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteriaNageur.add(new CriterionDao<String>(DossierNageurEntityFields.NOM,
						texteLibre.toUpperCase(), Operator.EQUAL));
				nageurs.addAll(dao.find(criteriaNageur));
			}
			
		} else if(groupe != null && groupe > 0) {
			List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE,
					groupe, Operator.EQUAL));
			nageurs.addAll(dao.find(criteriaNageur));
		} else if (BooleanUtils.isTrue(sansGroupe)) {
			List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE,
					null, Operator.NULL));
			nageurs.addAll(dao.find(criteriaNageur));
		} else {
			nageurs = dao.getAll();
		}
		result = DossierResultTransformer.getInstance().toUi(nageurs);
		return result;
	}
	
	@Path("{dossier}")
	@GET
	public DossierUi findOne(@PathParam("dossier") Long dossierId) {
		DossierUi dossierUi = new DossierUi();
		DossierEntity dossier = dossierDao.get(dossierId);
		dossierUi.setPrincipal(dossier);
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
				dossierId, Operator.EQUAL));
		List<DossierNageurEntity> entities = dao.find(criteria);
		for(DossierNageurEntity entity: entities) {
			dossierUi.addNageur(entity);
		}
		return dossierUi;
	}
	
	@Path("changerGroupe")
	@POST
	public DossierResultBean changerGroupe(DossierNageurEntity nageur) {
		final DossierResultBean result;
		if (nageur.getGroupe() != null) {
			DossierNageurEntity entity = dao.get(nageur.getId());
			GroupEntity groupe = groupeDao.get(nageur.getGroupe());
			if (groupe != null) {
				entity.setGroupe(nageur.getGroupe());
				entity.setCreneaux(null);
				DossierNageurEntity entitySaved = dao.save(entity);
				result = DossierResultTransformer.getInstance().toUi(
						entitySaved);
			} else {
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}
	
	@Path("creer")
	@POST
	public boolean creer(DossierCreerParameters parameters) {
		boolean result;
		// Test existante du dossier même e-mail
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL,
				parameters.getEmail(), Operator.EQUAL));
		List<DossierEntity> entities = dossierDao.find(criteria);
		if (CollectionUtils.isNotEmpty(entities)) {
			result = false;
		} else {
			DossierEntity entity = new DossierEntity();
			entity.setEmail(parameters.getEmail());
			entity.setMotdepasse(parameters.getMdp());
			DossierEntity entityCreated = dossierDao.save(entity);
			DossierNageurEntity nageur = new DossierNageurEntity();
			nageur.setNom(parameters.getNom());
			nageur.setPrenom(parameters.getPrenom());
			nageur.setDossier(entityCreated.getId());
			dao.save(nageur);
			result = true;
		}
		return result;
	}
	
	@Path("update")
	@POST
	public boolean update(DossierUpdateParameters parameters) {
		boolean result = true;
		dossierDao.save(parameters.getPrincipal());
		for(DossierNageurEntity nageur: parameters.getNageurs()) {
			dao.save(nageur);
		}
		return result;
	}
	
	@Path("{dossier}")
	@DELETE
	public void delete(@PathParam("dossier") Long dossierId) {
		DossierEntity dossier = dossierDao.get(dossierId);
		if(dossier != null) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
					dossierId, Operator.EQUAL));
			List<DossierNageurEntity> entities = dao.find(criteria);
			for(DossierNageurEntity nageur: entities) {
				List<CriterionDao<? extends Object>> criteriaCertificat = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteriaCertificat.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER,
						nageur.getId(), Operator.EQUAL));
				List<DossierCertificatEntity> certificats = certificatDao.find(criteriaCertificat);
				for(DossierCertificatEntity certificat: certificats) {
					certificatDao.delete(certificat);
				}
				dao.delete(nageur);
			}
			dossierDao.delete(dossier);
		}
	}
	
	@Path("paiement")
	@POST
	public void paiement(DossierPaiementParameters parameters) {
		DossierEntity dossier = dossierDao.get(parameters.getDossierId());
	
		if(DossierStatutEnum.PAIEMENT_COMPLET.equals(DossierStatutEnum.valueOf(parameters.getStatutPaiement()))
				&& hasAllCertificats(dossier)) {
			dossier.setStatut(DossierStatutEnum.INSCRIT.name());
		} else {
			dossier.setStatut(DossierStatutEnum.valueOf(parameters.getStatutPaiement()).name());
		}
		dossier.setMontantreel(parameters.getMontantReel());
		if(StringUtils.isNotBlank(parameters.getCommentaire())) {
			StringBuilder builder = new StringBuilder();
			if(StringUtils.isNotBlank(dossier.getComment())) {
				builder.append(dossier.getComment()).append("\n");
			}
			builder.append(parameters.getCommentaire());
			dossier.setComment(builder.toString());
		}
		dossierDao.save(dossier);
		
		sendConfirmation(dossier);
	}
	
	@Path("creneaux")
	@POST
	public void creneaux(DossierCreneauxParameters parameters) {
		DossierNageurEntity nageur = dao.get(parameters.getNageurId());
		// Suppression des créneaux actuels
		if (StringUtils.isNotBlank(nageur.getCreneaux())) {
			String[] creneauSplit = nageur.getCreneaux().split(";");
			for (String creneau : creneauSplit) {
				if (StringUtils.isNotBlank(creneau)) {
					final String creneauId;
					if (StringUtils.contains(creneau, "_")) {
						creneauId = creneau.split("_")[1];
					} else {
						creneauId = creneau;
					}
					if (StringUtils.isNumeric(creneauId)) {
						SlotEntity slotEntity = slotDao.get(Long
								.valueOf(creneauId));
						slotEntity.setPlaceRestante(slotEntity
								.getPlaceRestante() + 1);
						slotDao.save(slotEntity);
					}
				}
			}
		}

		// Mise à jour du groupe et du créneau
		if (CollectionUtils.isEmpty(parameters.getCreneaux())) {
			nageur.setCreneaux(null);
		} else {
			StrBuilder builder = new StrBuilder();
			builder.appendWithSeparators(parameters.getCreneaux(), ";");
			nageur.setCreneaux(builder.toString());
			for (Long creneau : parameters.getCreneaux()) {
				SlotEntity creneauEntity = slotDao.get(creneau);
				creneauEntity
						.setPlaceRestante(creneauEntity.getPlaceRestante() - 1);
				slotDao.save(creneauEntity);
			}
		}
		dao.save(nageur);
	}
	
	private void sendConfirmation(DossierEntity dossier) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"webmaster@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation"));
			Address[] replyTo = {new InternetAddress(
					"contact@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation")};
			msg.setReplyTo(replyTo);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					dossier.getEmail()));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					"contact@asptt-toulouse-natation.com"));
			if(StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					dossier.getEmailsecondaire()));
			}
			
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
					dossier.getId(), Operator.EQUAL));
			List<DossierNageurEntity> nageurs = dao.find(criteria);

			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous compter parmi nous pour cette nouvelle saison sportive 2015-2016.<br />");
			
			message.append("Nous vous confirmons la bonne réception de votre dossier qui finalise ainsi votre inscription. <br />");
			List<String> certificatsManquants = new ArrayList<String>();
			for(DossierNageurEntity nageur: nageurs) {
				if(BooleanUtils.isFalse(nageur.getCertificat())) {
					certificatsManquants.add(nageur.getNom() + " " + nageur.getPrenom());
				}
			}
			if(CollectionUtils.isNotEmpty(certificatsManquants)) {
				StrBuilder certificatsBuilder = new StrBuilder();
				certificatsBuilder.append("Cependant il manque le certificat médical de ");
				certificatsBuilder.appendWithSeparators(certificatsManquants, ", ");
				certificatsBuilder.append(". Nous vous remercions de nous les faire parvenir avant le début des cours.<br />");
				message.append(certificatsBuilder.toString());
			}
			message.append("Les cours reprendront à partir du 21 septembre selon les bassins et jours de pratique (voir ci-dessous):<br />");

			message.append("<dl>");
			for (DossierNageurEntity nageur : nageurs) {
				try {
				GroupEntity group = groupeDao.get(nageur
						.getGroupe());
				message.append("<dt>").append(nageur.getNom()).append(" ").append(nageur.getPrenom()).append(" ").append(group.getTitle()).append("</dt>");
				}catch(Exception e) {
					LOG.severe("Erreur du groupe (adherent " + nageur.getId() + "): " + nageur.getGroupe() + " ");
				}
				for (String creneau : AdherentListResultBeanTransformer
						.getInstance().getCreneaux(nageur.getCreneaux())) {
					message.append("<dd>").append(creneau).append("</dd>");
				}
			}
			message.append("</dl>");
			
			message.append("<p>Sportivement,<br />"
					+ "Le secrétariat,<br />"
					+ "ASPTT Grand Toulouse Natation<br />"
					+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("ASPTT Toulouse Natation - Confirmation",
					"UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + dossier.getEmail(), e);
		}
	}
	
	private boolean hasAllCertificats(DossierEntity dossier) {
		boolean result = true;
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
				dossier.getId(), Operator.EQUAL));
		List<DossierNageurEntity> nageurs = dao.find(criteria);
		for(DossierNageurEntity nageur: nageurs) {
			result = result && BooleanUtils.isTrue(nageur.getCertificat());
		}
		return result;
	}
	
	@Path("certificat/{nageur}")
	@PUT
	public void certificat(@PathParam("nageur") Long nageur) {
		if(nageur != null) {
			DossierNageurEntity entity = dao.get(nageur);
			entity.setCertificat(Boolean.TRUE);
			dao.save(entity);
			
			DossierEntity dossier = dossierDao.get(entity.getDossier());
			if(DossierStatutEnum.PAIEMENT_COMPLET.equals(DossierStatutEnum.valueOf(dossier.getStatut()))
					&& hasAllCertificats(dossier)) {
				dossier.setStatut(DossierStatutEnum.INSCRIT.name());
				dossierDao.save(dossier);
				sendConfirmation(dossier);
			}
		}
	}
	
	@Path("commentaire")
	@POST
	public void commentaire(DossierCommentaireParameters parameters) {
		if(parameters.getDossier() != null && StringUtils.isNotBlank(parameters.getCommentaire())) {
			DossierEntity dossier = dossierDao.get(parameters.getDossier());
			dossier.setComment(parameters.getCommentaire());
			dossierDao.save(dossier);
		}
	}
	
	@Path("relancer/{dossier}")
	@PUT
	public void relancer(@PathParam("dossier") Long dossierId) {
		DossierEntity dossier = dossierDao.get(dossierId);
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"webmaster@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation"));
			Address[] replyTo = {new InternetAddress(
					"contact@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation")};
			msg.setReplyTo(replyTo);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					dossier.getEmail()));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					"contact@asptt-toulouse-natation.com"));
			if(StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					dossier.getEmailsecondaire()));
			}
			
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
					dossier.getId(), Operator.EQUAL));
			List<DossierNageurEntity> nageurs = dao.find(criteria);
			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous vous envoyons cet e-mail suite à votre inscription. Nous constatons que votre dossier n'est pas complet.<br />");
			message.append("En effet les informations suivantes ne nous sont pas encore parvenues:");
			message.append("<ul>");
			if(!DossierStatutEnum.PAIEMENT_COMPLET.name().equals(dossier.getStatut())) {
				message.append("<li>paiement de votre cotisation</li>");
			}
			
			List<String> certificatsManquants = new ArrayList<String>();
			for(DossierNageurEntity nageur: nageurs) {
				if(BooleanUtils.isFalse(nageur.getCertificat())) {
					certificatsManquants.add(nageur.getNom() + " " + nageur.getPrenom());
				}
			}
			if(CollectionUtils.isNotEmpty(certificatsManquants)) {
				StrBuilder certificatsBuilder = new StrBuilder();
				certificatsBuilder.appendWithSeparators(certificatsManquants, ", ");
				message.append("<li>certificat médicale (").append(certificatsBuilder.toString()).append(") </li>");
			}
			message.append("</ul>");
			message.append("<p>Nous vous demandons de nous informer quant à la suite à donner à votre dossier.</p>");
			
			message.append("<p>Sportivement,<br />"
					+ "Le secrétariat,<br />"
					+ "ASPTT Grand Toulouse Natation<br />"
					+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("ASPTT Toulouse Natation - Relance",
					"UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
			
			dossier.setReminded(new Date());
			dossier.setReminder(true);
			dossierDao.save(dossier);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + dossier.getEmail(), e);
		}
	}
}