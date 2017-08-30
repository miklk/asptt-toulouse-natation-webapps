package com.asptttoulousenatation.core.adherent;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import com.asptttoulousenatation.core.groupe.GroupTransformer;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.inscription.DossierService;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierCertificatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierCertificatEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierFactureEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierFactureEnum;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurPhotoEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierStatutEnum;
import com.asptttoulousenatation.core.server.dao.inscription.DossierCertificatDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierFactureDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurPhotoDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.util.CreneauBuilder;
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
	private DossierNageurPhotoDao dossierNageurPhotoDao = new DossierNageurPhotoDao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();
	private DossierFactureDao factureDao = new DossierFactureDao();
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
			//Determine le nombre de séances
			boolean unique = !dossier.hasMultipleCreneaux();
			GroupEntity group = groupDao.get(dossier.getGroupe().getId());
			int tarif = 0; 
			switch (dossierCount) {
			case 1:
				tarif = GroupEntity.getTarif((unique ? group.getTarifUnique(): group.getTarif()));
				break;
			case 2:
				tarif = GroupEntity.getTarif((unique ? group.getTarifUnique2(): group.getTarif2()));
				break;
			case 3:
				tarif = GroupEntity.getTarif((unique ? group.getTarifUnique3(): group.getTarif3()));
				break;
			case 4:
				tarif = GroupEntity.getTarif((unique ? group.getTarifUnique4(): group.getTarif4()));
				break;
			default:
				tarif = GroupEntity.getTarif((unique ? group.getTarifUnique4(): group.getTarif4()));
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
			
			if(!dossiers.getDossier().getStatut().equals(DossierStatutEnum.PAIEMENT_PARTIEL) 
					&& !dossiers.getDossier().getStatut().equals(DossierStatutEnum.PAIEMENT_COMPLET)
					&& !dossiers.getDossier().getStatut().equals(DossierStatutEnum.INSCRIT)) {
				dossiers.getDossier().setStatut(DossierStatutEnum.PREINSCRIT.name());
			}
			DossierEntity principal = dao.save(dossiers.getDossier());
			
			//Facture
			DossierFactureEntity facture = factureDao.findByDossier(principal.getId());
			if(BooleanUtils.toBoolean(principal.getFacture())) {
				if(facture == null) {
					facture = new DossierFactureEntity();
					facture.setDossier(principal.getId());
					facture.setStatut(DossierFactureEnum.DEMANDE);
					factureDao.save(facture);
				}
			} else {
				//Supprime la demande si case décoché et facture existe.
				if(facture != null) {
					factureDao.delete(facture.getId());
				}
			}
			

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
							String[] dossierIdSplit = dossierIdAsString.split("_");
							if (dossierIdSplit.length > 1) {
								Long dossierId = Long.valueOf(dossierIdSplit[1]);

								List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
										1);
								criteria.add(new CriterionDao<Long>(DossierCertificatEntityFields.DOSSIER, dossierId,
										Operator.EQUAL));
								List<DossierCertificatEntity> entities = dossierCertificatDao.find(criteria);
								if (CollectionUtils.isNotEmpty(entities)) {
									for (DossierCertificatEntity entity : entities) {
										dossierCertificatDao.delete(entity);
									}
								}
								DossierCertificatEntity certificatEntity = new DossierCertificatEntity();
								certificatEntity.setDossier(dossierId);
								certificatEntity.setCertificatmedical(certificat);
								certificatEntity.setFileName(certificatPart.getContentDisposition().getFileName());
								dossierCertificatDao.save(certificatEntity);
								hasCertificat.add(dossierId);
							}
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

			//Message avant début des cours
