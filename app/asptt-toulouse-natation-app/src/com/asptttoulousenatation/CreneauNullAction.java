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

import com.asptttoulousenatation.client.util.CollectionUtils;
import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity;
import com.asptttoulousenatation.core.server.dao.inscription.InscriptionDao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

public class CreneauNullAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(CreneauNullAction.class
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
		int creneauMisAJour = 0;
		StrBuilder builder = new StrBuilder();
		List<InscriptionEntity> entities = inscriptionDao.getAll();
		for (InscriptionEntity entity : entities) {
			try {
			if (StringUtils.isNotBlank(entity.getCreneaux())) {
				String[] creneaux = entity.getCreneaux().split(";");
				for (String creneau : creneaux) {
					if (StringUtils.isNotBlank(creneau)) {
						final String creneauId;
						if (StringUtils.contains(creneau, "_")) {
							creneauId = creneau.split("_")[1];
						} else {
							creneauId = creneau;
						}
						if (StringUtils.isNumeric(creneauId)) {
							List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
									1);
							criteria.add(new CriterionDao<Long>(
									SlotEntityFields.ID, Long
									.valueOf(creneauId),
									Operator.EQUAL));
							List<SlotEntity> slotEntities = slotDao.find(criteria);
							if (CollectionUtils.isEmpty(slotEntities)) {
								creneauMisAJour++;
								GroupEntity group = groupDao.get(entity.getNouveauGroupe());
										builder.append(entity.getNom()).append(" ").append(entity.getPrenom()).append(" ").append(entity.getTelephone()).append(" ").append(entity.getEmail()).append(" ").append(group.getTitle()).append(" creneau manquant est ").append(creneauId).appendNewLine();
							}
						}
					}
				}
			}
			} catch(Exception e) {
				builder.append("Erreur avec l'adhérent " + entity.getId() + " ("+e.getMessage()+")");
			}
		}
		pResp.getOutputStream().print(creneauMisAJour + " ," + builder.toString());
	}
}