package com.asptttoulousenatation.core.groupe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.adherent.CreneauxBean;
import com.asptttoulousenatation.core.server.dao.club.group.PiscineDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.PiscineEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.PiscineEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;

@Path("/creneaux")
@Produces("application/json")
public class CreneauService {

	private static final Logger LOG = Logger.getLogger(CreneauService.class
			.getName());

	private SlotDao dao = new SlotDao();
	private PiscineDao piscineDao = new PiscineDao();
	private SlotTransformer transformer = new SlotTransformer();
	
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
	
	@Path("/create/{groupe}")
	@POST
	public SlotUi create(@PathParam("groupe") Long groupe, SlotUi creneau) {
		final SlotEntity entity;
		if(creneau.getId() == null) {
			entity = new SlotEntity();
		} else {
			entity = dao.get(creneau.getId());
		}
		entity.setDayOfWeek(creneau.getDayOfWeek());
		entity.setGroup(groupe);
		
		if(StringUtils.isNotBlank(creneau.getSwimmingPool())) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<String>(PiscineEntityFields.INTITULE, creneau.getSwimmingPool(),
					Operator.EQUAL));
			List<PiscineEntity> lEntities = piscineDao.find(criteria);
			if(CollectionUtils.isEmpty(lEntities)) {
				PiscineEntity piscine = new PiscineEntity();
				piscine.setIntitule(creneau.getSwimmingPool());
				piscineDao.save(piscine);
			}
			entity.setSwimmingPool(creneau.getSwimmingPool());
		}
		
		
		entity.setSecond(creneau.isSecond());
		
		int placeRestante = entity.getPlaceRestante() == null ? 0: entity.getPlaceRestante();
		int placeDisponible = entity.getPlaceDisponible() == null ? 0: entity.getPlaceDisponible();
		entity.setPlaceRestante(placeRestante + (creneau.getPlaceDisponible() - placeDisponible));
		entity.setPlaceDisponible(creneau.getPlaceDisponible());
		
		entity.setBeginDt(creneau.getBeginDt());
		entity.setEndDt(creneau.getEndDt());
		SlotEntity entityUpdated = dao.save(entity);
		return transformer.toUi(entityUpdated);
	}
	
	@Path("{creneau}")
	@DELETE
	public void delete(@PathParam("creneau") Long creneauId) {
		dao.delete(creneauId);
	}
}