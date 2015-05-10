package com.asptttoulousenatation.core.adherent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

@Path("/slots")
@Produces("application/json")
public class LoadingSlotService {

	private static final Logger LOG = Logger.getLogger(LoadingSlotService.class
			.getName());

	@Context
	private UriInfo uriInfo;
	@Context
	private Request request;

	private SlotDao dao = new SlotDao();

	@GET
	@Path("{groupe}")
	public LoadingSlotsUi getSlots(@PathParam("groupe") Long groupe, @QueryParam("creneaux") String creneaux) {
		// Retrieve slots
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe,
				Operator.EQUAL));
		List<SlotEntity> lEntities = dao.find(criteria);
		List<SlotUi> lUis = new SlotTransformer().toUi(lEntities, creneaux);
		Collections.sort(lUis, new Comparator<SlotUi>() {

			public int compare(SlotUi pO1, SlotUi pO2) {
				return pO1.getDayOfWeek().compareTo(pO2.getDayOfWeek());
			}
		});

		LoadingSlotsUi result = new LoadingSlotsUi();
		LinkedHashMap<String, Map<String, List<SlotUi>>> groupBy = new LinkedHashMap<String, Map<String, List<SlotUi>>>();
		boolean hasSeconde = false;
		for (SlotUi ui : lUis) {
			if(ui.isSecond()) {
				hasSeconde = true;
			}
			Map<String, List<SlotUi>> slotJours = null;
			if (groupBy.containsKey(ui.getSwimmingPool())) {
				slotJours = groupBy.get(ui.getSwimmingPool());
			} else {
				slotJours = new LinkedHashMap<String, List<SlotUi>>();
				groupBy.put(ui.getSwimmingPool(), slotJours);
			}
			List<SlotUi> slots = null;
			if (slotJours.containsKey(ui.getDayOfWeek())) {
				slots = slotJours.get(ui.getDayOfWeek());
			} else {
				slots = new ArrayList<SlotUi>(3);
				slotJours.put(ui.getDayOfWeek(), slots);
			}
			slots.add(ui);
		}
		result.setSeconde(hasSeconde);
		for (Map.Entry<String, Map<String, List<SlotUi>>> entry : groupBy
				.entrySet()) {
			LoadingSlotUi slotUi = new LoadingSlotUi(entry.getKey());
			result.addSlots(slotUi);
			for (Map.Entry<String, List<SlotUi>> entryJour : entry.getValue()
					.entrySet()) {
				switch (entryJour.getKey()) {
				case "Lundi":
					slotUi.setLundi(entryJour.getValue());
					break;
				case "Mardi":
					slotUi.setMardi(entryJour.getValue());
					break;
				case "Mercredi":
					slotUi.setMercredi(entryJour.getValue());
					break;
				case "Jeudi":
					slotUi.setJeudi(entryJour.getValue());
					break;
				case "Vendredi":
					slotUi.setVendredi(entryJour.getValue());
					break;
				case "Samedi":
					slotUi.setSamedi(entryJour.getValue());
					break;
				default:
					slotUi.setSamedi(entryJour.getValue());
				}
			}
		}
		result.sort();
		return result;
	}
}