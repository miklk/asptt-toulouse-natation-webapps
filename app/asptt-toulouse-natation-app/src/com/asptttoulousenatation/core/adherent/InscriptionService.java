package com.asptttoulousenatation.core.adherent;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierCertificatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierCertificatEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.DossierCertificatDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.web.adherent.AdherentListResultBeanTransformer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.Blob;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Path("/inscription")
public class InscriptionService {
	
	private static final Logger LOG = Logger.getLogger(InscriptionService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private DossierDao dao = new DossierDao();
	private DossierNageurDao dossierNageurDao = new DossierNageurDao();
	private DossierCertificatDao dossierCertificatDao = new DossierCertificatDao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();
	
	@Path("/price")
	@POST
	@Consumes("application/json")
	public InscriptionDossiersUi price(InscriptionDossiersUi pDossiers) {
		DossierEntity principal = dao.save(pDossiers.getDossier());

		int dossierCount = 0;
		List<InscriptionDossierUi> dossiers = new ArrayList<InscriptionDossierUi>(
				CollectionUtils.select(pDossiers.getNageurs(),
						new Predicate() {

							@Override
							public boolean evaluate(Object pArg0) {
								return !((InscriptionDossierUi) pArg0)
										.isSupprimer();
							}
						}));
		
		//Update dossier
		InscriptionDossierTransfomer.getInstance().updateGroupes(dossiers);
		
		Collections.sort(dossiers, new Comparator<InscriptionDossierUi>() {

			@Override
			public int compare(InscriptionDossierUi pO1,
					InscriptionDossierUi pO2) {
				return pO1.getGroupe().getTarifWeight() >= pO2.getGroupe()
						.getTarifWeight() ? 1 : -1;
			}
		});
		
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
			dossier.getDossier().setTarif(tarif);
			dossierNageurDao.save(dossier.getDossier());
			pDossiers.addPrice(tarif);

			// Enregistrement du dossier
				dossier.getDossier().setDossier(
						principal.getId());
				buildDossier(dossier);
				dossierNageurDao.save(dossier.getDossier());
		}

		//Reload dossiers
		return getDossiers(principal);
	}
	
