package com.asptttoulousenatation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.ContentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.DocumentEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.shared.document.DocumentUi;
import com.asptttoulousenatation.server.userspace.admin.entity.DocumentTransformer;
import com.google.gson.Gson;

public class GetContentAction extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4924117107849584365L;
	private ContentDao contentDao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();
	private DocumentTransformer documentTransformer = new DocumentTransformer();

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		Long menuId = Long.parseLong(pReq.getParameter("menuId"));
		List<CriterionDao<? extends Object>> lContentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lContentCriterion = new CriterionDao<Long>();
		lContentCriterion.setEntityField(ContentEntityFields.MENU);
		lContentCriterion.setOperator(Operator.EQUAL);
		lContentCriterion.setValue(menuId);
		lContentCriteria.add(lContentCriterion);
		List<ContentEntity> lContentEntities = contentDao.find(lContentCriteria);
		
		final LoadContentResult2 lResult = new LoadContentResult2();
		if(!lContentEntities.isEmpty()) {
			lResult.setContenu(new String(lContentEntities.get(0).getData().getBytes()));
		}
		
		//Get documents
		List<CriterionDao<? extends Object>> lDocumentCriteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		CriterionDao<Long> lDocumentCriterion = new CriterionDao<Long>(DocumentEntityFields.MENU, menuId, Operator.EQUAL);
		lDocumentCriteria.add(lDocumentCriterion);
		List<DocumentEntity> lDocumentEntities = documentDao.find(lDocumentCriteria);
		List<DocumentUi> lDocumentUis = documentTransformer.toUi(lDocumentEntities);
		lResult.setDocuments(lDocumentUis);
		Gson gson = new Gson();
		String json = gson.toJson(lResult);
		System.out.println(json);
		pResp.getWriter().write(json);
	}

}
