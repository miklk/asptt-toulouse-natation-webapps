package com.asptttoulousenatation.client;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.ClientFactoryImpl;
import com.asptttoulousenatation.client.config.MainActivityMapper;
import com.asptttoulousenatation.client.config.MainPlaceHistoryMapper;
import com.asptttoulousenatation.client.resources.ASPTT_ProtoResources;
import com.asptttoulousenatation.client.userspace.UserSpacePlace;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEvent;
import com.asptttoulousenatation.client.userspace.admin.event.UpdateContentEventHandler;
import com.asptttoulousenatation.core.client.resource.AutohitsCoreResource;
import com.asptttoulousenatation.core.shared.user.IsAuthenticatedAction;
import com.asptttoulousenatation.core.shared.user.IsAuthenticatedResult;
import com.asptttoulousenatation.core.shared.user.UserUi;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class ApplicationLaunch {

	public ApplicationLaunch() {
		
	}
	
	public void init() {
		Asptt_toulouse_natation_app.CSS = ASPTT_ProtoResources.RESOURCE.css();
		Asptt_toulouse_natation_app.CSS.ensureInjected();
		AutohitsCoreResource.RESOURCE.css().ensureInjected();
		final SimplePanel lPanel = new SimplePanel();
		DOM.setStyleAttribute(lPanel.getElement(), "width", "0px");

		final ClientFactory lClientFactory = new ClientFactoryImpl();
		final EventBus lEventBus = lClientFactory.getEventBus();
		final PlaceController lPlaceController = lClientFactory
				.getPlaceController();

		// Start the activity manager
		ActivityMapper lActivityMapper = new MainActivityMapper(lClientFactory);
		ActivityManager lActivityManager = new ActivityManager(lActivityMapper,
				lEventBus);
		lActivityManager.setDisplay(lPanel);

		// Start place history
		final MainPlaceHistoryMapper lPlaceHistoryMapper = GWT
				.create(MainPlaceHistoryMapper.class);
		final PlaceHistoryHandler lPlaceHistoryHandler = new PlaceHistoryHandler(
				lPlaceHistoryMapper);

		lEventBus.addHandler(UpdateContentEvent.TYPE,
				new UpdateContentEventHandler() {

					public void updateContent(UpdateContentEvent<?> pEvent) {
						switch (pEvent.getAction()) {
						case ADMIN:
							lPlaceController.goTo(new UserSpacePlace(
									(UserUi) pEvent.getObject()));
							break;
						case PUBLIC:
							lPlaceController.goTo(new MainPlace((UserUi) pEvent
									.getObject()));
							break;
						default: // Do nothing
						}
					}
				});

		DispatchAsync dispatchAsync = new StandardDispatchAsync(
				new DefaultExceptionHandler());
		dispatchAsync.execute(new IsAuthenticatedAction(),
				new AsyncCallback<IsAuthenticatedResult>() {
					public void onFailure(Throwable pCaught) {
						Window.alert("Erreur de connexion");
						GWT.log("Is authenticated error", pCaught);
					}

					public void onSuccess(final IsAuthenticatedResult pResult) {
						if (pResult.isAuthenticated()) {
							GWT.runAsync(new RunAsyncCallback() {
								
								public void onSuccess() {
									lPlaceHistoryHandler.register(lPlaceController,
											lEventBus,
											new UserSpacePlace(pResult.getUser()));
									lPlaceHistoryHandler.handleCurrentHistory();
								}
								
								public void onFailure(Throwable pReason) {
									GWT.log("Erreur lors du chargement du code (admin)", pReason);
								}
							});
						} else {
							MainActivity activity = new MainActivity(
									new MainPlace(pResult.getUser()),
									lClientFactory, null);
							activity.start(lPanel, lEventBus);
						}
						DOM.removeChild(RootPanel.getBodyElement(),
								DOM.getElementById("loading"));
						RootPanel.get("gwt-container").add((Widget) lPanel);
						DOM.setStyleAttribute(RootPanel.get("gwt-container")
								.getElement(), "visibility", "visible");
					}
				});
	}
}
