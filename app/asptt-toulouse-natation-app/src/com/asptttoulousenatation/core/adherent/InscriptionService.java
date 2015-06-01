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
import javax.mail.internet.AddressException;
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

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierCertificatEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierCertificatEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.inscription.DossierCertificatDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
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
			if (nageur.getNouveauGroupe() != null) {
				GroupEntity group = groupDao.get(nageur.getNouveauGroupe());
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
			DossierEntity principal = dao.save(dossiers.getDossier());

			// Add certificats
			if (MapUtils.isNotEmpty(dossiers.getCertificats())) {
				for (FormDataBodyPart certificatPart : certificats) {
					try {
						Blob certificat = new Blob(
								IOUtils.toByteArray(certificatPart
										.getValueAs(InputStream.class)));
						System.out.println(certificatPart
								.getContentDisposition().getFileName());
						String fileName = new String(certificatPart
								.getContentDisposition().getFileName().getBytes(), "ISO-8859-1");
						System.out.println(fileName);
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
						}
					} catch (IOException e) {
						LOG.log(Level.SEVERE, "Récupération des certificats", e);
					}

				}
			}

			for (InscriptionDossierUi dossier : dossiers.getNageurs()) {
				if (!dossier.isSupprimer()) {
					buildDossier(dossier);
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
					"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la sélection de vos créneaux pour la nouvelle saison sportive 2014-2015.<br />"
							+ "Nous attendons votre dossier, certificat médicale ainsi que votre réglement afin de finaliser ainsi votre inscription. <br />"
							+ "Vous recevrez un e-mail de confirmation dès que votre inscription sera finalisée.<br />"
							+ "Voici les créneaux que vous avez sélectionné: <br />");

			message.append("<dl>");
			for (InscriptionDossierUi nageurUi : dossierUi.getNageurs()) {
				DossierNageurEntity nageur = nageurUi.getDossier();
				try {
				GroupEntity group = groupDao.get(nageur
						.getNouveauGroupe());
				
				message.append("<dt>").append(nageur.getNom()).append(" ").append(nageur.getPrenom()).append(" ").append(group.getTitle())
				.append(" (")
				.append("<a href=\"http://www.asptt-toulouse-natation.com/resources/inscription/print/"+nageur.getId()+"\">Télécharger le dossier</a>")
				.append(")")
						.append("</dt>");
				}catch(Exception e) {
					LOG.severe("Erreur du groupe (adherent " + nageur.getId() + "): " + nageur.getNouveauGroupe() + " ");
				}
				for (String creneau : AdherentListResultBeanTransformer
						.getInstance().getCreneaux(nageur.getCreneaux())) {
					message.append("<dd>").append(creneau).append("</dd>");
				}
				
				ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
				getDossierAsPdf(nageur, dossier, fileOutputStream);
				
				MimeBodyPart attachment = new MimeBodyPart();
				attachment.setFileName("asptt_dossier_" + nageur.getPrenom() + ".pdf");
				attachment.setContent(
						new ByteArrayInputStream(fileOutputStream.toByteArray()), "application/pdf");
				mp.addBodyPart(attachment);
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
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			LOG.severe("Erreur pour l'e-mail: " + dossier.getEmail() + "("
					+ e.getMessage() + ")");
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
		pDossier.getDossier().setSaisie(true);
		pDossier.getDossier().setCertificat(false);
		pDossier.getDossier().setPaiement(false);
		
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
	
	private void buildMineur(DossierNageurEntity adherent) {
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

		fields.setField("untitled1", adherent.getNom());
		fields.setField("untitled2", adherent.getPrenom());
		Date dateNaissance = adherent.getNaissance();
		SimpleDateFormat formatDD = new SimpleDateFormat("dd");
		fields.setField("untitled3", formatDD.format(dateNaissance));
		SimpleDateFormat formatMM = new SimpleDateFormat("MM");
		fields.setField("untitled4", formatMM.format(dateNaissance));
		SimpleDateFormat formatYYYY = new SimpleDateFormat("yyyy");
		fields.setField("untitled5", formatYYYY.format(dateNaissance));

		fields.setField("untitled6", adherent.getProfession());
		fields.setField("untitled7", parent.getAdresse());
		fields.setField("untitled8", parent.getCodepostal());
		fields.setField("untitled9", parent.getVille());
		fields.setField("untitled10", parent.getTelephone());
		fields.setField("untitled11", parent.getEmail());
		fields.setField("untitled12", parent.getAccordNomPrenom());
		fields.setField("untitled13", adherent.getMineurParent());
		fields.setField("untitled14", adherent.getMineur());

		if (StringUtils.isNotBlank(adherent.getCivilite())) {
			switch (adherent.getCivilite()) {
			case "0":
				fields.setField("untitled15", "X");
				break;
			case "1":
				fields.setField("untitled16", "X");
				break;
			default:
			}
		}

		if (BooleanUtils.isTrue(adherent.getNouveau())) {
			fields.setField("untitled17", "X");
		} else {
			fields.setField("untitled18", "X");
		}

		GroupEntity group = groupDao.get(adherent.getNouveauGroupe());
		if (BooleanUtils.isTrue(group.getLicenceFfn())) {
			fields.setField("untitled19", "X");
			fields.setField("untitled20", "X");
		}

		fields.setField(
				"accidentNom1",
				StringUtils.defaultString(parent.getAccidentNom1())
						+ " "
						+ StringUtils.defaultString(parent.getAccidentPrenom1()));
		fields.setField("accidentTel1", parent.getAccidentTelephone1());
		fields.setField(
				"accidentNom2",
				StringUtils.defaultString(parent.getAccidentNom2())
						+ " "
						+ StringUtils.defaultString(parent.getAccidentPrenom2()));
		fields.setField("accidentTel2", parent.getAccidentTelephone2());

		stamper.close();
		reader.close();
	}
	
	@Path("/expire")
	@GET
	public void expire() {
		Date seuilInscription = DateTime.now().minusDays(15).toDate();
		List<DossierEntity> entities = dao.getAll();
		int count = 0;
		for(DossierEntity dossier: entities) {
			if(dossier.getUpdated().before(seuilInscription)) {
				dossier.setExpire(true);
				count++;
			}
		}
		LOG.log(Level.WARNING, count + " dossiers expirés");
	}
}