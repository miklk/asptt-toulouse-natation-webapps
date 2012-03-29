package com.asptttoulousenatation.server.init;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
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
import com.asptttoulousenatation.core.shared.actu.ActuUi;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.userspace.admin.entity.ActuTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.ContentTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.MenuTransformer;
import com.asptttoulousenatation.shared.init.InitUserSpaceAction;
import com.asptttoulousenatation.shared.init.InitUserSpaceResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.GetAreaAction;

public class InitUserSpaceActionHandler implements
		ActionHandler<InitUserSpaceAction, InitUserSpaceResult> {

	private AreaDao areaDao = new AreaDao();
	private MenuDao menuDao = new MenuDao();
	private ContentDao contentDao = new ContentDao();

	private AreaTransformer areaTransformer = new AreaTransformer();
	private MenuTransformer menuTransformer = new MenuTransformer();
	private ContentTransformer contentTransformer = new ContentTransformer();

	private ActuDao actuDao = new ActuDao();
	private ActuTransformer actuTransformer = new ActuTransformer();

	public InitUserSpaceResult execute(InitUserSpaceAction pAction,
			ExecutionContext pContext) throws DispatchException {
		InitUserSpaceResult lInitResult = new InitUserSpaceResult();

		// Structure
		List<CriterionDao<? extends Object>> lAreaSelectionCriteria = new ArrayList<CriterionDao<? extends Object>>(
				pAction.getProfiles().size());
		//Profiles
		for (ProfileEnum lProfile : pAction.getProfiles()) {
			CriterionDao<String> lAreaSelectionCriterion = new CriterionDao<String>(
					AreaEntityFields.PROFILE, lProfile.toString(),
					Operator.EQUAL);
			lAreaSelectionCriteria.add(lAreaSelectionCriterion);
		}
		OrderDao lAreaOrder = new OrderDao(AreaEntityFields.ORDER, OrderDao.OrderOperator.ASC);
		List<AreaEntity> lAreaEntities = areaDao.find(lAreaSelectionCriteria,
				Operator.OR, lAreaOrder);
		Map<String, AreaUi> lAreaUis = new LinkedHashMap<String, AreaUi>(
				lAreaEntities.size());
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
		lAreaCriterion.setEntityField(MenuEntityFields.AREA);
		lAreaCriterion.setOperator(Operator.EQUAL);
		lCriteria.add(lAreaCriterion);

		List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lMenuCriteria.add(lContentCriterion);

		for (AreaEntity lAreaEntity : lAreaEntities) {
			// Get menu
			lAreaCriterion.setValue(lAreaEntity.getId());
			List<MenuEntity> lMenuEntities = menuDao.find(lCriteria);
			Map<String, MenuUi> lMenuUis = new LinkedHashMap<String, MenuUi>(
					lMenuEntities.size());
			for (MenuEntity lMenuEntity : lMenuEntities) {
				// Get content
				lContentCriterion.setValue(lMenuEntity.getId());
				List<ContentEntity> lContentEntities = contentDao
						.find(lMenuCriteria);
				MenuUi lMenu = menuTransformer.toUi(lMenuEntity);
				lMenu.setContentSet(contentTransformer.toUi(lContentEntities));
				lMenuUis.put(lMenu.getTitle(), lMenu);
			}
			AreaUi lArea = areaTransformer.toUi(lAreaEntity);
			lArea.setMenuSet(lMenuUis);
			lAreaUis.put(lArea.getTitle(), lArea);
			if("Structure du site".equals(lAreaEntity.getTitle())) {
				lInitResult.setAreaResult(pContext.execute(new GetAreaAction(false)));
			}
		}
		lInitResult.setArea(lAreaUis);

		// Actu
		List<ActuEntity> lActuEntities = actuDao.getAll();
		ArrayList<ActuUi> lActu = new ArrayList<ActuUi>(
				actuTransformer.toUi(lActuEntities));
		lInitResult.setActu(lActu);
		return lInitResult;
	}

	public Class<InitUserSpaceAction> getActionType() {
		return InitUserSpaceAction.class;
	}

	public void rollback(InitUserSpaceAction pArg0, InitUserSpaceResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
