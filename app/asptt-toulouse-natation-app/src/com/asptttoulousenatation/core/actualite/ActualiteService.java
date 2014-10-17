package com.asptttoulousenatation.core.actualite;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.ActuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;

@Path("/actualites")
@Produces("application/json")
public class ActualiteService {

	private static final int ACTU_PAR_PAGE = 10;
	private ActuDao dao = new ActuDao();
	private DocumentDao documentDao = new DocumentDao();

	@GET
	@Path("list/{pageNumber}")
	public ActualitesResult getActualites(
			@PathParam("pageNumber") Integer pPageNumber,
			@QueryParam("competition") String pCompetition) {
		// Calcul de l'index
		int maxNumber = ACTU_PAR_PAGE * pPageNumber;
		
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		if (StringUtils.isNotBlank(pCompetition)) {
			criteria.add(new CriterionDao<Boolean>(
					ActuEntityFields.COMPETITION, BooleanUtils
							.toBoolean(pCompetition), Operator.EQUAL));
		}
		List<ActuEntity> lEntities = dao.find(criteria, 0, maxNumber);
		ActuTransformer actuTransformer = new ActuTransformer();
		DocumentTransformer documentTransformer = new DocumentTransformer();
		List<ActuUi> actualites = new ArrayList<ActuUi>(lEntities.size());
		for (ActuEntity entity : lEntities) {
			ActuUi lUi = actuTransformer.toUi(entity);
			// Get documents
			List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			lDocumentCriteria.add(new CriterionDao<Long>(
					DocumentEntityFields.MENU, entity.getId(), Operator.EQUAL));
			List<DocumentEntity> lDocumentEntities = documentDao
					.find(lDocumentCriteria);
			List<DocumentUi> lDocumentUis = documentTransformer
					.toUi(lDocumentEntities);
			lUi.setDocumentSet(lDocumentUis);
			actualites.add(lUi);
		}
		ActualitesResult result = new ActualitesResult();
		result.setActualites(actualites);
		return result;
	}
}