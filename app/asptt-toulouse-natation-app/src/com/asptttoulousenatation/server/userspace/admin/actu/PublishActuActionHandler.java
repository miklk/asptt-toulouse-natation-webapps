package com.asptttoulousenatation.server.userspace.admin.actu;

import org.apache.commons.lang3.StringUtils;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.asptttoulousenatation.core.server.dao.ActuDao;
import com.asptttoulousenatation.core.server.dao.document.DocumentDao;
import com.asptttoulousenatation.core.server.dao.entity.ActuEntity;
import com.asptttoulousenatation.core.server.dao.entity.document.DocumentEntity;
import com.asptttoulousenatation.core.server.dao.entity.structure.ContentEntity;
import com.asptttoulousenatation.core.server.dao.structure.ContentDao;
import com.asptttoulousenatation.core.shared.reference.SetDataUpdateAction;
import com.asptttoulousenatation.shared.userspace.admin.actu.PublishActionResult;
import com.asptttoulousenatation.shared.userspace.admin.actu.PublishActuAction;
import com.google.appengine.api.datastore.Text;

public class PublishActuActionHandler implements
		ActionHandler<PublishActuAction, PublishActionResult> {

	private ContentDao contentDao = new ContentDao();
	private DocumentDao documentDao = new DocumentDao();
	
	public PublishActionResult execute(PublishActuAction pAction,
			ExecutionContext pContext) throws DispatchException {
		ActuEntity lActuEntity = new ActuEntity(null, pAction.getTitle(),
				pAction.getSummary(), new Text(pAction.getContent()), pAction.getCreationDate(), StringUtils.defaultString(pAction.getImageUrl(), "img/actu_defaut.jpg"), pAction.isCompetition(), pAction.getExpiration());
		ActuDao lActuDao = new ActuDao();
		ActuEntity createdActu = lActuDao.save(lActuEntity);
		
		//Update document
		if(pAction.getDocumentId() != null) {
			DocumentEntity documentEntity = documentDao.get(pAction.getDocumentId());
			documentEntity.setMenuId(createdActu.getId());
			documentDao.save(documentEntity);
			
			ContentEntity contentEntity = contentDao.get(documentEntity.getDataId());
			contentEntity.setMenu(createdActu.getId());
			contentDao.save(contentEntity);
		}
		pContext.execute(new SetDataUpdateAction(ActuEntity.class, true));
		return new PublishActionResult();
	}

	public Class<PublishActuAction> getActionType() {
		return PublishActuAction.class;
	}

	public void rollback(PublishActuAction pArg0, PublishActionResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
	}
}