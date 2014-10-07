package com.asptttoulousenatation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class InscritsAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(InscritsAction.class
			.getName());

	private Inscription2Dao inscriptionDao = new Inscription2Dao();
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
		if ("inscrits".equals(action)) {
			inscrits(pReq, pResp);
		} else if ("renouvellement".equals(action)) {
			renouvellement(pReq, pResp);
		} else if ("nonRenouvellement".equals(action)) {
			nonRenouvellement(pReq, pResp);
		} else if ("nouveau".equals(action)) {
			nouveau(pReq, pResp);
		} else if ("complets".equals(action)) {
			complets(pReq, pResp);
		} else if ("incomplets".equals(action)) {
			incomplets(pReq, pResp);
		} else if ("incomplets2".equals(action)) {
			incomplets2(pReq, pResp);
		} else if ("finance".equals(action)) {
			finance(pReq, pResp);
		} else if("delete".equals(action)) {
			delete(pReq, pResp);
		}
	}

	private void inscrits(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity2 entity : entities) {
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

	private void complets(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.COMPLET,
				Boolean.TRUE, Operator.EQUAL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity2 entity : entities) {
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

	private void incomplets(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.COMPLET,
				Boolean.FALSE, Operator.EQUAL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity2 entity : entities) {
			try {
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

	private void incomplets2(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.COMPLET,
				null, Operator.NULL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity2 entity : entities) {
			try {
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

	private void renouvellement(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		criteria.add(new CriterionDao<Object>(InscriptionEntityFields.NOUVEAU,
				null, Operator.NULL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		int counter = 0;
		for (InscriptionEntity2 entity : entities) {
			try {
				counter++;
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
		pResp.getOutputStream().print(counter + "\n" + builder.toString());
	}

	private void nouveau(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		criteria.add(new CriterionDao<Boolean>(InscriptionEntityFields.NOUVEAU,
				Boolean.TRUE, Operator.EQUAL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity2 entity : entities) {
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

	private void nonRenouvellement(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NULL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		int counter = 0;
		for (InscriptionEntity2 entity : entities) {
			try {
				if (StringUtils.isEmpty(entity.getMotdepasse())) {
					counter++;
					List<String> results = new ArrayList<String>();
					results.add(Long.toString(entity.getId()));
					results.add(entity.getNom());
					results.add(entity.getPrenom());
					if (entity.getNouveauGroupe() != null) {
						GroupEntity group = groupDao.get(entity
								.getNouveauGroupe());
						results.add(group.getTitle());
					}

					if (StringUtils.isNotBlank(entity.getCreneaux())) {
						String[] creneaux = entity.getCreneaux().split(";");
						results.add(Integer.toString(creneaux.length));
					}
					StrBuilder resultAsString = new StrBuilder();
					resultAsString.appendWithSeparators(results, ";");
					builder.append(resultAsString.toString()).appendNewLine();
				}
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}
		pResp.getOutputStream().print(counter + "\n" + builder.toString());
	}

	private void finance(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		List<InscriptionEntity2> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity2 entity : entities) {
			try {
				List<String> results = new ArrayList<String>();
				results.add(Long.toString(entity.getId()));
				results.add(entity.getNom());
				results.add(entity.getPrenom());

				final GroupEntity group;
				if (entity.getNouveauGroupe() != null) {
					group = groupDao.get(entity.getNouveauGroupe());
					results.add(group.getTitle());
				} else {
					group = null;
					results.add("SANS GROUPE");
				}
				results.add(BooleanUtils.toStringYesNo(entity.getComplet()));

				// Tarif
				if (group != null) {
					results.add(Integer.toString(group.getTarif()));
				} else {
					results.add("");
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
	
	private void delete(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();

		List<InscriptionEntity2> entities = inscriptionDao.getAll();
		int count = 0;
		for (InscriptionEntity2 entity : entities) {
			try {
				if (entity.getInscriptiondt() == null || StringUtils.isBlank(entity.getNom())) {
					count++;
					List<String> results = new ArrayList<String>();
					results.add(Long.toString(entity.getId()));
					results.add(entity.getNom());
					results.add(entity.getPrenom());
					if (entity.getNouveauGroupe() != null) {
						GroupEntity group = groupDao.get(entity
								.getNouveauGroupe());
						results.add(group.getTitle());
					}

					results.add(Boolean.toString(StringUtils.isNotBlank(entity
							.getCreneaux())));
					StrBuilder resultAsString = new StrBuilder();
					resultAsString.appendWithSeparators(results, ";");
					builder.append(resultAsString.toString()).appendNewLine();
				}
			} catch (Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId()
						+ " (" + e.getMessage() + ")");
			}
		}
		pResp.getOutputStream().print(
				count + "\n" + builder.toString());
	}

}