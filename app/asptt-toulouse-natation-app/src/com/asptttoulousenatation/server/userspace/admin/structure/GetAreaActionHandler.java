package com.asptttoulousenatation.server.userspace.admin.structure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.ContentTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.MenuTransformer;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.GetAreaAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.GetAreaResult;

public class GetAreaActionHandler implements
		ActionHandler<GetAreaAction, GetAreaResult> {
	
	private AreaDao areaDao = new AreaDao();
	private MenuDao menuDao = new MenuDao();
	private ContentDao contentDao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();
	
	private AreaTransformer areaTransformer = new AreaTransformer();
	private MenuTransformer menuTransformer = new MenuTransformer();
	private ContentTransformer contentTransformer = new ContentTransformer();
	private DocumentTransformer documentTransformer = new DocumentTransformer();
	
	public GetAreaResult execute(GetAreaAction pAction, ExecutionContext pContext)
			throws DispatchException {
//		List<AreaEntity> lAreaEntities = areaDao.getAll();
		List<CriterionDao<? extends Object>> lAreaSelectionCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<String> lAreaSelectionCriterion = new CriterionDao<String>(AreaEntityFields.PROFILE, ProfileEnum.PUBLIC.toString(), Operator.EQUAL);
		lAreaSelectionCriteria.add(lAreaSelectionCriterion);
		//Order
		OrderDao lOrderDao = new OrderDao(AreaEntityFields.ORDER, OrderDao.OrderOperator.ASC);
		List<AreaEntity> lAreaEntities = areaDao.find(lAreaSelectionCriteria, lOrderDao);
		ArrayList<AreaUi> lAreaUis = new ArrayList<AreaUi>(lAreaEntities.size());
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
		lAreaCriterion.setEntityField(MenuEntityFields.AREA);
		lAreaCriterion.setOperator(Operator.EQUAL);
		
		if(pAction.isOnlyDisplay()) {
			CriterionDao<Boolean> lMenuDisplayCriterion = new CriterionDao<Boolean>(MenuEntityFields.DISPLAY, Boolean.TRUE, Operator.EQUAL);
			lCriteria.add(lMenuDisplayCriterion);
		}
		lCriteria.add(lAreaCriterion);
		
		List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lMenuCriteria.add(lContentCriterion);
		
		//Get documents
		List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>();
		lDocumentCriterion.setEntityField(DocumentEntityFields.MENU);
		lDocumentCriterion.setOperator(Operator.EQUAL);
		lDocumentCriteria.add(lDocumentCriterion);
		
		for(AreaEntity lAreaEntity: lAreaEntities) {
			//Get menu
			lAreaCriterion.setValue(lAreaEntity.getId());
			List<MenuEntity> lMenuEntities = menuDao.find(lCriteria);
			Map<String, MenuUi> lMenuUis = new LinkedHashMap<String, MenuUi>(lMenuEntities.size());
			for(MenuEntity lMenuEntity: lMenuEntities) {
				//Get content
				lContentCriterion.setValue(lMenuEntity.getId().getId());
				List<ContentEntity> lContentEntities = contentDao.find(lMenuCriteria);
				MenuUi lMenu = menuTransformer.toUi(lMenuEntity);
				lDocumentCriterion.setValue(lMenuEntity.getId().getId());
				List<DocumentEntity> lDocumentEntities = documentDao.find(lDocumentCriteria);
				List<DocumentUi> lDocumentUis = documentTransformer.toUi(lDocumentEntities);
				lMenu.setContentSet(contentTransformer.toUi(lContentEntities));
				lMenu.setDocumentSet(lDocumentUis);
				lMenuUis.put(lMenu.getTitle(), lMenu);
			}
			AreaUi lArea = areaTransformer.toUi(lAreaEntity);
			lArea.setMenuSet(lMenuUis);
			lAreaUis.add(lArea);
		}
		return new GetAreaResult(lAreaUis);
	}

	public Class<GetAreaAction> getActionType() {
		return GetAreaAction.class;
	}

	public void rollback(GetAreaAction pAction, GetAreaResult pResult,
			ExecutionContext pContext) throws DispatchException {

	}
}