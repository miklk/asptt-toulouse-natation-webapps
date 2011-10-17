package com.asptttoulousenatation.client.userspace.admin.structure.area;

import java.util.ArrayList;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.core.client.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentAction;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentResult;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentAction;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentResult;
import com.asptttoulousenatation.core.shared.structure.MenuUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.UpdateContentAction;
import com.asptttoulousenatation.shared.userspace.admin.structure.content.UpdateContentResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class AreaActivity extends MyAbstractActivity<AreaPlace> {

	private AreaUi area;
	
	public AreaActivity(AreaPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, EventBus pEventBus) {
		final AreaView lAreaView = clientFactory.getAreaView(new ArrayList<MenuUi>(area.getMenuSet().values()));
		lAreaView.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new UpdateContentAction(lAreaView.getContentId(), lAreaView.getSummary().getValue(), lAreaView.getContent().getBytes()), new AsyncCallback<UpdateContentResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur " + pCaught.getMessage());
					}

					public void onSuccess(UpdateContentResult pResult) {
						Window.alert("Mis à jour !");
					}
				});
			}
		});
		lAreaView.getDocumentUpdateButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new UpdateDocumentAction(lAreaView.getDocumentId(), lAreaView.getDocumentTitle().getValue(), lAreaView.getDocumentSummary().getValue()), new AsyncCallback<UpdateDocumentResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}

					public void onSuccess(UpdateDocumentResult pResult) {
						Window.alert("Document mis à jour !");
					}
				});
			}
		});
		lAreaView.getDocumentDeleteButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new DeleteDocumentAction(lAreaView.getDocumentId()), new AsyncCallback<DeleteDocumentResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}

					public void onSuccess(DeleteDocumentResult pResult) {
						Window.alert("Document supprimé !");
					}
				});
				
			}
		});
		pPanel.setWidget(lAreaView);

	}

	public AreaUi getArea() {
		return area;
	}

	public void setArea(AreaUi pArea) {
		area = pArea;
	}
}