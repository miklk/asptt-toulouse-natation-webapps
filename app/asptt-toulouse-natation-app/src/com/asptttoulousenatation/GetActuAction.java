package com.asptttoulousenatation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.google.gson.Gson;

public class GetActuAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;
	

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		System.out.println("tiens on m'appelle");
		ActuDao lActuDao = new ActuDao();
		List<ActuEntity> lEntities = lActuDao.getAll();
		Gson gson = new Gson();
		String json = gson.toJson(lEntities);
		System.out.println(json);
		pResp.getWriter().write(json);
			
			//ActuUi lUi = transformer.toUi(entity);
//			// Get documents
//			List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
//					1);
//			CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>();
//			lDocumentCriterion.setEntityField(DocumentEntityFields.MENU);
//			lDocumentCriterion.setOperator(Operator.EQUAL);
//			lDocumentCriteria.add(lDocumentCriterion);
//			lDocumentCriterion.setValue(entity.getId());
//			List<DocumentEntity> lDocumentEntities = documentDao
//					.find(lDocumentCriteria);
//			List<DocumentUi> lDocumentUis = documentTransformer
//					.toUi(lDocumentEntities);
//			lUi.setDocumentSet(lDocumentUis);
	}

	
}
