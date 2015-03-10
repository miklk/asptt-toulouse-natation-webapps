package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.field.InscriptionEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.inscription.InscriptionEntity2;
import com.asptttoulousenatation.core.server.dao.inscription.Inscription2Dao;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/adherentsStat")
@Produces("application/json")
public class AdherentStatService {

	private static final Logger LOG = Logger
			.getLogger(AdherentStatService.class.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private Inscription2Dao dao = new Inscription2Dao();
	private SlotDao slotDao = new SlotDao();

	@GET
	public GetAdherentsStatResult getAdherents(
			@QueryParam("groupes") Set<Long> groupes,
			@QueryParam("creneaux") final Set<Long> creneaux,
			@QueryParam("piscines") final Set<String> piscines) {
		GetAdherentsStatResult result = new GetAdherentsStatResult();
		List<InscriptionEntity2> adherents = new ArrayList<InscriptionEntity2>(
				0);
		// groupes
		if (CollectionUtils.isNotEmpty(groupes)) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					groupes.size());
			for (Long groupe : groupes) {
				criteria.add(new CriterionDao<Long>(
						InscriptionEntityFields.NOUVEAUGROUPE, groupe,
						Operator.EQUAL));
			}
			adherents.addAll(dao.find(criteria, Operator.OR, null));
		}
		if (CollectionUtils.isEmpty(adherents)) {
			adherents = dao.getAll();
		}
		if (CollectionUtils.isNotEmpty(creneaux)
				|| CollectionUtils.isNotEmpty(piscines)) {
			final Set<Long> piscinesAsLong = new HashSet<>();
			for (String piscine : piscines) {
				piscinesAsLong.addAll(AdherentBeanTransformer.getInstance()
						.getCreneauIds(piscine));
			}
			adherents = new ArrayList<>(CollectionUtils.select(adherents,
					new Predicate() {
						@Override
						public boolean evaluate(Object pArg0) {
							InscriptionEntity2 adherent = (InscriptionEntity2) pArg0;
							Set<Long> adherentCreneaux = AdherentBeanTransformer
									.getInstance().getCreneauIds(
											adherent.getCreneaux());
							boolean found = false;
							Iterator<Long> it = adherentCreneaux.iterator();
							while (it.hasNext() && !found) {
								Long adherentCreneau = it.next();
								if (creneaux.contains(adherentCreneau)
										|| piscinesAsLong
												.contains(adherentCreneau)) {
									found = true;
								}
							}
							return found;
						}
					}));
		}
		result.setAdherents(adherents);
		return result;
	}
}