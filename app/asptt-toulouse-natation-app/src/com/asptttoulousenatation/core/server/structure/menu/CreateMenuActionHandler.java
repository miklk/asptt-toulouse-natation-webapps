package com.asptttoulousenatation.core.server.structure.menu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.structure.menu.CreateMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.CreateMenuResult;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuResult;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.util.HTMLUtils;
import com.google.appengine.api.datastore.Blob;

public class CreateMenuActionHandler implements
		ActionHandler<CreateMenuAction, CreateMenuResult> {

	private MenuDao dao = new MenuDao();
	private ContentDao contentDao = new ContentDao();
	private AreaDao areaDao = new AreaDao();
	
	private AreaTransformer areaTransformer = new AreaTransformer();
	
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
			MenuEntity lMenu = new MenuEntity(MenuItems.VIDE.toString(), pAction.getTitle(), pAction.getArea(), false, true, pAction.getOrder(), null, pAction.getParent());
			MenuEntity lMenuEntity = dao.save(lMenu);
			ContentEntity lContentEntity = new ContentEntity(pAction.getSummary(),
					new Blob((HTMLUtils.escapeHTML(pAction.getContent())).getBytes()),
					ContentDataKindEnum.TEXT.toString(), lMenuEntity.getId());
			contentDao.save(lContentEntity);
			
			//Update parent
			if(pAction.getParent() != null) {
				//Retrieve parent menu
				MenuEntity lMenuParent = dao.get(pAction.getParent());
				lMenuParent.getSubMenu().add(lMenuEntity.getId());
				dao.save(lMenuParent);
			}
			
			//Retrieve area
			AreaEntity lAreaEntity = areaDao.get(pAction.getArea());
			//Get menu
			List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>(MenuEntityFields.AREA, lAreaEntity.getId(), Operator.EQUAL);
			lMenuCriteria.add(lAreaCriterion);
			List<MenuEntity> lMenuEntities = dao.find(lMenuCriteria);
			Map<String, MenuUi> lMenuUis = new LinkedHashMap<String, MenuUi>(lMenuEntities.size());
			for(MenuEntity lMenuEntity2: lMenuEntities) {
				GetMenuResult lGetMenuActionResult = pContext.execute(new GetMenuAction(lMenuEntity2.getId()));
				lMenuUis.put(lGetMenuActionResult.getMenu().getTitle(), lGetMenuActionResult.getMenu());
			}
			AreaUi lArea = areaTransformer.toUi(lAreaEntity);
			lArea.setMenuSet(lMenuUis);
			result.setArea(lArea);
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