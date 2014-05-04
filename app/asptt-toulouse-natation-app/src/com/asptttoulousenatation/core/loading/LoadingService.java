package com.asptttoulousenatation.core.loading;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;

@Path("/loading")
@Produces("application/json")
public class LoadingService {

	private static final Logger LOG = Logger.getLogger(LoadingService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private MenuDao menuDao = new MenuDao();
	private AreaDao areaDao = new AreaDao();
	private ActuDao actuDao = new ActuDao();
	private DocumentDao documentDao = new DocumentDao();

	@GET
	public LoadingResult getLoading() {

		Long startTime = System.currentTimeMillis();
		LoadingResult result = new LoadingResult();
		// Structure
		List<CriterionDao<? extends Object>> lAreaSelectionCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<String> lAreaSelectionCriterion = new CriterionDao<String>(
				AreaEntityFields.PROFILE, ProfileEnum.PUBLIC.toString(),
				Operator.EQUAL);
		lAreaSelectionCriteria.add(lAreaSelectionCriterion);
		// Order
		OrderDao lOrderDao = new OrderDao(AreaEntityFields.ORDER,
				OrderDao.OrderOperator.ASC);
		List<AreaEntity> lAreaEntities = areaDao.find(lAreaSelectionCriteria,
				lOrderDao);
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
		lAreaCriterion.setEntityField(MenuEntityFields.AREA);
		lAreaCriterion.setOperator(Operator.EQUAL);
		lCriteria.add(lAreaCriterion);
		lCriteria.add(new CriterionDao<Boolean>(MenuEntityFields.DISPLAY, Boolean.TRUE, Operator.EQUAL));
		OrderDao lMenuOrder = new OrderDao(MenuEntityFields.ORDER,
				OrderDao.OrderOperator.ASC);

		for (AreaEntity lAreaEntity : lAreaEntities) {
			MenuLoadingUi lMenuLoadingUi = new MenuLoadingUi(lAreaEntity.getTitle());
			// Get menu
			lAreaCriterion.setValue(lAreaEntity.getId());
			List<MenuEntity> lMenuEntities = menuDao
					.find(lCriteria, lMenuOrder);
			for (MenuEntity lMenuEntity : lMenuEntities) {
				MenuLoadingUi  lMenuLoadingUi2 = new MenuLoadingUi(lMenuEntity.getTitle());
				if (lMenuEntity.getParent() == null) {
					// Retrieve sub menu
					for (Long lSubMenuId : lMenuEntity.getSubMenu()) {
						MenuEntity lSubMenu = menuDao.get(lSubMenuId);
						lMenuLoadingUi2.addSubMenu(new MenuLoadingUi(lSubMenu.getTitle()));
					}
				}
				lMenuLoadingUi.addSubMenu(lMenuLoadingUi2);;
			}
			result.addMenu(lMenuLoadingUi);
		}

		// Actu
		List<ActuEntity> lEntities = actuDao.getAll(0, 7);
		ActuTransformer actuTransformer = new ActuTransformer();
		DocumentTransformer documentTransformer = new DocumentTransformer();
		for (ActuEntity entity : lEntities) {
			ActuUi lUi = actuTransformer.toUi(entity);
			// Get documents
			List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>();
			lDocumentCriterion.setEntityField(DocumentEntityFields.MENU);
			lDocumentCriterion.setOperator(Operator.EQUAL);
			lDocumentCriteria.add(lDocumentCriterion);
			lDocumentCriterion.setValue(entity.getId());
			List<DocumentEntity> lDocumentEntities = documentDao
					.find(lDocumentCriteria);
			List<DocumentUi> lDocumentUis = documentTransformer
					.toUi(lDocumentEntities);
			lUi.setDocumentSet(lDocumentUis);
			result.addActualite(lUi);
		}
		Long endTime = System.currentTimeMillis();
		LOG.info("Loading duration: " + (endTime - startTime) + " ms");
		return result;
	}
}
