package com.asptttoulousenatation.server.userspace.admin.actu;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

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
		
		Document document = Jsoup.parse(pAction.getContent());
		String facebookText = document.text();
		FacebookClient facebookClient = new DefaultFacebookClient("CAAKgVklrgZCoBAOngDFrI5X24JXMRFjyKMzCXRoZAz26KT7XHaenWxU5MLtkZCbu9vk09UNYkwZB6YivEUftisnO7i7FYDcgPo7VnW0fELT9gRNwucy3wtkG9ms0Cq4KaCxqIJ70Sj1QSJ9pdg17YVcRtaSoV52YbhzPlVNAfVGnDcuKyW5c");
		FacebookType publishMessageResponse =
				facebookClient.publish("710079642422594/feed", FacebookType.class,
				    Parameter.with("message", facebookText));
		return new PublishActionResult();
	}

	public Class<PublishActuAction> getActionType() {
		return PublishActuAction.class;
	}

	public void rollback(PublishActuAction pArg0, PublishActionResult pArg1,
			ExecutionContext pArg2) throws DispatchException {
	}
}