//			StringBuilder message = new StringBuilder(
//					"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la pré-sélection de vos créneaux pour la nouvelle saison sportive 2016-2017.<br />"
//							+ "Nous attendons (sous 8 jours) votre dossier signé, ainsi que votre réglement afin de finaliser ainsi votre inscription. Le certificat médical peut nous être envoyé dans un second temps. <br />"
//							+ "Vous recevrez un e-mail de confirmation dès que votre inscription sera finalisée.<br />"
//							+ "Voici les créneaux que vous avez sélectionné: <br />");
			
			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la pré-sélection de vos créneaux pour la nouvelle saison sportive 2017-2018.<br />"
							+ "Nous attendons votre dossier complet : fiche signée, réglement et certificat médical. Il ne sera pas possible d'accès au bassin sans dossier complet.<br />"
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
				for (String creneau : CreneauBuilder
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
					+ "ASPTT Toulouse Natation<br />"
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
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public InscriptionDossiersUi update(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
			@FormDataParam("file") FormDataBodyPart photo, @FormDataParam("action") String pAction, @FormDataParam("nageur") Long nageur) {
		InscriptionDossiersUi result = null;
		try {
			String unscape = URLDecoder.decode(pAction, "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			InscriptionDossiersUi dossiers = mapper.readValue(unscape, InscriptionDossiersUi.class);
			DossierEntity principal = dao.save(dossiers.getDossier());

			for (InscriptionDossierUi dossier : dossiers.getNageurs()) {
				if (!dossier.isSupprimer()) {
					buildDossier(dossier);
					dossierNageurDao.save(dossier.getDossier());
				}
			}
			result = getDossiers(principal);
			
			
			// Add photo
			if (photo != null) {
				try {

					Blob photoBlob = new Blob(IOUtils.toByteArray(photo.getValueAs(InputStream.class)));
					DossierNageurPhotoEntity currentPhoto = dossierNageurPhotoDao.findByDossier(nageur);
					if (currentPhoto != null) {
						dossierNageurPhotoDao.delete(currentPhoto);
					}
					DossierNageurPhotoEntity photoEntity = new DossierNageurPhotoEntity();
					photoEntity.setDossier(nageur);
					photoEntity.setPhoto(photoBlob);
					dossierNageurPhotoDao.save(photoEntity);
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "Récupération de la photo", e);
				}
			}

			
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error parsing JSON dossiers", e);
		}
		return result;
	}

	private void buildDossier(InscriptionDossierUi pDossier) {
		pDossier.getDossier().setCertificat(false);
		boolean checkSecond = false;
		if (pDossier.getGroupe() != null) {
			GroupEntity groupe = groupDao.get(pDossier.getGroupe().getId());
			checkSecond = groupe.getSecondes();
			if(BooleanUtils.isTrue(groupe.getCompetition())) {
				groupe.setOccupe(groupe.getOccupe() + 1);
				groupDao.save(groupe);
			}
			pDossier.getDossier().setGroupe(groupe.getId());
		}

		if (pDossier.getCreneaux() != null) {
			Set<SlotUi> creneaux = new HashSet<SlotUi>();
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
			
			//Check only one first
			Set<Long> ids = cleanCreneaux(checkSecond, creneaux);
			StrBuilder creneauxAsString = new StrBuilder();
			creneauxAsString.appendWithSeparators(ids, ";");
			pDossier.getDossier().setCreneaux(creneauxAsString.toString());
		}
	}

	private boolean buildCreneau(Set<SlotUi> pCreneaux, List<SlotUi> pSlots,
			String pOldCreneaux) {
		boolean containsFirst = false;
		for (SlotUi slot : pSlots) {
			if (slot.isSelected()) {
				containsFirst = !slot.isSecond();
				pCreneaux.add(slot);
				if (!StringUtils
						.contains(pOldCreneaux, slot.getId().toString())) {
					SlotEntity slotEntity = slotDao.get(slot.getId());
					slotEntity
							.setPlaceRestante(slotEntity.getPlaceRestante() - 1);
					slotDao.save(slotEntity);
				}
			}
		}
		return containsFirst;
	}
	
	private Set<Long> cleanCreneaux(boolean checkSecond, Set<SlotUi> creneaux) {
		Set<Long> ids = new HashSet<>();
		boolean hasOneFirst = false;
		for (SlotUi creneau : creneaux) {
			if (checkSecond) {
				if (!hasOneFirst || creneau.isSecond()) {
					ids.add(creneau.getId());
					hasOneFirst = !creneau.isSecond();
				}
			} else {
				ids.add(creneau.getId());
			}
		}
		return ids;
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
		
		initPdf(fields);

		fields.setField("nom", adherent.getNom());
		fields.setField("prenom", adherent.getPrenom());
		DateTime naissanceAsDate = new DateTime(adherent.getNaissance().getTime(), DateTimeZone.UTC).plusHours(3);
		fields.setField("naissance_day", "" + naissanceAsDate.getDayOfMonth());
		fields.setField("naissance_month", "" + naissanceAsDate.getMonthOfYear());
		fields.setField("naissance_year", "" + naissanceAsDate.getYear());

		
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
			fields.setField("profession", parent.getParent1Profession() + " / " + StringUtils.defaultString(parent.getParent2Profession()));
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
		
		if(BooleanUtils.isTrue(adherent.getFonctionnaire())) {
			fields.setField("fonctionnaire_oui", "Yes");
		} else {
			fields.setField("fonctionnaire_non", "Yes");
		}

		GroupEntity group = groupDao.get(adherent.getGroupe());
		if (BooleanUtils.isTrue(group.getLicenceFfn())) {
			fields.setField("licence_ffn_oui", "Yes");
		} else {
			fields.setField("licence_loisir", "Yes");
		}
		
		if(BooleanUtils.isFalse(group.getCompetition())) {
			fields.setField("licence_comp_non", "Yes");
		} else {
			fields.setField("licence_comp_oui", "Yes");
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
		
		Integer tarifStatutaire = 17;
		fields.setField("tarif_statutaire", tarifStatutaire.toString());
		Integer tarifFsasptt = 17;
		if(BooleanUtils.isTrue(group.getLicenceFfn())) {
			tarifFsasptt = 2;
		}
		fields.setField("tarif_fsasptt", tarifFsasptt.toString());
		final Double tarifFFn;
		if (BooleanUtils.isTrue(group.getLicenceFfn())) {
			year = Years.yearsBetween(adherentAge, LocalDate.now()).getYears();
			if (year < 10) {
				tarifFFn = 20.45;
			} else {
				tarifFFn = 33.85;
			}
		} else {
			tarifFFn = 0.0;
		}
		fields.setField("tarif_ffn", tarifFFn.toString());
		
		Double tarifSection = group.getTarif() - tarifStatutaire - tarifFsasptt - tarifFFn;
		fields.setField("tarif_section", tarifSection.toString());
		
		fields.setField("montant_du", group.getTarif().toString());
		fields.setField("remise", Integer.toString((group.getTarif() - adherent.getTarif())));
		fields.setField("montant_regler", adherent.getTarif().toString());

		stamper.close();
		reader.close();
	}
	
	private void initPdf(AcroFields fields) throws IOException, DocumentException {
		fields.setField("civilite_h", "No");
		fields.setField("civilite_f", "No");
		fields.setField("nouveau", "No");
		fields.setField("renouvellement", "No");
		fields.setField("fonctionnaire_oui", "No");
		fields.setField("fonctionnaire_non", "No");
		fields.setField("licence_ffn_oui", "No");
		fields.setField("licence_loisir", "No");
		fields.setField("licence_comp_oui", "No");
		fields.setField("licence_comp_non", "No");
	}
	
	@Path("/rappel")
	@GET
	public int rappel() {
		Date seuilInscription = DateTime.now().minusDays(5).toDate();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(2);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT, DossierStatutEnum.PREINSCRIT.name(),
				Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON, DossierService.NEW_SAISON,
				Operator.EQUAL));
		List<DossierEntity> entities = dao.find(criteria);
		int count = 0;
		List<String> dossiersRappeler = new ArrayList<>();
		for (DossierEntity dossier : entities) {
			if (dossier.getUpdated().before(seuilInscription) && !BooleanUtils.toBoolean(dossier.getReminder())) {
				count++;
				dossiersRappeler.add(dossier.getEmail());
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				try {
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();

					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(
							new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
					Address[] replyTo = {
							new InternetAddress("contact@asptt-toulouse-natation.com", "ASPTT Toulouse Natation") };
					msg.setReplyTo(replyTo);
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dossier.getEmail()));
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress("contact@asptt-toulouse-natation.com"));
					if (StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress(dossier.getEmailsecondaire()));
					}

					StringBuilder message = new StringBuilder(
							"Madame, Monsieur,<p>Vous avez effectué une demande d'inscription au club il y a 5 jours. Nous n'avons pas encore reçu votre paiement.<br /> Nous tenons à vous rappeler qu'au bout de 8 jours, votre dossier sera supprimé et les créneaux sélectionnés libérés pour d'autres adhérents.<br />");

					message.append("<p>Sportivement,<br />" + "Le secrétariat,<br />"
							+ "ASPTT Toulouse Natation<br />"
							+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
					htmlPart.setContent(message.toString(), "text/html");
					mp.addBodyPart(htmlPart);

					msg.setSubject("ASPTT Toulouse Natation - Rappel", "UTF-8");
					msg.setContent(mp);
					Transport.send(msg);
					dossier.setReminded(new DateTime().toDate());
					dossier.setReminder(true);
					dao.save(dossier);
				} catch (MessagingException | UnsupportedEncodingException e) {
					LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + dossier.getEmail(), e);
				}
			}
		}

		// Rapport
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("contact@asptt-toulouse-natation.com"));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("remi.lacaze@asptt-toulouse-natation.com"));

			StrBuilder message = new StrBuilder("<p>").append(count).append(" dossiers vont expirer dans 5 jours.</p>");
			message.append("<ul>");
			for (String dossierRappeler : dossiersRappeler) {
				message.append("<li>").append(dossierRappeler).append("</li>");
			}
			message.append("</ul>");

			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Rapport  - dossiers arrivant à expiration", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail de rapport", e);
		}

		LOG.log(Level.WARNING, count + " dossiers rappelés");
		return count;
	}
	
	@Path("/rappel2")
	@GET
	public int rappel2() {
		Date seuilInscription = DateTime.now().minusDays(8).toDate();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT, DossierStatutEnum.PREINSCRIT.name(),
				Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON, DossierService.NEW_SAISON,
				Operator.EQUAL));
		List<DossierEntity> entities = dao.find(criteria);
		int count = 0;
		List<String> dossiersRappeler = new ArrayList<>();
		for (DossierEntity dossier : entities) {
			if (BooleanUtils.toBoolean(dossier.getReminder()) && dossier.getReminded().before(seuilInscription)) {
				count++;
				dossiersRappeler.add(dossier.getEmail());
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				try {
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();

					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(
							new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
					Address[] replyTo = {
							new InternetAddress("contact@asptt-toulouse-natation.com", "ASPTT Toulouse Natation") };
					msg.setReplyTo(replyTo);
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dossier.getEmail()));
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress("contact@asptt-toulouse-natation.com"));
					if (StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress(dossier.getEmailsecondaire()));
					}

					StringBuilder message = new StringBuilder(
							"Madame, Monsieur,<p>Vous avez effectué une demande d'inscription au club il y a 8 jours. Nous n'avons pas encore reçu votre paiement.<br /> Nous vous informons que votre dossier sera supprimé ce soir et que les créneaux sélectionnés libérés pour d'autres adhérents.<br />");

					message.append("<p>Sportivement,<br />" + "Le secrétariat,<br />"
							+ "ASPTT Toulouse Natation<br />"
							+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
					htmlPart.setContent(message.toString(), "text/html");
					mp.addBodyPart(htmlPart);

					msg.setSubject("ASPTT Toulouse Natation - Rappel", "UTF-8");
					msg.setContent(mp);
					Transport.send(msg);
					dossier.setReminded(new DateTime().toDate());
					dossier.setReminder(true);
					dao.save(dossier);
				} catch (MessagingException | UnsupportedEncodingException e) {
					LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + dossier.getEmail(), e);
				}
			}
		}

		// Rapport
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("contact@asptt-toulouse-natation.com"));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("remi.lacaze@asptt-toulouse-natation.com"));

			StrBuilder message = new StrBuilder("<p>").append(count).append(" dossiers vont expirer dans 2 jours.</p>");
			message.append("<ul>");
			for (String dossierRappeler : dossiersRappeler) {
				message.append("<li>").append(dossierRappeler).append("</li>");
			}
			message.append("</ul>");

			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Rapport  - dossiers arrivant à expiration", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail de rapport", e);
		}

		LOG.log(Level.WARNING, count + " dossiers rappelés");
		return count;
	}
	
	@Path("/expire")
	@GET
	public int expire() {
		Date seuilInscription = DateTime.now().minusDays(10).toDate();
		
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT,
				DossierStatutEnum.PREINSCRIT.name(), Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				DossierService.NEW_SAISON, Operator.EQUAL));
		List<DossierEntity> entities = dao.find(criteria);
		int count = 0;
		for(DossierEntity dossier: entities) {
			if(dossier.getCreated().before(seuilInscription) && BooleanUtils.toBoolean(dossier.getReminder())) {
				dossier.setStatut(DossierStatutEnum.EXPIRE.name());
				count++;
				List<CriterionDao<? extends Object>> criteriaNageur = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteriaNageur.add(new CriterionDao<Long>(DossierNageurEntityFields.DOSSIER,
						dossier.getId(), Operator.EQUAL));
				List<DossierNageurEntity> nageurs = dossierNageurDao.find(criteriaNageur);
				for(DossierNageurEntity nageur: nageurs) {
					nageur.setCreneaux(StringUtils.EMPTY);
					dossierNageurDao.save(nageur);
				}
				dao.save(dossier);
			}
		}
		LOG.log(Level.WARNING, count + " dossiers expirés");
		return count;
	}
	
	@Path("/rappel-certifcats")
	@GET
	public int rappelCertificats() {
		List<DossierEntity> entities = dao.findByStatus(DossierStatutEnum.PAIEMENT_COMPLET, DossierStatutEnum.PAIEMENT_PARTIEL, DossierStatutEnum.PREINSCRIT);
		int count = 0;
		List<String> dossiersRappeler = new ArrayList<>();
		for (DossierEntity dossier : entities) {
			List<DossierNageurEntity> nageurs = dossierNageurDao.findByDossier(dossier.getId());
			boolean nocertificat = false;
			Iterator<DossierNageurEntity> it = nageurs.iterator();
			while(it.hasNext() && !nocertificat) {
				nocertificat = BooleanUtils.isFalse(it.next().getCertificat());
			}
			if (nocertificat) {
				count++;
				dossiersRappeler.add(dossier.getEmail());
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				try {
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();

					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(
							new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
					Address[] replyTo = {
							new InternetAddress("contact@asptt-toulouse-natation.com", "ASPTT Toulouse Natation") };
					msg.setReplyTo(replyTo);
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dossier.getEmail()));
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress("contact@asptt-toulouse-natation.com"));
					if (StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress(dossier.getEmailsecondaire()));
					}

					StringBuilder message = new StringBuilder(
							"Madame, Monsieur,<p>Vous avez effectué une demande d'inscription au club. Nous n'avons pas reçu le (les) certificat(s) médicaux. Sans ceux-ci avant le 12 septembre, nous seront obligé de libérer vos créneaux et d'annuler votre inscription.<br />");

					message.append("<p>Dans l'attente de vos documents, sportivement,<br />" + "Le secrétariat,<br />"
							+ "ASPTT Toulouse Natation<br />"
							+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
					htmlPart.setContent(message.toString(), "text/html");
					mp.addBodyPart(htmlPart);

					msg.setSubject("ASPTT Toulouse Natation - Rappel certificats médicaux", "UTF-8");
					msg.setContent(mp);
					Transport.send(msg);
					dossier.setReminded(new DateTime().toDate());
					dossier.setReminder(true);
					dao.save(dossier);
				} catch (MessagingException | UnsupportedEncodingException e) {
					LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + dossier.getEmail(), e);
				}
			}
		}

		// Rapport
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("contact@asptt-toulouse-natation.com"));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("remi.lacaze@asptt-toulouse-natation.com"));

			StrBuilder message = new StrBuilder("<p>").append(count).append(" dossiers vont expirer dans 2 jours.</p>");
			message.append("<ul>");
			for (String dossierRappeler : dossiersRappeler) {
				message.append("<li>").append(dossierRappeler).append("</li>");
			}
			message.append("</ul>");

			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Rapport  - dossiers arrivant à expiration", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail de rapport", e);
		}

		LOG.log(Level.WARNING, count + " dossiers rappelés");
		return count;
	}
	
	@Path("/lancer-inscription")
	@GET
	public int lancerInscription() {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(2);
		criteria.add(new CriterionDao<String>(DossierEntityFields.STATUT, DossierStatutEnum.INITIALISE.name(),
				Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON, DossierService.NEW_SAISON,
				Operator.EQUAL));
		List<DossierEntity> entities = dao.find(criteria);
		int count = 0;
		for (DossierEntity dossier : entities) {
				count++;
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				try {
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();

					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(
							new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
					Address[] replyTo = {
							new InternetAddress("contact@asptt-toulouse-natation.com", "ASPTT Toulouse Natation") };
					msg.setReplyTo(replyTo);
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dossier.getEmail()));
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress("contact@asptt-toulouse-natation.com"));
					if (StringUtils.isNotBlank(dossier.getEmailsecondaire())) {
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress(dossier.getEmailsecondaire()));
					}

					StringBuilder message = new StringBuilder(
							"Madame, Monsieur,<p>Les inscriptions pour la saison 2017-2018 sont ouvertes. Pour vous réinscrire veuillez utiliser vos identifiants de connexion rappelés ci-dessous et vous connecter à l'adresse : <a href=\"http://inscription.asptt-toulouse-natation.com/\">http://inscription.asptt-toulouse-natation.com</a>.</p>");
					message.append("Identifiant / mot de passe : " + dossier.getEmail() + " / " + dossier.getMotdepasse());
					message.append("<p>Sportivement,<br />" + "Le secrétariat,<br />"
							+ "ASPTT Toulouse Natation<br />"
							+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
					htmlPart.setContent(message.toString(), "text/html");
					mp.addBodyPart(htmlPart);

					msg.setSubject("ASPTT Toulouse Natation - Inscriptions", "UTF-8");
					msg.setContent(mp);
					Transport.send(msg);
					dossier.setReminded(new DateTime().toDate());
					dossier.setReminder(true);
					dao.save(dossier);
				} catch (MessagingException | UnsupportedEncodingException e) {
					LOG.log(Level.SEVERE, "Erreur pour l'e-mail: " + dossier.getEmail(), e);
				}
		}

		// Rapport
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("webmaster@asptt-toulouse-natation.com", "ASPTT Toulouse Natation"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("contact@asptt-toulouse-natation.com"));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress("michael.kargbo@gmail.com"));

			StrBuilder message = new StrBuilder("<p>").append(count).append(" dossiers.</p>");
			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Rapport  - réinscriptions", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (MessagingException | UnsupportedEncodingException e) {
			LOG.log(Level.SEVERE, "Erreur pour l'e-mail de rapport", e);
		}

		LOG.log(Level.WARNING, count + " dossiers rappelés");
		return count;
	}
}
