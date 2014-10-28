package com.asptttoulousenatation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

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
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.InscriptionTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class InscriptionAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(InscriptionAction.class
			.getName());

	private InscriptionTransformer inscriptionTransformer = new InscriptionTransformer();
	private InscriptionDao inscriptionDao = new InscriptionDao();
	private Inscription2Dao inscription2Dao = new Inscription2Dao();
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
		String action = pReq.getParameter("action");
		if ("loadCreneaux".equals(action)) {
			loadCreneaux(pReq, pResp);
		} else if ("inscription".equals(action)) {
			inscription(pReq, pResp);
		} else if ("inscriptionSub".equals(action)) {
			inscriptionSub(pReq, pResp);
		} else if ("findEmail".equals(action)) {
			findEmail(pReq, pResp);
		} else if ("findNomPrenom".equals(action)) {
			findNomPrenom(pReq, pResp);
		} else if ("loadGroupes".equals(action)) {
			loadGroupes(pReq, pResp);
		} else if ("nouveau".equals(action)) {
			nouveau(pReq, pResp);
		} else if("imprimerNew".equals(action)) {
			imprimerNew(pReq, pResp);
		}
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

	protected void inscription(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		pReq.getSession().removeAttribute("inscriptionIds");
		pReq.getSession().removeAttribute("inscriptionId");
		List<InscriptionEntity> adherents = (List<InscriptionEntity>) pReq
				.getSession().getAttribute("data");
		InscriptionEntity entity = adherents.get(0);
		StringBuilder creneau = new StringBuilder();
		Enumeration params = pReq.getParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			if (param.contains("creneau")) {
				String paramValue = pReq.getParameter(param);
				if (BooleanUtils.toBoolean(paramValue)) {
					String creneauId = param.replace("creneau", "");
					// Update creneaux
					if (!StringUtils.contains(entity.getCreneaux(), creneauId)) {
						SlotEntity creneauEntity = slotDao.get(Long
								.valueOf(creneauId));
						if (creneauEntity != null) {
							creneauEntity.setPlaceRestante(creneauEntity
									.getPlaceRestante() - 1);
							slotDao.save(creneauEntity);
						}
					}
					creneau.append(creneauId).append(";");
				}
			}
		}

		entity.setCreneaux(creneau.toString());
		try {
			BeanUtils.populate(entity, pReq.getParameterMap());
			inscriptionTransformer.update(entity);
			entity.setSaisie(true);
			Long inscriptionId = inscriptionDao.save(entity).getId();
			List<Long> inscriptionIds = new ArrayList<Long>(2);
			inscriptionIds.add(inscriptionId);
			pReq.getSession().setAttribute("inscriptionIds", inscriptionIds);
			pReq.getSession().setAttribute("inscriptionId", inscriptionId);
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write("1");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void inscriptionSub(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long principalId = (Long) pReq.getSession().getAttribute(
				"inscriptionId");
		String adherentIndexStr = pReq.getParameter("adherentIndex");
		final InscriptionEntity entity;
		if (StringUtils.isNotBlank(adherentIndexStr)) {
			Integer adherentIndex = Integer.valueOf(adherentIndexStr) - 1;
			List<InscriptionEntity> adherents = (List<InscriptionEntity>) pReq
					.getSession().getAttribute("data");
			if (adherentIndex < adherents.size()) {
				entity = adherents.get(adherentIndex);
			} else {
				entity = new InscriptionEntity();
			}
		} else {
			entity = new InscriptionEntity();
		}
		
		StringBuilder creneau = new StringBuilder();
		Enumeration params = pReq.getParameterNames();
		while (params.hasMoreElements()) {
			String param = (String) params.nextElement();
			if (param.contains("creneau")) {
				String paramValue = pReq.getParameter(param);
				if (BooleanUtils.toBoolean(paramValue)) {
					String creneauId = param.replace("creneau", "");
					if (creneauId.contains("_")) {
						creneauId = creneauId.split("_")[1];
					}
					// Update creneaux
					if(!StringUtils.contains(entity.getCreneaux(), creneauId)) {
					SlotEntity creneauEntity = slotDao.get(Long
							.valueOf(creneauId));
					if (creneauEntity != null) {
						creneauEntity.setPlaceRestante(creneauEntity
								.getPlaceRestante() - 1);
						slotDao.save(creneauEntity);
					}
					creneau.append(creneauId).append(";");
					}
				}
			}
		}
		try {
			
			entity.setCreneaux(creneau.toString());
			entity.setPrincipal(principalId);

			BeanUtils.populate(entity, pReq.getParameterMap());
			String dateNaissance = entity.getDatenaissance();
			if (dateNaissance.length() == 10
					&& StringUtils.isNumeric(dateNaissance.substring(2, 2))) {
				String separateur = dateNaissance.substring(2, 2);
				String[] dateNaissanceSplit = dateNaissance.split(separateur);
				dateNaissance = dateNaissanceSplit[2] + dateNaissanceSplit[1]
						+ dateNaissanceSplit[0];
				entity.setDatenaissance(dateNaissance);
			}
			InscriptionEntity findEntity = findByUniqueNomPrenomNaissance(
					entity.getNom().toUpperCase(), entity.getPrenom()
							.toUpperCase(), entity.getDatenaissance());
			if (findEntity != null) {
				entity.setId(findEntity.getId());
			}
			entity.setSaisie(true);
			inscriptionTransformer.update(entity);
			if (entity.isSupprimer()) {
				inscriptionDao.delete(entity);
			} else {
				InscriptionEntity inscriptionSaved = inscriptionDao
						.save(entity);
				List<Long> inscriptionIds = (List<Long>) pReq.getSession()
						.getAttribute("inscriptionIds");
				if (inscriptionIds == null) {
					inscriptionIds = new ArrayList<Long>(1);
				}
				inscriptionIds.add(inscriptionSaved.getId());
				pReq.getSession()
						.setAttribute("inscriptionIds", inscriptionIds);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pResp.setContentType("text/plain;charset=UTF-8");
		List<Long> inscriptionIds = (List<Long>) pReq.getSession()
				.getAttribute("inscriptionIds");
		pResp.getWriter().write(Integer.toString(inscriptionIds.size()));
	}

	protected void nouveau(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		String email = pReq.getParameter("nouveau_email");
		// Exists
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.EMAIL,
				email, Operator.EQUAL));
		List<InscriptionEntity> entities = inscriptionDao.find(criteria);
		if (CollectionUtils.isNotEmpty(entities)) {
			pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter()
					.write("L'adresse e-mail "
							+ email
							+ " est déjà enregistrée. Vous avez dû recevoir votre code d'accès (pensez à vérifier vos spams). Dans le cas contraire contactez webmaster@asptt-toulouse-natation.com");
		} else {
			try {
				InscriptionEntity entity = new InscriptionEntity();
				entity.setEmail(email);
				String code = RandomStringUtils.randomNumeric(4);
				entity.setMotdepasse(code);
				inscriptionDao.save(entity);

				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				Multipart mp = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();
				String msgBody = "Madame, Monsieurs,<br />"
						+ "Vous pouvez maintenant accéder au formulaire d'inscription en utilisant le code suivant: "
						+ "<b>" + code + "</b>"
						+ ".<br />"
						+ "<a href=\"http://asptt-toulouse-natation.com/v2/inscription.html\">Inscription en ligne - ASPTT Toulouse Natation</a>"
						+ "<p>Sportivement,<br />ASPTT Toulouse Natation</p>";

				htmlPart.setContent(msgBody, "text/html");
				mp.addBodyPart(htmlPart);
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(
						"webmaster@asptt-toulouse-natation.com",
						"ASPTT Toulouse Natation"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						entity.getEmail()));
				msg.setSubject("Votre compte web a été créé.", "UTF-8");
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
		}
	}

	protected void findEmail(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		String email = pReq.getParameter("find_email");
		String motdepasse = pReq.getParameter("find_email_motdepasse");
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.EMAIL,
				email, Operator.EQUAL));
		criteria.add(new CriterionDao<String>(
				InscriptionEntityFields.MOTDEPASSE, motdepasse, Operator.EQUAL));
		List<InscriptionEntity> adherents = inscriptionDao.find(criteria);
		if (CollectionUtils.isNotEmpty(adherents)) {
			InscriptionEntity adherent = adherents.get(0);
			if (adherent.getPrincipal() != null) {
				adherents.clear();
				adherents.add(inscriptionDao.get(adherent.getPrincipal()));
				List<CriterionDao<? extends Object>> lPrincipalCriteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				lPrincipalCriteria.add(new CriterionDao<Long>(
						InscriptionEntityFields.PRINCIPAL, adherent
								.getPrincipal(), Operator.EQUAL));
				adherents.addAll(inscriptionDao.find(lPrincipalCriteria));
			} else {
				List<CriterionDao<? extends Object>> lPrincipalCriteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				lPrincipalCriteria.add(new CriterionDao<Long>(
						InscriptionEntityFields.PRINCIPAL, adherent.getId(),
						Operator.EQUAL));
				adherents.addAll(inscriptionDao.find(lPrincipalCriteria));
			}
			// Get groupes
			GroupDao lGroupDao = new GroupDao();
			for (InscriptionEntity entity : adherents) {
				if (entity.getNouveauGroupe() != null
						&& entity.getNouveauGroupe() != 0l) {
					GroupEntity groupEntity = lGroupDao.get(entity
							.getNouveauGroupe());
					entity.setGroupEntity(groupEntity);
				}
			}

			pResp.setContentType("text/plain;charset=UTF-8");
			// if (adherent.isSaisie()) {
			// pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			// pResp.getWriter()
			// .write("Votre dossier a déjà été saisie, en cas de problème veuillez prendre contact par e-mail: webmaster@asptt-toulouse-natation.com");
			// } else {
			pReq.getSession().setAttribute("data", adherents);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(adherents);
			pResp.setContentType("application/json;charset=UTF-8");
			pResp.getWriter().write(json);
			// }
		} else {
			pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write(
					"L'adresse e-mail " + email
							+ " n'est pas enregistrée. Avez-vous entrez les trois chiffres composant votre code d'accès ?");
		}
	}

	protected void findNomPrenom(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		pReq.getSession().removeAttribute("inscriptionIds");
		pReq.getSession().removeAttribute("inscriptionId");
		pReq.getSession().removeAttribute("data");
		String nom = pReq.getParameter("find_nom");
		String prenom = pReq.getParameter("find_prenom");
		String dateNaissance = pReq.getParameter("find_dateNaissance");
		LOG.warning(nom + " " + prenom + " " + dateNaissance);
		List<InscriptionEntity> adherents = findByNomPrenomNaissance(nom,
				prenom, dateNaissance);
		if (CollectionUtils.isNotEmpty(adherents)) {

			pReq.getSession().setAttribute("data", adherents);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(adherents);
			System.out.println(json);
			pResp.setContentType("application/json;charset=UTF-8");
			pResp.getWriter().write(json);
		} else {
			pResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			pResp.setContentType("application/text;charset=UTF-8");
			pResp.getWriter().write(
					"Cet adhérent n'est pas enregistrée dans nos fichiers. Il doit s'agir d'une erreur de frappe dans nos fichiers, contactez-nous webmaster@asptt-toulouse-natation.com");
		}
	}

	private List<InscriptionEntity> findByNomPrenomNaissance(String nom,
			String prenom, String dateNaissance) {
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				3);
		lCriteria.add(new CriterionDao<String>(InscriptionEntityFields.NOM, nom
				.trim().toUpperCase(), Operator.EQUAL));
		lCriteria.add(new CriterionDao<String>(InscriptionEntityFields.PRENOM,
				prenom.trim().toUpperCase(), Operator.EQUAL));
		lCriteria.add(new CriterionDao<String>(
				InscriptionEntityFields.DATENAISSANCE, dateNaissance,
				Operator.EQUAL));
		List<InscriptionEntity> adherents = inscriptionDao.find(lCriteria);
		if (CollectionUtils.isNotEmpty(adherents)) {
			InscriptionEntity adherent = adherents.get(0);
			if (adherent.getPrincipal() != null) {
				adherents.clear();
				adherents.add(inscriptionDao.get(adherent.getPrincipal()));
				List<CriterionDao<? extends Object>> lPrincipalCriteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				lPrincipalCriteria.add(new CriterionDao<Long>(
						InscriptionEntityFields.PRINCIPAL, adherent
								.getPrincipal(), Operator.EQUAL));
				adherents.addAll(inscriptionDao.find(lPrincipalCriteria));
			} else {
				List<CriterionDao<? extends Object>> lPrincipalCriteria = new ArrayList<CriterionDao<? extends Object>>(
						1);
				lPrincipalCriteria.add(new CriterionDao<Long>(
						InscriptionEntityFields.PRINCIPAL, adherent.getId(),
						Operator.EQUAL));
				adherents.addAll(inscriptionDao.find(lPrincipalCriteria));
			}
			// Get groupes
			GroupDao lGroupDao = new GroupDao();
			for (InscriptionEntity entity : adherents) {
				if (entity.getNouveauGroupe() != null
						&& entity.getNouveauGroupe() != 0l) {
					GroupEntity groupEntity = lGroupDao.get(entity
							.getNouveauGroupe());
					entity.setGroupEntity(groupEntity);
				}
			}
		}
		return adherents;
	}

	private InscriptionEntity findByUniqueNomPrenomNaissance(String nom,
			String prenom, String dateNaissance) {
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				3);
		lCriteria.add(new CriterionDao<String>(InscriptionEntityFields.NOM, nom
				.toUpperCase(), Operator.EQUAL));
		lCriteria.add(new CriterionDao<String>(InscriptionEntityFields.PRENOM,
				prenom.toUpperCase(), Operator.EQUAL));
		lCriteria.add(new CriterionDao<String>(
				InscriptionEntityFields.DATENAISSANCE, dateNaissance,
				Operator.EQUAL));
		List<InscriptionEntity> adherents = inscriptionDao.find(lCriteria);
		final InscriptionEntity adherent;
		if (CollectionUtils.isNotEmpty(adherents)) {
			adherent = adherents.get(0);
		} else {
			adherent = null;
		}
		return adherent;
	}

	protected void loadGroupes(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(GroupEntityFields.INSCRIPTION,
				Boolean.TRUE, Operator.EQUAL));
		GroupDao dao = new GroupDao();
		List<GroupEntity> entities = dao.find(criteria);
		List<GroupUi> lUis = new GroupTransformer().toUi(entities);
		Collections.sort(lUis, new Comparator<GroupUi>() {

			@Override
			public int compare(GroupUi pO1, GroupUi pO2) {
				if (StringUtils.containsIgnoreCase(pO1.getTitle(), "Ecole")) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(lUis);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}
	
	protected void imprimerNew(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		Long numero = 0l;
		try {
			numero = Long.valueOf(pReq.getParameter("numero"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		InscriptionEntity2 adherent = inscription2Dao.get(numero);
		InscriptionEntity2 parent = adherent;
		if (adherent.getPrincipal() != null) {
			parent = inscription2Dao.get(adherent.getPrincipal());
		}
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path.replace("WEB-INF/classes/", "");
    	PdfReader reader = new PdfReader(new FileInputStream(path + "doc/bulletin.pdf"));

		
    	ServletOutputStream out = pResp.getOutputStream();
    	String contentType = "application/pdf";
		String contentDisposition = "attachment;filename=inscription_asptt_natation.pdf;";
		pResp.setContentType(contentType);
		pResp.setHeader("Content-Disposition", contentDisposition);

		try {
			PdfStamper 	stamper = new PdfStamper(reader, out);
    	AcroFields fields = stamper.getAcroFields();

		fields.setField("untitled1", adherent.getNom());
		fields.setField("untitled2", adherent.getPrenom());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dateNaissance = format.parse(adherent.getDatenaissance());
				SimpleDateFormat formatDD = new SimpleDateFormat("dd");
				fields.setField("untitled3", formatDD.format(dateNaissance));
				SimpleDateFormat formatMM = new SimpleDateFormat("MM");
				fields.setField("untitled4", formatMM.format(dateNaissance));
				SimpleDateFormat formatYYYY = new SimpleDateFormat("yyyy");
				fields.setField("untitled5", formatYYYY.format(dateNaissance));
			} catch (ParseException e) {
				LOG.severe("Adherent " + adherent.getNom() + " "
						+ e.getMessage());
			}
		
		
		fields.setField("untitled6", parent.getProfession());
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
		
		if(BooleanUtils.isTrue(adherent.getNouveau())) {
			fields.setField("untitled17", "X");
		} else {
			fields.setField("untitled18", "X");
		}
		
		GroupEntity group = groupDao.get(adherent.getNouveauGroupe());
		if(BooleanUtils.isTrue(group.getLicenceFfn())) {
			fields.setField("untitled19", "X");
			fields.setField("untitled20", "X");
		}
		
		fields.setField("accidentNom1", StringUtils.defaultString(parent.getAccidentNom1()) + " " + StringUtils.defaultString(parent.getAccidentPrenom1()));
		fields.setField("accidentTel1", parent.getAccidentTelephone1());
		fields.setField("accidentNom2", StringUtils.defaultString(parent.getAccidentNom2()) + " " + StringUtils.defaultString(parent.getAccidentPrenom2()));
		fields.setField("accidentTel2", parent.getAccidentTelephone2());
		
        stamper.close();
        reader.close();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		out.flush();
		out.close();
	}
}