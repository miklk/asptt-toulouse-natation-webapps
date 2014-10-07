package com.asptttoulousenatation.web.adherent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionNouveauEntity;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionNouveauDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.search.OrderDao.OrderOperator;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdherentListAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(AdherentListAction.class
			.getName());

	private static final int EMAIL_PAQUET = 10;

	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();
	private Inscription2Dao inscription2Dao = new Inscription2Dao();
	private InscriptionNouveauDao inscriptionNouveauDao = new InscriptionNouveauDao();

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
				BeanUtilsBean2.getInstance().populate(form,
						pReq.getParameterMap());
				search(pReq, pResp, form);
			} else if ("loadGroupes".equals(action)) {
				loadGroupes(pReq, pResp);
			} else if ("loadCreneaux".equals(action)) {
				loadCreneaux(pReq, pResp);
			} else if ("sendEmail".equals(action)) {
				BeanUtilsBean2.getInstance().populate(form,
						pReq.getParameterMap());
				sendEmail(pReq, pResp, form);
			} else if ("fixCreneaux".equals(action)) {
				fixCreneaux(pReq, pResp);
			} else if ("sendConfirmation".equals(action)) {
				BeanUtilsBean2.getInstance().populate(form,
						pReq.getParameterMap());
				sendConfirmation(pReq, pResp);
			} else if ("affecter".equals(action)) {
				BeanUtilsBean2.getInstance().populate(form,
						pReq.getParameterMap());
				affecter(pReq, pResp, form);
			} else if ("supprimer".equals(action)) {
				supprimer(pReq, pResp);
			} else if ("loadAdherent".equals(action)) {
				loadAdherent(pReq, pResp);
			} else if ("updateAdherent".equals(action)) {
				updateAdherent(pReq, pResp);
			} else if ("loadPiscines".equals(action)) {
				loadPiscines(pReq, pResp);
			} else if ("facture".equals(action)) {
				facture(pReq, pResp);
			} else if ("all".equals(action)) {
				BeanUtilsBean2.getInstance().populate(form,
						pReq.getParameterMap());
				form.setSaisie(true);
				searchCsv(pReq, pResp, form);
			} else if ("forgetEmail".equals(action)) {
				forgetEmail(pReq, pResp);
			} else if("sendConfirmationNew".equals(action)) {
				sendConfirmationNew(pReq, pResp);
			} else if("export".equals(action)) {
				export(pReq, pResp);
			} else if("sendOuverture".equals(action)) {
				sendOuverture(pReq, pResp);
			} else if("updateAttribute".equals(action)) {
				updateAttribute(pReq, pResp);
			} else if("fixComplet".equals(action)) {
				fixComplet(pReq, pResp);
			} else if("deleteNonInscrits".equals(action)) {
				deleteNonInscrits(pReq, pResp);
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
		if(StringUtils.isNotBlank(form.getSearchNomMinus())) {
			criteria.add(new CriterionDao<String>(InscriptionEntityFields.NOM,
					form.getSearchNomMinus(), Operator.EQUAL));
		}
		if (StringUtils.isNotBlank(form.getSearchPrenom())) {
			criteria.add(new CriterionDao<String>(
					InscriptionEntityFields.PRENOM, form.getSearchPrenom()
							.toUpperCase(), Operator.EQUAL));
		}
		if (StringUtils.isNotBlank(form.getSearchEmail())) {
			criteria.add(new CriterionDao<String>(
					InscriptionEntityFields.EMAIL, form.getSearchEmail(), Operator.EQUAL));
		}
		if (form.getSearchGroupe() != null && form.getSearchGroupe() == -2) {
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, null, Operator.NULL));
		} else if (form.getSearchGroupe() != null
				&& form.getSearchGroupe() != -1) {
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.NOUVEAUGROUPE, form
							.getSearchGroupe(), Operator.EQUAL));
		}
		if (form.isSearchDossier()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.COMPLET, form.isSearchDossier(),
					Operator.EQUAL));
		}
		if (form.isSaisie()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.SAISIE, form.isSaisie(),
					Operator.EQUAL));
		}
		
		if (form.isSearchCertificat()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.CERTIFICAT, form.isSearchCertificat(),
					Operator.EQUAL));
		}
		if (form.isSearchPaiement()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.PAIEMENT, form.isSearchPaiement(),
					Operator.EQUAL));
		}

		final List<InscriptionEntity2> entities;
		if (criteria.isEmpty())
			if (!"-1".equals(form.getSearchPiscine())) {
				entities = inscription2Dao.getAll();
			} else {
				entities = Collections.emptyList();
			}
		else {
			entities = inscription2Dao.find(criteria, new OrderDao(
					InscriptionEntityFields.NOM, OrderOperator.ASC));
		}
		
		List<AdherentListResultBean> results = new ArrayList<AdherentListResultBean>();
		for (InscriptionEntity2 entity : entities) {
			try {
				if (StringUtils.isBlank(form.getSearchCreneau())
						|| "-1".equals(form.getSearchCreneau())) {
					AdherentListResultBean adherentUi = AdherentListResultBeanTransformer
							.getInstance().get2(entity);
					if (StringUtils.isNotBlank(form.getSearchPiscine())
							&& !"-1".equals(form.getSearchPiscine())) {
						boolean trouve = false;
						Iterator<String> it = adherentUi.getCreneaux()
								.iterator();
						while (it.hasNext() && !trouve) {
							trouve = it.next()
									.contains(form.getSearchPiscine());
						}
						if (trouve) {
							results.add(adherentUi);
						}
					} else {
						results.add(adherentUi);
					}
				} else if ("-2".equals(form.getSearchCreneau())
						&& StringUtils.isBlank(entity.getCreneaux())) {
					results.add(AdherentListResultBeanTransformer.getInstance()
							.get2(entity));
				} else if (StringUtils.contains(entity.getCreneaux(),
						form.getSearchCreneau())) {
					results.add(AdherentListResultBeanTransformer.getInstance()
							.get2(entity));
				}
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}

		if (builder.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(results);
			pResp.setContentType("application/json;charset=UTF-8");
			pResp.getWriter().write(json);
		} else {
			pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write(builder.toString());
		}
	}

	private void searchCsv(HttpServletRequest pReq, HttpServletResponse pResp,
			AdherentListForm form) throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				5);
		if (form.isSearchDossier()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.COMPLET, form.isSearchDossier(),
					Operator.EQUAL));
		}
		if (form.isSaisie()) {
			criteria.add(new CriterionDao<Boolean>(
					InscriptionEntityFields.SAISIE, form.isSaisie(),
					Operator.EQUAL));
		}
		final List<InscriptionEntity2> entities;
		entities = inscription2Dao.find(criteria, new OrderDao(
				InscriptionEntityFields.NOM, OrderOperator.ASC));
		List<String> results = new ArrayList<String>();
		for (InscriptionEntity2 entity : entities) {
			try {
				results.add(entity.getNom() + ";" + entity.getPrenom() + ";"
						+ entity.getDatenaissance());
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}

		if (builder.isEmpty()) {
			StrBuilder res = new StrBuilder();
			res.appendWithSeparators(results, "\n");
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write(res.toString());
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
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(lUis);
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
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	protected void sendEmail(HttpServletRequest pReq,
			HttpServletResponse pResp, AdherentListForm form)
			throws ServletException, IOException {
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			List<String> recipents = new ArrayList<String>();
			List<String> destinataires = getAdresseEmail(form);
			for (int i = 0; i < destinataires.size(); i += EMAIL_PAQUET) {
				try {
					Multipart mp = new MimeMultipart();
					MimeBodyPart htmlPart = new MimeBodyPart();
					htmlPart.setContent(form.getSendEmail().getCorps(),
							"text/html");
					mp.addBodyPart(htmlPart);
					MimeMessage msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(
							"webmaster@asptt-toulouse-natation.com",
							"ASPTT Toulouse Natation"));
					Address[] replyTo = { new InternetAddress(
							"contact@asptt-toulouse-natation.com",
							"ASPTT Toulouse Natation") };
					msg.setReplyTo(replyTo);
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(
									"support@asptt-toulouse-natation.com"));

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
						msg.setSubject(form.getSendEmail().getSujet(), "UTF-8");
						msg.setContent(mp);
						Transport.send(msg);
					} catch (Exception e) {
						LOG.severe(e.getMessage());
					}
				} catch (Exception e) {
					LOG.severe(e.getMessage());
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

			pResp.setContentType("text/html;charset=UTF-8");
			pResp.getWriter().write(Integer.toString(recipents.size()));
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
			HttpServletResponse pResp) throws ServletException, IOException {
		Long adherentId = Long.valueOf(pReq.getParameter("adherentId"));
		Boolean attributeValue = Boolean.parseBoolean(pReq
				.getParameter("attributeValue"));
		Boolean envoi = Boolean.parseBoolean(pReq.getParameter("envoi"));

		if (BooleanUtils.isTrue(attributeValue)) {
			if (BooleanUtils.isTrue(envoi)) {
				sendConfirmation(pReq, pResp, adherentId);
			} else {
				InscriptionEntity2 adherent = inscription2Dao.get(adherentId);
				adherent.setComplet(false);
				inscription2Dao.save(adherent);
			}
		} else {
			InscriptionEntity2 adherent = inscription2Dao.get(adherentId);
			adherent.setComplet(false);
			inscription2Dao.save(adherent);
		}
	}
	
	private void sendConfirmation(HttpServletRequest pReq,
			HttpServletResponse pResp, Long adherentId)
			throws ServletException, IOException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		InscriptionEntity2 adherent = inscription2Dao.get(adherentId);
		try {
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
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					"contact@asptt-toulouse-natation.com"));

			InscriptionEntity2 principal = adherent;
			if (principal.getPrincipal() != null) {
				principal = inscription2Dao.get(principal.getPrincipal());
			}
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					principal.getEmail()));
			if (StringUtils.isNotBlank(principal.getEmailsecondaire())) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						principal.getEmailsecondaire()));
			}

			StringBuilder message = new StringBuilder(
					"Madame, Monsieur,<p>Nous avons le plaisir de vous compter parmi nous pour cette nouvelle saison sportive 2014-2015.<br />"
							+ "Nous vous confirmons la bonne réception de votre dossier qui finalise ainsi votre inscription. <br />"
							+ "Les cours reprendront à partir du 22 septembre selon les bassins et jours de pratique (voir ci-dessous):<br />");

			message.append("<dl>");
			if (adherent.getInscriptiondt() != null
					&& BooleanUtils.isTrue(adherent.getCertificat())
					&& BooleanUtils.isTrue(adherent.getPaiement())) {
				adherent.setComplet(true);
				inscription2Dao.save(adherent);
				GroupEntity group = groupDao.get(adherent.getNouveauGroupe());
				message.append("<dt>")
						.append(StringUtils.defaultString(adherent.getNom()))
						.append(" ")
						.append(StringUtils.defaultString(adherent.getPrenom()))
						.append(" ").append(group.getTitle()).append("</dt>");
				for (String creneau : AdherentListResultBeanTransformer
						.getInstance().getCreneaux(adherent.getCreneaux())) {
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

			msg.setSubject("ASPTT Toulouse Natation - Accusé de réception",
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
			LOG.severe("Erreur pour l'adherent: " + adherent.getNom() + "("
					+ e.getMessage() + ")");
		}
		pResp.setContentType("text/html;charset=UTF-8");
		pResp.getWriter().write("ok");

	}

	protected void fixCreneaux(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		for (GroupEntity group : groupDao.getAll()) {
			LOG.warning("Début " + group.getTitle());
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
			criteria.add(new CriterionDao<Date>(
					InscriptionEntityFields.INSCRIPTIONDT, new Date(),
					Operator.NOT_NULL));
			List<InscriptionEntity2> adherents = inscription2Dao.find(criteria);
			for (InscriptionEntity2 adherent : adherents) {
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
			LOG.warning("Fin " + group.getTitle());
		}
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(builder.toString());
	}

	protected void affecter(HttpServletRequest pReq, HttpServletResponse pResp,
			AdherentListForm form) throws ServletException, IOException {
		InscriptionEntity2 adherent = inscription2Dao.get(form.getAffecter()
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
		inscription2Dao.save(adherent);

		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write("ok");
	}

	protected void supprimer(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		InscriptionEntity2 adherent = inscription2Dao.get(Long.valueOf(pReq
				.getParameter("selectedAdherent")));
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
		inscription2Dao.delete(adherent);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write("ok");
	}

	protected void loadAdherent(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		InscriptionEntity2 adherent = inscription2Dao.get(Long.valueOf(pReq
				.getParameter("selectedAdherent")));
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(adherent);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	protected void updateAdherent(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long adherentId = Long.valueOf(pReq.getParameter("adherentId"));

		InscriptionEntity2 entity = inscription2Dao.get(adherentId);
		try {
			BeanUtils.populate(entity, pReq.getParameterMap());
			inscription2Dao.save(entity);
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
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(piscines);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	protected void facture(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.FACTURE,
				"on", Operator.EQUAL));
		List<InscriptionEntity2> adherents = inscription2Dao.find(criteria);

		StrBuilder results = new StrBuilder();
		for (InscriptionEntity2 adherent : adherents) {
			results.append(adherent.getNom() + " " + adherent.getPrenom())
					.appendNewLine();
		}
		ServletOutputStream out = pResp.getOutputStream();
		String contentType = "application/text";
		String contentDisposition = "attachment;filename=factures.txt;";
		pResp.setContentType(contentType);
		pResp.setHeader("Content-Disposition", contentDisposition);

		out.print(results.toString());
		out.flush();
		out.close();
	}

	protected void telephone(HttpServletRequest pReq,
			HttpServletResponse pResp, AdherentListForm form)
			throws ServletException, IOException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.FACTURE,
				"on", Operator.EQUAL));
		List<InscriptionEntity2> adherents = inscription2Dao.find(criteria);

		StrBuilder results = new StrBuilder();
		for (InscriptionEntity2 adherent : adherents) {
			results.append(adherent.getNom() + " " + adherent.getPrenom())
					.appendNewLine();
		}
		ServletOutputStream out = pResp.getOutputStream();
		String contentType = "application/text";
		String contentDisposition = "attachment;filename=factures.txt;";
		pResp.setContentType(contentType);
		pResp.setHeader("Content-Disposition", contentDisposition);

		out.print(results.toString());
		out.flush();
		out.close();
	}

	private List<String> getAdresseEmail(AdherentListForm pForm) {
		List<String> adresseEmail = new ArrayList<String>();
		if (pForm.getSendEmail().isAll()) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Object>(
					InscriptionEntityFields.INSCRIPTIONDT, null,
					Operator.NOT_NULL));
			List<InscriptionEntity2> adherents = inscription2Dao.find(criteria);
			for (InscriptionEntity2 adherent : adherents) {
				if (StringUtils.isNotBlank(adherent.getEmail())) {
					adresseEmail.add(adherent.getEmail());
				}
			}
		} else {
			for (Long id : pForm.getSelections()) {
				try {
					InscriptionEntity2 adherent = inscription2Dao.get(id);
					// Rechercher le principal
					if (adherent.getPrincipal() != null) {
						InscriptionEntity2 adherentPrincipal = inscription2Dao
								.get(adherent.getPrincipal());
						if (StringUtils
								.isNotBlank(adherentPrincipal.getEmail())) {
							adresseEmail.add(adherentPrincipal.getEmail());
						}
						if (StringUtils.isNotBlank(adherentPrincipal
								.getEmailsecondaire())) {
							adresseEmail.add(adherentPrincipal
									.getEmailsecondaire());
						}
					} else {
						if (StringUtils.isNotBlank(adherent.getEmail())) {
							adresseEmail.add(adherent.getEmail());
						}
						if (StringUtils.isNotBlank(adherent
								.getEmailsecondaire())) {
							adresseEmail.add(adherent.getEmailsecondaire());
						}
					}
				} catch (Exception e) {
					LOG.severe("Erreur pour l'e-mail: " + id + " "
							+ e.getMessage());
				}
			}
		}
		return adresseEmail;
	}

	protected void forgetEmail(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.EMAIL,
				pReq.getParameter("email"), Operator.EQUAL));
		criteria.add(new CriterionDao<Object>(InscriptionEntityFields.PRINCIPAL,
				null, Operator.NULL));
		List<InscriptionEntity2> adherents = inscription2Dao.find(criteria);
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"webmaster@asptt-toulouse-natation.com",
					"ASPTT Toulouse Natation"));
			StringBuilder message = new StringBuilder("Votre mot de passe: "
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
		} catch (Exception e) {
			LOG.severe(e.getMessage());
		}
	}
	
	protected void sendConfirmationNew(HttpServletRequest pReq,
			HttpServletResponse pResp)
			throws ServletException, IOException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		Long numero = 0l;
		try {
			numero = Long.valueOf(pReq.getParameter("numero"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		InscriptionEntity2 principal = inscription2Dao.get(numero);
		
		
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
						principal.getEmail()));
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						"contact@asptt-toulouse-natation.com"));
				if(StringUtils.isNotBlank(principal.getEmailsecondaire())) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						principal.getEmailsecondaire()));
				}

				StringBuilder message = new StringBuilder(
						"Madame, Monsieur,<p>Nous avons le plaisir de vous confirmer la sélection de vos créneaux pour la nouvelle saison sportive 2014-2015.<br />"
								+ "Nous attendons votre dossier, certificat médicale ainsi que votre réglement afin de finaliser ainsi votre inscription. <br />"
								+ "Vous recevrez un e-mail de confirmation dès que votre inscription sera finalisée.<br />"
								+ "Voici les créneaux que vous avez sélectionné: <br />");

				List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				criteria.add(new CriterionDao<Long>(
						InscriptionEntityFields.PRINCIPAL, principal.getId(),
						Operator.EQUAL));
				List<InscriptionEntity2> adherents = inscription2Dao
						.find(criteria);
				message.append("<dl>");
				adherents.add(principal);
				for (InscriptionEntity2 adherent : adherents) {
					try {
					GroupEntity group = groupDao.get(adherent
							.getNouveauGroupe());
					
					message.append("<dt>").append(adherent.getNom()).append(" ").append(adherent.getPrenom()).append(" ").append(group.getTitle())
							.append("</dt>");
					}catch(Exception e) {
						LOG.severe("Erreur du groupe (adherent " + adherent.getEmail() + "): " + adherent.getNouveauGroupe() + " ");
					}
					for (String creneau : AdherentListResultBeanTransformer
							.getInstance().getCreneaux(adherent.getCreneaux())) {
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
			} catch (AddressException e) {
				// ...
			} catch (MessagingException e) {
				// ...
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				LOG.severe("Erreur pour l'e-mail: " + principal.getEmail() + "("
						+ e.getMessage() + ")");
			}
		pResp.setContentType("text/html;charset=UTF-8");
		pResp.getWriter().write("ok");

	}
	
	protected void export(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(InscriptionEntityFields.COMPLET, Boolean.TRUE, Operator.EQUAL));
		final List<InscriptionEntity2> entities = inscription2Dao.find(criteria);
		List<String> results = new ArrayList<String>();
		for (InscriptionEntity2 entity : entities) {
			try {
				List<String> entityStr = new ArrayList<String>();
				entityStr.add(entity.getNom());
				entityStr.add(entity.getPrenom());
				entityStr.add(entity.getDatenaissance());
				entityStr.add(entity.getVille());
				entityStr.add(entity.getCodepostal());
				StrBuilder entityStrBuilder = new StrBuilder();
				entityStrBuilder.appendWithSeparators(entityStr, ";");
				results.add(entityStrBuilder.toString());
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}

		if (builder.isEmpty()) {
			StrBuilder res = new StrBuilder();
			res.appendWithSeparators(results, "\n");
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write(res.toString());
		} else {
			pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write(builder.toString());
		}
	}
	
	protected void sendOuverture(HttpServletRequest pReq,
			HttpServletResponse pResp)
			throws ServletException, IOException {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		List<InscriptionNouveauEntity> adherents = inscriptionNouveauDao.getAll();
			try {
				Multipart mp = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();

				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(
						"webmaster@asptt-toulouse-natation.com",
						"ASPTT Toulouse Natation"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress("contact@asptt-toulouse-natation.com"));
				int count = 0;
				int modulo = 10;
				
				StringBuilder message = new StringBuilder(
						"Madame, Monsieur,<p>Nous vous informons que les inscriptions pour la saison 2014-2015 sont ouvertes.<br />"
								+ "Vous pouvez vous inscrire depuis notre site internet à l'adresse suivante <a href=\"http://www.asptt-toulouse-natation.com/#/page/Inscription\">http://www.asptt-toulouse-natation.com/#/page/Inscription</a><br />");

				message.append("<p>Sportivement,<br />"
						+ "Le secrétariat,<br />"
						+ "ASPTT Grand Toulouse Natation<br />"
						+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
				htmlPart.setContent(message.toString(), "text/html");
				mp.addBodyPart(htmlPart);

				msg.setSubject("ASPTT Toulouse Natation - Ouverture des inscriptions",
						"UTF-8");
				msg.setContent(mp);
				for(InscriptionNouveauEntity nouveau: adherents) {
					msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(
							nouveau.getEmail()));
					LOG.warning("Added " + nouveau.getEmail());
					count++;
					if(count % modulo == 0) {
						Transport.send(msg);
						LOG.warning("Sended: " + count);
						msg = new MimeMessage(session);
						mp = new MimeMultipart();
						htmlPart = new MimeBodyPart();
						msg.setFrom(new InternetAddress(
								"webmaster@asptt-toulouse-natation.com",
								"ASPTT Toulouse Natation"));
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress("contact@asptt-toulouse-natation.com"));
						htmlPart.setContent(message.toString(), "text/html");
						mp.addBodyPart(htmlPart);

						msg.setSubject("ASPTT Toulouse Natation - Ouverture des inscriptions",
								"UTF-8");
						msg.setContent(mp);
					}
					
				}
				Transport.send(msg);
			} catch (AddressException e) {
				// ...
			} catch (MessagingException e) {
				// ...
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				LOG.severe("Erreur "
						+ e.getMessage());
			}
		pResp.setContentType("text/html;charset=UTF-8");
		pResp.getWriter().write("ok");
	}
	
	protected void updateAttribute(HttpServletRequest pReq,
			HttpServletResponse pResp) throws IOException {
		String entityIdAsString = pReq.getParameter("entity");
		if (StringUtils.isNotBlank(entityIdAsString)) {
			Long entityId = Long.valueOf(entityIdAsString);
			String attributeAsString = pReq.getParameter("attribute");
			if (StringUtils.isNotBlank(attributeAsString)) {
				String attributeValueAsString = pReq
						.getParameter("attributeValue");
				if (StringUtils.isNotBlank(attributeValueAsString)) {
					InscriptionEntity2 entity = inscription2Dao.get(entityId);
					switch (attributeAsString) {
					case "certificat":
						entity.setCertificat(BooleanUtils
								.toBooleanObject(attributeValueAsString));
						break;
					case "paiement":
						entity.setPaiement(BooleanUtils
								.toBooleanObject(attributeValueAsString));
						break;
					default:
						LOG.warning("Attribute not handled "
								+ attributeAsString);
					}
					inscription2Dao.save(entity);

					// Envoi de l'e-mail
					String envoi = pReq.getParameter("envoi");
					if (BooleanUtils.toBoolean(envoi)) {
						try {
							Properties props = new Properties();
							Session session = Session.getDefaultInstance(props,
									null);
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
								InscriptionEntity2 principal = inscription2Dao
										.get(entity.getPrincipal());
								email = principal.getEmail();
								emailSecondaire = principal
										.getEmailsecondaire();
							} else {
								email = entity.getEmail();
								emailSecondaire = entity.getEmailsecondaire();
							}
							msg.addRecipient(Message.RecipientType.TO,
									new InternetAddress(email));
							if (StringUtils.isNotBlank(emailSecondaire)) {
								msg.addRecipient(Message.RecipientType.CC,
										new InternetAddress(emailSecondaire));
							}
							msg.addRecipient(
									Message.RecipientType.CC,
									new InternetAddress(
											"contact@asptt-toulouse-natation.com"));

							StringBuilder message = new StringBuilder();
							if (BooleanUtils.isTrue(entity.getPaiement())) {
								String montant = pReq.getParameter("montant");
								message = new StringBuilder(
										"Madame, Monsieur,<p>Nous avons bien réceptionné votre dossier.<br />Cependant le paiment n'est pas complet, il manque "
												+ montant
												+ " euros.<br />Merci de nous le faire parvenir"
												+ " avant le 15 septembre 2014.");
								msg.setSubject(
										"ASPTT Toulouse Natation - Cotisation",
										"UTF-8");
							} else {
								message = new StringBuilder(
										"Madame, Monsieur,<p>Nous avons bien réceptionné votre dossier.<br />Cependant il manque le certificat médical.<br />Merci de nous le faire parvenir"
												+ " au plus vite.");
								msg.setSubject(
										"ASPTT Toulouse Natation - Certificat médical",
										"UTF-8");
							}

							message.append("<p>Sportivement,<br />"
									+ "Le secrétariat,<br />"
									+ "ASPTT Grand Toulouse Natation<br />"
									+ "<a href=\"www.asptt-toulouse-natation.com\">www.asptt-toulouse-natation.com</a></p>");
							htmlPart.setContent(message.toString(), "text/html");
							mp.addBodyPart(htmlPart);

							
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
							LOG.severe("Erreur l'adhérent: " + entity.getNom()
									+ "(" + e.getMessage() + ")");
						}
					}
				}
			}
		}
		pResp.setContentType("text/html;charset=UTF-8");
		pResp.getWriter().write("ok");
	}
	
	protected void fixComplet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.CERTIFICAT,
				Boolean.TRUE, Operator.EQUAL));
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.PAIEMENT,
				Boolean.TRUE, Operator.EQUAL));
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.COMPLET,
				Boolean.FALSE, Operator.EQUAL));
		List<InscriptionEntity2> entities = inscription2Dao.find(criteria);
		
		int count = 0;
		for(InscriptionEntity2 entity: entities) {
			if(BooleanUtils.isFalse(entity.getComplet())) {
				sendConfirmation(pReq, pResp, entity.getId());
				count++;
			}
		}
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(count + " / " + entities.size() + "adhérents mis à jour");
	}
	
	protected void deleteNonInscrits(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		List<InscriptionEntity2> entities = inscription2Dao.getAll();
		
		int count = 0;
		for(InscriptionEntity2 entity: entities) {
			if(entity.getInscriptiondt() == null) {
			inscription2Dao.delete(entity.getId());
			count++;
			}
		}
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(count + " / " + entities.size() + "adhérents mis à jour");
	}
}