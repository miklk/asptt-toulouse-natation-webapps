package com.asptttoulousenatation.core.inscription;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.asptttoulousenatation.core.adherent.AdherentBeanTransformer;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.lang.StatistiqueBase;
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
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.web.adherent.AdherentListResultBeanTransformer;
import com.google.appengine.api.datastore.Blob;

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
	public List<DossierResultBean> find(@QueryParam("query") String texteLibre, @QueryParam("groupe") Long groupe, @QueryParam("sansGroupe") Boolean sansGroupe, @QueryParam("dossierStatut") final String dossierStatut, @QueryParam("creneau") final Long creneau) {
		List<DossierResultBean> result = new ArrayList<DossierResultBean>();
		List<DossierNageurEntity> nageurs = new ArrayList<DossierNageurEntity>();
		
		boolean hasSelectGroupe = false;
		List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>();
		if(groupe != null && groupe > 0) {
			hasSelectGroupe = true;
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE,
					groupe, Operator.EQUAL));
			
			List<DossierNageurEntity> selectedNageur = dao.find(criteriaNageur);
			if (creneau != null) {
				selectedNageur = new ArrayList<DossierNageurEntity>(CollectionUtils.select(selectedNageur, new Predicate() {

					@Override
					public boolean evaluate(Object arg0) {
						DossierNageurEntity nageur = (DossierNageurEntity) arg0;
						return StringUtils.contains(nageur.getCreneaux(), creneau.toString());
					}
				}));
			}
			nageurs.addAll(selectedNageur);
		} else if (BooleanUtils.isTrue(sansGroupe)) {
			hasSelectGroupe = true;
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE,
					null, Operator.NULL));
			nageurs.addAll(dao.find(criteriaNageur));
		} 
		
		if(StringUtils.isNotBlank(dossierStatut)) {
			if(CollectionUtils.isEmpty(nageurs)) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
					dossierStatut, Operator.EQUAL));
			
			List<DossierEntity> entities = dossierDao.find(criteria);
			for(DossierEntity dossier: entities) {
				criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
						dossier.getId(), Operator.EQUAL));
				nageurs.addAll(dao.find(criteriaNageur));
			}
			} else {
				CollectionUtils.filter(nageurs, new Predicate() {
					
					@Override
					public boolean evaluate(Object arg0) {
						DossierNageurEntity nageur = (DossierNageurEntity) arg0;
						DossierEntity dossier = dossierDao.get(nageur.getDossier());
						return dossierStatut.equals(dossier.getStatut());
					}
				});
			}
		} else if(StringUtils.isNotBlank(texteLibre)) {
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
					criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
							1);
					criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
							dossier.getId(), Operator.EQUAL));
					List<DossierNageurEntity> nageursDossier = dao.find(criteriaNageur);
					if(CollectionUtils.isNotEmpty(nageursDossier)) {
						nageurs.addAll(nageursDossier);
					} else {
						result.add(DossierResultTransformer.getInstance().toUiDossier(dossier));
					}
				}
			} else {
				criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteriaNageur.add(new CriterionDao<String>(DossierNageurEntityFields.NOM,
						texteLibre.toUpperCase(), Operator.EQUAL));
				nageurs.addAll(dao.find(criteriaNageur));
			}
		} else if(!hasSelectGroupe) {
			nageurs = dao.getAll();
		}
		result.addAll(DossierResultTransformer.getInstance().toUi(nageurs));
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
			DossierNageurUi nageurUi = new DossierNageurUi();
			nageurUi.setNageur(entity);
			
			//Groupe
			if(entity.getGroupe() != null) {
				GroupEntity groupe = groupeDao.get(entity.getGroupe());
				nageurUi.setGroupe(GroupTransformer.getInstance().toUi(groupe));
			}
			
			//Creneaux
			if(StringUtils.isNotBlank(entity.getCreneaux())) {
				nageurUi.setCreneaux(AdherentBeanTransformer.getInstance().getCreneaux(
						entity.getCreneaux()));
			}
			
			//Certificat
			List<CriterionDao<? extends Object>> certificatCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
			certificatCriteria.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER, entity.getId(), Operator.EQUAL));
			List<DossierCertificatEntity> certificats = certificatDao.find(certificatCriteria);
			nageurUi.setHasCertificat(CollectionUtils.isNotEmpty(certificats));
			dossierUi.addNageur(nageurUi);
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
				groupe.setOccupe(groupe.getOccupe() + 1);
				groupeDao.save(groupe);
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
			nageur.setNouveau(Boolean.TRUE);
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
		for(DossierNageurUi nageurUi: parameters.getNageurs()) {
			DossierNageurEntity nageur = nageurUi.getNageur();
			//Groupe
			if(nageurUi.getGroupe() != null) {
				nageur.setGroupe(nageurUi.getGroupe().getId());
			}
			//Creneaux
			if (CollectionUtils.isNotEmpty(nageurUi.getCreneaux())) {
				String oldCreneaux = nageur.getCreneaux();
				Set<Long> newCreneaux = new HashSet<>();
				for (SlotUi slot : nageurUi.getCreneaux()) {
					newCreneaux.add(slot.getId());
					if (!StringUtils.contains(oldCreneaux, slot.getId().toString())) {
						SlotEntity slotEntity = slotDao.get(slot.getId());
						slotEntity.setPlaceRestante(slotEntity.getPlaceRestante() - 1);
						slotDao.save(slotEntity);
					}
				}
				StrBuilder creneauxAsString = new StrBuilder();
				creneauxAsString.appendWithSeparators(newCreneaux, ";");
				nageur.setCreneaux(creneauxAsString.toString());
			} else {
				nageur.setCreneaux(null);
			}
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
	
	@Path("/nageur/{nageur}")
	@DELETE
	public void deleteNageur(@PathParam("nageur") Long nageurId) {
		DossierNageurEntity nageur = dao.get(nageurId);
		if (nageur != null) {

			List<CriterionDao<? extends Object>> criteriaCertificat = new ArrayList<CriterionDao<? extends Object>>(1);
			criteriaCertificat
					.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER, nageur.getId(), Operator.EQUAL));
			List<DossierCertificatEntity> certificats = certificatDao.find(criteriaCertificat);
			for (DossierCertificatEntity certificat : certificats) {
				certificatDao.delete(certificat);
			}
			dao.delete(nageur);
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
	
	@Path("attente/{dossier}")
	@PUT
	public void attente(@PathParam("dossier") Long dossierId) {
		DossierEntity dossier = dossierDao.get(dossierId);
		dossier.setStatut(DossierStatutEnum.ATTENTE.name());
		dossierDao.save(dossier);
	}
	
	@Path("statistiques")
	@GET
	public DossierStatistiques statistiques() {
		DossierStatistiques result = new DossierStatistiques();
		result.setTotal(dossierDao.countAll());
		
		//Complets
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		result.setComplets(dossierDao.count(criteria));
		
		
		//Non payé
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PAIEMENT_COMPLET.name(), Operator.NOT_EQUAL));
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PAIEMENT_PARTIEL.name(), Operator.NOT_EQUAL));
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.INSCRIT.name(), Operator.NOT_EQUAL));
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.ANNULE.name(), Operator.NOT_EQUAL));
		result.setNonpayes(dossierDao.count(criteria));
		
		//payé
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PAIEMENT_COMPLET.name(), Operator.EQUAL));
		result.addPaye(dossierDao.count(criteria));
		
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PAIEMENT_PARTIEL.name(), Operator.EQUAL));
		result.addPaye(dossierDao.count(criteria));
		
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		result.addPaye(dossierDao.count(criteria));
		
		result.addDossier(new StatistiqueBase("Payés", new Long(result.getPayes()).intValue()));
		result.addDossier(new StatistiqueBase("Complets", new Long(result.getComplets()).intValue()));
		result.addDossier(new StatistiqueBase("Non payés", new Long(result.getNonpayes()).intValue()));
		
		//Nageur (nouveau, ancien)
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		long nageurTotal = 0l;
		int nageurNouveau = 0;
		int nageurRenouvellement = 0;
		for(DossierEntity dossier: dossierDao.find(criteria)) {
			List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
					dossier.getId(), Operator.EQUAL));
			for(DossierNageurEntity nageur: dao.find(criteriaNageur)) {
				nageurTotal++;
				if(BooleanUtils.isTrue(nageur.getNouveau())) {
					nageurNouveau++;
				}
				if(BooleanUtils.isFalse(nageur.getNouveau())) {
					nageurRenouvellement++;
				}
			}
			
		}
		
		result.setNageurs(nageurTotal);
		result.addNageur(new StatistiqueBase("Nouveau", nageurNouveau));
		result.addNageur(new StatistiqueBase("Renouvellement", nageurRenouvellement));
		return result;
	}
	
	@Path("/downloadCertificat/{nageur}")
	@GET
	@Produces({ "application/pdf" })
	public Response downloadCertificat(@PathParam("nageur") Long nageurId) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER, nageurId, Operator.EQUAL));
		DossierCertificatDao dossierCertificatDao = new DossierCertificatDao();
		List<DossierCertificatEntity> certificats = dossierCertificatDao.find(criteria);
		if (CollectionUtils.isNotEmpty(certificats)) {
			DossierCertificatEntity certificat = certificats.get(0);
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				out.write(certificat.getCertificatmedical().getBytes());
				String contentDisposition = "attachment;filename=" + StringUtils.defaultString(certificat.getFileName(), "certificat.jpg") + ";";
				return Response.ok(out.toByteArray(), "application/pdf")
						.header("content-disposition", contentDisposition).build();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Erreur lors de l'impression PDF", e);
			}
		}
		return Response.serverError().build();
	}
	
	@Path("/uploadCertificat/{nageur}")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadCertificat(@PathParam("nageur") Long nageurId,
			@FormDataParam("file") FormDataBodyPart certificatPart) {
		Response response = null;
		// Existence du nageur
		DossierNageurEntity nageur = dao.get(nageurId);
		if (nageur != null) {
			try {
				Blob certificat = new Blob(IOUtils.toByteArray(certificatPart.getValueAs(InputStream.class)));
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
				criteria.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER, nageurId, Operator.EQUAL));
				List<DossierCertificatEntity> entities = certificatDao.find(criteria);
				if (CollectionUtils.isNotEmpty(entities)) {
					for (DossierCertificatEntity entity : entities) {
						certificatDao.delete(entity);
					}
				}
				DossierCertificatEntity certificatEntity = new DossierCertificatEntity();
				certificatEntity.setDossier(nageurId);
				certificatEntity.setCertificatmedical(certificat);
				certificatEntity.setFileName(certificatPart.getContentDisposition().getFileName());
				certificatDao.save(certificatEntity);
				
				//Update dossier nageur
				nageur.setCertificat(true);
				dao.save(nageur);
				DossierEntity dossier = dossierDao.get(nageur.getDossier());
				if(DossierStatutEnum.PAIEMENT_COMPLET.equals(DossierStatutEnum.valueOf(dossier.getStatut()))
						&& hasAllCertificats(dossier)) {
					dossier.setStatut(DossierStatutEnum.INSCRIT.name());
					dossierDao.save(dossier);
					
					sendConfirmation(dossier);
				}
				response = Response.ok().build();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Récupération des certificats", e);
				response = Response.serverError().build();
			}
		} else {
			response = Response.serverError().build();
		}

		return response;
	}

	@Path("extraction/{fields}")
	@GET
	@Produces("text/csv; charset=UTF-8")
	public Response extraction(@PathParam("fields") String fields, @QueryParam("groupes") Set<Long> groupes) {
		String[] fieldsToChoose = fields.split("_");
		List<List<String>> extractions = new ArrayList<>();
		
		List<DossierNageurEntity> nageurs;
		if(CollectionUtils.isNotEmpty(groupes)) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(groupes.size());
			for (Long groupe : groupes) {
				criteria.add(new CriterionDao<Long>(
						DossierNageurEntityFields.GROUPE, groupe,
						Operator.EQUAL));
			}
			nageurs = dao.find(criteria, Operator.OR, null);
		} else {
			nageurs = dao.getAll();
		}
		for (DossierNageurEntity nageur : nageurs) {
			List<String> nageurValues = new ArrayList<>(fieldsToChoose.length);
			for (String field : fieldsToChoose) {
				switch (field) {
				case "NOM":
					nageurValues.add(nageur.getNom());
					break;
				case "PRENOM":
					nageurValues.add(nageur.getPrenom());
					break;
				case "GROUPE": {
					if (nageur.getGroupe() != null) {
						GroupEntity groupe = groupeDao.get(nageur.getGroupe());
						if (groupe != null) {
							nageurValues.add(groupe.getTitle());
						}
					}
				}
					break;
				case "SHORT":
					nageurValues.add(nageur.getShortPantalon());
					break;
				case "TSHIRT":
					nageurValues.add(nageur.getTshirt());
					break;
				case "MAILLOT":
					nageurValues.add(nageur.getMaillot());
					break;
				case "PROFESSION": {
					nageurValues.add(nageur.getProfession());
					DossierEntity dossier = dossierDao.get(nageur.getDossier());
					nageurValues.add(dossier.getParent1Profession());
					nageurValues.add(dossier.getParent2Profession());
				}
					break;
				default:// Do nothing
				}
			}
			extractions.add(nageurValues);
		}
		StrBuilder extractionAsString = new StrBuilder();
		//Build header
		extractionAsString.appendWithSeparators(fieldsToChoose, ",");
		if(fields.contains("PROFESSION")) {
			extractionAsString.append(",").append("PROFESSION PARENT 1").append(",").append("PROFESSION PARENT 2");
		}
		extractionAsString.appendNewLine();
		for (List<String> nageurFields : extractions) {
			StrBuilder nageurFieldsBuilder = new StrBuilder();
			nageurFieldsBuilder.appendWithSeparators(nageurFields, ",");
			extractionAsString.append(nageurFieldsBuilder.toString()).appendNewLine();
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			out.write(extractionAsString.toString().getBytes("UTF-8"));

			String contentDisposition = "attachment;filename=extraction.csv;";
			return Response.ok(out.toByteArray(), "text/csv").header("content-disposition", contentDisposition)
					.build();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Erreur when writing response (" + extractionAsString + ")", e);
			return Response.serverError().build();
		}
	}
	
	@Path("/certificat/{nageur}")
	@DELETE
	public void deleteCertificat(@PathParam("nageur") Long nageurId) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER, nageurId, Operator.EQUAL));
		List<DossierCertificatEntity> entities = certificatDao.find(criteria);
		if (CollectionUtils.isNotEmpty(entities)) {
			for (DossierCertificatEntity entity : entities) {
				certificatDao.delete(entity);
			}
		}
	}
}