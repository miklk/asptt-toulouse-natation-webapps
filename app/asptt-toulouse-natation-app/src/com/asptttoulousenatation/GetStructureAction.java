package com.asptttoulousenatation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.AreaEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.MenuEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.AreaEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.search.OrderDao;
import com.asptttoulousenatation.core.server.dao.structure.AreaDao;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.core.shared.user.ProfileEnum;
import com.asptttoulousenatation.server.userspace.admin.entity.AreaTransformer;
import com.asptttoulousenatation.server.userspace.admin.entity.MenuTransformer;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gson.Gson;

public class GetStructureAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
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
		
		AreaDao areaDao = new AreaDao();
		List<AreaEntity> lAreaEntities = areaDao.find(lAreaSelectionCriteria,
				lOrderDao);
		List<AreaUi> lAreaUis = new ArrayList<AreaUi>(
				lAreaEntities.size());
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lAreaCriterion = new CriterionDao<Long>();
		lAreaCriterion.setEntityField(MenuEntityFields.AREA);
		lAreaCriterion.setOperator(Operator.EQUAL);
		lCriteria.add(lAreaCriterion);
		OrderDao lMenuOrder = new OrderDao(MenuEntityFields.ORDER,
				OrderDao.OrderOperator.ASC);

		List<CriterionDao<? extends Object>> lMenuCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lMenuCriteria.add(lContentCriterion);
		MenuDao menuDao = new MenuDao();
		MenuTransformer menuTransformer = new MenuTransformer();
		AreaTransformer areaTransformer = new AreaTransformer();
		for (AreaEntity lAreaEntity : lAreaEntities) {
			// Get menu
			lAreaCriterion.setValue(lAreaEntity.getId());
			List<MenuEntity> lMenuEntities = menuDao
					.find(lCriteria, lMenuOrder);
			Map<String, MenuUi> lMenuUis = new LinkedHashMap<String, MenuUi>(
					lMenuEntities.size());
			for (MenuEntity lMenuEntity : lMenuEntities) {
				if (lMenuEntity.getParent() == null) {
					// Retrieve sub menu
					List<MenuUi> lSubMenuUis = new ArrayList<MenuUi>(
							lMenuEntity.getSubMenu().size());
					for (Long lSubMenuId : lMenuEntity.getSubMenu()) {
						MenuEntity lSubMenu = menuDao.get(lSubMenuId);
						lSubMenuUis.add(menuTransformer.toUi(lSubMenu));
					}
					MenuUi lMenu = menuTransformer.toUi(lMenuEntity);
					Collections.sort(lSubMenuUis, new Comparator<MenuUi>() {

						public int compare(MenuUi pO1, MenuUi pO2) {
							final int result;
							if (pO1.getOrder() == pO2.getOrder()) {
								result = 0;
							} else if (pO1.getOrder() > pO2.getOrder()) {
								result = 1;
							} else {
								result = -1;
							}
							return result;
						}

					});
					lMenu.setSubMenus(lSubMenuUis);
					lMenuUis.put(lMenu.getTitle(), lMenu);
				}
			}
			AreaUi lArea = areaTransformer.toUi(lAreaEntity);
			lArea.setMenuSet(lMenuUis);
			lAreaUis.add(lArea);
		}
		Gson gson = new Gson();
		String json = gson.toJson(lAreaUis);
		System.out.println(json);
		pResp.getWriter().write(json);
	}

}
