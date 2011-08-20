package com.asptttoulousenatation.server.userspace.admin.structure.content;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.UpdateContentAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.UpdateContentResult;
import com.google.appengine.api.datastore.Blob;

public class UpdateContentActionHandler implements
		ActionHandler<UpdateContentAction, UpdateContentResult> {

	public UpdateContentResult execute(UpdateContentAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Get content
		ContentDao lContentDao = new ContentDao();
		ContentEntity lContentEntity = lContentDao.get(pAction.getContentId());
		//Update fields
		lContentEntity.setSummary(pAction.getSummary());
		lContentEntity.setData(new Blob(pAction.getContent()));
		lContentDao.save(lContentEntity);
		return new UpdateContentResult();
	}

	public Class<UpdateContentAction> getActionType() {
		return UpdateContentAction.class;
	}

	public void rollback(UpdateContentAction pAction, UpdateContentResult pResult,
			ExecutionContext pContext) throws DispatchException {
	}
}