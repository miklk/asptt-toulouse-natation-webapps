package com.asptttoulousenatation.client;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.asptttoulousenatation.client.config.ClientFactory;
import com.asptttoulousenatation.client.config.ClientFactoryImpl;
import com.asptttoulousenatation.client.config.MainActivityMapper;
import com.asptttoulousenatation.client.config.MainPlaceHistoryMapper;
import com.asptttoulousenatation.client.resources.ASPTT_ProtoCss;
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
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Asptt_toulouse_natation_app implements EntryPoint {

	public static ASPTT_ProtoCss CSS = ASPTT_ProtoResources.RESOURCE.css();
	
	protected DispatchAsync dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
	public static  PopupManager popupManager = new PopupManager();

	public void onModuleLoad() {
		CSS.ensureInjected();
		AutohitsCoreResource.RESOURCE.css().ensureInjected();
		final SimplePanel lPanel = new SimplePanel();

		final ClientFactory lClientFactory = new ClientFactoryImpl();
		final EventBus lEventBus = lClientFactory.getEventBus();
		final PlaceController lPlaceController = lClientFactory.getPlaceController();
		
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

		dispatchAsync.execute(new IsAuthenticatedAction(), new AsyncCallback<IsAuthenticatedResult>() {
			public void onFailure(Throwable pCaught) {
				Window.alert("Erreur " + pCaught.getMessage());
			}

			public void onSuccess(IsAuthenticatedResult pResult) {
				if(pResult.isAuthenticated()) {
					lPlaceHistoryHandler.register(lPlaceController, lEventBus,
							new UserSpacePlace(pResult.getUser()));
				}
				else {
					lPlaceHistoryHandler.register(lPlaceController, lEventBus,
							new MainPlace(pResult.getUser()));
				}
				RootPanel.get().add((Widget) lPanel);
				lPlaceHistoryHandler.handleCurrentHistory();
			}
		});
		

		lEventBus.addHandler(UpdateContentEvent.TYPE,
				new UpdateContentEventHandler() {

					public void updateContent(UpdateContentEvent<?> pEvent) {
						switch (pEvent.getAction()) {
						case ADMIN:
							lPlaceController.goTo(new UserSpacePlace((UserUi) pEvent.getObject()));
							break;
						case PUBLIC:
							lPlaceController.goTo(new MainPlace((UserUi) pEvent.getObject()));
							break;
						default: // Do nothing
						}
					}
				});
	}

}