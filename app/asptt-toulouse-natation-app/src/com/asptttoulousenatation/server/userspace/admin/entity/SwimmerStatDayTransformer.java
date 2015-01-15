package com.asptttoulousenatation.server.userspace.admin.entity;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.asptttoulousenatation.core.server.dao.entity.swimmer.SwimmerStatEntity;
import com.asptttoulousenatation.core.shared.swimmer.DayTimeEnum;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatDataUi;
import com.asptttoulousenatation.core.shared.swimmer.SwimmerStatUi;

public class SwimmerStatDayTransformer extends AbstractEntityTransformer<SwimmerStatUi, SwimmerStatEntity> {

	@Override
	public SwimmerStatUi toUi(SwimmerStatEntity pEntity) {
		SwimmerStatUi stat = new SwimmerStatUi();
		stat.setId(pEntity.getId());
		stat.setAdherent(pEntity.getSwimmer());
		stat.setDay(pEntity.getDay());
		return stat;
	}
	
	public void update(SwimmerStatUi dayStat, SwimmerStatEntity pEntity) {
		SwimmerStatDataUi stat = new SwimmerStatDataUi();
		stat.setId(pEntity.getId());
		stat.setDayTime(DayTimeEnum.valueOf(pEntity
				.getDaytime()));
		stat.setDistance(pEntity.getDistance());
		if (StringUtils.isNotBlank(pEntity.getComment())) {
			stat.setComment(pEntity.getComment());
		}
		stat.setPresence(BooleanUtils.toBoolean(pEntity.getPresence()));
		switch (DayTimeEnum.valueOf(pEntity.getDaytime())) {
		case MATIN:
			dayStat.setMorning(stat);
			break;
		case MIDI:
			dayStat.setMidday(stat);
			break;
		case SOIR:
			dayStat.setNight(stat);
			break;
		case MUSCU:
			dayStat.setBodybuilding(stat);
			break;
		default:// Do nothing
		}
	}
}