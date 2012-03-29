package com.asptttoulousenatation.core.server.structure.menu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.AbstractDeleteActionHandler;
import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
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
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuResult;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuResult;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;

public class DeleteMenuActionHandler extends AbstractDeleteActionHandler<MenuEntity, DeleteMenuAction, DeleteMenuResult> {

	private ContentDao contentDao = new ContentDao();
	private AreaDao areaDao = new AreaDao();
	private AreaTransformer areaTransformer = new AreaTransformer();
	
	public Class<DeleteMenuAction> getActionType() {
		return DeleteMenuAction.class;
	}
	
	@Override
	protected DaoBase<MenuEntity> createDao() {
		return new MenuDao();
	}

	@Override
	protected DeleteMenuResult getResult() {
		return new DeleteMenuResult();
	}

	@Override
	protected void doBeforeDelete(MenuEntity pEntity, ExecutionContext pContext) throws DispatchException {
		List<CriterionDao<? extends Object>> lContentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lContentCriterion.setValue(pEntity.getId());
		lContentCriteria.add(lContentCriterion);
		List<ContentEntity> lContentEntities = contentDao.find(lContentCriteria);
		for(ContentEntity lContent: lContentEntities) {
			contentDao.delete(lContent);
		}
		
		//Update parent
		if(pEntity.getParent() != null) {
			DaoBase<MenuEntity> lMenuDao = createDao();
			MenuEntity lMenuParent = lMenuDao.get(pEntity.getParent());
			lMenuParent.getSubMenu().remove(pEntity.getId());
			lMenuDao.save(lMenuParent);
		}
	}

	@Override
	protected void fillResult(DeleteMenuAction pAction, DeleteMenuResult pResult,
			ExecutionContext pContext) throws DispatchException {
		//Retrieve area
		AreaEntity lAreaEntity = areaDao.get(pAction.getId());
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
		pResult.setArea(lArea);
	}
}