package com.asptttoulousenatation.core.server.structure.menu;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.structure.MenuEntity;
import com.asptttoulousenatation.core.server.dao.structure.MenuDao;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.core.shared.structure.menu.UpdateMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.UpdateMenuResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.UpdateContentAction;

public class UpdateMenuActionHandler implements
		ActionHandler<UpdateMenuAction, UpdateMenuResult> {

	private MenuDao dao = new MenuDao();
	public UpdateMenuResult execute(UpdateMenuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		MenuEntity lMenu = dao.get(pAction.getMenu());
		lMenu.setMenuKey(pAction.getMenuKey());
		lMenu.setTitle(pAction.getTitle());
		dao.save(lMenu);
		//Update content
		pContext.execute(new UpdateContentAction(pAction.getContentId(), pAction.getSummary(), pAction.getContent()));
		pContext.execute(new SetDataUpdateAction(MenuEntity.class, true));
		return new UpdateMenuResult();
	}

	public Class<UpdateMenuAction> getActionType() {
		return UpdateMenuAction.class;
	}

	public void rollback(UpdateMenuAction pAction, UpdateMenuResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}