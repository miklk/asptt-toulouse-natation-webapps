package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;

@Path("/creneaux")
@Produces("application/json")
public class CreneauService {

	private static final Logger LOG = Logger.getLogger(CreneauService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private SlotDao dao = new SlotDao();
	
	private static Map<String, Integer> JOURS;
	
	static {
		JOURS = new HashMap<String, Integer>();
		JOURS.put("Lundi", 0);
		JOURS.put("Mardi", 1);
		JOURS.put("Mercredi", 2);
		JOURS.put("Jeudi", 3);
		JOURS.put("Vendredi", 4);
		JOURS.put("Samedi", 5);
		JOURS.put("Dimanche", 6);
	}

	@GET
	@Path("{groupe}")
	public CreneauxBean getCreneaux(@PathParam("groupe") Long groupe) {
		// Retrieve slots
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe,
				Operator.EQUAL));
		List<SlotEntity> lEntities = dao.find(criteria);
		List<SlotUi> lUis = new SlotTransformer().toUi(lEntities);
		Collections.sort(lUis, new Comparator<SlotUi>() {

			public int compare(SlotUi pO1, SlotUi pO2) {
				Integer jour1 = JOURS.get(pO1.getDayOfWeek());
				Integer jour2 = JOURS.get(pO2.getDayOfWeek());
				return jour1.compareTo(jour2);
			}
		});

		CreneauxBean result = new CreneauxBean();
		result.setCreneaux(lUis);
		return result;
	}
}