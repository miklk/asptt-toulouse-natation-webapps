package com.asptttoulousenatation.core.adherent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.groupe.GroupTransformer;
import com.asptttoulousenatation.core.inscription.DossierService;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DossierNageurEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.DossierNageurEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionNouveauEntity;
import com.asptttoulousenatation.core.server.dao.inscription.DossierDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurDao;
import com.asptttoulousenatation.core.server.dao.inscription.DossierNageurPhotoDao;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionNouveauDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/inscription")
@Produces("application/json")
public class AuthenticateAdherentService {

	private static final Logger LOG = Logger
			.getLogger(AuthenticateAdherentService.class.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private DossierDao dao = new DossierDao();
	private DossierNageurDao dossierNageurDao = new DossierNageurDao();
	private InscriptionNouveauDao nouveauDao = new InscriptionNouveauDao();
	private DossierNageurPhotoDao photoDao = new DossierNageurPhotoDao();
	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();

	@Path("{authenticate}")
	@GET
	public InscriptionDossiersUi authenticate(
			@QueryParam("email") String email,
			@QueryParam("mdp") String motDePasse) {
		InscriptionDossiersUi dossiers = new InscriptionDossiersUi();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL, email,
				Operator.EQUAL));
		criteria.add(new CriterionDao<String>(DossierEntityFields.MOTDEPASSE,
				motDePasse, Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
				DossierService.NEW_SAISON, Operator.EQUAL));
		List<DossierEntity> entities = dao.find(criteria);
		if (CollectionUtils.isEmpty(entities)) {
			//Try with uppercase
			criteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
			criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL, email.toUpperCase(),
					Operator.EQUAL));
			criteria.add(new CriterionDao<String>(DossierEntityFields.MOTDEPASSE,
					motDePasse, Operator.EQUAL));
			criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
					DossierService.NEW_SAISON, Operator.EQUAL));
			entities = dao.find(criteria);
		}
		if (CollectionUtils.isEmpty(entities)) {
			dossiers.setInconnu(true);
		} else {
			dossiers.setInconnu(false);
			DossierEntity dossier = entities.get(0);
			dossiers.setDossier(dossier);

			criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<Long>(
					DossierNageurEntityFields.DOSSIER, dossier.getId(),
					Operator.EQUAL));
			List<DossierNageurEntity> nageursEntities = dossierNageurDao
					.find(criteria);

			dossiers.addDossier(nageursEntities);

			// Build UI dossier
			for (InscriptionDossierUi nageur : dossiers.getNageurs()) {
				DossierNageurEntity adherent = nageur.getDossier();
				// Get nouveau groupe et vérifie si on peut changer
				if (adherent.getGroupe() != null) {
					try {
						GroupEntity group = groupDao.get(adherent
								.getGroupe());
						nageur.setChoix(group.getInscription());
						nageur.setGroupe(groupTransformer.toUi(group));
					} catch (Exception e) {
						LOG.log(Level.SEVERE, "Erreur inattendue", e);
					}
				} else {
					nageur.setChoix(true);
				}
				// Test sélection de créneau
				nageur.setCreneauSelected(StringUtils.isNotBlank(adherent
						.getCreneaux()));
				
				//Get photo
				nageur.setHasPhoto(photoDao.findByDossier(nageur.getDossier().getId()) != null);
			}
		}
		return dossiers;

	}

	@Path("/forget")
	@GET
	public ForgetEmailResult forgetEmail(@QueryParam("email") String email) {
		ForgetEmailResult result = new ForgetEmailResult();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL, email,
				Operator.EQUAL));
		criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON, DossierService.NEW_SAISON,
				Operator.EQUAL));
		List<DossierEntity> adherents = dao.find(criteria);
		if(CollectionUtils.isEmpty(adherents)) {
			//Try uppercase
			criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL, email.toUpperCase(),
					Operator.EQUAL));
			criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON, DossierService.NEW_SAISON,
					Operator.EQUAL));
			adherents = dao.find(criteria);
		}
		
		if (CollectionUtils.isNotEmpty(adherents)) {
			result.setFound(true);
			try {
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				Multipart mp = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();

				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(
						"webmaster@asptt-toulouse-natation.com",
						"ASPTT Toulouse Natation"));
				StringBuilder message = new StringBuilder(
						"Votre mot de passe: "
								+ adherents.get(0).getMotdepasse());
				htmlPart.setContent(message.toString(), "text/html");
				mp.addBodyPart(htmlPart);

				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						adherents.get(0).getEmail()));

				msg.setSubject(
						"ASPTT Toulouse Natation - Inscription - Mot de passe",
						"UTF-8");
				msg.setContent(mp);
				Transport.send(msg);
			} catch (MessagingException | UnsupportedEncodingException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
			}
		} else {
			result.setFound(false);
		}
		return result;
	}
	
	@Path("/nouveau")
	@GET
	public NouveauPasswordResult motDePasse(@QueryParam("email") String pEmail) {
		NouveauPasswordResult result = new NouveauPasswordResult();
		if(StringUtils.isNotBlank(pEmail)) {
			//Test existence de l'adresse e-mail
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(DossierEntityFields.EMAIL,
					pEmail, Operator.EQUAL));
			criteria.add(new CriterionDao<Long>(DossierEntityFields.SAISON,
					DossierService.NEW_SAISON, Operator.EQUAL));
			List<DossierEntity> entities = dao.find(criteria);
			if(CollectionUtils.isNotEmpty(entities)) {
				result.setExist(true);
			} else {
				//Determine mot de passee
				String code = RandomStringUtils.randomNumeric(4);
				DossierEntity nouveau = new DossierEntity();
				nouveau.setSaison(DossierService.NEW_SAISON);
				nouveau.setEmail(pEmail);
				nouveau.setMotdepasse(code);
				DossierEntity nouveauCreated = dao.save(nouveau);
				
				DossierNageurEntity enfant = new DossierNageurEntity();
				enfant.setSaison(DossierService.NEW_SAISON);
				enfant.setDossier(nouveauCreated.getId());
				enfant.setNouveau(true);
				dossierNageurDao.save(enfant).getId();
				
				try {
					Properties props = new Properties();
					Session session = Session.getDefaultInstance(props, null);
	
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();
					String msgBody = "Madame, Monsieur,<br />"
							+ "Vous pouvez maintenant accéder au formulaire d'inscription en utilisant le code suivant: "
							+ "<b>" + nouveau.getMotdepasse() + "</b>"
							+ ".<br />"
							+ "<a href=\"http://www.asptt-toulouse-natation.com/#/page/Inscription\">Inscription en ligne - ASPTT Toulouse Natation</a>"
							+ "<p>Sportivement,<br />ASPTT Toulouse Natation</p>";
	
					htmlPart.setContent(msgBody, "text/html");
					mp.addBodyPart(htmlPart);
					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(
							"webmaster@asptt-toulouse-natation.com",
							"ASPTT Toulouse Natation"));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
							nouveau.getEmail()));
					msg.setSubject("Votre compte web a été créé.", "UTF-8");
					msg.setContent(mp);
					Transport.send(msg);
				} catch (MessagingException | UnsupportedEncodingException e) {
					LOG.log(Level.SEVERE, "Impossible d'envoyer le mot de passe par e-mail", e);
				}
			}
		}
		return result;
	}
	
	@Path("/pre-nouveau")
	@PUT
	public void nouveau(@QueryParam("email") String pEmail) {
		if (StringUtils.isNotBlank(pEmail)) {
			InscriptionNouveauEntity entity = new InscriptionNouveauEntity();
			entity.setEmail(pEmail);
			nouveauDao.save(entity);
		}
	}
	
	@Path("/remove/{dossier}")
	@DELETE
	public void remove(@PathParam("dossier") Long dossier) {
		dossierNageurDao.delete(dossier);
	}

	@Path("/ajouter-enfant")
	@POST
	@Consumes("application/json")
	public AjouterEnfantResult ajouterEnfant(
			InscriptionDossiersUi pDossiers) {

		DossierNageurEntity enfant = new DossierNageurEntity();
		enfant.setSaison(DossierService.NEW_SAISON);
		enfant.setDossier(pDossiers.getDossier().getId());
		enfant.setNouveau(true);
		Long idNouveau = dossierNageurDao.save(enfant).getId();
		

		InscriptionDossiersUi dossiersUpdated = getDossiers(pDossiers
				.getDossier());
		AjouterEnfantResult result = new AjouterEnfantResult();
		result.setDossiers(dossiersUpdated);
		// Recherche de la position du nouveau
		int index = 0;
		boolean trouve = false;
		Iterator<InscriptionDossierUi> it = dossiersUpdated.getNageurs()
				.iterator();
		while (!trouve && it.hasNext()) {
			if (it.next().getDossier().getId() == idNouveau) {
				trouve = true;
			} else {
				index++;
			}

		}
		result.setCurrentIndex(index);

		return result;
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
			//Get photo
			dossier.setHasPhoto(photoDao.findByDossier(nageur.getId()) != null);
		}
		return dossiers;
	}
}