package com.asptt.core.document.libelle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.asptt.core.document.LibelleCreateResult;
import com.asptt.core.server.dao.document.LibelleDao;
import com.asptt.core.server.dao.entity.document.LibelleEntity;
import com.asptt.core.server.dao.entity.field.LibelleEntityFields;
import com.asptt.core.server.dao.search.CriterionDao;
import com.asptt.core.server.dao.search.Operator;
import com.asptt.core.server.dao.search.OrderDao;
import com.asptt.core.server.dao.search.OrderDao.OrderOperator;
import com.asptt.core.shared.document.libelle.LibelleUi;

@Path("/libelles")
@Produces("application/json")
public class LibelleService {

	private LibelleDao dao = new LibelleDao();

	@GET
	public LibelleListResult find(@QueryParam("type") String pType) {
		LibelleListResult result = new LibelleListResult();
		List<LibelleEntity> entities = dao.getAll();
		if ("plat".equals(pType)) {
			List<String> libelles = new ArrayList<>(entities.size());
			for (LibelleEntity entity : entities) {
				libelles.add(entity.getIntitule());
			}
			Collections.sort(libelles);
			result.setWholeLibelles(libelles);
		} else {
			Map<String, LibelleUi> libellesMap = LibelleTransformer
					.getInstance().get(entities);
			Collection<LibelleUi> libellesColl = new ArrayList<>(
					libellesMap.values());
			CollectionUtils.filter(libellesColl, new Predicate() {
				@Override
				public boolean evaluate(Object pArg0) {
					LibelleUi ui = (LibelleUi) pArg0;
					return !ui.isHasAncestor();
				}
			});
			List<LibelleUi> libelles = new ArrayList<>(libellesColl);
			Collections.sort(libelles, new Comparator<LibelleUi>() {

				@Override
				public int compare(LibelleUi pO1, LibelleUi pO2) {
					return pO1.getWholeIntitule().compareTo(
							pO2.getWholeIntitule());
				}
			});

			result.setLibelles(libelles);
			List<String> wholeLibelles = new ArrayList<>();
			for (LibelleUi libelle : libellesMap.values()) {
				wholeLibelles.add(libelle.getWholeIntitule());
			}
			Collections.sort(wholeLibelles);
			result.setWholeLibelles(wholeLibelles);
		}
		return result;
	}

	@GET
	@Path("{intitule}")
	public LibelleEntity getLibelle(@PathParam("intitule") String pIntitule) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(LibelleEntityFields.INTITULE,
				pIntitule, Operator.EQUAL));
		List<LibelleEntity> entities = dao.find(criteria, new OrderDao(
				LibelleEntityFields.INTITULE, OrderOperator.ASC));
		final LibelleEntity entity;
		if (CollectionUtils.isNotEmpty(entities)) {
			entity = entities.get(0);
		} else {
			entity = null;
		}
		return entity;
	}

	@POST
	public LibelleCreateResult create(@QueryParam("intitule") String pIntitule,
			@QueryParam("parent") String pParent) {
		LibelleCreateResult result = new LibelleCreateResult();
		String intitule = "";
		if (StringUtils.isNotBlank(pParent)) {
			intitule = pParent + "/" + pIntitule;
		} else {
			intitule = pIntitule;
		}
		if (StringUtils.isNotBlank(intitule)) {
			// Si parent déterminer si le parent exist, si oui déterminer si
			// intitule exist, si oui fin, sinon créer intitule avec le parent.
			// Sinon (parent n'existe pas) créer parent et intitule

			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(LibelleEntityFields.INTITULE,
					intitule, Operator.EQUAL));
			List<LibelleEntity> entities = dao.find(criteria);
			if (CollectionUtils.isNotEmpty(entities)) {
				result.setExists(true);
			} else {
				result.setExists(false);
				LibelleEntity entity = new LibelleEntity();
				entity.setIntitule(intitule);
				dao.save(entity);
			}
		} else {
			result.setNoTitle(true);
		}
		return result;
	}

	@PUT
	@Path("{intitule}")
	public LibelleUpdateResult update(@PathParam("intitule") String pIntitule,
			@QueryParam("newintitule") String pNewIntitule) {
		LibelleUpdateResult result = new LibelleUpdateResult();
		if (StringUtils.isNotBlank(pNewIntitule)) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(LibelleEntityFields.INTITULE,
					pIntitule, Operator.EQUAL));
			List<LibelleEntity> entities = dao.find(criteria);
			if (CollectionUtils.isNotEmpty(entities)) {
				LibelleEntity entity = entities.get(0);
				// Test existence du nouveau libelle
				criteria = new ArrayList<CriterionDao<? extends Object>>(1);
				criteria.add(new CriterionDao<String>(
						LibelleEntityFields.INTITULE, pNewIntitule,
						Operator.EQUAL));
				entities = dao.find(criteria);
				if (CollectionUtils.isNotEmpty(entities)) {
					result.setExists(true);
					result.setSuccess(false);
				} else {
					entity.setIntitule(pNewIntitule);
					dao.save(entity);
					result.setSuccess(true);
				}
			} else {
				result.setSuccess(false);

			}
		}
		return result;
	}

	@DELETE
	@Path("{intitule}")
	public void remove(@PathParam("intitule") String pIntitule) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<String>(LibelleEntityFields.INTITULE,
				pIntitule, Operator.EQUAL));
		List<LibelleEntity> entities = dao.find(criteria);
		if (CollectionUtils.isNotEmpty(entities)) {
			for (LibelleEntity entity : entities) {
				dao.delete(entity);
			}
		}
	}
}