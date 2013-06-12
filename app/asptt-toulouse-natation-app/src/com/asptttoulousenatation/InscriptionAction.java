package com.asptttoulousenatation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.google.gson.Gson;

public class InscriptionAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;
	

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		String action = pReq.getParameter("action");
		if("loadGroupes".equals(action)) {
			loadGroupes(pReq, pResp);
		}
	}
	
	protected void loadGroupes(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException { 
		GroupDao lGroupDao = new GroupDao();
		List<GroupEntity> lEntities = lGroupDao.getAll();
		Gson gson = new Gson();
		String json = gson.toJson(lEntities);
		System.out.println(json);
		pResp.getWriter().write(json);
	}
}