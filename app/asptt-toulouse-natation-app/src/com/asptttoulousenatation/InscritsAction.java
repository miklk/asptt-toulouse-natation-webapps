package com.asptttoulousenatation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
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
		} else if ("delete".equals(action)) {
			delete(pReq, pResp);
		} else if ("tarif".equals(action)) {
			tarif(pReq, pResp);
		} else if("all".equals(action)) {
			getAll(pReq, pResp);
		} else if("updateAll".equals(action)) {
			updateAll(pReq, pResp);
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
				results.add(BooleanUtils.toString(entity.getCertificat(), "Oui", "Non", "Non"));
				results.add(BooleanUtils.toString(entity.getPaiement(), "Oui", "Non", "Non"));
				
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
				//computeTarif(new HashSet<Long>(), entity);
				//results.add(entity.getTarif() + "");
				if(group != null) {
					results.add(group.getTarif() + "");
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
				if (entity.getInscriptiondt() == null
						|| StringUtils.isBlank(entity.getNom())) {
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
		pResp.getOutputStream().print(count + "\n" + builder.toString());
	}

	private void tarif(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		Set<Long> computed = new HashSet<>();
		int count = 0;
		int index = 0;
		while (count< 5 && index < all.size()) {
			InscriptionEntity2 entity = all.get(index);
			if (entity.getInscriptiondt() != null && !computed.contains(entity.getId()) && entity.getTarif() == null && entity.getNouveauGroupe() != null) {
				try {
					computeTarif(computed, entity);
					count++;
				} catch(Exception e) {
					LOG.severe(e.getMessage());
				}
				
			}
			index++;
		}
		pResp.getOutputStream().print(count + " updated");
	}

	private void computeTarif(Set<Long> computed, InscriptionEntity2 adherent) {
		List<InscriptionEntity2> entities = new ArrayList<>();

		InscriptionEntity2 principal = adherent;
		if (adherent.getPrincipal() != null) {
			principal = inscriptionDao.get(adherent.getPrincipal());
		}
		entities.add(principal);

		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(InscriptionEntityFields.PRINCIPAL,
				principal.getPrincipal(), Operator.EQUAL));
		List<InscriptionEntity2> fils = inscriptionDao.find(criteria);
		if (CollectionUtils.isNotEmpty(fils)) {
			entities.addAll(fils);
		}

		Collections.sort(entities, new Comparator<InscriptionEntity2>() {

			@Override
			public int compare(InscriptionEntity2 pO1, InscriptionEntity2 pO2) {
				GroupEntity group1 = groups.get(pO1.getNouveauGroupe());
				GroupEntity group2 = groups.get(pO2.getNouveauGroupe());
				return group1.getTarifWeight() >= group2.getTarifWeight() ? 1
						: -1;
			}
		});

		int count = 0;
		boolean found = false;
		while(!found && count < entities.size()) {
		InscriptionEntity2 entity = entities.get(count);
			count++;
			GroupEntity group = groups.get(entity.getNouveauGroupe());
			int tarif = 0;
			switch (count) {
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
			tarifs.put(entity.getId(), tarif);
			if(entity.getId() == adherent.getId()) {
				adherent.setTarif(tarif);
				found = true;
			}
			//entity.setTarif(tarif);
			//inscriptionDao.save(entity);
		}
	}
	
	private static List<InscriptionEntity2> all;
	private static Map<Long, Integer> tarifs = new HashMap<>();
	private static Map<Long, GroupEntity> groups = new HashMap<>();
	
	private void getAll(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Object>(
				InscriptionEntityFields.INSCRIPTIONDT, null, Operator.NOT_NULL));
		all = inscriptionDao.getAll();
		
		List<GroupEntity> groupes = groupDao.getAll();
		for(GroupEntity entity: groupes) {
			groups.put(entity.getId(), entity);
		}
	}
	
	private void updateAll(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		for(Map.Entry<Long, Integer> entry: tarifs.entrySet()) {
			InscriptionEntity2 entity = inscriptionDao.get(entry.getKey());
			entity.setTarif(entry.getValue());
			inscriptionDao.save(entity);
		}
	}
}