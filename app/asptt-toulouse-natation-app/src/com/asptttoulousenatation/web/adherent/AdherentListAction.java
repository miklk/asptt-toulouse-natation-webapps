package com.asptttoulousenatation.web.adherent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.search.OrderDao.OrderOperator;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;
import com.google.gson.Gson;

public class AdherentListAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(AdherentListAction.class
			.getName());

	private InscriptionDao inscriptionDao = new InscriptionDao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();

	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		doPost(pReq, pResp);
	}

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		AdherentListForm form = new AdherentListForm();
		String action = pReq.getParameter("action");
		try {
		
		if ("search".equals(action)) {
			BeanUtilsBean2.getInstance().populate(form, pReq.getParameterMap());
			search(pReq, pResp, form);
		} else if ("loadGroupes".equals(action)) {
			loadGroupes(pReq, pResp);
		} else if ("loadCreneaux".equals(action)) {
			loadCreneaux(pReq, pResp);
		} else if ("sendEmail".equals(action)) {
			BeanUtilsBean2.getInstance().populate(form, pReq.getParameterMap());
			sendEmail(pReq, pResp, form);
		} else if ("fixCreneaux".equals(action)) {
			fixCreneaux(pReq, pResp);
		} else if ("sendConfirmation".equals(action)) {
			BeanUtilsBean2.getInstance().populate(form, pReq.getParameterMap());
			sendConfirmation(pReq, pResp, form);
		} else if ("affecter".equals(action)) {
			BeanUtilsBean2.getInstance().populate(form, pReq.getParameterMap());
			affecter(pReq, pResp, form);
		} else if ("fixEmail".equals(action)) {
			fixEmail(pReq, pResp);
		} else if ("sendConfirmationLeo".equals(action)) {
			BeanUtilsBean2.getInstance().populate(form, pReq.getParameterMap());
			sendConfirmationLeo(pReq, pResp, form);
		} else if("supprimer".equals(action)) {
			supprimer(pReq, pResp);
		} else if ("sendExcuse".equals(action)) {
			BeanUtilsBean2.getInstance().populate(form, pReq.getParameterMap());
			sendExcuse(pReq, pResp, form);
		} else if("loadAdherent".equals(action)) {
			loadAdherent(pReq, pResp);
		} else if("updateAdherent".equals(action)) {
			updateAdherent(pReq, pResp);
		} else if("loadPiscines".equals(action)) {
			loadPiscines(pReq, pResp);
		} else if("fixPrincipal".equals(action)) {
			fixPrincipal(pReq, pResp);
		}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search(HttpServletRequest pReq, HttpServletResponse pResp,
			AdherentListForm form) throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				5);
		if (StringUtils.isNotBlank(form.getSearchNom())) {
			criteria.add(new CriterionDao<String>(InscriptionEntityFields.NOM,
					form.getSearchNom().toUpperCase(), Operator.EQUAL));
		}
		if (StringUtils.isNotBlank(form.getSearchPrenom())) {
			criteria.add(new CriterionDao<String>(
					InscriptionEntityFields.PRENOM, form.getSearchPrenom()
							.toUpperCase(), Operator.EQUAL));
		}
		if(form.getSearchGroupe() != null && form.getSearchGroupe() == -2) {
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, null,
					Operator.NULL));
		} else if (form.getSearchGroupe() != null && form.getSearchGroupe() != -1) {
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, form.getSearchGroupe(),
					Operator.EQUAL));
		}
		if(form.isSearchDossier()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.COMPLET, form.isSearchDossier(),
					Operator.EQUAL));
		}
		if(form.isSaisie()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.SAISIE, form.isSaisie(),
					Operator.EQUAL));
		}
		if(form.isSansEmail()) {
			List<CriterionDao<?>> orCriteria = new ArrayList<CriterionDao<?>>(2);
			orCriteria.add(new CriterionDao<Object>(
					InscriptionEntityFields.EMAIL, null,
					Operator.NULL));
			orCriteria.add(new CriterionDao<String>(
					InscriptionEntityFields.EMAIL, "",
					Operator.EQUAL));
		}
		
		
		final List<InscriptionEntity> entities;
		if (criteria.isEmpty())
			if (!"-1".equals(form.getSearchPiscine())) {
				entities = inscriptionDao.getAll();
			} else {
				entities = Collections.emptyList();
			}
		else {
			entities = inscriptionDao.find(criteria, new OrderDao(
					InscriptionEntityFields.NOM, OrderOperator.ASC));
		}
		List<AdherentListResultBean> results = new ArrayList<AdherentListResultBean>();
		for (InscriptionEntity entity : entities) {
			try {
				if (StringUtils.isBlank(form.getSearchCreneau()) || "-1".equals(form.getSearchCreneau())) {
					AdherentListResultBean adherentUi = AdherentListResultBeanTransformer.getInstance()
							.get(entity);
					if(StringUtils.isNotBlank(form.getSearchPiscine()) &&  !"-1".equals(form.getSearchPiscine())) {
						boolean trouve = false;
						Iterator<String> it = adherentUi.getCreneaux().iterator();
						while(it.hasNext() && !trouve) {
							trouve = it.next().contains(form.getSearchPiscine());
						}
						if(trouve) {
							results.add(adherentUi);
						}
					} else {
						results.add(adherentUi);
					}
				} else if("-2".equals(form.getSearchCreneau()) && StringUtils.isBlank(entity.getCreneaux())) {
					results.add(AdherentListResultBeanTransformer.getInstance()
							.get(entity));
				} else if(StringUtils.contains(entity.getCreneaux(),
						form.getSearchCreneau())) {
					results.add(AdherentListResultBeanTransformer.getInstance()
							.get(entity));
				}
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}

		if (builder.isEmpty()) {
			Gson gson = new Gson();
			String json = gson.toJson(results);
			pResp.setContentType("application/json;charset=UTF-8");
			pResp.getWriter().write(json);
		} else {
			pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write(builder.toString());
		}
	}

	protected void loadGroupes(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		GroupDao dao = new GroupDao();
		List<GroupEntity> entities = dao.getAll();
		List<GroupUi> lUis = new GroupTransformer().toUi(entities);
		Gson gson = new Gson();
		String json = gson.toJson(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	protected void loadCreneaux(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long groupId = Long.valueOf(pReq.getParameter("selectedGroupe"));
		// Retrieve slots
		List<CriterionDao<? extends Object>> lSlotCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lSlotCriterion = new CriterionDao<Long>(
				SlotEntityFields.GROUP, groupId, Operator.EQUAL);
		lSlotCriteria.add(lSlotCriterion);
		SlotDao slotDao = new SlotDao();
		List<SlotEntity> lEntities = slotDao.find(lSlotCriteria);
		List<SlotUi> lUis = new SlotTransformer().toUi(lEntities);
		Collections.sort(lUis, new Comparator<SlotUi>() {

			public int compare(SlotUi pO1, SlotUi pO2) {
				return pO1.getDayOfWeek().compareTo(pO2.getDayOfWeek());
			}
		});
		Gson gson = new Gson();
		String json = gson.toJson(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	protected void sendEmail(HttpServletRequest pReq,
			HttpServletResponse pResp, AdherentListForm form)
			throws ServletException, IOException {
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(form.getSendEmail().getCorps(), "text/html");
			mp.addBodyPart(htmlPart);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"webmaster@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation"));
			Address[] replyTo = {new InternetAddress(
					"contact@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation")};
			msg.setReplyTo(replyTo);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"contact@asptt-toulouse-natation.com"));
			for (String destinataire : form.getSendEmail().getDestinataires()) {
				if (StringUtils.isNotBlank(destinataire)) {
					msg.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(destinataire));
				}
			}
			msg.setSubject(form.getSendEmail().getSujet(), "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
			pResp.setContentType("text/html;charset=UTF-8");
			pResp.getWriter().write("ok");
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sendConfirmation(HttpServletRequest pReq,
			HttpServletResponse pResp, AdherentListForm form)
			throws ServletException, IOException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		for (String destinataire : form.getSendEmail().getDestinataires()) {
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
						destinataire));

				StringBuilder message = new StringBuilder(
						"Madame, Monsieur,<p>Nous avons le plaisir de vous compter parmi nous pour cette nouvelle saison sportive 2013-2014.<br />"
								+ "Nous vous confirmons la bonne réception de votre dossier qui finalise ainsi votre inscription. <br />"
								+ "Les cours reprendrons à partir du 23 septembre selon les bassins et jours de pratique (voir ci-dessous):<br />");

				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteria.add(new CriterionDao<String>(
						InscriptionEntityFields.EMAIL, destinataire,
						Operator.EQUAL));
				List<InscriptionEntity> adherents = inscriptionDao
						.find(criteria);
				message.append("<dl>");
				for (InscriptionEntity adherent : adherents) {
					adherent.setComplet(true);
					inscriptionDao.save(adherent);
					GroupEntity group = groupDao.get(adherent
							.getNouveauGroupe());
					message.append("<dt>").append(adherent.getNom()).append(" ").append(adherent.getPrenom()).append(" ").append(group.getTitle())
							.append("</dt>");
					for (String creneau : AdherentListResultBeanTransformer
							.getInstance().getCreneaux(adherent.getCreneaux())) {
						message.append("<dd>").append(creneau).append("</dd>");
					}
				}
				message.append("</dl>");
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						"contact@asptt-toulouse-natation.com"));
				message.append("<p>Si votre dossier n'est pas à jour, merci de bien vouloir le compléter dans les plus brefs délais, impérativement avant la première séance.</p>");
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
				LOG.severe("Erreur pour l'e-mail: " + destinataire + "("
						+ e.getMessage() + ")");
			}
		}
		pResp.setContentType("text/html;charset=UTF-8");
		pResp.getWriter().write("ok");

	}

	protected void sendConfirmationLeo(HttpServletRequest pReq,
			HttpServletResponse pResp, AdherentListForm form)
			throws ServletException, IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		for (String destinataire : form.getSendEmail().getDestinataires()) {
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
						destinataire));

				StringBuilder message = new StringBuilder(
						"Madame, Monsieur,<p>Nous avons le plaisir de vous compter parmi nous pour cette nouvelle saison sportive 2013-2014.<br />"
								+ "Nous vous confirmons la bonne réception de votre dossier qui finalise ainsi votre inscription. <br />"
								+ "Toutefois, nous ne serons en capacité de reprendre les cours sur Léo Lagrange, qu’à compter du retour des vacances de la Toussaint, c’est-à-dire début Novembre (date précisée ultérieurement).<br />"
								+ "<p>Néanmoins, nous pouvons d’ores et déjà vous proposer des créneaux de substitution, dès la semaine du 23/09, sur le Mercredi soir (19h à 20h45) ou le Samedi matin (09h à 12h) à chaque fois sur Alex Jany, selon les groupes et les places disponibles. Merci de vous positionner au préalable par retour de mail ou appel téléphonique.</p>"
								+ "Pour rappel, le créneau sur lequel vous vous êtes positionnés initialement sur le jeudi ou vendredi soirs (voir ci-dessous) :<br />");

				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteria.add(new CriterionDao<String>(
						InscriptionEntityFields.EMAIL, destinataire,
						Operator.EQUAL));
				List<InscriptionEntity> adherents = inscriptionDao
						.find(criteria);
				message.append("<dl>");
				for (InscriptionEntity adherent : adherents) {
					adherent.setComplet(true);
					inscriptionDao.save(adherent);
					GroupEntity group = groupDao.get(adherent
							.getNouveauGroupe());
					message.append("<dt>").append(adherent.getNom()).append(" ").append(adherent.getPrenom()).append(" ").append(group.getTitle())
					.append("</dt>");
					for (String creneau : AdherentListResultBeanTransformer
							.getInstance().getCreneaux(adherent.getCreneaux())) {
						message.append("<dd>").append(creneau).append("</dd>");
					}
				}
				message.append("</dl>");
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						"contact@asptt-toulouse-natation.com"));
				message.append("<p>Si votre dossier n'est pas à jour, merci de bien vouloir le compléter dans les plus brefs délais, impérativement avant la première séance.</p>");
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
				LOG.severe("Erreur pour l'e-mail: " + destinataire + "("
						+ e.getMessage() + ")");
			}
		}
		pResp.setContentType("text/html;charset=UTF-8");
		pResp.getWriter().write("ok");
	}
	
	protected void sendExcuse(HttpServletRequest pReq,
			HttpServletResponse pResp, AdherentListForm form)
			throws ServletException, IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"webmaster@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation"));

			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					"contact@asptt-toulouse-natation.com"));
			Address[] replyTo = {new InternetAddress(
					"contact@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation")};
			msg.setReplyTo(replyTo);

			for (String destinataire : form.getSendEmail().getDestinataires()) {
				msg.addRecipient(Message.RecipientType.BCC,
						new InternetAddress(destinataire));
			}

			StringBuilder message = new StringBuilder(
					"Chers adhérents,<p>Suite à l’incendie survenu à la piscine Léo Lagrange et la ré-affection tardive des bassins par la Mairie de Toulouse, nous avons dû faire face à des difficultés lors des premières séances de natation.<br />Nous remercions les parents et nageurs qui nous ont aidé à gérer ces inconvénients.<br />Nous sommes désolés pour les désagréments occasionnés et nous mettons tout en œuvre pour améliorer la situation. Nous retrouverons un fonctionnement normal dès la réouverture du bassin Léo Lagrange, la date annoncée par la Mairie de Toulouse est la mi-novembre.</p>"
							+ "<br />Nous vous informons que des créneaux d’entrainement sont encore disponibles. Vous pouvez les consulter sur notre site web et nous contacter par e-mail si vous souhaitez en bénéficier."
							+ "<p>Sportivement,<br />Le Bureau de la section Natation</p>");

			htmlPart.setContent(message.toString(), "text/html");
			mp.addBodyPart(htmlPart);

			msg.setSubject("Démarrage de la saison de natation", "UTF-8");
			msg.setContent(mp);
			Transport.send(msg);
		} catch (AddressException e) {
			// ...
		} catch (MessagingException e) {
			// ...
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pResp.setContentType("text/html;charset=UTF-8");
		pResp.getWriter().write("ok");
	}

	protected void fixCreneaux(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		for (GroupEntity group : groupDao.getAll()) {
			builder.append(group.getTitle());

			// Creation des creneaux
			Map<Long, Integer> counter = new HashMap<Long, Integer>();
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, group
					.getId(), Operator.EQUAL));
			List<SlotEntity> creneaux = slotDao.find(criteria);
			for (SlotEntity creneau : creneaux) {
				counter.put(creneau.getId(), 0);
			}

			criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, group.getId(),
					Operator.EQUAL));
			List<InscriptionEntity> adherents = inscriptionDao.find(criteria);
			for (InscriptionEntity adherent : adherents) {
				String creneau = adherent.getCreneaux();
				if (StringUtils.isNotBlank(creneau)) {
					for (String creneauSplit : creneau.split(";")) {
						final String creneauId;
						if (StringUtils.contains(creneauSplit, "_")) {
							creneauId = creneauSplit.split("_")[1];
						} else {
							creneauId = creneauSplit;
						}
						if (StringUtils.isNumeric(creneauId)) {
							Long creneauIdentifier = Long.valueOf(creneauId);
							final int count;
							if (counter.containsKey(creneauIdentifier)) {
								count = counter.get(creneauIdentifier) + 1;
								counter.put(creneauIdentifier, count);
							}

						}
					}
				}
			}

			for (Map.Entry<Long, Integer> creneauEntry : counter.entrySet()) {
				SlotEntity creneauEntity = slotDao.get(creneauEntry.getKey());
				creneauEntity.setPlaceRestante(creneauEntity
						.getPlaceDisponible() - creneauEntry.getValue());
				slotDao.save(creneauEntity);
			}
		}
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(builder.toString());
	}

	protected void fixEmail(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.SAISIE,
				Boolean.TRUE, Operator.EQUAL));
		List<InscriptionEntity> adherents = inscriptionDao.find(criteria);
		builder.append(adherents.size()).append(" adhérents ");
		LOG.warning(adherents.size() + " adhérents sans e-mail");
		for (InscriptionEntity adherent : adherents) {
			if (StringUtils.isBlank(adherent.getEmail()) && adherent.getPrincipal() != null) {
				try {
					InscriptionEntity adherentPrincipal = inscriptionDao
							.get(adherent.getPrincipal());
					adherent.setEmail(adherentPrincipal.getEmail());
					inscriptionDao.save(adherent);
				} catch (Exception e) {
					builder.append("Adhérent " + adherent.getNom()
							+ " pose un problème (" + e.getMessage() + ")");
				}
			}
		}

		if (builder.length() <= 0) {
			pResp.setContentType("text/html;charset=UTF-8");
			pResp.getWriter().write("" + adherents.size());
		} else {
			pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pResp.setContentType("text/html;charset=UTF-8");
			pResp.getWriter().write(builder.toString());
		}
	}

	protected void affecter(HttpServletRequest pReq, HttpServletResponse pResp,
			AdherentListForm form) throws ServletException, IOException {
		InscriptionEntity adherent = inscriptionDao.get(form.getAffecter()
				.getAffecterAdherent());
		// Suppression des créneaux actuels
		if (StringUtils.isNotBlank(adherent.getCreneaux())) {
			String[] creneauSplit = adherent.getCreneaux().split(";");
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
		if (form.getAffecter().getAffecterGroupe() == -1l) {
			adherent.setNouveauGroupe(null);
		} else {
			adherent.setNouveauGroupe(form.getAffecter().getAffecterGroupe());
		}

		if (ArrayUtils.contains(form.getAffecter().getAffecterCreneau(), -1l)) {
			adherent.setCreneaux("");
		} else {
			StrBuilder builder = new StrBuilder();
			builder.appendWithSeparators(form.getAffecter()
					.getAffecterCreneau(), ";");
			for (Long creneau : form.getAffecter().getAffecterCreneau()) {
				SlotEntity creneauEntity = slotDao.get(creneau);
				creneauEntity
						.setPlaceRestante(creneauEntity.getPlaceRestante() - 1);
				slotDao.save(creneauEntity);
			}
			adherent.setCreneaux(builder.toString());
		}
		inscriptionDao.save(adherent);

		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write("ok");
	}
	
	protected void supprimer(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException {
		InscriptionEntity adherent = inscriptionDao.get(Long.valueOf(pReq.getParameter("selectedAdherent")));
		// Suppression des créneaux actuels
		if (StringUtils.isNotBlank(adherent.getCreneaux())) {
			String[] creneauSplit = adherent.getCreneaux().split(";");
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
		inscriptionDao.delete(adherent);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write("ok");
	}
	
	protected void loadAdherent(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		InscriptionEntity adherent = inscriptionDao.get(Long.valueOf(pReq.getParameter("selectedAdherent")));
		Gson gson = new Gson();
		String json = gson.toJson(adherent);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}
	
	protected void updateAdherent(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long adherentId = Long.valueOf(pReq.getParameter("adherentId"));
		
		InscriptionEntity entity = inscriptionDao.get(adherentId);
		try {
			BeanUtils.populate(entity, pReq.getParameterMap());
			inscriptionDao.save(entity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pResp.getWriter().write("ok");
	}
	
	protected void loadPiscines(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		List<String> piscines = slotDao.getPiscines();
		Gson gson = new Gson();
		String json = gson.toJson(piscines);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}
	
	protected void fixPrincipal(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		List<InscriptionEntity> entities = inscriptionDao.getAll();
		for(InscriptionEntity entity: entities) {
			if(entity.getPrincipal() != null) {
				try {
					inscriptionDao.get(entity.getPrincipal());
				} catch(Exception e) {
					entity.setPrincipal(null);
					inscriptionDao.save(entity);
				}
			}
		}
	}
}