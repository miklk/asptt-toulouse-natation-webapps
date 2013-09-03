package com.asptttoulousenatation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class InscritsAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(InscritsAction.class
			.getName());

	private InscriptionDao inscriptionDao = new InscriptionDao();
	private SlotDao slotDao = new SlotDao();
	private GroupDao groupDao = new GroupDao();

	@Override
	protected void doGet(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		doPost(pReq, pResp);
	}

	@Override
	protected void doPost(HttpServletRequest pReq, HttpServletResponse pResp)
			throws ServletException, IOException {
		StrBuilder builder = new StrBuilder();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Boolean>(
				InscriptionEntityFields.SAISIE, Boolean.TRUE,
				Operator.EQUAL));
		List<InscriptionEntity> entities = inscriptionDao.find(criteria);
		for (InscriptionEntity entity : entities) {
			try {
			if (StringUtils.isNotBlank(entity.getCreneaux())) {
				String[] creneaux = entity.getCreneaux().split(";");
				GroupEntity group = groupDao.get(entity.getNouveauGroupe());
				builder.append(entity.getNom()).append(" ").append(entity.getPrenom()).append(" ").append(group.getTitle()).append(" ").append(creneaux.length).append(" creneaux").append("\n").appendNewLine();
				}
			} catch(Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId() + " ("+e.getMessage()+")");
			}
		}
		pResp.getOutputStream().print(entities.size() + "\n" + builder.toString());
	}
}