package com.asptttoulousenatation.server.userspace.admin.structure.content;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.ContentDataKindEnum;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.CreateContentAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.CreateContentResult;
import com.google.appengine.api.datastore.Blob;

public class CreateContentActionHandler implements
		ActionHandler<CreateContentAction, CreateContentResult> {

	public CreateContentResult execute(CreateContentAction pAction,
			ExecutionContext pContext) throws DispatchException {
		//Create content entity
		ContentEntity lContentEntity = new ContentEntity(pAction.getSummary(), new Blob(pAction.getContent()), ContentDataKindEnum.TEXT.name(), pAction.getMenuId());
		ContentDao lContentDao = new ContentDao();
		lContentDao.save(lContentEntity);
		return new CreateContentResult();
	}

	public Class<CreateContentAction> getActionType() {
		return CreateContentAction.class;
	}

	public void rollback(CreateContentAction pAction, CreateContentResult pResult,
			ExecutionContext pContext) throws DispatchException {

	}

}
