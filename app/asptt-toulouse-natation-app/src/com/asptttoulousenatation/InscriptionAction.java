package com.asptttoulousenatation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;
import com.google.gson.Gson;

public class InscriptionAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;
	
	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		doPost(pReq, pResp);
	}

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		String action = pReq.getParameter("action");
		if ("loadGroupes".equals(action)) {
			loadGroupes(pReq, pResp);
		} else if ("loadCreneaux".equals(action)) {
			loadCreneaux(pReq, pResp);
		} else if("inscription".equals(action)) {
			inscription(pReq, pResp);
		} else if("inscriptionSub".equals(action)) {
				inscriptionSub(pReq, pResp);
		} else if("findEmail".equals(action)) {
			findEmail(pReq, pResp);
		} else if("loadOldGroupe".equals(action)) {
			loadOldGroupe(pReq, pResp);
		}
	}

	protected void loadGroupes(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long groupId = Long.valueOf(pReq.getParameter("groupeId"));
		GroupDao lGroupDao = new GroupDao();
		GroupEntity lEntity = lGroupDao.get(groupId);
		Gson gson = new Gson();
		String json = gson.toJson(lEntity);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}
	
	protected void loadOldGroupe(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long groupId = Long.valueOf(pReq.getParameter("groupeId"));
		GroupDao lGroupDao = new GroupDao();
		GroupEntity lEntity = lGroupDao.get(groupId);
		Gson gson = new Gson();
		String json = gson.toJson(lEntity);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}

	protected void loadCreneaux(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long groupId = Long.valueOf(pReq.getParameter("selectedGroupe"));
		// Retrieve slots
		List<CriterionDao<? extends Object>> lSlotCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lSlotCriterion = new CriterionDao<Long>(
				SlotEntityFields.GROUP, groupId, Operator.EQUAL);
		lSlotCriteria.add(lSlotCriterion);
		SlotDao slotDao = new SlotDao();
		List<SlotEntity> lEntities = slotDao.find(lSlotCriteria);
		List<SlotUi> lUis = new SlotTransformer().toUi(lEntities);
		Gson gson = new Gson();
		String json = gson.toJson(lUis);
		System.out.println(json);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}
	
	protected void inscription(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		System.out.println("inscription");
		pReq.getSession().removeAttribute("inscriptionId");
		StringBuilder creneau = new StringBuilder();
		Enumeration params = pReq.getParameterNames();
		while(params.hasMoreElements()) {
			String param = (String) params.nextElement();
			if(param.contains("creneau")) {
				String paramValue = pReq.getParameter(param);
				if(BooleanUtils.toBoolean(paramValue)) {
					creneau.append(param.replace("creneau", "")).append(";");
				}
			}
		}
		
		List<InscriptionEntity> adherents = (List<InscriptionEntity>) pReq.getSession().getAttribute("data");
		InscriptionEntity entity = adherents.get(0);
		entity.setCreneaux(creneau.toString());
		try {
			BeanUtils.populate(entity, pReq.getParameterMap());
			InscriptionDao dao = new InscriptionDao();
			Long inscriptionId = dao.save(entity).getId();
			System.out.println("ID = " + inscriptionId);
			pReq.getSession().setAttribute("inscriptionId", inscriptionId);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pResp.getWriter().write(ReflectionToStringBuilder.toString(entity));
	}
	
	protected void inscriptionSub(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		Long principalId = (Long) pReq.getSession().getAttribute("inscriptionId");
		System.out.println("GET ID = " + principalId);
		System.out.println("inscription sub");
		StringBuilder creneau = new StringBuilder();
		String adherentIndexStr = StringUtils.EMPTY;
		Enumeration params = pReq.getParameterNames();
		while(params.hasMoreElements()) {
			String param = (String) params.nextElement();
			if(param.contains("creneau")) {
				String paramValue = pReq.getParameter(param);
				if(BooleanUtils.toBoolean(paramValue)) {
					creneau.append(param.replace("creneau", "")).append(";");
				}
			} else if(param.contains("adherentIndex")) {
				adherentIndexStr = pReq.getParameter(param);
			}
		}
		
		final InscriptionEntity entity;
		if(StringUtils.isNotBlank(adherentIndexStr)) {
			Integer adherentIndex = Integer.valueOf(adherentIndexStr) - 1;
			List<InscriptionEntity> adherents = (List<InscriptionEntity>) pReq.getSession().getAttribute("data");
			if(adherentIndex < adherents.size()) {
				entity = adherents.get(adherentIndex);
			} else {
				entity = new InscriptionEntity();	
			}
		} else {
			entity = new InscriptionEntity();
		}
		entity.setCreneaux(creneau.toString());
		entity.setPrincipal(principalId);
		try {
			BeanUtils.populate(entity, pReq.getParameterMap());
			InscriptionDao dao = new InscriptionDao();
			dao.save(entity);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pResp.getWriter().write(ReflectionToStringBuilder.toString(entity));
	}
	
	protected void findEmail(HttpServletRequest pReq,
			HttpServletResponse pResp) throws ServletException, IOException {
		String email = pReq.getParameter("find_email");
		InscriptionDao inscriptionDao = new InscriptionDao();
		List<CriterionDao<? extends Object>> lCriteria = new ArrayList<CriterionDao<? extends Object>>(2);
		CriterionDao<String> lEmailAddressCriterion = new CriterionDao<String>(InscriptionEntityFields.EMAIL, email, Operator.EQUAL);
		lCriteria.add(lEmailAddressCriterion);
		CriterionDao<Long> lPrincipalCriterion = new CriterionDao<Long>(InscriptionEntityFields.PRINCIPAL, null, Operator.NULL);
		lCriteria.add(lPrincipalCriterion);
		List<InscriptionEntity> adherents = inscriptionDao.find(lCriteria);
		InscriptionEntity adherent = adherents.get(0);
		List<CriterionDao<? extends Object>> lPrincipalCriteria = new ArrayList<CriterionDao<? extends Object>>(1);
		CriterionDao<Long> lAdherentsCriterion = new CriterionDao<Long>(InscriptionEntityFields.PRINCIPAL, adherent.getId(), Operator.EQUAL);
		lPrincipalCriteria.add(lAdherentsCriterion);
		adherents.addAll(inscriptionDao.find(lPrincipalCriteria));
		
		pReq.getSession().setAttribute("data", adherents);
		Gson gson = new Gson();
		String json = gson.toJson(adherents);
		System.out.println(json);
		pResp.setContentType("application/json;charset=UTF-8");
		pResp.getWriter().write(json);
	}
}