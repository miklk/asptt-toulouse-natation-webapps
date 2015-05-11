package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.server.userspace.admin.entity.GroupTransformer;

public class AuthenticateAdherentResource {

	private Inscription2Dao dao = new Inscription2Dao();
	private GroupDao groupDao = new GroupDao();
	private GroupTransformer groupTransformer = new GroupTransformer();
	private String email;
	private String mdp;

	public AuthenticateAdherentResource() {
		super();
	}

	public AuthenticateAdherentResource(String pEmail, String pMdp) {
		super();
		email = pEmail;
		mdp = pMdp;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public InscriptionDossiersUi authenticate() {
		InscriptionDossiersUi dossiers = new InscriptionDossiersUi();
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<String>(InscriptionEntityFields.EMAIL,
				email, Operator.EQUAL));
		criteria.add(new CriterionDao<String>(
				InscriptionEntityFields.MOTDEPASSE, mdp, Operator.EQUAL));
		criteria.add(new CriterionDao<Object>(InscriptionEntityFields.PRINCIPAL,
				null, Operator.NULL));
		List<InscriptionEntity2> entities = dao.find(criteria);
		if (entities == null || entities.isEmpty()) {
			dossiers.setInconnu(true);
		} else {
			dossiers.setInconnu(false);
			InscriptionEntity2 adherent = entities.get(0);
			dossiers.setPrincipal(new InscriptionDossierUi(adherent));
			dossiers.addDossier(new InscriptionDossierUi(adherent));

			criteria = new ArrayList<CriterionDao<? extends Object>>(1);
			criteria.add(new CriterionDao<Long>(
					InscriptionEntityFields.PRINCIPAL, adherent.getId(),
					Operator.EQUAL));
			entities = dao.find(criteria);

			dossiers.addDossier(entities);

			// Build UI dossier
			// Set date naissance
			for (InscriptionDossierUi dossier : dossiers.getDossiers()) {
				adherent = dossier.getDossier();
				
				// Get nouveau groupe et vérifie si on peut changer
				if (adherent.getNouveauGroupe() != null) {
					try {
					GroupEntity group = groupDao.get(adherent
							.getNouveauGroupe());
					dossier.setChoix(group.getInscription());
					dossier.setGroupe(groupTransformer.toUi(group));
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					dossier.setChoix(true);
				}
				//Test sélection de créneau
				dossier.setCreneauSelected(StringUtils.isNotBlank(adherent.getCreneaux()));
			}
		}
		return dossiers;
	}
}
