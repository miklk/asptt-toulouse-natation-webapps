package com.asptttoulousenatation.core.server.club.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.club.group.SlotDao;
import com.asptttoulousenatation.core.server.dao.entity.club.group.SlotEntity;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupAction;
import com.asptttoulousenatation.core.shared.club.group.GetAllGroupResult;
import com.asptttoulousenatation.core.shared.club.group.GroupUi;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotAction;
import com.asptttoulousenatation.core.shared.club.slot.GetAllSlotResult;
import com.asptttoulousenatation.core.shared.club.slot.SlotUi;
import com.asptttoulousenatation.server.userspace.admin.entity.SlotTransformer;

public class GetAllSlotActionHandler implements
		ActionHandler<GetAllSlotAction, GetAllSlotResult> {

	private SlotDao slotDao = new SlotDao();
	private SlotTransformer slotTransformer = new SlotTransformer();
	
	public GetAllSlotResult execute(GetAllSlotAction pAction, ExecutionContext pContext)
			throws DispatchException {
		GetAllGroupResult lGroupResult = pContext.execute(new GetAllGroupAction());
		Map<Long, GroupUi> lGroupMap = new HashMap<Long, GroupUi>(lGroupResult.getGroups().size());
		for(GroupUi lGroup: lGroupResult.getGroups()) {
			lGroupMap.put(lGroup.getId(), lGroup);
		}
		List<SlotEntity> lEntities = slotDao.getAll();
		List<SlotUi> lUis = new ArrayList<SlotUi>(lEntities.size());
		for(SlotEntity lEntity: lEntities) {
			SlotUi lUi = slotTransformer.toUi(lEntity);
			lUi.setGroup(lGroupMap.get(lEntity.getGroup()));
			lUis.add(lUi);
		}
		return new GetAllSlotResult(lGroupResult.getGroups(), lUis);
	}

	public Class<GetAllSlotAction> getActionType() {
		return GetAllSlotAction.class;
	}

	public void rollback(GetAllSlotAction arg0, GetAllSlotResult arg1,
			ExecutionContext arg2) throws DispatchException {
	}
}