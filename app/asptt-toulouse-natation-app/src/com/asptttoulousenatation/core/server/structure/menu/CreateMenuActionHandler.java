package com.asptttoulousenatation.core.server.structure.menu;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.structure.menu.CreateMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.CreateMenuResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.util.HTMLUtils;
import com.google.appengine.api.datastore.Blob;

public class CreateMenuActionHandler implements
		ActionHandler<CreateMenuAction, CreateMenuResult> {

	private MenuDao dao = new MenuDao();
	private ContentDao contentDao = new ContentDao();
	
	public CreateMenuResult execute(CreateMenuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		final CreateMenuResult result = new CreateMenuResult();
		//Test existence
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(2);
		CriterionDao<String> lCriterion = new CriterionDao<String>(MenuEntityFields.TITLE, pAction.getTitle(), Operator.EQUAL);
		lCriteria.add(lCriterion);
		CriterionDao<Long> lCriterionArea = new CriterionDao<Long>(MenuEntityFields.AREA, pAction.getArea(), Operator.EQUAL);
		lCriteria.add(lCriterionArea);
		List<MenuEntity> existenceTest = dao.find(lCriteria);
		if(CollectionUtils.isEmpty(existenceTest)) {//doesn't exist
			//Creation
			MenuEntity lMenu = new MenuEntity(MenuItems.VIDE.toString(), pAction.getTitle(), pAction.getArea(), false, true, pAction.getOrder());
			MenuEntity lMenuEntity = dao.save(lMenu);
			ContentEntity lContentEntity = new ContentEntity(pAction.getSummary(),
					new Blob((HTMLUtils.escapeHTML(pAction.getContent())).getBytes()),
					ContentDataKindEnum.TEXT.toString(), lMenuEntity.getId()
							.getId());
			contentDao.save(lContentEntity);
		} else {
			result.setExists(true);
		}
		return result;
	}

	public Class<CreateMenuAction> getActionType() {
		return CreateMenuAction.class;
	}

	public void rollback(CreateMenuAction pAction, CreateMenuResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}