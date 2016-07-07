package com.asptttoulousenatation.core.inscription;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.joda.time.DateTime;

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
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierCertificatEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierFactureEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierFactureEnum;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurPhotoEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.entity.inscription.ModePaiementEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierCertificatDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierFactureDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurPhotoDao;
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
	private DossierFactureDao factureDao = new DossierFactureDao();
	private DossierNageurPhotoDao nageurPhotoDao = new DossierNageurPhotoDao();
	
	
	@Path("/find")
	@GET
	@Consumes("application/json")
	public List<DossierResultBean> find(@QueryParam("query") String texteLibre, @QueryParam("groupe") Long groupe, @QueryParam("sansGroupe") Boolean sansGroupe, @QueryParam("dossierStatut") final String dossierStatut, @QueryParam("creneau") final Long creneau, @QueryParam("filter_facture") final Boolean facture, @QueryParam("filter_facture2") final Boolean facture2, @QueryParam("certificat") final Boolean certificat, @QueryParam("certificat2") final Boolean certificat2, @QueryParam("certificatNon") final Boolean certificatNon) {
		List<DossierResultBean> result = new ArrayList<DossierResultBean>();
		List<DossierNageurEntity> nageurs = new ArrayList<DossierNageurEntity>();
		
		boolean hasSelectGroupe = false;
		List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>();
		criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON,
				1L, Operator.EQUAL));
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
			criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
					1L, Operator.EQUAL));
			
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
				criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
						1L, Operator.EQUAL));
				List<DossierEntity> entities = dossierDao.find(criteria);
				if(CollectionUtils.isEmpty(entities)) {
					criteria = new ArrayList<CriterionDao<? extends Object>>(
							1);
					criteria.add(new CriterionDao<String>(DossierEntityFields.EMAILSECONDAIRE,
							texteLibre, Operator.EQUAL));
					criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON,
							1L, Operator.EQUAL));
					
					entities = dossierDao.find(criteria);
				}
				for(DossierEntity dossier: entities) {
					criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
							1);
					criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
							dossier.getId(), Operator.EQUAL));
					criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON,
							1L, Operator.EQUAL));
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
				criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON,
						1L, Operator.EQUAL));
				nageurs.addAll(dao.find(criteriaNageur));
			}
		} else if (!hasSelectGroupe) {
			if (BooleanUtils.isTrue(certificat)) {
				criteriaNageur.add(
						new CriterionDao<Boolean>(DossierNageurEntityFields.CERTIFICAT, certificat, Operator.EQUAL));
				nageurs = new ArrayList<>(dao.find(criteriaNageur));
			} else if (BooleanUtils.isTrue(certificat2)) {
				criteriaNageur.add(
						new CriterionDao<Boolean>(DossierNageurEntityFields.CERTIFICAT, Boolean.TRUE, Operator.EQUAL));
				List<DossierNageurEntity> nageursCertificat = dao.find(criteriaNageur);
				nageurs = new ArrayList<>();
				for (DossierNageurEntity nageur : nageursCertificat) {
					List<CriterionDao<? extends Object>> certificatCriteria = new ArrayList<CriterionDao<? extends Object>>(
							1);
					certificatCriteria.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER,
							nageur.getId(), Operator.EQUAL));
					List<DossierCertificatEntity> nageurCertificat = certificatDao.find(certificatCriteria);
					if (CollectionUtils.isNotEmpty(nageurCertificat)) {
						nageurs.add(nageur);
					}

				}

			} else if (BooleanUtils.isTrue(certificatNon)) {
				criteriaNageur.add(
						new CriterionDao<Boolean>(DossierNageurEntityFields.CERTIFICAT, Boolean.FALSE, Operator.EQUAL));
				nageurs = new ArrayList<>(dao.find(criteriaNageur)); 
			} else {
				criteriaNageur = new ArrayList<CriterionDao<? extends Object>>();
				criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON,
						1L, Operator.EQUAL));
				nageurs = new ArrayList<>(dao.find(criteriaNageur));
			}
		}
		
		CollectionUtils.filter(nageurs, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				DossierNageurEntity nageur = (DossierNageurEntity) arg0;
				
				final boolean keep;
				if(facture != null || facture2 != null) {
					DossierEntity dossier = dossierDao.get(nageur.getDossier());
					if(facture != null) {
						keep = BooleanUtils.toBoolean(dossier.getFacture()) && !factureDao.existsByDossierAndStatut(nageur.getDossier(), DossierFactureEnum.ENVOYE);
					} else {
						keep = BooleanUtils.toBoolean(dossier.getFacture()) && factureDao.existsByDossierAndStatut(nageur.getDossier(), DossierFactureEnum.ENVOYE);
					}
				} else {
					keep = true;
				}
				return keep;
			}
		});
		result.addAll(DossierResultTransformer.getInstance().toUi(nageurs));
		return result;
	}
	
	@Path("{dossier}")
	@GET
	public DossierUi findOne(@PathParam("dossier") Long dossierId) {
		DossierUi dossierUi = new DossierUi();
		DossierEntity dossier = dossierDao.get(dossierId);
		dossierUi.setPrincipal(dossier);
		
		//Facture
		DossierFactureEntity facture = factureDao.findByDossier(dossierId);
		if(facture != null) {
			dossierUi.setFacture(facture);
		} else if(BooleanUtils.toBoolean(dossier.getFacture())) {
			DossierFactureEntity newFacture = new DossierFactureEntity();
			newFacture.setDossier(dossierId);
			newFacture.setStatut(DossierFactureEnum.DEMANDE);
			DossierFactureEntity factureCreated = factureDao.save(newFacture);
			dossierUi.setFacture(factureCreated);
		}
		
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
			
			//Photo
			nageurUi.setHasPhoto(nageurPhotoDao.findByDossier(entity.getId()) != null);
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
			entity.setSaison(1L);
			entity.setEmail(parameters.getEmail());
			entity.setMotdepasse(parameters.getMdp());
			DossierEntity entityCreated = dossierDao.save(entity);
			DossierNageurEntity nageur = new DossierNageurEntity();
			nageur.setSaison(1L);
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
		dossier.setModepaiement(ModePaiementEnum.valueOf(parameters.getModePaiement()).name());
		dossier.setNumeroPaiement(parameters.getNumeroPaiement());
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
					"Madame, Monsieur,<p>Nous avons le plaisir de vous compter parmi nous pour cette nouvelle saison sportive 2016-2017.<br />");
			
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
			message.append("Les cours reprendront à partir du 19 septembre selon les bassins et jours de pratique (voir ci-dessous):<br />");

			message.append("<dl>");
			for (DossierNageurEntity nageur : nageurs) {
				try {
				GroupEntity group = groupeDao.get(nageur
						.getGroupe());
				message.append("<dt>").append(nageur.getNom()).append(" ").append(nageur.getPrenom()).append(" ").append(group.getTitle()).append("</dt>");
				} catch(Exception e) {
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
	public void relancer(@PathParam("dossier") Long dossierId, List<Long> dossiers) {
		Set<Long> dossierIds = new HashSet<>();
		if(CollectionUtils.isNotEmpty(dossiers)) {
			dossierIds.addAll(dossiers);
		} else {
			dossierIds.add(dossierId);
		}
		
		for(Long dossierIdentifier: dossierIds) {
			DossierEntity dossier = dossierDao.get(dossierIdentifier);
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
		
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
		result.setPotentiel(dossierDao.count(criteria));
		
		//Total
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PREINSCRIT.name(), Operator.NOT_EQUAL));
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.EXPIRE.name(), Operator.NOT_EQUAL));
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.ANNULE.name(), Operator.NOT_EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
		result.setTotal(dossierDao.count(criteria));
		
		//Complets
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
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
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
		result.setNonpayes(dossierDao.count(criteria));
		
		//payé
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PAIEMENT_COMPLET.name(), Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
		result.addPaye(dossierDao.count(criteria));
		
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PAIEMENT_PARTIEL.name(), Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
		result.addPaye(dossierDao.count(criteria));
		
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
		result.addPaye(dossierDao.count(criteria));
		
		result.addDossier(new StatistiqueBase("Payés", new Long(result.getPayes()).intValue()));
		result.addDossier(new StatistiqueBase("Complets", new Long(result.getComplets()).intValue()));
		result.addDossier(new StatistiqueBase("Non payés", new Long(result.getNonpayes()).intValue()));
		
		//Nageur (nouveau, ancien)
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				1L, Operator.EQUAL));
		long nageurTotal = 0l;
		int nageurNouveau = 0;
		int nageurRenouvellement = 0;
		for(DossierEntity dossier: dossierDao.find(criteria)) {
			List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
					dossier.getId(), Operator.EQUAL));
			criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.SAISON,
					1L, Operator.EQUAL));
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

	@Path("extraction/{fields}/{conditions}")
	@GET
	@Produces("text/csv; charset=UTF-8")
	public Response extraction(@PathParam("fields") String fields, @QueryParam("groupes") Set<Long> groupes, @PathParam("conditions") String conditions) {
		String[] fieldsToChoose = fields.split("_");
		String[] conditionsToAdd = conditions.split("_");
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
	
	@Path("facture/{dossier}")
	@PUT
	public void facture(@PathParam("dossier") Long dossier, List<Long> dossiers) {
		Set<Long> dossierIds = new HashSet<>();
		if (CollectionUtils.isNotEmpty(dossiers)) {
			dossierIds.addAll(dossiers);
		} else {
			dossierIds.add(dossier);
		}
		for (Long dossierId : dossierIds) {
			DossierFactureEntity facture = factureDao.findByDossier(dossierId);
			if (facture != null) {
				facture.setStatut(DossierFactureEnum.ENVOYE);
				factureDao.save(facture);
			} else { // Rattrapage car peut être demandé par e-mail
				DossierEntity dossierEntity = dossierDao.get(dossierId);
				dossierEntity.setFacture("true");
				dossierDao.save(dossierEntity);
				facture = new DossierFactureEntity();
				facture.setDossier(dossierId);
				facture.setStatut(DossierFactureEnum.ENVOYE);
				factureDao.save(facture);
			}
		}
	}
	
	@Path("/clean")
	@GET
	public int clean() {
		DateTime seuil = DateTime.now().minusDays(7);
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT, DossierStatutEnum.INITIALISE.name(),
				Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON, 1L,
				Operator.EQUAL));
		criteria.add(new CriterionDao<Date>(DossierEntityFields.UPDATED, seuil.toDate(),
				Operator.LESS));
		List<DossierEntity> entities = dossierDao.find(criteria);
		int count = 0;
		for (DossierEntity dossier : entities) {
			dossier.setStatut(DossierStatutEnum.ANNULE.name());
			dossierDao.save(dossier);
			count++;
		}
		LOG.log(Level.WARNING, count + " dossiers clean");
		return count;
	}
	
	@Path("/uploadPhoto/{nageur}")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadPhoto(@PathParam("nageur") Long nageurId, @FormDataParam("file") FormDataBodyPart photoPart) {
		Response response = null;
		// Existence du nageur
		DossierNageurEntity nageur = dao.get(nageurId);
		if (nageur != null) {
			try {
				Blob photo = new Blob(IOUtils.toByteArray(photoPart.getValueAs(InputStream.class)));

				// Create or update photo
				DossierNageurPhotoEntity photoEntity = nageurPhotoDao.findByDossier(nageur.getId());
				if (photoEntity == null) {
					photoEntity = new DossierNageurPhotoEntity();
					photoEntity.setDossier(nageur.getId());
				}
				photoEntity.setPhoto(photo);
				nageurPhotoDao.save(photoEntity);
				response = Response.ok().build();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "Ajout de la photo", e);
				response = Response.serverError().build();
			}
		} else {
			response = Response.serverError().build();
		}

		return response;
	}
	
	@Path("/downloadPhoto/{nageur}")
	@GET
	@Produces("application/octet-stream")
	public Response downloadPhoto(@PathParam("nageur") Long nageurId) {
		DossierNageurPhotoEntity photoEntity = nageurPhotoDao.findByDossier(nageurId);
		if(photoEntity != null) {
			return Response.ok(photoEntity.getPhoto().getBytes()).build(); 
		} else {
			return Response.noContent().build();
		}
	}
	
	@Path("/annuler/{nageur}")
	@PUT
	public void annuler(@PathParam("nageur") Long nageurId) {
		DossierNageurEntity nageur = dao.get(nageurId);
		DossierEntity dossier = dossierDao.get(nageur.getDossier());
		//Duplicate dossier if more than one nageur
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
				dossier.getId(), Operator.EQUAL));
		List<DossierNageurEntity> nageurs = dao.find(criteria);
		if(nageurs.size() > 1) {
			DossierEntity dossierCopied = new DossierEntity();
			dossier.copyAnnuler(dossierCopied);
			DossierEntity dossierCopiedSaved = dossierDao.save(dossierCopied);
			nageur.setDossier(dossierCopiedSaved.getId());
		} else {
			dossier.setStatut(DossierStatutEnum.ANNULE.name());
			dossierDao.save(dossier);
		}
		nageur.setCreneaux(null);
		dao.save(nageur);
	}
	
	@Path("/findByGroupeDay/{groupe}/{day}")
	@GET
	public List<DossierNageurEntity> findByGroupeDay(@PathParam("groupe") Long groupe, @PathParam("day") String day) {
		// Retrieve slots
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe,
				Operator.EQUAL));
		criteria.add(new CriterionDao<String>(SlotEntityFields.DAYOFWEEK, day,
				Operator.EQUAL));
		List<SlotEntity> lEntities = slotDao.find(criteria);
		Set<Long> slots = new HashSet<>();
		for(SlotEntity creneau: lEntities) {
			slots.add(creneau.getId());
		}
		
		//Find nageur du groupe
		criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.GROUPE,
				groupe, Operator.EQUAL));
		List<DossierNageurEntity> nageurs = dao.find(criteria);
		List<DossierNageurEntity> selectedNageurs = new ArrayList<>();
		for(DossierNageurEntity nageur: nageurs) {
			Set<Long> nageurCreneaux = AdherentBeanTransformer.getInstance().getCreneauIds(nageur.getCreneaux());
			if(CollectionUtils.containsAny(slots, nageurCreneaux)) {
				selectedNageurs.add(nageur);
			}
		}
		
		return selectedNageurs;
	}
	
	@Path("/init-saison")
	@GET
	public int nouvelleSaison() {
		int count = 0;
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(
				new CriterionDao<String>(DossierEntityFields.STATUT, DossierStatutEnum.INSCRIT.name(), Operator.EQUAL));
		List<DossierEntity> dossiers = dossierDao.find(criteria);
		for (DossierEntity dossier : dossiers) {
			try {
				DossierEntity dossier2 = new DossierEntity();
				dossier.copyInit(dossier2);
				dossier2.setStatut(DossierStatutEnum.INITIALISE.name());
				dossier2.setSaison(1L);
				DossierEntity cloned = dossierDao.save(dossier2);
				// Clone des nageurs
				List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(1);
				criteriaNageur.add(
						new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER, dossier.getId(), Operator.EQUAL));
				List<DossierNageurEntity> nageurs = dao.find(criteriaNageur);
				for (DossierNageurEntity nageur : nageurs) {
					DossierNageurEntity nageur2 = new DossierNageurEntity();
					nageur.copieInit(nageur2);
					nageur2.setSaison(1L);
					nageur2.setDossier(cloned.getId());
					nageur2.setNouveau(Boolean.FALSE);
					dao.save(nageur2);
				}
				count++;
			} catch (Exception e) {
				LOG.log(Level.WARNING, "Impossible de copier dossier #" + dossier.getId());
			}
		}
		LOG.log(Level.INFO, "init saison #" + count);
		return count;
	}
	
	@Path("/init-saison-2")
	@GET
	public int nouvelleSaison2() {
		int count = 0;
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(
				new CriterionDao<String>(DossierEntityFields.STATUT, DossierStatutEnum.PAIEMENT_COMPLET.name(), Operator.EQUAL));
		List<DossierEntity> dossiers = dossierDao.find(criteria);
		for (DossierEntity dossier : dossiers) {
			try {
				DossierEntity dossier2 = new DossierEntity();
				dossier.copyInit(dossier2);
				dossier2.setStatut(DossierStatutEnum.INITIALISE.name());
				dossier2.setSaison(1L);
				DossierEntity cloned = dossierDao.save(dossier2);
				// Clone des nageurs
				List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(1);
				criteriaNageur.add(
						new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER, dossier.getId(), Operator.EQUAL));
				List<DossierNageurEntity> nageurs = dao.find(criteriaNageur);
				for (DossierNageurEntity nageur : nageurs) {
					DossierNageurEntity nageur2 = new DossierNageurEntity();
					nageur.copieInit(nageur2);
					nageur2.setSaison(1L);
					nageur2.setDossier(cloned.getId());
					nageur2.setNouveau(Boolean.FALSE);
					dao.save(nageur2);
				}
				count++;
			} catch (Exception e) {
				LOG.log(Level.WARNING, "Impossible de copier dossier #" + dossier.getId());
			}
		}
		LOG.log(Level.INFO, "init saison #" + count);
		return count;
	}
	@Path("/doublons")
	@GET
	public int doublons() {
		int count = 0;
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(
				new CriterionDao<Long>(DossierEntityFields.SAISON, 1L, Operator.EQUAL));
		List<DossierEntity> dossiers = dossierDao.find(criteria);
		Map<String, List<Long>> doublons = new HashMap<>();
		for (DossierEntity dossier : dossiers) {
			String email = dossier.getEmail();
			final List<Long> ids;
			if(doublons.containsKey(email) && DossierStatutEnum.INITIALISE.name().equals(dossier.getStatut())) {
				ids = doublons.get(email);
				dossier.setStatut(DossierStatutEnum.ANNULE.name());
				dossierDao.save(dossier);
			} else {
				ids = new ArrayList<>(2);
			}
			ids.add(dossier.getId());
			doublons.put(email, ids);
		}
		for(Map.Entry<String, List<Long>> doublon: doublons.entrySet()) {
			List<Long> ids = doublon.getValue();
			if(ids.size() > 1) {
				count++;
			}
		}
		LOG.log(Level.WARNING, "doublons #" + count);
		return count;
	}
}