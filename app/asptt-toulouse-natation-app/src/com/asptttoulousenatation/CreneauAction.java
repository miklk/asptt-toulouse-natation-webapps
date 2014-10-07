package com.asptttoulousenatation;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;

public class CreneauAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178809147424818945L;

	private static final Logger LOG = Logger.getLogger(CreneauAction.class
			.getName());

	private Inscription2Dao inscriptionDao = new Inscription2Dao();
	private SlotDao slotDao = new SlotDao();

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
		List<InscriptionEntity2> entities = inscriptionDao.getAll();
		for (InscriptionEntity2 entity : entities) {
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
							SlotEntity creneauEntity = slotDao.get(Long
									.valueOf(creneauId));
							if (creneauEntity != null) {
								if(creneauEntity.getPlaceRestante() != null) {
									creneauEntity.setPlaceRestante(creneauEntity.getPlaceRestante() - 1);
									creneauMisAJour = 0;
								}
										
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