package com.asptttoulousenatation.core.groupe.stat;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.BooleanUtils;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.server.dao.club.group.GroupDao;
import com.asptttoulousenatation.core.server.dao.club.group.PiscineDao;
import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.PiscineEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.server.dao.entity.field.GroupEntityFields;
import com.asptttoulousenatation.core.server.dao.entity.field.SlotEntityFields;
import com.asptttoulousenatation.core.server.dao.search.CriterionDao;
import com.asptttoulousenatation.core.server.dao.search.Operator;

@Path("/creneauxStat")
@Produces("application/json")
public class CreneauStatService {

	
	private GroupDao groupeDao = new GroupDao();
	private SlotDao slotDao = new SlotDao();
	private PiscineDao piscineDao = new PiscineDao();
	
	@Path("/groupes")
	@GET
	public List<GroupeStatUi> groupes(@QueryParam("enf") Boolean enf) {
		List<GroupeStatUi> result = new ArrayList<>();
		
		final List<GroupEntity> groupes;
		if(BooleanUtils.isTrue(enf)) {
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					1);
			criteria.add(new CriterionDao<Boolean>(GroupEntityFields.ENF, Boolean.TRUE,
					Operator.EQUAL));
			groupes = groupeDao.find(criteria);
		} else {
			groupes = groupeDao.getAll();
		}
		for(GroupEntity groupe: groupes) {
			GroupeStatUi stat = new GroupeStatUi();
			stat.setGroupeTitle(groupe.getTitle());
			//récupération des créneaux
			List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
					2);
			criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe.getId(),
					Operator.EQUAL));
			criteria.add(new CriterionDao<Boolean>(SlotEntityFields.SECOND, Boolean.FALSE,
					Operator.EQUAL));
			List<SlotEntity> entities = slotDao.find(criteria);
			for(SlotEntity entity: entities) {
				stat.addCapacite(entity.getPlaceDisponible());
				stat.addDisponibles(entity.getPlaceRestante());
			}
			result.add(stat);
		}
		return result;
	}
	
	@Path("/piscines")
	@GET
	public List<PiscineStatUi> piscines() {
		List<PiscineStatUi> result = new ArrayList<>();
		for(PiscineEntity piscine: piscineDao.getAll()) {
			PiscineStatUi stat = new PiscineStatUi();
			stat.setStatTitle(piscine.getIntitule());
			
			//Récupération des jours
			List<String> dayOfWeeks = slotDao.getDays(piscine.getIntitule());
			//récupération des créneaux par jour
			for(String dayOfWeek: dayOfWeeks) {
				JourStat jourStat = new JourStat();
				jourStat.setStatTitle(dayOfWeek);
				
				List<Long> groupes = slotDao.getGroupesDays(piscine.getIntitule(), dayOfWeek);
				for(Long groupe: groupes) {
					GroupEntity groupeEntity = groupeDao.get(groupe);
					GroupeStat groupeStat = new GroupeStat();
					groupeStat.setStatTitle(groupeEntity.getTitle());
					
					List<CriterionDao<? extends Object>> criteria = new ArrayList<CriterionDao<? extends Object>>(
							3);
					criteria.add(new CriterionDao<String>(SlotEntityFields.SWIMMINGPOOL, piscine.getIntitule(),
							Operator.EQUAL));
					criteria.add(new CriterionDao<String>(SlotEntityFields.DAYOFWEEK, dayOfWeek,
							Operator.EQUAL));
					criteria.add(new CriterionDao<Long>(SlotEntityFields.GROUP, groupe,
							Operator.EQUAL));
					List<SlotEntity> entities = slotDao.find(criteria);
					for(SlotEntity entity: entities) {
						StatBase creneauStat = new StatBase();
						creneauStat.setStatTitle(new DateTime(entity.getBeginDt().getTime()).toString("HH:mm") + " - " + new DateTime(entity.getEndDt().getTime()).toString("HH:mm"));
						creneauStat.addCapacite(entity.getPlaceDisponible());
						creneauStat.addDisponibles(entity.getPlaceRestante());
						groupeStat.addCreneau(creneauStat);
					}
					jourStat.addGroupe(groupeStat);
				}
				stat.addJour(jourStat);
			}
			result.add(stat);
		}
		return result;
	}
	
	
}
