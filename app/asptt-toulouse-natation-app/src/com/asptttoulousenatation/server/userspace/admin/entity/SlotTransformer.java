package com.asptttoulousenatation.server.userspace.admin.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.asptttoulousenatation.core.groupe.GroupUi;
import com.asptttoulousenatation.core.groupe.SlotUi;
import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.server.util.DateUtils;

public class SlotTransformer extends
		AbstractEntityTransformer<SlotUi, SlotEntity> {

	private GroupTransformer groupTransformer = new GroupTransformer();
	
	private static SlotTransformer INSTANCE;
	
	public static SlotTransformer getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SlotTransformer();
		}
		return INSTANCE;
	}
	
	@Override
	public SlotUi toUi(SlotEntity pEntity) {
		SlotUi lUi = new SlotUi();
		lUi.setId(pEntity.getId());
		lUi.setDayOfWeek(pEntity.getDayOfWeek());
		lUi.setBegin(pEntity.getBegin());
		lUi.setEnd(pEntity.getEnd());
		lUi.setBeginDt(pEntity.getBeginDt());
		lUi.setEndDt(pEntity.getEndDt());
		lUi.setSwimmingPool(pEntity.getSwimmingPool());
		lUi.setEducateur(pEntity.getEducateur());
		lUi.setSecond(BooleanUtils.toBoolean(pEntity.getSecond()));
		int[] begin = DateUtils.getHour(pEntity.getBegin());
		lUi.setBeginStr(begin[0] + ":" + DateUtils.formatMinutes(begin[1]));
		int[] end = DateUtils.getHour(pEntity.getEnd());
		lUi.setEndStr(end[0] + ":" + DateUtils.formatMinutes(end[1]));
		
		if(pEntity.getPlaceDisponible() != null) {
			lUi.setPlaceDisponible(pEntity.getPlaceDisponible());
		}
		if(pEntity.getPlaceRestante() != null) {
			lUi.setPlaceRestante(pEntity.getPlaceRestante());
		}
		lUi.setComplet(lUi.getPlaceRestante() <= 0);
		
		if(pEntity.getBeginDt() == null) {
			lUi.setBeginDt(DateTime.now().withHourOfDay(begin[0]).withMinuteOfHour(begin[1]).toDate());
		}
		if(pEntity.getEndDt() == null) {
			lUi.setEndDt(DateTime.now().withHourOfDay(end[0]).withMinuteOfHour(end[1]).toDate());
		}
		return lUi;
	}
	
	public List<SlotUi> toUi(List<SlotEntity> pEntities, String selectedCreneaux) {
		List<SlotUi> uis = new ArrayList<SlotUi>();
		if(StringUtils.isNotBlank(selectedCreneaux)) {
			for(SlotEntity entity: pEntities) {
				uis.add(toUi(entity, selectedCreneaux));
			}
		} else {
			uis = toUi(pEntities);
		}
		return uis;
	}
	
	public SlotUi toUi(SlotEntity pEntity, String selectedCreneaux) {
		SlotUi lUi = new SlotUi();
		lUi.setId(pEntity.getId());
		lUi.setDayOfWeek(pEntity.getDayOfWeek());
		lUi.setBegin(pEntity.getBegin());
		lUi.setEnd(pEntity.getEnd());
		lUi.setBeginDt(pEntity.getBeginDt());
		lUi.setEndDt(pEntity.getEndDt());
		lUi.setSwimmingPool(pEntity.getSwimmingPool());
		lUi.setEducateur(pEntity.getEducateur());
		lUi.setSecond(BooleanUtils.toBoolean(pEntity.getSecond()));
		int[] begin = DateUtils.getHour(pEntity.getBegin());
		lUi.setBeginStr(begin[0] + ":" + DateUtils.formatMinutes(begin[1]));
		int[] end = DateUtils.getHour(pEntity.getEnd());
		lUi.setEndStr(end[0] + ":" + DateUtils.formatMinutes(end[1]));
		
		if(pEntity.getPlaceDisponible() != null) {
			lUi.setPlaceDisponible(pEntity.getPlaceDisponible());
		}
		if(pEntity.getPlaceRestante() != null) {
			lUi.setPlaceRestante(pEntity.getPlaceRestante());
		}
		lUi.setSelected(selectedCreneaux.contains(pEntity.getId().toString()));
		lUi.setComplet(lUi.getPlaceRestante() <= 0);
		
		if(pEntity.getBeginDt() == null) {
			lUi.setBeginDt(DateTime.now().withHourOfDay(begin[0]).withMinuteOfHour(begin[1]).toDate());
		}
		if(pEntity.getEndDt() == null) {
			lUi.setEndDt(DateTime.now().withHourOfDay(end[0]).withMinuteOfHour(end[1]).toDate());
		}
		return lUi;
	}
	
	public SlotUi toUi(SlotEntity pEntity, GroupEntity pGroupEntity) {
		SlotUi lUi = toUi(pEntity);
		GroupUi lGroupUi = groupTransformer.toUi(pGroupEntity);
		lUi.setGroup(lGroupUi);
		return lUi;
	}
}