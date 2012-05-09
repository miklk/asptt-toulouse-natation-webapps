package com.asptttoulousenatation.core.server.structure.menu;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuResult;
import com.asptttoulousenatation.server.userspace.admin.entity.ContentTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.MenuTransformer;

public class GetMenuActionHandler implements
		ActionHandler<GetMenuAction, GetMenuResult> {

	private MenuDao menuDao = new MenuDao();
	private ContentDao contentDao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();
	
	private MenuTransformer menuTransformer = new MenuTransformer();
	private ContentTransformer contentTransformer = new ContentTransformer();
	private DocumentTransformer documentTransformer = new DocumentTransformer();
	
	public GetMenuResult execute(GetMenuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		
		MenuEntity lMenuEntity = menuDao.get(pAction.getMenuId());
		final MenuUi lMenu = menuTransformer.toUi(lMenuEntity);
		
		//Content criteria
		List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lMenuCriteria.add(lContentCriterion);
		lContentCriterion.setValue(lMenuEntity.getId());
		List<ContentEntity> lContentEntities = contentDao.find(lMenuCriteria);
		lMenu.setContentSet(contentTransformer.toUi(lContentEntities));
		
		
		//Get documents
		List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>();
		lDocumentCriterion.setEntityField(DocumentEntityFields.MENU);
		lDocumentCriterion.setOperator(Operator.EQUAL);
		lDocumentCriteria.add(lDocumentCriterion);
		lDocumentCriterion.setValue(lMenuEntity.getId());
		List<DocumentEntity> lDocumentEntities = documentDao.find(lDocumentCriteria);
		List<DocumentUi> lDocumentUis = documentTransformer.toUi(lDocumentEntities);
		lMenu.setDocumentSet(lDocumentUis);
		
		//Retrieve sub menu
		List<MenuUi> lSubMenuUis = new ArrayList<MenuUi>(lMenuEntity.getSubMenu().size());
		for(Long lSubMenuId: lMenuEntity.getSubMenu()) {
			GetMenuResult lGetMenuResult = pContext.execute(new GetMenuAction(lSubMenuId));
			lSubMenuUis.add(lGetMenuResult.getMenu());
		}
		lMenu.setSubMenus(lSubMenuUis);
		return new GetMenuResult(lMenu);
	}

	public Class<GetMenuAction> getActionType() {
		return GetMenuAction.class;
	}

	public void rollback(GetMenuAction pAction, GetMenuResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}

}