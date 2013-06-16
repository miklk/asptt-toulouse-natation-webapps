package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.shared.util.DateUtils;

public class SlotTransformer extends
		AbstractEntityTransformer<SlotUi, SlotEntity> {

	private GroupTransformer groupTransformer = new GroupTransformer();
	
	@Override
	public SlotUi toUi(SlotEntity pEntity) {
		SlotUi lUi = new SlotUi();
		lUi.setId(pEntity.getId());
		lUi.setDayOfWeek(pEntity.getDayOfWeek());
		lUi.setBegin(pEntity.getBegin());
		lUi.setEnd(pEntity.getEnd());
		lUi.setSwimmingPool(pEntity.getSwimmingPool());
		lUi.setEducateur(pEntity.getEducateur());
		int[] begin = DateUtils.getHour(pEntity.getBegin());
		lUi.setBeginStr(begin[0] + ":" + begin[1]);
		int[] end = DateUtils.getHour(pEntity.getEnd());
		lUi.setEndStr(end[0] + ":" + end[1]);
		return lUi;
	}
	
	public SlotUi toUi(SlotEntity pEntity, GroupEntity pGroupEntity) {
		SlotUi lUi = toUi(pEntity);
		GroupUi lGroupUi = groupTransformer.toUi(pGroupEntity);
		lUi.setGroup(lGroupUi);
		return lUi;
	}
}