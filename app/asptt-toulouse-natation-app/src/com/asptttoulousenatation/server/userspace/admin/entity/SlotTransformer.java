package com.asptttoulousenatation.server.userspace.admin.entity;

import com.asptttoulousenatation.core.server.dao.entity.club.group.GroupEntity;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;

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
		return lUi;
	}
	
	public SlotUi toUi(SlotEntity pEntity, GroupEntity pGroupEntity) {
		SlotUi lUi = toUi(pEntity);
		GroupUi lGroupUi = groupTransformer.toUi(pGroupEntity);
		lUi.setGroup(lGroupUi);
		return lUi;
	}
}