	private InscriptionDossiersUi getDossiers(DossierEntity pPrincipal) {
		InscriptionDossiersUi dossiers = new InscriptionDossiersUi();
		DossierEntity adherent = dao.get(pPrincipal.getId());
		dossiers.setDossier(adherent);

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
				adherent.getId(), Operator.EQUAL));
		List<DossierNageurEntity> entities = dossierNageurDao.find(criteria);

		dossiers.addDossier(entities);

		// Build UI dossier
		for (InscriptionDossierUi dossier : dossiers.getNageurs()) {
			DossierNageurEntity nageur = dossier.getDossier();

			// Get nouveau groupe et vérifie si on peut changer
			if (nageur.getGroupe() != null) {
				GroupEntity group = groupDao.get(nageur.getGroupe());
				dossier.setChoix(group.getInscription());
				dossier.setGroupe(groupTransformer.toUi(group));
			} else {
				dossier.setChoix(true);
			}
			dossier.setCreneauSelected(StringUtils.isNotBlank(nageur
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
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
					true);
			InscriptionDossiersUi dossiers = mapper.readValue(unscape,
					InscriptionDossiersUi.class);
			
			dossiers.getDossier().setStatut(DossierStatutEnum.PREINSCRIT.name());
			DossierEntity principal = dao.save(dossiers.getDossier());

			// Add certificats
			Set<Long> hasCertificat = new HashSet<>();
			if (MapUtils.isNotEmpty(dossiers.getCertificats())) {
				for (FormDataBodyPart certificatPart : certificats) {
					try {
						Blob certificat = new Blob(
								IOUtils.toByteArray(certificatPart
										.getValueAs(InputStream.class)));
						String fileName = new String(certificatPart
								.getContentDisposition().getFileName().getBytes(), "ISO-8859-1");
						String dossierIdAsString = dossiers.getCertificats()
						.get(fileName);
						if (StringUtils.isNotBlank(dossierIdAsString)) {
							Long dossierId = Long.valueOf(dossierIdAsString
									.split("_")[1]);

							List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
									1);
							criteria.add(new CriterionDao<Long>(
									DossierCertificatEntityFields.DOSSIER,
									dossierId, Operator.EQUAL));
							List<DossierCertificatEntity> entities = dossierCertificatDao
									.find(criteria);
							if (CollectionUtils.isNotEmpty(entities)) {
								for (DossierCertificatEntity entity : entities) {
									dossierCertificatDao.delete(entity);
								}
							}
							DossierCertificatEntity certificatEntity = new DossierCertificatEntity();
							certificatEntity.setDossier(dossierId);
							certificatEntity.setCertificatmedical(certificat);
							dossierCertificatDao.save(certificatEntity);
							hasCertificat.add(dossierId);
						}
					} catch (IOException e) {
						LOG.log(Level.SEVERE, "Récupération des certificats", e);
					}

				}
			}

			for (InscriptionDossierUi dossier : dossiers.getNageurs()) {
				if (!dossier.isSupprimer()) {
					buildDossier(dossier);
					if(hasCertificat.contains(dossier.getDossier().getId())) {
						dossier.getDossier().setCertificat(Boolean.TRUE);
					}
					dossierNageurDao.save(dossier.getDossier());
				}
			}

			result = getDossiers(principal);

			sendConfirmation(result);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error parsing JSON dossiers", e);
		}
		return result;
	}
	
	private void sendConfirmation(InscriptionDossiersUi dossierUi) {
		DossierEntity dossier = dossierUi.getDossier();
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

			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la pré-sélection de vos créneaux pour la nouvelle saison sportive 2015-2016.<br />"
							+ "Nous attendons (sous 8 jours) votre dossier signé, ainsi que votre réglement afin de finaliser ainsi votre inscription. Le certificat médical peut nous être envoyé dans un second temps. <br />"
							+ "Vous recevrez un e-mail de confirmation dès que votre inscription sera finalisée.<br />"
							+ "Voici les créneaux que vous avez sélectionné: <br />");

			message.append("<dl>");
			for (InscriptionDossierUi nageurUi : dossierUi.getNageurs()) {
				DossierNageurEntity nageur = nageurUi.getDossier();
				try {
				GroupEntity group = groupDao.get(nageur
						.getGroupe());
				
				message.append("<dt>").append(nageur.getNom()).append(" ").append(nageur.getPrenom()).append(" ").append(group.getTitle())
				.append(" (")
				.append("<a href=\"http://www.asptt-toulouse-natation.com/resources/inscription/print/"+nageur.getId()+"\">Télécharger le dossier</a>")
				.append(")")
						.append("</dt>");
				}catch(Exception e) {
					LOG.severe("Erreur du groupe (adherent " + nageur.getId() + "): " + nageur.getGroupe() + " ");
				}
				for (String creneau : AdherentListResultBeanTransformer
						.getInstance().getCreneaux(nageur.getCreneaux())) {
					message.append("<dd>").append(creneau).append("</dd>");
				}
				
				try {
					ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
					getDossierAsPdf(nageur, dossier, fileOutputStream);
					MimeBodyPart attachment = new MimeBodyPart();
					attachment.setFileName("asptt_dossier_" + nageur.getPrenom() + ".pdf");
					attachment.setContent(
							new ByteArrayInputStream(fileOutputStream.toByteArray()), "application/pdf");
					mp.addBodyPart(attachment);
				} catch (DocumentException | IOException e) {
					LOG.log(Level.SEVERE, "Erreur lors de la génération du fichier PDF pour l'e-mail: " + dossier.getEmail(), e);
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
	
	@Path("/update")
	@POST
	@Consumes("application/json")
	public InscriptionDossiersUi update(InscriptionDossiersUi dossiers) {
		DossierEntity principal = dao.save(dossiers.getDossier());
		
		for (InscriptionDossierUi dossier : dossiers.getNageurs()) {
			if(!dossier.isSupprimer()) {
				buildDossier(dossier);
				dossierNageurDao.save(dossier.getDossier());
			}
		}
		InscriptionDossiersUi result = getDossiers(principal);
		return result;
	}

	private void buildDossier(InscriptionDossierUi pDossier) {
		pDossier.getDossier().setCertificat(false);
		
		if (pDossier.getGroupe() != null) {
			pDossier.getDossier()
					.setGroupe(pDossier.getGroupe().getId());
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
	
	@Path("/print/{dossier}")
	@GET
	@Produces({"application/pdf"})
	public Response imprimerNew(@PathParam("dossier") Long dossierNageurId) {
		DossierNageurEntity adherent = dossierNageurDao.get(dossierNageurId);
		DossierEntity parent = dao.get(adherent.getDossier());
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			getDossierAsPdf(adherent, parent, out);
			String contentDisposition = "attachment;filename=inscription_asptt_natation.pdf;";
			return Response.ok(out.toByteArray(), "application/pdf")
					.header("content-disposition", contentDisposition).build();
		} catch (DocumentException | IOException e) {
			LOG.log(Level.SEVERE, "Erreur lors de l'impression PDF", e);
		}
		return Response.serverError().build();
	}

	private void getDossierAsPdf(DossierNageurEntity adherent,
			DossierEntity parent, OutputStream out) throws DocumentException,
			IOException {
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path.replace("WEB-INF/classes/", "");
		PdfReader reader = new PdfReader(new FileInputStream(path
				+ "doc/bulletin.pdf"));
		PdfStamper stamper = new PdfStamper(reader, out);
		AcroFields fields = stamper.getAcroFields();

		fields.setField("nom", adherent.getNom());
		fields.setField("prenom", adherent.getPrenom());
		Date dateNaissance = adherent.getNaissance();
		SimpleDateFormat formatDD = new SimpleDateFormat("dd");
		fields.setField("naissance_day", formatDD.format(dateNaissance));
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		fields.setField("naissance_month", formatMM.format(dateNaissance));
		SimpleDateFormat formatYYYY = new SimpleDateFormat("yyyy");
		fields.setField("naissance_year", formatYYYY.format(dateNaissance));

		
		fields.setField("adresse", parent.getAdresse());
		fields.setField("codepostal", parent.getCodepostal());
		fields.setField("ville", parent.getVille());
		fields.setField("telephone", parent.getTelephone());
		fields.setField("email", parent.getEmail());
		fields.setField("accord_nom", parent.getAccordNomPrenom());
		
		// Determine si l'adherent est mineur ou majeur
		LocalDate adherentAge = new LocalDate(adherent.getNaissance());
		int year = Years.yearsBetween(adherentAge, LocalDate.now()).getYears();
		if (year < 18) {
			fields.setField("mineur_parent", parent.getParent1Nom() + " "
					+ parent.getParent1Prenom());
			fields.setField("mineur_nom",
					adherent.getNom() + " " + adherent.getPrenom());
			fields.setField("profession", parent.getParent1Profession() + " / " + parent.getParent2Profession());
		} else {
			fields.setField("profession", adherent.getProfession());
		}

		if (StringUtils.isNotBlank(adherent.getCivilite())) {
			switch (adherent.getCivilite()) {
			case "0":
				fields.setField("civilite_h", "Yes");
				break;
			case "1":
				fields.setField("civilite_f", "Yes");
				break;
			default:
			}
		}

		if (BooleanUtils.isTrue(adherent.getNouveau())) {
			fields.setField("nouveau", "Yes");
		}
		
		if (BooleanUtils.isFalse(adherent.getNouveau())) {
			fields.setField("renouvellement", "Yes");
		}

		GroupEntity group = groupDao.get(adherent.getGroupe());
		if (BooleanUtils.isTrue(group.getLicenceFfn())) {
			fields.setField("licence_ffn_oui", "Yes");
		} else {
			fields.setField("licence_ffn_non", "Yes");
		}
		
		if(BooleanUtils.isFalse(group.getCompetition())) {
			fields.setField("licence_comp_oui", "Yes");
		} else {
			fields.setField("licence_comp_non", "Yes");
		}

		fields.setField(
				"accident_nom_1",
				StringUtils.defaultString(parent.getAccidentNom1())
						+ " "
						+ StringUtils.defaultString(parent.getAccidentPrenom1()));
		fields.setField("accident_telephone_1", parent.getAccidentTelephone1());
		fields.setField(
				"accident_nom_2",
				StringUtils.defaultString(parent.getAccidentNom2())
						+ " "
						+ StringUtils.defaultString(parent.getAccidentPrenom2()));
		fields.setField("accident_telephone_2", parent.getAccidentTelephone2());
		
		fields.setField("montant_du", group.getTarif().toString());
		fields.setField("remise", Integer.toString((group.getTarif() - adherent.getTarif())));
		fields.setField("montant_regler", adherent.getTarif().toString());

		stamper.close();
		reader.close();
	}
	
	@Path("/rappel")
	@GET
	public int rappel() {
		Date seuilInscription = DateTime.now().minusDays(5).toDate();
		List<DossierEntity> entities = dao.getAll();
		int count = 0;
		for(DossierEntity dossier: entities) {
			if(dossier.getUpdated().before(seuilInscription)) {
				count++;
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

					StringBuilder message = new StringBuilder(
							"Madame, Monsieur,<p>Vous avez effectué une demande d'inscription au club il y a 5 jours. Nous n'avons pas encore reçu votre paiement.<br /> Nous tenons à vous rappeler qu'au bout de 8 jours, votre dossier sera supprimé et les créneaux sélectionnés libérés pour d'autres adhérents.<br />");

					message.append("<p>Sportivement,<br />"
							+ "Le secrétariat,<br />"
							+ "ASPTT Grand Toulouse Natation<br />"
							+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
					htmlPart.setContent(message.toString(), "text/html");
					mp.addBodyPart(htmlPart);

					msg.setSubject("ASPTT Toulouse Natation - Rappel",
							"UTF-8");
					msg.setContent(mp);
					Transport.send(msg);
				} catch (MessagingException | UnsupportedEncodingException e) {
					LOG.log(Level.SEVERE,"Erreur pour l'e-mail: " + dossier.getEmail(), e);
				}
			}
		}
		LOG.log(Level.WARNING, count + " dossiers rappelés");
		return count;
	}
	
	@Path("/expire")
	@GET
	public int expire() {
		Date seuilInscription = DateTime.now().minusDays(10).toDate();
		List<DossierEntity> entities = dao.getAll();
		int count = 0;
		for(DossierEntity dossier: entities) {
			if(dossier.getUpdated().before(seuilInscription)) {
				dossier.setStatut(DossierStatutEnum.EXPIRE.name());
				count++;
				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteria.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
						dossier.getId(), Operator.EQUAL));
				List<DossierNageurEntity> nageurs = dossierNageurDao.find(criteria);
				for(DossierNageurEntity nageur: nageurs) {
					nageur.setCreneaux(StringUtils.EMPTY);
					dossierNageurDao.save(nageur);
				}
			}
		}
		LOG.log(Level.WARNING, count + " dossiers expirés");
		return count;
	}
	
	@Path("/copy")
	@GET
	public int copyPreviousYear() {
		List<DossierEntity> removies = dao.getAll();
		for(DossierEntity removy: removies) {
			dao.delete(removy);
		}
		List<DossierNageurEntity> removiesNageur = dossierNageurDao.getAll();
		for(DossierNageurEntity removyNageur: removiesNageur) {
			dossierNageurDao.delete(removyNageur);
		}
		return copyPreviousYear(0, 100);
	}
	
	@Path("/copy2/{limit}")
	@GET
	public int copyPreviousYear2(@PathParam("limit") String limit) {
		String[] limitArray = limit.split(",");
		return copyPreviousYear(Integer.parseInt(limitArray[0]), Integer.parseInt(limitArray[1]));
	}
	
	private int copyPreviousYear(int debut, int fin) {
		int count = 0;
		Map<Long, Long> principalDossier = new HashMap<>();
		Inscription2Dao inscriptionDao = new Inscription2Dao();
		
		//Build des principaux
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(InscriptionEntityFields.PRINCIPAL,
				null, Operator.NULL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria, debut, fin);
		for(InscriptionEntity2 entity: entities) {
			DossierEntity dossier = new DossierEntity();
			dossier.setAccidentNom1(entity.getAccidentNom1());
			dossier.setAccidentPrenom1(entity.getAccidentPrenom1());
			dossier.setAccidentTelephone1(entity.getAccidentTelephone1());
			dossier.setAccidentNom2(entity.getAccidentNom2());
			dossier.setAccidentPrenom2(entity.getAccidentPrenom2());
			dossier.setAccidentTelephone2(entity.getAccidentTelephone2());
			dossier.setAccordNomPrenom(entity.getAccordNomPrenom());
			dossier.setAdresse(entity.getAdresse());
			dossier.setCodepostal(entity.getCodepostal());
			dossier.setVille(entity.getVille());
			dossier.setEmail(entity.getEmail());
			dossier.setEmailsecondaire(entity.getEmailsecondaire());
			dossier.setMotdepasse(entity.getMotdepasse());
			dossier.setTelephone(entity.getTelephone());
			dossier.setParent1Profession(entity.getProfessionTextPere());
			dossier.setParent2Profession(entity.getProfessionTextMere());
			DossierEntity dossierCreated = dao.save(dossier);
			count++;
			
			principalDossier.put(entity.getId(), dossierCreated.getId());
			
			DossierNageurEntity nageur = getNageur(entity);
			nageur.setDossier(dossierCreated.getId());
			dossierNageurDao.save(nageur);
			
			List<CriterionDao<? extends Object>> criteriaEnfant = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaEnfant.add(new CriterionDao<Long>(InscriptionEntityFields.PRINCIPAL,
					entity.getId(), Operator.EQUAL));
			List<InscriptionEntity2> entitiesEnfants = inscriptionDao.find(criteriaEnfant);
			for(InscriptionEntity2 enfant: entitiesEnfants) {
				DossierNageurEntity nageurEnfant = getNageur(enfant);
				nageurEnfant.setDossier(dossierCreated.getId());
				dossierNageurDao.save(nageurEnfant);
			}
		}
		LOG.warning(count + " dossiers créés");
		return count;
	}
	
	private DossierNageurEntity getNageur(InscriptionEntity2 entity) {
		DossierNageurEntity nageur = new DossierNageurEntity();
		nageur.setCivilite(entity.getCivilite());
		nageur.setMaillot(entity.getMaillot());
		nageur.setNom(entity.getNom());
		nageur.setNouveau(Boolean.FALSE);
		nageur.setPrenom(entity.getPrenom());
		nageur.setProfession(entity.getProfession());
		nageur.setShortPantalon(entity.getShortPantalon());
		nageur.setTshirt(entity.getTshirt());
		nageur.setNouveau(Boolean.FALSE);
		if(StringUtils.isNotBlank(entity.getDatenaissance())) {
			try {
			DateTime dateNaissance = DateTime.parse(entity.getDatenaissance(),
					DateTimeFormat.forPattern("yyyy-MM-dd"));
			nageur.setNaissance(dateNaissance.toDate());
			// Groupe
			Long groupe = entity.getNouveauGroupe();
			if (entity.getNouveauGroupe() != null) {// Aquagym, Adultes debutant
													// (1035002), Loisir adultes
													// (1039001)
				if (groupe.longValue() == 1040001L
						|| groupe.longValue() == 1035002L
						|| groupe.longValue() == 1039001L) {
					nageur.setGroupe(groupe);
				} else if (groupe.longValue() == 1035001L) {// Marsouins
					if (dateNaissance.year().get() == 2003) {
						nageur.setGroupe(1034001L);
					} else {
						nageur.setGroupe(groupe);
					}
				} else if (groupe.longValue() == 1034001L) {// Cachalots
					if (dateNaissance.year().get() == 2003) {
						nageur.setGroupe(1029003L);
					} else {
						nageur.setGroupe(groupe);
					}
				} else if (groupe.longValue() == 1024001L) {// Tetard
					nageur.setGroupe(1025001L);
				} else {
					nageur.setGroupe(groupe);
				}
			}
			} catch(IllegalArgumentException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
		return nageur;
	}
	
	@Path("/naissance")
	@GET
	@Produces("application/json")
	public Set<String> dateNaissance() {
		Set<String> naissances = new HashSet<>();
		for(InscriptionEntity2 entity: new Inscription2Dao().getAll()) {
			naissances.add(entity.getDatenaissance());
		}
		return naissances;
	}
	
	@Path("statut")
	@GET
	public void setStatut() {
		for(DossierEntity entity: dao.getAll()) {
			entity.setStatut(DossierStatutEnum.INITIALISE.name());
			dao.save(entity);
		}
	}
}