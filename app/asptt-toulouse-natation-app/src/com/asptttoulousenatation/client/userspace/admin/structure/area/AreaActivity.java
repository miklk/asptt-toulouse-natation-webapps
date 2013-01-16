package com.asptttoulousenatation.client.userspace.admin.structure.area;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.menu.MenuItems;
import com.asptttoulousenatation.client.config.MyAbstractActivity;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentAction;
import com.asptttoulousenatation.core.shared.document.DeleteDocumentResult;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentAction;
import com.asptttoulousenatation.core.shared.document.UpdateDocumentResult;
import com.asptttoulousenatation.core.shared.structure.area.DeleteAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.DeleteAreaResult;
import com.asptttoulousenatation.core.shared.structure.area.UpdateAreaAction;
import com.asptttoulousenatation.core.shared.structure.area.UpdateAreaResult;
import com.asptttoulousenatation.core.shared.structure.menu.CreateMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.CreateMenuResult;
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.DeleteMenuResult;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.GetMenuResult;
import com.asptttoulousenatation.core.shared.structure.menu.UpdateMenuAction;
import com.asptttoulousenatation.core.shared.structure.menu.UpdateMenuResult;
import com.asptttoulousenatation.shared.userspace.admin.structure.area.AreaUi;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.SelectionChangeEvent;

public class AreaActivity extends MyAbstractActivity<AreaPlace> {

	private AreaUi area;
	
	public AreaActivity(AreaPlace pPlace, ClientFactory pClientFactory) {
		super(pPlace, pClientFactory);
	}

	public void start(AcceptsOneWidget pPanel, final EventBus pEventBus) {
		final AreaView lAreaView = clientFactory.getAreaView(area);
		
		lAreaView.setPageSelectionAction(new SelectionChangeEvent.Handler() {
			
			public void onSelectionChange(SelectionChangeEvent pEvent) {
				dispatchAsync.execute(new GetMenuAction(lAreaView.getMenuId()), new AsyncCallback<GetMenuResult>() {
					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur " + pCaught.getMessage());
					}

					public void onSuccess(GetMenuResult pResult) {
						lAreaView.buildEditionPanel(pResult.getMenu());
					}
				});
			}
		});
		
		lAreaView.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new UpdateMenuAction(lAreaView.getMenuId(), lAreaView.getMenuCreationMenuKey(), lAreaView.getMenuTitle().getValue(), lAreaView.getContentId(), lAreaView.getSummary().getValue(), lAreaView.getContent().getBytes()), new AsyncCallback<UpdateMenuResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur " + pCaught.getMessage());
					}

					public void onSuccess(UpdateMenuResult pResult) {
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
		
		lAreaView.getAreaUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new UpdateAreaAction(area.getId(), lAreaView.getAreaTitle().getValue(), lAreaView.getAreaOrder()), new AsyncCallback<UpdateAreaResult>() {
					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}
					public void onSuccess(UpdateAreaResult pResult) {
						Window.alert("Zone mise à jour !");
					}
				});
			}
		});
		lAreaView.getMenuCreationButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new CreateMenuAction(lAreaView.getMenuCreationMenuKey(), lAreaView.getMenuCreationTitle().getValue(), lAreaView.getMenuCreationSummary().getValue(), lAreaView.getMenuCreationContent(), (short)0, area.getId(), lAreaView.getParentMenuId()), new AsyncCallback<CreateMenuResult>() {
					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}
					public void onSuccess(CreateMenuResult pResult) {
						Window.alert("Menu créé !");
						lAreaView.hideMenuCreationPopup();
						pEventBus.fireEvent(new UpdateContentEvent(MenuItems.STRUCTURE, pResult.getArea()));
					}
				});
			}
		});
		lAreaView.getAreaDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new DeleteAreaAction(area.getId()), new AsyncCallback<DeleteAreaResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}

					public void onSuccess(DeleteAreaResult pResult) {
						Window.alert("Supprimé avec succès.");
						pEventBus.fireEvent(new UpdateContentEvent(MenuItems.REFRESH_ADMIN));
					}
				});
			}
		});
		lAreaView.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent pEvent) {
				dispatchAsync.execute(new DeleteMenuAction(lAreaView.getMenuId()), new AsyncCallback<DeleteMenuResult>() {

					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur: " + pCaught.getMessage());
					}

					public void onSuccess(DeleteMenuResult pResult) {
						Window.alert("Supprimé avec succès.");
						pEventBus.fireEvent(new UpdateContentEvent(MenuItems.STRUCTURE, pResult.getArea()));
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