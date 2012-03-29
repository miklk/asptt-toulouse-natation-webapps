package com.asptttoulousenatation.server.userspace.admin.structure;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuResult;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.GetAreaAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.GetAreaResult;

public class GetAreaActionHandler implements
		ActionHandler<GetAreaAction, GetAreaResult> {

	private AreaDao areaDao = new AreaDao();
	private MenuDao menuDao = new MenuDao();

	private AreaTransformer areaTransformer = new AreaTransformer();

	public GetAreaResult execute(GetAreaAction pAction,
			ExecutionContext pContext) throws DispatchException {
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
		ArrayList<AreaUi> lAreaUis = new ArrayList<AreaUi>(lAreaEntities.size());
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				3);
		CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
		lAreaCriterion.setEntityField(MenuEntityFields.AREA);
		lAreaCriterion.setOperator(Operator.EQUAL);
		lCriteria.add(lAreaCriterion);

		if (pAction.isOnlyDisplay()) {
			CriterionDao<Boolean> lMenuDisplayCriterion = new CriterionDao<Boolean>(
					MenuEntityFields.DISPLAY, Boolean.TRUE, Operator.EQUAL);
			lCriteria.add(lMenuDisplayCriterion);
		}

		for (AreaEntity lAreaEntity : lAreaEntities) {
			// Get menu
			lAreaCriterion.setValue(lAreaEntity.getId());
			List<MenuEntity> lMenuEntities = menuDao.find(lCriteria);
			Map<String, MenuUi> lMenuUis = new LinkedHashMap<String, MenuUi>(
					lMenuEntities.size());
			for (MenuEntity lMenuEntity : lMenuEntities) {
				// Only root menu
				if (lMenuEntity.getParent() == null) {
					GetMenuResult lGetMenuActionResult = pContext
							.execute(new GetMenuAction(lMenuEntity.getId()));
					MenuUi lMenu = lGetMenuActionResult.getMenu();
					lMenuUis.put(lMenu.getTitle(), lMenu);
				}
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