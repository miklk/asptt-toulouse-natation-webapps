package com.asptttoulousenatation.core.server.dao.club.group;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.asptttoulousenatation.core.server.dao.DaoBase;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;

public class SlotDao extends DaoBase<SlotEntity> {

	@Override
	public Class<SlotEntity> getEntityClass() {
		return SlotEntity.class;
	}
	
	public List<String> getPiscines() {
		LinkedHashSet<String> piscines = new LinkedHashSet<String>();
		for(SlotEntity entity: getAll()) {
			piscines.add(entity.getSwimmingPool());
		}
		return new ArrayList<String>(piscines);
	}

	@Override
	public String getAlias() {
		return "slot";
	}
	
	@Override
	public Object getKey(SlotEntity pEntity) {
		return pEntity.getId();
	}
}