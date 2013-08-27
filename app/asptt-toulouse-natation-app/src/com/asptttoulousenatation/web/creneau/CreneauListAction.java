package com.asptttoulousenatation.web.creneau;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.google.gson.Gson;

public class CreneauListAction extends HttpServlet {

	private static final long serialVersionUID = -4912389865312123661L;
	private GroupDao groupDao = new GroupDao();
	private SlotDao slotDao = new SlotDao();

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		CreneauListForm form = (CreneauListForm) pReq.getSession()
				.getAttribute("creneauListForm");
		if (form == null) {
			form = new CreneauListForm();
		}

		try {
			BeanUtils.populate(form, pReq.getParameterMap());
			if ("load".equals(form.getAction())) {
				load(form, pReq, pResp);
			} else if ("search".equals(form.getAction())) {
				search(form, pReq, pResp);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pReq.getSession().setAttribute("creneauListForm", form);
	}

	private void load(CreneauListForm form, HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		List<String> piscines = slotDao.getPiscines();
		Gson gson = new Gson();
		String json = gson.toJson(piscines);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	private void search(CreneauListForm form, HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		if (StringUtils.isNotBlank(form.getPiscine())) {
			// Load creneaux
			ArrayList<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(
					SlotEntityFields.SWIMMINGPOOL, form.getPiscine(),
					Operator.EQUAL));
			List<SlotEntity> entities = slotDao.find(criteria);
			Map<String, CreneauListResultBean> resultsMap = new HashMap<String, CreneauListResultBean>();
			for (SlotEntity entity : entities) {
				String cle = entity.getDayOfWeek();
				final CreneauListResultBean creneau;
				if (resultsMap.containsKey(cle)) {
					creneau = resultsMap.get(cle);
				} else {
					creneau = new CreneauListResultBean();
					creneau.setJour(entity.getDayOfWeek());
					resultsMap.put(cle, creneau);
				}
				
				
				// Load group
				GroupEntity group = groupDao.get(entity.getGroup());
				int effectif = 0;
				if(entity.getPlaceDisponible() != null) {
					effectif = entity.getPlaceDisponible()
							- entity.getPlaceRestante();
				}
				
				creneau.addCreneau(entity.getBegin(), group.getTitle(), effectif);
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(resultsMap.values());
			pResp.setContentType("application/json;charset=UTF-8");
			pResp.getWriter().write(json);
		}
	}
}