package com.asptttoulousenatation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class InscritsAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(InscritsAction.class
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
		String action = pReq.getParameter("action");
		if("inscrits".equals(action)) {
			inscrits(pReq, pResp);
		} else if("emailLeo".equals(action)) {
			emailLeo(pResp);
		} else if("renouvellement".equals(action)) {
			renouvellement(pReq, pResp);
		} else if("nonRenouvellement".equals(action)) {
			nonRenouvellement(pReq, pResp);
		} else if("nouveau".equals(action)) {
			nouveau(pReq, pResp);
		}
	}

	private void inscrits(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.SAISIE,
				Boolean.TRUE, Operator.EQUAL));
		List<InscriptionEntity> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity entity : entities) {
			try {
				List<String> results = new ArrayList<String>();
				results.add(Long.toString(entity.getId()));
				results.add(entity.getNom());
				results.add(entity.getPrenom());
				if (entity.getNouveauGroupe() != null) {
					GroupEntity group = groupDao.get(entity.getNouveauGroupe());
					results.add(group.getTitle());
				}

				if (StringUtils.isNotBlank(entity.getCreneaux())) {
					String[] creneaux = entity.getCreneaux().split(";");
					results.add(Integer.toString(creneaux.length));
				}
				StrBuilder resultAsString = new StrBuilder();
				resultAsString.appendWithSeparators(results, ";");
				builder.append(resultAsString.toString()).appendNewLine();
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}
		pResp.getOutputStream().print(
				entities.size() + "\n" + builder.toString());
	}

	private void emailLeo(HttpServletResponse pResp) throws ServletException,
			IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.SAISIE,
				Boolean.TRUE, Operator.EQUAL));
		List<InscriptionEntity> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity entity : entities) {
			try {
				StrBuilder resultAsString = new StrBuilder();
				List<String> results = new ArrayList<String>();
				results.add(Long.toString(entity.getId()));
				results.add(entity.getNom());
				results.add(entity.getPrenom());
				results.add(entity.getEmail());
				if (entity.getNouveauGroupe() != null) {
					GroupEntity group = groupDao.get(entity.getNouveauGroupe());
					results.add(group.getTitle());
				}

				if (StringUtils.isNotBlank(entity.getCreneaux())) {
					String[] creneaux = entity.getCreneaux().split(";");
					for (String creneau : creneaux) {
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
								if (slotEntity != null) {
									resultAsString.appendWithSeparators(
											results, ";");
									builder.append(resultAsString.toString())
											.appendNewLine();
								}
							}
						}

					}
				}
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}
		pResp.getOutputStream().print(
				entities.size() + "\n" + builder.toString());
	}
	
	private void renouvellement(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.SAISIE,
				Boolean.TRUE, Operator.EQUAL));
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.MOTDEPASSE,
				"701", Operator.NOT_EQUAL));
		List<InscriptionEntity> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity entity : entities) {
			try {
				List<String> results = new ArrayList<String>();
				results.add(Long.toString(entity.getId()));
				results.add(entity.getNom());
				results.add(entity.getPrenom());
				if (entity.getNouveauGroupe() != null) {
					GroupEntity group = groupDao.get(entity.getNouveauGroupe());
					results.add(group.getTitle());
				}

				if (StringUtils.isNotBlank(entity.getCreneaux())) {
					String[] creneaux = entity.getCreneaux().split(";");
					results.add(Integer.toString(creneaux.length));
				}
				StrBuilder resultAsString = new StrBuilder();
				resultAsString.appendWithSeparators(results, ";");
				builder.append(resultAsString.toString()).appendNewLine();
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}
		pResp.getOutputStream().print(
				entities.size() + "\n" + builder.toString());
	}
	
	private void nouveau(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.SAISIE,
				Boolean.TRUE, Operator.EQUAL));
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.MOTDEPASSE,
				"701", Operator.EQUAL));
		List<InscriptionEntity> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity entity : entities) {
			try {
				List<String> results = new ArrayList<String>();
				results.add(Long.toString(entity.getId()));
				results.add(entity.getNom());
				results.add(entity.getPrenom());
				if (entity.getNouveauGroupe() != null) {
					GroupEntity group = groupDao.get(entity.getNouveauGroupe());
					results.add(group.getTitle());
				}

				if (StringUtils.isNotBlank(entity.getCreneaux())) {
					String[] creneaux = entity.getCreneaux().split(";");
					results.add(Integer.toString(creneaux.length));
				}
				StrBuilder resultAsString = new StrBuilder();
				resultAsString.appendWithSeparators(results, ";");
				builder.append(resultAsString.toString()).appendNewLine();
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}
		pResp.getOutputStream().print(
				entities.size() + "\n" + builder.toString());
	}
	
	private void nonRenouvellement(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.SAISIE,
				Boolean.FALSE, Operator.NULL));
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.MOTDEPASSE,
				"701", Operator.NOT_EQUAL));
		List<InscriptionEntity> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity entity : entities) {
			try {
				List<String> results = new ArrayList<String>();
				results.add(Long.toString(entity.getId()));
				results.add(entity.getNom());
				results.add(entity.getPrenom());
				if (entity.getNouveauGroupe() != null) {
					GroupEntity group = groupDao.get(entity.getNouveauGroupe());
					results.add(group.getTitle());
				}

				if (StringUtils.isNotBlank(entity.getCreneaux())) {
					String[] creneaux = entity.getCreneaux().split(";");
					results.add(Integer.toString(creneaux.length));
				}
				StrBuilder resultAsString = new StrBuilder();
				resultAsString.appendWithSeparators(results, ";");
				builder.append(resultAsString.toString()).appendNewLine();
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}
		pResp.getOutputStream().print(
				entities.size() + "\n" + builder.toString());
	}
}