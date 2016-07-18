package com.asptttoulousenatation.core.groupe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
import com.asptttoulousenatation.core.server.dao.club.group.CreneauHierarchyDao;
import com.asptttoulousenatation.core.server.dao.club.group.PiscineDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.CreneauHierarchyEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.PiscineEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.CreneauHierarchyEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.PiscineEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;
import com.asptttoulousenatation.core.util.DayComparator;
import com.asptttoulousenatation.core.util.DayStringComparator;

@Path("/creneaux")
@Produces("application/json")
public class CreneauService {

	private static final Logger LOG = Logger.getLogger(CreneauService.class
			.getName());

	private SlotDao dao = new SlotDao();
	private CreneauHierarchyDao creneauHierarchyDao = new CreneauHierarchyDao();
	private PiscineDao piscineDao = new PiscineDao();
	private SlotTransformer transformer = new SlotTransformer();
	
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
		

		CreneauxBean result = new CreneauxBean();
		result.setCreneaux(lUis);
		return result;
	}
	
	@GET
	@Path("seconde/{groupe}")
	public CreneauxBean getSecondCreneaux(@PathParam("groupe") Long groupe) {
		// Retrieve slots
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				2);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe,
				Operator.EQUAL));
		criteria.add(new CriterionDao<Boolean>(SlotEntityFields.SECOND, Boolean.TRUE,
				Operator.EQUAL));
		List<SlotEntity> lEntities = dao.find(criteria);
		List<SlotUi> lUis = new SlotTransformer().toUi(lEntities);
		Collections.sort(lUis, new DayComparator());

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
		entity.setHasSecond(creneau.isHasSecond());
		SlotEntity entityUpdated = dao.save(entity);
		
		//Build child link
		if(creneau.isHasSecond() && CollectionUtils.isNotEmpty(creneau.getChildren())) {
			//Annuler / remplace
			List<CriterionDao<? extends Object>> criteriaChild = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaChild.add(new CriterionDao<Long>(CreneauHierarchyEntityFields.PARENT, entityUpdated.getId(),
					Operator.EQUAL));
			List<CreneauHierarchyEntity> children = creneauHierarchyDao.find(criteriaChild);
			for(CreneauHierarchyEntity child: children) {
				creneauHierarchyDao.delete(child);
			}
			
			//Create
			for(Long child: creneau.getChildren()) {
				CreneauHierarchyEntity creneauHierarchyEntity = new CreneauHierarchyEntity();
				creneauHierarchyEntity.setParent(entityUpdated.getId());
				creneauHierarchyEntity.setChild(child);
				creneauHierarchyDao.save(creneauHierarchyEntity);
			}
		}
		
		return transformer.toUi(entityUpdated);
	}
	
	@Path("{creneau}")
	@DELETE
	public void delete(@PathParam("creneau") Long creneauId) {
		dao.delete(creneauId);
	}
	
	@Path("clear/{groupe}")
	@DELETE
	public void clear(@PathParam("groupe") Long groupe) {
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe,
				Operator.EQUAL));
		List<SlotEntity> entities = dao.find(criteria);
		for(SlotEntity entity: entities) {
			
			//suppression du lien avec l'enfant
			List<CriterionDao<? extends Object>> criteriaChild = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteriaChild.add(new CriterionDao<Long>(CreneauHierarchyEntityFields.PARENT, entity.getId(),
					Operator.EQUAL));
			List<CreneauHierarchyEntity> children = creneauHierarchyDao.find(criteriaChild);
			for(CreneauHierarchyEntity child: children) {
				creneauHierarchyDao.delete(child);
			}
			dao.delete(entity.getId());			
		}
	}
	
	@GET
	@Path("/all")
	public CreneauxBean getAll() {
		List<SlotEntity> lEntities = dao.getAll();
		List<SlotUi> lUis = new SlotTransformer().toUi(lEntities);
		Collections.sort(lUis, new DayComparator());

		CreneauxBean result = new CreneauxBean();
		result.setCreneaux(lUis);
		return result;
	}
	
	@GET
	@Path("/days/{groupe}")
	public Collection<String> getDays(@PathParam("groupe") Long groupe) {
		// Retrieve slots
		List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
				1);
		criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe,
				Operator.EQUAL));
		List<SlotEntity> lEntities = dao.find(criteria);
		
		Set<String> days = new TreeSet<>(new DayStringComparator());
		for(SlotEntity slot: lEntities) {
			days.add(slot.getDayOfWeek());
		}
		return days;
	}
}